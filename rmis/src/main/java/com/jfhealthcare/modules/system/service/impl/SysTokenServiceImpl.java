package com.jfhealthcare.modules.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfhealthcare.common.utils.NameUtils;
import com.jfhealthcare.modules.system.entity.SysToken;
import com.jfhealthcare.modules.system.mapper.SysOperatorMapper;
import com.jfhealthcare.modules.system.mapper.SysTokenMapper;
import com.jfhealthcare.modules.system.oauth2.TokenGenerator;
import com.jfhealthcare.modules.system.service.SysTokenService;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@Service
public class SysTokenServiceImpl implements SysTokenService {

	//24小时后过期
	private final static int EXPIRE = 3600 * 24;
	@Autowired
	private SysTokenMapper sysTokenMapper;
	@Autowired
	private SysOperatorMapper sysOperatorMapper;
	
	@Override
	public SysToken queryByToken(String token) {
		SysToken sysToken=new SysToken();
		sysToken.setToken(token);
		List<SysToken> sysTokens = sysTokenMapper.select(sysToken);
		return sysTokens.isEmpty()?null:sysTokens.get(0);
	}

	@Override
	@Transactional
	public Map<String, Object> createToken(String logincode) {
		String token = TokenGenerator.generateValue();
		SysToken sysToken=new SysToken();
		sysToken.setLogincode(logincode);
		List<SysToken> sysTokens = sysTokenMapper.select(sysToken);
		//当前时间
		Date now = new Date();
		//过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
		if(sysTokens.isEmpty()){
			sysToken.setToken(token);
			sysToken.setExpireTime(expireTime);
			sysToken.setCrtTime(now);
			sysTokenMapper.insert(sysToken);
		}else{
			sysToken = sysTokens.get(0);
			//多人登录 不做限制
			if(sysToken.getExpireTime().getTime() > System.currentTimeMillis()){
				token=sysToken.getToken();
				sysToken.setUpdTime(now);
				sysToken.setExpireTime(expireTime);
				sysTokenMapper.updateByPrimaryKeySelective(sysToken);
			}else{
				sysToken.setToken(token);
				sysToken.setUpdTime(now);
				sysToken.setExpireTime(expireTime);
				sysTokenMapper.updateByPrimaryKeySelective(sysToken);
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		map.put("expire", EXPIRE);
		return map;
	}
	
	@Override
	public void deleteToken(String logincode) {
		SysToken sysToken=new SysToken();
		sysToken.setLogincode(logincode);
		sysTokenMapper.delete(sysToken);
	}
}
