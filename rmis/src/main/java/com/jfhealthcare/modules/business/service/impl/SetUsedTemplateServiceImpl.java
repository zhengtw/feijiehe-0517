package com.jfhealthcare.modules.business.service.impl;



import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.utils.NameUtils;
import com.jfhealthcare.common.utils.TransferUtils;
import com.jfhealthcare.modules.business.entity.SetUsedtemplate;
import com.jfhealthcare.modules.business.mapper.SetUsedtemplateMapper;
import com.jfhealthcare.modules.business.request.SetUsedTemplateRequest;
import com.jfhealthcare.modules.business.service.SetUsedTemplateService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SetUsedTemplateServiceImpl implements SetUsedTemplateService {

	@Autowired
	private SetUsedtemplateMapper setUsedTemplateMapper;
	
	@Override
	public PageInfo<SetUsedtemplate> querySetUsedTemplateService(SetUsedTemplateRequest setUsedTemplateRequest) {
		PageHelper.startPage(setUsedTemplateRequest.getPageNum(), setUsedTemplateRequest.getPageSize());
		Example example = new Example(SetUsedtemplate.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("logincode", NameUtils.getLoginCode());
		if(StringUtils.isNotBlank(setUsedTemplateRequest.getExamtech())){
			createCriteria.andLike("examtech", "%"+setUsedTemplateRequest.getExamtech()+"%");
		}
		if(StringUtils.isNotBlank(setUsedTemplateRequest.getNamepy())){
			createCriteria.andCondition("(namepy like \"%"+setUsedTemplateRequest.getNamepy()+"%\"" +"or namewb like \"%"+setUsedTemplateRequest.getNamepy()+"%\")");
		}
		example.setOrderByClause("TYPE_CODE,CAST(NINDEX AS DECIMAL) asc");
		List<SetUsedtemplate> setUsedTemplates = setUsedTemplateMapper.selectByExample(example);
		PageInfo<SetUsedtemplate> pageInfo = new PageInfo<SetUsedtemplate>(setUsedTemplates);
		return pageInfo;
	}
	
	@Override
	public SetUsedtemplate querySetUsedTemplateById(String usedtempId) {
		SetUsedtemplate setUsedtemplate = setUsedTemplateMapper.selectByPrimaryKey(usedtempId);
		return setUsedtemplate;
	}

	@Override
	public void deleteSetUsedTemplate(String usedtempId) {
		setUsedTemplateMapper.deleteByPrimaryKey(usedtempId);
	}

	@Override
	public void insertSetUsedTemplate(SetUsedTemplateRequest setUsedTemplateRequest) {
		SetUsedtemplate setUsedTemplate=new SetUsedtemplate();
		TransferUtils.copyPropertiesIgnoreNull(setUsedTemplateRequest, setUsedTemplate);
		setUsedTemplate.setLogincode(NameUtils.getLoginCode());
		setUsedTemplate.setCrtTiem(new Date());
		setUsedTemplate.setCrtUser(NameUtils.getLoginCode());
		setUsedTemplateMapper.insertSelective(setUsedTemplate);
	}

	@Override
	public void updateSetUsedTemplate(SetUsedTemplateRequest setUsedTemplateRequest) {
		SetUsedtemplate setUsedTemplate=new SetUsedtemplate();
		TransferUtils.copyPropertiesIgnoreNull(setUsedTemplateRequest, setUsedTemplate);
		setUsedTemplate.setUpdTime(new Date());
		setUsedTemplate.setUpdUser(NameUtils.getLoginCode());
		setUsedTemplateMapper.updateByPrimaryKey(setUsedTemplate);
	}

	@Override
	public String queryNindexForSetUsedTemplate() {
		Example example = new Example(SetUsedtemplate.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("logincode", NameUtils.getLoginCode());
		example.setOrderByClause("CAST(NINDEX AS DECIMAL) desc");
		List<SetUsedtemplate> sut = setUsedTemplateMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(sut)){
			return "0";
		}
		SetUsedtemplate setUsedTemplate = sut.get(0);
		return String.valueOf(StringUtils.isEmpty(setUsedTemplate.getNindex())?0:Integer.parseInt(setUsedTemplate.getNindex())+1);
	}

	

	
}
