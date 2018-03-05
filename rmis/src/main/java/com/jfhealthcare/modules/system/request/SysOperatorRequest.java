package com.jfhealthcare.modules.system.request;

import org.hibernate.validator.constraints.NotBlank;

import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.common.validator.group.Insert;

import lombok.Data;

@Data
public class SysOperatorRequest {
	 /**
     * 登陆名
     */
	@NotBlank(message="登陆名不能为空!",groups={Insert.class})
    private String logincode;

    /**
     * 用户编号
     */
	@NotBlank(message="ID不能为空!",groups={Edit.class})
    private String id;

    /**
     * 密码
     */
	@NotBlank(message="密码不能为空!",groups={Insert.class})
    private String password;

    /**
     * 审核Code
     */
    private String adminCode;

    /**
     * 审核
     */
    private String admin;

    /**
     * 自定义主页
     */
    private String homePage;

    /**
     * 启用状态
     */
    private Boolean status;


    /**
     * 备注
     */
    private String remark;


    /**
     * 签名
     */
    private String signature;
}
