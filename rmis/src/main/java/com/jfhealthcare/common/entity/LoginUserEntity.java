package com.jfhealthcare.common.entity;

import java.util.List;

import com.jfhealthcare.modules.system.entity.SysArmariumOper;
import com.jfhealthcare.modules.system.entity.SysOperator;
import com.jfhealthcare.modules.system.entity.SysOperatorDtl;
import com.jfhealthcare.modules.system.entity.SysRole;

import lombok.Data;

@Data
public class LoginUserEntity {

	private SysOperator sysOperator;//系统用户
	
	private SysOperatorDtl sysOperatorDtl;//人员信息
	
	private List<SysArmariumOper> sysArmariumOpers;//用户仪器权限
	
	private List<SysRole> sysRoles;//用户角色
	
	private String cloudImageUrl;//云上訪問地址 首
}
