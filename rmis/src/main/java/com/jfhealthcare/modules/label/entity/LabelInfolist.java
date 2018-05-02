package com.jfhealthcare.modules.label.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Table(name = "label_info_list")
@Data
public class LabelInfolist {
	/**
	 * ID_标注流水号
	 */
	@Id
	@Column(name = "LABEL_ACCNUM")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
	private String labelAccnum;

	@Column(name = "series_uid")
	private String seriesUid;

	/**
	 * 标注状态CODE
	 */
	@Column(name = "STATUS_CODE")
	private String statusCode;

	/**
	 * 标注状态
	 */
	@Column(name = "STATUS")
	private String status;

	/**
	 * 标注人
	 */
	@Column(name = "LABEL_USER")
	private String labelUser;

	/**
	 * 标注时间
	 */
	@Column(name = "LABEL_TIME")
	private Date labelTime;

	/**
	 * 审核人
	 */
	@Column(name = "AUDIT_USER")
	private String auditUser;

	/**
	 * 审核时间
	 */
	@Column(name = "AUDIT_TIME")
	private Date auditTime;

	/**
	 * 标注描述
	 */
	@Column(name = "DESCRIBE")
	private String describe;

	/**
	 * 备注
	 */
	@Column(name = "REMARK")
	private String remark;

	@Column(name = "CRT_USER")
	private String crtUser;

	@Column(name = "CRT_TIME")
	private Date crtTime;

	@Column(name = "UPD_USER")
	private String updUser;

	@Column(name = "UPD_TIME")
	private Date updTime;
}