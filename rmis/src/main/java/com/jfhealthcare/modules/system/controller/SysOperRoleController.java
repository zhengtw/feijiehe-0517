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
import com.jfhealthcare.common.validator.group.Insert;
import com.jfhealthcare.modules.basics.BasicPageEntity;
import com.jfhealthcare.modules.system.annotation.SysLogAop;
import com.jfhealthcare.modules.system.entity.SysOperRole;
import com.jfhealthcare.modules.system.request.SysOperRoleRequest;
import com.jfhealthcare.modules.system.response.OperatorRoleResponse;
import com.jfhealthcare.modules.system.service.SysOperRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
* <p>Title: SysOperRoleController</p>
* <p>Description: </p>
* <p>Company: jf</p> 
* @author jianglinyan
* @date 2017年9月27日下午3:16:37
*/
@Slf4j 
@RestController
@Api(value = "a用户->用户和角色关系接口api")
@RequestMapping("/v2/rmis/sysop/sysOperRole")
public class SysOperRoleController {
	@Autowired
	private SysOperRoleService sysOperRoleService;

	/* 查询所有角色和人员关系详情信息 */
	@RequestMapping(path="/all",method = RequestMethod.POST)
	@ApiOperation(value = "查询所有角色和人员关系详情", notes = "查询所有角色和人员关系详情")
	public BaseResponse querySysOperRole(@RequestBody BasicPageEntity basic) {
		try {
			PageInfo<SysOperRole> sysOperRole = sysOperRoleService.querySysOperRole(basic.getPageNum(),
					basic.getPageSize());
			return PageResponse.getSuccessPage(sysOperRole);
		} catch (RmisException rmis) {
			log.info("查询所有角色和人员关系失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("查询所有角色和人员关系失败!", e);
			return BaseResponse.getFailResponse("查询所有角色和人员关系失败!");
		}
	}

	/* 查询单个角色和人员关系信息 */
	@RequestMapping(path = "/one", method = RequestMethod.POST)
	@ApiOperation(value = "查询单个角色和人员关系", notes = "查询单个角色和人员关系")
	public BaseResponse querySysOperRoleById(@RequestBody SysOperRoleRequest sysOperRoleRequest) {
		try {
			SysOperRole sysrg = new SysOperRole();
			TransferUtils.copyPropertiesIgnoreNull(sysOperRoleRequest, sysrg);
			PageInfo<OperatorRoleResponse> sysOperRole = sysOperRoleService.querySysOperRoleById(sysrg,
					sysOperRoleRequest.getPageNum(), sysOperRoleRequest.getPageSize(),sysOperRoleRequest.getNamepy());
			return PageResponse.getSuccessPage(sysOperRole);
		} catch (RmisException rmis) {
			log.info("查询单个角色和人员关系失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("查询单个角色和人员关系失败!", e);
			return BaseResponse.getFailResponse("查询单个角色和人员关系失败!");
		}
	}

	/* 批量删除角色和人员关系表中的 相关数据 */
	@SysLogAop("删除角色和人员关系")
	@RequestMapping(path = "/{ids}", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除角色和人员关系", notes = "删除角色和人员关系，需要删除的IDs 放到路径中如：/v2/SysOperRole/1,2,3")
	public BaseResponse deleteSysOperRole(@PathVariable String ids) {
		try {
			Assert.isBlank(ids, "ids不能为空!");
			String[] split = ids.split(",");
			sysOperRoleService.deleteSysOperRole(split);
			return BaseResponse.getSuccessResponse();
		} catch (RmisException rmis) {
			log.info("批量删除角色和人员关系失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("批量删除角色和人员关系失败!", e);
			return BaseResponse.getFailResponse("批量删除角色和人员关系失败!");
		}
	}

	/* 新增角色和人员关系 */
	@SysLogAop("新增角色和人员关系")
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "新增角色和人员关系", notes = "新增角色和人员关系")
	public BaseResponse saveSysOperRole(@RequestBody SysOperRoleRequest sysOperRoleRequest) {
		try {
			ValidatorUtils.validateEntity(sysOperRoleRequest, Insert.class);
			SysOperRole re = new SysOperRole();
			TransferUtils.copyPropertiesIgnoreNull(sysOperRoleRequest, re);
			sysOperRoleService.saveSysOperRole(re);
			return BaseResponse.getSuccessResponse();
		} catch (RmisException rmis) {
			log.info("新增角色和人员关系失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("新增角色和人员关系失败!", e);
			return BaseResponse.getFailResponse("新增角色和人员关系失败!");
		}
	}

	/* 修改角色和人员关系信息 */
	@SysLogAop("修改角色和人员关系信息")
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "修改角色和人员关系", notes = "修改角色和人员关系")
	public BaseResponse updateSysOperRole(@RequestBody SysOperRoleRequest sysOperRoleRequest) {
		try {
			ValidatorUtils.validateEntity(sysOperRoleRequest, Edit.class);
			SysOperRole re = new SysOperRole();
			TransferUtils.copyPropertiesIgnoreNull(sysOperRoleRequest, re);
			sysOperRoleService.updateSysOperRole(re);
			return BaseResponse.getSuccessResponse();
		} catch (RmisException rmis) {
			log.info("修改角色和人员关系失败!", rmis.getMessage());
			return BaseResponse.getFailResponse(rmis.getMessage());
		} catch (Exception e) {
			log.error("修改角色和人员关系失败!", e);
			return BaseResponse.getFailResponse("修改角色和人员关系失败!");
		}
	}
}
