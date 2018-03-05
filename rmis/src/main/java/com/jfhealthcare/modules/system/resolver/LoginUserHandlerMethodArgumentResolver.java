package com.jfhealthcare.modules.system.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.common.enums.RedisEnum;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.common.utils.RedisUtils;
import com.jfhealthcare.modules.system.annotation.LoginUser;
import com.jfhealthcare.modules.system.interceptor.AuthorizationInterceptor;
import com.jfhealthcare.modules.system.service.ShiroService;

import lombok.extern.slf4j.Slf4j;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 * @author xujinma
 */
@Slf4j
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	@Autowired
	private ShiroService shiroService;
	
    @Autowired
	private RedisUtils redisUtils;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(LoginUserEntity.class) && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
        //获取用户ID
        Object object = request.getAttribute(AuthorizationInterceptor.LOGIN_USER_KEY, RequestAttributes.SCOPE_REQUEST);
        if(object == null){
            return null;
        }
        //获取用户信息
        
        LoginUserEntity loginUserEntity=null;
        try {
        	loginUserEntity= redisUtils.get(RedisEnum.LOGINCODE.getValue()+":"+(String)object, LoginUserEntity.class);
			if(loginUserEntity==null){
				loginUserEntity =shiroService.queryLoginUser((String)object);
				redisUtils.set(RedisEnum.LOGINCODE.getValue()+":"+(String)object, loginUserEntity, redisUtils.DEFAULT_EXPIRE*7);
			}
			if(loginUserEntity==null || loginUserEntity.getSysOperator()==null){
				throw new RmisException("用户注入异常！");
			}
		} catch (Exception e) {
			log.error("用户注入异常！", e);
		}
        
        
        return loginUserEntity;
    }
}
