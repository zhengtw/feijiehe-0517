package com.jfhealthcare.modules.business.entity;

import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class BusinChecklistIndex {
	
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;
    
    /**
     * 检查流水号
     */
    @Id
    private String accessionNum;

    /**
     * 检查单号
     */
    private String checkNum;
    
    /**
     * 检查部位Code
     */
    private String partsCode;

    /**
     * 检查部位
     */
    private String parts;

    /**
     * 检查方法Code
     */
    private String summaryCode;

    /**
     * 检查方法
     */
    private String summary;

    /**
     * 检查项目Code
     */
    private String examCode;

    /**
     * 检查项目
     */
    private String exam;

    /**
     * 金额
     */
    private Long costs;

    /**
     * 当前状态Code
     */
    private String statusCode;

    /**
     * 当前状态
     */
    private String status;

    /**
     * AI状态Code
     */
    private String statusAiCode;

    /**
     * AI状态
     */
    private String statusAi;

    /**
     * 检查部位分组Code
     */
    private String partsGroupCode;

    /**
     * 检查部位分组
     */
    private String partsGroup;
    
    /**
     * 检查类型Code
     */
    private String typeCode;
    
    /**
     * 检查类型
     */
    private String type;

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

    /**
     * 备注
     */
    private String remark;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 图像数量
     */
    private Integer imgNum;
    /**
     * 图像接收时间
     */
    private Date imgReceiveTime;

    /**
     * 申请医生Code
     */
    private String applyDocCode;

    /**
     * 申请医生
     */
    private String applyDoc;

    /**
     * 申请医院Code
     */
    private String applyHospCode;

    /**
     * 申请医院
     */
    private String applyHosp;

    /**
     * 报告分组ID
     */
    private String repGroupId;

    /**
     * 病情描述
     */
    private String describeBq;

    /**
     * 报告医生
     */
    private String reportDr;

    /**
     * 报告世间
     */
    private Date reportTime;

    /**
     * 审核医生
     */
    private String auditDr;

    /**
     * 审核世间
     */
    private Date auditTime;

    /**
     * 打印医生
     */
    private String printDr;

    /**
     * 打印世间
     */
    private Date printTime;
    
    /**
    * 急诊
    */
   private Boolean jzFlag;

   /**
    * 危急
    */
   private Boolean vjFlag;
   
   /**
    * 拒绝字典code
    */
   private String refuseCode;
   /**
    * 拒绝字典name
    */
   private String refuseName;
}