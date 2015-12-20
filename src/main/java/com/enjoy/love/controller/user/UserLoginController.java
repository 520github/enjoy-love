package com.enjoy.love.controller.user;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enjoy.love.controller.BaseController;

@Controller
@RequestMapping("/logon")
public class UserLoginController extends BaseController {
	@RequestMapping("/logon")
	public String logon() {
		return "web/user/logon";
	}
	
	@RequestMapping("/login")
	public String login(String phone, String password) {
		this.print("phone {}, password {}", phone, password);
		UsernamePasswordToken token = new UsernamePasswordToken(phone, password);
		Subject user = SecurityUtils.getSubject();
		try {
			user.login(token);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return "web/user/logon";
		}
		
		return "web/user/index";
	}
}
