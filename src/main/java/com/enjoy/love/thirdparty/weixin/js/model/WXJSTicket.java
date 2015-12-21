package com.enjoy.love.thirdparty.weixin.js.model;

import java.util.Date;

public class WXJSTicket {
	private Date accessDate;
	
	private String ticket;
	
	private int expires_in;
	
	private String errmsg;
	
	private int errcode;
	
	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public Date getAccessDate() {
		return accessDate;
	}
	
	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}
	
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	public String toString() {
		return String.format("accessDate: %s, ticket: %s, expiresIn: %s, errmsg: %s, errcode: %s", accessDate, ticket, expires_in, errmsg, errcode);
	}
}
