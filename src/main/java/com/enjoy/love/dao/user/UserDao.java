package com.enjoy.love.dao.user;

import org.springframework.data.repository.CrudRepository;

import com.enjoy.love.entity.user.User;

public interface UserDao extends CrudRepository<User, Integer> {
	
	/**
	 * 根据用户名查询用户信息
	 * @param userName
	 * @return
	 */
	public User findByUserName(String userName);
}
