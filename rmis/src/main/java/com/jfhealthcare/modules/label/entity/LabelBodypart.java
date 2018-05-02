package com.jfhealthcare.modules.label.entity;

import javax.persistence.*;

import lombok.Data;

@Table(name = "label_bodypart")
@Data
public class LabelBodypart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
	private String id;
	@Column(name = "uid")
	private String uid;

	@Column(name = "bodypart_code")
	private String bodypartCode;
	
	@Column(name = "bodypart")
	private String bodypart;
}