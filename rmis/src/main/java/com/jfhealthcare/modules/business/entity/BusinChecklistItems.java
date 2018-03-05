package com.jfhealthcare.modules.business.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
public class BusinChecklistItems {
    /**
     * ID
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 序号
     */
    private Integer numberId;

    /**
     * 流水号
     */
    private String accessionNum;

    /**
     * 检查项目Code
     */
    private String examItemCode;

    /**
     * 检查项目
     */
    private String examItem;

    /**
     * 检查项目EN
     */
    private String examItemEn;

    /**
     * 检查部位Code
     */
    private String partsCode;

    /**
     * 检查部位
     */
    private String parts;

    /**
     * 检查部位EN
     */
    private String partsEn;

    /**
     * 检查方法Code
     */
    private String summaryCode;

    /**
     * 检查方法
     */
    private String summary;

    /**
     * 检查方法EN
     */
    private String summaryEn;

    /**
     * 金额
     */
    private Long costs;

    /**
     * 部位分组Code
     */
    private String partsgroupCode;

    /**
     * 部位分组
     */
    private String partsgroup;

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

    
}