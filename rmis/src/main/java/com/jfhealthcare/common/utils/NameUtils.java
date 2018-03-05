package com.jfhealthcare.common.utils;

import javax.servlet.http.HttpServletRequest;

import com.jfhealthcare.modules.system.interceptor.AuthorizationInterceptor;

/**
 * 获取登录用户名
 * @author xujinma
 */
public class NameUtils {
    public static String getLoginCode() {  
    	HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
    	String logincode = (String) request.getAttribute(AuthorizationInterceptor.LOGIN_USER_KEY);
        return logincode;  
    } 
}
