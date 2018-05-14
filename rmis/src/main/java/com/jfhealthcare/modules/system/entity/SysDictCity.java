package com.jfhealthcare.modules.system.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
@Table(name = "sys_dict_city")
public class SysDictCity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
	private Integer id;

	@Column(name = "AREACODE")
	private String areacode;

	@Column(name = "AREANAME")
	private String areaname;

	@Column(name = "UPPCODE")
	private Integer uppcode;

	@Column(name = "SHORTNAME")
	private String shortname;

	@Column(name = "LEVEL")
	private Integer level;

	@Column(name = "LEVELTYPE")
	private String leveltype;

	@Column(name = "CITYCODE")
	private String citycode;

	@Column(name = "ZIPCODE")
	private String zipcode;

	@Column(name = "MERGERNAME")
	private String mergername;

	@Column(name = "LNG")
	private String lng;

	@Column(name = "LAT")
	private String lat;

	@Column(name = "PINYIN")
	private String pinyin;

	@Column(name = "NAMEPY")
	private String namepy;

	@Column(name = "NAMEWB")
	private String namewb;

	@Column(name = "CRT_USER")
	private String crtUser;

	@Column(name = "CRT_TIME")
	private Date crtTime;

	@Column(name = "UPD_USER")
	private String updUser;

	@Column(name = "UPD_TIME")
	private Date updTime;

	@Column(name = "REMARK")
	private String remark;
}