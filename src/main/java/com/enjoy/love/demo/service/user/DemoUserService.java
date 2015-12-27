package com.enjoy.love.demo.service.user;

import com.enjoy.love.entity.result.UserCommonResult;
import com.enjoy.love.entity.user.User;

public interface DemoUserService {
	public UserCommonResult<User> addUser(User user);
}
