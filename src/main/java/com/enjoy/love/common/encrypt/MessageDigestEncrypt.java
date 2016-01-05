/**
 * 
 */
package com.enjoy.love.common.encrypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.enjoy.love.common.util.CharConvertUtils;

/**
 * @author lenovo
 *
 */
@SuppressWarnings("restriction")
public class MessageDigestEncrypt {
	private static String ENCODING =  "UTF-8";
	
	private static char[] base64EncodeChars = { 
	    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 
	    'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 
	    'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 
	    'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 
	    'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
	    'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
	    'w', 'x', 'y', 'z', '0', '1', '2', '3', 
	    '4', '5', '6', '7', '8', '9', '+', '/' };
	
	
	public static String encryptBASE64(byte[] key) throws Exception {  
	    return (new BASE64Encoder()).encodeBuffer(key);  
	} 
	
	public static byte[] decryptBASE64(String key) throws Exception {  
	    return (new BASE64Decoder()).decodeBuffer(key);  
	}  
	
	public static String md5ByMessageDigest(String source) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return CharConvertUtils.byteToHexByInteger(md5BytesByMessageDigest(source));
	}
	
	public static byte[] md5BytesByMessageDigest(String source)  throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return getBytesResultByMessageDigestAlgorithm("MD5", source);
	}
	
	public static String sha1ByMessageDigest(String source) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		 return CharConvertUtils.byteToHexByInteger(sha1BytesByMessageDigest(source));
	}
	
	public static byte[] sha1BytesByMessageDigest(String source)  throws NoSuchAlgorithmException, UnsupportedEncodingException {
		 return getBytesResultByMessageDigestAlgorithm("SHA-1", source);
	}
	
	public static String hmacSHA1Encrypt(String source, String encryptKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
		return CharConvertUtils.byteToHexByInteger(hmacSHA1BytesEncrypt(source, encryptKey));
	}
	
	public static byte[] hmacSHA1BytesEncrypt(String source, String encryptKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
		 return hmacSHA1BytesEncrypt(source.getBytes(ENCODING), encryptKey);
	}
	
	public static byte[] hmacSHA1BytesEncrypt(byte source[], String encryptKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
		 String MAC_NAME = "HmacSHA1";
		 byte[] data = encryptKey.getBytes(ENCODING);
		 SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
		 Mac mac = Mac.getInstance(MAC_NAME);
		 mac.init(secretKey);
		 return mac.doFinal(source);
	}
	
	public static String urlEncode(String source) throws UnsupportedEncodingException {
		return URLEncoder.encode(source, ENCODING);
	}
	
	public static String hmacSHA1AndBase64AndUrlEncodeByTreeMap(TreeMap<String, String> params, String sercet) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {
		String signStr = "";
		String flag = "&";
		for (String pName : params.keySet()) {
			signStr = signStr + pName + "=" + (String)params.get(pName) + flag ;
		}
		if(signStr.lastIndexOf(flag) > -1) {
			signStr = signStr.substring(0, signStr.length() - flag.length());
		}
		System.out.println("signStr---->" + signStr);
		return hmacSHA1AndBase64AndUrlEncode(signStr, sercet);
	}
	
	public static String hmacSHA1AndBase64AndUrlEncode(String source, String sercet) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {
		byte[] hmacSHA1Bytes = hmacSHA1BytesEncrypt(source, sercet);
		String base64 = base64EncodeByEncodeChar(hmacSHA1Bytes);
		return urlEncode(base64);
	}
	
	private static byte[] getBytesResultByMessageDigestAlgorithm(String algorithm, String source)  throws NoSuchAlgorithmException, UnsupportedEncodingException  {
		MessageDigest sha =  MessageDigest.getInstance(algorithm);
	    sha.reset();
	    sha.update(source.getBytes(ENCODING));
	    return sha.digest();
	}
	
	public static String base64EncodeByEncodeChar(byte[] data)
	  {
	    StringBuffer sb = new StringBuffer();
	    int len = data.length;
	    int i = 0;

	    while (i < len) {
	      int b1 = data[(i++)] & 0xFF;
	      if (i == len) {
	        sb.append(base64EncodeChars[(b1 >>> 2)]);
	        sb.append(base64EncodeChars[((b1 & 0x3) << 4)]);
	        sb.append("==");
	        break;
	      }
	      int b2 = data[(i++)] & 0xFF;
	      if (i == len) {
	        sb.append(base64EncodeChars[(b1 >>> 2)]);
	        sb.append(base64EncodeChars[((b1 & 0x3) << 4 | (b2 & 0xF0) >>> 4)]);
	        sb.append(base64EncodeChars[((b2 & 0xF) << 2)]);
	        sb.append("=");
	        break;
	      }
	      int b3 = data[(i++)] & 0xFF;
	      sb.append(base64EncodeChars[(b1 >>> 2)]);
	      sb.append(base64EncodeChars[((b1 & 0x3) << 4 | (b2 & 0xF0) >>> 4)]);
	      sb.append(base64EncodeChars[((b2 & 0xF) << 2 | (b3 & 0xC0) >>> 6)]);
	      sb.append(base64EncodeChars[(b3 & 0x3F)]);
	    }
	    return sb.toString();
	  }
}
