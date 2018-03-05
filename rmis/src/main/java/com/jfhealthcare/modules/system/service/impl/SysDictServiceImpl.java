package com.jfhealthcare.modules.system.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.common.utils.NameUtils;
import com.jfhealthcare.modules.system.entity.SysDict;
import com.jfhealthcare.modules.system.entity.SysDictDtl;
import com.jfhealthcare.modules.system.mapper.SysDictDtlMapper;
import com.jfhealthcare.modules.system.mapper.SysDictMapper;
import com.jfhealthcare.modules.system.service.SysDictService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SysDictServiceImpl implements SysDictService {
	@Autowired
	private SysDictMapper sysDictMapper;
	@Autowired
	private SysDictDtlMapper sysDictDtlMapper;

	@Override
	public PageInfo<SysDict> queryDict(int pageNum,int pageSize) {
		
		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(SysDict.class);
		example.createCriteria().andEqualTo("isdelete",Boolean.FALSE);
		List<SysDict> selectAll = sysDictMapper.selectByExample(example);
		PageInfo<SysDict> pageInfo = new PageInfo<SysDict>(selectAll);
		return pageInfo;
	}

	@Override
	@Transactional
	public void deleteDict(String[] ids) {
		SysDict sysDi = new SysDict(); //组装查询条件
		SysDictDtl sysdtl = new SysDictDtl();//组装改变参数
		sysdtl.setIsdelete(Boolean.TRUE);
		for (String id : ids) {
			// 查询字典目录信息,判断是否锁定，锁定状态下，用户需为root
			sysDi.setIsdelete(Boolean.FALSE);
			sysDi.setId(id);
			SysDict sysDict = sysDictMapper.selectOne(sysDi);
			if(sysDict==null){
				continue;
			}
			sysDict.setIsdelete(Boolean.TRUE);
			sysDict.setUpdTime(new Date());
			sysDict.setUpdUser(NameUtils.getLoginCode());
			sysDictMapper.updateByPrimaryKey(sysDict);
			//删除目录中的相关详情
			Example example = new Example(SysDictDtl.class);
			example.createCriteria().andEqualTo("dictId",id);
			sysdtl.setUpdTime(new Date());
			sysdtl.setUpdUser(NameUtils.getLoginCode());
			sysDictDtlMapper.updateByExampleSelective(sysdtl, example);
		}

	}

	@Override
	public void updateDict(SysDict sysDict) {
		SysDict sys = new SysDict();
		sys.setId(sysDict.getId());
		sys.setIsdelete(Boolean.FALSE);
		SysDict one = sysDictMapper.selectOne(sys);
		if(one==null){
			throw new RmisException("该目录不存在!");
		}
		sysDictMapper.updateByPrimaryKey(sysDict);
	}

	@Override
	public PageInfo<SysDict> selectDictByKeys(SysDict sysDict,int pageNum,int pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		Example example = new Example(SysDict.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("isdelete", Boolean.FALSE);
		if(StringUtils.isNotBlank(sysDict.getClsId())){
			criteria.andEqualTo("clsId",sysDict.getClsId());
		}
		if(StringUtils.isNotBlank(sysDict.getId())){
			criteria.andEqualTo("id", sysDict.getId());
		}
		if(StringUtils.isNotBlank(sysDict.getNamepy())){
			criteria.andCondition("(namepy like \"%"+sysDict.getNamepy()+"%\"" +"or namewb like \"%"+sysDict.getNamepy()+"%\")");
		}
		List<SysDict> selectByExample = sysDictMapper.selectByExample(example);
		PageInfo<SysDict> pageInfo = new PageInfo<SysDict>(selectByExample);
		return pageInfo;
	}

	@Override
	public void saveDict(SysDict sysDict) {
		sysDictMapper.insertSelective(sysDict);
	}

	@Override
	public SysDict queryDictById(String id) {
		SysDict sysDict= new SysDict();
		sysDict.setId(id);
		sysDict.setIsdelete(Boolean.FALSE);
		return sysDictMapper.selectOne(sysDict);
	}

}
