package com.jfhealthcare.modules.system.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.common.utils.NameUtils;
import com.jfhealthcare.common.utils.TransferUtils;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.modules.system.entity.RepGroup;
import com.jfhealthcare.modules.system.entity.SysDict;
import com.jfhealthcare.modules.system.entity.SysDictBodypart;
import com.jfhealthcare.modules.system.entity.SysDictDtl;
import com.jfhealthcare.modules.system.entity.SysDictScanmethod;
import com.jfhealthcare.modules.system.mapper.SysDictBodypartMapper;
import com.jfhealthcare.modules.system.mapper.SysDictDtlMapper;
import com.jfhealthcare.modules.system.mapper.SysDictMapper;
import com.jfhealthcare.modules.system.mapper.SysDictScanmethodMapper;
import com.jfhealthcare.modules.system.request.SysDictBodypartRequest;
import com.jfhealthcare.modules.system.request.SysDictScanmethodRequest;
import com.jfhealthcare.modules.system.service.SysDictBodypartService;
import com.jfhealthcare.modules.system.service.SysDictDtlService;
import com.jfhealthcare.modules.system.service.SysDictScanmethodService;
import com.jfhealthcare.modules.system.service.SysDictService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SysDictScanmethodServiceImpl implements SysDictScanmethodService {
	
	@Autowired
	private SysDictScanmethodMapper sysDictScanmethodMapper;
	
	@Autowired
	private SysDictDtlService sysDictDtlService;
	
	@Override
	public PageInfo<SysDictScanmethod> queryDictScanmethod(SysDictScanmethodRequest sysDictScanmethodRequest) {
		PageHelper.startPage(sysDictScanmethodRequest.getPageNum(), sysDictScanmethodRequest.getPageSize());
		Example example = new Example(SysDictBodypart.class);
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(sysDictScanmethodRequest.getChecktypeCode())){
			criteria.andEqualTo("checktypeCode",sysDictScanmethodRequest.getChecktypeCode());
		}
		if(StringUtils.isNotBlank(sysDictScanmethodRequest.getNamepy())){
			criteria.andCondition("(namepy like \"%"+sysDictScanmethodRequest.getNamepy()+"%\"" +"or namewb like \"%"+sysDictScanmethodRequest.getNamepy()+"%\")");
		}
		example.setOrderByClause("CHECKTYPE_CODE,CAST(NINDEX AS DECIMAL) asc");
		List<SysDictScanmethod> selectByExample = sysDictScanmethodMapper.selectByExample(example);
		PageInfo<SysDictScanmethod> pageInfo = new PageInfo<SysDictScanmethod>(selectByExample);
		return pageInfo;
	}

	@Override
	public void deleteDictScanmethod(String id) {
		sysDictScanmethodMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void updateDictScanmethod(SysDictScanmethodRequest sysDictScanmethodRequest) {
		SysDictDtl sysDictDtl = sysDictDtlService.queryDictDtlById(sysDictScanmethodRequest.getChecktypeCode());
		Assert.isNull(sysDictDtl, "字典检查方法中检查类型检查不能为空！");
		SysDictScanmethod sysDictScanmethod = new SysDictScanmethod();
		TransferUtils.copyPropertiesIgnoreNull(sysDictScanmethodRequest, sysDictScanmethod);
		sysDictScanmethod.setChecktypeName(sysDictDtl.getName());
		sysDictScanmethod.setUpdTime(new Date());
		sysDictScanmethod.setUpdUser(NameUtils.getLoginCode());
		sysDictScanmethodMapper.updateByPrimaryKey(sysDictScanmethod);
		
	}

	@Override
	public void saveDictScanmethod(SysDictScanmethodRequest sysDictScanmethodRequest) {
		SysDictDtl sysDictDtl = sysDictDtlService.queryDictDtlById(sysDictScanmethodRequest.getChecktypeCode());
		Assert.isNull(sysDictDtl, "字典检查方法中检查类型检查不能为空！");
		SysDictScanmethod sysDictScanmethod = new SysDictScanmethod();
		TransferUtils.copyPropertiesIgnoreNull(sysDictScanmethodRequest, sysDictScanmethod);
		sysDictScanmethod.setChecktypeName(sysDictDtl.getName());
		sysDictScanmethod.setCrtTime(new Date());
		sysDictScanmethod.setCrtUser(NameUtils.getLoginCode());
		sysDictScanmethodMapper.insertSelective(sysDictScanmethod);
		
	}

	@Override
	public String queryMaxNindex(String code) {
		Example example = new Example(SysDictScanmethod.class);
		example.createCriteria().andEqualTo("checktypeCode", code);
		example.setOrderByClause("CAST(NINDEX AS DECIMAL) desc");
		List<SysDictScanmethod> selectAll = sysDictScanmethodMapper.selectByExample(example);
		return CollectionUtils.isEmpty(selectAll)?"0":String.valueOf(Integer.valueOf(selectAll.get(0).getNindex())+1);
	}

	@Override
	public SysDictScanmethod queryDictScanmethodById(String id) {
		return sysDictScanmethodMapper.selectByPrimaryKey(id);
	}

}
