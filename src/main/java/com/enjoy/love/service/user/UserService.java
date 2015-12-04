package com.enjoy.love.service.user;

import com.enjoy.love.entity.user.User;

public interface UserService {
	/**
	 * 判断用户名是否存在
	 * @param userName
	 * @return
	 */
	public boolean isExistUserName(String userName);
	
	/**
	 * 插入一条用户记录
	 * @param user
	 * @return
	 */
	public int insertUser(User user);
	
	
	/**
	 * 根据用户名获取对应用户信息
	 * @param userName
	 * @return
	 */
	public User getUserByUserName(String userName);
}
