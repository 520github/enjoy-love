package com.enjoy.love.demo.mq.consumer;

import com.enjoy.love.common.log.CommonLogger;
import com.enjoy.love.common.log.CommonLoggerFactory;
import com.enjoy.love.common.log.LogUtil;
import com.enjoy.love.common.mq.GeneralMessage;
import com.enjoy.love.common.mq.MQRuntimeException;
import com.enjoy.love.common.mq.TopicTagAssemble;
import com.enjoy.love.common.mq.consumer.AbstractMQSyncConsumerConcurrently;
import com.enjoy.love.common.mq.enums.MQConsumeStatus;
import com.enjoy.love.common.mq.enums.MQTopicEnum;


/**
 * <bean id="regEventConsumer"
		  class="com.ucf.jrgc.user.mq.RegEventConsumer"
		  init-method="init" destroy-method="destroy">
		<property name="namesrvAddr" value="${rocketmq.namesrv.addr}"></property>
		<property name="instanceName" value="${rocketmq.consumer.instance.name}"></property>
		<property name="pullIntervalInMillis" value="${rocketmq.consumer.pullIntervalInMillis}"></property>
	</bean>
 * @author keke
 *
 */
public class RegEventConsumer extends AbstractMQSyncConsumerConcurrently {
	private static CommonLogger logger = CommonLoggerFactory.getLogger(RegEventConsumer.class);
	
	@Override
	public MQConsumeStatus doConsume(GeneralMessage msg)
			throws MQRuntimeException {
		//User user = (User)msg.getBizValue();
		//to do 调用service方法进行其他注册事件的业务实现
		//LogUtil.info(logger, "收到用户注册信息,UUID:{0}", user.getUUID());
		return MQConsumeStatus.SUCCESS;
	}

	@Override
	public void handleMQException(GeneralMessage msg) {
		// TODO Auto-generated method stub

	}
	
	@Override
    public TopicTagAssemble getTopicAndTagExpress() {
        TopicTagAssemble ttAssemble = new TopicTagAssemble();
        ttAssemble.setTopic(MQTopicEnum.MQ_REG.getTopic());
        ttAssemble.setTag(MQTopicEnum.MQ_REG.getTag());
        return ttAssemble;
    }

}
