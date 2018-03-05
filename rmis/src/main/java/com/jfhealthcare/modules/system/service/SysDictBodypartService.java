package com.jfhealthcare.modules.system.service;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.system.entity.SysDictBodypart;
import com.jfhealthcare.modules.system.request.SysDictBodypartRequest;

public interface SysDictBodypartService {

	PageInfo<SysDictBodypart> queryDictBodypart(SysDictBodypartRequest sysDictBodypartRequest);

	void deleteDictBodypart(String id);

	void updateDictBodypart(SysDictBodypartRequest sysDictBodypartRequest);

	void saveDictBodypart(SysDictBodypartRequest sysDictBodypartRequest);

	String queryMaxNindex(String code);

	SysDictBodypart queryDictBodypartById(String id);

}
