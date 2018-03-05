package com.jfhealthcare.modules.system.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Table(name = "sys_dict_scanmethod")
@Data
public class SysDictScanmethod {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 检查类型Code
     */
    @Column(name = "CHECKTYPE_CODE")
    private String checktypeCode;

    /**
     * 检查类型Name
     */
    @Column(name = "CHECKTYPE_NAME")
    private String checktypeName;

    /**
     * 名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 辅助值1
     */
    @Column(name = "OTHERVALUE")
    private String othervalue;

    /**
     * 辅助值2
     */
    @Column(name = "OTHERVALUE2")
    private String othervalue2;

    /**
     * 创建人
     */
    @Column(name = "CRT_USER")
    private String crtUser;

    /**
     * 创建时间
     */
    @Column(name = "CRT_TIME")
    private Date crtTime;

    /**
     * 修改人
     */
    @Column(name = "UPD_USER")
    private String updUser;

    /**
     * 修改时间
     */
    @Column(name = "UPD_TIME")
    private Date updTime;

    /**
     * 备注
     */
    @Column(name = "REMARK")
    private String remark;

    /**
     * 启用状态
     */
    @Column(name = "STATUS")
    private Boolean status;

    /**
     * 排序值
     */
    @Column(name = "NINDEX")
    private Integer nindex;

    /**
     * 拼音简码
     */
    @Column(name = "NAMEPY")
    private String namepy;

    /**
     * 五笔简码
     */
    @Column(name = "NAMEWB")
    private String namewb;

}