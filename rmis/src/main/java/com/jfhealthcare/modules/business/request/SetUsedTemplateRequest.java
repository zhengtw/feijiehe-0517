package com.jfhealthcare.modules.business.request;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;

@Data
public class SetUsedTemplateRequest extends BasicPageEntity{
    /**
     * ID
     */
	@NotBlank(message="常用语ID不能为空！",groups={Edit.class})
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private String nindex;

    /**
     * 类型Code
     */
    private String typeCode;

    /**
     * 类型
     */
    private String type;

    /**
     * 拼音简码
     */
    private String namepy;

    /**
     * 五笔简码
     */
    private String namewb;

    /**
     * 修改人
     */
    private String updUser;

    /**
     * 修改时间
     */
    private Date updTime;

    /**
     * 创建人
     */
    private String crtUser;

    /**
     * 创建时间
     */
    private Date crtTiem;

    /**
     * 备注
     */
    private String remark;

    /**
     * 登录号
     */
    private String logincode;

    /**
     * 描述
     */
    private String examtech;

}