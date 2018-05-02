package com.jfhealthcare.modules.business.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
public class BusinCheckFlowState {
    /**
     * 主键
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 流水号
     */
    private String accessionNum;

    /**
     * 编号
     */
    private Integer number;

    /**
     * 状态CODE
     */
    private String statusCode;

    /**
     * 状态
     */
    private String status;

    /**
     * 操作人
     */
    private String operationUser;

    /**
     * 操作时间
     */
    private Date operationTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 影像所见
     */
    private String finding;

    /**
     * 诊断建议
     */
    private String impression;

    /**
     * HP
     */
    private String hp;
}