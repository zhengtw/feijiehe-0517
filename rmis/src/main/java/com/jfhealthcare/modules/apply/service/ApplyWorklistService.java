package com.jfhealthcare.modules.apply.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.modules.apply.request.ApplyWorklistRequest;
import com.jfhealthcare.modules.apply.request.PrintWorklistRequest;
import com.jfhealthcare.modules.apply.response.ApplyWorklistResponse;
import com.jfhealthcare.modules.apply.response.PrintWorklistResponse;

public interface ApplyWorklistService {

	PageInfo<ApplyWorklistResponse> queryApplyWorkList(ApplyWorklistRequest applyWorklistRequest);

	PageInfo<PrintWorklistResponse> queryPrintWorklist(PrintWorklistRequest printWorklistRequest);

	ApplyWorklistResponse queryApplyWorkListById(String checkNum);

	void updateApplyWorkListToView(ApplyWorklistRequest applyWorklistRequest, LoginUserEntity loginUserEntity);

	List<Map<String,Object>> queryPrintWorkListStatus(PrintWorklistRequest printWorklistRequest);

	String queryApplyWorkListToRemind(String checkNum);

	String queryApplyWorkToCheckImageByUids(List<String> instanceUids);

	void updateApplyWorkListStatus(ApplyWorklistRequest applyWorklistRequest, LoginUserEntity loginUserEntity);

}
