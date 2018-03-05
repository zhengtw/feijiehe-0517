package com.jfhealthcare.modules.system.entity;

import javax.persistence.*;

import lombok.Data;

@Data
public class SysRightModule {
    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 菜单编号
     */
    private String menuId;

    /**
     * 登录号
     */
    private String logincode;

    
}