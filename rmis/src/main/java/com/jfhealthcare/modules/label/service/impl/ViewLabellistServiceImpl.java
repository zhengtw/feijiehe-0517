package com.jfhealthcare.modules.label.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.label.entity.ViewLabellist;
import com.jfhealthcare.modules.label.mapper.ViewLabellistMapper;
import com.jfhealthcare.modules.label.request.ViewLabellistRequest;
import com.jfhealthcare.modules.label.service.ViewLabellistService;
@Service
public class ViewLabellistServiceImpl implements ViewLabellistService {
	@Autowired
	private ViewLabellistMapper viewLabellistMapper;
	

	@Override
	public PageInfo<ViewLabellist> queryAllViewLabellist(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ViewLabellist> selectAll = viewLabellistMapper.selectAll();
		return new PageInfo<ViewLabellist>(selectAll);
	}


	@Override
	public PageInfo<ViewLabellist> queryViewLabellist(ViewLabellistRequest viewLabellistRequest) {
		PageHelper.startPage(viewLabellistRequest.getPageNum(),viewLabellistRequest.getPageSize());
		//TODO MAPPER中的东西要整理全
		List<ViewLabellist> viewLabellist = viewLabellistMapper.selectByParams(viewLabellistRequest);
		return new PageInfo<ViewLabellist>(viewLabellist);
	}

	

}
