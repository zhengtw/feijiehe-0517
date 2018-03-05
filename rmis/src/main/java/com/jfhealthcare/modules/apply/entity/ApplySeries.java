package com.jfhealthcare.modules.apply.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Table(name = "apply_series")
@Data
public class ApplySeries {
    /**
     * 序列唯一号码
     */
    @Id
    @Column(name = "series_uid")
    private String seriesUid;

    /**
     * id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 序列序号
     */
    @Column(name = "series_number")
    private String seriesNumber;

    /**
     * 检查唯一
     */
    @Column(name = "study_uid")
    private String studyUid;

    /**
     * 仪器种类
     */
    private String modality;

    /**
     * 检查部位
     */
    @Column(name = "body_part")
    private String bodyPart;

    /**
     * 序列生成日期
     */
    @Column(name = "series_date")
    private String seriesDate;

    /**
     * 序列生成时间
     */
    @Column(name = "series_time")
    private String seriesTime;

    /**
     * 序列描述
     */
    @Column(name = "series_desc")
    private String seriesDesc;

    /**
     * 开单项目检查号
     */
    @Column(name = "req_proc_id")
    private String reqProcId;

    /**
     * 排程项目检查号
     */
    @Column(name = "sched_proc_id")
    private String schedProcId;

    /**
     * 检查技师
     */
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * 仪器型号
     */
    @Column(name = "model_name")
    private String modelName;

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

}