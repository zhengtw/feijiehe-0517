package com.jfhealthcare.modules.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jfhealthcare.modules.system.entity.SysRole;
import com.jfhealthcare.tk.mybatis.util.MyMapper;

public interface SysRoleMapper extends MyMapper<SysRole> {

	List<SysRole> selectRolesByLoginCode(@Param(value="logincode")String logincode);
}