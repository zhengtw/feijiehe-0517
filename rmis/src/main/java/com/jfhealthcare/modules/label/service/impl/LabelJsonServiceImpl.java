package com.jfhealthcare.modules.label.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.jfhealthcare.common.enums.LabelParamEnum;
import com.jfhealthcare.modules.label.entity.LabelInfo;
import com.jfhealthcare.modules.label.entity.LabelInfolist;
import com.jfhealthcare.modules.label.entity.LabelJson;
import com.jfhealthcare.modules.label.mapper.LabelInfoMapper;
import com.jfhealthcare.modules.label.mapper.LabelInfolistMapper;
import com.jfhealthcare.modules.label.mapper.LabelJsonMapper;
import com.jfhealthcare.modules.label.request.LabelJsonRequest;
import com.jfhealthcare.modules.label.service.LabelJsonService;
import com.jfhealthcare.modules.system.entity.SysLog;
import com.jfhealthcare.modules.system.mapper.SysLogMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class LabelJsonServiceImpl implements LabelJsonService {
	@Autowired
	private LabelJsonMapper labelJsonMapper;
	@Autowired
	private LabelInfolistMapper labelInfolistMapper;
	@Autowired
	private LabelInfoMapper labelInfoMapper;
	@Autowired
	private SysLogMapper sysLogMapper;

	@Override
	public List<LabelJson> queryByUid(String id) {
		Example example = new Example(LabelJson.class);
		example.createCriteria().andEqualTo("labelValueUid", id);
		return labelJsonMapper.selectByExample(example);
	}

	@Override
	@Transactional
	public void updateByParams(LabelJsonRequest labeljRequest, String userId) {
		// 先删除，后新增
		String jsonValue = labeljRequest.getJsonValue();
		if (StringUtils.isBlank(jsonValue) || !jsonValue.startsWith("[") || !jsonValue.endsWith("]")
				|| jsonValue.length() < 3) {
			return;
		}
		String nidusType = "";
		String imageUid = "";
		String labelInfoId = "";
		List<Map> resultList = JSON.parseArray(jsonValue, Map.class);
		Map map0 = resultList.get(0);
		String studyUid = (String) map0.get(LabelParamEnum.STUDY_UID.getParam());
		String seriesUid = (String) map0.get(LabelParamEnum.SERIES_UID.getParam());
		LabelInfolist lil = new LabelInfolist();
		lil.setSeriesUid(seriesUid);
		LabelInfolist selectOne = labelInfolistMapper.selectOne(lil);
		if (selectOne == null) {
			return;
		}
		String labelAccnum = selectOne.getLabelAccnum();

		labelJsonMapper.deleteByLabelAccnum(labelAccnum);
		for (Map map : resultList) {
			Boolean isNidusTypeChange =Boolean.FALSE;
			if (!imageUid.contains((String) map.get(LabelParamEnum.IMAGE_UID.getParam()))) {
				LabelInfo labelInfo = new LabelInfo();
				labelInfo.setImageUid((String) map.get(LabelParamEnum.IMAGE_UID.getParam()));
				labelInfo.setNidusType((String) map.get(LabelParamEnum.NIDUS_TYPE.getParam()));
				labelInfo.setSeriesUid(seriesUid);
				labelInfo.setStudyUid(studyUid);
				labelInfo.setUid(labelAccnum);
				labelInfo.setUpdTime(new Date());
				labelInfo.setUpdUser(userId);
				HashMap viewPort = JSON.parseObject((String) map.get(LabelParamEnum.VIEW_PORT.getParam()),
						HashMap.class);
				HashMap voi = JSON.parseObject((String) viewPort.get(LabelParamEnum.VOI.getParam()), HashMap.class);
				labelInfo.setWinLevel((Double) voi.get(LabelParamEnum.WINDOW_CENTER.getParam()));
				labelInfo.setWinWidth((Double) voi.get(LabelParamEnum.WINDOW_WIDTH.getParam()));
				labelInfoMapper.insertSelective(labelInfo);
				labelInfoId = labelInfo.getId();
				imageUid = imageUid + labelInfo.getImageUid();
				if(StringUtils.isNotBlank(labelInfo.getNidusType())&&!labelInfo.getNidusType().contains(nidusType)){
					nidusType= nidusType+labelInfo.getNidusType()+"|";
					isNidusTypeChange = Boolean.TRUE;
				}
			}
			LabelJson labelJson = new LabelJson();
			labelJson.setJsonId((String)map.get(LabelParamEnum.ID.getParam()));
			labelJson.setUpdTime(new Date());
			labelJson.setUpdUser(userId);
			labelJson.setLabelValueUid(labelInfoId);
			labelJson.setLabelValueUid(JSON.toJSONString(map));
			labelJsonMapper.insertSelective(labelJson);
			if(isNidusTypeChange){
				selectOne.setNidusType(nidusType);
			}
		}
		// 记录日志表
		SysLog sysLog = new SysLog();
		sysLog.setCrtTime(new Date());
		sysLog.setCrtUser(userId);
		sysLog.setLogincode(userId);
		sysLog.setMethod("updateLabelJson");
		sysLog.setParams(labeljRequest.getJsonValue());
		sysLogMapper.insert(sysLog);
	}

}
