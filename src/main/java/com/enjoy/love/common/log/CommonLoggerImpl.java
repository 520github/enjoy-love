package com.enjoy.love.common.log;

import org.slf4j.Logger;

public class CommonLoggerImpl implements CommonLogger {

	protected Logger logger = null;

	public void setLogger(Logger loger) {
		logger = loger;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#getName()
	 */
	@Override
	public String getName() {
		return logger.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#isTraceEnabled()
	 */
	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#trace(java.lang.String)
	 */
	@Override
	public void trace(String msg) {
		logger.trace(msg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#trace(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void trace(String format, Object arg) {
		logger.trace(format, arg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#trace(java.lang.String,
	 * java.lang.Object[])
	 */
	@Override
	public void trace(String format, Object[] argArray) {
		logger.trace(format, argArray);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#trace(java.lang.String,
	 * java.lang.Throwable)
	 */
	@Override
	public void trace(String msg, Throwable t) {
		logger.trace(msg, t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#isDebugEnabled()
	 */
	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#debug(java.lang.String)
	 */
	@Override
	public void debug(String msg) {
		logger.debug(msg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#debug(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void debug(String format, Object arg) {
		logger.debug(format, arg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#debug(java.lang.String,
	 * java.lang.Object[])
	 */
	@Override
	public void debug(String format, Object[] argArray) {
		logger.debug(format, argArray);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#debug(java.lang.String,
	 * java.lang.Throwable)
	 */
	@Override
	public void debug(String msg, Throwable t) {
		logger.debug(msg, t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#isInfoEnabled()
	 */
	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#info(java.lang.String)
	 */
	@Override
	public void info(String msg) {
		logger.info(msg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#info(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void info(String format, Object arg) {
		logger.info(format, arg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#info(java.lang.String,
	 * java.lang.Object[])
	 */
	@Override
	public void info(String format, Object[] argArray) {
		logger.info(format, argArray);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#info(java.lang.String,
	 * java.lang.Throwable)
	 */
	@Override
	public void info(String msg, Throwable t) {
		logger.info(msg, t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#isWarnEnabled()
	 */
	@Override
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#warn(java.lang.String)
	 */
	@Override
	public void warn(String msg) {
		logger.warn(msg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#warn(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void warn(String format, Object arg) {
		logger.warn(format, arg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#warn(java.lang.String,
	 * java.lang.Object[])
	 */
	@Override
	public void warn(String format, Object[] argArray) {
		logger.warn(format, argArray);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#warn(java.lang.String,
	 * java.lang.Throwable)
	 */
	@Override
	public void warn(String msg, Throwable t) {
		logger.warn(msg, t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#isErrorEnabled()
	 */
	@Override
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#error(java.lang.String)
	 */
	@Override
	public void error(String msg) {
		logger.error(msg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.core.log.CommonLogger#error(java.lang.String,
	 * java.lang.Throwable)
	 */
	@Override
	public void error(String msg, Throwable t) {
		logger.error(msg, t);

	}

}
