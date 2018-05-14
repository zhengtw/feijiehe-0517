package com.jfhealthcare.modules.label.service;

import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.modules.label.entity.LabelInfolist;
import com.jfhealthcare.modules.label.request.LabelInfolistRequest;

public interface LabelInfolistService {

	LabelInfolist queryByParams(LabelInfolist labelil);

	LabelInfolist queryOneLabelInfolistByParams(String checkNum);

	void updateStatus(LabelInfolist labelInfolist, String oldStatusCode);

	LabelInfolist queryById(String labelAccnum);

	void updateLabelInfolist(LabelInfolistRequest labelInfolistRequest, LoginUserEntity loginUserEntity);

}
