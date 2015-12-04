/**
 * 
 */
package com.enjoy.love.resource.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.enjoy.love.entity.user.User;
import com.enjoy.love.exception.EntityNotFoundException;
import com.enjoy.love.service.user.UserService;

@RestController
@RequestMapping("/user")
public class UserResource {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("exist/{userName}")
	public boolean isExistUserName(@PathVariable String userName) {
		System.out.println("userName-->" + userName);
		return userService.isExistUserName(userName);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Integer addUser(@RequestBody User user) {
		return userService.insertUser(user);
	}
	
	@RequestMapping("/{userName}")
	public User getUserByUserName(@PathVariable String userName) throws EntityNotFoundException {
		User user =  userService.getUserByUserName(userName);
		if(user == null) {
			throw new EntityNotFoundException();
		}
		return user;
	}
}
