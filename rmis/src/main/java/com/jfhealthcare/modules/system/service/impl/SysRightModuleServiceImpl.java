package com.jfhealthcare.modules.system.service.impl;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfhealthcare.modules.system.entity.SysRightModule;
import com.jfhealthcare.modules.system.mapper.SysRightModuleMapper;
import com.jfhealthcare.modules.system.service.SysRightModuleService;

@Service
public class SysRightModuleServiceImpl implements SysRightModuleService {

	@Autowired
	private SysRightModuleMapper sysRightModuleMapper;
	
	@Override
	public List<SysRightModule> querySysRightModule(String logincode) {
		SysRightModule sysRightModule=new SysRightModule();
		sysRightModule.setLogincode(logincode);
		List<SysRightModule> sysRightModules = sysRightModuleMapper.select(sysRightModule);
		return sysRightModules;
	}
	
	
	/**
	 * 菜单权限重新生成
	 */
	@Override
	@Transactional
    public void restartSysRightModule(String logincode) {
		//删除原先用户对应权限
    	SysRightModule sysRightModule=new SysRightModule();
    	sysRightModule.setLogincode(logincode);
    	sysRightModuleMapper.delete(sysRightModule);
    	
    	//去重
    	List<SysRightModule> distinctMenuIds=sysRightModuleMapper.querySysMenuForRestart(logincode);
    	Set<String> menuids = new  HashSet<String>();
    	for (SysRightModule srm : distinctMenuIds) {
    		menuids.add(srm.getMenuId());
		}
    	if(menuids.size()>0){
    		for(String menuId : menuids){  
        		SysRightModule newSysRightModule=new SysRightModule();
        		newSysRightModule.setLogincode(logincode);
        		newSysRightModule.setMenuId(menuId);
        		sysRightModuleMapper.insert(newSysRightModule);
            }
    	}
	}
	
   
}
