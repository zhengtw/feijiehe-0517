package com.jfhealthcare.modules.business.response;

import lombok.Data;

@Data
public class CheckApiResponse {
    /**
     * 影像所见
     */
    private String finding;
    
    /**
     * 影像状态
     */
    private boolean find_has_error;

    /**
     * 诊断建议
     */
    private String impression;
    
    /**
     * 诊断状态
     */
    private boolean impress_has_error;
}