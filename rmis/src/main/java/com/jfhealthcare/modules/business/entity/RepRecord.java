package com.jfhealthcare.modules.business.entity;

import javax.persistence.*;

import lombok.Data;

@Data
public class RepRecord {
    /**
     * 报告UID
     */
	@Id
    private String repUid;

    /**
     * ID
     */
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 检查流水号
     */
    private String accessionNum;

    /**
     * 患者唯一标识
     */
    private String patId;

    /**
     * 患者年龄
     */
    private String patAge;

    /**
     * 年龄单位Code
     */
    private String ageUnitCode;

    /**
     * 年龄单位
     */
    private String ageUnit;

    /**
     * 影像所见1
     */
    private String finding1;

    /**
     * 影像所见2
     */
    private String finding2;

    /**
     * 影像所见3
     */
    private String finding3;

    /**
     * 诊断建议1
     */
    private String impression1;

    /**
     * 诊断建议2
     */
    private String impression2;

    /**
     * 诊断建议3
     */
    private String impression3;
    /**
     * HP
     */
    private String hp;
}