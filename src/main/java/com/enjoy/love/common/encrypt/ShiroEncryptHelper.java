package com.enjoy.love.common.encrypt;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(locations="classpath:properties/password.properties",prefix="password")
@Configuration
public class ShiroEncryptHelper {
	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	private String algorithmName = "md5";
	private int hashIterations = 2;
	private String encode = EncryptEncode.HEX.desc;
	
	public enum EncryptEncode{
		BASE64("base64"),HEX("hex");
		private String desc;
		private EncryptEncode(String desc){
			this.desc = desc;
		}
		public String getDesc(){
			return this.desc;
		}
	}

	public String getAlgorithmName() {
		return algorithmName;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public int getHashIterations() {
		return hashIterations;
	}

	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}
	
	public String encrypt(String str){
		return encrypt(str, null);
	}

	public String encrypt(String str, String salt){
		if(StringUtils.isBlank(str)){
			return str;
		}
		if(StringUtils.isBlank(salt)){
			if("base64".equalsIgnoreCase(encode)){
				salt = randomNumberGenerator.nextBytes().toBase64();
			}else if("hex".equalsIgnoreCase(encode)){
				salt = randomNumberGenerator.nextBytes().toHex();
			}
		}
		String result = "";
		if("base64".equalsIgnoreCase(encode)){
			result = new SimpleHash(algorithmName, str, ByteSource.Util.bytes(salt), hashIterations).toBase64();
		}else if("hex".equalsIgnoreCase(encode)){
			result = new SimpleHash(algorithmName, str, ByteSource.Util.bytes(salt), hashIterations).toHex();
		}
		return result;
	}
	
	public String encryptWithEncode(String str, EncryptEncode encode){
		if(StringUtils.isBlank(str)){
			return str;
		}
		String result = "";
		String salt = "";
		if(EncryptEncode.BASE64 == encode){
			salt = randomNumberGenerator.nextBytes().toBase64();
			result = new SimpleHash(algorithmName, str, ByteSource.Util.bytes(salt), hashIterations).toBase64();
		}else if(EncryptEncode.HEX == encode){
			salt = randomNumberGenerator.nextBytes().toHex();
			result = new SimpleHash(algorithmName, str, ByteSource.Util.bytes(salt), hashIterations).toHex();
		}
		return result;
	}
	
	public String encryptPwd(String password, String salt){
		return encrypt(password, salt);
	}
}
