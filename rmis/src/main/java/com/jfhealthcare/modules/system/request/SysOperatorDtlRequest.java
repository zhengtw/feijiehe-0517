package com.jfhealthcare.modules.system.request;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.common.validator.group.Insert;
import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;

@Data
public class SysOperatorDtlRequest extends BasicPageEntity{
	 /**
     * 主键
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
    
    @NotBlank(message="登陆名不能为空!",groups={Insert.class})
    private String logincode;
    
    private String namepy;
    
    private String namewb;
}
