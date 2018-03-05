package com.jfhealthcare.modules.system.entity;

import javax.persistence.*;

import lombok.Data;

@Data
public class SysDictCls {
    /**
     * 分类编号
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 分类名称
     */
    private String clsName;

}