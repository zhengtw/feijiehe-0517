package com.jfhealthcare.modules.system.service;

import java.util.List;
import java.util.Map;

public interface SysRightModsetService {

	void updateSysRightModset(String logincodeOrRoleId,List<String> menuIds,int flag);

	void updateSysRightModsetForOper(String logincodeOrRoleId, List<String> menuIds, int i);

	void updateSysRightModsetForRole(String logincodeOrRoleId, List<String> menuIds, int i);

}
