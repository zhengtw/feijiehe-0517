package com.jfhealthcare.modules.system.entity;

import java.util.Date;
import javax.persistence.*;

import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;

@Data
public class SysOperator {
    /**
     * 登陆名
     */
    @Id
    private String logincode;

    /**
     * 用户编号
     */
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 密码
     */
    private String password;

    /**
     * 审核Code
     */
    private String adminCode;

    /**
     * 审核
     */
    private String admin;

    /**
     * 自定义主页
     */
    private String homePage;

    /**
     * 启用状态
     */
    private Boolean status;

    /**
     * 逻辑删除
     */
    private Boolean isdelete;

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
     * 签名
     */
    private String signature;

}