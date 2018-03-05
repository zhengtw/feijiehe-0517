package com.jfhealthcare.modules.system.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
public class SysDepartment {
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
	private String id;

	/**
	 * 部门编号
	 */
	private String depName;

	/**
	 * 机构编号
	 */
	private String orgId;

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