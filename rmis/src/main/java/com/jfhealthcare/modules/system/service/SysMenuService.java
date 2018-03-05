package com.jfhealthcare.modules.system.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.system.entity.SysMenu;
import com.jfhealthcare.modules.system.entity.SysRightModset;
import com.jfhealthcare.modules.system.entity.SysRightModule;
import com.jfhealthcare.modules.system.request.SysMenuRequest;

public interface SysMenuService {


	List<Map<String, Object>> querySysMenu();

	SysMenu querySingleSysMenu(String id);

	void insertSysMenu(SysMenuRequest sysMenuRequest);

	void updateSysMenu(SysMenuRequest sysMenuRequest);

	void deleteSysMenu(String id);

    List<Map<String, Object>> querySysMenuForLogin(String logincode);

	List<SysRightModset> querySysMenuForRole(String roleId);

	List<SysMenu> queryUppderSysMenu();

	List<SysRightModule> querySysMenuForClick(String logincode);

	Integer querySysMenuMaxNindex(String faMenuId);

	Boolean querySysMenuByNindexAndFaMenuId(String faMenuId, Integer nindex);

}
