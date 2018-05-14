package com.jfhealthcare.modules.label.mapper;

import java.util.List;

import com.jfhealthcare.modules.label.entity.ViewLabellist;
import com.jfhealthcare.modules.label.request.ViewLabellistRequest;
import com.jfhealthcare.tk.mybatis.util.MyMapper;

public interface ViewLabellistMapper extends MyMapper<ViewLabellist> {

	List<ViewLabellist> selectByParams(ViewLabellistRequest viewLabellistRequest);
}