package com.jfhealthcare.common.enums;

/**
 * 报告当前状态
 */
public enum PrintStatusEnum {
	/**
	 * printstatus  基层打印状态
	 */
	PENDING_DOING("处理中","6002"),
	COMPLETE_DOING("完成未打印","6003"),
	COMPLETE_PRINT("已打印","6004"),
	COMPLETE_REFUSE("已拒绝","6005"); 
	
    private String status;
    
    private String statusCode;
    
	private PrintStatusEnum(String status,String statusCode) {
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
