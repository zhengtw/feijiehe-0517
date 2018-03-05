package com.jfhealthcare.modules.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.modules.system.entity.SysDictCls;
import com.jfhealthcare.modules.system.service.SysDictClsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jianglinyan
 * @date 2017年9月20日下午2:07:26
 */

@Slf4j
@Api(value = "z字典->字典分类接口api") 
@RestController
@RequestMapping("/v2/rmis/sysop/dictCls") 
public class SysDictClsController {
	@Autowired
	private SysDictClsService sysDictClsService;

	/* 查询所有的字典大类信息 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "查询所有字典大类详情", notes ="查询所有字典大类详情")
	public BaseResponse queryDictCls() {
		try {
			List<SysDictCls> dictCls = sysDictClsService.queryDictCls();
			return BaseResponse.getSuccessResponse(dictCls);
		} catch (Exception e) {
			log.error("查询字典大类失败!", e);
			return BaseResponse.getFailResponse("查询字典大类失败!");
		}
	}
	
	/* 查询单个字典大类信息 */
	@RequestMapping(path="/{id}",method = RequestMethod.GET)
	@ApiOperation(value = "查询单个字典大类详情", notes ="查询单个字典大类详情，查询条件为ID ID写在请求路径中")
	public BaseResponse queryDictClsById(@PathVariable String id) {
		try {
			Assert.isBlank(id, "id不能为空!");
			SysDictCls dictCls = sysDictClsService.queryDictClsById(id);
			return BaseResponse.getSuccessResponse(dictCls);
		} catch (Exception e) {
			log.error("查询单个字典大类失败!", e);
			return BaseResponse.getFailResponse("查询单个字典大类失败!");
		}
	}
}
