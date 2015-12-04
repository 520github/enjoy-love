package com.enjoy.love.resource.user;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.enjoy.love.entity.user.User;
import com.enjoy.love.resource.BaseResourceTest;
import com.enjoy.love.service.user.UserService;

public class UserResourceTest extends BaseResourceTest {
	UserResource userResource = new UserResource();
	
	@Mock
	UserService userService;
	
	private MockMvc mockMvc;
	
	@Before
	public void before() {
		userResource.userService = userService;
		mockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
	}
	
	@Test
	public void test10FindUserByUserName() throws Exception {
		User user = new User();
		user.setUserName("sunso");
		user.setLoginPassword("hello");
		
		when(userService.getUserByUserName("sunso")).thenReturn(user);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/user/{userName}", "sunso"))
		       .andExpect(MockMvcResultMatchers.status().is(200))
		       .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		       //.andExpect(MockMvcResultMatchers.content().json(""))
		       ;
	}
	
	@Test
	public void test20FindUserByUserNameNotFoundAndReturnNull() throws Exception {
		when(userService.getUserByUserName(anyString())).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/user/{userName}", "sunso"))
		.andExpect(MockMvcResultMatchers.status().is(404));
	}
}
