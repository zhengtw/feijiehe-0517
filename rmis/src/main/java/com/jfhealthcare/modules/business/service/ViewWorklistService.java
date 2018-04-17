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

	void updateCheckListIndex(ViewWorklistRequest viewWorklistRequest,LoginUserEntity loginUserEntity);

	List<RepImage> queryRepImageByRepUid(String repUid);

	void deleteRepImageByRepImageId(String repImageId);

	int queryViewWorklistIsRemind();

	List<ViewWorklistResponse> queryHistoryReport(ViewWorklistRequest viewWorklistRequest);

	Map<String,String> queryBtnsByCheckAccessionNum(String checkAccessionNum, LoginUserEntity loginUserEntity);

	ViewWorklistResponse queryOneViewWorklist(String checkAccessionNum);

	String queryWebviewerUrlByAccessionNum(String checkAccessionNum);


}
