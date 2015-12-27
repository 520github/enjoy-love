package com.enjoy.love.common.mq.consumer;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.enjoy.love.common.log.CommonLogger;
import com.enjoy.love.common.log.CommonLoggerFactory;
import com.enjoy.love.common.log.LogUtil;
import com.enjoy.love.common.mq.GeneralMessage;
import com.enjoy.love.common.mq.MQRuntimeException;
import com.enjoy.love.common.mq.TopicTagAssemble;
import com.enjoy.love.common.mq.enums.MQConsumeStatus;
import com.enjoy.love.common.mq.enums.MQRuntimeFailStatus;
import com.enjoy.love.common.util.CommonSerializerUtil;

public abstract class AbstractMQSyncConsumerConcurrently implements
		MessageListenerConcurrently {
	
	protected final static CommonLogger consumerLogger = CommonLoggerFactory
			.getLogger(AbstractMQSyncConsumerConcurrently.class);
	
	/**
	 * 延时时间 level对应延时时间 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h
	 * 2h
	 */
	private int DELAY_LEVEL = 0;

	/**
	 * rocketmq name server address and port,eg:192.168.3.70:9876
	 */
	private String namesrvAddr;

	private String instanceName;

	/*
	 * 消费者groupid
	 */
	protected String group;

	private String topic;

	/**
	 * broker依据此表达式进行过滤。目前只支持或运算,例如: "tag1 || tag2 || tag3"
	 * 如果subExpression等于null或者*，则表示全部订阅
	 */
	private String tagExpress;

	/**
	 * 允许接收的topics,多个使用逗号隔开
	 */
	private String topicsACL;

	/**
	 * 允许接收的tags,多个使用逗号隔开
	 */
	private String tagsACL;

	/**
	 * 是否对可以接收的topic进行检查的标示
	 */
	private boolean needCheckTopics = true;

	/**
	 * 是否对可以接收的tag进行检查的标示
	 */
	private boolean needCheckTags = true;

	protected DefaultMQPushConsumer consumer;
	
	/**
	 * 消费消息线程，最小数目
	 */
	protected int consumeThreadMin = 20;

	/**
	 * 消费消息线程，最大数目
	 */
	protected int consumeThreadMax = 100;

	/**
	 * 拉消息间隔,设置为10秒(设置太长会降低系统TPS)
	 */
	protected String pullIntervalInMillis;
	
	public void init() {
		checkPropertiesAndSetEnv();
		consumer = new DefaultMQPushConsumer();
		consumer.setNamesrvAddr(namesrvAddr);
		consumer.setInstanceName(instanceName);
		// 一次只处理一条消息，控制高可靠
		consumer.setConsumeMessageBatchMaxSize(1);
		consumer.setConsumerGroup(getGroup());
		consumer.setConsumeThreadMin(consumeThreadMin);
		consumer.setConsumeThreadMax(consumeThreadMax);
		consumer.setPullInterval(0);

		// 注册接收的Topic,Tag
		try {
			consumer.subscribe(topic, tagExpress);
			consumer.registerMessageListener(this);
			consumer.start();
			LogUtil.info(consumerLogger, "rockmq consumer start success!",
					topic + "|" + tagExpress);
		} catch (MQClientException e) {
			LogUtil.error(
					e,
					consumerLogger,
					"RocketMQ consumer subscribe err. nameserver={0},consumerGroup={1}",
					namesrvAddr, getGroup());
			throw new MQRuntimeException(MQRuntimeFailStatus.MQ_CLIENT_FAILURE,
					e);
		}
	}
	
	public void destroy() {
		if (consumer != null) {
			consumer.shutdown();
			LogUtil.info(consumerLogger,
					"Consumer Shutdown Success.topic={0},tagExpress={1}",
					getTopic(), getTagExpress());
		}
	}
	
	
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
			ConsumeConcurrentlyContext context) {
		if (null == msgs || msgs.size() != 1) {
			LogUtil.error(
					consumerLogger,
					"Consumer error. msgs is null or size is not euqals to 1:Topic={0},tags={1},group={2},msgs={3},msgs.size={4}",
					topic, tagExpress, getGroup(), msgs, msgs == null ? 0
							: msgs.size());
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
		
		GeneralMessage msg = new GeneralMessage();
		try {
			MessageExt msgExt = msgs.get(0);
			msg.setMsgKeys(msgExt.getKeys());
			msg.setMsgId(msgExt.getMsgId());
			msg.setQueueId(msgExt.getQueueId());
			msg.setBornTimestamp(msgExt.getBornTimestamp());
			msg.setStoreTimestamp(msgExt.getStoreTimestamp());
			msg.setStoreSize(msgExt.getStoreSize());
			msg.setQueueOffset(msgExt.getQueueOffset());
			msg.setStoreHost(msgExt.getStoreHost());
			msg.setCommitLogOffset(msgExt.getCommitLogOffset());
			msg.setBizValue(msgExt.getBodyCRC());
			msg.setReconsumeTimes(msgExt.getReconsumeTimes());
			Object val = CommonSerializerUtil.deSerialize(msgExt.getBody());
			msg.setBizValue(val);
		} catch (ClassNotFoundException e) {
			throw new MQRuntimeException(
					MQRuntimeFailStatus.MQ_SERIALIZE_FAILURE, e);
		} catch (IOException e) {
			throw new MQRuntimeException(
					MQRuntimeFailStatus.MQ_SERIALIZE_FAILURE, e);
		}
		StopWatch stopWatch = new StopWatch();
		try {
			stopWatch.start();
			/**
			 * doConsume在获取消息的过程中同步调用,如果业务方法执行过长,
			 * 会导致rocketmq阻塞到timeout(pullInterval的时间间隔)并触发自动重发 好处: 编程简单 坏处:
			 * 阻塞broker,2若导致重发,调用端需要写代码处理重发的情况
			 * (尤其是消息第一次到达后,业务尚未处理完毕,重发的消息又到了导致业务代码多次操作数据库的情况)
			 * 
			 * to do:
			 * 
			 * 将客户端获取msg的模式改为异步
			 * 
			 * 好处: 隔离应用与rocketmq
			 * consumer拉取线程,不阻塞broker,不需要设置pullInterval值(保持默认0就OK) 坏处: 编程复杂度提高
			 * 
			 * to do DefaultUcfMQAsynConsumerConcurrently
			 */
			MQConsumeStatus result = doConsume(msg);
			stopWatch.stop();
			LogUtil.info(
					consumerLogger,
					"doConsume execution success,time consuming is:{0},msgDetail:{1},",
					stopWatch, msg);
			
			if (result.equals(MQConsumeStatus.SUCCESS)
					|| result.equals(MQConsumeStatus.HANDLED)) {
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			} else {
				if (DELAY_LEVEL > 0) {
					context.setDelayLevelWhenNextConsume(DELAY_LEVEL);
				}
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}
		} catch (MQRuntimeException e) {
			handleMQException(msg);
			stopWatch.stop();

			LogUtil.error(
					e,
					consumerLogger,
					"doConsume execution failure,time consuming is:{0},msg={1},",
					stopWatch, msg);
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		} finally {
			//if (stopWatch.isRunning()) {
				stopWatch.stop();
			//}
			stopWatch = null;
		}
	}
	
	/**
	 * delayLevel 相应level对应的延时时间列表： 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m
	 * 20m 30m 1h 2h
	 */
	public void setDelayLevel(int level) {
		this.DELAY_LEVEL = level;
	}
	
	/**
	 * 具体业务处理 针对不同业务系统进行相应的个性化业务后续处理
	 * 
	 * @param msgs
	 * @param context
	 * @return
	 */
	public abstract MQConsumeStatus doConsume(GeneralMessage msg)
			throws MQRuntimeException;
	
	/**
	 * 返回topic及tag(tag必须是属于这个topic下的) 如果想通过程序设置topic及tag属性，可以重载该方法
	 * (目前的规则是程序设置的优先级大于在配置文件中设置的)
	 * 
	 * @param topic
	 * @param tagExpress
	 *            broker依据此表达式进行过滤。目前只支持或运算,例如: "tag1 || tag2 || tag3"
	 *            如果subExpression等于null或者*，则表示全部订阅
	 */
	public TopicTagAssemble getTopicAndTagExpress() {
		return null;
	}
	
	/**
	 * doConsume如果出现异常,调用handleMQException方法处理(比如将一些业务数据持久化至数据库,再通过其他方式处理)
	 * 此方法不允许抛出异常,需要自行处理掉
	 * 
	 * @param msg
	 */
	public abstract void handleMQException(GeneralMessage msg);
	
	
	public void checkPropertiesAndSetEnv() {
		TopicTagAssemble topicAndTag = getTopicAndTagExpress();
		if (null != topicAndTag) {
			topic = topicAndTag.getTopic();
			tagExpress = topicAndTag.getTag();
			group = "S_" + topic + "_" + tagExpress;
		}

		if (StringUtils.isEmpty(namesrvAddr)
				|| StringUtils.isEmpty(instanceName)
				|| StringUtils.isEmpty(getGroup())) {
			LogUtil.error(
					consumerLogger,
					"No setting a namesrv or instance name or group,namesrvAddr={0},instanceName={1},group={2}",
					namesrvAddr, instanceName, getGroup());
			throw new MQRuntimeException(
					MQRuntimeFailStatus.MQ_INIT_PARAM_FAILURE);
		}

		// setting nameserver environment variable
		if (StringUtils.isEmpty(System.getProperty("rocketmq.namesrv.addr"))
				&& StringUtils.isNotEmpty(namesrvAddr)) {
			System.setProperty("rocketmq.namesrv.addr", namesrvAddr);
		}

		if (StringUtils.isEmpty(topic) || StringUtils.isEmpty(tagExpress)) {
			LogUtil.error(
					consumerLogger,
					"Topic or tag value is null,checking your config file or the return value of getTopicAndTagExpress().Topic={0},tagExpress={1}",
					topic, tagExpress);
			throw new MQRuntimeException(
					MQRuntimeFailStatus.MQ_INIT_PARAM_FAILURE);
		}
		// 检查topic及tag是否在ACL列表中
		if (doCheckTopic(topic) || doCheckTag(tagExpress)) {
			LogUtil.error(
					consumerLogger,
					"checking your config file,topic or tag you ar using is not in the corresponding ACL.Topic={0},tagExpress={1},topicsACL={2},tagsACL={3}",
					topic, tagExpress, topicsACL, tagsACL);
			throw new MQRuntimeException(
					MQRuntimeFailStatus.MQ_PUBLISH_PERMISSION_FAILURE);
		}
	}
	
	public boolean doCheckTopic(String topic) {
		if (needCheckTopics && StringUtils.isNotEmpty(topicsACL)) {
			if (topicsACL.contains(topic)) {
				return true;
			}
			LogUtil.error(
					consumerLogger,
					"This topic is not in allowed topic list:topic={0},allowed topic list={1}",
					topic, topicsACL);
			return false;
		}
		return false;
	}

	public boolean doCheckTag(String tag) {
		if (needCheckTags && StringUtils.isNotEmpty(tagsACL)) {
			if (topicsACL.contains(tag)) {
				return true;
			}
			LogUtil.error(
					consumerLogger,
					"This tag is not in allowed tag list:tag={0},allowed tag list={1}",
					tag, tagsACL);
		}
		return false;
	}
	
	protected String getGroup() {
		if (StringUtils.isEmpty(group)) {
			group = "S_" + topic + "_" + tagExpress;
		}
		return group;
	}
	
	public String getNamesrvAddr() {
		return namesrvAddr;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTagExpress() {
		return tagExpress;
	}

	public void setTagExpress(String tagExpress) {
		this.tagExpress = tagExpress;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getTopicsACL() {
		return topicsACL;
	}

	public void setTopicsACL(String topicsACL) {
		this.topicsACL = topicsACL;
	}

	public String getTagsACL() {
		return tagsACL;
	}

	public void setTagsACL(String tagsACL) {
		this.tagsACL = tagsACL;
	}

	public String getPullIntervalInMillis() {
		return pullIntervalInMillis;
	}

	public void setPullIntervalInMillis(String pullIntervalInMillis) {
		this.pullIntervalInMillis = pullIntervalInMillis;
	}

}
