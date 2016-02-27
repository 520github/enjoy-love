/**
 * 
 */
package com.enjoy.love.personal.asset;

import org.junit.Test;

import com.enjoy.love.BaseTest;

/**
 * @author keke
 *
 */
public class CalProfitTest extends BaseTest {
	
	@Test
	public void calProfitTest() {
		int investCycle =3;
		double investProfitRate = 0.1;
		double investAmount = 400000;
		
		double result = CalProfit.calProfit(investCycle, investProfitRate, investAmount);
		this.print("calProfit Total amount is {}, Profit amount is {}", result, result - investAmount);
	}
	
	@Test
	public void calMonthProfitTest() {
		int investMonthCycle = 12*30;
	    double investMonthProfileRate = 0.06;
	    double investMonthAmount = 200;
	    
	    double result = CalProfit.calMonthProfit(investMonthCycle, investMonthProfileRate, investMonthAmount);
	    this.print("calMonthProfit tatal amount is {}", result);
	}
}
