package com.jfhealthcare.modules.business.entity;

import javax.persistence.*;

import lombok.Data;

@Data
public class BusinPatientInfo {
    /**
     * ID
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 患者唯一标识
     */
    private String patId;

    /**
     * 就诊卡号
     */
    private String cardNo;

    /**
     * 住院号
     */
    private String ihpNo;

    /**
     * 住院次数
     */
    private Integer visid;

    /**
     * 检查单号
     */
    private String checklistId;
}