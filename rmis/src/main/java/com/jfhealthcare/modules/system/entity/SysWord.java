package com.jfhealthcare.modules.system.entity;

import lombok.Data;

@Data
public class SysWord {
	
    /**
     * 中文
     */
    private String cword;
    /**
     * 拼音
     */
    private String pycode;
    /**
     * 五笔
     */
    private String wbcode;

    private String sjcode;

    
}