package com.jfhealthcare.modules.BI.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfhealthcare.modules.BI.mapper.RepCountMapper;
import com.jfhealthcare.modules.BI.response.RepCountResponse;
import com.jfhealthcare.modules.BI.service.RepCountService;


@Service
public class RepCountServiceImpl implements RepCountService 
{
	@Autowired
	private RepCountMapper repCountMapper;
	
	@Override
	public List<RepCountResponse> queryRepCount()
	{
		List<RepCountResponse> repCountM = repCountMapper.queryRepCount();
		
		return repCountM;
	}
	
	

}
