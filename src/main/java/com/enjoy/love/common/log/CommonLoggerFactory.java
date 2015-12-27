package com.enjoy.love.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonLoggerFactory {
	/**
	 * Return a logger named according to the name parameter using the
	 * statically bound {@link ILoggerFactory} instance.
	 * 
	 * @param name
	 *            The name of the logger.
	 * @return logger
	 */
	public static CommonLogger getLogger(String name) {
		Logger logger = LoggerFactory.getLogger(name);
		CommonLoggerImpl loggerImpl = new CommonLoggerImpl();
		loggerImpl.setLogger(logger);
		return loggerImpl;
	}

	/**
	 * 根据所在类获取logger
	 * 
	 * @param clazz
	 *            the returned logger will be named after clazz
	 * @return logger
	 */
	public static CommonLogger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}
}
