package com.enjoy.love.service;

public class CallbackResult {
	/**
	 * 可以继续执行，或者提交事务
	 */
	public final static int SUCCESS = 1;

	/**
	 * 不能继续执行，或者回滚事务
	 */
	public final static int FAILURE = -1;

	/**
	 * 表征事务状态，默认事务提交
	 */
	private int statusCode = SUCCESS;

	/**
	 * 代表业务语义的结果代码，主要用在发生回滚，进行异常throw的场景
	 */
	private int resultCode = 0;

	/**
	 * 发生的异常信息
	 */
	private Throwable throwable;

	/**
	 * 与该结果关联的业务主体
	 */
	private Object businessObject;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public Object getBusinessObject() {
		return businessObject;
	}

	public void setBusinessObject(Object businessObject) {
		this.businessObject = businessObject;
	}

	/**
	 * 禁止用户直接构造该对象，必须通过业务方法构造
	 */
	private CallbackResult(int statusCode, int resultCode, Throwable throwable,
			Object businessObject) {
		this.statusCode = statusCode;
		this.resultCode = resultCode;
		this.throwable = throwable;
		this.businessObject = businessObject;
	}

	// ------------------ 辅助方法 --------------------------------------

	/**
	 * 直接构造成功状况下的回调结果对象
	 */
	public static CallbackResult success() {
		return success(0, null);
	}

	/**
	 * 直接构造成功状况下的回调结果对象，同时设置业务代码
	 * 
	 * @param resultCode
	 * @return
	 */
	public static CallbackResult success(int resultCode) {
		return success(resultCode, null);
	}

	/**
	 * 直接构造成功状况下的回调结果对象，同时设置业务代码、业务对象
	 * 
	 * @param resultCode
	 * @return
	 */
	public static CallbackResult success(int resultCode, Object businessObject) {
		return new CallbackResult(SUCCESS, resultCode, null, businessObject);
	}

	/**
	 * 直接构造失败状况下的回调结果对象，同时设置业务代码
	 * 
	 * @param resultCode
	 * @return
	 */
	public static CallbackResult failure(int resultCode) {
		return failure(resultCode, null, null);
	}

	/**
	 * 直接构造失败状况下的回调结果对象，同时设置业务代码、异常
	 * 
	 * @param resultCode
	 * @return
	 */
	public static CallbackResult failure(int resultCode, Throwable throwable) {
		return failure(resultCode, throwable, null);
	}

	/**
	 * 直接构造失败状况下的回调结果对象，同时设置业务代码、异常、业务对象
	 * 
	 * @param resultCode
	 * @return
	 */
	public static CallbackResult failure(int resultCode, Throwable throwable,
			Object businessObject) {
		return new CallbackResult(FAILURE, resultCode, throwable,
				businessObject);
	}

	/**
	 * 检查服务处理是否成功
	 * 
	 * @return 成功返回true，否则返回false
	 */
	public boolean isSuccess() {
		return statusCode == SUCCESS;
	}

	/**
	 * 检查服务处理是否失败
	 * 
	 * @return 失败返回true，否则返回false
	 */
	public boolean isFailure() {
		return statusCode == FAILURE;
	}

	/**
	 * 检查业务对象是否为NULL，以便于外部方法判断是否进行其它处理
	 * 
	 * @return
	 */
	public boolean isNullBusinessObject() {
		return null == this.businessObject;
	}
}
