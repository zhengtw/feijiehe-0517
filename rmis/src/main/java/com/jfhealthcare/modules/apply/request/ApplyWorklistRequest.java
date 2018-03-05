package com.jfhealthcare.modules.apply.request;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;

@Data
public class ApplyWorklistRequest extends BasicPageEntity{
    /**
     * id
     */
    private String id;
    
    /**
     * 检查单号
     */
    private String checkNum;

    /**
     * 患者ID
     */
    private String ptnId;

    /**
     * 患者姓名
     */
    private String ptnName;

    /**
     * 急诊
     */
    private Boolean checkJzFlag;
    
    /**
     * 选中时间范围
     */
    private Integer checkDate;
    
    /**
     * 检查开始时间
     */
    private Date studyStartTime;
    
    /**
     * 检查结束时间
     */
    private Date studyEndTime;

    /**
     * 患者性别
     */
    private String sex;
    
    private String sexCode;


    /**
     * 检查部位
     */
    private String bodyPart;
    
    /**
     * 病情描述
     */
    private String describeBq;

    /**
     * 上传机构
     */
    private String applyOrg;
    
    private String ptnAge;
    private String ptnAgeUnit;
    private String ptnAgeUnitCode;
    
    /**
     * 检查项目
     */
    private String exam;
    
    /**
     * 检查方法以及code
     */
    private String summary;
     /**
      * instances，接收的图像数组
      */
    private List<String> instanceUids;
}