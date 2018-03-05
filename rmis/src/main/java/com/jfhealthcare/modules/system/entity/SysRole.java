package com.jfhealthcare.modules.system.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
public class SysRole {
    /**
     * 角色编号
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 拼音简码
     */
    private String namepy;

    /**
     * 五笔简码
     */
    private String namewb;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 逻辑删除
     */
    private Boolean isdelete;

    /**
     * 更新人
     */
    private String updUser;

    /**
     * 更新时间
     */
    private Date updTime;

    /**
     * 创建人
     */
    private String crtUser;

    /**
     * 创建时间
     */
    private Date crtTime;

}