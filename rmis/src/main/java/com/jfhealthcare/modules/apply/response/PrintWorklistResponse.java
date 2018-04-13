package com.jfhealthcare.modules.apply.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PrintWorklistResponse {
	
	private String accessionNum;
	private String printStatusCode;
	private String printStatus;
	private String patName;
	private String sexCode;
	private String sex;
	private String patAge;
	private String ptnAgeUnitCode;
	private String ptnAgeUnit;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date applyTime;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date studyTime;
	private String applyOrg;
	private String applyOrgName;
	private String bodyPartCode;
	private String bodyPart;
	private String examCode;
	private String exam;
	private String summaryCode;
	private String summary;
	private String checkTypeCode;
	private String checkType;
    private String checkRefuseCode;
    private String checkRefuseName;
    private String checkPtnSource;
    private String checkPastIllness;
    private String imageNum;
    private boolean checkJzFlag;
}