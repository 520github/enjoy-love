package com.enjoy.love.common.response;

//import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
//import org.apache.commons.lang3.builder.ToStringStyle;

public class ResponseObject {
	//成功代码从100开始
	private static final int SUCCESS_CODE = 100;

	//错误代码从300开始
	private static final int FAIL_CODE = 300;
	private static final int UNKNOWN_ERROR_CODE = 301;
	private static final int ILLEGAL_ARGUMENTS_CODE = 302;
	private static final int ACCESS_DENIED_CODE = 303;
	/** 找不到数据 **/
	private static final int NOT_FOUND_CODE = 404;
	/** 参数非法，不满足预期 **/
	private static final int ILLEGAL_PARAMETER_ERROR_CODE = 406;
	/** 请求数据已存在，发生冲突 **/
	private static final int CONFLICT_ERROR_CODE = 409;
	/** 服务器内部错误 **/
	private static final int SERVER_INNER_ERROR_CODE =500;


	public static final ResponseObject SUCCESS = new ResponseObject(SUCCESS_CODE, "success");
	public static final ResponseObject ILLEGAL_ARGUMENTS = new ResponseObject(ILLEGAL_ARGUMENTS_CODE, "illegal arguments");
	public static final ResponseObject UNKNOWN_ERROR = new ResponseObject(UNKNOWN_ERROR_CODE, "unknown error");
	public static final ResponseObject ACCESS_DENIED = new ResponseObject(ACCESS_DENIED_CODE, "access denied");
	public static final ResponseObject SERVER_INNER_ERROR = new ResponseObject(SERVER_INNER_ERROR_CODE, "服务器内部错误");
	public static final ResponseObject ILLEGAL_PARAMETER_ERROR = new ResponseObject(ILLEGAL_PARAMETER_ERROR_CODE, "请求参数非法，不满足预期 ");
	public static final ResponseObject CONFLICT_ERROR = new ResponseObject(CONFLICT_ERROR_CODE, "数据已存在");
	public static final ResponseObject NOT_FOUND_ERROR = new ResponseObject(NOT_FOUND_CODE, "没有找到对应的数据");

	private int code;
	private String message;
	private Object data;

	public static int getSuccessCode() {
		return SUCCESS_CODE;
	}

	public static int getUnknownErrorCode() {
		return UNKNOWN_ERROR_CODE;
	}

	public static int getIllegalArgumentsCode() {
		return ILLEGAL_ARGUMENTS_CODE;
	}

	public static ResponseObject getSUCCESS() {
		return SUCCESS;
	}

	public static ResponseObject getIllegalArguments() {
		return ILLEGAL_ARGUMENTS;
	}

	public static ResponseObject getUnknownError() {
		return UNKNOWN_ERROR;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ResponseObject(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}


	public ResponseObject(int code, String message) {
		this(code, message, null);
	}

	public static ResponseObject success(String message, Object data) {
		return new ResponseObject(SUCCESS_CODE, message, data);
	}

	public static ResponseObject success(String message) {
		return new ResponseObject(SUCCESS_CODE, message);
	}


	public static ResponseObject fail(String message, Object data) {
		return new ResponseObject(FAIL_CODE, message, data);
	}

	public static ResponseObject fail(String message) {
		return new ResponseObject(FAIL_CODE, message);
	}


//		@Override
//		public String toString() {
//			return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
//		}
}
