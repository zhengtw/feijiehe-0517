package com.jfhealthcare.modules.system.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.base.PageResponse;
import com.jfhealthcare.modules.system.entity.SysLog;
import com.jfhealthcare.modules.system.request.SysLogRequest;
import com.jfhealthcare.modules.system.service.SysLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志管理api
 * @author xujinma
 */
@Slf4j
@Api(value = "z日志->日志管理api")
@RestController
@RequestMapping("/v2/rmis/sysop/syslog")
public class SysLogController {

	
	@Autowired
	private SysLogService sysLogService;
	
	
	@ApiOperation(value = "查询日志", notes = "查询日志说明")
	@RequestMapping(method = RequestMethod.POST,value="/all")
	public BaseResponse querySysLog(@RequestBody SysLogRequest sysLogRequest) {
		
		PageInfo<SysLog> sysLogs=sysLogService.querySysLog(sysLogRequest);
		
		return PageResponse.getSuccessPage(sysLogs);
	}
	
//	@ApiOperation(value="查询日志操作种类",notes="查询日志操作种类")
//	@RequestMapping(method = RequestMethod.GET,value="/operations")
//	public BaseResponse querySysLogOperations() {
//		
//		List<String> sysoper = sysLogService.querySysLogOperations();
//		
//		return BaseResponse.getSuccessResponse(sysoper);
//	}
	
	
	
	
	
	
}
