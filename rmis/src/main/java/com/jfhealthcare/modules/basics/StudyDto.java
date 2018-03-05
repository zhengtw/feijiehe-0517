package com.jfhealthcare.modules.basics;
/**
 * @author Kitty daddy
 * 检查Dto
 */
public class StudyDto {
	/**
	 * 检查号
	 */
	private String acc_num;
	
	/**
	 * 检查部位
	 */
	private String body_part;
	
	/**
	 * 阴性或者阳性
	 */
	private String hp;
	
	/**
	 * 检查时间
	 */
	private String study_time;

	
	public String getAcc_num() {
		return acc_num;
	}

	public void setAcc_num(String acc_num) {
		this.acc_num = acc_num;
	}

	public String getBody_part() {
		return body_part;
	}

	public void setBody_part(String body_part) {
		this.body_part = body_part;
	}

	public String getHp() {
		return hp;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	public String getStudy_time() {
		return study_time;
	}

	public void setStudy_time(String study_time) {
		this.study_time = study_time;
	}
}
