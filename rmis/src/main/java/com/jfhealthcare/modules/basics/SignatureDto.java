package com.jfhealthcare.modules.basics;

import lombok.Data;

@Data
public class SignatureDto {
	/**
	 * 报告医生
	 */
	private String report_phy;
	
	/**
	 * 报告医生签名路径
	 */
	private String report_phy_sign;
	
	/**
	 * 报告时间
	 */
	private String report_time;
	
	/**
	 * 审核医生
	 */
	private String verify_phy;
	
	/**
	 * 审核医生签名路径
	 */
	private String verify_phy_sign;
	
	/**
	 * 审核时间
	 */
	private String verify_time;

}
