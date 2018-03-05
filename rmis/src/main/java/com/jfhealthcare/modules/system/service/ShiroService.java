package com.jfhealthcare.modules.system.service;


import java.util.Set;

import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.modules.system.entity.SysOperator;
import com.jfhealthcare.modules.system.entity.SysToken;

/**
 * shiro相关接口
 * @author xujinma
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(String logincode);

    SysToken queryByToken(String token);

    /**
     * 根据用户logincode，查询用户
     */
    SysOperator queryUser(String logincode);

	LoginUserEntity queryLoginUser(String object);
}
