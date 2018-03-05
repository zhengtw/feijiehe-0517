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
public class SysMenuRequest extends BasicPageEntity{
	
	/**
	 * 菜单编号
	 */
	@NotBlank(message="ID不能为空",groups={Edit.class})
	private String id;

	/**
	 * 菜单名
	 */
	private String mname;

	/**
	 * 上级菜单
	 */
	@NotBlank(message="上級目录不能为空",groups={Insert.class,Edit.class})
	private String uppderId;

	/**
	 * 层级
	 */
	private Integer nlevel;

	/**
	 * 所属系统
	 */
	private String systemId;

	/**
	 * 排序
	 */
	private Integer nindex;

	/**
	 * BC_FLAG 页面或者C端展示
	 */
	private Integer bcFlag;

	/**
	 * 页面地址
	 */
	private String page;

	/**
	 * 图标
	 */
	private String pic;

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
	 * 修改人
	 */
	private String updUser;

	/**
	 * 修改时间
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