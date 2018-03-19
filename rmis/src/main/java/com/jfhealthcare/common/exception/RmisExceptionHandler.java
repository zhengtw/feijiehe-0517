package com.jfhealthcare.common.exception;

import org.apache.shiro.authz.AuthorizationException;
//import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jfhealthcare.common.base.BaseResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 异常处理器
 * 
 * @author xujinma
 */
@Slf4j
@RestControllerAdvice
public class RmisExceptionHandler {
	
	/**
	 * 自定义异常
	 */
	@ExceptionHandler(RmisException.class)
	public BaseResponse handleRRException(RmisException e){
		if("token不能为空".equals(e.getMessage()) || "token失效，请重新登录".equals(e.getMessage())){
//			log.error("token异常：=========="+e.getMessage()+"==========");
			return BaseResponse.getFailResponse(e.getCode(), e.getMessage());
		}
		if("version不能为空".equals(e.getMessage()) || "版本更新，需要升级".equals(e.getMessage())){
//			log.error("版本异常：=========="+e.getMessage()+"==========");
			return BaseResponse.getFailResponse(e.getCode(), e.getMessage());
		}
		log.error(e.getMessage(), e);
		return BaseResponse.getFailResponse(e.getCode(), e.getMessage());
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public BaseResponse handleDuplicateKeyException(DuplicateKeyException e){
		log.error(e.getMessage(), e);
		return BaseResponse.getFailResponse("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public BaseResponse handleAuthorizationException(AuthorizationException e){
		log.error(e.getMessage(), e);
		return BaseResponse.getFailResponse("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(Exception.class)
	public BaseResponse handleException(Exception e){
		if("帐号或密码不正确！".equals(e.getMessage())){
			log.error("===========帐号或密码不正确！==========");
			return BaseResponse.getFailResponse(e.getMessage());
		}
		log.error(e.getMessage(), e);
		return BaseResponse.getFailResponse(e.getMessage());
	}
}
