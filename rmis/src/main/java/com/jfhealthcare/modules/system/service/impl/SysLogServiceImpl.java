package com.jfhealthcare.modules.system.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.utils.DateUtils;
import com.jfhealthcare.modules.system.entity.SysLog;
import com.jfhealthcare.modules.system.mapper.SysLogMapper;
import com.jfhealthcare.modules.system.request.SysLogRequest;
import com.jfhealthcare.modules.system.service.SysLogService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SysLogServiceImpl implements SysLogService {

	@Autowired SysLogMapper sysLogMapper;
	
	@Override
	public void save(SysLog sysLog) {
		sysLogMapper.insert(sysLog);
	}

	@Override
	public PageInfo<SysLog> querySysLog(SysLogRequest sysLogRequest) {
		Page<Object> startPage = PageHelper.startPage(sysLogRequest.getPageNum(), sysLogRequest.getPageSize());
		Example example = new Example(SysLog.class);
		Criteria createCriteria = example.createCriteria();
		if(StringUtils.isNotBlank(sysLogRequest.getLogincode())){
			createCriteria.andLike("logincode", sysLogRequest.getLogincode());
		}
		if(StringUtils.isNotBlank(sysLogRequest.getOperation())){
			createCriteria.andLike("operation", sysLogRequest.getOperation());
		}
		if(sysLogRequest.getCrtTime()!=null){
			createCriteria.andBetween("crtTime", DateUtils.getStartDateDay(sysLogRequest.getCrtTime()), DateUtils.getEndDateDay(sysLogRequest.getCrtTime()));
		}
		example.setOrderByClause("crt_time desc");
		List<SysLog> selectByExample = sysLogMapper.selectByExample(example);
		PageInfo<SysLog> pageInfo = new PageInfo<SysLog>(selectByExample);
		return pageInfo;
	}

	@Override
	public List<String> querySysLogOperations() {
		return sysLogMapper.querySysLogOperations();
	}
}
