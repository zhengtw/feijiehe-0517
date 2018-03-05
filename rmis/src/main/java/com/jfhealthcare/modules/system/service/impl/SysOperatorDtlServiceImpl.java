package com.jfhealthcare.modules.system.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.system.entity.SysOperatorDtl;
import com.jfhealthcare.modules.system.entity.SysOrganization;
import com.jfhealthcare.modules.system.mapper.SysOperatorDtlMapper;
import com.jfhealthcare.modules.system.request.SysOperatorDtlRequest;
import com.jfhealthcare.modules.system.service.SysOperatorDtlService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SysOperatorDtlServiceImpl implements SysOperatorDtlService {

	@Autowired
	private SysOperatorDtlMapper sysOperatorDtlMapper;
	@Override
	public PageInfo<SysOperatorDtl> querySysDepartment(SysOperatorDtlRequest sysOperatorDtlRequest) {
		PageHelper.startPage(sysOperatorDtlRequest.getPageNum(), sysOperatorDtlRequest.getPageSize());
		Example example=new Example(SysOperatorDtl.class);
		Criteria createCriteria = example.createCriteria();
		if(StringUtils.isNotBlank(sysOperatorDtlRequest.getLogincode())){
			createCriteria.andLike("logincode", "%"+sysOperatorDtlRequest.getLogincode()+"%");
		}
		if(StringUtils.isNotBlank(sysOperatorDtlRequest.getDepId())){
			createCriteria.andEqualTo("depId", sysOperatorDtlRequest.getDepId());
		}
		if(StringUtils.isNotBlank(sysOperatorDtlRequest.getOrgId())){
			createCriteria.andEqualTo("orgId", sysOperatorDtlRequest.getOrgId());
		}
		if(StringUtils.isNotBlank(sysOperatorDtlRequest.getNamepy())){
			createCriteria.andCondition("(namepy like \"%"+sysOperatorDtlRequest.getNamepy()+"%\"" +"or namewb like \"%"+sysOperatorDtlRequest.getNamepy()+"%\")");
		}
		List<SysOperatorDtl> sysOperatorDtls = sysOperatorDtlMapper.selectByExample(example);
		PageInfo<SysOperatorDtl> pageInfo = new PageInfo<SysOperatorDtl>(sysOperatorDtls);
		return pageInfo;
	}
	@Override
	public SysOperatorDtl queryDtlByLoginCode(String logincode) {
		SysOperatorDtl sysOperatorDtl=new SysOperatorDtl();
		sysOperatorDtl.setLogincode(logincode);
		List<SysOperatorDtl> sysOperatorDtls = sysOperatorDtlMapper.select(sysOperatorDtl);
		return sysOperatorDtls.isEmpty()?null:sysOperatorDtls.get(0);
	}



}
