package com.jfhealthcare.modules.system.mapper;

import java.util.List;
import java.util.Map;


import com.jfhealthcare.modules.system.entity.SysOperator;
import com.jfhealthcare.modules.system.request.SysOperatorQueryRequest;
import com.jfhealthcare.modules.system.response.OperatorResponse;
import com.jfhealthcare.tk.mybatis.util.MyMapper;

public interface SysOperatorMapper extends MyMapper<SysOperator> {
	List<OperatorResponse> queryOperatorList(); 
	List<OperatorResponse> queryOperatorListByParameter(SysOperatorQueryRequest sysOqr);
	List<OperatorResponse> queryOperatorAllInfoByLogincode(Map<String, Object> pmap); 
}