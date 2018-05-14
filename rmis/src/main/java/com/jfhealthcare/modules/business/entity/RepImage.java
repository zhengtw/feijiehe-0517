package com.jfhealthcare.modules.business.entity;

import javax.persistence.*;

import lombok.Data;

@Data
public class RepImage {
    /**
     * ID
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 报告UID
     */
    private String repUid;

    /**
     * 编号
     */
    private Integer number;

    /**
     * 图像下标Code
     */
    private String imgLabelCode;

    /**
     * 图像下标
     */
    private String imgLabel;

    /**
     * 图像路径
     */
    private String imgPage;
    
    /**
	 * 逻辑删除
	 */
	private Boolean isdelete;

}