package com.enjoy.love.security.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyShiroDbRealm extends AuthorizingRealm {
	private static int roleId = 1;

	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		 String username = (String) getAvailablePrincipal(principals);
		 System.out.println("username-->" + username);
		 Set<String> roleSet = new HashSet<String>();
	     roleSet.add(String.valueOf(roleId));
	     SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleSet);
	     info.setStringPermissions(null);
		 return info;
	}

	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String phone = upToken.getUsername();
		System.out.println("phone-->" + phone);
		
		String password = "111111";
		AuthenticationInfo info = new SimpleAuthenticationInfo(phone, password, "realName");
		return info;
	}

}
