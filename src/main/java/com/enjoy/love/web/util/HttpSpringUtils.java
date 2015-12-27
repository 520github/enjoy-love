package com.enjoy.love.web.util;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.enjoy.love.common.log.CommonLogger;
import com.enjoy.love.common.log.CommonLoggerFactory;

public class HttpSpringUtils {
	private final static CommonLogger logger = CommonLoggerFactory.getLogger(HttpSpringUtils.class);

    private static ApplicationContext applicationContext = null;

    /**
     * 获取通过web.xml装载的Spring环境
     * 
     * @param servletContext
     * @return
     */
    public static ApplicationContext getCurrentApplicationContext(ServletContext servletContext) {
        if (null == applicationContext) {
            applicationContext = (ApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        }
        return applicationContext;
    }

    /**
     * getBean， And init applicationContext
     * 
     * @param beanName
     * @param servletContext
     * @return
     */
    public static Object getBean(String beanName, ServletContext servletContext) {
        ApplicationContext tempApplicationContext = getCurrentApplicationContext(servletContext);
        if (null == tempApplicationContext) {
            return null;
        } else {
            try {
                return tempApplicationContext.getBean(beanName);
            } catch (Exception e) {
                logger.error("Get Bean ,It's Name : " + beanName, e);
                return null;
            }
        }
    }

    /**
     * Just get bean
     * 
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
        ApplicationContext tempApplicationContext = applicationContext;
        if (null == tempApplicationContext) {
            return null;
        } else {
            try {
                return tempApplicationContext.getBean(beanName);
            } catch (Exception e) {
                logger.error("Get Bean ,It's Name : " + beanName, e);
                return null;
            }
        }
    }
}
