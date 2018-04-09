package com.jfhealthcare.modules.system.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
public class SysOperatorDtl {
    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

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
    
    /**
     * 拼音简码
     */
    private String namepy;

    /**
     * 五笔简码
     */
    private String namewb;

    private String updUser;

    private Date updTime;

    private String crtUser;

    private Date crtTime;

    private String sex;

    private String depId;

    private String orgId;

    private String logincode;

}