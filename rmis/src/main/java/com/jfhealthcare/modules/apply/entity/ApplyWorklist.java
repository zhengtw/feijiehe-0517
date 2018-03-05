package com.jfhealthcare.modules.apply.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Table(name = "apply_worklist")
@Data
public class ApplyWorklist {
    /**
     * 检查单号
     */
    @Id
    @Column(name = "check_num")
    private String checkNum;

    /**
     * id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 患者ID
     */
    @Column(name = "ptn_id")
    private String ptnId;

    /**
     * 患者姓名
     */
    @Column(name = "ptn_name")
    private String ptnName;

    /**
     * 出生日期
     */
    @Column(name = "birth_date")
    private Date birthDate;

    /**
     * 患者性别
     */
    private String sex;

    /**
     * 性别code
     */
    @Column(name = "sex_code")
    private String sexCode;

    /**
     * 患者身高
     */
    private String hight;

    /**
     * 患者体重
     */
    private String weight;

    /**
     * 医疗注意事项
     */
    private String announcements;

    /**
     * 显影剂过敏项目
     */
    private String allergy;

    /**
     * 患者职业
     */
    private String occupation;

    /**
     * 患者怀孕状态
     */
    @Column(name = "pgy_status")
    private String pgyStatus;

    /**
     * 仪器种类
     */
    private String modality;

    /**
     * 仪器AE_title
     */
    @Column(name = "ae_title")
    private String aeTitle;

    /**
     * 排检时间
     */
    @Column(name = "study_time")
    private Date studyTime;

    /**
     * 检查医生
     */
    @Column(name = "physician_name")
    private String physicianName;

    /**
     * 检查项目
     */
    private String exam;

    /**
     * 检查部位
     */
    @Column(name = "body_part")
    private String bodyPart;

    /**
     * 检查方法
     */
    private String summary;

    /**
     * 金额
     */
    private Long costs;

    /**
     * 上传医生账号
     */
    @Column(name = "apply_doc")
    private String applyDoc;

    /**
     * 上传机构
     */
    @Column(name = "apply_org")
    private String applyOrg;

    /**
     * 当前状态
     */
    @Column(name = "apply_status")
    private String applyStatus;

    /**
     * 检查唯一
     */
    @Column(name = "study_uid")
    private String studyUid;

    /**
     * 申请状态code
     */
    @Column(name = "apply_status_code")
    private String applyStatusCode;

    /**
     * 患者年龄
     */
    @Column(name = "ptn_age")
    private String ptnAge;

    /**
     * 年龄单位code
     */
    @Column(name = "ptn_age_unit_code")
    private String ptnAgeUnitCode;

    /**
     * 年龄单位
     */
    @Column(name = "ptn_age_unit")
    private String ptnAgeUnit;

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

    /**
     * 是否有正常的影像
     */
    @Column(name = "IS_NORMAL_IMAGE")
    private Boolean isNormalImage;

}