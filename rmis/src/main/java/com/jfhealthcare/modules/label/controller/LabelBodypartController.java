package com.jfhealthcare.modules.label.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.modules.label.request.LabelBodypartRequest;
import com.jfhealthcare.modules.label.service.LabelBodypartService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v2/rmis/sysop/labelBodypart")
@Slf4j
@Api(value = "a-label->labelBodypart api")
public class LabelBodypartController {
	@Autowired
	private LabelBodypartService labelBodypartService;

	/**
	 * 标注和部位修改
	 * */
	@ApiOperation(value = "标注和部位关系修改", notes = "只需要传入用户最新修改的已选ID!")
	@RequestMapping(method=RequestMethod.POST)
	public BaseResponse updateBodypart(@RequestBody LabelBodypartRequest labelbrequest) {
		try {
			Assert.isBlank(labelbrequest.getLabelAccnum(), "labelAccnum不能为空!");
			labelBodypartService.updateLabelBodypart(labelbrequest.getLabelAccnum(),labelbrequest.getBodyCodes());
			return BaseResponse.getSuccessResponse();
		} catch (Exception e) {
			log.error("标注部位关系更新失败!", e);
			return BaseResponse.getFailResponse("标注部位关系更新失败!");
		}
	}
	
	/**
	 * 标注和部位新增
	 * */
	@ApiOperation(value = "标注和部位关系新增", notes = "只需要传入新增ID,支持批量操作.")
	@RequestMapping(method=RequestMethod.PUT)
	public BaseResponse insertBodypart(@RequestBody LabelBodypartRequest labelbrequest) {
		try {
			Assert.isBlank(labelbrequest.getLabelAccnum(), "labelAccnum不能为空!");
			Assert.isBlank(labelbrequest.getBodyCodes(), "bodyCodes不能为空!");
			labelBodypartService.insertLabelBodypart(labelbrequest.getLabelAccnum(),labelbrequest.getBodyCodes());
			return BaseResponse.getSuccessResponse();
		} catch (Exception e) {
			log.error("标注部位关系新增失败!", e);
			return BaseResponse.getFailResponse("标注部位关系新增失败!");
		}
	}
	/**
	 * 标注和部位删除
	 * */
	
	@ApiOperation(value = "标注和部位关系删除", notes = "只需要传入删除ID,支持批量操作.ID之间用逗号相隔")
	@RequestMapping(method=RequestMethod.DELETE,path="/{ids}")
	public BaseResponse deleteBodypart(@PathVariable(value="ids") String ids) {
		try {
			Assert.isBlank(ids, "ids不能为空!");
			labelBodypartService.deleteLabelBodypart(ids);
			return BaseResponse.getSuccessResponse();
		} catch (Exception e) {
			log.error("标注部位关系删除失败!", e);
			return BaseResponse.getFailResponse("标注部位关系删除失败!");
		}
	}
}
