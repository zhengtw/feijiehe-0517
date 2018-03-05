package com.jfhealthcare.modules.system.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.system.entity.SysLog;
import com.jfhealthcare.modules.system.request.SysLogRequest;

public interface SysLogService {

	void save(SysLog sysLog);

	PageInfo<SysLog> querySysLog(SysLogRequest sysLogRequest);

	List<String> querySysLogOperations();

}
