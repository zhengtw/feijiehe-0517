package com.jfhealthcare.modules.label.response;

import java.util.Date;
import java.util.List;

import com.jfhealthcare.modules.label.entity.LabelBodypart;
import com.jfhealthcare.modules.label.entity.LabelDisease;

import lombok.Data;

@Data
public class ViewLabellistResponse {
	/**
	 * userID
	 */
	private String userId;
	/**
	 * userName
	 */
	private String userName;
	/**
	 * HisReport
	 */
	private String HisReport;
	/**
	 * bodyPart 部位
	 */
	private List<LabelBodypart> bodyPart;
	/**
	 * disease 疾病
	 */
	private List<LabelDisease> disease;

	/**
	 * ID_标注流水号
	 */
	private String labelAccnum;

	private String applyId;

	/**
	 * 标注状态CODE
	 */
	private String statusCode;

	/**
	 * 标注状态
	 */
	private String status;

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
	 * 审核时间
	 */
	private Date auditTime;

	/**
	 * 标注描述
	 */
	private String describe;

	/**
	 * 备注
	 */
	private String remark;

	private String crtUser;

	private Date crtTime;

	private String updUser;

	private Date updTime;

	private String applyCheckNum;
}
