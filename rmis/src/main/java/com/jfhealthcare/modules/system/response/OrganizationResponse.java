package com.jfhealthcare.modules.system.response;

import java.util.Date;

import com.jfhealthcare.modules.system.entity.SysOrganization;

import lombok.Data;

@Data
public class OrganizationResponse extends SysOrganization{
	 /**
     * 机构编号
     */
    private String id;

    /**
     * 机构编号
     */
    private String code;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 机构简称
     */
    private String shortname;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 机构负责人
     */
    private String orgHeader;

    /**
     * 地址
     */
    private String address;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 简介
     */
    private String notation;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 逻辑删除
     */
    private Boolean isdelete;

    /**
     * 分类编号
     */
    private String clsId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 更新人
     */
    private String updUser;

    /**
     * 更新时间
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

    private String namepy;

    private String namewb;

    /**
     * 城市编码
     */
    private Integer cityNo;

    /**
     * 省份编码
     */
    private Integer provinceNo;

    /**
     * 机构级别
     */
    private String orgLevel;

    /**
     * 县\区编码
     */
    private Integer countyNo;
    
    
    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 省份名称
     */
    private String provinceName;

    /**
     * 机构级别名称
     */
    private String orgLevelName;

    /**
     * 县\区名称
     */
    private String countyName;
}
