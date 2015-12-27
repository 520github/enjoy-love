package com.enjoy.love.aop.bsi;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

import com.enjoy.love.aop.BaseInterceptor;
import com.enjoy.love.common.annotation.DigestLogAnnotated;
import com.enjoy.love.common.util.AnnotatedUtils;

public class AnnotatedLogInterceptor extends BaseInterceptor {

	@Override
	public Object bizInvoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		String argumentString = null;

		String className = method.getDeclaringClass().getSimpleName();
		String methodName = method.getName();
		
		try {
			DigestLogAnnotated digestAnnotated = AnnotatedUtils.getAnnotated(DigestLogAnnotated.class, invocation);
			if (digestAnnotated != null) {
				
			}
			
			// 执行业务方法
			Object result = invocation.proceed();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
