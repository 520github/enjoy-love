package com.enjoy.love.web.session;

public interface UcfHttpRequestLifecycle {
	/**
     * 生命周期的开始
     */
    public void begin();

    /**
     * 生命周期的结束
     */
    public void commit();
}
