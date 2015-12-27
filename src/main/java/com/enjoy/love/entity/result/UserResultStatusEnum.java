package com.enjoy.love.entity.result;

import com.enjoy.love.common.util.JavaEnumUtils;

public enum UserResultStatusEnum {
	SUCCESS(51000, "成功"),

	FAILURE(51099, "失败"),

	PHONE_NONEXIST(51010, "手机号码不存在"),
	
	PHONE_EXISTED(51011,"手机号码已注册"),

    AUTHCODE_ERROR(51012,"验证码错误"),

    /**
     * 参数格式校验错误
     */
    PARAMETER_ERROR(51013, "参数不完整或格式校验错误"),

    REGISTER_ERROR(51015,"第三方账户注册失败"),

    UID_NONEXIST(51016, "用户ID不存在"),

    INVITATION_EXISTED(51017,"该号码已被邀请"),

	/**
	 * 参数不完整
	 */
	PARAMETER_NULL(51011, "参数不完整"),

	/**
	 * 值太长
	 */
	VALUE_TOOLONG(51013, "值太长"),

	/**
	 * 记录值已经存在
	 */
	EXIST_RECORD_VALUE(51014, "记录值已经存在"),

	/**
	 * 记录值不存在
	 */
	NOT_EXIST_RECORD_VALUE(51015, "记录值不存在"),

	/**
	 * 登陆账号格式不正确
	 */
	LOGON_FORMAT_ERROR(51019, "登陆账号格式不正确"),

	/**
	 * 用户不存在
	 */
	USER_NOT_EXIST(51020, "用户不存在"),

	/**
	 * 密码不正确
	 */
	PASSWORD_FAULT(51021, "密码不正确"),
	
	
	UNKOWN_SYS_ERROR(51098, "未知系统错误");

	private int code;

	private String description;

	private UserResultStatusEnum(int code, String description) {
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
	public static UserResultStatusEnum valueOf(int code) {
		Object obj = JavaEnumUtils.get(UserResultStatusEnum.class.getName()
				+ code);
		if (null != obj) {
			return (UserResultStatusEnum) obj;
		}
		return FAILURE;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
