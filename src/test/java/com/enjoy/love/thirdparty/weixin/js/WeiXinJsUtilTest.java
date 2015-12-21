package com.enjoy.love.thirdparty.weixin.js;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.enjoy.love.BaseSpringBootTest;
import com.enjoy.love.thirdparty.weixin.js.model.WXAccessToken;

public class WeiXinJsUtilTest extends BaseSpringBootTest {
	@Autowired
	private WeiXinJsUtil weiXinJsUtil;
	
	@Test
	public void getWXAccessTokenTest() {
		WXAccessToken accessToken = weiXinJsUtil.getWXAccessToken();
		this.print("get accessToken {}", accessToken);
	}
	
	@Test
	public void getWeiXinJsSignMapTest() {
		String url = "https://www.qianshengcai.com/wechat/v1/myQRCode";
		Map<String, String> result = weiXinJsUtil.getWeiXinJsSignMap(url);
		this.print("get result {}", result);
	}
}
