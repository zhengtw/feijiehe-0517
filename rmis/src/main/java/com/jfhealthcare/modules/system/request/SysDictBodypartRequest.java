package com.jfhealthcare.modules.system.request;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.validator.constraints.NotBlank;

import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;

@Data
public class SysDictBodypartRequest extends BasicPageEntity{
    /**
     * 部位主键ID
     */
	@NotBlank(message="ID不能为空",groups = Edit.class)
    private String id;

    /**
     * 检查类型Code
     */
    private String checktypeCode;

    /**
     * 检查类型Name
     */
    private String checktypeName;

    /**
     * 名称
     */
    private String name;

    /**
     * 辅助值1
     */
    private String othervalue;

    /**
     * 辅助值2
     */
    private String othervalue2;

    /**
     * 创建人
     */
    private String crtUser;

    /**
     * 创建时间
     */
    private Date crtTime;

    /**
     * 修改人
     */
    private String updUser;

    /**
     * 修改时间
     */
    private Date updTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 启用状态
     */
    private Boolean status;

    /**
     * 排序值
     */
    private Integer nindex;

    /**
     * 拼音简码
     */
    private String namepy;

    /**
     * 五笔简码
     */
    private String namewb;

}