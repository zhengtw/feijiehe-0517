package com.jfhealthcare.modules.label.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Table(name = "label_operate_record")
@Data
public class LabelOperateRecord {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    @Column(name = "UID")
    private String uid;

    @Column(name = "operation_step")
    private Integer operationStep;

    @Column(name = "label_status_code")
    private String labelStatusCode;

    @Column(name = "label_status")
    private String labelStatus;

    private String operator;

    @Column(name = "operate_time")
    private Date operateTime;
}