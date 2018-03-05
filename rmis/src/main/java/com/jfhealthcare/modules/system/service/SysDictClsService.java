package com.jfhealthcare.modules.system.service;

import java.util.List;

import com.jfhealthcare.modules.system.entity.SysDictCls;

public interface SysDictClsService {

	List<SysDictCls> queryDictCls();

	SysDictCls queryDictClsById(String id);

}
