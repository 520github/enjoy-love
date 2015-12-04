package com.enjoy.love.entity.user;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.enjoy.love.entity.base.BaseEntity;

@Entity
public class User extends BaseEntity {
	//@GeneratedValue(strategy=GenerationType., generator="SEQ_STORE")
	@Id
	private Integer userId;
	/** 用户名  **/
	private String userName;
	/** 登录密码 **/
	private String loginPassword;
	
	public Integer getUserId() {
		if(userId == null) {
			userId = new Random().nextInt(100000);
		}
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	
}
