package com.enjoy.love.common.mq;

import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

public class MQSendResult extends SendResult {
	private Message mqMsg;

	public Message getMqMsg() {
		return mqMsg;
	}

	public void setMqMsg(Message mqMsg) {
		this.mqMsg = mqMsg;
	}

	@Override
	public String toString() {
		return mqMsg.toString();
	}
}
