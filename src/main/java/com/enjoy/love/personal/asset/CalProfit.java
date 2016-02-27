/**
 * 
 */
package com.enjoy.love.personal.asset;

/**
 * @author keke
 *
 */
public class CalProfit {
	public static double calProfit(int investCycle, double investProfitRate, double investAmount) {
		for(int i=0; i<investCycle; i++) {
			double profitValue = investAmount*investProfitRate;
			investAmount = investAmount + profitValue;
		}
		return investAmount;
	}
	
	public static double calMonthProfit(int investMonthCycle, double investMonthProfileRate, double investMonthAmount) {
		double totalProfit = 0;
		double tatalInvestAmount = 0;
		for(int i=0; i<investMonthCycle; i++) {
			tatalInvestAmount = investMonthAmount *(i+1) + totalProfit;
			totalProfit = totalProfit + (tatalInvestAmount*investMonthProfileRate/12);
		}
		System.out.println("totalInvestAmount-->" + investMonthAmount*investMonthCycle);
		System.out.println("totalProfit-->" + totalProfit);
		return investMonthAmount*investMonthCycle + totalProfit;
	}
}
