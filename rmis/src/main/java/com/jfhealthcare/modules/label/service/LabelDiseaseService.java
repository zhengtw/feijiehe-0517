package com.jfhealthcare.modules.label.service;

import java.util.List;

import com.jfhealthcare.modules.label.entity.LabelDisease;

public interface LabelDiseaseService {

	List<LabelDisease> queryInfoByUid(String labelAccnum);

	void updateLabelDisease(LabelDisease labelDisease);

	void insertLabelDisease(LabelDisease labelDisease);

	void deleteLabelDisease(String ids);

}
