package com.enjoy.love.web.session.impl;

import java.util.concurrent.TimeUnit;

import com.enjoy.love.common.redis.support.ICache;
import com.enjoy.love.web.session.DistributedSessionData;
import com.enjoy.love.web.session.UcfSessionStore;

public class RedisSessionStoreImpl implements UcfSessionStore {

	private static String prefixCode = "SESSION#";

	private ICache<String> redisKVManager;

	/**
	 * 初始化Session redis
	 */
	public DistributedSessionData loadSession(String jSessionId) {
		return this.redisKVManager.get(prefixCode + jSessionId,
				DistributedSessionData.class);
	}

	/**
	 * 传入的是DistributedSessionData对象
	 */
	public boolean saveSession(String jSessionId,
			DistributedSessionData session, long sessionTimeout) {
		this.redisKVManager.set(prefixCode + jSessionId, session,
				sessionTimeout, TimeUnit.MILLISECONDS);
		return true;
	}

	/**
	 * 从缓存中根据j_sessionId删除Session
	 */
	public boolean removeSession(String jSessionId) {
		this.redisKVManager.delete(prefixCode + jSessionId);
		return true;
	}

	public ICache<String> getRedisKVManager() {
		return redisKVManager;
	}

	public void setRedisKVManager(ICache<String> redisKVManager) {
		this.redisKVManager = redisKVManager;
	}

}
