package com.jfhealthcare.modules.system.request;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.common.validator.group.Insert;
import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;

@Data
public class SysOrganizationRequest extends BasicPageEntity{
    /**
     * 机构编号
     */
	@NotBlank(message="机构ID不能为空",groups = Edit.class)
    private String id;
	
	/**
     * 机构编号
     */
    @Column(name = "CODE")
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
    @Email(message="不符合邮箱规范",groups = {Edit.class,Insert.class})
    private String email;

    /**
     * 简介
     */
    private String notation;
    
    /**
     * 拼音简码
     */
    private String namepy;

    /**
     * 五笔简码
     */
    private String namewb;

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
    @NotBlank(message="机构分类不能为空",groups = {Edit.class,Insert.class})
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

}