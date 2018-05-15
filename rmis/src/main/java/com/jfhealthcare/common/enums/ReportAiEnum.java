package com.jfhealthcare.common.enums;


/**
 * @author jinmaxu
 * 报告中AI状态
 */
public enum ReportAiEnum {
	AIREFUSE("ai拒绝","4998"),//ai拒绝
	TOFENPIAN("转分片","4999"),//4998
	TOREPORTE("转报告","5000"),//转报告
	TOREVIEWE("转审核","5001"),//转审核
	UNTREATED("未参与","5002");//未处理
	
	
	private String aiStatus;

    private String aiStatusCode;
    
	private ReportAiEnum(String aiStatus,String aiStatusCode) {
		this.aiStatus = aiStatus;
		this.aiStatusCode = aiStatusCode;
	}
	public String getAiStatus() {
		return aiStatus;
	}

	public void setAiStatus(String aiStatus) {
		this.aiStatus = aiStatus;
	}
	
	public String getAiStatusCode() {
		return aiStatusCode;
	}
	
	public void setAiStatusCode(String aiStatusCode) {
		this.aiStatusCode = aiStatusCode;
	}
}
