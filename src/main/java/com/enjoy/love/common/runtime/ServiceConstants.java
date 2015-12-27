package com.enjoy.love.common.runtime;

public final class ServiceConstants {
	/**
	 * 服务调用的时候，没有返回结果值
	 */
	public final static int SERVICE_NO_RESULT = 0xFF0001;

	/**
	 * 发生系统级别的异常
	 */
	public final static int SERVICE_SYSTEM_FALIURE = 0xFF0002;

	/**
	 * 服务的方法参数少于1个，为规则所不允许
	 */
	public final static int SERVICE_METHOD_ARGS_LESS_THAN_1 = 0xFF0003;
}
