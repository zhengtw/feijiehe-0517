package com.jfhealthcare.modules.BI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jfhealthcare.modules.BI.response.RepCountResponse;
import com.jfhealthcare.modules.BI.service.RepCountService;
import com.jfhealthcare.modules.system.annotation.AuthIgnore;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(value = "a统计BI->医疗信息api")
@RestController
@RequestMapping("/v2/rmis/sysop/repCount")
public class RepCountController {
	
	@Autowired 
	private RepCountService repCountService;

	@AuthIgnore
	@RequestMapping(method = RequestMethod.GET,path="/QueryRepCount")
	@ApiOperation(value="BI统计",notes="报告数量")
	public List<RepCountResponse> QueryRepCount()
	{
		List<RepCountResponse> repCount = repCountService.queryRepCount();
		return repCount;
	}
	
}
