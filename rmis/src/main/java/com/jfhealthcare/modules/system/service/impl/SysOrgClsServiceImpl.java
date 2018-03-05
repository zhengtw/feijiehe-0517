package com.jfhealthcare.modules.system.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfhealthcare.modules.system.entity.SysOrgCls;
import com.jfhealthcare.modules.system.mapper.SysOrgClsMapper;
import com.jfhealthcare.modules.system.service.SysOrgClsService;


@Service
public class SysOrgClsServiceImpl implements SysOrgClsService {

	@Autowired
	private SysOrgClsMapper sysOrgClsMapper;
	
	
	@Override
	public List<SysOrgCls> querySysOrgClsAll() {
		
		List<SysOrgCls> sysOrgClss = sysOrgClsMapper.selectAll();
		
		return sysOrgClss;
	}

	
	
	
	

}
