package com.jfhealthcare.modules.system.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.modules.system.entity.SysOperator;
import com.jfhealthcare.modules.system.entity.SysOperatorDtl;
import com.jfhealthcare.modules.system.request.OrdinaryOperatorRequest;
import com.jfhealthcare.modules.system.request.SysOperatorAllRequest;
import com.jfhealthcare.modules.system.request.SysOperatorQueryRequest;
import com.jfhealthcare.modules.system.response.OperatorResponse;

public interface SysOperatorService {

	SysOperator selectSysOperator(String logincode);

	Map<String, Object> login(String logincode, String password);

	void save(String logincode, String password);

	void saveOperatorAndDtl(SysOperator sysOperatorNew, SysOperatorDtl sysOperatorDtlNew, String logincode, String roleIdd, String armariumIds);

	void deleteOperator(String[] split);

	void updateOperator(SysOperator sysOperatorNew, SysOperatorDtl sysOperatorDtlNew, String roleIdd, String armariumIds,LoginUserEntity loginUserEntity);

	void updateOrdinaryOperator(OrdinaryOperatorRequest ooRequest);

	PageInfo<OperatorResponse> queryOperator(Integer pageNum, Integer pageSize);

	PageInfo<OperatorResponse> queryOperatorByParameter(SysOperatorQueryRequest sysOqr);

	OperatorResponse queryOperatorByLogincode(String logincode);

	String judgelogin(String logincode);

	void updateOperator(SysOperatorAllRequest sysOperatorAllRequest) throws Exception;

}
