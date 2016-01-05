/**
 * 
 */
package com.enjoy.love.common.util;

import java.util.TreeMap;

import org.junit.BeforeClass;
import org.junit.Test;

import com.enjoy.love.BaseTest;
import com.enjoy.love.common.encrypt.MessageDigestEncrypt;

/**
 * @author lenovo
 *
 */
public class MessageDigestEncryptTest extends BaseTest {
	
	private static String sercet = "a5f8fe5k59eb0c6534787b6d1a739192";
	private static byte bytes[] = new byte[10];
	
	@BeforeClass
	public static void beforeClass() {
		try {
			String source = "miaoxuehui19850425";
			bytes = MessageDigestEncrypt.sha1BytesByMessageDigest(source);
			//bytes = MessageDigestEncrypt.md5BytesByMessageDigest(source);
			printStatic("bytes length is {}", bytes.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void encryptBASE64Test() {
		try {
			String result = MessageDigestEncrypt.encryptBASE64(bytes);
			this.print("encryptBASE64Test result is {}", result);
		} catch (Exception e) {
			this.fail(e);
		}
	}
	
	@Test
	public void base64EncodeByEncodeCharTest() {
		try {
			String result = MessageDigestEncrypt.base64EncodeByEncodeChar(bytes);
			this.print("base64EncodeByEncodeCharTest result is {}", result);
		} catch (Exception e) {
			this.fail(e);
		}
	}
	
	@Test
	public void hmacSHA1AndBase64AndUrlEncodeByTreeMapTest() {
		try {
			String result = MessageDigestEncrypt.hmacSHA1AndBase64AndUrlEncodeByTreeMap(this.getTreeMapParameter(), sercet);
			this.print("result is {}", result);
		} catch (Exception e) {
			this.fail(e);
		}
	}
	
	private TreeMap<String, String> getTreeMapParameter() {
		TreeMap<String, String> paramter = new TreeMap<String, String>();
		paramter.put("bName", "lihong1");
		paramter.put("aName", "lihong2");
		paramter.put("fName", "lihong3");
		paramter.put("gName", "lihong0");
		paramter.put("dName", "lihong4");
		paramter.put("cName", "lihong5");

		return paramter;
	}
}
