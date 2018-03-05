package com.jfhealthcare.modules.business.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
public class BusinPatient {
    /**
     * 患者唯一标识
     */
	@Id
    private String patId;

    /**
     * ID
     */
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 患者姓名
     */
    private String name;

    /**
     * 患者姓名_拼音
     */
    private String namePy;

    /**
     * 性别Code
     */
    private String sexCode;

    /**
     * 性别
     */
    private String sex;

    /**
     * 出生日期
     */
    private Date bitth;

    /**
     * 身份证号
     */
    private String idSh;

    /**
     * 社保号
     */
    private String socialsecurity;

    /**
     * 民族Code
     */
    private String nationCode;

    /**
     * 民族
     */
    private String nation;

    /**
     * 血型Code
     */
    private String bloodtypeCode;

    /**
     * 血型
     */
    private String bloodtype;

    /**
     * 联系电话
     */
    private String phon;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * Email
     */
    private String email;

    /**
     * 患者身份Code
     */
    private String identityCode;

    /**
     * 患者身份
     */
    private String identity;

    /**
     * 逻辑删除
     */
    private Boolean isdelete;

    /**
     * 修改人
     */
    private String updUser;

    /**
     * 修改时间
     */
    private Date updTime;

    /**
     * 创建人
     */
    private String crtUser;

    /**
     * 创建时间
     */
    private Date crtTime;

}