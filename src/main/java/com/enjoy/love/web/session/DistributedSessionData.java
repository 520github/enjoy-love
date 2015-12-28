package com.enjoy.love.web.session;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class DistributedSessionData implements Serializable {
	private static final long serialVersionUID = 4359110344092234145L;

	/**
	 * Session创建时间
	 */
	private long createdDmt = System.currentTimeMillis();

	/**
	 * Session上次修改时间
	 */
	private long lastModifiedDmt;

	/**
	 * Session数据
	 */
	private Map<String, Object> sessionData;

	public long getCreatedDmt() {
		return createdDmt;
	}

	public void setCreatedDmt(long createdDmt) {
		this.createdDmt = createdDmt;
	}

	public long getLastModifiedDmt() {
		return lastModifiedDmt;
	}

	public void setLastModifiedDmt(long lastModifiedDmt) {
		this.lastModifiedDmt = lastModifiedDmt;
	}

	public Map<String, Object> getSessionData() {
		return sessionData;
	}

	public void setSessionData(Map<String, Object> sessionData) {
		this.sessionData = sessionData;
	}

	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("createdDmt : ").append((new java.util.Date(createdDmt)).toString());
		buff.append("lastModifiedDmt : ").append((new java.util.Date(lastModifiedDmt)).toString());
		buff.append("sessionData : ");
		if (null != sessionData) {
			Set<String> keys = sessionData.keySet();
			Iterator<String> iterator = keys.iterator();
			String key = null;
			while (iterator.hasNext()) {
				key = iterator.next();
				buff.append(" [ ").append(key).append(" = ").append(sessionData.get(key)).append(" ]");
			}
		}
		return buff.toString();
	}
}
