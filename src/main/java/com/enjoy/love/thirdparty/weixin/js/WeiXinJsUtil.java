package com.enjoy.love.thirdparty.weixin.js;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.enjoy.love.common.util.MessageFormatterUtils;
import com.enjoy.love.thirdparty.weixin.js.config.WeiXinJsConfig;
import com.enjoy.love.thirdparty.weixin.js.model.WXAccessToken;
import com.enjoy.love.thirdparty.weixin.js.model.WXJSTicket;

@Component
public class WeiXinJsUtil {
	@Autowired
	private WeiXinJsConfig weiXinJsConfig;
	
	private final static String WX_APP_ID_KEY = "WX_AppId";
	
	private static int TIME_COUNT = 4;
	
	private static WXAccessToken wxAccessToken = new WXAccessToken();

	private static WXJSTicket wxJsTicket = new WXJSTicket();
	
	
	private boolean isExpiresAccessToken() {
		if(wxAccessToken.getAccessDate() == null) {
			return true;
		}
		if(wxAccessToken.getAccessDate().getTime() + getExpires(wxAccessToken.getExpires_in()) < getCurrentDate().getTime()) {
			return true;
		}
		if(StringUtils.isBlank(wxAccessToken.getAccess_token())) {
			return true;
		}
		return false;
	}
	
	private static long getExpires(int expiresIn) {
		if(expiresIn == 0 || TIME_COUNT == 0) {
			return 0;
		}
		return (expiresIn * 1000) / TIME_COUNT;
	}
	
	private static Date getCurrentDate() {
		return new Date();
	}
	
	public WXAccessToken getWXAccessToken() {
		RestTemplate restTemplate = new RestTemplate();
		String url = this.getAccessTokenUrl();
		WXAccessToken accessToken = restTemplate.getForObject(url, WXAccessToken.class);
		return accessToken;
	}
	
	public WXJSTicket getWXJSTicket(String accessToken) {
		String url = this.getTicketUrl(accessToken);
		RestTemplate restTemplate = new RestTemplate();
		WXJSTicket ticket = restTemplate.getForObject(url, WXJSTicket.class);
		return ticket;
	}
	
	private String getAccessTokenUrl() {
		String accessTokenUrl = weiXinJsConfig.getAccessTokenUrl();
		accessTokenUrl = MessageFormatterUtils.messageArrayFormat(accessTokenUrl, this.getAppId(), this.getAppSecret());
		return accessTokenUrl;
	}
	
	private String getTicketUrl(String accessToken) {
		String ticketUrl = weiXinJsConfig.getTicketUrl();
		ticketUrl = MessageFormatterUtils.messageArrayFormat(ticketUrl, accessToken);
		return ticketUrl;
	}
	
	private String getAppId() {
		return weiXinJsConfig.getId();
	}
	
	private String getAppSecret() {
		return weiXinJsConfig.getSecret();
	}
	
	/**
	 * Sha1加密方法
	 * 
	 * @param str
	 * @return
	 */
	public static String sha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Map<String, String> getWeiXinJsSignMap(String url) {
		if(this.isExpiresAccessToken()) {
			wxAccessToken = this.getWXAccessToken();
			wxAccessToken.setAccessDate(getCurrentDate());
		}
		String accessToken = wxAccessToken.getAccess_token();
		String jsapiTicket = wxJsTicket.getTicket();
		if(StringUtils.isBlank(jsapiTicket)) {
			wxJsTicket = this.getWXJSTicket(accessToken);
			jsapiTicket = wxJsTicket.getTicket();
		}
		return this.getWeiXinJsSignMap(accessToken, jsapiTicket, url);
	}
	
	/**
	 * 获取js api相关参数map
	 * 
	 * @param jsapi_ticket
	 * @param url
	 * @return
	 */
	public Map<String, String> getWeiXinJsSignMap(String accessToken, String jsapiTicket, String url) {
		Map<String, String> jsSignMap = new HashMap<String, String>();

		String nonce_str = UUID.randomUUID().toString();
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);

		StringBuilder sb = new StringBuilder();
		sb.append("jsapi_ticket=").append(jsapiTicket).append("&noncestr=").append(nonce_str).append("&timestamp=")
				.append(timestamp).append("&url=").append(url);
		String signature = sha1(sb.toString());
		jsSignMap.put(WX_APP_ID_KEY, this.getAppId());
		jsSignMap.put("Access_token", accessToken);
		jsSignMap.put("url", url);
		jsSignMap.put("jsapi_ticket", jsapiTicket);
		jsSignMap.put("nonce", nonce_str);
		jsSignMap.put("timestamp", timestamp);
		jsSignMap.put("signature", signature);

		return jsSignMap;
	}
}
