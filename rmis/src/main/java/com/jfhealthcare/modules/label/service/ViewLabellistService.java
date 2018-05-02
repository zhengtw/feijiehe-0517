package com.jfhealthcare.modules.label.service;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.label.entity.ViewLabellist;
import com.jfhealthcare.modules.label.request.ViewLabellistRequest;

public interface ViewLabellistService {

	PageInfo<ViewLabellist> queryViewLabellist(ViewLabellistRequest viewLabelRequest);

	PageInfo<ViewLabellist> queryAllViewLabellist(Integer pageNum, Integer pageSize);

}
