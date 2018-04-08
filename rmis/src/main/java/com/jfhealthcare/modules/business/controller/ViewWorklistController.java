package com.jfhealthcare.modules.business.controller;

import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.base.PageResponse;
import com.jfhealthcare.common.base.ValueResponse;
import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.common.utils.HttpClientUtils;
import com.jfhealthcare.common.validator.ValidatorUtils;
import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.modules.business.entity.AiCheckEntity;
import com.jfhealthcare.modules.business.entity.RepImage;
import com.jfhealthcare.modules.business.request.CheckApiRequest;
import com.jfhealthcare.modules.business.request.ViewWorklistRequest;
import com.jfhealthcare.modules.business.response.CheckApiResponse;
import com.jfhealthcare.modules.business.response.ViewWorklistResponse;
import com.jfhealthcare.modules.business.service.ViewWorklistService;
import com.jfhealthcare.modules.system.annotation.LoginUser;
import com.jfhealthcare.modules.system.annotation.SysLogAop;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xujinma
 */
@Slf4j
@Api(value = "a-work->worklist api")
@RestController
@RequestMapping("/v2/rmis/sysop/worklist")
public class ViewWorklistController {
	@Value("${ai.checkapi.host}")
	private String checkApi; 
	
	@Value("${ai.studyapi.host}")
	private String studyApi; 
	
	@Value("${dcm.image.url}")
	private String dcmImageUrl; 
	
	@Autowired
	private ViewWorklistService viewWorklistService;
	
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "worklist查询", notes = "worklist查询详情")
	public BaseResponse queryViewWorklist(@RequestBody ViewWorklistRequest viewWorklistRequest) {
		try {
			PageInfo<ViewWorklistResponse> vwls=viewWorklistService.queryViewWorklist(viewWorklistRequest);
			return PageResponse.getSuccessResponse(vwls);
		}catch (Exception e) {
			log.error("worklist查询!", e);
			return PageResponse.getFailResponse("worklist查询失败!");
		}
	}
	
	@RequestMapping(method = RequestMethod.POST,path="/count")
	@ApiOperation(value = "worklist数量统计查询", notes = "worklist数量统计查询详情")
	public BaseResponse queryCountViewWorklist(@RequestBody ViewWorklistRequest viewWorklistRequest) {
		try {
			ViewWorklistResponse viewWorklistResponse=viewWorklistService.queryCountViewWorklist(viewWorklistRequest);
			return BaseResponse.getSuccessResponse(viewWorklistResponse);
		}catch (Exception e) {
			log.error("worklist数量统计查询!", e);
			return BaseResponse.getFailResponse("worklist数量统计查询失败!");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET,path="/{checkAccessionNum}")
	@ApiOperation(value = "worklist单个查询", notes = "worklist单个查询详情")
	public BaseResponse queryOneViewWorklist(@PathVariable("checkAccessionNum")String checkAccessionNum) {
		try {
			ViewWorklistResponse viewWorklistResponse=viewWorklistService.queryOneViewWorklist(checkAccessionNum);
			return BaseResponse.getSuccessResponse(viewWorklistResponse);
		}catch (Exception e) {
			log.error("worklist单个查询!", e);
			return BaseResponse.getFailResponse("worklist单个查询失败!");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET,path="/repimage/{repUid}")
	@ApiOperation(value = "worklist查询报告贴图", notes = "worklist查询报告贴图详情")
	public BaseResponse queryRepImageByRepUid(@PathVariable("repUid")String repUid) {
		try {
			List<RepImage> repimages=viewWorklistService.queryRepImageByRepUid(repUid);
			return ValueResponse.getSuccessValue(repimages,dcmImageUrl);
		}catch (Exception e) {
			log.error("worklist查询报告贴图失败!", e);
			return BaseResponse.getFailResponse("worklist查询报告贴图失败!");
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE,path="/repimage/{repImageId}")
	@ApiOperation(value = "worklist删除报告贴图", notes = "worklist删除报告贴图详情")
	public BaseResponse deleteRepImageByRepImageId(@PathVariable("repImageId")String repImageId) {
		try {
			viewWorklistService.deleteRepImageByRepImageId(repImageId);
			return BaseResponse.getSuccessResponse("worklist删除报告贴图成功!");
		}catch (Exception e) {
			log.error("worklist删除报告贴图失败!", e);
			return BaseResponse.getFailResponse("worklist删除报告贴图失败!");
		}
	}
	
	@SysLogAop("更新报告")
	@ApiOperation(value = "更新报告", notes = "更新报告详情")
	@RequestMapping(method = RequestMethod.POST,path="/report")
	public BaseResponse updateCheckListIndex(@RequestBody ViewWorklistRequest viewWorklistRequest,@LoginUser LoginUserEntity loginUserEntity) {
		try {
			
			log.info(loginUserEntity.getSysOperator().getLogincode()+"：处理报告开始..........");
			ValidatorUtils.validateEntity(viewWorklistRequest, Edit.class);
			viewWorklistService.updateCheckListIndex(viewWorklistRequest,loginUserEntity);
			log.info(loginUserEntity.getSysOperator().getLogincode()+"：处理报告结束.........");
			return BaseResponse.getSuccessResponse();
		} catch (RmisException e) {
			log.error(e.getMessage(), e);
			return BaseResponse.getFailResponse(e.getMessage());
		}catch (Exception e) {
			log.error("更新报告失败!", e);
			return BaseResponse.getFailResponse("更新报告失败!");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET,path="/isRemind")
	@ApiOperation(value = "worklist报告是否有需要处理的", notes = "worklist报告是否有需要处理的详情 ：{1：要处理，0：不需要处理}")
	public BaseResponse queryViewWorklistIsRemind() {
		try {
			int isRemind=viewWorklistService.queryViewWorklistIsRemind();
			return BaseResponse.getSuccessResponse(isRemind);
		}catch (Exception e) {
			log.error("worklist报告是否有需要处理的失败!", e);
			return BaseResponse.getFailResponse("worklist报告是否有需要处理的失败!");
		}
	}
	
	@RequestMapping(method = RequestMethod.POST,path="/checkapi")
	@ApiOperation(value = "报告纠错", notes = "报告纠错详情")
	public BaseResponse checkApiViewWorklist(@RequestBody CheckApiRequest checkApiRequest) {
		try {
			CheckApiResponse car=new CheckApiResponse();
			
			HttpClientUtils instance = HttpClientUtils.getInstance();
			String findurl =checkApi+URLEncoder.encode(checkApiRequest.getFinding(),"UTF-8");
			String impressionurl =checkApi+URLEncoder.encode(checkApiRequest.getImpression(),"UTF-8");
			String findGet =null;
			String impressionGet =null;
			try {
				 findGet = instance.httpGetByWaitTime(findurl, 10000, 20000);
			} catch (Exception e) {
				log.error("影像所见异常!", e);
			}
			try {
				impressionGet = instance.httpGetByWaitTime(impressionurl, 10000, 20000);
			} catch (Exception e) {
				log.error("诊断建议异常!", e);
			}
			if(StringUtils.isNoneBlank(findGet)){
				AiCheckEntity findObject = JSON.parseObject(findGet, AiCheckEntity.class);
				car.setFind_has_error(findObject.getHas_error());
				car.setFinding(findObject.getResult());
		    }else{
		    	car.setFind_has_error(Boolean.FALSE);
		    }
			if(StringUtils.isNoneBlank(impressionGet)){
				AiCheckEntity impressionObject = JSON.parseObject(impressionGet, AiCheckEntity.class);
				car.setImpress_has_error(impressionObject.getHas_error());
				car.setImpression(impressionObject.getResult());
			}else{
				car.setImpress_has_error(Boolean.FALSE);
			}
			return BaseResponse.getSuccessResponse(car);
		}catch (Exception e) {
			log.error("报告纠错异常!", e);
			return BaseResponse.getFailResponse("报告纠错异常!");
		}
	}
	
	@RequestMapping(method = RequestMethod.POST,path="/studyapi")
	@ApiOperation(value = "报告学习", notes = "报告学习详情")
	public BaseResponse studyApiViewWorklist(@RequestBody CheckApiRequest checkApiRequest) {
		try {
			HttpClientUtils instance = HttpClientUtils.getInstance();
			String findurl =studyApi+URLEncoder.encode(checkApiRequest.getFinding(),"UTF-8");
			String impressionurl =studyApi+URLEncoder.encode(checkApiRequest.getImpression(),"UTF-8");
			try {
				String findGet = instance.httpGetByWaitTime(findurl, 10000, 20000);
			} catch (Exception e) {
				log.error("影像所见学习异常!", e);
			}
			try {
				String impressionGet = instance.httpGetByWaitTime(impressionurl, 10000, 20000);
			} catch (Exception e) {
				log.error("诊断建议学习异常!", e);
			}
			return BaseResponse.getSuccessResponse();
		}catch (Exception e) {
			log.error("报告纠错学习异常!", e);
			return BaseResponse.getFailResponse("报告纠错学习异常!");
		}
	}
}
