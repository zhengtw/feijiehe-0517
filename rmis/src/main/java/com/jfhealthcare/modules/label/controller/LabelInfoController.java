package com.jfhealthcare.modules.label.controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.common.utils.TransferUtils;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.modules.label.entity.LabelInfo;
import com.jfhealthcare.modules.label.entity.LabelJson;
import com.jfhealthcare.modules.label.request.LabelInfoRequest;
import com.jfhealthcare.modules.label.response.LabelInfoWebViewResponse;
import com.jfhealthcare.modules.label.service.LabelInfoService;
import com.jfhealthcare.modules.system.annotation.LoginUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/v2/rmis/sysop/labelInfo")
@Slf4j
@Api(value = "a-label->labelInfo api")
public class LabelInfoController {
	@Autowired
	private LabelInfoService labelInfoService;

	@ApiOperation(value = "查询标注信息", notes = "提供给WEBVIEW,返回传入的JSON和图像信息")
	@RequestMapping(method = RequestMethod.POST,path="/webView")
	public BaseResponse queryLabelInfoForWebView(@RequestBody LabelInfoRequest labelInfoRequest,
			@ApiIgnore @LoginUser LoginUserEntity loginUserEntity) {
		try {
			Assert.isAnyBlank(labelInfoRequest.getStudyUid(), labelInfoRequest.getCheckNum(),
					labelInfoRequest.getUserId(), "userId,studyUid,checkNum不能为空!");
			LabelInfoWebViewResponse labelResponse = labelInfoService.queryInfoForWebView(labelInfoRequest,
					loginUserEntity.getSysOperator().getAdminCode());
			return BaseResponse.getSuccessResponse(labelResponse);
		} catch (Exception e) {
			log.error("查询标注信息失败!", e);
			return BaseResponse.getFailResponse("查询标注信息失败!");
		}
	}
	
	@ApiOperation(value = "修改标注信息", notes = "修改标注信息")
	@RequestMapping(method = RequestMethod.POST)
	public BaseResponse updateLabelInfo(@RequestBody LabelInfoRequest labelInfoRequest,
			@ApiIgnore @LoginUser LoginUserEntity loginUserEntity) {
		try {
			Assert.isAnyBlank(labelInfoRequest.getId(), "id不能为空!");
			LabelInfo labelInfo = new LabelInfo();
			TransferUtils.copyPropertiesIgnoreNull(labelInfoRequest, labelInfo);
			labelInfo.setUpdTime(new Date());
			labelInfo.setUpdUser(loginUserEntity.getSysOperator().getLogincode());
		    labelInfoService.updateLabelInfo(labelInfo);
			return BaseResponse.getSuccessResponse();
		} catch (Exception e) {
			log.error("修改标注信息失败!", e);
			return BaseResponse.getFailResponse("修改标注信息失败!");
		}
	}
	
	@ApiOperation(value = "查询标注信息", notes = "提供给页面,返回标注的创建人,修改人信息")
	@RequestMapping(method = RequestMethod.POST,path="/web")
	public BaseResponse queryLabelInfoForWeb(@RequestBody LabelInfoRequest labelInfoRequest) {
		try {
			Assert.isBlank(labelInfoRequest.getLabelAccnum(), "labelAccnum不能为空!");
			PageInfo<LabelJson> labelJson= labelInfoService.queryInfoForWeb(labelInfoRequest.getLabelAccnum(),labelInfoRequest.getPageNum(),labelInfoRequest.getPageSize());
			return BaseResponse.getSuccessResponse(labelJson);
		} catch (Exception e) {
			log.error("查询标注信息失败!", e);
			return BaseResponse.getFailResponse("查询标注信息失败!");
		}
	}
}
