package com.jfhealthcare.modules.business.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
@Data
public class BusinCheckDmcRecord {
    /**
     * ID
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 流水号
     */
    private String accessionNum;//放检查流水号

    /**
     * 影像ACC
     */
    private String accessionNumImg;//放报告地址 sopurl

    /**
     * 影像UID
     */
    private String uidImg;//放检查UID

   
}