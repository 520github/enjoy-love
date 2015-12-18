package com.enjoy.love.common.email;

import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Component
public class EmailVelocityTemplate {
	@Autowired
	private VelocityEngine engine;
	
	public String getEmailRegisterTemplateValue(Map<String, Object> model) {
		return this.getVelocityTemplateValue("email/register.vm", model);
	}
	
	private String getVelocityTemplateValue(String template, Map<String, Object> model) {
		return VelocityEngineUtils.mergeTemplateIntoString(this.engine,
				template, "UTF-8", model);
	}
}
