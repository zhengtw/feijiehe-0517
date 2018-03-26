package com.jfhealthcare.modules.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfhealthcare.common.utils.NameUtils;
import com.jfhealthcare.common.utils.TransferUtils;
import com.jfhealthcare.modules.system.entity.SysMenu;
import com.jfhealthcare.modules.system.entity.SysOperRole;
import com.jfhealthcare.modules.system.entity.SysOrganization;
import com.jfhealthcare.modules.system.entity.SysRightModset;
import com.jfhealthcare.modules.system.entity.SysRightModule;
import com.jfhealthcare.modules.system.mapper.SysMenuMapper;
import com.jfhealthcare.modules.system.mapper.SysRightModsetMapper;
import com.jfhealthcare.modules.system.request.SysMenuRequest;
import com.jfhealthcare.modules.system.service.SysMenuService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


@Service
public class SysMenuServiceImpl implements SysMenuService {
	
	@Autowired
	private SysMenuMapper sysMenuMapper;

	/*@Autowired
	private SysRightModuleMapper sysRightModuleMapper;*/
	
	@Autowired
	private SysRightModsetMapper sysRightModsetMapper;
	
	@Override
	public List<Map<String, Object>> querySysMenu() {
		List<Map<String, Object>> allMnus=new ArrayList<Map<String, Object>>();
		SysMenu sysMenu=new SysMenu();
		sysMenu.setUppderId("0");
		List<Map<String, Object>> fatherMenus = sysMenuMapper.querySysMenuBycondition(sysMenu);
		for (Map<String, Object> map : fatherMenus) {
			sysMenu.setUppderId((String)map.get("id"));
			List<Map<String, Object>> chlidsMenus = sysMenuMapper.querySysMenuBycondition(sysMenu);
			map.put("children", chlidsMenus);
			allMnus.add(map);
		}
		return allMnus;
	}

	@Override
	public SysMenu querySingleSysMenu(String id) {
		SysMenu selectByPrimaryKey = sysMenuMapper.selectByPrimaryKey(id);
		return selectByPrimaryKey;
	}

	@Override
	public void insertSysMenu(SysMenuRequest sysMenuRequest) {
		SysMenu sysMenu=new SysMenu();
		TransferUtils.copyPropertiesIgnoreNull(sysMenuRequest, sysMenu);
		sysMenu.setBcFlag(1);
		sysMenu.setCrtTime(new Date());
		sysMenu.setCrtUser(NameUtils.getLoginCode());
		sysMenuMapper.insertSelective(sysMenu);
	}

	@Override
	public void updateSysMenu(SysMenuRequest sysMenuRequest) {
		SysMenu sysMenu=new SysMenu();
		TransferUtils.copyPropertiesIgnoreNull(sysMenuRequest, sysMenu);
		sysMenu.setUpdTime(new Date());
		sysMenu.setUpdUser(NameUtils.getLoginCode());
		sysMenuMapper.updateByPrimaryKey(sysMenu);
	}

	@Override
	@Transactional
	public void deleteSysMenu(String id) {
		Set<String> menuIds=new HashSet<String>();
		SysMenu sysmenu = sysMenuMapper.selectByPrimaryKey(id);
		if(sysmenu !=null && "0".equals(sysmenu.getUppderId())){
			menuIds.add(id);
			SysMenu sc=new SysMenu();
			sc.setUppderId(id);
			List<SysMenu> select = sysMenuMapper.select(sc);
			for (SysMenu sm : select) {
				menuIds.add(sm.getId());
			}
		}else if(sysmenu !=null && !"0".equals(sysmenu.getUppderId())){
			menuIds.add(id);
		}
		
		if(menuIds.size()>0){
			for (String menuId : menuIds) {
				/*SysRightModule srmo=new SysRightModule();
				srmo.setMenuId(menuId);
				sysRightModuleMapper.delete(srmo);*/
				SysRightModset srmt=new SysRightModset();
				srmt.setMenuId(menuId);
				sysRightModsetMapper.delete(srmt);
				sysMenuMapper.deleteByPrimaryKey(menuId);
			}
		}
	}

	@Override
	public List<Map<String, Object>> querySysMenuForLogin(String logincode) {
		List<Map<String, Object>> allMnus=new ArrayList<Map<String, Object>>();
		Map<String, Object> parmMap=new HashMap<String, Object>();
//		parmMap.put("uppderId", "0");
		parmMap.put("logincode", logincode);
//		List<Map<String, Object>> fatherMenus = sysMenuMapper.querySysMenuByconditionForLogin(parmMap);
		List<Map<String, Object>> fatherMenus = sysMenuMapper.querySysMenuForFatherId(logincode);
		if(!fatherMenus.isEmpty()){
			for (Map<String, Object> map : fatherMenus) {
				parmMap.put("uppderId", map.get("id"));
				List<Map<String, Object>> chlidsMenus = sysMenuMapper.querySysMenuByconditionForLogin(parmMap);
				map.put("children", chlidsMenus);
				allMnus.add(map);
			}
		}
		return allMnus;
	}

	@Override
	public List<SysRightModule> querySysMenuForClick(String logincode) {
		return sysMenuMapper.querySysMenuForClick(logincode);
	}
	
	@Override
	public List<SysRightModset> querySysMenuForRole(String roleId) {
		SysRightModset sysRightModset=new SysRightModset();
		sysRightModset.setLogincode(roleId);
		sysRightModset.setObjflag(2);
		List<SysRightModset> select = sysRightModsetMapper.select(sysRightModset);
		return select;
	}

	@Override
	public List<SysMenu> queryUppderSysMenu() {
		SysMenu sysMenu=new SysMenu();
		sysMenu.setUppderId("0");
		List<SysMenu> sysMenus = sysMenuMapper.select(sysMenu);
		return sysMenus;
	}

	@Override
	public Integer querySysMenuMaxNindex(String faMenuId) {
		
		
		Integer maxNindex=sysMenuMapper.querySysMenuMaxNindex(faMenuId);
		
		return maxNindex;
	}

	@Override
	public Boolean querySysMenuByNindexAndFaMenuId(String faMenuId, Integer nindex) {
		SysMenu sysMenu=new SysMenu();
		sysMenu.setUppderId(faMenuId);
		sysMenu.setNindex(nindex);
		List<SysMenu> sysMenus = sysMenuMapper.select(sysMenu);
		if(sysMenus.isEmpty()){
			return true;	
		}
		return false;
	}

   
}
