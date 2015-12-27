package com.enjoy.love.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInvocation;

public class AnnotatedUtils {
	/**
	 * 从拦截的方法上面取回注解，不仅从接口方法上取，还从实现类方法上取
	 * 
	 * @param <T>
	 * @param annotatedClass
	 * @param invocation
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAnnotated(
			Class<? extends Annotation> annotatedClass,
			MethodInvocation invocation) {
		// 实现类的方法
		Method impMethod = null;

		Method method = invocation.getMethod();
		// 先取拦截的接口方法上面是否有注解
		if (!method.isAnnotationPresent(annotatedClass)) {
			// 接口方法上没有注解，则取具体的实现类的方法，检查该实现方法上是否有注解
			impMethod = implLogInterceptor(invocation, method);
			if (impMethod == null
					|| !impMethod.isAnnotationPresent(annotatedClass)) {
				// 实现类上仍然没有,返回null
				return null;
			}
			return (T) impMethod.getAnnotation(annotatedClass);
		} else {
			// 接口上有注解
			return (T) method.getAnnotation(annotatedClass);
		}
	}

	/**
	 * 这个方法是用来处理，有些注解不能加在接口上，必须要加在具体的实现类的方法上，而调用该方法，取真正实现类的方法，来进行处理
	 * 目前这个特殊的逻辑主要用在facade上
	 * 
	 * @param invocation
	 *            拦截的invocation
	 * @param method
	 *            拦截的接口方法签名
	 * @return
	 */
	private static Method implLogInterceptor(MethodInvocation invocation,
			Method method) {
		Method[] methods = invocation.getThis().getClass().getMethods();
		for (Method templateMethod : methods) {
			if (templateMethod.getName().equals(method.getName())) {
				return templateMethod;
			}
		}
		return null;
	}
}
