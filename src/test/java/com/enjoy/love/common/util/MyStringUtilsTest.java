/**
 * 
 */
package com.enjoy.love.common.util;

import org.junit.Test;

import com.enjoy.love.BaseTest;

/**
 * @author lenovo
 *
 */
public class MyStringUtilsTest extends BaseTest {
	
	@Test
	public void sortAscByCharArrayTest() {
		String source = "AEBJKC19OP8";
		String result = MyStringUtils.sortAscByCharArray(source);
		this.print("result is {}", result);
	}
}
