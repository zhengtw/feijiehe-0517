package com.jfhealthcare.modules.system.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.modules.system.entity.SysDictCity;
import com.jfhealthcare.modules.system.service.SysDictCityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(value = "z字典_省市区->字典_省市区接口api") 
@RestController
@RequestMapping("/v2/rmis/sysop/dictCity") 
public class SysDictCityController {
	@Autowired
	private SysDictCityService sysDictCityService;
	
	/* 查询省市区信息 */
	@RequestMapping(path ="/{level}/{code}",method = RequestMethod.GET)
	@ApiOperation(value = "查询省市区相关信息", notes ="查询省市区相关信息")
	public BaseResponse queryDictCity(@PathVariable("level") int level,@PathVariable("code") int code) {
		try {
			Assert.isNull(code, "code不能为空!");
			Assert.isNull(level, "level不能为空!");
			List<SysDictCity> dictCity = sysDictCityService.queryDictCity(level,code);
			return BaseResponse.getSuccessResponse(dictCity);
		} catch (Exception e) {
			log.error("查询省市区相关信息失败!", e);
			return BaseResponse.getFailResponse("查询省市区相关信息失败!");
		}
	}
}
