package com.jfhealthcare.modules.system.request;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;

@Data
public class SysRoleRequest extends BasicPageEntity{
	/**
	 * 角色编号
	 */
	@NotBlank(message = "id不能为空!", groups = Edit.class)
	private String id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 拼音简码
     */
    private String namepy;

    /**
     * 五笔简码
     */
    private String namewb;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 逻辑删除
     */
    private Boolean isdelete;

    /**
     * 更新人
     */
    private String updUser;

    /**
     * 更新时间
     */
    private Date updTime;

    /**
     * 创建人
     */
    private String crtUser;

    /**
     * 创建时间
     */
    private Date crtTime;
}
