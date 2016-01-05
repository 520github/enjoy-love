/**
 * 
 */
package com.enjoy.love.common.util;

import org.junit.BeforeClass;
import org.junit.Test;

import com.enjoy.love.BaseTest;
import com.enjoy.love.common.encrypt.MessageDigestEncrypt;

/**
 * @author lenovo
 *
 */
public class CharConvertUtilsTest extends BaseTest {
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
	public void byteToHexByLongTest() {
		try {
			String result = CharConvertUtils.byteToHexByLong(bytes);
			this.print("byteToHexByLong result is {}", result);
		} catch (Exception e) {
			this.fail(e);
		}
	}
	
	@Test
	public void byteToHexByFormatTest() {
		try {
			String result = CharConvertUtils.byteToHexByFormat(bytes);
			this.print("result is {}", result);
		} catch (Exception e) {
			this.fail(e);
		}
	}
	
	@Test
	public void byteToHexByCharBufTest() {
		try {
			String result = CharConvertUtils.byteToHexByCharBuf(bytes);
			this.print("result is {}", result);
		} catch (Exception e) {
			this.fail(e);
		}
	}
	
	@Test
	public void byteToHexByIntegerTest() {
		try {
			String result = CharConvertUtils.byteToHexByInteger(bytes);
			this.print("result is {}", result);
		} catch (Exception e) {
			this.fail(e);
		}
	}
	
	@Test
	public void byteToHexByIntegerHexTest() {
		try {
			String result = CharConvertUtils.byteToHexByIntegerHex(bytes);
			this.print("result is {}", result);
		} catch (Exception e) {
			this.fail(e);
		}
	}
}
