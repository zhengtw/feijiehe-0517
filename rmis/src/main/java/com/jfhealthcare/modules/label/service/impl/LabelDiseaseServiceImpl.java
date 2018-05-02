package com.jfhealthcare.modules.label.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfhealthcare.modules.label.entity.LabelDisease;
import com.jfhealthcare.modules.label.mapper.LabelDiseaseMapper;
import com.jfhealthcare.modules.label.service.LabelDiseaseService;

import tk.mybatis.mapper.entity.Example;

@Service
public class LabelDiseaseServiceImpl implements LabelDiseaseService {
	@Autowired
	private LabelDiseaseMapper labelDiseaseMapper;

	@Override
	public List<LabelDisease> queryInfoByUid(String labelAccnum) {
		Example example = new Example(LabelDisease.class);
		example.createCriteria().andEqualTo("uid", labelAccnum);
		return labelDiseaseMapper.selectByExample(example);
	}

	@Override
	public void updateLabelDisease(LabelDisease labelDisease) {
		labelDiseaseMapper.updateByPrimaryKeySelective(labelDisease);

	}

	@Override
	public void insertLabelDisease(LabelDisease labelDisease) {
		labelDiseaseMapper.insertSelective(labelDisease);

	}

	@Override
	@Transactional
	public void deleteLabelDisease(String ids) {
		String[] split = ids.split(",");
		for (String id : split) {
			labelDiseaseMapper.deleteByPrimaryKey(id);
		}

	}

}
