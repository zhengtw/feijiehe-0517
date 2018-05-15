package com.jfhealthcare.common.enums;

public enum LabelParamEnum {
	ID("_id","单条标注ID"),
	STUDY_UID("studyInstanceUid","studyUID"),
	SERIES_UID("seriesInstanceUid","seriesUID"),
	IMAGE_UID("sopInstanceUid","sopUID"),
	VIEW_PORT("viewport","--"),
	VOI("voi","--"),
	WINDOW_WIDTH("windowWidth","窗宽"),
	WINDOW_CENTER("windowCenter","窗位"),
	NIDUS_TYPE("location","病灶");
	
	
	
    private String param;
    
    private String desc;
    
	private LabelParamEnum(String param,String desc) {
		this.param = param;
		this.desc = desc;
		
	}
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
