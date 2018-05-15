package com.jfhealthcare.modules.label.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.common.enums.LabelStatusEnum;
import com.jfhealthcare.modules.label.entity.LabelInfolist;
import com.jfhealthcare.modules.label.entity.LabelOperateRecord;
import com.jfhealthcare.modules.label.mapper.LabelInfolistMapper;
import com.jfhealthcare.modules.label.mapper.LabelOperateRecordMapper;
import com.jfhealthcare.modules.label.request.LabelInfolistRequest;
import com.jfhealthcare.modules.label.service.LabelInfolistService;
import com.jfhealthcare.modules.system.entity.SysDictDtl;
import com.jfhealthcare.modules.system.entity.SysLog;
import com.jfhealthcare.modules.system.mapper.SysLogMapper;
import com.jfhealthcare.modules.system.service.SysDictDtlService;

import tk.mybatis.mapper.entity.Example;

@Service
public class LabelInfolistServiceImpl implements LabelInfolistService {
	@Autowired
	private LabelInfolistMapper labelInfolistMapper;
	@Autowired
	private LabelOperateRecordMapper labelOperateRecordMapper;
	@Autowired
	private SysDictDtlService sysDictDtlService;
	@Autowired
	private SysLogMapper sysLogMapper;

	@Override
	public LabelInfolist queryByParams(LabelInfolist labelil) {
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
				&& !LabelStatusEnum.CHECKING_LABEL.getStatusCode().equals(labelInfolist.getStatusCode())) {
			List<LabelOperateRecord> labelOperaRecord = labelOperateRecordMapper
					.selectByLabelAccnum(labelInfolist.getLabelAccnum());
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
		labelInfolistMapper.updateByPrimaryKey(labelInfolist);
		//记录在日志表中
		SysLog sysLog = new SysLog();
		sysLog.setCrtTime(new Date());
		sysLog.setCrtUser(labelInfolist.getUpdUser());
		sysLog.setUpdTime(new Date());
		sysLog.setUpdUser(labelInfolist.getUpdUser());
		sysLog.setLogincode(labelInfolist.getUpdUser());
		sysLog.setMethod("updateLabelStatus");
		sysLog.setOperation("修改标注列表状态");
		sysLog.setParams(labelInfolist.toString());
		sysLogMapper.insert(sysLog);

	}

	@Override
	public LabelInfolist queryById(String labelAccnum) {
		return labelInfolistMapper.selectByPrimaryKey(labelAccnum);
	}

	@Override
	public void updateLabelInfolist(LabelInfolistRequest labelInfolistRequest, LoginUserEntity loginUserEntity) {
		LabelInfolist labelInfolist = new LabelInfolist();
		labelInfolist.setApplyCheckNum(labelInfolistRequest.getLabelAccnum());
		LabelInfolist selectOne = labelInfolistMapper.selectOne(labelInfolist);
		selectOne.setUpdTime(new Date());
		selectOne.setUpdUser(loginUserEntity.getSysOperator().getLogincode());
		selectOne.setLabelTaskCode(labelInfolistRequest.getLabelTaskCode());
		selectOne.setNidusTypeCode(labelInfolistRequest.getNidusTypeCode());
		
		SysDictDtl labelTask = sysDictDtlService.quertDictDtlByDictId(labelInfolistRequest.getLabelTaskCode());
		if(labelTask!=null){
			selectOne.setLabelTask(labelTask.getOthervalue());
		}
		SysDictDtl nidusType = sysDictDtlService.quertDictDtlByDictId(labelInfolistRequest.getNidusTypeCode());
		if(nidusType!=null){
			selectOne.setLabelTask(nidusType.getOthervalue());
		}
		//记录在日志表中
		SysLog sysLog = new SysLog();
		sysLog.setCrtTime(new Date());
		sysLog.setCrtUser(loginUserEntity.getSysOperator().getLogincode());
		sysLog.setLogincode(loginUserEntity.getSysOperator().getLogincode());
		sysLog.setUpdTime(new Date());
		sysLog.setUpdUser(loginUserEntity.getSysOperator().getLogincode());
		sysLog.setMethod("updateLabelInfolist");
		sysLog.setParams(labelInfolistRequest.toString());
		sysLog.setOperation("修改标注列表");
		sysLogMapper.insert(sysLog);
	}

}
