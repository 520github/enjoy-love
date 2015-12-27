package com.enjoy.love.common.mq.producer.impl;

import org.apache.commons.lang.StringUtils;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.enjoy.love.common.log.CommonLogger;
import com.enjoy.love.common.log.CommonLoggerFactory;
import com.enjoy.love.common.log.LogUtil;
import com.enjoy.love.common.mq.GeneralMessage;
import com.enjoy.love.common.mq.MQRuntimeException;
import com.enjoy.love.common.mq.MQSendResult;
import com.enjoy.love.common.mq.enums.MQRuntimeFailStatus;
import com.enjoy.love.common.mq.producer.MQProducer;
import com.enjoy.love.common.util.CommonSerializerUtil;

public class GeneralMQProducer implements MQProducer {
	private final static CommonLogger producerLogger = CommonLoggerFactory
			.getLogger(GeneralMQProducer.class);
	
	/**
	 * rocketmq name server address and port,eg:192.168.3.70:9876
	 */
	private String namesrvAddr;

	/**
	 * producer instance name:同一个server上启动多个producer的标示信息
	 */
	private String instanceName;

	private String group;

	/**
	 * 允许发送的topics,多个使用逗号隔开
	 */
	private String topicsACL;

	/**
	 * 允许发送的tags,多个使用逗号隔开
	 */
	private String tagsACL;

	/**
	 * 是否对可以发送的topic进行检查的标示
	 */
	private boolean needCheckTopics = true;

	/**
	 * 是否对可以发送的tag进行检查的标示
	 */
	private boolean needCheckTags = true;
	
	/**
	 * com.alibaba defaultMQProducer
	 */
	private DefaultMQProducer producer;
	
	public void init() {
		checkPropertiesAndSetEnv();
		producer = new DefaultMQProducer();
		producer.setNamesrvAddr(namesrvAddr);
		producer.setInstanceName(instanceName);
		producer.setProducerGroup(group);
		try {
			producer.start();
			LogUtil.info(
					producerLogger,
					"RocketMQ producer start success:nameserver={0},instanceName={1},group={2}",
					namesrvAddr, instanceName, group);
		} catch (MQClientException e) {
			LogUtil.error(
					e,
					producerLogger,
					"RocketMQ producer start fail:nameserver={0},instanceName={1},group={2}",
					namesrvAddr, instanceName, group);
		}
	}
	
	public void destroy() {
		if (producer != null) {
			LogUtil.info(producerLogger,
					"RocketMQ 发送端关闭成功;rocketmq.namesrv.addr={0}",
					System.getProperty("rocketmq.namesrv.addr"));
		}
	}
	
	
	@Override
	public MQSendResult sendMessage(String topic, String tag, GeneralMessage msg) {
		if (doCheckTopic(topic) || doCheckTag(tag)) {
			throw new MQRuntimeException(
					MQRuntimeFailStatus.MQ_PUBLISH_PERMISSION_FAILURE);
		}
		byte[] body = null;
		try {
			body = CommonSerializerUtil.serialize(msg.getBizValue());
		} catch (Exception e) {
			LogUtil.error(e, producerLogger, null);
		}
		Message mqMsg = new Message(topic, tag, body);
		SendResult sendResult = doSend(mqMsg);
		LogUtil.info(
				producerLogger,
				"Sending rocketMQ message success: msg={0},topic={1},tag={2},sendResult={3},msgId={4}",
				mqMsg, topic, tag, sendResult.getSendStatus(),
				sendResult.getMsgId());
		
		// 转成封装对象
		MQSendResult ucfSendResult = new MQSendResult();
		ucfSendResult.setMessageQueue(sendResult.getMessageQueue());
		ucfSendResult.setMsgId(sendResult.getMsgId());
		ucfSendResult.setQueueOffset(sendResult.getQueueOffset());
		ucfSendResult.setSendStatus(sendResult.getSendStatus());
		ucfSendResult.setMqMsg(mqMsg);
				
		return ucfSendResult;
	}
	
	private SendResult doSend(Message message) {
		SendResult sendResult = null;
		try {
			sendResult = producer.send(message);
		} catch (MQClientException e) {
			LogUtil.error(e, producerLogger, "Sending rocketMQ message error",
					message);
			throw new MQRuntimeException(MQRuntimeFailStatus.MQ_CLIENT_FAILURE,
					e);
		} catch (RemotingException e) {
			LogUtil.error(e, producerLogger, "Sending rocketMQ message error",
					message);
			throw new MQRuntimeException(
					MQRuntimeFailStatus.MQ_REMOTING_FAILURE, e);
		} catch (MQBrokerException e) {
			LogUtil.error(e, producerLogger, "Sending rocketMQ message error",
					message);
			throw new MQRuntimeException(MQRuntimeFailStatus.MQ_BROKER_FAILURE,
					e);
		} catch (InterruptedException e) {
			LogUtil.error(e, producerLogger, "Sending rocketMQ message error",
					message);
			throw new MQRuntimeException(
					MQRuntimeFailStatus.MQ_INTERRUPT_FAILURE, e);
		}

		return sendResult;
	}
	
	public boolean doCheckTopic(String topic) {
		if (needCheckTopics && StringUtils.isNotEmpty(topicsACL)) {
			if (topicsACL.contains(topic)) {
				return true;
			}
			LogUtil.error(
					producerLogger,
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
					producerLogger,
					"This tag is not in allowed tag list:tag={0},allowed tag list={1}",
					tag, tagsACL);
		}
		return false;
	}
	
	public void checkPropertiesAndSetEnv() {
		if (StringUtils.isEmpty(namesrvAddr)
				|| StringUtils.isEmpty(instanceName)) {
			LogUtil.error(
					producerLogger,
					"No setting a namesrv or instance name,namesrvAddr={0},instanceName={1}",
					namesrvAddr, instanceName);
			throw new MQRuntimeException(
					MQRuntimeFailStatus.MQ_INIT_PARAM_FAILURE);
		}

		if (StringUtils.isEmpty(System.getProperty("rocketmq.namesrv.addr"))) {
			System.setProperty("rocketmq.namesrv.addr", namesrvAddr);
		}

		if (StringUtils.isEmpty(System.getProperty("rocketmq.publisher.name"))) {
			System.setProperty("rocketmq.publisher.name", instanceName);
		}

		// 如果在配置中没有设置allowedTopics属性,则认为对可以发送的topics不做检查
		if (StringUtils.isEmpty(topicsACL)) {
			needCheckTopics = false;
		}
		// 如果在配置中没有设置allowedTags属性,则认为对可以发送的tags不做检查
		if (StringUtils.isEmpty(tagsACL)) {
			needCheckTags = false;
		}
	}
	
	public String getNamesrvAddr() {
		return namesrvAddr;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
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

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
