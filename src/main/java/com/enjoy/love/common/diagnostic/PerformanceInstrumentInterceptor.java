package com.enjoy.love.common.diagnostic;

import org.aopalliance.intercept.MethodInvocation;

import com.enjoy.love.aop.BaseInterceptor;

public class PerformanceInstrumentInterceptor extends BaseInterceptor {
	/**
	 * 缺省的构造方法.
	 */
	public PerformanceInstrumentInterceptor() {
		super();
	}
	
	@Override
	public Object bizInvoke(MethodInvocation invocation) throws Throwable {
		StringBuilder builder = new StringBuilder(64);
		builder.append("Invoking method: ");
		builder.append(invocation.getMethod().getDeclaringClass().getName());
		builder.append(".");
		builder.append(invocation.getMethod().getName());
		Profiler.enter(builder.toString());
		try {
			return invocation.proceed();
		} finally {
			Profiler.release();
		}
	}

}
