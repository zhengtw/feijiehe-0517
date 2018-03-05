package com.jfhealthcare.modules.system.response;

import java.util.Date;
import java.util.List;

import com.jfhealthcare.modules.system.entity.SysArmariumOper;
import com.jfhealthcare.modules.system.entity.SysOperRole;

import lombok.Data;

@Data
public class OperatorResponse {

	private String id;
	/**
	 * 登录账号
	 */
	private String logincode;
	
	
	private String oid;
	/**
	 * 用户详情编号ID
	 */
	private String did;
	/**
	 * 审核Code
	 */
	private String adminCode;
	/**
	 * 审核
	 */
	private String admin;
	/**
	 * 部门编号
	 */
	private String depId;
	/**
	 * 部门名称
	 */
	private String depName;
	/**
	 * 用户姓名
	 */
	private String name;
	
	private String manageCode;
	
	private String manage;
	
	private String namepy;
	
	private String namewb;
	/**
	 * 职务编号
	 */
	private String eflagCode;
	/**
	 * 职务名称
	 */
    private String eflag;
    
    private String education;
    
    private String orgId;
    
    private String orgName;
    
    private String signature;
    
    private String homePage;
    
    private String remark;
    
    private String archivesNum;
    
    private String beforeName;
    
    private String idNum;
    
    private Date birthday;
    
    private String birthplace;
    
    private String familyName;
    
    private String nativePlace;
    
    private String health;
    
    private String politicalOutlook;
    
    private String martialStatus;
    
    private String academicDegree;
    
    private String bloodType;
    
    private String height;
    
    private String leftVersion;
    
    private String rightVersion;
    
    private String major;
    
    private String language;
    
    private String languageLevel;
    
    private String postCode;
    
    private String homeTel;
    
    private String homeMail;
    
    private String officeTel;
    
    private String residenceAddress;
    
    private String homeAddress;
    
    private String residenceDifference;
    
    private String professionalLevel;
    
    private String professional;
    
    private String technicalLevel;
    
    private String technical;
    
    private String sex;
	
	private List<SysArmariumOper> armariums;
	
	private List<SysOperRole> roleIdd;
}
