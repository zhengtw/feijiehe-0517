package com.jfhealthcare.modules.label.request;

import java.util.Date;

import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;

@Data
public class ViewLabellistRequest extends BasicPageEntity {
	/**
	 * 检查单号
	 */
	private String checkNum;

	/**
	 * 检查唯一
	 */
	private String studyUid;

	/**
	 * 序列唯一号码
	 */
	private String seriesUid;

	/**
	 * 检查部位
	 */
	private String bodyPart;

	/**
	 * 序列序号
	 */
	private String seriesNumber;

	/**
	 * 仪器种类
	 */
	private String modality;

	/**
	 * 患者姓名
	 */
	private String ptnName;

	/**
	 * 出生日期
	 */
	private Date birthDate;

	/**
	 * 患者性别
	 */
	private String sex;

	/**
	 * 性别code
	 */
	private String sexCode;

	/**
	 * 患者年龄
	 */
	private String ptnAge;

	/**
	 * 年龄单位
	 */
	private String ptnAgeUnit;

	/**
	 * 年龄单位code
	 */
	private String ptnAgeUnitCode;

	/**
	 * 患者身高
	 */
	private String hight;

	/**
	 * 患者体重
	 */
	private String weight;

	/**
	 * 仪器种类
	 */
	private String wmodality;

	/**
	 * 排检时间
	 */
	private Date studyTime;

	/**
	 * 检查部位
	 */
	private String wbodyPart;

	/**
	 * 上传医生账号
	 */
	private String applyDoc;

	/**
	 * 上传机构
	 */
	private String applyOrg;

	/**
	 * 申请状态code
	 */
	private String applyStatusCode;

	/**
	 * 当前状态
	 */
	private String applyStatus;

	/**
	 * 患者ID
	 */
	private String ptnId;

	/**
	 * 是否有正常的影像
	 */
	private Boolean isNormalImage;

	/**
	 * ID_标注流水号
	 */
	private String labelAccnum;

	/**
	 * 标注状态CODE
	 */
	private String[] statusCode;

	/**
	 * 标注状态
	 */
	private String[] status;

	/**
	 * 标注人
	 */
	private String labelUser;

	/**
	 * 标注时间
	 */
	private Date labelTime;

	/**
	 * 审核人
	 */
	private String auditUser;

	/**
	 * 标注起始时间
	 * */
	private Date fromLabelDate;
	/**
	 * 标注结束时间
	 * */
	private Date endLabelDate;
	
	/**
	 * 拍片开始时间
	 * */
	private Date fromDicomDate;
	/**
	 * 拍片结束时间
	 * */
	private Date endDicomDate;
	
	/**
	 * 标注时间段
	 * */
	private Integer checkTime;
	
	/**
	 * 上传开始日期
	 * */
	private Date fromApplyDate;
	
	/**
	 * 上传结束日期
	 * */
	private Date endApplyDate;
	
	private String labelTaskCode;
	private String labelTask;
	private String nidusTypeCode;
	private String nidusType;
	
	private String[] modalitys;
}
