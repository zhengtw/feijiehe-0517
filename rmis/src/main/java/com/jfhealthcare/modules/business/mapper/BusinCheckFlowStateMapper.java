package com.jfhealthcare.modules.business.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jfhealthcare.modules.business.entity.BusinCheckFlowState;
import com.jfhealthcare.tk.mybatis.util.MyMapper;

public interface BusinCheckFlowStateMapper extends MyMapper<BusinCheckFlowState> {

	List<BusinCheckFlowState> selectFlowRorIndex(@Param(value="accessionNum")String accessionNum);
}