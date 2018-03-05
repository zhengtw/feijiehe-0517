package com.jfhealthcare.modules.apply.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ApplyWorklistResponse {
	 /**
     * id
     */
    @Id
    private String id;

    /**
     * 患者ID
     */
    @Column(name = "ptn_id")
    private String ptnId;

    /**
     * 检查单号
     */
    @Column(name = "check_num")
    private String checkNum;

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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
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
    
    private String applyOrgName;

    /**
     * 当前状态
     */
    @Column(name = "apply_status")
    private String applyStatus;

    @Column(name = "apply_status_code")
    private String applyStatusCode;
    
    /**
     * 检查唯一
     */
    @Column(name = "study_uid")
    private String studyUid;
    
    @Column(name = "ptn_age")
    private String ptnAge;
    @Column(name = "ptn_age_unit")
    private String ptnAgeUnit;
    @Column(name = "ptn_age_unit_code")
    private String ptnAgeUnitCode;
    
    private int imageNum;
    
    private List<Map<String, Object>> imageUrls=new ArrayList<Map<String, Object>>();
    
    private String urlStart;
    
    /**
	 * 更改人
	 */
	private String updUser;

	/**
	 * 更改时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updTime;

	/**
	 * 创建人
	 */
	private String crtUser;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date crtTime;
	
	/**
	 * 是否有正常影像
	 */
	private Boolean isNormalImage;
	
	private String checkType;
	
	 /**
     * 病情描述
     */
    private String describeBq;
    /**
     * instances，接收的图像数组
     */
   private List<String> instanceUids=new ArrayList<String>();
}