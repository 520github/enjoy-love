package com.enjoy.love.common.email;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(locations="classpath:email.properties",prefix="email")
public class EmailUtil {
	
	private String from;
	private String to;
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public MimeMessageHelperTemplate getMimeMessageHelperTemplate(MimeMessage mimeMessage) throws MessagingException {
		File file = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Hydrangeas.jpg");
		return new MimeMessageHelperTemplate(mimeMessage)
		.from(this.getFrom())
		.to(this.getTo())
		.subject("this is text and attachment")
		.text("this is text and attachmen and inline ")
		.addAttachment("first.jpg", file)
		.addInline("file1", file);
	}
	
	public SimpleMailMessageTemplate getSimpleMailMessage() {
		return new SimpleMailMessageTemplate()
		.from(this.getFrom())
		.to(this.getTo())
		.subject("this is test")
		.text("this is a test value");
	}
}
