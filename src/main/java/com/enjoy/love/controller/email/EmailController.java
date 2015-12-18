package com.enjoy.love.controller.email;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enjoy.love.common.email.EmailUtil;
import com.enjoy.love.common.email.EmailVelocityTemplate;
import com.enjoy.love.common.response.ResponseObject;
import com.enjoy.love.controller.BaseController;

@Controller
@RequestMapping("/email")
public class EmailController extends BaseController {
	@Autowired
	private EmailVelocityTemplate emailVelocityTemplate;
	@Autowired
	private JavaMailSender javaMailSender;
	
	@RequestMapping("/send/attachment")
	@ResponseBody
	public Object sendEmailAndAttachment() throws MailException, MessagingException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("time", new Date());
		model.put("message", "this is register email");
		javaMailSender.send(EmailUtil.getMimeMessageHelperTemplate(javaMailSender.createMimeMessage()).text(emailVelocityTemplate.getEmailRegisterTemplateValue(model)).getMimeMessage());
		//javaMailSender.send(EmailUtil.getSimpleMailMessage().text(emailVelocityTemplate.getEmailRegisterTemplateValue(model)));
		return ResponseObject.success("发送成功");
	}
	
	@RequestMapping("/send")
	@ResponseBody
	public Object sendEmail() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("time", new Date());
		model.put("message", "this is register email");
		javaMailSender.send(EmailUtil.getSimpleMailMessage().text(emailVelocityTemplate.getEmailRegisterTemplateValue(model)));
		return ResponseObject.success("发送成功");
	}
}
