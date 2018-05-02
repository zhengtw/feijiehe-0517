package com.jfhealthcare.modules.system.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.utils.NameUtils;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.modules.system.entity.SysOperRole;
import com.jfhealthcare.modules.system.entity.SysRole;
import com.jfhealthcare.modules.system.mapper.SysOperRoleMapper;
import com.jfhealthcare.modules.system.mapper.SysRightModsetMapper;
import com.jfhealthcare.modules.system.mapper.SysRoleMapper;
import com.jfhealthcare.modules.system.service.SysRightModsetService;
import com.jfhealthcare.modules.system.service.SysRightModuleService;
import com.jfhealthcare.modules.system.service.SysRoleService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	SysRoleMapper sysRoleMapper;
	@Autowired
	SysRightModsetMapper sysRightModsetMapper;
	@Autowired
	SysOperRoleMapper sysOperRoleMapper;
	@Autowired
	SysRightModsetService sysRightModsetService;
	/*@Autowired
	SysRightModuleService sysRightModuleService;*/
	@Override
	public PageInfo<SysRole> querySysRole(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(SysRole.class);
		example.createCriteria().andEqualTo("isdelete", Boolean.FALSE);
		List<SysRole> selectAll = sysRoleMapper.selectByExample(example);
		PageInfo<SysRole> pageInfo = new PageInfo<SysRole>(selectAll);
		return pageInfo;
	}

	@Transactional
	@Override
	public void delectSysRole(String[] split) {
		SysRole sysRole = new SysRole();
		for (String id : split) {
			sysRole.setId(id);
			sysRole.setIsdelete(Boolean.TRUE);
			sysRole.setUpdTime(new Date());
			sysRole.setUpdUser(NameUtils.getLoginCode());
			Example example = new Example(SysOperRole.class);
			example.createCriteria().andEqualTo("roleId", id);
			//逻辑删除角色
			sysRoleMapper.updateByPrimaryKeySelective(sysRole);
			//同时更新表 菜单 角色 用户 表
			sysRightModsetService.updateSysRightModset(id,null,2);
			/*List<SysOperRole> selects = sysOperRoleMapper.selectByExample(example);
			for (SysOperRole sysOperRole : selects) {
				sysRightModuleService.restartSysRightModule(sysOperRole.getLogincode());
			}*/
			//删除角色和用户表
			sysOperRoleMapper.deleteByExample(example);
		}
	}

	@Override
	public void saveSysRole(SysRole sysRole) {
		sysRole.setIsdelete(Boolean.FALSE);
		sysRole.setCrtTime(new Date());
		sysRole.setCrtUser(NameUtils.getLoginCode());
		sysRoleMapper.insertSelective(sysRole);
	}
	
	@Override
	public void updateSysRole(SysRole sysRole) {
		sysRole.setUpdTime(new Date());
		sysRole.setUpdUser(NameUtils.getLoginCode());
		sysRoleMapper.updateByPrimaryKey(sysRole);
	}

	@Override
	public PageInfo<SysRole> querySysRoleByParameter(SysRole sysRole, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(SysRole.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("isdelete", Boolean.FALSE);
		if(StringUtils.isNotBlank(sysRole.getId())){
			criteria.andEqualTo("id", sysRole.getId());
		}
		if(StringUtils.isNotBlank(sysRole.getNamepy())){
			criteria.andCondition("(namepy like \"%"+sysRole.getNamepy()+"%\"" +"or namewb like \"%"+sysRole.getNamepy()+"%\")");
		}
		List<SysRole> selectAll = sysRoleMapper.selectByExample(example);
		PageInfo<SysRole> pageInfo = new PageInfo<SysRole>(selectAll);
		return pageInfo;
	}

	@Override
	public SysRole querySysRoleById(String id) {
		return sysRoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public String judgerole(String adOrup, String roleName) {
		Example ex=new Example(SysRole.class);
		Criteria cr = ex.createCriteria().andEqualTo("name", roleName).andEqualTo("isdelete", "0");
		if(!"ad".equals(adOrup)){
			cr.andNotEqualTo("id", adOrup);
		}
		List<SysRole> sysroles = sysRoleMapper.selectByExample(ex);
		return CollectionUtils.isEmpty(sysroles)?"0":"1";
	}

}
