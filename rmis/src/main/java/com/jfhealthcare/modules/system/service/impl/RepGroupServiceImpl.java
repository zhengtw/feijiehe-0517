package com.jfhealthcare.modules.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.modules.system.entity.RepGroup;
import com.jfhealthcare.modules.system.entity.SysOrganization;
import com.jfhealthcare.modules.system.entity.SysRepGroup;
import com.jfhealthcare.modules.system.mapper.RepGroupMapper;
import com.jfhealthcare.modules.system.mapper.SysRepGroupMapper;
import com.jfhealthcare.modules.system.request.RepGroupRequest;
import com.jfhealthcare.modules.system.request.SysRepGroupRequest;
import com.jfhealthcare.modules.system.service.RepGroupService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class RepGroupServiceImpl implements RepGroupService {
	@Autowired
	RepGroupMapper repGroupMapper;
	@Autowired
	SysRepGroupMapper sysRepGroupMapper;

	@Override
	public PageInfo<RepGroup> queryRepGroup(RepGroupRequest repGroupRequest) {
		Page<Object> startPage = PageHelper.startPage(repGroupRequest.getPageNum(), repGroupRequest.getPageSize());
		Example example = new Example(RepGroup.class);
		Criteria createCriteria = example.createCriteria();
		if (StringUtils.isNotBlank(repGroupRequest.getName())) {
			createCriteria.andLike("name", "%" + repGroupRequest.getName() + "%");
		}
		if (StringUtils.isNotBlank(repGroupRequest.getNamepy())) {
			createCriteria.andCondition("(namepy like \"%" + repGroupRequest.getNamepy() + "%\"" + "or namewb like \"%"
					+ repGroupRequest.getNamepy() + "%\")");
		}
		example.setOrderByClause("status asc,CAST(nindex AS DECIMAL) asc");
		List<RepGroup> selectByExample = repGroupMapper.selectByExample(example);
		PageInfo<RepGroup> pageInfo = new PageInfo<RepGroup>(selectByExample);
		return pageInfo;
	}

	@Override
	public List<RepGroup> queryRepGroupAll() {
		Example example = new Example(RepGroup.class);
		example.setOrderByClause("status asc,CAST(nindex AS DECIMAL) asc");
		List<RepGroup> selectAll = repGroupMapper.selectByExample(example);
		return selectAll;
	}
	
	@Override
	public String queryMaxNindex() {
		Example example = new Example(RepGroup.class);
		example.setOrderByClause("CAST(NINDEX AS DECIMAL) desc");
		List<RepGroup> selectAll = repGroupMapper.selectByExample(example);
		return CollectionUtils.isEmpty(selectAll)?"0":String.valueOf(Integer.valueOf(selectAll.get(0).getNindex())+1);
	}
	
	@Override
	public List<SysRepGroup> queryLoginCodeByGroupId(String id) {
		SysRepGroup sysRepGroup = new SysRepGroup();
		sysRepGroup.setGroupId(id);
		List<SysRepGroup> srgs = sysRepGroupMapper.select(sysRepGroup);
		return srgs;
	}

	@Override
	@Transactional
	public void delectRepGroup(String groupId) {
		Example example = new Example(SysRepGroup.class);
		example.createCriteria().andEqualTo("groupId", groupId);
		sysRepGroupMapper.deleteByExample(example);
		repGroupMapper.deleteByPrimaryKey(groupId);
	}

	@Override
	public void saveRepGroup(RepGroup re) {
		Example ex=new Example(RepGroup.class);
		ex.createCriteria().andEqualTo("name", re.getName()).andEqualTo("status", re.getStatus());
		List<RepGroup> repGroups = repGroupMapper.selectByExample(ex);
		Assert.isNotNull(repGroups, "分组名已存在，请重新输入！");
		repGroupMapper.insertSelective(re);
	}

	@Override
	public void updateRepGroup(RepGroup re) {
		Example ex=new Example(RepGroup.class);
		ex.createCriteria().andEqualTo("name", re.getName()).andEqualTo("status", re.getStatus()).andNotEqualTo("id", re.getId());
		List<RepGroup> repGroups = repGroupMapper.selectByExample(ex);
		Assert.isNotNull(repGroups, "分组名已存在，请重新输入！");
		repGroupMapper.updateByPrimaryKey(re);
	}

	@Override
	@Transactional
	public void updateSysRepGroup(SysRepGroupRequest sysRepGroupRequest) {
		
		String logincodes = sysRepGroupRequest.getLogincodes();
		String[] lcs = StringUtils.split(logincodes,",");
		List<String> asList = new ArrayList<String>(Arrays.asList(lcs));
		
		SysRepGroup srg=new SysRepGroup();
		srg.setGroupId(sysRepGroupRequest.getGroupId());
		List<SysRepGroup> select = sysRepGroupMapper.select(srg);
		List<String> orginRepGroup=new ArrayList<String>();
		for (SysRepGroup sysRepGroup : select) {
			orginRepGroup.add(sysRepGroup.getLogincode());
		}
		asList.removeAll(orginRepGroup);
		for (String logincode : asList) {
			SysRepGroup sysRepGroup=new SysRepGroup();
			sysRepGroup.setGroupId(sysRepGroupRequest.getGroupId());
			sysRepGroup.setLogincode(logincode);
			sysRepGroupMapper.insertSelective(sysRepGroup);
		}
	}

	@Override
	public RepGroup queryRepGroupByGroupId(String groupId) {
		RepGroup repGroup = repGroupMapper.selectByPrimaryKey(groupId);
		return repGroup;
	}

	@Override
	public void deleteRepGroup(String groupId,String logincodes) {
		String[] lcs = StringUtils.split(logincodes,",");
		for (String logincode : lcs) {
			SysRepGroup sysRepGroup=new SysRepGroup();
			sysRepGroup.setGroupId(groupId);
			sysRepGroup.setLogincode(logincode);
			sysRepGroupMapper.delete(sysRepGroup);
		}
	}


	

}
