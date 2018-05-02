package com.jfhealthcare.modules.label.controller;

import java.util.Date;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.common.enums.LabelStatusEnum;
import com.jfhealthcare.modules.label.entity.LabelInfolist;
import com.jfhealthcare.modules.label.service.LabelInfolistService;
import com.jfhealthcare.modules.system.annotation.LoginUser;
import com.jfhealthcare.modules.system.entity.SysDictDtl;
import com.jfhealthcare.modules.system.service.SysDictDtlService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Api(value = "a-label->labelInfolist api")
@RestController
@RequestMapping("/v2/rmis/sysop/labelInfolist")
public class LabelInfolistController {
	@Autowired
	private LabelInfolistService labelInfolistService;
	@Autowired
	private SysDictDtlService sysDictDtlService;

	@ApiOperation(value = "修改标注列表", notes = "修改标注列表接口")
	@RequestMapping(method = RequestMethod.POST, path = "/{labelAccnum}/{statusCode}")
	public BaseResponse updateStatus(@PathVariable(value = "labelAccnum") String labelAccnum,
			@PathVariable(value = "statusCode") String statusCode,@ApiIgnore @LoginUser LoginUserEntity loginUserEntity) {
		try {
			LabelInfolist labelInfolist = labelInfolistService.queryById(labelAccnum);
			if(labelInfolist == null){
				return BaseResponse.getFailResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR,"标注列表不存在!");
			}
			//已审核状态的标注不给修改 	TODO 和前段商量
			if(!LabelStatusEnum.LABEL_CHECKED.getStatusCode().equals(labelInfolist.getStatusCode())){
				if(LabelStatusEnum.IN_LABEL.getStatusCode().equals(labelInfolist.getStatusCode())&&!loginUserEntity.getSysOperator().getLogincode().equals(labelInfolist.getUpdUser())){
					return BaseResponse.getFailResponse(HttpStatus.SC_ACCEPTED, "有医生正在标注中,请稍后!");
				}
				if(LabelStatusEnum.CHECKING_LABEL.getStatusCode().equals(labelInfolist.getStatusCode())&&!loginUserEntity.getSysOperator().getLogincode().equals(labelInfolist.getUpdUser())){
					return BaseResponse.getFailResponse(HttpStatus.SC_ACCEPTED, "有医生正在审核中,请稍后!");
				}
				String oldStatusCode = labelInfolist.getStatusCode();
				labelInfolist.setStatusCode(statusCode);
				SysDictDtl queryDictDtlById = sysDictDtlService.queryDictDtlById(statusCode);
				labelInfolist.setStatus(queryDictDtlById.getOthervalue());
				labelInfolist.setUpdTime(new Date());
				labelInfolist.setUpdUser(loginUserEntity.getSysOperator().getName());
				labelInfolistService.updateStatus(labelInfolist,oldStatusCode);
			}
			
			return BaseResponse.getSuccessResponse();
		} catch (Exception e) {
			log.error("修改标注列表失败!", e);
			return BaseResponse.getFailResponse("修改标注列表失败!");
		}
	}
}
