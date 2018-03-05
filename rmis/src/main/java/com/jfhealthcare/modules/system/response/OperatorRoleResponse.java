package com.jfhealthcare.modules.system.response;


import lombok.Data;
@Data
public class OperatorRoleResponse {
	/**
     * 主键
     */
    private String id;

    /**
     * 角色编码
     */
    private String roleId;

    /**
     * 登录号
     */
    private String logincode;
    
    private String name;
    
    private String deptId;
    
    private String deptName;
}
