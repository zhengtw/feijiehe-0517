package com.jfhealthcare.modules.business.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.business.entity.SetUsedtemplate;
import com.jfhealthcare.modules.business.request.SetUsedTemplateRequest;

public interface SetUsedTemplateService {

	PageInfo<SetUsedtemplate> querySetUsedTemplateService(SetUsedTemplateRequest setUsedTemplateRequest);

	void deleteSetUsedTemplate(String usedtempId);

	void insertSetUsedTemplate(SetUsedTemplateRequest setUsedTemplateRequest);

	void updateSetUsedTemplate(SetUsedTemplateRequest setUsedTemplateRequest);

	String queryNindexForSetUsedTemplate();

	SetUsedtemplate querySetUsedTemplateById(String usedtempId);



}
