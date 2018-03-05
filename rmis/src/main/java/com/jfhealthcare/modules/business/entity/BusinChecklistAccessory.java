package com.jfhealthcare.modules.business.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
public class BusinChecklistAccessory {
    /**
     * ID
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 序号
     */
    private Integer numId;

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
     * 附件地址
     */
    private String file;

}