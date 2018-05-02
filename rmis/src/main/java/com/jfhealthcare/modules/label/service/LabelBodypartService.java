package com.jfhealthcare.modules.label.service;

import java.util.List;

import com.jfhealthcare.modules.label.entity.LabelBodypart;

public interface LabelBodypartService {

	List<LabelBodypart> queryInfoByUid(String labelAccnum);

	void updateLabelBodypart(String labelAccnum, String bodyCodes);

	void insertLabelBodypart(String labelAccnum, String bodyCodes);

	void deleteLabelBodypart(String ids);

}
