package com.jfhealthcare.modules.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfhealthcare.modules.system.entity.SysMenu;
import com.jfhealthcare.modules.system.entity.SysOperRole;
import com.jfhealthcare.modules.system.entity.SysRightModset;
import com.jfhealthcare.modules.system.mapper.SysMenuMapper;
import com.jfhealthcare.modules.system.mapper.SysOperRoleMapper;
import com.jfhealthcare.modules.system.mapper.SysRightModsetMapper;
import com.jfhealthcare.modules.system.mapper.SysRightModuleMapper;
import com.jfhealthcare.modules.system.service.SysRightModsetService;
import com.jfhealthcare.modules.system.service.SysRightModuleService;

import tk.mybatis.mapper.entity.Example;

@Service
public class SysRightModsetServiceImpl implements SysRightModsetService {

	@Autowired
	private SysRightModsetMapper sysRightModsetMapper;

	/*
	 * @Autowired private SysRightModuleMapper sysRightModuleMapper;
	 */

	@Autowired
	private SysMenuMapper sysMenuMapper;

	/*
	 * @Autowired private SysRightModuleService sysRightModuleService;
	 */

	@Autowired
	private SysOperRoleMapper sysOperRoleMapper;

	/**
	 * SysRightModset 表的操作 删除 新增 修改
	 * 
	 * @param flag
	 *            标识： 1 账号 ，2 角色id
	 * @param logincodeOrRoleId
	 *            账号或者角色参数
	 * @param menuIds
	 *            改后的菜单ID集合
	 */
	@Override
	@Transactional
	public void updateSysRightModset(String logincodeOrRoleId, List<String> menuIds, int flag) {

		// 对 SysRightModset 删除后，再新增
		SysRightModset sysRightModset = new SysRightModset();
		sysRightModset.setLogincode(logincodeOrRoleId);
		sysRightModset.setObjflag(flag);
		sysRightModsetMapper.delete(sysRightModset);

		// 获取要插入的权限的父类ID 这里因为系统只有两级权限,所以往上查一级插入
		// 当要插入的权限本身为一级权限时，不查父类
		ArrayList<String> idList = new ArrayList<String>();
		if (menuIds != null && !menuIds.isEmpty()) {
			for (String menuId : menuIds) {
				SysMenu sym = new SysMenu();
				sym.setId(menuId);
				SysMenu selectOne = sysMenuMapper.selectOne(sym);
				if (!"0".equals(selectOne.getUppderId()) && !idList.contains(selectOne.getUppderId())) {
					SysRightModset srm2 = new SysRightModset();
					srm2.setLogincode(logincodeOrRoleId);
					srm2.setMenuId(selectOne.getUppderId());
					srm2.setObjflag(flag);
					sysRightModsetMapper.insert(srm2);
					idList.add(selectOne.getUppderId());
				}
				if (!idList.contains(menuId)) {
					SysRightModset srm = new SysRightModset();
					srm.setLogincode(logincodeOrRoleId);
					srm.setMenuId(menuId);
					srm.setObjflag(flag);
					sysRightModsetMapper.insert(srm);
					idList.add(menuId);
				}
			}
		}
	}

	@Override
	@Transactional
	public void updateSysRightModsetForRole(String logincodeOrRoleId, List<String> menuIds, int i) {
		this.updateSysRightModset(logincodeOrRoleId, menuIds, i);
		/*
		 * Example example = new Example(SysOperRole.class);
		 * example.createCriteria().andEqualTo("roleId", logincodeOrRoleId);
		 * List<SysOperRole> sysOperRoles =
		 * sysOperRoleMapper.selectByExample(example);
		 * if(!sysOperRoles.isEmpty()){ for (SysOperRole sysOperRole :
		 * sysOperRoles) {
		 * sysRightModuleService.restartSysRightModule(sysOperRole.getLogincode(
		 * )); } }
		 */
	}

	/*
	 * @Override
	 * 
	 * @Transactional public void updateSysRightModsetForOper(String
	 * logincodeOrRoleId, List<String> menuIds, int i) {
	 * this.updateSysRightModset(logincodeOrRoleId, menuIds, i);
	 * sysRightModuleService.restartSysRightModule(logincodeOrRoleId); }
	 */

}
