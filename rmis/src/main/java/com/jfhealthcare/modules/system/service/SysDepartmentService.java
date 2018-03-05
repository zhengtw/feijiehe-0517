package com.jfhealthcare.modules.system.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.system.entity.SysDepartment;
import com.jfhealthcare.modules.system.request.SysDepartmentRequest;

public interface SysDepartmentService {

	PageInfo<Map<String,Object>> querySysDepartment(SysDepartmentRequest sysDepartmentRequest);

	void insertSysDepartment(SysDepartmentRequest sysDepartmentRequest);

	void updateSysDepartment(SysDepartmentRequest sysDepartmentRequest);

	void deleteSysDepartment(String isDelete,String id);

	SysDepartment querySysDepartment(String id);

	List<SysDepartment> querySysDepartmentByOrg(String orgId);

	List<SysDepartment> queryAllSysDepartment();

}
