package com.jfhealthcare.modules.system.service;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.system.entity.SysRole;

public interface SysRoleService {

	PageInfo<SysRole> querySysRole(Integer pageNum, Integer pageSize);

	PageInfo<SysRole> querySysRoleByParameter(SysRole sysRole, Integer pageNum, Integer pageSize);

	void delectSysRole(String[] split);

	void saveSysRole(SysRole sysRole);

	void updateSysRole(SysRole sysRole);

	SysRole querySysRoleById(String id);

	String judgerole(String adOrup, String roleName);

}
