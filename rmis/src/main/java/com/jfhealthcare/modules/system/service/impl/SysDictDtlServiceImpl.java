package com.jfhealthcare.modules.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.modules.system.entity.SysDictDtl;
import com.jfhealthcare.modules.system.mapper.SysDictDtlMapper;
import com.jfhealthcare.modules.system.service.SysDictDtlService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SysDictDtlServiceImpl implements SysDictDtlService {
	@Autowired
	private SysDictDtlMapper sysDictDtlMapper;

	@Override
	public PageInfo<SysDictDtl> queryDictDtl(int pageNum,int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		Example example = new Example(SysDictDtl.class);
		example.createCriteria().andEqualTo("isdelete",Boolean.FALSE);
		List<SysDictDtl> selectAll = sysDictDtlMapper.selectByExample(example);
		PageInfo<SysDictDtl> pageInfo = new PageInfo<SysDictDtl>(selectAll);
		return pageInfo;
	}

	@Override
	public void deleteDictDtl(String[] ids) {
		SysDictDtl sysdtl = new SysDictDtl();
		for (String id : ids) {
			sysDictDtlMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public void updateDictDtl(SysDictDtl sysDictDtl) {
		//先查询用户的目录是否为锁定状态！或者是否存在！
		SysDictDtl sys = new SysDictDtl();
		sys.setIsdelete(Boolean.FALSE);
		sys.setId(sysDictDtl.getId());
		SysDictDtl one = sysDictDtlMapper.selectOne(sys);
		if(one==null){
			throw new RmisException("该目录详情不存在!");
		}
		sysDictDtlMapper.updateByPrimaryKey(sysDictDtl);

	}

	@Override
	public PageInfo<SysDictDtl> selectDictDtlByKeys(SysDictDtl sysDictDtl,int pageNum,int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		Example example = new Example(SysDictDtl.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("isdelete", Boolean.FALSE);
		if(StringUtils.isNotBlank(sysDictDtl.getDictId())){
			criteria.andEqualTo("dictId",sysDictDtl.getDictId());
		}
		if(StringUtils.isNotBlank(sysDictDtl.getId())){
			criteria.andEqualTo("id", sysDictDtl.getId());
		}
		if(StringUtils.isNotBlank(sysDictDtl.getNamepy())){
			criteria.andCondition("(namepy like \"%"+sysDictDtl.getNamepy()+"%\"" +"or namewb like \"%"+sysDictDtl.getNamepy()+"%\")");
		}
		if(StringUtils.isNotBlank(sysDictDtl.getOthervalue2())){
			criteria.andCondition("(OTHERVALUE2 like \"%"+sysDictDtl.getOthervalue2()+"%\")");
		}
		List<SysDictDtl> selectByExample = sysDictDtlMapper.selectByExample(example);
		PageInfo<SysDictDtl> pageInfo = new PageInfo<SysDictDtl>(selectByExample);
		return pageInfo;
	}

	@Override
	public void saveDictDtl(SysDictDtl sysDictDtl) {
		sysDictDtlMapper.insertSelective(sysDictDtl);
	}

	@Override
	public SysDictDtl queryDictDtlById(String id) {
		SysDictDtl sysDictDtl = new SysDictDtl();
		sysDictDtl.setId(id);
		sysDictDtl.setIsdelete(Boolean.FALSE);
		return sysDictDtlMapper.selectOne(sysDictDtl);
	}

	@Override
	public List<SysDictDtl> quertDictDtlByCode(String name) {
		SysDictDtl sysDictDtl = new SysDictDtl();
		sysDictDtl.setCode(name);
		sysDictDtl.setIsdelete(Boolean.FALSE);
		List<SysDictDtl> sysDictDtls = sysDictDtlMapper.select(sysDictDtl);
		return sysDictDtls;
	}

	@Override
	public SysDictDtl quertDictDtlByDictId(String dictId) {
		SysDictDtl sysDictDtl = new SysDictDtl();
		sysDictDtl.setDictId(dictId);
		sysDictDtl.setIsdelete(Boolean.FALSE);
		List<SysDictDtl> sysDictDtls = sysDictDtlMapper.select(sysDictDtl);
		return sysDictDtls.isEmpty()?null:sysDictDtls.get(0);
	}
	
	@Override
	public SysDictDtl quertDictDtlByDictIdAndName(String dictId,String name) {
		SysDictDtl sysDictDtl = new SysDictDtl();
		sysDictDtl.setDictId(dictId);
		sysDictDtl.setName(name);
		sysDictDtl.setIsdelete(Boolean.FALSE);
		List<SysDictDtl> sysDictDtls = sysDictDtlMapper.select(sysDictDtl);
		return sysDictDtls.isEmpty()?null:sysDictDtls.get(0);
	}

	@Override
	public List<SysDictDtl> queryDictDtlByCode(String code) {
		SysDictDtl sysDictDtl = new SysDictDtl();
		sysDictDtl.setCode(code);
		sysDictDtl.setIsdelete(Boolean.FALSE);
		List<SysDictDtl> sysDictDtls = sysDictDtlMapper.select(sysDictDtl);
		return CollectionUtils.isEmpty(sysDictDtls)?new ArrayList<SysDictDtl>():sysDictDtls;
	}

	@Override
	public SysDictDtl quertDictDtlByCodeAndName(String code, String name) {
		SysDictDtl sysDictDtl = new SysDictDtl();
		sysDictDtl.setCode(code);
		sysDictDtl.setName(name);
		sysDictDtl.setIsdelete(Boolean.FALSE);
		List<SysDictDtl> sysDictDtls = sysDictDtlMapper.select(sysDictDtl);
		return CollectionUtils.isEmpty(sysDictDtls)?null:sysDictDtls.get(0);
	}
}
