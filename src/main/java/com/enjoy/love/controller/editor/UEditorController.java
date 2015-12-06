package com.enjoy.love.controller.editor;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.enjoy.love.controller.BaseController;

@Controller
@RequestMapping("/ueditor")
public class UEditorController extends BaseController {
	
	private static Map<String,Object> cache = new HashMap<String, Object>();
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addByUEditor(String projectDetail) {
		this.print("projectDetail {}", projectDetail);
		cache.put("projectDetail", projectDetail);
		return "editorByUEditor.html";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String updateByUEditor(String projectDetail) {
		this.print("projectDetail {}", projectDetail);
		cache.put("projectDetail", projectDetail);
		return "";
	}
	
	@RequestMapping(value="/get")
	@ResponseBody
	public Object getEUditorCache() {
		return cache;
	}
	
	
}
