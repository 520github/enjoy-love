package com.enjoy.love.common.mq;

import java.io.Serializable;
import java.net.SocketAddress;

public class GeneralMessage implements Serializable {
	private static final long serialVersionUID = 5534655680676443099L;

	private Object bizValue;

	private String msgKeys;

	// 消息ID
	private String msgId;
	// 队列ID
	private int queueId;

	private String bornHostString;

	// 消息在客户端创建时间戳
	private long bornTimestamp;
	// 消息在服务器存储时间戳
	private long storeTimestamp;

	private int storeSize;

	// 队列偏移量
	private long queueOffset;

	private SocketAddress storeHost;

	// 消息对应的Commit Log Offset
	private long commitLogOffset;

	// 消息体循环校验码(CRC码)
	private int bodyCRC;

	// 当前消息被某个订阅组重新消费了几次（订阅组之间独立计数）
	private int reconsumeTimes;

	public Object getBizValue() {
		return bizValue;
	}

	public void setBizValue(Object bizValue) {
		this.bizValue = bizValue;
	}

	public String getMsgKeys() {
		return msgKeys;
	}

	public void setMsgKeys(String msgKeys) {
		this.msgKeys = msgKeys;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public int getQueueId() {
		return queueId;
	}

	public void setQueueId(int queueId) {
		this.queueId = queueId;
	}

	public String getBornHostString() {
		return bornHostString;
	}

	public void setBornHostString(String bornHostString) {
		this.bornHostString = bornHostString;
	}

	public long getBornTimestamp() {
		return bornTimestamp;
	}

	public void setBornTimestamp(long bornTimestamp) {
		this.bornTimestamp = bornTimestamp;
	}

	public long getStoreTimestamp() {
		return storeTimestamp;
	}

	public void setStoreTimestamp(long storeTimestamp) {
		this.storeTimestamp = storeTimestamp;
	}

	public int getStoreSize() {
		return storeSize;
	}

	public void setStoreSize(int storeSize) {
		this.storeSize = storeSize;
	}

	public long getQueueOffset() {
		return queueOffset;
	}

	public void setQueueOffset(long queueOffset) {
		this.queueOffset = queueOffset;
	}

	public SocketAddress getStoreHost() {
		return storeHost;
	}

	public void setStoreHost(SocketAddress storeHost) {
		this.storeHost = storeHost;
	}

	public long getCommitLogOffset() {
		return commitLogOffset;
	}

	public void setCommitLogOffset(long commitLogOffset) {
		this.commitLogOffset = commitLogOffset;
	}

	public int getBodyCRC() {
		return bodyCRC;
	}

	public void setBodyCRC(int bodyCRC) {
		this.bodyCRC = bodyCRC;
	}

	public int getReconsumeTimes() {
		return reconsumeTimes;
	}

	public void setReconsumeTimes(int reconsumeTimes) {
		this.reconsumeTimes = reconsumeTimes;
	}

	@Override
	public String toString() {
		return "General Message [queueId=" + queueId + ", storeSize="
				+ storeSize + ", queueOffset=" + queueOffset
				+ ", bornTimestamp=" + bornTimestamp + ", storeTimestamp="
				+ storeTimestamp + ", storeHost=" + storeHost + ", msgId="
				+ msgId + ", commitLogOffset=" + commitLogOffset + ", bodyCRC="
				+ bodyCRC + ", reconsumeTimes=" + reconsumeTimes
				+ "]--[content:]" + bizValue.toString();
	}
}
