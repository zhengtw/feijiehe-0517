package com.jfhealthcare.modules.system.service;

import com.jfhealthcare.modules.system.entity.SysWord;

public interface SysWordService {

	SysWord querySimpleSysWord(String word);

	SysWord queryFullSysWord(String word);

}
