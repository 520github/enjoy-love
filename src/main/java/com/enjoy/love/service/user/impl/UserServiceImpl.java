package com.enjoy.love.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enjoy.love.dao.user.UserDao;
import com.enjoy.love.entity.user.User;
import com.enjoy.love.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	public boolean isExistUserName(String userName) {
		User user = userDao.findByUserName(userName);
		if(user != null) {
			return true;
		}
		return false;
	}
	
	public int insertUser(User user) {
		return userDao.save(user).getUserId();
	}
	
	
	public User getUserByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

}
