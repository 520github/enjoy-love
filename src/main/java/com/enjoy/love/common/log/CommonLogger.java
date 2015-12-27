package com.enjoy.love.common.log;

public interface CommonLogger {
	/**
	 * 获取logger实例名
	 */
	public String getName();

	/**
	 * 判断当前logger的日志级别，如果是trace返回true，否则返回false
	 * 
	 * @since 1.4
	 */
	public boolean isTraceEnabled();

	/**
	 * 以trace级别打印日志信息。
	 * 
	 * @param msg
	 *            the message string to be logged
	 * @since 1.4
	 */
	public void trace(String msg);

	/**
	 * 
	 * 以trace级别打印日志信息格式: trace("trace.msg-{}",new String[]{"test"})
	 * 日志显示结果：trace.msg-test
	 * 
	 * @param format
	 *            the format string
	 * @param arg
	 *            the argument
	 * 
	 * @since 1.4
	 */
	public void trace(String format, Object arg);

	/**
	 * 以trace级别打印日志信息格式: trace("trace.msg-{}",new String[]{"test"})
	 * 日志显示结果：trace.msg-test
	 * 
	 * @param format
	 *            the format string
	 * @param argArray
	 *            an array of arguments
	 * 
	 * @since 1.4
	 */
	public void trace(String format, Object[] argArray);

	/**
	 * 以trace级别打印日志信息，同时打印异常信息
	 * 
	 * @param msg
	 *            the message accompanying the exception
	 * @param t
	 *            the exception (throwable) to log
	 * 
	 * @since 1.4
	 */
	public void trace(String msg, Throwable t);

	/**
	 * 判断当前logger的日志级别，如果是debug返回true，否则返回false
	 * 
	 */
	public boolean isDebugEnabled();

	/**
	 * 以debug级别打印日志信息。
	 * 
	 * @param msg
	 *            the message string to be logged
	 */
	public void debug(String msg);

	/**
	 * 以debug级别打印日志信息格式: debug("debug.msg-{}",new String[]{"test"})
	 * 日志显示结果：debug.msg-test
	 * 
	 * @param format
	 *            the format string
	 * @param arg
	 *            the argument
	 */
	public void debug(String format, Object arg);

	/**
	 * 以debug级别打印日志信息格式: debug("debug.msg-{}",new String[]{"test"})
	 * 日志显示结果：debug.msg-test
	 * 
	 * @param format
	 *            the format string
	 * @param argArray
	 *            an array of arguments
	 */
	public void debug(String format, Object[] argArray);

	/**
	 * 以debug级别打印日志信息，同时打印异常信息
	 * 
	 * @param msg
	 *            the message accompanying the exception
	 * @param t
	 *            the exception (throwable) to log
	 */
	public void debug(String msg, Throwable t);

	/**
	 * 判断当前logger的日志级别，如果是info返回true，否则返回false
	 */
	public boolean isInfoEnabled();

	/**
	 * 以trace级别打印日志信息。.
	 * 
	 * @param msg
	 *            the message string to be logged
	 */
	public void info(String msg);

	/**
	 * 以info级别打印日志信息格式: info("info.msg-{}",new String[]{"test"})
	 * 日志显示结果：info.msg-test
	 * 
	 * @param format
	 *            the format string
	 * @param arg
	 *            the argument
	 */
	public void info(String format, Object arg);

	/**
	 * 以info级别打印日志信息格式: info("info.msg-{}",new String[]{"test"})
	 * 日志显示结果：info.msg-test
	 * 
	 * @param format
	 *            the format string
	 * @param argArray
	 *            an array of arguments
	 */
	public void info(String format, Object[] argArray);

	/**
	 * 以info级别打印日志信息,同时打印异常信息
	 * 
	 * @param msg
	 *            the message accompanying the exception
	 * @param t
	 *            the exception (throwable) to log
	 */
	public void info(String msg, Throwable t);

	/**
	 * 判断当前logger的日志级别，如果是warn返回true，否则返回false
	 */
	public boolean isWarnEnabled();

	/**
	 * 以warn级别打印日志信息
	 * 
	 * @param msg
	 *            the message string to be logged
	 */
	public void warn(String msg);

	/**
	 * 以warn级别打印日志信息格式: warn("warn.msg-{}",new String[]{"test"})
	 * 日志显示结果：warn.msg-test
	 * 
	 * @param format
	 *            the format string
	 * @param arg
	 *            the argument
	 */
	public void warn(String format, Object arg);

	/**
	 * 以warn级别打印日志信息格式: warn("warn.msg-{}",new String[]{"test"})
	 * 日志显示结果：warn.msg-test
	 * 
	 * @param format
	 *            the format string
	 * @param argArray
	 *            an array of arguments
	 */
	public void warn(String format, Object[] argArray);

	/**
	 * 以warn级别打印日志信息，同时打印异常信息
	 * 
	 * @param msg
	 *            the message accompanying the exception
	 * @param t
	 *            the exception (throwable) to log
	 */
	public void warn(String msg, Throwable t);

	/**
	 * 判断当前logger的日志级别，如果是error返回true，否则返回false
	 */
	public boolean isErrorEnabled();

	/**
	 * 打印异常信息
	 * 
	 * @param msg
	 *            the message string to be logged
	 */
	public void error(String msg);

	/**
	 * 打印异常信息
	 * 
	 * @param msg
	 *            the message accompanying the exception
	 * @param t
	 *            the exception (throwable) to log
	 */
	public void error(String msg, Throwable t);
}
