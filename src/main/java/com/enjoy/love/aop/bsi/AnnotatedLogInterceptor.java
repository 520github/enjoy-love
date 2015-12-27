package com.enjoy.love.aop.bsi;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.enjoy.love.aop.BaseInterceptor;
import com.enjoy.love.common.annotation.DigestLogAnnotated;
import com.enjoy.love.common.enums.LoggerLevel;
import com.enjoy.love.common.enums.LoggerPrintType;
import com.enjoy.love.common.log.CommonLogger;
import com.enjoy.love.common.log.CommonLoggerFactory;
import com.enjoy.love.common.log.DefaultDigestLogInfo;
import com.enjoy.love.common.log.LogInfo;
import com.enjoy.love.common.util.AnnotatedUtils;

public class AnnotatedLogInterceptor extends BaseInterceptor {

	@Override
	public Object bizInvoke(MethodInvocation invocation) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Method method = invocation.getMethod();
		String argumentString = null;

		String className = method.getDeclaringClass().getSimpleName();
		String methodName = method.getName();
		
		try {
			DigestLogAnnotated digestAnnotated = AnnotatedUtils.getAnnotated(DigestLogAnnotated.class, invocation);
			if (digestAnnotated != null) {
				if (LoggerPrintType.IGNORE_INPUT != digestAnnotated.printType()) {
					argumentString = convert2argumentList(invocation.getArguments());
				}
			}
			
			// 执行业务方法
			Object result = invocation.proceed();
			if (digestAnnotated != null) {
				if (LoggerPrintType.IGNORE_OUTPUT != digestAnnotated.printType()) {
					digestInvokeLog(className, methodName,
							digestAnnotated.logFileName(),
							digestAnnotated.loggerLevel(), stopWatch, result,
							argumentString, null, true, "",
							digestAnnotated.digestIdentificationCode());
				}
				else {
					digestInvokeLog(className, methodName,
							digestAnnotated.logFileName(),
							digestAnnotated.loggerLevel(), stopWatch, null,
							argumentString, null, true, "",
							digestAnnotated.digestIdentificationCode());
				}
			}
			return result;
		} catch (Exception e) {
			boolean needThrow = true;
			DigestLogAnnotated digestAnnotated = AnnotatedUtils.getAnnotated(
					DigestLogAnnotated.class, invocation);
			if (digestAnnotated != null) {
				if (LoggerPrintType.IGNORE_INPUT != digestAnnotated.printType()) {
					argumentString = (StringUtils.isBlank(argumentString) ? convert2argumentList(invocation
							.getArguments()) : argumentString);
				}
				if (LoggerPrintType.IGNORE_EXCEPTION != digestAnnotated
						.printType()) {
					digestInvokeLog(className, methodName,
							digestAnnotated.logFileName(),
							digestAnnotated.loggerLevel(), stopWatch, null,
							argumentString, e, false, "",
							digestAnnotated.digestIdentificationCode());
				} else {
					digestInvokeLog(className, methodName,
							digestAnnotated.logFileName(),
							digestAnnotated.loggerLevel(), stopWatch, null,
							argumentString, null, true, "",
							digestAnnotated.digestIdentificationCode());
				}
			}
			if (needThrow) {
				throw e;
			} else {
				return null;
			}
		}
	}
	
	/**
	 * 转换请求参数数组为请求参数列表
	 * 
	 * @param arguments
	 * @return
	 */
	private String convert2argumentList(Object[] arguments) {
		String returnStr = "";
		if (null != arguments) {
			returnStr = JSON.toJSONString(arguments,
					SerializerFeature.WriteMapNullValue,
					SerializerFeature.WriteNullListAsEmpty);
		}
		return returnStr;
	}
	
	/**
	 * 执行摘要日志记录
	 * 
	 * @param method
	 * @param stopWatch
	 * @param result
	 * @param arguments
	 */
	protected void digestInvokeLog(String className, String methodName,
			String loggerName, LoggerLevel logLevel, StopWatch stopWatch,
			Object result, String arguments, Throwable e,
			boolean isInvokeSuccess, String logId,
			String digestIdentificationCode) {
		stopWatch.split();

		DefaultDigestLogInfo digestLogInfo = new DefaultDigestLogInfo();
		digestLogInfo.setLogId(logId);
		digestLogInfo.setInterceptorClass(className);
		digestLogInfo.setInterceptorMethod(methodName);
		digestLogInfo.setStopWatch(stopWatch);
		digestLogInfo.setLogFileName(loggerName);
		digestLogInfo.setLoggerLevel(logLevel);
		digestLogInfo.setDigestIdentificationCode(digestIdentificationCode);
		digestLogInfo.setRequestParams(arguments);
		digestLogInfo.setInvokeResult(result);
		digestLogInfo.setException(e);
		digestLogInfo.setInvokeSuccess(isInvokeSuccess);
		printLog(digestLogInfo);

	}
	
	
	/**
	 * 输出日志
	 * 
	 * @param logInfo
	 */
	protected void printLog(LogInfo logInfo) {
		CommonLogger logger = CommonLoggerFactory.getLogger(logInfo
				.getLogFileName());
		switch (logInfo.getLoggerLevel()) {
		case DEBUG: {
			logger.debug(logInfo.toLogString());
			break;
		}
		case INFO: {
			logger.info(logInfo.toLogString());
			break;
		}
		case WAR: {
			logger.warn(logInfo.toLogString());
			break;
		}
		case ERROR: {
			logger.error(logInfo.toLogString());
			break;
		}
		default:
			break;
		}
	}

}
