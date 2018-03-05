package com.jfhealthcare.modules.system.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jfhealthcare.common.base.BaseResponse;
import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.common.utils.RedisUtils;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.common.validator.ValidatorUtils;
import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.common.validator.group.Insert;
import com.jfhealthcare.modules.system.annotation.LoginUser;
import com.jfhealthcare.modules.system.annotation.SysLogAop;
import com.jfhealthcare.modules.system.entity.SysMenu;
import com.jfhealthcare.modules.system.entity.SysOperator;
import com.jfhealthcare.modules.system.entity.SysRightModset;
import com.jfhealthcare.modules.system.entity.SysRightModule;
import com.jfhealthcare.modules.system.request.SysMenuRequest;
import com.jfhealthcare.modules.system.service.SysMenuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 机构分类
 * @author xujinma
 */
@Slf4j
@Api(value = "a权限->菜单管理api")
@RestController
@RequestMapping("/v2/rmis/sysop/sysmenu")
public class SysMenuController {

	@Autowired
	private SysMenuService sysMenuService;
	
	@Autowired
	private RedisUtils redisUtils;
	
	@ApiOperation(value = "查询当前登录用户所有菜单", notes = "查询当前登录用户所有菜单说明")
	@RequestMapping(method = RequestMethod.GET,path="/oper")
	public BaseResponse querySysMenuForLogin(@LoginUser LoginUserEntity loginUserEntity) {
		
		List<Map<String, Object>> sysMenus =sysMenuService.querySysMenuForLogin(loginUserEntity.getSysOperator().getLogincode());
		
		return BaseResponse.getSuccessResponse(sysMenus);
	}
	
	@ApiOperation(value = "查询当前选中用户所有菜单", notes = "查询当前选中用户所有菜单说明")
	@RequestMapping(method = RequestMethod.GET,path="/oper/{logincode}")
	public BaseResponse querySysMenuForLogin(@PathVariable("logincode")String logincode) {
		
		List<SysRightModule> sysMenus =sysMenuService.querySysMenuForClick(logincode);
		
		return BaseResponse.getSuccessResponse(sysMenus);
	}
	
	@ApiOperation(value = "查询当前角色所有菜单", notes = "查询当前角色所有菜单说明")
	@RequestMapping(method = RequestMethod.GET,path="/role/{roleId}")
	public BaseResponse querySysMenuForRole(@PathVariable("roleId")String roleId) {
		
		List<SysRightModset> sysMenus =sysMenuService.querySysMenuForRole(roleId);
		
		return BaseResponse.getSuccessResponse(sysMenus);
	}
	
	@ApiOperation(value = "查询上级菜单", notes = "查询上级菜单说明")
	@RequestMapping(method = RequestMethod.GET,path="uppder")
	public BaseResponse queryUppderSysMenu() {
		
		List<SysMenu> queryByCondition =sysMenuService.queryUppderSysMenu();
		
		return BaseResponse.getSuccessResponse(queryByCondition);
	}
	
	@ApiOperation(value = "查询该父级目录下最大排序号", notes = "查询该父级目录下最大排序号说明")
	@RequestMapping(method = RequestMethod.GET,path="/maxNindex/{faMenuId}")
	public BaseResponse querySysMenuMaxNindex(@PathVariable("faMenuId")String faMenuId) {
		Assert.isBlank(faMenuId, "父目录ID不能为空！");
		Integer maxNindex=sysMenuService.querySysMenuMaxNindex(faMenuId);
		return BaseResponse.getSuccessResponse(maxNindex);
	}
	
	@ApiOperation(value = "效验改父级目录下改序号是否存在", notes = "效验改父级目录下改序号是否存在说明")
	@RequestMapping(method = RequestMethod.GET,path="/{faMenuId}/{nindex}")
	public BaseResponse querySysMenuByNindexAndFaMenuId(@PathVariable("faMenuId")String faMenuId,@PathVariable("nindex")Integer nindex) {
		Assert.isBlank(faMenuId, "验证父目录ID不能为空！");
		Assert.isNull(nindex, "验证序号不能为空！");
		Boolean flag=sysMenuService.querySysMenuByNindexAndFaMenuId(faMenuId,nindex);
		if(!flag){
			return BaseResponse.getFailResponse("已存在");
		}
		return BaseResponse.getSuccessResponse("不存在");
	}
	
	@ApiOperation(value = "查询所有菜单", notes = "查询所有菜单说明")
	@RequestMapping(method = RequestMethod.GET)
	public BaseResponse querySysMenu() {
		List<Map<String,Object>> queryByCondition =sysMenuService.querySysMenu();
		return BaseResponse.getSuccessResponse(queryByCondition);
	}
	
	@ApiOperation(value = "查询单个菜单", notes = "查询单个菜单说明")
	@RequestMapping(method = RequestMethod.GET,value="/{id}")
	public BaseResponse querySingleSysMenu(@PathVariable String id) {
		SysMenu sysMenu=null;
		try {
			Assert.isBlank(id, "查询id不能为空！");
			sysMenu=sysMenuService.querySingleSysMenu(id);
		} catch (Exception e) {
			log.error("单个查询异常！", e);
			return BaseResponse.getFailResponse("单个查询异常！");
		}
		return BaseResponse.getSuccessResponse(sysMenu);
	}
	
	@SysLogAop("新增菜单")
	@ApiOperation(value = "新增菜单", notes = "新增菜单说明")
	@RequestMapping(method = RequestMethod.PUT)
	public BaseResponse addSysMenu(@RequestBody SysMenuRequest sysMenuRequest){
		try {
			ValidatorUtils.validateEntity(sysMenuRequest,Insert.class);
			sysMenuService.insertSysMenu(sysMenuRequest);
		} catch (Exception e) {
			log.error("新增异常！", e);
			return BaseResponse.getFailResponse("新增失败！");
		}
		return BaseResponse.getSuccessResponse("新增成功！");
	}
	
	@SysLogAop("修改菜单")
	@ApiOperation(value = "修改菜单", notes = "修改菜单说明")
	@RequestMapping(method = RequestMethod.POST)
	public BaseResponse updateSysMenu(@RequestBody SysMenuRequest sysMenuRequest){
		try {
			ValidatorUtils.validateEntity(sysMenuRequest,Edit.class);
			sysMenuService.updateSysMenu(sysMenuRequest);
		} catch (Exception e) {
			log.error("修改异常！", e);
			return BaseResponse.getFailResponse("修改失败！");
		}
		return BaseResponse.getSuccessResponse("修改成功！");
	}
	
	@SysLogAop("删除菜单")
	@ApiOperation(value = "删除菜单", notes = "删除菜单说明")
	@RequestMapping(method = RequestMethod.DELETE,value="/{id}")
	public BaseResponse deleteSysMenu(@PathVariable String id){
		try {
			Assert.isBlank(id, "删除id不能为空！");
			sysMenuService.deleteSysMenu(id);
		} catch (Exception e) {
			log.error("删除异常！", e);
			return BaseResponse.getFailResponse("删除失败！");
		}
		return BaseResponse.getSuccessResponse("删除成功！");
	}
	
}
