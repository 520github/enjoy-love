package com.enjoy.love.web.session.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.enjoy.love.common.log.CommonLogger;
import com.enjoy.love.common.log.CommonLoggerFactory;
import com.enjoy.love.web.AbstractFilter;
import com.enjoy.love.web.HttpContextFactory;
import com.enjoy.love.web.WebConstant;
import com.enjoy.love.web.session.UcfHttpRequestLifecycle;
import com.enjoy.love.web.util.HttpHeaderUtils;
import com.enjoy.love.web.util.HttpSpringUtils;


public class HttpSessionFilter extends AbstractFilter implements Filter {

	public static final CommonLogger logger = CommonLoggerFactory
			.getLogger(HttpSessionFilter.class);

	private HttpContextFactory httpContextFactory;

	protected void doInit(FilterConfig filterConfig) throws ServletException {
		httpContextFactory = (HttpContextFactory) HttpSpringUtils.getBean(WebConstant.HTTP_CONTEXT_FACTORY);
		logger.warn("init httpContextFactory OK .");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ucf.framework.web.AbstractFilter#doFilterLogic(javax.servlet.
	 * ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterLogic(ServletRequest request,
			ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest hrequest = (HttpServletRequest) request;
        HttpServletResponse hresponse = (HttpServletResponse) response;
        
        String sessionId = UUID.randomUUID().toString();
        Cookie cookie = updateCookie(hrequest, WebConstant.JRGC_SESSIONID, sessionId);

        HttpServletRequest requestWrapper = httpContextFactory.createHttpServletRequest(hrequest);
        HttpServletResponse responseWrapper = httpContextFactory.createHttpServletResponse(hresponse);
        
        try {
            if (logger.isDebugEnabled()) {
            	logger.debug("user Http Server session , HID : " + cookie.getValue() + " | SID = "
                              + hrequest.getRequestedSessionId() + " | SVID = "
                              + hrequest.getSession().getId());
            }
            // FIXME:写入cookie的时机各个web容器有差别
            responseWrapper.addCookie(cookie);
            ((UcfHttpRequestLifecycle) requestWrapper).begin();
            chain.doFilter(requestWrapper, responseWrapper);
        } finally {
            // NOTICE: handle exception ?!!!
            ((UcfHttpRequestLifecycle) requestWrapper).commit();

        }

	}
	
    private Cookie updateCookie(HttpServletRequest hrequest, String cookieId, String sessionId) {
        Cookie cookie = HttpHeaderUtils.getAllCookie(hrequest).get(cookieId);
        if (null != cookie) {
            if (StringUtils.isBlank(cookie.getDomain())) {
                cookie.setMaxAge(-1);// session cookie,浏览器退出就失效,默认是-1
                cookie.setDomain(WebConstant.JRGC_DOMAIN);
                cookie.setPath("/");
            }
        } else {
            cookie = new Cookie(cookieId, sessionId);// MaxAge默认是-1
            cookie.setDomain(WebConstant.JRGC_DOMAIN);
            cookie.setPath("/");
            HttpHeaderUtils.getAllCookie(hrequest).put(cookieId, cookie);
        }
        return cookie;
    }

}
