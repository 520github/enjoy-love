package com.enjoy.love.aop;

import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;

public class InterceptorChain extends BaseInterceptor {
	private List<Interceptor> chains = new ArrayList<Interceptor>();
	
	@Override
	public Object bizInvoke(MethodInvocation invocation) throws Throwable {
		InterceptorChainSupport support = new InterceptorChainSupport(
				invocation, new ArrayList<Interceptor>(chains));
		return support.proceed();
	}
	
	public void setChains(List<Interceptor> chains) {
		this.chains = chains;
	}

}
