package com.jfhealthcare.modules.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.base.PageResponse;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.common.utils.TransferUtils;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.common.validator.ValidatorUtils;
import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.modules.basics.BasicPageEntity;
import com.jfhealthcare.modules.system.annotation.SysLogAop;
import com.jfhealthcare.modules.system.entity.SysRole;
import com.jfhealthcare.modules.system.request.SysRoleRequest;
import com.jfhealthcare.modules.system.service.SysRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jianglinyan
 * @date 2017年9月25日下午5:31:09
 */
@Slf4j
@Api(value = "a角色->角色接口api")
@RestController
@RequestMapping("/v2/rmis/sysop/sysRole")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;

	/* 查询所有角色信息 */
	@RequestMapping(path = "/all", method = RequestMethod.POST)
	@ApiOperation(value = "查询所有角色详情", notes = "查询所有角色大类详情")
	public BaseResponse querySysRole(@RequestBody BasicPageEntity basic) {
		try {
			PageInfo<SysRole> sysRole = sysRoleService.querySysRole(basic.getPageNum(), basic.getPageSize());
			return PageResponse.getSuccessPage(sysRole);
		} catch (RmisException rmis) {
			log.info("查询角色失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("查询角色失败!", e);
			return BaseResponse.getFailResponse("查询角色失败!");
		}
	}

	/* 条件查询角色信息 */
	@RequestMapping(path = "/one", method = RequestMethod.POST)
	@ApiOperation(value = "条件查询角色信息 ", notes = "条件查询角色信息 ")
	public BaseResponse querySysRoleByParameter(@RequestBody SysRoleRequest sysRoleRequest) {
		try {
			SysRole sys = new SysRole();
			TransferUtils.copyPropertiesIgnoreNull(sysRoleRequest, sys);
			PageInfo<SysRole> sysRole = sysRoleService.querySysRoleByParameter(sys, sysRoleRequest.getPageNum(),
					sysRoleRequest.getPageSize());
			return PageResponse.getSuccessPage(sysRole);
		} catch (RmisException rmis) {
			log.info("查询单个角色失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("查询单个角色失败!", e);
			return BaseResponse.getFailResponse("查询单个角色失败!");
		}
	}
	
	/* 查询单个角色信息 */
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "查询单个角色信息 ", notes = "查询单个角色信息 ")
	public BaseResponse c(@PathVariable String id) {
		try {
			SysRole sysRole = sysRoleService.querySysRoleById(id);
			return BaseResponse.getSuccessResponse(sysRole);
		} catch (RmisException rmis) {
			log.info("查询单个角色失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("查询单个角色失败!", e);
			return BaseResponse.getFailResponse("查询单个角色失败!");
		}
	}

	/* 批量删除角色 同时将表和人员关系表中的 相关数据的角色ID设置为空 */
	@SysLogAop("删除角色")
	@RequestMapping(path = "/{ids}", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除角色", notes = "删除角色，并会将关联的角色号变为空，需要删除的IDs 放到路径中如：/v2/repGroup/1,2,3")
	public BaseResponse deleteSysRole(@PathVariable String ids) {
		try {
			Assert.isBlank(ids, "ids不能为空!");
			String[] split = ids.split(",");
			sysRoleService.delectSysRole(split);
			return BaseResponse.getSuccessResponse();
		} catch (RmisException rmis) {
			log.info("批量删除角色失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("批量删除角色失败!", e);
			return BaseResponse.getFailResponse("批量删除角色失败!");
		}
	}

	/* 新增角色 */
	@SysLogAop("新增角色")
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "新增角色", notes = "新增角色,pageNum,pageSize不用传参")
	public BaseResponse saveSysRole(@RequestBody SysRoleRequest sysRoleRequest) {
		try {
			SysRole sysRole = new SysRole();
			TransferUtils.copyPropertiesIgnoreNull(sysRoleRequest, sysRole);
			sysRoleService.saveSysRole(sysRole);
			return BaseResponse.getSuccessResponse();
		} catch (RmisException rmis) {
			log.info("新增角色失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("新增角色失败!", e);
			return BaseResponse.getFailResponse("新增角色失败!");
		}
	}

	/* 修改角色信息 */
	@SysLogAop("修改角色")
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "修改角色", notes = "修改角色")
	public BaseResponse updateRepGroup(@RequestBody SysRoleRequest sysRoleRequest) {
		try {
			ValidatorUtils.validateEntity(sysRoleRequest, Edit.class);
			SysRole sysRole = new SysRole();
			TransferUtils.copyPropertiesIgnoreNull(sysRoleRequest, sysRole);
			sysRoleService.updateSysRole(sysRole);
			return BaseResponse.getSuccessResponse();
		} catch (RmisException rmis) {
			log.info("修改角色失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("修改角色失败!", e);
			return BaseResponse.getFailResponse("修改角色失败!");
		}
	}
	
	@ApiOperation(value = "角色名是否重复查询", notes = "角色名是否重复查询:{adOrup:ad：0，修改：传ID}")
	@RequestMapping(method = RequestMethod.GET, value = "/judgerole/{adOrup}/{roleName}")
	public BaseResponse judgerole(@PathVariable(value="adOrup") String adOrup,@PathVariable(value="roleName") String roleName) throws Exception { 
		String status=sysRoleService.judgerole(adOrup,roleName);
		return BaseResponse.getSuccessResponse((Object)status);
	}
}
