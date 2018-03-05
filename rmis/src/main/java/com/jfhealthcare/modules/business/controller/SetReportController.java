package com.jfhealthcare.modules.business.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.base.PageResponse;
import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.common.validator.ValidatorUtils;
import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.common.validator.group.Insert;
import com.jfhealthcare.modules.business.request.SetReportRequest;
import com.jfhealthcare.modules.business.response.SetReportResponse;
import com.jfhealthcare.modules.business.service.SetReportService;
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
@Api(value = "a知识库->知识库管理api")
@RestController
@RequestMapping("/v2/rmis/sysop/setReport")
public class SetReportController {
	
	@Autowired
	private SetReportService setReportService;
	
	
	/* 查询所有分组信息 */
	@RequestMapping(method = RequestMethod.GET,path="/{isPublic}")
	@ApiOperation(value = "知识库目录查询", notes = "识库目录查询{ture:0  公共,false:1  私有}")
	public BaseResponse querySetReport(@PathVariable("isPublic")String isPublic,@ApiIgnore @LoginUser LoginUserEntity loginUserEntity) {
		try {
			List<SetReportResponse> querySetReportWithArmarium = setReportService.querySetReportWithArmarium(isPublic,loginUserEntity);
			
			return BaseResponse.getSuccessResponse(querySetReportWithArmarium);
		}catch (Exception e) {
			log.error("知识库目录查询失败!", e);
			return BaseResponse.getFailResponse("识库目录查询失败!");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET,path="/{isPublic}/{repId}")
	@ApiOperation(value = "知识库文本查询", notes = "知识库文本查询详情{ture:0 公共,false:1 私有}")
	public BaseResponse querySetReportByRepId(@PathVariable("isPublic")String isPublic,@PathVariable("repId")String repId) {
		try {
			Assert.isBlank(repId, "知识库ID不能为空！");
			SetReportResponse setReportResponse=setReportService.querySetReportByRepId(isPublic,repId);
			return BaseResponse.getSuccessResponse(setReportResponse);
		}catch (Exception e) {
			log.error("知识库文本查询失败!", e);
			return BaseResponse.getFailResponse("知识库文本查询失败!");
		}
	}

	/* 新增组 */
	@SysLogAop("新增知识库目录")
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "新增知识库目录", notes = "新增知识库目录详情")
	public BaseResponse saveSetReport(@RequestBody SetReportRequest setReportRequest) {
		try {
			ValidatorUtils.validateEntity(setReportRequest,Insert.class);
			setReportService.saveSetReport(setReportRequest);
			return BaseResponse.getSuccessResponse();
		}catch (Exception e) {
			log.error("新增知识库目录失败!", e);
			return BaseResponse.getFailResponse("新增知识库目录失败!");
		}
	}
	
	
	@SysLogAop("删除知识库目录")
	@RequestMapping(path = "/{isPublic}/{repId}", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除知识库目录", notes = "删除知识库目录详情")
	public BaseResponse deleteSetReport(@PathVariable("isPublic")String isPublic,@PathVariable("repId")String repId) {
		try {
			Assert.isBlank(isPublic, "公共私有判断不能为空!");
			Assert.isBlank(repId, "知识库ID不能为空!");
			setReportService.deleteSetReport(isPublic,repId);
			return BaseResponse.getSuccessResponse();
		}catch (Exception e) {
			log.error("删除知识库目录失败!", e);
			return BaseResponse.getFailResponse("删除知识库目录失败!");
		}
	}

	@SysLogAop("修改知识库目录")
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "修改知识库目录", notes = "修改知识库目录详情")
	public BaseResponse updateSetReport(@RequestBody SetReportRequest setReportRequest) {
		try {
//			ValidatorUtils.validateEntity(setReportRequest, Edit.class);
			setReportService.updateSetReport(setReportRequest);
			return BaseResponse.getSuccessResponse();
		}catch (Exception e) {
			log.error("修改知识库目录失败!", e);
			return BaseResponse.getFailResponse("修改知识库目录失败!");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET,path="/upper/{isPublic}")
	@ApiOperation(value = "知识库文件夹目录查询", notes = "知识库文件夹目录查询{ture:0  公共,false:1  私有}")
	public BaseResponse queryUpperForSetReport(@PathVariable("isPublic")String isPublic,@ApiIgnore @LoginUser LoginUserEntity loginUserEntity) {
		try {
			List<?> setReportResponses=setReportService.queryUpperForSetReport(isPublic,loginUserEntity);
			return BaseResponse.getSuccessResponse(setReportResponses);
		}catch (Exception e) {
			log.error("知识库文件夹目录查询失败!", e);
			return BaseResponse.getFailResponse("知识库文件夹目录查询失败!");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET,path="/nindex/{isPublic}/{repId}")
	@ApiOperation(value = "知识库排序查询", notes = "知识库排序查询{ture:0  公共,false:1  私有}")
	public BaseResponse queryNindexForSetReport(@PathVariable("isPublic")String isPublic,@PathVariable("repId")String repId,@ApiIgnore @LoginUser LoginUserEntity loginUserEntity) {
		try {
			String nindex=setReportService.queryNindexForSetReport(isPublic,repId,loginUserEntity);
			return BaseResponse.getSuccessResponse((Object)nindex);
		}catch (Exception e) {
			log.error("知识库文件夹目录查询失败!", e);
			return BaseResponse.getFailResponse("知识库文件夹目录查询失败!");
		}
	}
}
