package com.jfhealthcare.modules.apply.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Table(name = "apply_study")
@Data
public class ApplyStudy {
    /**
     * 检查唯一号码
     */
    @Id
    @Column(name = "study_uid")
    private String studyUid;

    /**
     * id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 检查日期
     */
    @Column(name = "study_date")
    private String studyDate;

    /**
     * 检查时间
     */
    @Column(name = "study_time")
    private String studyTime;

    /**
     * 检查号
     */
    @Column(name = "study_id")
    private String studyId;

    /**
     * 检查流水号
     */
    @Column(name = "accession_number")
    private String accessionNumber;

    /**
     * 主治医生姓名
     */
    @Column(name = "physician_name")
    private String physicianName;

    /**
     * 检查描述
     */
    @Column(name = "check_describe")
    private String checkDescribe;

    /**
     * 使用者群组
     */
    @Column(name = "user_group")
    private String userGroup;

    /**
     * 创建者
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