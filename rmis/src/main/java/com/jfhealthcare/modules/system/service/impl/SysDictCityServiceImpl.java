package com.jfhealthcare.modules.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfhealthcare.modules.system.entity.SysDictCity;
import com.jfhealthcare.modules.system.mapper.SysDictCityMapper;
import com.jfhealthcare.modules.system.service.SysDictCityService;

import tk.mybatis.mapper.entity.Example;
@Service
public class SysDictCityServiceImpl implements SysDictCityService {
	@Autowired
	private SysDictCityMapper sysDictCityMapper;
	@Override
	public List<SysDictCity> queryDictCity(int level, int code) {
		Example ex = new Example(SysDictCity.class);
		ex.createCriteria().andEqualTo("uppcode", code).andEqualTo("level",level);
		return sysDictCityMapper.selectByExample(ex);
	}

}
