package com.jfhealthcare.modules.label.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.base.PageResponse;
import com.jfhealthcare.common.utils.DateUtils;
import com.jfhealthcare.modules.basics.BasicPageEntity;
import com.jfhealthcare.modules.label.entity.ViewLabellist;
import com.jfhealthcare.modules.label.request.ViewLabellistRequest;
import com.jfhealthcare.modules.label.service.ViewLabellistService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(value = "a-label->labelView api")
@RestController
@RequestMapping("/v2/rmis/sysop/labelView")
public class ViewLabellistController {
	@Autowired
	private ViewLabellistService viewLabellistService;

	/**
	 * 分页查询所有
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/all")
	@ApiOperation(value = "labellist查询", notes = "labellist查询详情")
	public BaseResponse queryAllViewLabellist(@RequestBody BasicPageEntity basic) {
		try {
			PageInfo<ViewLabellist> vll = viewLabellistService.queryAllViewLabellist(basic.getPageNum(),
					basic.getPageSize());
			return PageResponse.getSuccessResponse(vll);
		} catch (Exception e) {
			log.error("Labellist查询失败!", e);
			return PageResponse.getFailResponse("Labellist查询失败!");
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "labellist查询", notes = "labellist查询详情")
	public BaseResponse queryViewLabellist(@RequestBody ViewLabellistRequest viewLabellistRequest) {
		try {
			if(viewLabellistRequest.getCheckTime()!=null){
				List<Date> checkTime = DateUtils.getCheckTime(viewLabellistRequest.getCheckTime());
				viewLabellistRequest.setFromLabelDate(checkTime.get(0));
				viewLabellistRequest.setEndLabelDate(checkTime.get(1));
			}
			PageInfo<ViewLabellist> vll = viewLabellistService.queryViewLabellist(viewLabellistRequest);
			return PageResponse.getSuccessResponse(vll);
		} catch (Exception e) {
			log.error("Labellist查询失败!", e);
			return PageResponse.getFailResponse("Labellist查询失败!");
		}
	}
}
