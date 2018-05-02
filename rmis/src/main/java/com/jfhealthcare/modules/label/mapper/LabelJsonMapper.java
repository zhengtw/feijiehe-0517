package com.jfhealthcare.modules.label.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jfhealthcare.modules.label.entity.LabelJson;
import com.jfhealthcare.tk.mybatis.util.MyMapper;

public interface LabelJsonMapper extends MyMapper<LabelJson> {

	List<LabelJson> selectByLabelAccnum(@Param(value="labelAccnum") String labelAccnum);
}