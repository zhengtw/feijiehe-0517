package com.jfhealthcare.modules.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.system.entity.SysDepartment;
import com.jfhealthcare.modules.system.entity.SysOperRole;
import com.jfhealthcare.modules.system.entity.SysOperatorDtl;
import com.jfhealthcare.modules.system.mapper.SysDepartmentMapper;
import com.jfhealthcare.modules.system.mapper.SysOperRoleMapper;
import com.jfhealthcare.modules.system.mapper.SysOperatorDtlMapper;
import com.jfhealthcare.modules.system.mapper.SysOperatorMapper;
import com.jfhealthcare.modules.system.response.OperatorResponse;
import com.jfhealthcare.modules.system.response.OperatorRoleResponse;
import com.jfhealthcare.modules.system.service.SysOperRoleService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SysOperRoleServiceImpl implements SysOperRoleService {
	@Autowired
	private SysOperRoleMapper sysOperRoleMapper;
	@Autowired
	private SysOperatorDtlMapper sysOperatorDtlMapper;
	@Autowired
	private SysDepartmentMapper sysDepartmentMapper;
	@Autowired
	private SysOperatorMapper sysOperatorMapper;

	@Override
	public PageInfo<SysOperRole> querySysOperRole(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<SysOperRole> selects = sysOperRoleMapper.selectAll();
		PageInfo<SysOperRole> pageInfo = new PageInfo<SysOperRole>(selects);
		return pageInfo;
	}

	@Override
	public PageInfo<OperatorRoleResponse> querySysOperRoleById(SysOperRole sysrg, Integer pageNum, Integer pageSize,
			String namepy) {
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(SysOperRole.class);
		example.createCriteria().andEqualTo("roleId", sysrg.getRoleId());
		List<SysOperRole> selects = sysOperRoleMapper.selectByExample(example);
		ArrayList<OperatorRoleResponse> arr = new ArrayList<OperatorRoleResponse>();
		for (SysOperRole sysOperRole : selects) {
			Example example2 = new Example(SysOperatorDtl.class);
			Criteria criteria = example2.createCriteria();
			criteria.andEqualTo("logincode", sysOperRole.getLogincode());
			if (StringUtils.isNotBlank(namepy)) {
				criteria.andCondition("(namepy like \"%" + namepy + "%\"" + "or namewb like \"%" + namepy + "%\")");
			}
			OperatorRoleResponse operatorRoleResponse = new OperatorRoleResponse();
			List<SysOperatorDtl> selectOne = sysOperatorDtlMapper.selectByExample(example2);
			operatorRoleResponse.setId(sysOperRole.getId());
			operatorRoleResponse.setLogincode(sysOperRole.getLogincode());
			operatorRoleResponse.setRoleId(sysOperRole.getRoleId());
			if (selectOne == null || selectOne.size() < 1 || selectOne.get(0) == null) {
			} else {
				//从人员表中获取人员姓名
				HashMap<String,Object> pmap = new HashMap<String,Object>();
				pmap.put("logincode",sysOperRole.getLogincode());
				List<OperatorResponse> queryOperatorListByParameter = sysOperatorMapper.queryOperatorAllInfoByLogincode(pmap);
				operatorRoleResponse.setDeptId(selectOne.get(0).getDepId());
				operatorRoleResponse.setName(queryOperatorListByParameter.get(0).getName());

				SysDepartment selectByPrimaryKey = sysDepartmentMapper.selectByPrimaryKey(selectOne.get(0).getDepId());
				if (selectByPrimaryKey != null) {
					operatorRoleResponse.setDeptName(selectByPrimaryKey.getDepName());
				}
				arr.add(operatorRoleResponse);
			}
		}
		PageInfo<OperatorRoleResponse> pageInfo = new PageInfo<OperatorRoleResponse>(arr);
		return pageInfo;
	}

	@Override
	@Transactional
	public void deleteSysOperRole(String[] split) {
		for (String id : split) {
			sysOperRoleMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public void saveSysOperRole(SysOperRole re) {
		sysOperRoleMapper.insertSelective(re);
	}

	@Override
	public void updateSysOperRole(SysOperRole re) {
		sysOperRoleMapper.updateByPrimaryKey(re);
	}

}
