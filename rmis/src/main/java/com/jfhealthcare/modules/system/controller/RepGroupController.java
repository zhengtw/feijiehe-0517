package com.jfhealthcare.modules.system.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.base.PageResponse;
import com.jfhealthcare.common.enums.RedisEnum;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.common.utils.NameUtils;
import com.jfhealthcare.common.utils.RedisUtils;
import com.jfhealthcare.common.utils.TransferUtils;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.common.validator.ValidatorUtils;
import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.modules.system.annotation.SysLogAop;
import com.jfhealthcare.modules.system.entity.RepGroup;
import com.jfhealthcare.modules.system.entity.SysRepGroup;
import com.jfhealthcare.modules.system.request.RepGroupRequest;
import com.jfhealthcare.modules.system.request.SysRepGroupRequest;
import com.jfhealthcare.modules.system.service.RepGroupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jianglinyan
 * @date 2017年9月25日下午3:16:58
 */
@Slf4j
@Api(value = "a用户->报告分组接口api")
@RestController
@RequestMapping("/v2/rmis/sysop/repGroup")
public class RepGroupController {
	@Autowired
	private RepGroupService repGroupService;
	@Autowired
	private RedisUtils redisUtils;

	/* 查询所有分组信息 */
	@RequestMapping(method = RequestMethod.POST,path="/all")
	@ApiOperation(value = "条件查询查询分组详情", notes = "条件查询查询分组大类详情")
	public BaseResponse queryRepGroup(@RequestBody RepGroupRequest repGroupRequest) {
		try {
			PageInfo<RepGroup> queryRepGroup = repGroupService.queryRepGroup(repGroupRequest);
			return PageResponse.getSuccessPage(queryRepGroup);
		}catch (Exception e) {
			log.error("查询分组失败!", e);
			return PageResponse.getFailResponse("查询分组失败!");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "条件查询查询分组详情", notes = "条件查询查询分组大类详情")
	public BaseResponse queryRepGroupAll() {
		try {
			List<RepGroup> reps=repGroupService.queryRepGroupAll();
			return BaseResponse.getSuccessResponse(reps);
		}catch (Exception e) {
			log.error("查询分组失败!", e);
			return BaseResponse.getFailResponse("查询分组失败!");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET,path="/nindex")
	@ApiOperation(value = "查询最大nindex", notes = "查询最大nindex详情")
	public BaseResponse queryMaxNindex() {
		try {
			String nindex=repGroupService.queryMaxNindex();
			return BaseResponse.getSuccessResponse((Object)nindex);
		}catch (Exception e) {
			log.error("查询分组失败!", e);
			return BaseResponse.getFailResponse("查询分组失败!");
		}
	}

	@SysLogAop("新增报告分组")
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "新增报告分组", notes = "新增报告分组")
	public BaseResponse saveRepGroup(@RequestBody RepGroup repGroup) {
		try {
			repGroup.setCrtTime(new Date());
			repGroup.setCrtUser(NameUtils.getLoginCode());
			repGroupService.saveRepGroup(repGroup);
			redisUtils.delete(RedisEnum.REPGROUP.getValue()+":nancang");
			return BaseResponse.getSuccessResponse();
		}catch (RmisException e) {
			log.error(e.getMessage(), e);
			return BaseResponse.getFailResponse(e.getMessage());
		}catch (Exception e) {
			log.error("新增分组失败!", e);
			return BaseResponse.getFailResponse("新增分组失败!");
		}
	}
	
	
	@SysLogAop("删除报告分组")
	@RequestMapping(path = "/{groupId}", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除报告分组", notes = "删除报告分组详情")
	public BaseResponse deleteRepGroup(@PathVariable String groupId) {
		try {
			Assert.isBlank(groupId, "ids不能为空!");
			redisUtils.delete(RedisEnum.REPGROUP.getValue()+":nancang");
			repGroupService.delectRepGroup(groupId);
			return BaseResponse.getSuccessResponse();
		}catch (Exception e) {
			log.error("删除报告分组失败!", e);
			return BaseResponse.getFailResponse("删除报告分组失败!");
		}
	}

	/* 修改组信息 */
	@SysLogAop("修改报告分组")
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "修改报告分组", notes = "修改报告分组")
	public BaseResponse updateRepGroup(@RequestBody RepGroupRequest repGroupRequest) {
		try {
			ValidatorUtils.validateEntity(repGroupRequest, Edit.class);
			RepGroup re = new RepGroup();
			TransferUtils.copyPropertiesIgnoreNull(repGroupRequest, re);
			redisUtils.delete(RedisEnum.REPGROUP.getValue()+":nancang");
			repGroupService.updateRepGroup(re);
			return BaseResponse.getSuccessResponse();
		}catch (RmisException e) {
			log.error(e.getMessage(), e);
			return BaseResponse.getFailResponse(e.getMessage());
		}catch (Exception e) {
			log.error("修改分组失败!", e);
			return BaseResponse.getFailResponse("修改分组失败!");
		}
	}
	
	@RequestMapping(path = "/logincode/{groupId}", method = RequestMethod.GET)
	@ApiOperation(value = "根据分组查询分组人员", notes = "根据分组查询分组人员详情")
	public BaseResponse queryRepGroupLogincodeById(@PathVariable String groupId) {
		try {
			Assert.isBlank(groupId, "groupId不能为空!");
			List<SysRepGroup> srgs=repGroupService.queryLoginCodeByGroupId(groupId);
			return BaseResponse.getSuccessResponse(srgs);
		}catch (Exception e) {
			log.error("根据分组查询分组人员失败!", e);
			return BaseResponse.getFailResponse("根据分组查询分组人员失败!");
		}
	}
	
	@RequestMapping(path = "/{groupId}", method = RequestMethod.GET)
	@ApiOperation(value = "查询单个分组", notes = "查询单个分组详情")
	public BaseResponse queryRepGroupById(@PathVariable String groupId) {
		try {
			Assert.isBlank(groupId, "groupId不能为空!");
			RepGroup repGroup=repGroupService.queryRepGroupByGroupId(groupId);
			return BaseResponse.getSuccessResponse(repGroup);
		}catch (Exception e) {
			log.error("查询单个分组失败!", e);
			return BaseResponse.getFailResponse("查询单个分组失败!");
		}
	}
	
	/* 修改组信息 */
	@SysLogAop("新增分组人员")
	@RequestMapping(method = RequestMethod.PUT,path="/logincode")
	@ApiOperation(value = "新增分组人员", notes = "新增分组人员详情")
	public BaseResponse insertRepGroup(@RequestBody SysRepGroupRequest sysRepGroupRequest) {
		try {
			ValidatorUtils.validateEntity(sysRepGroupRequest, Edit.class);
			repGroupService.updateSysRepGroup(sysRepGroupRequest);
			return BaseResponse.getSuccessResponse();
		}catch (Exception e) {
			log.error("修改分组人员失败!", e);
			return BaseResponse.getFailResponse("修改分组人员失败!");
		}
	}
	
	@SysLogAop("删除分组人员")
	@RequestMapping(method = RequestMethod.DELETE,path="/{groupId}/{logincodes}")
	@ApiOperation(value = "删除分组人员", notes = "删除分组人员详情")
	public BaseResponse deleteRepGroupWithLogincode(@PathVariable("groupId")String groupId,@PathVariable("logincodes")String logincodes) {
		try {
			Assert.isBlank(groupId, "分组Id不能为空");
			Assert.isBlank(logincodes, "账号不能为空");
			repGroupService.deleteRepGroup(groupId,logincodes);
			return BaseResponse.getSuccessResponse();
		}catch (Exception e) {
			log.error("修改分组人员失败!", e);
			return BaseResponse.getFailResponse("修改分组人员失败!");
		}
	}
}
