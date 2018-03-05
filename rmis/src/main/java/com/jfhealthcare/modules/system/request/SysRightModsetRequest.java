package com.jfhealthcare.modules.system.request;

import java.util.List;

import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;

@Data
public class SysRightModsetRequest extends BasicPageEntity{
    /**
     * 主键
     */
    private String id;
    /**
     * 菜单编号
     */
    private String menuId;
    /**
     * 登录号/角色编号
     */
    private String logincodeOrRoleId;
    /**
     * 标识
     */
    private Integer objflag;

    private List<String> menuIds;
}