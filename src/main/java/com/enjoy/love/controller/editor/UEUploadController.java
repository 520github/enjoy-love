package com.enjoy.love.controller.editor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.ueditor.ActionEnter;
import com.enjoy.love.controller.BaseController;

@Controller
@RequestMapping("/js/lib/ueditor")
public class UEUploadController extends BaseController {
	
	@RequestMapping(value="/jsp/upload", method={RequestMethod.GET,RequestMethod.POST}, produces="text/html")
	@ResponseBody
	public String upload(HttpServletRequest request) {
		String rootPath = request.getRealPath("/");
		this.print("rootPath {}", rootPath);
		//rootPath = "http://localhost:8080";
		String result =  new ActionEnter( request, rootPath ).exec();
		this.print("result {}", result);
		return result;
	}
}
