package com.enjoy.love.aop;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;

public class InterceptorChainSupport implements MethodInvocation {
	private MethodInvocation proxy;

	private List<Interceptor> chains;
	
	public InterceptorChainSupport(MethodInvocation proxy,
			List<Interceptor> chains) {
		this.proxy = proxy;
		this.chains = chains;
	}
	
	@Override
	public Object[] getArguments() {
		return proxy.getArguments();
	}

	@Override
	public Object proceed() throws Throwable {
		if (null != chains) {
			if (chains.size() > 0) {
//				if (logger.isDebugEnabled()) {
//					logger.debug(" [ " + Thread.currentThread().getId()
//							+ " ] Invoke Chanin [ " + chains.size()
//							+ " ] , name is : " + chains.get(0).getClass());
//				}
				return chains.remove(0).invoke(this);
			}
		}
		return proxy.proceed();
	}

	@Override
	public Object getThis() {
		return proxy.getThis();
	}

	@Override
	public AccessibleObject getStaticPart() {
		return proxy.getStaticPart();
	}

	@Override
	public Method getMethod() {
		return proxy.getMethod();
	}

}
