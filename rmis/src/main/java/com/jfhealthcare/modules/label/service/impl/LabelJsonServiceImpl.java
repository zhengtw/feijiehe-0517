package com.jfhealthcare.modules.label.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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
		ArrayList<HashMap<String, Object>> resultList = labeljRequest.getJsonValue();
		if (resultList.size() < 1) {
			return;
		}
		String imageUid = "";
		String labelInfoId = "";
		String niType = "";
		HashMap<String, Object> map0 = resultList.get(0);
		String studyUid = (String) map0.get(LabelParamEnum.STUDY_UID.getParam());
		String seriesUid = (String) map0.get(LabelParamEnum.SERIES_UID.getParam());
		LabelInfolist lil = new LabelInfolist();
		lil.setSeriesUid(seriesUid);
		LabelInfolist selectOne = labelInfolistMapper.selectOne(lil);
		if (selectOne == null) {
			return;
		}
		LabelInfo lio = new LabelInfo();
		lio.setSeriesUid(seriesUid);
		List<LabelInfo> labelInfos = labelInfoMapper.select(lio);
		if (!ObjectUtils.isEmpty(labelInfos)) {
			for (LabelInfo labelInfo : labelInfos) {
				imageUid = imageUid + labelInfo.getImageUid();
				if(StringUtils.isNotBlank(labelInfo.getNidusType())){
					labelInfo.setNidusType("");
					labelInfoMapper.updateByPrimaryKey(labelInfo);
				}
			}
		}
		String labelAccnum = selectOne.getLabelAccnum();

		labelJsonMapper.deleteByLabelAccnum(labelAccnum);
		for (HashMap<String, Object> map : resultList) {
			LabelInfo labelInfo = new LabelInfo();
			if (!imageUid.contains((String) map.get(LabelParamEnum.IMAGE_UID.getParam()))) {
				labelInfo.setImageUid((String) map.get(LabelParamEnum.IMAGE_UID.getParam()));
				labelInfo.setNidusType((String) map.get(LabelParamEnum.NIDUS_TYPE.getParam()));
				labelInfo.setSeriesUid(seriesUid);
				labelInfo.setStudyUid(studyUid);
				labelInfo.setUid(labelAccnum);
				labelInfo.setUpdTime(new Date());
				labelInfo.setUpdUser(userId);
				HashMap viewPort = JSON.parseObject(JSON.toJSONString(map.get(LabelParamEnum.VIEW_PORT.getParam())),
						HashMap.class);
				HashMap voi = JSON.parseObject(JSON.toJSONString(viewPort.get(LabelParamEnum.VOI.getParam())),
						HashMap.class);
				labelInfo.setWinLevel(
						Double.valueOf(JSON.toJSONString(voi.get(LabelParamEnum.WINDOW_CENTER.getParam()))));
				labelInfo.setWinWidth(
						Double.valueOf(JSON.toJSONString(voi.get(LabelParamEnum.WINDOW_WIDTH.getParam()))));
				labelInfoMapper.insertSelective(labelInfo);
				labelInfoId = labelInfo.getId();
				imageUid = imageUid + labelInfo.getImageUid();
			} else {
				String imageUidd = (String) map.get(LabelParamEnum.IMAGE_UID.getParam());
				labelInfo.setImageUid(imageUidd);
				LabelInfo selectOne2 = labelInfoMapper.selectOne(labelInfo);
				labelInfoId = selectOne2.getId();
				if (StringUtils.isBlank(selectOne2.getNidusType()) || !selectOne2.getNidusType()
						.contains((String) map.get(LabelParamEnum.NIDUS_TYPE.getParam()))) {
					String oldnidusType = StringUtils.isBlank(selectOne2.getNidusType()) ? ""
							: selectOne2.getNidusType();
					String nidusTypep = oldnidusType + "|" + ((String) map.get(LabelParamEnum.NIDUS_TYPE.getParam()))
							+ "|";
					selectOne2.setNidusType(nidusTypep);
					labelInfoMapper.updateByPrimaryKey(selectOne2);
				}
			}
			LabelJson labelJson = new LabelJson();
			labelJson.setJsonId((String) map.get(LabelParamEnum.ID.getParam()));
			labelJson.setUpdTime(new Date());
			labelJson.setUpdUser(userId);
			labelJson.setLabelValueUid(labelInfoId);
			labelJson.setJsonValue1(JSON.toJSONString(map));
			labelJsonMapper.insertSelective(labelJson);
			if (StringUtils.isBlank(niType)
					|| !niType.contains((String) map.get(LabelParamEnum.NIDUS_TYPE.getParam()))) {
				niType = niType + "|" + ((String) map.get(LabelParamEnum.NIDUS_TYPE.getParam())) + "|";
			}
		}
		selectOne.setNidusType(niType);
		labelInfolistMapper.updateByPrimaryKey(selectOne);
		// 记录日志表
		SysLog sysLog = new SysLog();
		sysLog.setCrtTime(new Date());
		sysLog.setCrtUser(userId);
		sysLog.setUpdTime(new Date());
		sysLog.setUpdUser(userId);
		sysLog.setLogincode(userId);
		sysLog.setOperation("标注JSON记录修改");
		sysLog.setMethod("updateLabelJson");
		sysLog.setParams(JSON.toJSONString(labeljRequest.getJsonValue()));
		sysLogMapper.insert(sysLog);
	}

}
