package com.jfhealthcare.modules.label.entity;

import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Table(name = "view_labellist")
@Data
public class ViewLabellist {
    /**
     * 检查单号
     */
    @Column(name = "check_num")
    private String checkNum;

    /**
     * 检查唯一
     */
    @Column(name = "study_uid")
    private String studyUid;

    /**
     * 序列唯一号码
     */
    @Column(name = "series_uid")
    private String seriesUid;

    /**
     * 检查部位
     */
    @Column(name = "body_part")
    private String bodyPart;

    /**
     * 序列序号
     */
    @Column(name = "series_number")
    private String seriesNumber;

    /**
     * 仪器种类
     */
    private String modality;

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
     * 患者年龄
     */
    @Column(name = "ptn_age")
    private String ptnAge;

    /**
     * 年龄单位
     */
    @Column(name = "ptn_age_unit")
    private String ptnAgeUnit;

    /**
     * 年龄单位code
     */
    @Column(name = "ptn_age_unit_code")
    private String ptnAgeUnitCode;

    /**
     * 患者身高
     */
    private String hight;

    /**
     * 患者体重
     */
    private String weight;

    /**
     * 仪器种类
     */
    @Column(name = "Wmodality")
    private String wmodality;

    /**
     * 排检时间
     */
    @Column(name = "study_time")
    private Date studyTime;

    /**
     * 检查部位
     */
    @Column(name = "Wbody_part")
    private String wbodyPart;

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
     * 申请状态code
     */
    @Column(name = "apply_status_code")
    private String applyStatusCode;

    /**
     * 当前状态
     */
    @Column(name = "apply_status")
    private String applyStatus;

    /**
     * 患者ID
     */
    @Column(name = "ptn_id")
    private String ptnId;

    /**
     * 是否有正常的影像
     */
    @Column(name = "IS_NORMAL_IMAGE")
    private Boolean isNormalImage;

    /**
     * ID_标注流水号
     */
    @Column(name = "LABEL_ACCNUM")
    private String labelAccnum;

    /**
     * 标注状态CODE
     */
    @Column(name = "STATUS_CODE")
    private String statusCode;

    /**
     * 标注状态
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * 标注人
     */
    @Column(name = "LABEL_USER")
    private String labelUser;

    /**
     * 标注时间
     */
    @Column(name = "LABEL_TIME")
    private Date labelTime;

    /**
     * 审核人
     */
    @Column(name = "AUDIT_USER")
    private String auditUser;

    /**
     * 审核时间
     */
    @Column(name = "AUDIT_TIME")
    private Date auditTime;

    @Column(name = "label_Task_Code")
    private String labelTaskCode;

    @Column(name = "label_Task")
    private String labelTask;

    @Column(name = "nidus_Type_Code")
    private String nidusTypeCode;

    @Column(name = "nidus_Type")
    private String nidusType;
    
    @Column(name= "CRT_TIME")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date crtTime;
}