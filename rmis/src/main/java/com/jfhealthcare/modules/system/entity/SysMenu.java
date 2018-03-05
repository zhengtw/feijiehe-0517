package com.jfhealthcare.modules.system.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
public class SysMenu {
	/**
	 * 菜单编号
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
	private String id;

	/**
	 * 菜单名
	 */
	private String mname;

	/**
	 * 上级菜单
	 */
	private String uppderId;

	/**
	 * 层级
	 */
	private Integer nlevel;//父级：1  子级：2

	/**
	 * 所属系统
	 */
	private String systemId;

	/**
	 * 排序
	 */
	private Integer nindex;

	/**
	 * BC_FLAG 页面或者C端展示  //页面展示 1  c端展示 2
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