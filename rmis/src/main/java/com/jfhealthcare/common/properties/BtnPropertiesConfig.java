package com.jfhealthcare.common.properties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "view.worklist")
public class BtnPropertiesConfig {

	public Map<String, String> btnsMap = new HashMap<String, String>();
	
	
	
}
