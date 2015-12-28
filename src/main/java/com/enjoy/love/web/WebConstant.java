package com.enjoy.love.web;

import org.springframework.beans.factory.annotation.Value;

public class WebConstant {
	/**
     * session cookie key
     */
    public final static String JRGC_SESSIONID = "redis_jrgc_session_id";

    /**
     * 默认Session的超时时间，用在Session类中
     */
    public static final Integer SESSION_TIME_OUT = 30;

    /**
     * 域
     */
    @Value("${session.domain}")
    public static final String JRGC_DOMAIN = ".9888.cn";

    /**
     * 定制的HTTTP环境工厂，用在HttpSessionFilter类中
     */
    public static final String HTTP_CONTEXT_FACTORY = "httpContextFactory";
}
