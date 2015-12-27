package com.enjoy.love.common.util;

import java.util.HashMap;
import java.util.Map;


/**
 * 作为一个全局的Java Enum的cache来用，帮助解决从code-->得到enum的问题 <br>
 * @author keke
 *
 */
public class JavaEnumUtils {
	private static Map<String, Object> cache = new HashMap<String, Object>();

	public static void put(String key, Object value) {
		cache.put(key, value);
	}

	public static Object get(String key) {
		return cache.get(key);
	}
}
