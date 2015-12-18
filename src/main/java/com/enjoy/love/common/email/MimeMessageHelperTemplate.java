package com.enjoy.love.common.email;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;

public class MimeMessageHelperTemplate  {
	private MimeMessage mimeMessage;
	private MimeMessageHelper helper;
	
	public MimeMessageHelperTemplate(MimeMessage mimeMessage)
			throws MessagingException {
		this.mimeMessage = mimeMessage;
		helper = new MimeMessageHelper(this.mimeMessage, true);
	}
	
	public MimeMessageHelperTemplate to(String to) throws MessagingException {
		helper.setTo(to);
		return this;
	}
	
	public MimeMessageHelperTemplate from(String from) throws MessagingException {
		helper.setFrom(from);
		return this;
	}
	
	public MimeMessageHelperTemplate subject(String subject) throws MessagingException {
		helper.setSubject(subject);
		return this;
	}
	
	public MimeMessageHelperTemplate text(String text) throws MessagingException {
		helper.setText(text);
		return this;
	}
	
	public MimeMessageHelperTemplate addAttachment(String attachmentFilename, File file) throws MessagingException {
		helper.addAttachment(attachmentFilename, file);
		return this;
	}
	
	public MimeMessageHelperTemplate addInline(String contentId, File file) throws MessagingException {
		helper.addInline(contentId, file);
		return this;
	}

	public MimeMessage getMimeMessage() {
		return mimeMessage;
	}

}
