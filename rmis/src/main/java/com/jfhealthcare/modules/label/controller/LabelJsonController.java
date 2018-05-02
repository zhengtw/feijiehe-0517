package com.jfhealthcare.modules.label.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.modules.label.request.LabelJsonRequest;
import com.jfhealthcare.modules.label.service.LabelJsonService;
import com.jfhealthcare.modules.system.annotation.LoginUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Api(value = "a-label->labelJson api")
@RestController
@RequestMapping("/v2/rmis/sysop/labelJson")
public class LabelJsonController {
	@Autowired
	private LabelJsonService labelJsonService;

	/**
	 * 修改标注
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "新增、修改、删除标注", notes = "提供给WEBVIEW使用的新增、修改、标注接口")
	public BaseResponse updateLabelJson(@RequestBody LabelJsonRequest labeljRequest,
			@ApiIgnore @LoginUser LoginUserEntity loginUserEntity) {
		try {
			labelJsonService.updateByParams(labeljRequest,loginUserEntity.getSysOperator().getName());
			return BaseResponse.getSuccessResponse();
		} catch (Exception e) {
			log.error("新增、修改、删除标注信息失败!", e);
			return BaseResponse.getFailResponse("新增、修改、删除标注信息失败!");
		}
	}

}
