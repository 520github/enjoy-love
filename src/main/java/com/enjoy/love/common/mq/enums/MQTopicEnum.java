package com.enjoy.love.common.mq.enums;

public enum MQTopicEnum {
	/** 开发测试用的*/
	MQ_DEV_TEST("testTopic","testTag","开发测试使用的MQ通道"),
	
	MQ_REG("regTopic","regTag","用户注册通知MQ通道");
	
	/** Topic */
	private String topic;
	/** Tag */
	private String tag;
	/** Desc */
	private String desc;

	private MQTopicEnum(String topic, String tag, String desc) {
		this.topic = topic;
		this.tag = tag;
		this.desc = desc;
	}

	public String[] getTopicAndTag() {
		String[] topicAndTag = new String[] { this.topic, this.tag, this.desc };
		return topicAndTag;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
