package com.jfhealthcare.modules.business.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
public class SetReportDb {
    /**
     * ID
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级
     */
    private String upper;

    /**
     * 排序
     */
    private String nindex;

    /**
     * 类型Code
     */
    private String typeCode;

    /**
     * 类型
     */
    private String type;

    /**
     * 拼音简码
     */
    private String namepy;

    /**
     * 五笔简码
     */
    private String namewb;

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
     * 逻辑删除
     */
    private Boolean isdelete;

    /**
     * 影像所见
     */
    private String finding;

    /**
     * 诊断建议
     */
    private String impression;
    /**
     * 是否文件
     */
    private Boolean fileflag; //ture  文件夹    false 文件
}