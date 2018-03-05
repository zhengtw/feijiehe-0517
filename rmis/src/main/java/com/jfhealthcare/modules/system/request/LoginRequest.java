package com.jfhealthcare.modules.system.request;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录请求
 */
@ApiModel(value = "LoginRequest", description = "登录对象")
@Data
public class LoginRequest {
	/**
	 * 登录名称
	 */
	@ApiModelProperty(value = "账号")
	@NotBlank(message="账号不能为空")
	private String logincode;
    
	/**
	 * 登录请求密码
	 */
	@ApiModelProperty(value = "密码")
	@NotBlank(message="密码不能为空")
	private String password;
	
}
