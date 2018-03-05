package com.jfhealthcare.modules.basics;

import lombok.Data;

@Data
public class MqMessage {

	private String sopUID;
	
	private String seriesUID;
	
	private String studyUID;
	
	private String userId;
}
