package com.enjoy.love.common.util;

import org.slf4j.helpers.MessageFormatter;

public class MessageFormatterUtils {
	/**
	 * 消息格式化参数为数组的情况
	 * @param format
	 * @param argArray
	 * @return
	 */
	public static String messageArrayFormat(String format, Object... argArray) {
		return MessageFormatter.arrayFormat(format, argArray).getMessage();
	}
}
