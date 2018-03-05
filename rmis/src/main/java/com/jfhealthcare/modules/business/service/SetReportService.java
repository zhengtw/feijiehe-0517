package com.jfhealthcare.modules.business.service;

import java.util.List;

import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.modules.business.request.SetReportRequest;
import com.jfhealthcare.modules.business.response.SetReportResponse;

public interface SetReportService {
	List<SetReportResponse> querySetReportWithArmarium(String isPublic,LoginUserEntity loginUserEntity);
	
	SetReportResponse querySetReportByRepId(String isPublic,String repId);

	void saveSetReport(SetReportRequest setReportRequest);

	void deleteSetReport(String isPublic, String repId);

	void updateSetReport(SetReportRequest setReportRequest);

	List<?> queryUpperForSetReport(String isPublic,LoginUserEntity loginUserEntity);

	String queryNindexForSetReport(String isPublic,String repId, LoginUserEntity loginUserEntity);

	


}
