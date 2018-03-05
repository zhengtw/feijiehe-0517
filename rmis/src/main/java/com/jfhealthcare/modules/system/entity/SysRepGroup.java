package com.jfhealthcare.modules.system.entity;

import javax.persistence.*;

import lombok.Data;

@Data
public class SysRepGroup {
    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 分组编号
     */
    private String groupId;

    private String logincode;

}