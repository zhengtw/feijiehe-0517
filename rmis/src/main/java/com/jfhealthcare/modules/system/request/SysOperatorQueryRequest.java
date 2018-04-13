package com.jfhealthcare.modules.system.request;

import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;

@Data
public class SysOperatorQueryRequest extends BasicPageEntity {
	
	private String namepy;
	
	private String eflag;
	
	private String logincode;
	
	private String orgId;
	
	private String depId;
	
	private String name;
}
