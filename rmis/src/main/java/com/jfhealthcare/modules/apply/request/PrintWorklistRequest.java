package com.jfhealthcare.modules.apply.request;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;

@Data
public class PrintWorklistRequest extends BasicPageEntity{
    /**
     * id
     */
    private String id;

    /**
     * 患者ID
     */
    private String ptnId;

    /**
     * 患者姓名
     */
    private String ptnName;

    
    /**
     * 选中时间范围
     */
    private Integer checkDate=1601;//默认今天
    
    /**
     * 检查开始时间
     */
    private Date applyStartTime;
    
    /**
     * 检查结束时间
     */
    private Date applyEndTime;

    /**
     * 患者性别
     */
    private String sex;
    
    @Column(name = "sex_code")
    private String sexCode;

    private List<String> printStatusCodes;

    /**
     * 检查部位
     */
    private String bodyPart;

    /**
     * 上传机构
     */
    private String applyOrg;
    
    private String isOpen;
}