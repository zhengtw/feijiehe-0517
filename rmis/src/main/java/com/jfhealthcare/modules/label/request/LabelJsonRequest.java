package com.jfhealthcare.modules.label.request;


import lombok.Data;

@Data
public class LabelJsonRequest {
	private String labelValueUid;
	private String jsonValue1;
	private String userId;
	private String studyUid;
	private String labelAccnum;
}
