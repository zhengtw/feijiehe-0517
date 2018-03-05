package com.jfhealthcare.modules.business.response;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class SetReportResponse {
    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级
     */
    private String upper;

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
    private Date updTiem;

    /**
     * 创建人
     */
    private String crtUser;

    /**
     * 创建时间
     */
    private Date crtTime;

    /**
     * 备注
     */
    private String remark;
    /**
     * 逻辑删除
     */
    private Boolean isdelete;
    /**
     * 影像所见
     */
    private String finding;
    /**
     * 诊断建议
     */
    private String impression;
    /**
     * 登录号
     */
    private String logincode;
    /**
     * 是否文件
     */
    private Boolean fileflag;//true  文件夹   false  文件
    
    //放子目录
    private List<SetReportResponse> childrens;
}