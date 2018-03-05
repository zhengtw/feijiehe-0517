package com.jfhealthcare.modules.business.response;

import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
/**
 * 视图
 * @author xujinma
 *
 */
@Data
public class ViewWorklistResponse {
	/**
     * 检查单号
     */
    @Column(name = "CHECK_NUM")
    private String checkNum;
	/**
     * 患者唯一标识
     */
    @Column(name = "PAT_ONLY_ID")
    private String patOnlyId;
	/**
     * 急诊
     */
    @Column(name = "CHECK_JZ_FLAG")
    private Boolean checkJzFlag;

    /**
     * 危急
     */
    @Column(name = "CHECK_VJ_FLAG")
    private Boolean checkVjFlag;
    
    private Boolean isHaveImage;
    
    /**
     * 检查流水号
     */
    @Column(name = "CHECK_ACCESSION_NUM")
    private String checkAccessionNum;
    
    /**
     * 当前状态Code
     */
    @Column(name = "CHECK_STATUS_CODE")
    private String checkStatusCode;

    /**
     * 当前状态
     */
    @Column(name = "CHECK_STATUS")
    private String checkStatus;

    /**
     * AI状态Code
     */
    @Column(name = "CHECK_STATUS_AI_CODE")
    private String checkStatusAiCode;

    /**
     * AI状态
     */
    @Column(name = "CHECK_STATUS_AI")
    private String checkStatusAi;
    
    /**
     * 患者姓名
     */
    @Column(name = "PAT_NAME")
    private String patName;

    /**
     * 性别Code
     */
    @Column(name = "PAT_SEX_CODE")
    private String patSexCode;

    /**
     * 性别
     */
    @Column(name = "PAT_SEX")
    private String patSex;

    /**
     * 出生日期
     */
    @Column(name = "PAT_BITTH")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date patBitth;
    
    /**
     * 患者年龄
     */
    @Column(name = "REPRCD_PAT_AGE")
    private String reprcdPatAge;

    /**
     * 年龄单位Code
     */
    @Column(name = "REPRCD_AGE_UNIT_CODE")
    private String reprcdAgeUnitCode;

    /**
     * 年龄单位
     */
    @Column(name = "REPRCD_AGE_UNIT")
    private String reprcdAgeUnit;
    
    /**
     * 申请时间
     */
    @Column(name = "CHECK_APPLY_TIME")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date checkApplyTime;

    /**
     * 图像接收时间
     */
    @Column(name = "CHECK_IMG_RECEIVE_TIME")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date checkImgReceiveTime;

    /**
     * 申请医生Code
     */
    @Column(name = "CHECK_APPLY_DOC_CODE")
    private String checkApplyDocCode;

    /**
     * 申请医生
     */
    @Column(name = "CHECK_APPLY_DOC")
    private String checkApplyDoc;

    /**
     * 申请医院Code
     */
    @Column(name = "CHECK_APPLY_HOSP_CODE")
    private String checkApplyHospCode;

    /**
     * 申请医院
     */
    @Column(name = "CHECK_APPLY_HOSP")
    private String checkApplyHosp;
    
    /**
     * 报告分组ID
     */
    @Column(name = "CHECK_REP_GROUP_ID")
    private String checkRepGroupId;
    
    /**
     * 检查部位Code
     */
    @Column(name = "CHECK_PARTS_CODE")
    private String checkPartsCode;

    /**
     * 检查部位
     */
    @Column(name = "CHECK_PARTS")
    private String checkParts;
    
    /**
     * 检查项目Code
     */
    @Column(name = "CHECK_EXAM_CODE")
    private String checkExamCode;

    /**
     * 检查项目
     */
    @Column(name = "CHECK_EXAM")
    private String checkExam;
    
    /**
     * 检查方法Code
     */
    @Column(name = "CHECK_SUMMARY_CODE")
    private String checkSummaryCode;

    /**
     * 检查方法
     */
    @Column(name = "CHECK_SUMMARY")
    private String checkSummary;
    
    /**
     * 检查类型Code
     */
    private String checkTypeCode;
    
    /**
     * 检查类型
     */
    private String checkType;
    
    /**
     * 病情描述
     */
    @Column(name = "CHECK_DESCRIBE_BQ")
    private String checkDescribeBq;
    
    /**
     * 报告医生
     */
    @Column(name = "CHECK_REPORT_DR")
    private String checkReportDr;
    
    /**
     * 报告医生
     */
    @Column(name = "CHECK_REPORT_DR_NAME")
    private String checkReportDrName;
    
    /**
     * 报告医生签名
     */
    @Column(name = "CHECK_REPORT_DR_SIGNA")
    private String checkReportDrSigna;

    /**
     * 报告世间
     */
    @Column(name = "CHECK_REPORT_TIME")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date checkReportTime;

    /**
     * 审核医生
     */
    @Column(name = "CHECK_AUDIT_DR")
    private String checkAuditDr;
    
    /**
     * 审核医生
     */
    @Column(name = "CHECK_AUDIT_DR_NAME")
    private String checkAuditDrName;

    /**
     * 审核医生签名
     */
    @Column(name = "CHECK_AUDIT_DR_SIGNA")
    private String checkAuditDrSigna;
    
    /**
     * 审核世间
     */
    @Column(name = "CHECK_AUDIT_TIME")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date checkAuditTime;

    /**
     * 打印医生
     */
    @Column(name = "CHECK_PRINT_DR")
    private String checkPrintDr;

    /**
     * 打印医生
     */
    @Column(name = "CHECK_PRINT_DR_SIGNA")
    private String checkPrintDrSigna;
    
    /**
     * 打印世间
     */
    @Column(name = "CHECK_PRINT_TIME")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date checkPrintTime;
    
    /**
     * 影像所见1
     */
    @Column(name = "REPRCD_FINDING1")
    private String reprcdFinding1;

    /**
     * 影像所见2
     */
    @Column(name = "REPRCD_FINDING2")
    private String reprcdFinding2;

    /**
     * 影像所见3
     */
    @Column(name = "REPRCD_FINDING3")
    private String reprcdFinding3;

    /**
     * 诊断建议1
     */
    @Column(name = "REPRCD_IMPRESSION1")
    private String reprcdImpression1;

    /**
     * 诊断建议2
     */
    @Column(name = "REPRCD_IMPRESSION2")
    private String reprcdImpression2;

    /**
     * 诊断建议3
     */
    @Column(name = "REPRCD_IMPRESSION3")
    private String reprcdImpression3;
    
    /**
     * HP
     */
    @Column(name = "REPRCD_HP")
    private String reprcdHp;
    
    /**
     * 报告UID
     */
    @Column(name = "REPRCD_REP_UID")
    private String reprcdRepUid;
    
    private String checkRefuseName;
    
    //-----------------worklist数量---------------------
    private int pendingreportNum;
    private int pendingonereviewNum;
    private int pendingtworeviewNum;
    private int pendingthreereviewNum;
    private int reportingNum;
    private int reviewingNum;
    private int zancuningNum;
    private int completerefuseNum;
    private int completereviewNum;
    private int completeprintNum;
    private int pendinghuizhenReportNum;
    private int pendinghuizhenReviewNum;
    private int completeAbandonedNum;
    //--------------------------------------
    private String sopUrl;
    //--------------------------------------
}