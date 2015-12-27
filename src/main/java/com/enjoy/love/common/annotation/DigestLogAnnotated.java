package com.enjoy.love.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.enjoy.love.common.enums.LoggerLevel;
import com.enjoy.love.common.enums.LoggerPrintType;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DigestLogAnnotated {
	/**
	 * 日志文件名
	 * 
	 * @return
	 */
	public String logFileName();

	/**
	 * 日志记录级别
	 * 
	 * @return
	 */
	public LoggerLevel loggerLevel();

	/**
	 * 摘要标识码
	 * 
	 * @return
	 */
	public String digestIdentificationCode();

	/**
	 * 日志输出类型
	 * 
	 * @return
	 */
	public LoggerPrintType printType() default LoggerPrintType.ALL;
}
