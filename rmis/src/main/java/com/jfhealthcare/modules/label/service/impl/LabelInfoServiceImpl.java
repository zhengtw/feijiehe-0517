package com.jfhealthcare.modules.label.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.enums.AdminEnum;
import com.jfhealthcare.common.enums.LabelStatusEnum;
import com.jfhealthcare.modules.label.entity.LabelInfo;
import com.jfhealthcare.modules.label.entity.LabelInfolist;
import com.jfhealthcare.modules.label.entity.LabelJson;
import com.jfhealthcare.modules.label.mapper.LabelInfoMapper;
import com.jfhealthcare.modules.label.mapper.LabelInfolistMapper;
import com.jfhealthcare.modules.label.mapper.LabelJsonMapper;
import com.jfhealthcare.modules.label.request.LabelInfoRequest;
import com.jfhealthcare.modules.label.response.LabelInfoWebViewResponse;
import com.jfhealthcare.modules.label.service.LabelInfoService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class LabelInfoServiceImpl implements LabelInfoService {
	@Autowired
	private LabelInfoMapper labelInfoMapper;
	@Autowired
	private LabelInfolistMapper labelInfolistMapper;
	@Autowired
	private LabelJsonMapper labelJsonMapper;

	@Override
	public LabelInfoWebViewResponse queryInfoForWebView(LabelInfoRequest labelInfoRequest, String adminCode) {
		/**
		 * 1、获取标注信息列表中的数据 判断是否为未标注 2-1、未标注状态下 直接跳过查询JSON，直接去查询图像信息
		 * 2-2、标注中或未审核状态下 判断当前登陆人是否为审核医生，如果是则查所有标注信息，如果不是则查自己的标注信息 2-3、审核状态下
		 * 所有人都能看所有标注
		 */
		LabelInfoWebViewResponse labelInfoResponse = new LabelInfoWebViewResponse();
		HashMap<String, List<LabelJson>> labelJson = new HashMap<String, List<LabelJson>>();
		List<LabelInfo> labelInfo = new ArrayList<LabelInfo>();

		Example example = new Example(LabelInfolist.class);
		example.createCriteria().andEqualTo("applyCheckNum", labelInfoRequest.getCheckNum());
		List<LabelInfolist> labelInfolist = labelInfolistMapper.selectByExample(example);
		if (ObjectUtils.isEmpty(labelInfolist)) {
			return labelInfoResponse;
		}

		// 查询相关 标注信息数据
		Example example2 = new Example(LabelInfo.class);
		example2.createCriteria().andEqualTo("uid", labelInfolist.get(0).getLabelAccnum());
		labelInfo = labelInfoMapper.selectByExample(example2);
		labelInfoResponse.setLabelInfo(labelInfo);
		if (ObjectUtils.isEmpty(labelInfo)) {
			return labelInfoResponse;
		}

		// 未标注
		if (LabelStatusEnum.PENDING_LABEL.getStatusCode().equals(labelInfolist.get(0).getStatus())) {
			return labelInfoResponse;
		}

		// 标注中 或者已经完成标注但是未审核 TODO 审核中的虽然查全部但是 要置灰 不让保存，后续考虑返回字段中加一个 ISRead 是否只读
		if (LabelStatusEnum.IN_LABEL.getStatusCode().equals(labelInfolist.get(0).getStatus())
				|| LabelStatusEnum.LABELED.getStatusCode().equals(labelInfolist.get(0).getStatus())) {
			if (AdminEnum.JICENG.getAdminCode().equals(adminCode)
					|| AdminEnum.REPORT.getAdminCode().equals(adminCode)) {
				// 报告医生查自己的
				queryJsonByLabelAndUserId(labelInfo, labelJson, labelInfoRequest.getUserId());
			} else {
				// 查所有
				queryJsonByLabelAndUserId(labelInfo, labelJson, null);
			}
		}
		// 已审核状态
		if (LabelStatusEnum.LABEL_CHECKED.getStatusCode().equals(labelInfolist.get(0).getStatus())) {
			queryJsonByLabelAndUserId(labelInfo, labelJson, null);
		}

		labelInfoResponse.setLabelJson(labelJson);

		return labelInfoResponse;
	}

	@Override
	public PageInfo<LabelJson> queryInfoForWeb(String labelAccnum, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<LabelJson> labelJson = labelJsonMapper.selectByLabelAccnum(labelAccnum);
		PageInfo<LabelJson> pageInfo = new PageInfo<LabelJson>(labelJson);
		return pageInfo;
	}

	@Override
	public void updateLabelInfo(LabelInfo labelInfo) {
		labelInfoMapper.updateByPrimaryKeySelective(labelInfo);
	}

	private HashMap<String, List<LabelJson>> queryJsonByLabelAndUserId(List<LabelInfo> labelInfo,
			HashMap<String, List<LabelJson>> laMap, String userId) {
		for (LabelInfo la : labelInfo) {
			Example example3 = new Example(LabelJson.class);
			Criteria criteria = example3.createCriteria();
			criteria.andEqualTo("labelValueUid", la.getId());
			if (StringUtils.isNotBlank(userId)) {
				criteria.andEqualTo("crtUser", userId);
			}
			List<LabelJson> labelJson = labelJsonMapper.selectByExample(example3);
			if (ObjectUtils.isEmpty(labelJson)) {
				laMap.put(la.getImageUid(), new ArrayList<LabelJson>());
			} else {
				laMap.put(la.getImageUid(), labelJson);
			}
		}

		return laMap;
	}

}
