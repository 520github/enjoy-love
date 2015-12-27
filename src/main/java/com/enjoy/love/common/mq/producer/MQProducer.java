package com.enjoy.love.common.mq.producer;

import com.enjoy.love.common.mq.GeneralMessage;
import com.enjoy.love.common.mq.MQSendResult;

public interface MQProducer {
	/**
	 * 发送消息服务
	 * @param message
	 * @param topic
	 * @param tag
	 * @return
	 */
	MQSendResult sendMessage(String topic, String tag, GeneralMessage msg);
}
