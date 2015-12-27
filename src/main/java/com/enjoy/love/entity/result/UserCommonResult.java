package com.enjoy.love.entity.result;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class UserCommonResult<B> implements Externalizable {

	/** 业务处理状态 */
	protected UserResultStatusEnum businessResult = null;

	/** 业务结果 */
	protected B businessObject = null;

	/** 可以放置exception的msg信息 */
	protected String resultCodeMsg = null;

	public UserCommonResult() {
	}

	public UserCommonResult(UserResultStatusEnum businessResult,
			B businessObject) {
		this.businessResult = businessResult;
		this.businessObject = businessObject;
	}

	public UserCommonResult(UserResultStatusEnum businessResult,
			String resultCodeMsg, B businessObject) {
		this(businessResult, businessObject);
		this.resultCodeMsg = resultCodeMsg;
	}

	/**
	 * 是否成功
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return businessResult == UserResultStatusEnum.SUCCESS;
	}

	/**
	 * 是否失败
	 * 
	 * @return
	 */
	public boolean isFailure() {
		return !isSuccess();
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(businessResult != null ? businessResult.getCode()
				: UserResultStatusEnum.UNKOWN_SYS_ERROR.getCode());
		out.writeObject(businessObject);
		out.writeObject(resultCodeMsg);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		businessResult = UserResultStatusEnum.valueOf(in.readInt());
		businessObject = (B) in.readObject();
		resultCodeMsg = (String) in.readObject();
	}

	public UserResultStatusEnum getBusinessResult() {
		return businessResult;
	}

	public void setBusinessResult(UserResultStatusEnum businessResult) {
		this.businessResult = businessResult;
	}

	public B getBusinessObject() {
		return businessObject;
	}

	public void setBusinessObject(B businessObject) {
		this.businessObject = businessObject;
	}

	public String getResultCodeMsg() {
		return resultCodeMsg;
	}

	public void setResultCodeMsg(String resultCodeMsg) {
		this.resultCodeMsg = resultCodeMsg;
	}

}
