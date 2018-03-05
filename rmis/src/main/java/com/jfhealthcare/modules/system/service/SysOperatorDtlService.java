package com.jfhealthcare.modules.system.service;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.system.entity.SysOperatorDtl;
import com.jfhealthcare.modules.system.request.SysOperatorDtlRequest;

public interface SysOperatorDtlService {

	PageInfo<SysOperatorDtl> querySysDepartment(SysOperatorDtlRequest sysOperatorDtlRequest);

	SysOperatorDtl queryDtlByLoginCode(String checkPrintDr);

	

}
