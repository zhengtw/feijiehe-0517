package com.jfhealthcare.modules.system.mapper;

import java.util.List;
import java.util.Map;

import com.jfhealthcare.modules.system.entity.SysDepartment;
import com.jfhealthcare.tk.mybatis.util.MyMapper;

public interface SysDepartmentMapper extends MyMapper<SysDepartment> {

	List<Map<String, Object>> queryDepartmentBycondition(SysDepartment sysDepartment);
}