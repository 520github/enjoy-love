package com.enjoy.love.common.email;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
	
	public static MimeMessageHelperTemplate getMimeMessageHelperTemplate(MimeMessage mimeMessage) throws MessagingException {
		File file = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\Hydrangeas.jpg");
		return new MimeMessageHelperTemplate(mimeMessage)
		.from("test_customerservices@qianshengcai.com")
		.to("xuehui.miao@qianshengcai.com")
		.subject("this is text and attachment")
		.text("this is text and attachmen and inline ")
		.addAttachment("first.jpg", file)
		.addInline("file1", file);
	}
	
	public static SimpleMailMessageTemplate getSimpleMailMessage() {
		return new SimpleMailMessageTemplate()
		.from("test_customerservices@qianshengcai.com")
		.to("xuehui.miao@qianshengcai.com")
		.subject("this is test")
		.text("this is a test value");
	}
}
