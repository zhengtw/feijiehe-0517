package com.jfhealthcare.modules.system.request;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.common.validator.group.Insert;

import lombok.Data;

@Data
public class SysOperatorAllRequest {
	 /**
     * 登陆名
     */
	@NotBlank(message="登陆名不能为空!",groups={Insert.class})
    private String logincode;

    /**
     * 用户编号
     */
	@NotBlank(message="ID不能为空!",groups={Edit.class})
    private String id;

    /**
     * 密码
     */
	@NotBlank(message="密码不能为空!",groups={Insert.class})
    private String password;

    /**
     * 审核Code
     */
    private String adminCode;

    /**
     * 审核
     */
    private String admin;

    /**
     * 自定义主页
     */
    private String homePage;

    /**
     * 启用状态
     */
    private Boolean status;


    /**
     * 备注
     */
    private String remark;


    /**
     * 签名
     */
    private String signature;
    
    /**
     * 用户详情ID
     */
	@NotBlank(message="DtlId不能为空!",groups={Edit.class})
    private String did;

    private String archivesNum;

    private String name;

    private String beforeName;

    private String idNum;

    private Date birthday;

    private String birthplace;

    private String familyName;

    private String nativePlace;

    private String health;

    private String politicalOutlook;

    private String martialStatus;

    private String education;

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

    private String eflagCode;

    private String eflag;

    private String manageCode;

    private String manage;

    private String professionalLevel;

    private String professional;

    private String technicalLevel;

    private String technical;

    private String sex;

    private String depId;

    private String orgId;
    
    private String namepy;
    
    private String namewb;
	
	//医疗
	private String armariums;
	
	//角色ID
	private String roleIdd;

	public String getDepId() {
		return StringUtils.isBlank(depId)?null:depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getOrgId() {
		return StringUtils.isBlank(orgId)?null:orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	
}
