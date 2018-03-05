package com.jfhealthcare.modules.system.request;


import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;

@Data
public class DictRequest extends BasicPageEntity{
	
	/**
     * 目录编号
     */
	@NotBlank(message="ID不能为空!",groups={Edit.class})
	 private String id;

    /**
     * 目录名称
     */
    private String dname;

    /**
     * 分类编号
     */
    private String clsId;

    /**
     * 分类名称
     */
    private String clsName;

    /**
     * 锁定状态
     */
    private Boolean ismodify;

    /**
     * 启用状态
     */
    private Boolean status;

    /**
     * 拼音简码
     */
    private String namepy;

    /**
     * 五笔简码
     */
    private String namewb;

    /**
     * 逻辑删除
     */
    private Boolean isdelete;

    /**
     * 备注
     */
    private String remark;

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
