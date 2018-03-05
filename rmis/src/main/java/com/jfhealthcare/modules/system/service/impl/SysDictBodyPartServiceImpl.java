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
import com.jfhealthcare.modules.system.request.SysDictBodypartRequest;
import com.jfhealthcare.modules.system.service.SysDictBodypartService;
import com.jfhealthcare.modules.system.service.SysDictDtlService;
import com.jfhealthcare.modules.system.service.SysDictService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SysDictBodyPartServiceImpl implements SysDictBodypartService {
	
	@Autowired
	private SysDictBodypartMapper sysDictBodypartMapper;
	
	@Autowired
	private SysDictDtlService sysDictDtlService;
	
	@Override
	public PageInfo<SysDictBodypart> queryDictBodypart(SysDictBodypartRequest sysDictBodypartRequest) {
		PageHelper.startPage(sysDictBodypartRequest.getPageNum(), sysDictBodypartRequest.getPageSize());
		Example example = new Example(SysDictBodypart.class);
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(sysDictBodypartRequest.getChecktypeCode())){
			criteria.andEqualTo("checktypeCode",sysDictBodypartRequest.getChecktypeCode());
		}
		if(StringUtils.isNotBlank(sysDictBodypartRequest.getNamepy())){
			criteria.andCondition("(namepy like \"%"+sysDictBodypartRequest.getNamepy()+"%\"" +"or namewb like \"%"+sysDictBodypartRequest.getNamepy()+"%\")");
		}
		example.setOrderByClause("CHECKTYPE_CODE,CAST(NINDEX AS DECIMAL) asc");
		List<SysDictBodypart> selectByExample = sysDictBodypartMapper.selectByExample(example);
		PageInfo<SysDictBodypart> pageInfo = new PageInfo<SysDictBodypart>(selectByExample);
		return pageInfo;
	}

	@Override
	public void deleteDictBodypart(String id) {
		sysDictBodypartMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void updateDictBodypart(SysDictBodypartRequest sysDictBodypartRequest) {
		SysDictDtl sysDictDtl = sysDictDtlService.queryDictDtlById(sysDictBodypartRequest.getChecktypeCode());
		Assert.isNull(sysDictDtl, "字典部位中检查类型检查不能为空！");
		SysDictBodypart sysDictBodypart = new SysDictBodypart();
		TransferUtils.copyPropertiesIgnoreNull(sysDictBodypartRequest, sysDictBodypart);
		sysDictBodypart.setChecktypeName(sysDictDtl.getName());
		sysDictBodypart.setUpdTime(new Date());
		sysDictBodypart.setUpdUser(NameUtils.getLoginCode());
		sysDictBodypartMapper.updateByPrimaryKey(sysDictBodypart);
	}

	@Override
	public void saveDictBodypart(SysDictBodypartRequest sysDictBodypartRequest) {
		SysDictDtl sysDictDtl = sysDictDtlService.queryDictDtlById(sysDictBodypartRequest.getChecktypeCode());
		Assert.isNull(sysDictDtl, "字典部位中检查类型检查不能为空！");
		SysDictBodypart sysDictBodypart = new SysDictBodypart();
		TransferUtils.copyPropertiesIgnoreNull(sysDictBodypartRequest, sysDictBodypart);
		sysDictBodypart.setChecktypeName(sysDictDtl.getName());
		sysDictBodypart.setCrtTime(new Date());
		sysDictBodypart.setCrtUser(NameUtils.getLoginCode());
		sysDictBodypartMapper.insertSelective(sysDictBodypart);
	}

	@Override
	public String queryMaxNindex(String code) {
		Example example = new Example(SysDictBodypart.class);
		example.createCriteria().andEqualTo("checktypeCode", code);
		example.setOrderByClause("CAST(NINDEX AS DECIMAL) desc");
		List<SysDictBodypart> selectAll = sysDictBodypartMapper.selectByExample(example);
		return CollectionUtils.isEmpty(selectAll)?"0":String.valueOf(Integer.valueOf(selectAll.get(0).getNindex())+1);
	}

	@Override
	public SysDictBodypart queryDictBodypartById(String id) {
		return sysDictBodypartMapper.selectByPrimaryKey(id);
	}

}
