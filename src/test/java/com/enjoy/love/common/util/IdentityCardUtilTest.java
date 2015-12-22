package com.enjoy.love.common.util;

import org.junit.Test;

import com.enjoy.love.BaseTest;

public class IdentityCardUtilTest extends BaseTest {
	
	@Test
	public void getValidateCodeTest() {
		String idCard = "35220319850408201";
		char result = IdentityCardUtil.getValidateCode(idCard);
		this.print("get result {}", result);
	}
}
