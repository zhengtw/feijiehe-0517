package com.jfhealthcare.modules.label.service;

import com.jfhealthcare.modules.label.entity.LabelInfolist;

public interface LabelInfolistService {

	LabelInfolist queryByParams(LabelInfolist labelil);

	LabelInfolist queryOneLabelInfolistByParams(String checkNum);

	void updateStatus(LabelInfolist labelInfolist, String oldStatusCode);

	LabelInfolist queryById(String labelAccnum);

}
