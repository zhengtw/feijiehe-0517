package com.jfhealthcare.modules.system.request;

import lombok.Data;

@Data
public class SysArmariumOperRequest {
	/**
	 * 主键
	 */
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
	private boolean armarium;

	/**
	 * 医疗设备ID
	 */
	private boolean armariumId;
}
