package com.enjoy.love.controller;

import org.slf4j.helpers.MessageFormatter;

public class BaseController {
	
	protected void print(String message, Object... obj) {
		message = MessageFormatter.arrayFormat(message, obj).getMessage();
		System.out.println("message-->" + message);
	}
}
