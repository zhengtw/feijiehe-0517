package com.jfhealthcare.modules.system.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.base.PageResponse;
import com.jfhealthcare.modules.system.entity.SysOperatorDtl;
import com.jfhealthcare.modules.system.request.SysOperatorDtlRequest;
import com.jfhealthcare.modules.system.service.SysOperatorDtlService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户管理
 * @author xujinma
 */
@Slf4j
@Api(value = "a用户->用户详情管理api")
@RestController
@RequestMapping("/v2/rmis/sysop/sysoperatordtl")
public class SysOperatorDtlController {

	@Autowired
	private SysOperatorDtlService sysOperatorDtlService;

	@ApiOperation(value = "条件详情查询用户(机构，部门，账号，简拼)", notes = "条件查询用户说明")
	@RequestMapping(method = RequestMethod.POST,value="/all")
	public BaseResponse querySysDepartment(@RequestBody SysOperatorDtlRequest sysOperatorDtlRequest) {
		
		PageInfo<SysOperatorDtl> querySysDepartment = sysOperatorDtlService.querySysDepartment(sysOperatorDtlRequest);
		
		return PageResponse.getSuccessPage(querySysDepartment);
	}
    
}
