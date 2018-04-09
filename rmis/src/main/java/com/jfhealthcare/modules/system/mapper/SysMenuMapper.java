package com.jfhealthcare.modules.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jfhealthcare.modules.system.entity.SysMenu;
import com.jfhealthcare.modules.system.entity.SysRightModule;
import com.jfhealthcare.tk.mybatis.util.MyMapper;

public interface SysMenuMapper extends MyMapper<SysMenu> {

	List<Map<String, Object>> querySysMenuBycondition(SysMenu sysMenu);
	
	List<Map<String, Object>> querySysMenuByconditionForLogin(Map<String,Object> sysMenu);

	Integer querySysMenuMaxNindex(@Param(value="faMenuId")String faMenuId);
	
	List<Map<String, Object>> querySysMenuForFatherId(@Param(value="logincode")String logincode);

	List<SysRightModule> querySysMenuForClick(@Param(value="logincode")String logincode);
}