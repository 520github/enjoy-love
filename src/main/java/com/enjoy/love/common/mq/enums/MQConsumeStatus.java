package com.enjoy.love.common.mq.enums;

public enum MQConsumeStatus {
	// 表示消费成功
	SUCCESS,
	// 表示消费失败，但是稍后还会重新消费此消息
	LATER,
	// 表示消费失败,但程序对该异常进行了处理(比如将此次失败的情况持久化至数据库，后续通过后台人工操作处理
	HANDLED,
}
