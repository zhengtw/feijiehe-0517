package com.jfhealthcare.modules.business.request;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;
/**
 * 视图
 * @author xujinma
 *
 */
@Data
public class ViewWorklistRequest extends BasicPageEntity{
	/**
     * 检查流水号
     */
    @Column(name = "CHECK_ACCESSION_NUM")
    @NotBlank(message="报告流水号不能为空",groups={Edit.class})
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
     * 报告医生
     */
    @Column(name = "CHECK_REPORT_DR")
    private String checkReportDr;

    /**
     * 报告医生名称
     */
    @Column(name = "CHECK_REPORT_DR_NAME")
    private String checkReportDrName;
    
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
     * 审核世间
     */
    @Column(name = "CHECK_AUDIT_TIME")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date checkAuditTime;
    
    /**
     * 拒绝字典code
     */
    @Column(name = "CHECK_REFUSE_CODE")
    private String checkRefuseCode;
    
    /**
     * 拒绝字典name
     */
    @Column(name = "CHECK_REFUSE_NAME")
    private String checkRefuseName;
    
    
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
     * 检查单号
     */
    @Column(name = "CHECK_NUM")
    private String checkNum;
    
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
    
    private List<String> checkStatusCodes;
    
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
     * 申请开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date checkApplyStartTime;
    
    /**
     * 申请结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date checkApplyEndTime;
    
    /**
     * 选中时间范围
     */
    private Integer checkDate;
    
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
    
    private List<String> checkTypeCodes;
    /**
     * 检查类型Code
     */
    private String checkTypeCode;
    
    /**
     * 检查类型
     */
    private String checkType;
    
    /**
     * 检查方法Code
     */
    private String checkSummaryCode;

    /**
     * 检查方法
     */
    private String checkSummary;
    
    private String isButton;   //是：1  不是：0
}