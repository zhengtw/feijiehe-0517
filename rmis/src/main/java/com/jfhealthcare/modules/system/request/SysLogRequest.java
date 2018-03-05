package com.jfhealthcare.modules.system.request;

import java.util.Date;


import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;

@Data
public class SysLogRequest extends BasicPageEntity{
    
	/**
     * id
     */
    private String id;

    /**
     * 账号
     */
    private String logincode;

    /**
     * 操作
     */
    private String operation;

    /**
     * 方法
     */
    private String method;

    /**
     * 参数
     */
    private String params;

    /**
     * ip
     */
    private String ip;

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
