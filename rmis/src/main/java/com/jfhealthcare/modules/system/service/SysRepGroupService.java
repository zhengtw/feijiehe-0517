package com.jfhealthcare.modules.system.service;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.system.entity.SysRepGroup;

public interface SysRepGroupService {

	PageInfo<SysRepGroup> querySysRepGroup(Integer pageNum, Integer pageSize);

	PageInfo<SysRepGroup> querySysRepGroupById(SysRepGroup sysrg, Integer pageNum, Integer pageSize);

	void deleteSysRepGroup(String[] split);

	void saveSysRepGroup(SysRepGroup re);

	void updateSysRepGroup(SysRepGroup re);

}
