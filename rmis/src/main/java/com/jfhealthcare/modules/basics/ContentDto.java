package com.jfhealthcare.modules.basics;

import lombok.Data;

@Data
public class ContentDto {
	/**
	 * 影像所见
	 */
    private String findings;

    /**
     * 诊断意见
     */
    private String suggests;
    
}
