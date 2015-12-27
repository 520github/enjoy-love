package com.enjoy.love.service;

public interface ServiceTemplate {
	/**
	 * 带事务的模板 
	 * 说明：事务模板是在事务外发送事件，如果要在事务内发生事件，必须由业务自己来发送
	 * 
	 * @param action
	 *            被回调的类
	 * @param domain
	 *            将要作为事件被发送的对象
	 * @void
	 */
	CallbackResult execute(ServiceCallback action, Object domain);

	/**
	 * 没有事务的模板 
	 * 
	 * @param action
	 *            被回调的类
	 * @param domain
	 *            将要作为事件被发送的对象
	 * @void
	 */
	CallbackResult executeWithoutTransaction(ServiceCallback action,
			Object domain);
}
