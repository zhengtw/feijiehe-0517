package com.jfhealthcare.modules.business.request;

import lombok.Data;

@Data
public class CheckApiRequest {
    /**
     * 影像所见
     */
    private String finding;

    /**
     * 诊断建议
     */
    private String impression;
}