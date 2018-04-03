package com.jfhealthcare.modules.business.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.modules.business.entity.RepImage;
import com.jfhealthcare.modules.business.request.ViewWorklistRequest;
import com.jfhealthcare.modules.business.response.ViewWorklistResponse;

public interface ViewWorklistService {
	PageInfo<ViewWorklistResponse> queryViewWorklist(ViewWorklistRequest viewWorklistRequest);

//	ViewWorklistResponse queryOneViewWorklist(String checkAccessionNum);

	void updateCheckListIndex(ViewWorklistRequest viewWorklistRequest,LoginUserEntity loginUserEntity);

	List<RepImage> queryRepImageByRepUid(String repUid);

	void deleteRepImageByRepImageId(String repImageId);

//	ViewWorklistResponse queryCountViewWorklist(ViewWorklistRequest viewWorklistRequest);

	int queryViewWorklistIsRemind();

	List<ViewWorklistResponse> queryHistoryReport(ViewWorklistRequest viewWorklistRequest);

	String queryHistoryReportImage(String checkNum);

	Map<String,String> queryBtnsByCheckAccessionNum(String checkAccessionNum, LoginUserEntity loginUserEntity);

	ViewWorklistResponse queryOneViewWorklist(String checkAccessionNum);


}
