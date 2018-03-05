package com.jfhealthcare.modules.system.service;


import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.system.entity.SysDict;

public interface SysDictService {

	PageInfo<SysDict> queryDict(int pageNum,int pageSize);

	void deleteDict(String[] ids);

	void updateDict(SysDict sysDict);

	PageInfo<SysDict> selectDictByKeys(SysDict sysDict,int pageNum,int pageSize);

	void saveDict(SysDict sysDict);

	SysDict queryDictById(String id);

}
