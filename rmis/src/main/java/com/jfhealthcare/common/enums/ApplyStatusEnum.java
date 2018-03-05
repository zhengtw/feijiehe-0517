package com.jfhealthcare.common.enums;

/**
 * 报告当前状态
 */
public enum ApplyStatusEnum {
	/**
	 * applystatus  基层申请状态
	 */
	PENDING_APPLY("待申请","6000"),
	COMPLETE_APPLY("已申请","6001"); 
	
    private String status;
    
    private String statusCode;
    
	private ApplyStatusEnum(String status,String statusCode) {
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
