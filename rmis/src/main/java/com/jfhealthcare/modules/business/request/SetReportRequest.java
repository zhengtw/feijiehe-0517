package com.jfhealthcare.modules.business.request;

import java.util.Date;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.common.validator.group.Insert;

import lombok.Data;

@Data
public class SetReportRequest {
    /**
     * ID
     */
	@NotBlank(message="id不能为空!",groups={Edit.class})
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级
     */
    @NotBlank(message="上级不能为空!",groups={Edit.class,Insert.class})
    private String upper;

    /**
     * 排序
     */
    @NotBlank(message="排序不能为空!",groups={Edit.class,Insert.class})
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
    @NotNull(message="是否文件判断不能为空!",groups={Edit.class,Insert.class})
    private Boolean fileflag;
    /**
     * 是公共 或私有    ture:0 公共,false:1 私有
     */
    @NotBlank(message="私有公共判断不能为空!",groups={Edit.class,Insert.class})
    private String isPublic;
}