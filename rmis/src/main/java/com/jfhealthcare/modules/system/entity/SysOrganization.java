package com.jfhealthcare.modules.system.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
@Table(name = "sys_organization")
public class SysOrganization {
    /**
     * 机构编号
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 机构编号
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 机构名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 机构简称
     */
    @Column(name = "SHORTNAME")
    private String shortname;

    /**
     * 联系电话
     */
    @Column(name = "PHONE")
    private String phone;

    /**
     * 机构负责人
     */
    @Column(name = "ORG_HEADER")
    private String orgHeader;

    /**
     * 地址
     */
    @Column(name = "ADDRESS")
    private String address;

    /**
     * 邮箱
     */
    @Column(name = "EMAIL")
    private String email;

    /**
     * 简介
     */
    @Column(name = "NOTATION")
    private String notation;

    /**
     * 状态
     */
    @Column(name = "STATUS")
    private Boolean status;

    /**
     * 逻辑删除
     */
    @Column(name = "ISDELETE")
    private Boolean isdelete;

    /**
     * 分类编号
     */
    @Column(name = "CLS_ID")
    private String clsId;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 更新人
     */
    @Column(name = "UPD_USER")
    private String updUser;

    /**
     * 更新时间
     */
    @Column(name = "UPD_TIME")
    private Date updTime;

    /**
     * 创建人
     */
    @Column(name = "CRT_USER")
    private String crtUser;

    /**
     * 创建时间
     */
    @Column(name = "CRT_TIME")
    private Date crtTime;

    @Column(name = "NAMEPY")
    private String namepy;

    @Column(name = "NAMEWB")
    private String namewb;

    /**
     * 城市编码
     */
    @Column(name = "CITY_NO")
    private Integer cityNo;

    /**
     * 省份编码
     */
    @Column(name = "PROVINCE_NO")
    private Integer provinceNo;

    /**
     * 机构级别
     */
    @Column(name = "ORG_LEVEL")
    private String orgLevel;

    /**
     * 县\区编码
     */
    @Column(name = "COUNTY_NO")
    private Integer countyNo;
}