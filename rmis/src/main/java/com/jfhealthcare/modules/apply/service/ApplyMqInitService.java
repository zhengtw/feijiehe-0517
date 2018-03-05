package com.jfhealthcare.modules.apply.service;

import java.util.Map;

public interface ApplyMqInitService {

	void initApply(String sopUID, String seriesUID, String studyUID, String userId);

	void initErrorApply(Map<String, Object> mesge);

	

}
