package com.jfhealthcare.modules.system.request;

import org.hibernate.validator.constraints.NotBlank;

import com.jfhealthcare.common.validator.group.Edit;

import lombok.Data;

@Data
public class OrdinaryOperatorRequest {
	private String oldPassword;
	private String newPassword;
	private String homePage;
	/*
	 * 1为修改密码  2为修改用户其他可修改参数
	@Range(min=1,max=2,message="operateType非法!",groups={All.class})
	@NotBlank(message="operateType非法!",groups={All.class})
	private int operateType;*/
	@NotBlank(message="id必填!",groups={Edit.class})
	private String id;
	
	private String armariums;
	@NotBlank(message="用户名必填!",groups={Edit.class})
	private String logincode;
	//签名
	private String signature;
}
