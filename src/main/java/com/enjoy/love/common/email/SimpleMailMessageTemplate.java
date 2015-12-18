package com.enjoy.love.common.email;

import org.springframework.mail.SimpleMailMessage;

public class SimpleMailMessageTemplate extends SimpleMailMessage {
	
	private static final long serialVersionUID = 3438226728774526777L;
	
	public SimpleMailMessageTemplate to(String to) {
		this.setTo(to);
		return this;
	}
	
	public SimpleMailMessageTemplate from(String from) {
		this.setFrom(from);
		return this;
	}
	
	public SimpleMailMessageTemplate subject(String subject) {
		this.setSubject(subject);
		return this;
	}
	
	public SimpleMailMessageTemplate text(String text) {
		this.setText(text);
		return this;
	}
	
}
