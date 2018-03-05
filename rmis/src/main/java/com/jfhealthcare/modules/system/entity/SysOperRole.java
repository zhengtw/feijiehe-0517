package com.jfhealthcare.modules.system.entity;

import javax.persistence.*;

import lombok.Data;

@Data
public class SysOperRole {
    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 角色编码
     */
    private String roleId;

    /**
     * 登录号
     */
    private String logincode;

    
}