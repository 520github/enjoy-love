package com.enjoy.love.common.mq.listener;

import java.util.EventListener;

import com.enjoy.love.common.mq.GeneralMessage;
import com.enjoy.love.common.mq.enums.MQConsumeStatus;

public interface MessageListener extends EventListener {
	public MQConsumeStatus doConsume(GeneralMessage msgEvent);
}
