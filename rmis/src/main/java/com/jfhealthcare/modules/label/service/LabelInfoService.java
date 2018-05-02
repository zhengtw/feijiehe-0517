package com.jfhealthcare.modules.label.service;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.label.entity.LabelInfo;
import com.jfhealthcare.modules.label.entity.LabelJson;
import com.jfhealthcare.modules.label.request.LabelInfoRequest;
import com.jfhealthcare.modules.label.response.LabelInfoWebViewResponse;

public interface LabelInfoService {

	LabelInfoWebViewResponse queryInfoForWebView(LabelInfoRequest labelInfoRequest, String adminCode);

	PageInfo<LabelJson> queryInfoForWeb(String labelAccnum, Integer pageNum, Integer pageSize);

	void updateLabelInfo(LabelInfo labelInfo);

}
