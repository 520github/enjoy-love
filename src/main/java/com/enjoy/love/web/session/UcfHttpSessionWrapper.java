package com.enjoy.love.web.session;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.apache.commons.lang.StringUtils;

import com.enjoy.love.common.log.CommonLogger;
import com.enjoy.love.common.log.CommonLoggerFactory;
import com.enjoy.love.common.log.LogUtil;

@SuppressWarnings("deprecation")
public class UcfHttpSessionWrapper implements HttpSession {

	private final static CommonLogger logger = CommonLoggerFactory.getLogger(UcfHttpSessionWrapper.class);
	
	private DistributedSessionData sessionData = null;

    private String sessionId = null;

    private String cookieSessionId = null;
	
    /**
     * session是否被改变过标识
     */
    private boolean sessionHasChanged = false;
    
    long timeout = -1;

    boolean isValid = true;

    private HttpServletRequest requestProxy;

    private HttpSession sessionProxy;

    private UcfSessionStore ucfSessionStore;
    
    public UcfHttpSessionWrapper(String sessionId, String cookieSessionId,
            UcfSessionStore ucfSessionStore, HttpServletRequest requestProxy) {
    	 this.sessionId = sessionId;
         this.cookieSessionId = cookieSessionId;
         this.sessionData = new DistributedSessionData();
         this.sessionData.setSessionData(new HashMap<String, Object>());
         this.ucfSessionStore = ucfSessionStore;
         this.requestProxy = requestProxy;
         
         LogUtil.debug(logger,
					"sessionId={0},cookieSessionId={1}", sessionId, cookieSessionId);
    } 
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getAttribute(java.lang.String)
	 */
	@Override
	public Object getAttribute(String name) {
		return sessionData.getSessionData().get(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getAttributeNames()
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Enumeration getAttributeNames() {
		 return new KeyIterator<String>(sessionData.getSessionData().keySet().iterator());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getCreationTime()
	 */
	@Override
	public long getCreationTime() {
		return sessionData.getCreatedDmt();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getId()
	 */
	@Override
	public String getId() {
		return sessionId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getLastAccessedTime()
	 */
	@Override
	public long getLastAccessedTime() {
		return sessionData.getLastModifiedDmt();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
	 */
	@Override
	public int getMaxInactiveInterval() {
		if (-1L <= timeout)
            return -1;

        return (int) (timeout / 1000L);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getServletContext()
	 */
	@Override
	public ServletContext getServletContext() {
		 return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getSessionContext()
	 */
	@Override
	public HttpSessionContext getSessionContext() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
	 */
	@Override
	public Object getValue(String name) {
        return getAttribute(name);
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#getValueNames()
	 */
	@Override
	@SuppressWarnings("rawtypes")
    public String[] getValueNames() {
        try {
            if (!(isValid))
                throw new IllegalStateException();

            Enumeration e = getAttributeNames();
            String[] s = new String[sessionData.getSessionData().size()];
            int count = 0;
            while (e.hasMoreElements())
                s[(count++)] = ((String) e.nextElement());
            return s;
        } finally {

        }
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#invalidate()
	 */
	@Override
	public void invalidate() {
		isValid = false;
        sessionHasChanged = false;
        sessionData.getSessionData().clear();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#isNew()
	 */
	@Override
	public boolean isNew() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#putValue(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void putValue(String name, Object value) {
        sessionData.getSessionData().put(name, value);
        sessionHasChanged = true;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
	 */
	@Override
	public void removeAttribute(String name) {
        sessionData.getSessionData().remove(name);
        sessionHasChanged = true;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
	 */
	@Override
	public void removeValue(String name) {
		removeAttribute(name);
        sessionHasChanged = true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#setAttribute(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
    public void setAttribute(String name, Object value) {
        this.putValue(name, value);
        sessionHasChanged = true;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
	 */
	@Override
	public void setMaxInactiveInterval(int interval) {
		if (interval < 0)
            this.timeout = -1L;
        else
            this.timeout = (interval * 1000L);
	}
	
    public String getCookieSessionId() {
        return cookieSessionId;
    }
	
	// ---------------- 定制的方法 ----------------------------

    public void begin() {
        String session_id = this.getCookieSessionId(); // 拿到j_sessionid
        if (StringUtils.isNotBlank(session_id)) {
            // 从Redis读取Session
            try {
                DistributedSessionData tempSessionData = ucfSessionStore.loadSession(session_id);
                if ((null == tempSessionData) && StringUtils.isNotBlank(getId())) {
                    tempSessionData = ucfSessionStore.loadSession(getId());
                }
                if (tempSessionData != null) {
                    this.sessionData = tempSessionData;
                    long session_modified_time = sessionData.getLastModifiedDmt();
                    if ((System.currentTimeMillis() - session_modified_time) > timeout) {
                        LogUtil.debug(logger, "session has expired : " + tempSessionData);
                        invalidate();
                    } else {
                        LogUtil.debug(logger, "loaded session data : " + tempSessionData);
                    }
                }
            } catch (Throwable e) {
                if (logger.isDebugEnabled()) {
                    logger.error("use requestProxy,requestProxy.getSession(),", e);
                }
                sessionProxy = requestProxy.getSession();
            }
        }

    }

    public void commit() {
        // 使用本地Session代理
        if (null != sessionProxy) {
            Map<String, Object> data = sessionData.getSessionData();
            Set<String> keys = data.keySet();
            Iterator<String> iterator = keys.iterator();
            String key = null;
            while (iterator.hasNext()) {
                key = iterator.next();
                sessionProxy.setAttribute(key, data.get(key));
            }
            LogUtil.debug(logger, " use local http session , data : " + sessionData);

        } else {
            if (sessionHasChanged && !sessionData.getSessionData().isEmpty()) {
                sessionData.setLastModifiedDmt(System.currentTimeMillis());
                boolean result = ucfSessionStore.saveSession(getCookieSessionId(), sessionData, getTimeout());
                LogUtil.debug(logger, result
                                         + " | commit remote session , getCookieSessionId : "
                                         + getCookieSessionId() + " | data : " + sessionData);
            } else if (!isValid) {
                ucfSessionStore.removeSession(getCookieSessionId());

                LogUtil.debug(logger, " remove remote session , getCookieSessionId : "
                                         + getCookieSessionId());
            }
        }
    }
	
	
	public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    class KeyIterator<K> implements Enumeration<K> {
        private Iterator<K> iterator;

        public KeyIterator(Iterator<K> iterator) {
            this.iterator = iterator;
        }

        public boolean hasMoreElements() {
            return this.iterator.hasNext();
        }

        public K nextElement() {
            return this.iterator.next();
        }
    }

}
