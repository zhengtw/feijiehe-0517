package com.jfhealthcare.modules.system.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
public class SysArmariumOper {
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
	private String id;

	/**
	 * 登陆名
	 */
	private String logincode;

	/**
	 * 使用状态
	 */
	private boolean status;

	/**
	 * 医疗设备名称
	 */
	private String armarium;

	/**
	 * 医疗设备ID
	 */
	private String armariumId;

}
