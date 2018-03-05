package com.jfhealthcare.modules.basics;

import java.util.List;

import lombok.Data;

/**
 *  C++客户端请求之后给出的报告的返回
 */
@Data
public class ReportJsonDto {
     /**
      * 医院名称
      */
	 private String title;
	 
	 /**
	  * 送检医疗机构的名称
	  */
	 private String sending_institution_name;
	 
	 /**
	  * 报告单名称
	  */
	 private String sub_title;
	 
	 /**
	  * 病人属性
	  */
	 private PatientDto patient;
	 
	 /**
	  * 检查属性
	  */
	 private StudyDto study;
	 
	 /**
	  * 截图地址
	  */
	 private List<String> image;
	 
	 /**
	  * 图像所见和检查意见
	  */
	 private ContentDto content;
	 
	 /**
	  * 签名
	  */
	 private SignatureDto signature;
	 
	 /**
	  * 固定字符
	  */
	 private String footnote;
	 
	 private String reportStatus;
}
