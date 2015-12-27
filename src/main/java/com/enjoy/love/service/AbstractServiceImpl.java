package com.enjoy.love.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.enjoy.love.common.log.CommonLogger;
import com.enjoy.love.common.log.CommonLoggerFactory;

public abstract class AbstractServiceImpl {
	protected final CommonLogger logger = CommonLoggerFactory.getLogger(this
			.getClass());

	@Autowired
	protected ServiceTemplate serviceTemplate;
}
