package com.jfhealthcare.modules.system.service.impl;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.modules.system.entity.SysArmariumOper;
import com.jfhealthcare.modules.system.entity.SysOperator;
import com.jfhealthcare.modules.system.entity.SysOperatorDtl;
import com.jfhealthcare.modules.system.entity.SysRightModule;
import com.jfhealthcare.modules.system.entity.SysRole;
import com.jfhealthcare.modules.system.entity.SysToken;
import com.jfhealthcare.modules.system.mapper.SysTokenMapper;
import com.jfhealthcare.modules.system.mapper.SysArmariumOperMapper;
import com.jfhealthcare.modules.system.mapper.SysMenuMapper;
import com.jfhealthcare.modules.system.mapper.SysOperatorDtlMapper;
import com.jfhealthcare.modules.system.mapper.SysOperatorMapper;
import com.jfhealthcare.modules.system.mapper.SysRightModuleMapper;
import com.jfhealthcare.modules.system.mapper.SysRoleMapper;
import com.jfhealthcare.modules.system.service.ShiroService;
import com.jfhealthcare.modules.system.service.SysTokenService;

import java.util.*;

@Service
public class ShiroServiceImpl implements ShiroService {
	
	@Autowired
	private SysTokenMapper sysTokenMapper;
	
	@Autowired
	private SysOperatorMapper sysOperatorMapper;
	
	/*@Autowired
	private SysRightModuleMapper sysRightModuleMapper;*/
	
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	@Autowired
	private SysTokenService sysTokenService;
	
	@Autowired
    private SysOperatorDtlMapper sysOperatorDtlMapper;
	
	@Autowired
    private SysArmariumOperMapper sysArmariumOperMapper;
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Value("${yun.image.url.path}")
	private String imageUrl;
	
	@Override
	public Set<String> getUserPermissions(String logincode) {
		Set<String> permsSet = new HashSet<>();
		List<SysRightModule> sysRightModules = sysMenuMapper.querySysMenuForClick(logincode);
		/*SysRightModule sysRightModule=new SysRightModule();
		sysRightModule.setLogincode(logincode);
		List<SysRightModule> sysRightModules = sysRightModuleMapper.select(sysRightModule);*/
		if(!sysRightModules.isEmpty()){
			sysRightModules.forEach(srm->permsSet.add(srm.getMenuId()));
		}
		return permsSet;
	}

	@Override
	public SysToken queryByToken(String token) {
		return sysTokenService.queryByToken(token);
	}

	@Override
	public SysOperator queryUser(String logincode) {
		SysOperator sysOperator=new SysOperator();
		sysOperator.setLogincode(logincode);
		List<SysOperator> sysOperators = sysOperatorMapper.select(sysOperator);
		return sysOperators.isEmpty()?null:sysOperators.get(0);
	}

	@Override
	public LoginUserEntity queryLoginUser(String logincode) {
		LoginUserEntity loginUserEntity=new LoginUserEntity();
		SysOperator sysOperator=new SysOperator();
		sysOperator.setLogincode(logincode);
		List<SysOperator> sysOperators = sysOperatorMapper.select(sysOperator);
		if(!CollectionUtils.isEmpty(sysOperators)){
			loginUserEntity.setSysOperator(sysOperators.get(0));
		}
		SysOperatorDtl sysOperatorDtl=new SysOperatorDtl();
		sysOperatorDtl.setLogincode(logincode);
		List<SysOperatorDtl> sysOperatorDtls = sysOperatorDtlMapper.select(sysOperatorDtl);
		if(!CollectionUtils.isEmpty(sysOperatorDtls)){
			loginUserEntity.setSysOperatorDtl(sysOperatorDtls.get(0));
		}
		SysArmariumOper sysArmariumOper=new SysArmariumOper();
		sysArmariumOper.setLogincode(logincode);
		sysArmariumOper.setStatus(true);
		List<SysArmariumOper> sysArmariumOpers = sysArmariumOperMapper.select(sysArmariumOper);
		if(!CollectionUtils.isEmpty(sysArmariumOpers)){
			loginUserEntity.setSysArmariumOpers(sysArmariumOpers);
		}
		
		List<SysRole> roles = sysRoleMapper.selectRolesByLoginCode(logincode);
		if(!CollectionUtils.isEmpty(roles)){
			loginUserEntity.setSysRoles(roles);
		}
		
		loginUserEntity.setCloudImageUrl(imageUrl);
		return loginUserEntity;
	}

   
}
