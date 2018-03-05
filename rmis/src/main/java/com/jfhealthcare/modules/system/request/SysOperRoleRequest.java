package com.jfhealthcare.modules.system.request;


import org.hibernate.validator.constraints.NotBlank;

import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.common.validator.group.Insert;
import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;
@Data
public class SysOperRoleRequest extends BasicPageEntity{
	@NotBlank(message="id不能为空!",groups={Edit.class})
    private String id;
	@NotBlank(message="roleId不能为空!",groups={Insert.class})
    private String roleId;
	@NotBlank(message="logincode不能为空!",groups={Insert.class})
    private String logincode;
	
	private String namepy;
}
