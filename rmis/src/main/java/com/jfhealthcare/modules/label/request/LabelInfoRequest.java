package com.jfhealthcare.modules.label.request;

import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;

@Data
public class LabelInfoRequest extends BasicPageEntity {

	private String checkNum;

	private String userId;

	private String labelAccnum;

	private String id;

	private String uid;

	private String studyUid;

	private String seriesUid;

	private String imageUid;

	private Double winWidth;

	private Double winLevel;

	/**
	 * 传输语法识别号
	 */
	private String transferUid;

	/**
	 * 影像格式ID号
	 */
	private String classUidId;

	/**
	 * 影像备份标签
	 */
	private String backupLabel;

	/**
	 * 是否关键影像
	 */
	private Boolean keyImage;

	/**
	 * 水平像素
	 */
	private Integer nRows;

	/**
	 * 垂直像素
	 */
	private Integer nColumns;

	/**
	 * 灰阶值
	 */
	private Integer bitsAllocated;

	/**
	 * 动态影像的影像格数
	 */
	private Integer nFrames;
}
