package com.jfhealthcare.modules.system.mapper;

import java.util.List;

import com.jfhealthcare.modules.system.entity.SysLog;
import com.jfhealthcare.tk.mybatis.util.MyMapper;

public interface SysLogMapper extends MyMapper<SysLog> {
	List<String> querySysLogOperations();
}