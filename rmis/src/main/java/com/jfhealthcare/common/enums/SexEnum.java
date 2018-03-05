package com.jfhealthcare.common.enums;


/**
 * @author jinmaxu
 */
public enum SexEnum {
	MAN("男","2905"),
	WOMEN("女","3425"),
	OTHER("其他","3426");
	
	private String sexStatus;

    private String sexStatusCode;
    
	private SexEnum(String sexStatus,String sexStatusCode) {
		this.sexStatus = sexStatus;
		this.sexStatusCode = sexStatusCode;
	}
	public String getSexStatus() {
		return sexStatus;
	}

	public void setSexStatus(String sexStatus) {
		this.sexStatus = sexStatus;
	}
	
	public String getSexStatusCode() {
		return sexStatusCode;
	}
	
	public void setSexStatusCode(String sexStatusCode) {
		this.sexStatusCode = sexStatusCode;
	}
}
