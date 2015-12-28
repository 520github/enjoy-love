package com.enjoy.love.web.session.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.enjoy.love.web.HttpContextFactory;
import com.enjoy.love.web.WebConstant;
import com.enjoy.love.web.session.UcfHttpRequestWrapper;
import com.enjoy.love.web.session.UcfHttpResponseWrapper;
import com.enjoy.love.web.session.UcfSessionStore;

public class UcfHttpContextFactoryImpl implements HttpContextFactory {

	@Autowired
    private UcfSessionStore ucfSessionStore = null;

	//session默认超时时间为30分钟
    private Integer sessionTimeoutInMin = WebConstant.SESSION_TIME_OUT;

    private String errorPage;

    @Override
    public HttpServletRequest createHttpServletRequest(HttpServletRequest proxy) {
        // 通过Http环境工厂来传入所有需要的参数
        return new UcfHttpRequestWrapper(proxy, ucfSessionStore, sessionTimeoutInMin * 60 * 1000);
    }

    @Override
    public HttpServletResponse createHttpServletResponse(HttpServletResponse proxy) {
        UcfHttpResponseWrapper wrapper = new UcfHttpResponseWrapper(proxy);
        wrapper.setErrorPage(errorPage);
        return wrapper;
    }

    // --------------- IOC 注入 ---------------------------------------

    public void setUcfSessionStore(UcfSessionStore ucfSessionStore) {
        this.ucfSessionStore = ucfSessionStore;
    }

    public void setSessionTimeoutInMin(Integer sessionTimeoutInMin) {
        this.sessionTimeoutInMin = sessionTimeoutInMin;
    }

    public Integer getSessionTimeoutInMin() {
        return sessionTimeoutInMin;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

}
