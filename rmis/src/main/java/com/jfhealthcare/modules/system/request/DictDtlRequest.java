package com.jfhealthcare.modules.system.request;



import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.modules.basics.BasicPageEntity;

import lombok.Data;

@Data
public class DictDtlRequest extends BasicPageEntity{
	 /**
     * 字典编号
     */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 目录编号
     */
    private String dictId;
    
    /**
     * 字典code
     */
    private String code;

    /**
     * 数据值
     */
    private String name;

    /**
     * 辅助值
     */
    private String othervalue;
    
    /**
     * 辅助值
     */
    private String othervalue2;
    
    /**
     * 排序值
     */
    private String nindex;

    /**
     * 锁定状态
     */
    private Boolean ismodify;

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
    private Date crtTime;

}
