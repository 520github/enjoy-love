package com.enjoy.love.controller.user;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enjoy.love.controller.BaseController;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	@RequestMapping("/index")
	public String userIndex() {
		Subject currentUser = SecurityUtils.getSubject();
		System.out.println("getPrincipal--->" + currentUser.getPrincipal());
		System.out.println("getPrincipals--->" + currentUser.getPrincipals());
		return "web/user/index";
	}
	
	
}
