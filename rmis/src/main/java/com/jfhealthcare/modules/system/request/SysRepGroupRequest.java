package com.jfhealthcare.modules.system.request;


import org.hibernate.validator.constraints.NotBlank;

import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.common.validator.group.Insert;
import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;
@Data
public class SysRepGroupRequest extends BasicPageEntity{
	
    private String id;
    
	@NotBlank(message="groupId不能为空!",groups={Edit.class})
    private String groupId;
	
	@NotBlank(message="logincodes不能为空!",groups={Edit.class})
    private String logincodes;
}
