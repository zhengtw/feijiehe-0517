package com.jfhealthcare.common.enums;

/**
 * 审核权限
 */
public enum AdminEnum {
	ROOT("管理员","3702"),
	ONE_REVIEW("一审医师","3705"),
	TWO_REVIEW("二审医师","3704"),
	THREE_REVIEW("三审医师","3703"),
	REPORT("报告医师","3706"),
	JICENG("基层医师","3707"); 
	
    private String admin;
    
    private String adminCode;
    
	private AdminEnum(String admin,String adminCode) {
		this.admin = admin;
		this.adminCode = adminCode;
		
	}
	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
	public String getAdminCode() {
		return adminCode;
	}
	
	public void setAdminCode(String adminCode) {
		this.adminCode = adminCode;
	}
}
