package com.enjoy.love.config;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanNameAutoProxyCreatorConfig {
	
	@Bean
	public BeanNameAutoProxyCreator serviceProxyCreator() {
		BeanNameAutoProxyCreator beanNameAutoProxyCreator=new BeanNameAutoProxyCreator();  
        //设置要创建代理的那些Bean的名字  
        beanNameAutoProxyCreator.setBeanNames("*Service", "*ServiceImpl");  
        //设置拦截链名字(这些拦截器是有先后顺序的)  
        beanNameAutoProxyCreator.setInterceptorNames("interceptorChain");  
        return beanNameAutoProxyCreator;  
	}
}
