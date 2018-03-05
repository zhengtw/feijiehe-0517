package com.jfhealthcare.modules.BI.response;

import java.util.Date;

import javax.persistence.Column;

import lombok.Data;

@Data
public class RepCountResponse {

	/**
     * AI统计日期
     */
	@Column(name = "dateAI")
	private Date dateAI;
	
	
	/**
     * AI报告数量
     */
	@Column(name = "countAI")
	private int countAI;
	
	
	
	
	/**
     * 人工统计日期
     */
	@Column(name = "datePeople")
	private Date datePeople;
	/**
     * 人工报告数量
     */
	@Column(name = "countPeople")
	private int countPeople;
	
	
	
	
	/**
     * 总数统计日期
     */
	@Column(name = "dateSum")
	private Date dateSum;
	/**
     * 每日报告总数
     */
	@Column(name = "countSum")
	private int countSum;
	
	
	
}
