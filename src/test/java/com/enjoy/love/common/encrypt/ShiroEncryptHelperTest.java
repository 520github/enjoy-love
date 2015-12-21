package com.enjoy.love.common.encrypt;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.enjoy.love.BaseSpringBootTest;

public class ShiroEncryptHelperTest extends BaseSpringBootTest {
	@Autowired
	private ShiroEncryptHelper shiroEncryptHelper;
	
	@Test
	public void encryptPwdTest() {
		String password = "123qwe";
		String salt = "17090137197" + "1A9AF39B50D44286A0D40C14AFDF1456";
		String result = shiroEncryptHelper.encryptPwd(password, salt);
		this.print("result {}, AlgorithmName {}, HashIterations {}, Encode {}", 
				result, shiroEncryptHelper.getAlgorithmName(), shiroEncryptHelper.getHashIterations(), shiroEncryptHelper.getEncode());
	}
}
