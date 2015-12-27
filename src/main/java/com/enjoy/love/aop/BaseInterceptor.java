package com.enjoy.love.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.aopalliance.intercept.MethodInvocation;

public abstract class BaseInterceptor implements Interceptor {
	private static Map<Method, Boolean> methods = null;
	
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// 过滤原生方法
        if (null == methods) {
            methods = new HashMap<Method, Boolean>();
            for (Method m : Object.class.getMethods()) {
                methods.put(m, true);
            }
        }
        if (null!=methods.get(invocation.getMethod()))
            return invocation.proceed();
        return bizInvoke(invocation);
	}
	
	public abstract Object bizInvoke(MethodInvocation invocation) throws Throwable;

}
