package com.enjoy.love.web.session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import com.enjoy.love.common.log.CommonLogger;
import com.enjoy.love.common.log.CommonLoggerFactory;
import com.enjoy.love.web.WebConstant;
import com.enjoy.love.web.util.HttpHeaderUtils;


public class UcfHttpRequestWrapper extends HttpServletRequestWrapper implements
		UcfHttpRequestLifecycle {
	
	private final static CommonLogger logger = CommonLoggerFactory.getLogger(UcfHttpRequestWrapper.class);
	
    public UcfHttpSessionWrapper httpSession = null;

    private String cookieSessionId = null;
	
	public UcfHttpRequestWrapper(HttpServletRequest request,
			UcfSessionStore ucfSessionStore, long sessionTimeout) {
		super(request);
        //FIXME:若浏览器没有JRGC_SESSIONID这个cookie(第一次访问),则cookie取不到,cookieSessionId为空,会导致redis里存的时候key值为"session#null"
		Cookie cookie = HttpHeaderUtils.getAllCookie(request).get(WebConstant.JRGC_SESSIONID);
        cookieSessionId = request.getRequestedSessionId();
        
        if (logger.isDebugEnabled()) {
            logger.debug("request.getRequestedSessionId : " + request.getRequestedSessionId()
                         + " | JSESSIONID cookie : " + ((null == cookie) ? "" : cookie.getValue())
                         + " | SESSION ID : " + request.getSession().getId());
        }
        if (null != cookie) {
            cookieSessionId = cookie.getValue();
        }else{
        	//fix:第一次访问cookieSessionId为空的情况:
        	//第一次访问:request.getRequestedSessionId : null | JSESSIONID cookie :  | SESSION ID : F7E198B6CC3FA260A61866D4CC761A9B
        	cookieSessionId = request.getSession().getId();
        }
        httpSession = new UcfHttpSessionWrapper(request.getRequestedSessionId(), cookieSessionId, ucfSessionStore, request);
        httpSession.setTimeout(sessionTimeout);
        
	}
	
	/**
     * The default behavior of this method is to return getRequestedSessionId()
     * on the wrapped request object.
     */
    public String getRequestedSessionId() {
        return this.cookieSessionId;
    }

    /**
     * The default behavior of this method is to return getSession(boolean
     * create) on the wrapped request object.
     */
    public HttpSession getSession(boolean create) {
        return httpSession;
    }

    /**
     * The default behavior of this method is to return getSession() on the
     * wrapped request object.
     */
    public HttpSession getSession() {
        return httpSession;
    }

	@Override
	public void begin() {
		httpSession.begin();
	}

	@Override
	public void commit() {
		httpSession.commit();
	}

}
