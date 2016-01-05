/**
 * 
 */
package com.enjoy.love.common.util;

import java.util.Formatter;

/**
 * @author lenovo
 *
 */
public class CharConvertUtils {
	
	public static String byteToHexByLong(byte bytes[]) {
		StringBuffer sb = new StringBuffer();
		for (int x = 0; x < bytes.length; ++x) {
	          if ((bytes[x] & 0xFF) < 16) {
	            sb.append("0");
	          }
	          sb.append(Long.toString(bytes[x] & 0xFF, 16));
        }
		return sb.toString();
	}
	
	public static String byteToHexByFormat(byte bytes[]) {
		Formatter formatter = new Formatter();
        for (byte b : bytes)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
	}
	
	public static String byteToHexByCharBuf(byte bytes[]) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		int j = bytes.length;
		char buf[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte byte0 = bytes[i];
			buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
			buf[k++] = hexDigits[byte0 & 0xf];
		}
		return new String(buf);
	}
	
	public static String byteToHexByInteger(byte bytes[]) {
		StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int val = ((int) bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
	}
	
	public static String byteToHexByIntegerHex(byte bytes[]) {
		StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int val =  bytes[i] & 0xff;
            if (val < 0x10) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
	}
}
