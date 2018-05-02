package com.jfhealthcare.modules.label.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfhealthcare.modules.label.entity.LabelJson;
import com.jfhealthcare.modules.label.mapper.LabelInfolistMapper;
import com.jfhealthcare.modules.label.mapper.LabelJsonMapper;
import com.jfhealthcare.modules.label.mapper.LabelOperateRecordMapper;
import com.jfhealthcare.modules.label.request.LabelJsonRequest;
import com.jfhealthcare.modules.label.service.LabelJsonService;

import tk.mybatis.mapper.entity.Example;

@Service
public class LabelJsonServiceImpl implements LabelJsonService {
	@Autowired
	private LabelJsonMapper labelJsonMapper;
	@Autowired
	private LabelInfolistMapper labelInfolistMapper;
	@Autowired
	private LabelOperateRecordMapper labelOperateRecordMapper;

	@Override
	public List<LabelJson> queryByUid(String id) {
		Example example = new Example(LabelJson.class);
		example.createCriteria().andEqualTo("labelValueUid", id);
		return labelJsonMapper.selectByExample(example);
	}


	@Override
	@Transactional
	public void updateByParams(LabelJsonRequest labeljRequest, String userId) {
		//先删除，后新增
		//TODO 解析出 要增加的JSON
		//TODO 如果JSON为空的话 就直接 更改标注列表状态为未标注，如果插入之前没有数据 则说明 改为已标注
		//TODO 记录日志表 以及操作表
	}

	

}
