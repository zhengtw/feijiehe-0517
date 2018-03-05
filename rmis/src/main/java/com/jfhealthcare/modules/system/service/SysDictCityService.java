package com.jfhealthcare.modules.system.service;

import java.util.List;

import com.jfhealthcare.modules.system.entity.SysDictCity;

public interface SysDictCityService {
	List<SysDictCity> queryDictCity(int level, int code);

}
