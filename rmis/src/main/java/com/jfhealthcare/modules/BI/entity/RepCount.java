package com.jfhealthcare.modules.BI.entity;

import java.util.Date;

import lombok.Data;

/**
 * wangpeng/20171212
 */

@Data
public class RepCount {
	
	/**
     * AI报告数量
     */
	private int countAI;
	
	/**
     * AI统计日期
     */
	private Date dateAI;
	
	/**
     * 人工报告数量
     */
	private int countPeople;
	
	/**
     * 人工统计日期
     */
	private Date datePeople;
	
	/**
     * 每日报告总数
     */
	private int countSum;
	
	/**
     * 总数统计日期
     */
	private Date dateSum;

}
