package com.jfhealthcare.modules.system.service;

import java.util.Map;

import com.jfhealthcare.modules.system.entity.SysToken;

public interface SysTokenService {

	SysToken queryByToken(String token);

	Map<String, Object> createToken(String logincode);

	void deleteToken(String logincode);

}
