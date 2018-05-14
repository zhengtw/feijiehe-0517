package com.jfhealthcare.modules.label.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Table(name = "label_info")
@Data
public class LabelInfo {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
	private String id;

	@Column(name = "UID")
	private String uid;

	@Column(name = "STUDY_UID")
	private String studyUid;

	@Column(name = "SERIES_UID")
	private String seriesUid;

	@Column(name = "IMAGE_UID")
	private String imageUid;

	@Column(name = "UPD_USER")
	private String updUser;

	@Column(name = "UPD_TIME")
	private Date updTime;

	/**
	 * 窗宽
	 */
	@Column(name = "WIN_WIDTH")
	private Double winWidth;

	/**
	 * 窗位
	 */
	@Column(name = "WIN_LEVEL")
	private Double winLevel;

	/**
	 * 传输语法识别号
	 */
	@Column(name = "TRANSFER_UID")
	private String transferUid;

	/**
	 * 影像格式ID号
	 */
	@Column(name = "CLASS_UID_ID")
	private String classUidId;

	/**
	 * 影像备份标签
	 */
	@Column(name = "BACKUP_LABEL")
	private String backupLabel;

	/**
	 * 是否关键影像
	 */
	@Column(name = "KEY_IMAGE")
	private Boolean keyImage;

	/**
	 * 水平像素
	 */
	@Column(name = "N_ROWS")
	private Integer nRows;

	/**
	 * 垂直像素
	 */
	@Column(name = "N_COLUMNS")
	private Integer nColumns;

	/**
	 * 灰阶值
	 */
	@Column(name = "BITS_ALLOCATED")
	private Integer bitsAllocated;

	/**
	 * 动态影像的影像格数
	 */
	@Column(name = "N_FRAMES")
	private Integer nFrames;

	@Column(name = "nidus_Type_Code")
	private String nidusTypeCode;

	@Column(name = "nidus_Type")
	private String nidusType;

}