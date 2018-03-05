package com.jfhealthcare.modules.system.service;

import java.util.List;

import com.jfhealthcare.modules.system.entity.SysRightModule;

public interface SysRightModuleService {

	List<SysRightModule> querySysRightModule(String logincode);

	void restartSysRightModule(String logincode);
}
