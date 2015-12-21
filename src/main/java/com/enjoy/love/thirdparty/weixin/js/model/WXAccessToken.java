package com.enjoy.love.thirdparty.weixin.js.model;

import java.util.Date;

public class WXAccessToken {
	
	private Date accessDate;
	
	private String access_token;
	
	private int expires_in;
	
	public Date getAccessDate() {
		return accessDate;
	}
	
	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}
	
	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public String toString() {
		return String.format("accessDate: %s, token: %s, expiresIn: %s", accessDate, access_token, expires_in);
	}
}
