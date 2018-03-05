package com.jfhealthcare.modules.basics;


import lombok.Data;

@Data
public class PatientDto {
	
	/**
	 * 病人的名称
	 */
	private String name;
	
	/**
	 * 病人的性别
	 */
	private String sex;
	
	/**
	 * 病人的年龄
	 */
	private String age;
	
	/**
	 * 病人诊断编码
	 */
	private String id;
}
