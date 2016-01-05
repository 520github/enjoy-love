/**
 * 
 */
package com.enjoy.love.common.util;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

/**
 * @author lenovo
 *
 */
public class MyStringUtils {
	
	public static String sortAscByCharArray(String source) {
		if(StringUtils.isBlank(source)) {
			return source;
		}
		char[] c = source.toCharArray();
		Arrays.sort(c);
		return String.valueOf(c);
	}
	
	
	
}
