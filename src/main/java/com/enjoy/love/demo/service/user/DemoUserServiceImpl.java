package com.enjoy.love.demo.service.user;

import org.springframework.stereotype.Service;

import com.enjoy.love.common.annotation.DigestLogAnnotated;
import com.enjoy.love.common.enums.LoggerLevel;
import com.enjoy.love.common.enums.LoggerPrintType;
import com.enjoy.love.entity.result.UserCommonResult;
import com.enjoy.love.entity.user.User;
import com.enjoy.love.service.AbstractServiceImpl;
import com.enjoy.love.service.CallbackResult;
import com.enjoy.love.service.ServiceCallback;

@Service("demoUserService")
public class DemoUserServiceImpl extends AbstractServiceImpl implements DemoUserService {

	@Override
	@DigestLogAnnotated(digestIdentificationCode = "6001",
    logFileName = "DEFAULT.DIG",
    loggerLevel = LoggerLevel.INFO,
    printType = LoggerPrintType.IGNORE_OUTPUT)
	public UserCommonResult<User> addUser(User user) {
		this.serviceTemplate.execute(new ServiceCallback() {
			
			@Override
			public CallbackResult executeCheck() {
				return CallbackResult.success();
			}
			
			@Override
			public CallbackResult executeAction() {
				return CallbackResult.success();
			}
		}, user);
		return new UserCommonResult<User>();
	}

}
