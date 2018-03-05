package com.jfhealthcare.modules.system.request;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.common.validator.group.Insert;
import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;

@Data
public class SysDepartmentRequest extends BasicPageEntity{
	/**
	 * 主键
	 */
	@NotBlank(message="部门ID不能为空",groups = Edit.class)
	private String id;

	/**
	 * 部门编号
	 */
	private String depName;

	/**
	 * 机构编号
	 */
	@NotBlank(message="机构编号不能为空",groups = {Edit.class,Insert.class})
	private String orgId;

	/**
	 * 机构名称
	 */
	private String orgName;

	/**
	 * 上级
	 */
	private String upperid;

	/**
	 * 层级
	 */
	private Integer nlevel;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 传真
	 */
	private String fax;

	/**
	 * 邮箱
	 */
	@Email(message="输入邮箱不符合规范",groups={Insert.class,Edit.class})
	private String email;

	/**
	 * 部门类型CODE
	 */
	private String depTypeCode;

	/**
	 * 部门类型
	 */
	private String depType;

	/**
	 * 排序值
	 */
	private String nindex;

	/**
	 * 负责人
	 */
	private String chargePerson;
	
	 /**
     * 拼音简码
     */
    private String namepy;

    /**
     * 五笔简码
     */
    private String namewb;

	/**
	 * 状态
	 */
	private Boolean status;

	/**
	 * 逻辑删除
	 */
	private Boolean isdelete;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 更改人
	 */
	private String updUser;

	/**
	 * 更改时间
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