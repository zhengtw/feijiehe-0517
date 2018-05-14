package com.jfhealthcare.modules.label.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfhealthcare.modules.label.entity.LabelBodypart;
import com.jfhealthcare.modules.label.mapper.LabelBodypartMapper;
import com.jfhealthcare.modules.label.service.LabelBodypartService;
import com.jfhealthcare.modules.system.entity.SysDictBodypart;
import com.jfhealthcare.modules.system.mapper.SysDictBodypartMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class LabelBodypartServiceImpl implements LabelBodypartService {
	@Autowired
	private LabelBodypartMapper labelBodypartMapper;
	@Autowired
	private SysDictBodypartMapper sysDictBodypartMapper;

	@Override
	public List<LabelBodypart> queryInfoByUid(String labelAccnum) {
		Example example = new Example(LabelBodypart.class);
		example.createCriteria().andEqualTo("uid", labelAccnum);
		return labelBodypartMapper.selectByExample(example);
	}

	@Override
	@Transactional
	public void updateLabelBodypart(String labelAccnum, String bodyCodes) {
		// 先删除，后新增
		LabelBodypart labelBodypart = new LabelBodypart();
		labelBodypart.setUid(labelAccnum);
		labelBodypartMapper.delete(labelBodypart);
		addBodyPart(labelAccnum, bodyCodes);

	}

	@Override
	@Transactional
	public void insertLabelBodypart(String labelAccnum, String bodyCodes) {
		addBodyPart(labelAccnum, bodyCodes);
	}

	@Override
	@Transactional
	public void deleteLabelBodypart(String ids) {
		String[] split = ids.split(",");
		for (String id : split) {
			labelBodypartMapper.deleteByPrimaryKey(id);
		}

	}

	// TODO 测试回滚
	private void addBodyPart(String labelAccnum, String bodyCodes) {
		if (StringUtils.isNotBlank(bodyCodes)) {
			String[] split = bodyCodes.split(",");
			for (String bodyCode : split) {
				LabelBodypart businLabelBodypart = new LabelBodypart();
				SysDictBodypart dictBodyPart = sysDictBodypartMapper.selectByPrimaryKey(bodyCode);
				if(dictBodyPart == null){
					continue;
				}
				businLabelBodypart.setBodypartCode(bodyCode);
				businLabelBodypart.setBodypart(dictBodyPart.getName());
				businLabelBodypart.setUid(labelAccnum);
				labelBodypartMapper.insertSelective(businLabelBodypart);
			}
		}
	}

}
