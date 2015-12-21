package com.enjoy.love;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.json.JSONObject;
import org.junit.Assert;
import org.slf4j.helpers.MessageFormatter;

public class BaseTest {
	protected void print(Object obj) {
		if(obj == null) {
			return;
		}
		this.print("{}", obj);
	}
	
	protected void print(String format, Object... obj) {
		String message =  MessageFormatter.arrayFormat(format, obj).getMessage();
		System.out.println(message);
	}
	
	protected String toJson(Object obj) {
		if(obj == null) {
			return null;
		}
		return JSONObject.valueToString(obj);
	}
	
	protected void fail(Exception e) {
		if(e == null) {
			return;
		}
		e.printStackTrace();
		Assert.fail(e.getMessage());
	}
	
	protected Date getCurrentDate() {
		return new Date();
	}
	
	protected int getRandomInt() {
		return 10;
	}
	
	protected String getRandomString() {
		return UUID.randomUUID().toString();
	}
	
	protected String getRandomStringAndReplace() {
		return this.getRandomString().replaceAll("-", "");
	}
	
	protected BigDecimal getRandomBigDecimal() {
		return new BigDecimal(100);
	}
	
	protected String getRandomUrl() {
		return "http://www.didi.com/" + this.getRandomInt();
	}
}
