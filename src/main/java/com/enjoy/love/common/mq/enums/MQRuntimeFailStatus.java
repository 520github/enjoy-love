package com.enjoy.love.common.mq.enums;

import com.enjoy.love.common.util.JavaEnumUtils;

public enum MQRuntimeFailStatus {
	MQ_CLIENT_FAILURE(900101, "MQ Client Failure"),

	MQ_REMOTING_FAILURE(900102, "MQ Remoting Failure"),

	MQ_BROKER_FAILURE(900103, "MQ Broker Failure"),

	MQ_INTERRUPT_FAILURE(900104, "MQ Interrupt Failure"),

	MQ_INIT_PARAM_FAILURE(900105, "MQ Initialize Failure"),

	MQ_SERIALIZE_FAILURE(900106, "MQ Serialize Failure"),

	MQ_PUBLISH_PERMISSION_FAILURE(900107, "Send Message Permission Failure");

	private int code;

	private String description;

	private MQRuntimeFailStatus(int code, String description) {
		this.code = code;
		this.description = description;
		JavaEnumUtils.put(this.getClass().getName() + code, this);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
