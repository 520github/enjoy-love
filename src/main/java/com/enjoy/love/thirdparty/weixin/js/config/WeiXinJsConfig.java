package com.enjoy.love.thirdparty.weixin.js.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(locations="classpath:properties/weixin/weixin_js_config.properties",prefix="wx.app")
public class WeiXinJsConfig {
	private String id;
	private String secret;
	private String token;
	private String accessTokenUrl;
	private String ticketUrl;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAccessTokenUrl() {
		return accessTokenUrl;
	}
	public void setAccessTokenUrl(String accessTokenUrl) {
		this.accessTokenUrl = accessTokenUrl;
	}
	public String getTicketUrl() {
		return ticketUrl;
	}
	public void setTicketUrl(String ticketUrl) {
		this.ticketUrl = ticketUrl;
	}
	
}
