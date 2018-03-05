package com.jfhealthcare.common.enums;

/**
 * 报告当前状态
 */
public enum CheckStatusEnum {
	/**
	 * checkstatus  影像中心报告状态
	 */
	PENDING_REPORT("待报告","3434"),
	PENDING_ONE_REVIEW("待审核","3445"),
	PENDING_TWO_REVIEW("待二审","3449"),
	PENDING_THREE_REVIEW("待三审","3550"),
	REPORTING("报告中","3552"),
	REVIEWING("审核中","3553"),
	ZANCUNING("暂存中","3554"),
	COMPLETE_REFUSE("已拒绝","3555"),
	COMPLETE_REVIEW("已审核","3556"),
	COMPLETE_PRINT("已打印","3557"),
	PENGING_HUIZHENG_REPORT("待会报","3558"),
	PENGING_HUIZHENG_REVIEW("待会审","3559"),
	COMPLETE_ABANDONED("已作废","3560"); 
	
    private String status;
    
    private String statusCode;
    
	private CheckStatusEnum(String status,String statusCode) {
		this.status = status;
		this.statusCode = statusCode;
		
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(String status) {
		this.statusCode = statusCode;
	}
}
