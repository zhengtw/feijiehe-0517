package com.jfhealthcare.modules.system.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.base.PageResponse;
import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.modules.system.annotation.LoginUser;
import com.jfhealthcare.modules.system.entity.SysOperator;
import com.jfhealthcare.modules.system.entity.SysRightModule;
import com.jfhealthcare.modules.system.service.SysRightModsetService;
import com.jfhealthcare.modules.system.service.SysRightModuleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xujinma
 */
@Slf4j
@Api(value = "a权限->菜单权限管理api")
@RestController
@RequestMapping("/v2/rmis/sysop/sysrightmodule")
public class SysRightModuleController {

	@Autowired
	private SysRightModuleService sysRightModuleService;
	
	@ApiOperation(value = "查询当前用户所有菜单", notes = "查询当前用户所有菜单说明")
	@RequestMapping(method = RequestMethod.GET)
	public BaseResponse querySysRightModuleForLogin(@LoginUser LoginUserEntity loginUserEntity) {
		
		List<SysRightModule> querySysRightModule = sysRightModuleService.querySysRightModule(loginUserEntity.getSysOperator().getLogincode());
		
		return BaseResponse.getSuccessResponse(querySysRightModule);
	}
	
	
	
	
}
