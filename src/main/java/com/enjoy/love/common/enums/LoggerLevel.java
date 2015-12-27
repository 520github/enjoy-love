package com.enjoy.love.common.enums;

import com.enjoy.love.common.util.JavaEnumUtils;

public enum LoggerLevel {
	DEBUG("DEBUG", "DEBUG级别日志"), INFO("INFO", "INFO级别日志"), WAR("WAR", "WAR级别日志"), ERROR(
			"ERROR", "ERROR级别日志");

	/**
	 * 构造函数
	 * 
	 * @param code
	 *            编码
	 * @param description
	 *            说明
	 */
	private LoggerLevel(String code, String description) {
		this.code = code;
		this.description = description;
		JavaEnumUtils.put(this.getClass().getName() + code, this);
	}

	/**
	 * <pre>
	 * 一个便利的方法，方便使用者通过code获得枚举对象，
	 * 对于非法状态，以个人处理&lt;/b&gt;
	 * </pre>
	 * 
	 * @param code
	 * @return
	 */
	public static LoggerLevel valueByCode(String code) {
		Object obj = JavaEnumUtils.get(LoggerLevel.class.getName() + code);
		if (null != obj) {
			return (LoggerLevel) obj;
		}
		return INFO;
	}

	/** 编码 */
	private String code;

	/** 描述的KEY */
	private String description;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
