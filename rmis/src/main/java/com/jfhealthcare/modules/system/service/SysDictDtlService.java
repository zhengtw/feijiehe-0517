package com.jfhealthcare.modules.system.service;


import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.system.entity.SysDictDtl;

public interface SysDictDtlService {

	public PageInfo<SysDictDtl> queryDictDtl(int pageNum,int pageSize) ;

	public void deleteDictDtl(String[] ids);

	public void updateDictDtl(SysDictDtl sysDictDtl);

	public PageInfo<SysDictDtl> selectDictDtlByKeys(SysDictDtl sysDictDtl,int pageNum,int pageSize);

	public void saveDictDtl(SysDictDtl sysDictDtl);
	
	public SysDictDtl quertDictDtlByDictId(String dictId);

	public SysDictDtl queryDictDtlById(String id);

	public List<SysDictDtl> quertDictDtlByCode(String string);
	
	public SysDictDtl quertDictDtlByDictIdAndName(String dictId,String name);

	public List<SysDictDtl> queryDictDtlByCode(String code);

}
