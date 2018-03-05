package com.jfhealthcare.modules.system.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.base.PageResponse;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.common.validator.ValidatorUtils;
import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.common.validator.group.Insert;
import com.jfhealthcare.modules.system.annotation.SysLogAop;
import com.jfhealthcare.modules.system.entity.SysDepartment;
import com.jfhealthcare.modules.system.entity.SysOperatorDtl;
import com.jfhealthcare.modules.system.mapper.SysDepartmentMapper;
import com.jfhealthcare.modules.system.mapper.SysOperatorDtlMapper;
import com.jfhealthcare.modules.system.request.SysDepartmentRequest;
import com.jfhealthcare.modules.system.service.SysDepartmentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


/**
 * 机构管理
 * @author xujinma
 */
@Slf4j
@Api(value = "b组织->部门管理api")
@RestController
@RequestMapping("/v2/rmis/sysop/sysdepartment")
public class SysDepartmentController {

	@Autowired
	private SysDepartmentService sysDepartmentService;
	
	@Autowired
	private SysDepartmentMapper sysDepartmentMapper;
	
	@Autowired
	private SysOperatorDtlMapper sysOperatorDtlMapper;
	
	
	@ApiOperation(value = "条件查询部门", notes = "条件查询部门说明")
	@RequestMapping(method = RequestMethod.POST,value="/all")
	public BaseResponse querySysDepartment(@RequestBody SysDepartmentRequest sysDepartmentRequest) {
		
		PageInfo<Map<String,Object>> sysDepartments = sysDepartmentService.querySysDepartment(sysDepartmentRequest);
		
		return PageResponse.getSuccessPage(sysDepartments);
	}
	
	@ApiOperation(value = "查询所有部门", notes = "查询所有部门说明")
	@RequestMapping(method = RequestMethod.GET)
	public BaseResponse queryAllSysDepartment() {
		try {
			List<SysDepartment> all = sysDepartmentService.queryAllSysDepartment();
			return BaseResponse.getSuccessResponse(all);
		} catch (Exception e) {
			log.error("查询所有机构异常！", e);
			return BaseResponse.getFailResponse("查询所有机构异常！");
		}
		
	}
	
	@ApiOperation(value = "查询单个机构", notes = "查询单个机构说明")
	@RequestMapping(method = RequestMethod.GET,value="/{id}")
	public BaseResponse querySysDepartment(@PathVariable String id) {
		SysDepartment sysDepartment=null;
		try {
			Assert.isBlank(id, "查询id不能为空！");
			sysDepartment = sysDepartmentService.querySysDepartment(id);
		} catch (Exception e) {
			log.error("单个查询异常！", e);
			return BaseResponse.getFailResponse("单个查询异常！");
		}
		return BaseResponse.getSuccessResponse(sysDepartment);
	}
	
	@ApiOperation(value = "查询机构下所有部门", notes = "查询机构下所有部门说明")
	@RequestMapping(method = RequestMethod.GET,path="/org/{orgId}")
	public BaseResponse querySysDepartmentByOrg(@PathVariable String orgId) {
		List<SysDepartment> sysDepartments=null;
		try {
			Assert.isBlank(orgId, "查询机构id不能为空！");
			sysDepartments = sysDepartmentService.querySysDepartmentByOrg(orgId);
		} catch (Exception e) {
			log.error("单个查询异常！", e);
			return BaseResponse.getFailResponse("单个查询异常！");
		}
		return BaseResponse.getSuccessResponse(sysDepartments);
	}
	
	@SysLogAop("新增部门")
	@ApiOperation(value = "新增部门", notes = "新增部门说明")
	@RequestMapping(method = RequestMethod.PUT)
	public BaseResponse addSysDepartment(@RequestBody SysDepartmentRequest sysDepartmentRequest){
		
		try {
			ValidatorUtils.validateEntity(sysDepartmentRequest,Insert.class);
			sysDepartmentService.insertSysDepartment(sysDepartmentRequest);
		} catch (Exception e) {
			log.error("新增异常！", e);
			return BaseResponse.getFailResponse("新增失败！");
		}
		return BaseResponse.getSuccessResponse("新增成功！");
	}
	
	@SysLogAop("修改部门")
	@ApiOperation(value = "修改部门", notes = "修改部门说明")
	@RequestMapping(method = RequestMethod.POST)
	public BaseResponse updateSysDepartment(@RequestBody SysDepartmentRequest sysDepartmentRequest){
		
		try {
			ValidatorUtils.validateEntity(sysDepartmentRequest,Edit.class);
			sysDepartmentService.updateSysDepartment(sysDepartmentRequest);
		} catch (Exception e) {
			log.error("部门修改异常！", e);
			return BaseResponse.getFailResponse("部门修改失败！");
		}
		return BaseResponse.getSuccessResponse("部门修改成功！");
	}
	
	@SysLogAop("删除部门")
	@ApiOperation(value = "删除部门", notes = "删除部门说明:{isDelete:0:判断效验，1：强制删，不效验}")
	@RequestMapping(method = RequestMethod.DELETE,value="/{isDelete}/{id}")
	public BaseResponse deleteSysDepartment(@PathVariable(value="isDelete") String isDelete,@PathVariable(value="id") String id){
		
		try {
			Assert.isBlank(isDelete, "删除判断不能为空！");
			Assert.isBlank(id, "删除id不能为空！");
			//"删除部门说明:{isDelete:0:判断效验，1：强制删，不效验}"
			if(StringUtils.equals("0", isDelete)){
				List<SysOperatorDtl> sysOperatorDtls =getSysOperatorDtlsByDepId(id);
				if(!CollectionUtils.isEmpty(sysOperatorDtls)){
					return BaseResponse.getFailResponse(510,"改部门下有关联人员，请确认是否强制删除！");
				}
			}
			sysDepartmentService.deleteSysDepartment(isDelete,id);
		} catch (Exception e) {
			log.error("部门删除异常！", e);
			return BaseResponse.getFailResponse("部门删除失败！");
		}
		return BaseResponse.getSuccessResponse("部门删除成功！");
	}

	private List<SysOperatorDtl> getSysOperatorDtlsByDepId(String id) {
		SysOperatorDtl sysOperatorDtl=new SysOperatorDtl();
		sysOperatorDtl.setDepId(id);
		List<SysOperatorDtl> sysOperatorDtls = sysOperatorDtlMapper.select(sysOperatorDtl);
		return sysOperatorDtls;
	}
}
