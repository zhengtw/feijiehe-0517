package com.jfhealthcare.modules.label.service;

import java.util.List;

import com.jfhealthcare.modules.label.entity.LabelJson;
import com.jfhealthcare.modules.label.request.LabelJsonRequest;


public interface LabelJsonService {

	List<LabelJson> queryByUid(String id);

	void updateByParams(LabelJsonRequest labeljRequest,String userId);

}
