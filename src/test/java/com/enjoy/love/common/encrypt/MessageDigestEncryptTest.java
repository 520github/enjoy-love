/**
 * 
 */
package com.enjoy.love.common.encrypt;

import org.junit.Test;

import com.enjoy.love.BaseTest;

/**
 * @author miaoxuehui@ucfgroup.com
 * @Title MessageDigestEncryptTest
 * @Copyright: Copyright (c) 2015
 * @Description: <br>
 * @Company: ucfgroup.com
 * @Created on 2016-1-13 下午3:04:08
 */
public class MessageDigestEncryptTest extends BaseTest {
	
	@Test
	public void encryptBASE64Test() {
		try {
			MessageDigestEncrypt.encryptBASE64(null);
		} catch (Exception e) {
			this.fail(e);
		}
	}
	
	@Test
	public void decryptBASE64Test() {
		try {
			String key = "";
			MessageDigestEncrypt.decryptBASE64(key);
		} catch (Exception e) {
			this.fail(e);
		}
	}
	
	@Test
	public void base64EncodeByEncodeCharTest() {
		try {
			MessageDigestEncrypt.base64EncodeByEncodeChar(null);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
