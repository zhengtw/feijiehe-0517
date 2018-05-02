package com.jfhealthcare.common.enums;

/**
 * 标注当前状态
 * */
public enum LabelStatusEnum {
	PENDING_LABEL("未标注","7001"),
	IN_LABEL("标注中","7002"),
	LABELED("已标注","7001"),
	CHECKING_LABEL("审核中","7001"),
	LABEL_CHECKED("已审核","7001");
	
	
    private String status;
    
    private String statusCode;
    
	private LabelStatusEnum(String status,String statusCode) {
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
