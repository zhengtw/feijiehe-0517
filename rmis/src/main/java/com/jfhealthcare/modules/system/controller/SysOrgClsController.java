package com.jfhealthcare.modules.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.modules.system.entity.SysOrgCls;
import com.jfhealthcare.modules.system.service.SysOrgClsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 机构分类
 * @author xujinma
 */
@Slf4j
@Api(value = "b组织->机构分类管理api")
@RestController
@RequestMapping("/v2/rmis/sysop/sysorgcls")
public class SysOrgClsController {

	@Autowired
	private SysOrgClsService sysOrgClsService;
	
	@ApiOperation(value = "机构分类查询", notes = "机构分类查询说明")
	@RequestMapping(method = RequestMethod.GET)
	public BaseResponse querySysOrgCls() {
		List<SysOrgCls> sysOrgClss = sysOrgClsService.querySysOrgClsAll();
		return BaseResponse.getSuccessResponse(sysOrgClss);
	}
	
	
	
	
	
	
}
