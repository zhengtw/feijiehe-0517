package com.jfhealthcare.modules.business.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jfhealthcare.modules.apply.request.PrintWorklistRequest;
import com.jfhealthcare.modules.apply.response.PrintWorklistResponse;
import com.jfhealthcare.modules.business.entity.ViewWorklist;
import com.jfhealthcare.modules.business.request.ViewWorklistRequest;
import com.jfhealthcare.modules.business.response.ViewWorklistResponse;
import com.jfhealthcare.tk.mybatis.util.MyMapper;

public interface ViewWorklistMapper extends MyMapper<ViewWorklist> {

	List<ViewWorklistResponse> queryViewWorklist(ViewWorklistRequest viewWorklistRequest);

	ViewWorklistResponse queryCountViewWorklist(ViewWorklistRequest vwlr);

	List<PrintWorklistResponse> queryPrintWorklist(PrintWorklistRequest vwlr);

	List<Map<String, Object>> queryPrintCountWorklist(PrintWorklistRequest vwlr);

	String queryApplyWorkListToRemind(@Param(value="checkNum")String checkNum);
}