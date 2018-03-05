package com.jfhealthcare.modules.apply.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Table(name = "apply_patient")
@Data
public class ApplyPatient {
    /**
     * 患者Id
     */
    @Id
    @Column(name = "ptn_id")
    private String ptnId;

    /**
     * ID
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 患者姓名
     */
    @Column(name = "ptn_name")
    private String ptnName;

    /**
     * 检查唯一
     */
    @Column(name = "study_uid")
    private String studyUid;

    /**
     * 患者性别
     */
    private String sex;

    /**
     * 患者出生日期
     */
    @Column(name = "birth_date")
    private String birthDate;

    /**
     * 身高
     */
    @Column(name = "ptn_size")
    private String ptnSize;

    /**
     * 体重
     */
    @Column(name = "ptn_weight")
    private String ptnWeight;

    /**
     * 患者年龄
     */
    @Column(name = "ptn_age")
    private String ptnAge;

    /**
     * 更新者
     */
    @Column(name = "UPD_USER")
    private String updUser;

    /**
     * 更新时间
     */
    @Column(name = "UPD_TIME")
    private Date updTime;

    /**
     * 创建者
     */
    @Column(name = "CRT_USER")
    private String crtUser;

    /**
     * 创建时间
     */
    @Column(name = "CRT_TIME")
    private Date crtTime;
}