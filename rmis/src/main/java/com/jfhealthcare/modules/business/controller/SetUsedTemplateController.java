package com.jfhealthcare.modules.business.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.base.PageResponse;
import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.common.validator.ValidatorUtils;
import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.modules.business.entity.SetUsedtemplate;
import com.jfhealthcare.modules.business.request.SetUsedTemplateRequest;
import com.jfhealthcare.modules.business.service.SetUsedTemplateService;
import com.jfhealthcare.modules.system.annotation.LoginUser;
import com.jfhealthcare.modules.system.annotation.SysLogAop;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author xujinma
 */
@Slf4j
@Api(value = "a知识库->常用语管理api")
@RestController
@RequestMapping("/v2/rmis/sysop/setUsedTemplate")
public class SetUsedTemplateController {
	
	@Autowired
	private SetUsedTemplateService setUsedTemplateService;
	
	
	/* 查询所有分组信息 */
	@RequestMapping(method = RequestMethod.POST,path="/all")
	@ApiOperation(value = "常用语条件查询", notes = "常用语条件查询详情")
	public BaseResponse querySetUsedTemplate(@RequestBody SetUsedTemplateRequest setUsedTemplateRequest) {
		try {
			PageInfo<SetUsedtemplate> setUsedTemplates=setUsedTemplateService.querySetUsedTemplateService(setUsedTemplateRequest);
			return PageResponse.getSuccessPage(setUsedTemplates);
		}catch (Exception e) {
			log.error("常用语条件查询失败!", e);
			return PageResponse.getFailResponse("常用语条件查询失败!");
		}
	}

	@RequestMapping(method = RequestMethod.GET,path="/{usedtempId}")
	@ApiOperation(value = "常用语单个查询", notes = "常用语单个查询详情")
	public BaseResponse querySetUsedTemplate(@PathVariable("usedtempId")String usedtempId) {
		try {
			SetUsedtemplate setUsedtemplate=setUsedTemplateService.querySetUsedTemplateById(usedtempId);
			return BaseResponse.getSuccessResponse(setUsedtemplate);
		}catch (Exception e) {
			log.error("常用语查询失败!", e);
			return BaseResponse.getFailResponse("常用语查询失败!");
		}
	}

	@SysLogAop("常用语新增")
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "常用语新增", notes = "常用语新增详情")
	public BaseResponse saveSetUsedTemplate(@RequestBody SetUsedTemplateRequest setUsedTemplateRequest) {
		try {
			setUsedTemplateService.insertSetUsedTemplate(setUsedTemplateRequest);
			return BaseResponse.getSuccessResponse();
		}catch (Exception e) {
			log.error("新增知识库目录失败!", e);
			return BaseResponse.getFailResponse("新增知识库目录失败!");
		}
	}
	
	
	@SysLogAop("常用语删除")
	@RequestMapping(path = "/{usedtempId}", method = RequestMethod.DELETE)
	@ApiOperation(value = "常用语删除", notes = "常用语删除详情")
	public BaseResponse deleteSetUsedTemplate(@PathVariable("usedtempId")String usedtempId) {
		try {
			Assert.isBlank(usedtempId, "常用语ID不能为空!");
			setUsedTemplateService.deleteSetUsedTemplate(usedtempId);
			return BaseResponse.getSuccessResponse();
		}catch (Exception e) {
			log.error("常用语删除失败!", e);
			return BaseResponse.getFailResponse("常用语删除失败!");
		}
	}

	@SysLogAop("常用语修改")
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "常用语修改", notes = "常用语修改详情")
	public BaseResponse updateSetUsedTemplate(@RequestBody SetUsedTemplateRequest setUsedTemplateRequest) {
		try {
			ValidatorUtils.validateEntity(setUsedTemplateRequest, Edit.class);
			setUsedTemplateService.updateSetUsedTemplate(setUsedTemplateRequest);
			return BaseResponse.getSuccessResponse();
		}catch (Exception e) {
			log.error("修改知识库目录失败!", e);
			return BaseResponse.getFailResponse("修改知识库目录失败!");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET,path="/nindex")
	@ApiOperation(value = "常用语排序查询", notes = "常用语排序查询")
	public BaseResponse queryNindexForSetUsedTemplate() {
		try {
			String nindex=setUsedTemplateService.queryNindexForSetUsedTemplate();
			return BaseResponse.getSuccessResponse((Object)nindex);
		}catch (Exception e) {
			log.error("常用语排序查询失败!", e);
			return BaseResponse.getFailResponse("常用语排序查询失败!");
		}
	}
}
