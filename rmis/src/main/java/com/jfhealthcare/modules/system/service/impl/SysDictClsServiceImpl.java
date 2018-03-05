package com.jfhealthcare.modules.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfhealthcare.modules.system.entity.SysDictCls;
import com.jfhealthcare.modules.system.mapper.SysDictClsMapper;
import com.jfhealthcare.modules.system.service.SysDictClsService;

@Service
public class SysDictClsServiceImpl implements SysDictClsService {
	@Autowired
	private SysDictClsMapper sysDictClsMapper ;

	@Override
	public List<SysDictCls> queryDictCls() {
		List<SysDictCls> selectAll = sysDictClsMapper.selectAll();
		return selectAll;
		
	}

	@Override
	public SysDictCls queryDictClsById(String id) {
		return sysDictClsMapper.selectByPrimaryKey(id);
	} 

}
