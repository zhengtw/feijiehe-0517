package com.jfhealthcare.modules.system.service;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.system.entity.SysOperRole;
import com.jfhealthcare.modules.system.response.OperatorRoleResponse;

public interface SysOperRoleService {

	PageInfo<SysOperRole> querySysOperRole(Integer pageNum, Integer pageSize);

	PageInfo<OperatorRoleResponse> querySysOperRoleById(SysOperRole sysrg, Integer pageNum, Integer pageSize, String namepy);

	void deleteSysOperRole(String[] split);

	void saveSysOperRole(SysOperRole re);

	void updateSysOperRole(SysOperRole re);

}
