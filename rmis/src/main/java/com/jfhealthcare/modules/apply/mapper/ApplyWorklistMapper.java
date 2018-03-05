package com.jfhealthcare.modules.apply.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jfhealthcare.modules.apply.entity.ApplyWorklist;
import com.jfhealthcare.modules.apply.request.ApplyWorklistRequest;
import com.jfhealthcare.modules.apply.response.ApplyWorklistResponse;
import com.jfhealthcare.tk.mybatis.util.MyMapper;

public interface ApplyWorklistMapper extends MyMapper<ApplyWorklist> {
	
	List<ApplyWorklistResponse> queryApplyWorkList(ApplyWorklistRequest vwlr);

	List<Map<String, Object>> queryInstanceByStudyUid(@Param(value="studyUid")String studyUid);

	List<Map<String, Object>> queryInstanceByInstanceUid(List<String> list);
}