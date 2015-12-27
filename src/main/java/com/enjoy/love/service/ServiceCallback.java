package com.enjoy.love.service;

public interface ServiceCallback {
	/**
	 * 服务执行前的检查，支持针对领域对象(domain)的annotation方式的配置
	 *
	 * @param serviceContext
	 *            服务环境
	 * @param domain
	 *            业务领域对象
	 * @return 反向调用的执行结果
	 */
	public CallbackResult executeCheck();

	/**
	 * 服务执行
	 * 
	 * @param serviceContext
	 *            服务环境
	 * @param domain
	 *            业务领域对象
	 * @return 反向调用的执行结果
	 */
	public CallbackResult executeAction();
}
