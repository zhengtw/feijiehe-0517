package com.jfhealthcare.modules.label.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jfhealthcare.modules.label.entity.LabelOperateRecord;
import com.jfhealthcare.tk.mybatis.util.MyMapper;

public interface LabelOperateRecordMapper extends MyMapper<LabelOperateRecord> {

	List<LabelOperateRecord> selectByUid(@Param(value="labelAccnum")String labelAccnum);
}