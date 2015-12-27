package com.enjoy.love.common.mq;

import com.enjoy.love.common.mq.enums.MQRuntimeFailStatus;
import com.enjoy.love.common.runtime.CommonRuntimeException;

public class MQRuntimeException extends CommonRuntimeException {
	private static final long serialVersionUID = 4766591342250500835L;

	public MQRuntimeException(MQRuntimeFailStatus status) {
		super(status.getCode(),status.getDescription());
	}
	
	public MQRuntimeException(MQRuntimeFailStatus status,Throwable cause) {
		super(status.getCode(),status.getDescription(),cause);
	}
}
