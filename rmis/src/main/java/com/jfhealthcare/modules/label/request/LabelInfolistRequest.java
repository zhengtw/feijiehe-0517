package com.jfhealthcare.modules.label.request;

import lombok.Data;

@Data
public class LabelInfolistRequest {
	/**
	 * ID_标注流水号
	 */
	private String labelAccnum;

	private String labelTaskCode;

	private String nidusTypeCode;

}
