package com.jfhealthcare.modules.system.interceptor;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.modules.system.annotation.AuthIgnore;
import com.jfhealthcare.modules.system.entity.SysDictDtl;
import com.jfhealthcare.modules.system.entity.SysToken;
import com.jfhealthcare.modules.system.service.SysDictDtlService;
import com.jfhealthcare.modules.system.service.SysTokenService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 * @author xujinma
 */
@Slf4j
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private SysTokenService sysTokenService;
    
    @Autowired
    private SysDictDtlService sysDictDtlService;

    public static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuthIgnore authIgnore;
        if(handler instanceof HandlerMethod) {
        	authIgnore = ((HandlerMethod) handler).getMethodAnnotation(AuthIgnore.class);
        }else{
            return true;
        }

        //如果有@IgnoreAuth注解，则不验证token
        if(authIgnore != null){
            return true;
        }
        
        //从头信息中获取版本号
        String version = request.getHeader("version");
        if(StringUtils.isBlank(version)){
        	version = request.getParameter("version");
        }
        
        if(StringUtils.isBlank(version)){
            throw new RmisException("version不能为空");
        }
        List<SysDictDtl> versions = sysDictDtlService.queryDictDtlByCode("version");
        if(!StringUtils.equalsIgnoreCase(version, versions.get(0).getOthervalue())) {
        	throw new RmisException("版本更新，需要升级",556);
        }
        
        //从header中获取token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = request.getParameter("token");
        }

        //token为空
        if(StringUtils.isBlank(token)){
            throw new RmisException("token不能为空");
        }

        //查询token信息
        SysToken sysToken = sysTokenService.queryByToken(token);
        if(sysToken == null || sysToken.getExpireTime().getTime() < System.currentTimeMillis()){
            throw new RmisException("token失效，请重新登录",555);
        }
        //设置logincode到request里，后续根据logincode，获取用户信息
        request.setAttribute(LOGIN_USER_KEY, sysToken.getLogincode());

        return super.preHandle(request, response, handler);
    }
}
