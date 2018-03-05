package com.jfhealthcare.modules.system.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.modules.system.annotation.SysLogAop;
import com.jfhealthcare.modules.system.request.SysRightModsetRequest;
import com.jfhealthcare.modules.system.service.SysRightModsetService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 角色、用户->菜单管理api
 * @author xujinma
 */
@Slf4j
@Api(value = "a权限->角色、用户修改菜单管理api")
@RestController
@RequestMapping("/v2/rmis/sysop/sysrightmodset")
public class SysRightModsetController {

	@Autowired
	private SysRightModsetService sysRightModsetService;
	
	@SysLogAop("角色修改菜单")
	@ApiOperation(value = "角色修改菜单", notes = "角色修改菜单说明")
	@RequestMapping(method = RequestMethod.POST,path="/role")
	public BaseResponse updateSysRightModsetByRole(@RequestBody SysRightModsetRequest SysRightModsetRequest) {
		try {
			sysRightModsetService.updateSysRightModsetForRole(SysRightModsetRequest.getLogincodeOrRoleId(), SysRightModsetRequest.getMenuIds(), 2);
			return BaseResponse.getSuccessResponse("修改成功！");
		} catch (Exception e) {
			log.debug("角色修改菜单", e);
			return BaseResponse.getFailResponse("修改失败！");
		}
	}
	
	@SysLogAop("用户修改菜单")
	@ApiOperation(value = "用户修改菜单", notes = "用户修改菜单说明")
	@RequestMapping(method = RequestMethod.POST,path="/oper")
	public BaseResponse updateSysRightModsetByOper(@RequestBody SysRightModsetRequest sysRightModsetRequest) {
		try {
			sysRightModsetService.updateSysRightModsetForOper(sysRightModsetRequest.getLogincodeOrRoleId(), sysRightModsetRequest.getMenuIds(), 1);
			return BaseResponse.getSuccessResponse("修改成功！");
		} catch (Exception e) {
			log.debug("用户修改菜单", e);
			return BaseResponse.getFailResponse("修改失败！");
		}
	}
	
	
}
