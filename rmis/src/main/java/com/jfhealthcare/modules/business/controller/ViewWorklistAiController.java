package com.jfhealthcare.modules.business.controller;

import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.enums.RedisEnum;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.common.utils.HttpClientUtils;
import com.jfhealthcare.common.utils.RedisUtils;
import com.jfhealthcare.modules.business.entity.AiCheckEntity;
import com.jfhealthcare.modules.business.request.CheckApiRequest;
import com.jfhealthcare.modules.business.response.CheckApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xujinma
 */
@Slf4j
@Api(value = "a-work->worklist ai api")
@RestController
@RequestMapping("/v2/rmis/sysop/worklist/ai")
public class ViewWorklistAiController {
	@Value("${ai.checkapi.host}")
	private String checkApi; 
	
	@Value("${ai.studyapi.host}")
	private String studyApi; 
	
	@Autowired
	private RedisUtils redisUtils;
	
	@RequestMapping(method = RequestMethod.GET,path="/{btnStatus}")
	@ApiOperation(value = "ai报告转至对应医师开关", notes = "开关详情：1：打开，0：关闭,2:查询按钮状态")
	public BaseResponse aiBtnStatusViewWorklist(@PathVariable("btnStatus")String btnStatus) {
		try {
			if(StringUtils.isBlank(btnStatus) || !StringUtils.equalsAny(btnStatus, "0","1","2")) {
				throw new RmisException("btnStatus 为空或者不在指定值 0/1/2内");
			}
			
			if(StringUtils.equals("2", btnStatus)) {//查询
				String bs = redisUtils.get(RedisEnum.AIBTNSTATUS.getValue());
			    if(StringUtils.isBlank(bs)) {
			    	bs="0";
			    	redisUtils.set(RedisEnum.AIBTNSTATUS.getValue(), bs, RedisUtils.NOT_EXPIRE);
			    }
			    return BaseResponse.getSuccessResponse(bs);
			}else {
				redisUtils.set(RedisEnum.AIBTNSTATUS.getValue(), btnStatus, RedisUtils.NOT_EXPIRE);
				return BaseResponse.getSuccessResponse();
			}
			
		}catch (Exception e) {
			log.error("ai报告开关异常!", e);
			return BaseResponse.getFailResponse("ai报告开关异常!");
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
