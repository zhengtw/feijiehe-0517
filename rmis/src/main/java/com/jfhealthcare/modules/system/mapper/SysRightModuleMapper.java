package com.jfhealthcare.modules.system.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.jfhealthcare.modules.system.entity.SysRightModule;
import com.jfhealthcare.tk.mybatis.util.MyMapper;

public interface SysRightModuleMapper extends MyMapper<SysRightModule> {

	List<SysRightModule> querySysMenuForRestart(@Param(value="logincode")String logincode);
}