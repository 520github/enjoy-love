package com.enjoy.love.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpContextFactory {
	/**
     * 创建HttpServletRequest，而且HttpServletRequest的实现者，必须同时实现HttpRequestLifecycle
     * 
     * @param proxy
     * @return
     */
    public HttpServletRequest createHttpServletRequest(HttpServletRequest proxy);

    /**
     * 创建HttpServletResponse
     * 
     * @param proxy
     * @return
     */
    public HttpServletResponse createHttpServletResponse(HttpServletResponse proxy);
}
