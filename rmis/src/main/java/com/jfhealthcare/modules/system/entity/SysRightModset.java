package com.jfhealthcare.modules.system.entity;

import javax.persistence.*;

import lombok.Data;

@Data
public class SysRightModset {
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
     * 登录号/角色编号
     */
    private String logincode;

    /**
     * 标识
     */
    private Integer objflag;

}