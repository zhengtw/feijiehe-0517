package com.jfhealthcare.modules.label.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.jfhealthcare.common.enums.LabelStatusEnum;
import com.jfhealthcare.modules.label.entity.LabelInfolist;
import com.jfhealthcare.modules.label.entity.LabelOperateRecord;
import com.jfhealthcare.modules.label.mapper.LabelInfolistMapper;
import com.jfhealthcare.modules.label.mapper.LabelOperateRecordMapper;
import com.jfhealthcare.modules.label.service.LabelInfolistService;

import tk.mybatis.mapper.entity.Example;

@Service
public class LabelInfolistServiceImpl implements LabelInfolistService {
	@Autowired
	private LabelInfolistMapper labelInfolistMapper;
	@Autowired
	private LabelOperateRecordMapper labelOperateRecordMapper;

	@Override
	public LabelInfolist queryByParams(LabelInfolist labelil) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LabelInfolist queryOneLabelInfolistByParams(String checkNum) {
		Example example = new Example(LabelInfolist.class);
		example.createCriteria().andEqualTo("applyCheckNum", checkNum);
		List<LabelInfolist> selectByExample = labelInfolistMapper.selectByExample(example);
		if (!ObjectUtils.isEmpty(selectByExample)) {
			return selectByExample.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public void updateStatus(LabelInfolist labelInfolist, String oldStatusCode) {
		// 任何状态 变成 标注中或者审核中的时候 都不记录操作表,反之都记录
		if (!LabelStatusEnum.IN_LABEL.getStatusCode().equals(labelInfolist.getStatusCode())
				&& !LabelStatusEnum.CHECKING_LABEL.getStatusCode().contentEquals(labelInfolist.getStatusCode())) {
			List<LabelOperateRecord> labelOperaRecord = labelOperateRecordMapper
					.selectByUid(labelInfolist.getLabelAccnum());
			LabelOperateRecord labelOperateRecord = new LabelOperateRecord();
			labelOperateRecord.setLabelStatus(labelInfolist.getStatus());
			labelOperateRecord.setLabelStatusCode(labelInfolist.getStatusCode());
			labelOperateRecord.setOperateTime(labelInfolist.getUpdTime());
			labelOperateRecord.setOperator(labelInfolist.getUpdUser());
			labelOperateRecord.setUid(labelInfolist.getLabelAccnum());
			if (ObjectUtils.isEmpty(labelOperaRecord)) {
				labelOperateRecord.setOperationStep(1);
			} else {
				labelOperateRecord.setOperationStep(labelOperaRecord.get(0).getOperationStep() + 1);
			}
			labelOperateRecordMapper.insert(labelOperateRecord);
		}
		labelInfolistMapper.updateByPrimaryKeySelective(labelInfolist);
		// TODO 记录在日志表中

	}

	@Override
	public LabelInfolist queryById(String labelAccnum) {
		return labelInfolistMapper.selectByPrimaryKey(labelAccnum);
	}

}
