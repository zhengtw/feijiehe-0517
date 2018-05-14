package com.jfhealthcare.modules.label.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.utils.TransferUtils;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.common.validator.ValidatorUtils;
import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.common.validator.group.Insert;
import com.jfhealthcare.modules.label.entity.LabelDisease;
import com.jfhealthcare.modules.label.request.LabelDiseaseRequest;
import com.jfhealthcare.modules.label.service.LabelDiseaseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v2/rmis/sysop/labelDisease")
@Slf4j
@Api(value = "a-label->labelDisease api")
public class LabelDiseaseController {
	@Autowired
	private LabelDiseaseService labelDiseaseService;

	/**
	 * 标注和疾病修改
	 */
	@ApiOperation(value = "标注和疾病关系修改", notes = "标注和疾病关系修改")
	@RequestMapping(method = RequestMethod.POST)
	public BaseResponse updateDisease(@RequestBody LabelDiseaseRequest labeldrequest) {
		try {
			ValidatorUtils.validateEntity(labeldrequest, Edit.class);
			//单条修改
			LabelDisease labelDisease = new LabelDisease();
			TransferUtils.copyProperties(labeldrequest, labelDisease);
			labelDiseaseService.updateLabelDisease(labelDisease);
			return BaseResponse.getSuccessResponse();
		} catch (Exception e) {
			log.error("标注疾病关系更新失败!", e);
			return BaseResponse.getFailResponse("标注疾病关系更新失败!");
		}
	}

	/**
	 * 标注和疾病新增
	 */
	@ApiOperation(value = "标注和疾病关系新增", notes = "标注和疾病关系新增!")
	@RequestMapping(method = RequestMethod.PUT)
	public BaseResponse insertDisease(@RequestBody LabelDiseaseRequest labeldrequest) {
		try {
			ValidatorUtils.validateEntity(labeldrequest, Insert.class);
			LabelDisease labelDisease = new LabelDisease();
			TransferUtils.copyProperties(labeldrequest, labelDisease);
			labelDiseaseService.insertLabelDisease(labelDisease);
			return BaseResponse.getSuccessResponse();
		} catch (Exception e) {
			log.error("标注疾病关系新增失败!", e);
			return BaseResponse.getFailResponse("标注疾病关系新增失败!");
		}
	}

	/**
	 * 标注和疾病删除
	 */

	@ApiOperation(value = "标注和疾病关系删除", notes = "只需要传入删除ID,支持批量操作.用逗号隔开ID")
	@RequestMapping(method = RequestMethod.DELETE,path="/{ids}")
	public BaseResponse deleteDisease(@PathVariable(value="ids") String ids) {
		try {
			Assert.isBlank(ids, "ids不能为空!");
			labelDiseaseService.deleteLabelDisease(ids);
			return BaseResponse.getSuccessResponse();
		} catch (Exception e) {
			log.error("标注疾病关系删除失败!", e);
			return BaseResponse.getFailResponse("标注疾病关系删除失败!");
		}
	}
}
