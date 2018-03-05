package com.jfhealthcare.modules.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.system.entity.SysRepGroup;
import com.jfhealthcare.modules.system.mapper.SysRepGroupMapper;
import com.jfhealthcare.modules.system.service.SysRepGroupService;

import tk.mybatis.mapper.entity.Example;

@Service
public class SysRepGroupServiceImpl implements SysRepGroupService {
	@Autowired
	private SysRepGroupMapper sysRepGroupMapper;

	@Override
	public PageInfo<SysRepGroup> querySysRepGroup(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<SysRepGroup> selects = sysRepGroupMapper.selectAll();
		PageInfo<SysRepGroup> pageInfo = new PageInfo<SysRepGroup>(selects);
		return pageInfo;
	}

	@Override
	public PageInfo<SysRepGroup> querySysRepGroupById(SysRepGroup sysrg, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(SysRepGroup.class);
		example.createCriteria().andEqualTo("groupId",sysrg.getGroupId());
		List<SysRepGroup> selects = sysRepGroupMapper.selectByExample(example);
		PageInfo<SysRepGroup> pageInfo = new PageInfo<SysRepGroup>(selects);
		return pageInfo;
	}

	@Override
	@Transactional
	public void deleteSysRepGroup(String[] split) {
		for (String id : split) {
			sysRepGroupMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public void saveSysRepGroup(SysRepGroup re) {
		sysRepGroupMapper.insertSelective(re);
	}

	@Override
	public void updateSysRepGroup(SysRepGroup re) {
		sysRepGroupMapper.updateByPrimaryKey(re);
	}

}
