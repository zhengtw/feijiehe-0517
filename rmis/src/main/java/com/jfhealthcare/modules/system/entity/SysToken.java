package com.jfhealthcare.modules.system.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
public class SysToken {
    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 账号
     */
    private String logincode;

    /**
     * token
     */
    private String token;

    /**
     * 过期时间
     */
    private Date expireTime;

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