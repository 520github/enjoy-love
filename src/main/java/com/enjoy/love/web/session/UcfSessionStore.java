package com.enjoy.love.web.session;


public interface UcfSessionStore {
	/**
     * 获取Session
     */
    public DistributedSessionData loadSession(String jSessionId);

    /**
     * 更新Session
     * 
     * @param key
     * @param session
     * @param sessionTimeOut
     * @return
     */
    public boolean saveSession(String key, DistributedSessionData session, long sessionTimeOut);

    /**
     * 删除Session
     * 
     * @param j_sessionId
     * @return
     */
    public boolean removeSession(String jSessionId);
}
