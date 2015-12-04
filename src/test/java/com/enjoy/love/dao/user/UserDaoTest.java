package com.enjoy.love.dao.user;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.enjoy.love.dao.BaseDaoTest;
import com.enjoy.love.entity.user.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDaoTest extends BaseDaoTest {
	@Autowired
	private UserDao userDao;
	
	private static User user;
	
	@BeforeClass
	public static void before() {
		user = new User();
		user.setUserName("xm");
		user.setLoginPassword("xml");
	}
	
	
	@Test
	public void test10InsertUser() {
		User result = userDao.save(user);
		Assert.assertNotNull(result);
	}
}
