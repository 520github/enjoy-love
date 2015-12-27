package com.enjoy.love.common.diagnostic;

import org.aopalliance.intercept.MethodInvocation;

import com.enjoy.love.aop.BaseInterceptor;

public class PerformanceMonitorInterceptor extends BaseInterceptor {
	/** 以毫秒表示的阈值 */
	private int threshold = 250;
	
	@Override
	public Object bizInvoke(MethodInvocation invocation) throws Throwable {
		StringBuilder builder = new StringBuilder(64);
		builder.append(invocation.getMethod().getDeclaringClass().getName());
		builder.append(".");
		builder.append(invocation.getMethod().getName());
		String name = builder.toString();
		Profiler.start("Invoking method: " + name);
		
		try {
			return invocation.proceed();
		}finally {
			Profiler.release();
			if (!Profiler.isSuperStart()) {
				long elapseTime = Profiler.getDuration();
				if (elapseTime > threshold) {
					StringBuilder builderTmp = new StringBuilder();
					builderTmp.append(" method ").append(name);
					// 执行时间超过阈值时间
					builderTmp.append(" over PMX = ").append(threshold)
							.append("ms,");
					// 实际执行时间为
					builderTmp.append(" used P = ").append(elapseTime)
							.append("ms.\r\n");
					builderTmp.append(Profiler.dump());
					//logger.info(builderTmp.toString());
				}
				else {
					//if (logger.isDebugEnabled()) {
						StringBuilder builderTmp = new StringBuilder();
						builderTmp.append("method").append(name);
						// 实际执行时间为
						builderTmp.append(" used P = ").append(elapseTime)
								.append("ms.\r\n");
						//logger.debug(builderTmp.toString());
					//}
				}
			}
			Profiler.reset();
		}
	}
	
	/**
	 * @param threshold
	 *            The threshold to set.
	 */
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
}
