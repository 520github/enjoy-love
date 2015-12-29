package com.enjoy.love.frame.member.jwt;

import net.minidev.json.JSONObject;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;

public class JWTExample {
	static String DUOSHUO_SHORTNAME = "test";
	static String DUOSHUO_SECRET = "3d990d2276917dfac04467df11fff26d";
	
	public static void main(String[] args) {
		try {
			JSONObject userInfo = new JSONObject();
	        
	        userInfo.put("short_name", DUOSHUO_SHORTNAME);//必须项
	        userInfo.put("user_key", "1");//必须项
	        userInfo.put("name", "网站用户A");//可选项
	        
			Payload payload = new Payload(userInfo);
			
			JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
			
			
			JWSObject jwsObject = new JWSObject(header, payload);
			
			JWSSigner signer = new MACSigner(DUOSHUO_SECRET.getBytes());
			
			jwsObject.sign(signer);
			
			String token = jwsObject.serialize();
			
			System.out.println("Serialised JWS object: " + token);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
