package com.jfhealthcare.modules.system.service;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.system.entity.SysDictScanmethod;
import com.jfhealthcare.modules.system.request.SysDictBodypartRequest;
import com.jfhealthcare.modules.system.request.SysDictScanmethodRequest;

public interface SysDictScanmethodService {

	PageInfo<SysDictScanmethod> queryDictScanmethod(SysDictScanmethodRequest sysDictScanmethodRequest);

	void deleteDictScanmethod(String id);

	void updateDictScanmethod(SysDictScanmethodRequest sysDictScanmethodRequest);

	void saveDictScanmethod(SysDictScanmethodRequest sysDictScanmethodRequest);

	String queryMaxNindex(String code);

	SysDictScanmethod queryDictScanmethodById(String id);


}
