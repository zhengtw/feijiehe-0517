package com.jfhealthcare.modules.business.mapper;

import java.util.List;

import com.jfhealthcare.modules.business.entity.RepRecord;
import com.jfhealthcare.tk.mybatis.util.MyMapper;

public interface RepRecordMapper extends MyMapper<RepRecord> {

	Integer selectCountByreportUid(String reportUid);
	
	List<String> selectListByreportUid(String reportUid);
}