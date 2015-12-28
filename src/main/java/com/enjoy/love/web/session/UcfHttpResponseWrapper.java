package com.enjoy.love.web.session;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.StringUtils;

import com.enjoy.love.common.log.CommonLogger;
import com.enjoy.love.common.log.CommonLoggerFactory;

public class UcfHttpResponseWrapper extends HttpServletResponseWrapper {
private static final CommonLogger logger = CommonLoggerFactory.getLogger(UcfHttpResponseWrapper.class);
	
	/** 当系统发生error时，将页面转发到哪儿，例如发生 action找不到的场景 */
    private String errorPage;

    /** 当前Http应用的跟地址 */
    private String rootAddress;

    public UcfHttpResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    public void setRootAddress(String rootAddress) {
        this.rootAddress = rootAddress;
    }

    // ---------- 重载业务方法
    /**
     * The default behavior of this method is to call sendError(int sc, String
     * msg) on the wrapped response object.
     */
    public void sendError(int sc, String msg) throws IOException {
        if (StringUtils.isNotBlank(errorPage)) {
            if (sc == 404) {
                logError(sc, msg);
                super.sendRedirect(rootAddress + errorPage);
            } else {
                if (sc != 304) {
                    logError(sc, msg);
                }
                super.sendError(sc, msg);
            }
        } else {
            super.sendError(sc, msg);
        }
    }

	/**
     * The default behavior of this method is to call sendError(int sc) on the
     * wrapped response object.
     */
    public void sendError(int sc) throws IOException {
        this.sendError(sc, "");
    }
    
    //--------------------------- HELP METHOD ----------------------------

    private final static String TIP_ONE = "sendError , sc =";

    private final static String TIP_TWO = "| msg = ";

    private void logError(int sc, String msg) {
        StringBuilder builder = new StringBuilder();
        builder.append(TIP_ONE);
        builder.append(sc);
        builder.append(TIP_TWO);
        builder.append(msg);
        logger.error(builder.toString());
    }
}
