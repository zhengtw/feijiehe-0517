package com.jfhealthcare.modules.BI.mapper;

import com.jfhealthcare.modules.BI.entity.RepCount;
import com.jfhealthcare.modules.BI.response.RepCountResponse;
import com.jfhealthcare.tk.mybatis.util.MyMapper;
import java.util.List;

public interface RepCountMapper extends MyMapper<RepCount> {
	
	List<RepCountResponse> queryRepCount();

}
