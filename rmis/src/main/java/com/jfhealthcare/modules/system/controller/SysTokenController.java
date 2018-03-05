package com.jfhealthcare.modules.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jfhealthcare.common.base.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * token效验api
 * @author xujinma
 */
@Api(value = "a权限->页面跳转token效验api")
@RestController
@RequestMapping("/v2/rmis/sysop/token")
public class SysTokenController {

	@ApiOperation(value = "token效验", notes = "token效验说明")
	@RequestMapping(method = RequestMethod.GET)
	public BaseResponse querySysLog() {
		return BaseResponse.getSuccessResponse("token效验通过");
	}
	
	
	
	
	
}
