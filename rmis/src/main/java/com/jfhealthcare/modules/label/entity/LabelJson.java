package com.jfhealthcare.modules.label.entity;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;

@Table(name = "label_json")
@Data
public class LabelJson {
	@Id
	@Column(name = "JSON_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
	private String jsonId;

	@Column(name = "LABEL_VALUE_UID")
	private String labelValueUid;

	@Column(name = "CRT_USER")
	private String crtUser;

	@Column(name = "UPD_USER")
	private String updUser;

	@Column(name = "JSON_VALUE1")
	private String jsonValue1;
	

	@Column(name = "CRT_TIME")
	private Date crtTime;
	
	@Column(name = "UPD_TIME")
	private Date updTime;
	
}