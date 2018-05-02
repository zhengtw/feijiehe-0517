package com.jfhealthcare.modules.business.service.impl;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.entity.CommonStaticValue;
import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.common.entity.MyPageInfo;
import com.jfhealthcare.common.enums.AdminEnum;
import com.jfhealthcare.common.enums.CheckStatusEnum;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.common.properties.BtnPropertiesConfig;
import com.jfhealthcare.common.utils.DateUtils;
import com.jfhealthcare.common.utils.NameUtils;
import com.jfhealthcare.common.utils.TransferUtils;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.modules.apply.entity.ApplyWorklist;
import com.jfhealthcare.modules.apply.mapper.ApplyWorklistMapper;
import com.jfhealthcare.modules.business.entity.BusinCheckFlowState;
import com.jfhealthcare.modules.business.entity.BusinChecklistIndex;
import com.jfhealthcare.modules.business.entity.RepImage;
import com.jfhealthcare.modules.business.entity.RepRecord;
import com.jfhealthcare.modules.business.entity.ViewWorklist;
import com.jfhealthcare.modules.business.mapper.BusinCheckDmcRecordMapper;
import com.jfhealthcare.modules.business.mapper.BusinCheckFlowStateMapper;
import com.jfhealthcare.modules.business.mapper.BusinChecklistIndexMapper;
import com.jfhealthcare.modules.business.mapper.RepImageMapper;
import com.jfhealthcare.modules.business.mapper.RepRecordMapper;
import com.jfhealthcare.modules.business.mapper.ViewWorklistMapper;
import com.jfhealthcare.modules.business.request.ViewWorklistRequest;
import com.jfhealthcare.modules.business.response.ViewWorklistResponse;
import com.jfhealthcare.modules.business.service.ViewWorklistService;
import com.jfhealthcare.modules.system.entity.RepGroup;
import com.jfhealthcare.modules.system.entity.SysDictDtl;
import com.jfhealthcare.modules.system.mapper.RepGroupMapper;
import com.jfhealthcare.modules.system.service.SysDictDtlService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Slf4j
@Service
public class ViewWorklistServiceImpl implements ViewWorklistService {

	@Autowired
	private ViewWorklistMapper viewWorklistMapper;
	
	@Autowired
	private BusinCheckFlowStateMapper businCheckFlowStateMapper;
	
	@Autowired
	private RepRecordMapper repRecordMapper;
	
	@Autowired
	private BusinChecklistIndexMapper businChecklistIndexMapper;
	

	@Autowired
	private RepGroupMapper repGroupMapper;
	
	@Autowired
	private RepImageMapper repImageMapper;
	
	@Autowired
	private SysDictDtlService sysDictDtlService;
	
//	@Autowired
//	private BtnPropertiesConfig btnPropertiesConfig;
	
	@Value("${dcm.webview.url}")
	private String webViewUrl;
	
	@Override
	public MyPageInfo<ViewWorklistResponse> queryViewWorklist(ViewWorklistRequest vwlr) {
		if(vwlr.getCheckDate()!=null){
			if(vwlr.getCheckApplyStartTime()==null || vwlr.getCheckApplyEndTime()==null){
				List<Date> checkTime = DateUtils.getCheckTime(vwlr.getCheckDate());
				if(!ObjectUtils.isEmpty(checkTime)){
					vwlr.setCheckApplyStartTime(checkTime.get(0));
					vwlr.setCheckApplyEndTime(checkTime.get(1));
				}
			}
		}
		PageHelper.startPage(vwlr.getPageNum(), vwlr.getPageSize());
		List<ViewWorklistResponse> vwls=viewWorklistMapper.queryViewWorklist(vwlr);
		MyPageInfo<ViewWorklistResponse> pageInfo = new MyPageInfo<ViewWorklistResponse>(vwls);
		if("1".equals(vwlr.getIsButton())) {
			List<Map<String, Object>> queryCountViewWorklist = viewWorklistMapper.queryCountViewWorklist(vwlr);
			pageInfo.setOtherValue(queryCountViewWorklist);
		}
		
		return pageInfo;
	}
	
    @Override
	public ViewWorklistResponse queryOneViewWorklist(String checkAccessionNum) {
		ViewWorklistRequest vwlr=new ViewWorklistRequest();
		vwlr.setCheckAccessionNum(checkAccessionNum);
		List<ViewWorklistResponse> vwls=viewWorklistMapper.queryViewWorklist(vwlr);
		if(!CollectionUtils.isEmpty(vwls)){
			ViewWorklistResponse viewWorklistResponse = vwls.get(0);
			String reprcdRepUid = viewWorklistResponse.getReprcdRepUid();
			RepImage repImage=new RepImage();
			repImage.setRepUid(reprcdRepUid);
			List<RepImage> repImages = repImageMapper.select(repImage);
			List<Map<String, String>> uidmaps=new ArrayList<Map<String, String>>();
			for (RepImage rimg : repImages) {
				String[] splits = StringUtils.split(rimg.getImgPage(), "&");
				Map<String, String> uidmap=new HashMap<String, String>();
				for (String uids : splits) {
					String[] keyAndUid = uids.split("=");
					String uid = keyAndUid[1];
					if("studyUID".equals(keyAndUid[0])) {
						uidmap.put("StudyUid", uid);
					}else if("seriesUID".equals(keyAndUid[0])) {
						uidmap.put("SeriesUid", uid);
					}else if("objectUID".equals(keyAndUid[0])) {
						uidmap.put("ObjectUid", uid);
					}
				}
				uidmaps.add(uidmap);
			}
			try {
				String encode = URLEncoder.encode(JSON.toJSONString(uidmaps),"UTF-8");
				viewWorklistResponse.setSopUrl(webViewUrl+encode);
				log.info("web view url:{}", uidmaps);
			} catch (Exception e) {
				throw new RmisException("web view encode exception!");
			}
			//查询历史报告数量
			String[] status = new String[] {"3557","3556"};
			Example ex=new Example(ViewWorklist.class);
			ex.createCriteria().andEqualTo("checkApplyHospCode", viewWorklistResponse.getCheckApplyHospCode())
			 .andEqualTo("patName", viewWorklistResponse.getPatName()).andIn("checkStatusCode", Arrays.asList(status))
			 .andNotEqualTo("checkAccessionNum", checkAccessionNum)
			 .andBetween("checkApplyTime", DateUtils.getSomeYearsBeforeAfter(new Date(), -3), new Date());
			int historyNum = viewWorklistMapper.selectCountByExample(ex);
			viewWorklistResponse.setHistoryNum(historyNum);
			return viewWorklistResponse;
		}
		return new ViewWorklistResponse();
	}
    
    @Override
	public String queryWebviewerUrlByAccessionNum(String checkAccessionNum) {
    	RepRecord repRecord=new RepRecord();
    	repRecord.setAccessionNum(checkAccessionNum);
    	List<RepRecord> repRecords = repRecordMapper.select(repRecord);
    	Assert.isListOnlyOne(repRecords, "流水号对应的报告记录不存在或者存在多个！");
    	RepImage repImage=new RepImage();
		repImage.setRepUid(repRecords.get(0).getRepUid());
		List<RepImage> repImages = repImageMapper.select(repImage);
		List<Map<String, String>> uidmaps=new ArrayList<Map<String, String>>();
		for (RepImage rimg : repImages) {
			String[] splits = StringUtils.split(rimg.getImgPage(), "&");
			Map<String, String> uidmap=new HashMap<String, String>();
			for (String uids : splits) {
				String[] keyAndUid = uids.split("=");
				String uid = keyAndUid[1];
				if("studyUID".equals(keyAndUid[0])) {
					uidmap.put("StudyUid", uid);
				}else if("seriesUID".equals(keyAndUid[0])) {
					uidmap.put("SeriesUid", uid);
				}else if("objectUID".equals(keyAndUid[0])) {
					uidmap.put("ObjectUid", uid);
				}
			}
			uidmaps.add(uidmap);
		}
		try {
			String encode = URLEncoder.encode(JSON.toJSONString(uidmaps),"UTF-8");
			return webViewUrl+encode;
		} catch (Exception e) {
			throw new RmisException("web view encode exception!");
		}
	}
	
	
	@Override
	public Map<String, String> queryBtnsByCheckAccessionNum(String checkAccessionNum, LoginUserEntity user) {
		ViewWorklistRequest vwlr=new ViewWorklistRequest();
		vwlr.setCheckAccessionNum(checkAccessionNum);
		List<ViewWorklistResponse> vwls=viewWorklistMapper.queryViewWorklist(vwlr);
//		Map<String, String> allBtnsMap = btnPropertiesConfig.getBtnsMap();
		Map<String, String> allBtnsMap = CommonStaticValue.getBtnsMap();
		
		Map<String, String> btnsMap=getBtns(null);
		if(!CollectionUtils.isEmpty(vwls)) {
			//1.确定按钮状态   
			ViewWorklistResponse viewWorklistResponse = vwls.get(0);
			String checkStatusCode = viewWorklistResponse.getCheckStatusCode();//检查状态
			String adminCode = user.getSysOperator().getAdminCode();//审核权限
			String loginCode = user.getSysOperator().getLogincode();//登录账号
			String btn = allBtnsMap.get(adminCode+checkStatusCode);
			if(StringUtils.isNoneBlank(btn)) {
				//返回结果不为空  ：  匹配中有其存在的按钮  直接返回
				btnsMap = getBtns(btn);
			}else {
				//返回结果为空  ：   匹配中没有存在的按钮
				/**
				 * 报告中： 自己的可打开 可操作   其他权限通待报告     其他人的不可打开
				 * 审核中： 自己的可打开 可操作   其他权限通待审核     其他人的不可打开
				 * 暂存中： 状态取消   按钮保留，功能保留为保存
				 */
				//报告中
				if(CheckStatusEnum.REPORTING.getStatusCode().equals(checkStatusCode)) {
					//自己的
					if(loginCode.equals(viewWorklistResponse.getCheckReportDr())) {
						String reportingBtn = allBtnsMap.get(adminCode+CheckStatusEnum.PENDING_REPORT.getStatusCode());
						btnsMap = getBtns(reportingBtn);
					}else {
					//不是自己的
						btnsMap.put("isOpen", "0");
					}
				//审核中
				}else if(CheckStatusEnum.REVIEWING.getStatusCode().equals(checkStatusCode)) {
					//自己的
					if(loginCode.equals(viewWorklistResponse.getCheckAuditDr())) {
						String reviewingBtn = allBtnsMap.get(adminCode+CheckStatusEnum.PENDING_ONE_REVIEW.getStatusCode());
						btnsMap = getBtns(reviewingBtn);
					}else {
					//不是自己的
						btnsMap.put("isOpen", "0");
					}
				//已作废
				}else if(CheckStatusEnum.COMPLETE_ABANDONED.getStatusCode().equals(checkStatusCode)) {
					btnsMap.put("isOpen", "0");
				//待会报
				}else if(CheckStatusEnum.PENGING_HUIZHENG_REPORT.getStatusCode().equals(checkStatusCode)) {
					String huibaoBtn = allBtnsMap.get(adminCode+CheckStatusEnum.PENDING_REPORT.getStatusCode());
					if(StringUtils.isNoneBlank(huibaoBtn)) {
						btnsMap = getBtns(huibaoBtn);
					}
				//待会审
				}else if(CheckStatusEnum.PENGING_HUIZHENG_REVIEW.getStatusCode().equals(checkStatusCode)) {
					String huishenBtn = allBtnsMap.get(adminCode+CheckStatusEnum.PENDING_ONE_REVIEW.getStatusCode());
					if(StringUtils.isNoneBlank(huishenBtn)) {
						btnsMap = getBtns(huishenBtn);
					}
				}
			}
		}
		return btnsMap;
	}
	
	private Map<String, String> getBtns(String newBtns) {
		Map<String, String> btnsMap=new HashMap<String, String>() ;
//		String btnString = btnPropertiesConfig.getBtnsMap().get("00000000");
		String btnString = CommonStaticValue.getBtnsMap().get("00000000");
		String[] allBtns = StringUtils.split(btnString,",");
		for (String allbtn : allBtns) {
			/**
			 * 所有按钮初始时为不可用状态，除了 退出和预览
			 */
			if(StringUtils.equalsAny(allbtn, "isOpen","tc","yl")) {
				btnsMap.put(allbtn, "1");
			}else {
				btnsMap.put(allbtn, "0");
			}
		}
		/**
		 * 给有权限的按钮复制为可使用状态
		 */
		if(StringUtils.isNotEmpty(newBtns)) {
			String[] split = StringUtils.split(newBtns, ",");
			for (String btn : split) {
				btnsMap.put(btn, "1");
			}
		}
		return btnsMap;
	}
	
	
	@Override
	public List<ViewWorklistResponse> queryHistoryReport(ViewWorklistRequest vr) {
		List<ViewWorklistResponse> vws=viewWorklistMapper.queryHistoryReport(vr);
		return vws;
	}
	
	@Override
	@Transactional
	public void updateCheckListIndex(ViewWorklistRequest vmlr,LoginUserEntity loginUserEntity) {
		BusinChecklistIndex bc=new BusinChecklistIndex();
		bc.setAccessionNum(vmlr.getCheckAccessionNum());
		List<BusinChecklistIndex> bCLIs = businChecklistIndexMapper.select(bc);
		Assert.isListOnlyOne(bCLIs, "检查报告异常，请联系开发人员！");
		BusinChecklistIndex bCLI = bCLIs.get(0);
		
		RepRecord repRecord=new RepRecord();
		repRecord.setAccessionNum(vmlr.getCheckAccessionNum());
		List<RepRecord> repRecords = repRecordMapper.select(repRecord);
		Assert.isListOnlyOne(repRecords, "诊断报告异常，请联系开发人员！");
		RepRecord rrd = repRecords.get(0);
		
		//2018 03 26 重构更新功能    以按钮传值为准
		//isOpen,wctj,shxf,tc,jj,fq,zc,yl,zhz,zf,thcx: 打开 退回 拒绝 预览 暂存 转会诊 完成提交 完成下发 作废 退回重写
		String statusCode = bCLI.getStatusCode();//当前状态值   打开报告没做更改时  的状态   
		String status = bCLI.getStatus();//当前状态
		String checkBut = vmlr.getCheckBut();//按钮传值
		Assert.isBlank(checkBut, "按钮状态不能为空！");
		if(StringUtils.equals("isOpen", checkBut)) {
			Example example = new Example(BusinChecklistIndex.class);
			Criteria createCriteria = example.createCriteria();
			//第一次打开   待报告     待会报
			if(StringUtils.equalsAny(statusCode,CheckStatusEnum.PENDING_REPORT.getStatusCode(),CheckStatusEnum.PENGING_HUIZHENG_REPORT.getStatusCode())  ) {
				createCriteria.andNotEqualTo("statusCode", CheckStatusEnum.REPORTING.getStatusCode());
				createCriteria.andEqualTo("accessionNum", vmlr.getCheckAccessionNum());
				
				bCLI.setStatus(CheckStatusEnum.REPORTING.getStatus());
				bCLI.setStatusCode(CheckStatusEnum.REPORTING.getStatusCode());
				bCLI.setReportDr(NameUtils.getLoginCode());
				bCLI.setReportTime(new Date());
			}else if(StringUtils.equalsAny(statusCode,CheckStatusEnum.PENDING_ONE_REVIEW.getStatusCode(),CheckStatusEnum.PENGING_HUIZHENG_REVIEW.getStatusCode(),
					  CheckStatusEnum.PENDING_TWO_REVIEW.getStatusCode(),CheckStatusEnum.PENDING_THREE_REVIEW.getStatusCode())) {
				//第一次打开   待审核 待二审 待三审    待会审
				createCriteria.andNotEqualTo("statusCode", CheckStatusEnum.REVIEWING.getStatusCode());
				createCriteria.andEqualTo("accessionNum", vmlr.getCheckAccessionNum());
				
				bCLI.setStatus(CheckStatusEnum.REVIEWING.getStatus());
				bCLI.setStatusCode(CheckStatusEnum.REVIEWING.getStatusCode());
				bCLI.setAuditDr(NameUtils.getLoginCode());
				bCLI.setAuditTime(new Date());
			}
		}else if(StringUtils.equals(checkBut, "wctj")) {
			//完成提交   报告   一审  二审    wctj wctj wctj
			if(StringUtils.equalsAny(statusCode,CheckStatusEnum.PENDING_REPORT.getStatusCode(),
					CheckStatusEnum.REPORTING.getStatusCode(),CheckStatusEnum.PENGING_HUIZHENG_REPORT.getStatusCode())) {
				bCLI.setStatus(CheckStatusEnum.PENDING_ONE_REVIEW.getStatus());
				bCLI.setStatusCode(CheckStatusEnum.PENDING_ONE_REVIEW.getStatusCode());
				bCLI.setReportTime(new Date());
				bCLI.setReportDr(NameUtils.getLoginCode());
			}else if(StringUtils.equals(CheckStatusEnum.PENDING_ONE_REVIEW.getStatusCode(), statusCode)) {
				bCLI.setStatus(CheckStatusEnum.PENDING_TWO_REVIEW.getStatus());
				bCLI.setStatusCode(CheckStatusEnum.PENDING_TWO_REVIEW.getStatusCode());
				bCLI.setAuditTime(new Date());
				bCLI.setAuditDr(NameUtils.getLoginCode());
			}else if(StringUtils.equals(CheckStatusEnum.PENDING_TWO_REVIEW.getStatusCode(), statusCode)) {
				bCLI.setStatus(CheckStatusEnum.PENDING_THREE_REVIEW.getStatus());
				bCLI.setStatusCode(CheckStatusEnum.PENDING_THREE_REVIEW.getStatusCode());
				bCLI.setAuditTime(new Date());
				bCLI.setAuditDr(NameUtils.getLoginCode());
			}else if(StringUtils.equalsAny(statusCode,CheckStatusEnum.REVIEWING.getStatusCode(),CheckStatusEnum.PENGING_HUIZHENG_REVIEW.getStatusCode())) {
				String adminCode = loginUserEntity.getSysOperator().getAdminCode();
				if(StringUtils.equals(AdminEnum.ONE_REVIEW.getAdminCode(), adminCode)) {
					bCLI.setStatus(CheckStatusEnum.PENDING_TWO_REVIEW.getStatus());
					bCLI.setStatusCode(CheckStatusEnum.PENDING_TWO_REVIEW.getStatusCode());
				}else if(StringUtils.equals(AdminEnum.TWO_REVIEW.getAdminCode(), adminCode)) {
					bCLI.setStatus(CheckStatusEnum.PENDING_THREE_REVIEW.getStatus());
					bCLI.setStatusCode(CheckStatusEnum.PENDING_THREE_REVIEW.getStatusCode());
				}
				bCLI.setAuditTime(new Date());
				bCLI.setAuditDr(NameUtils.getLoginCode());
			}
			rrd.setFinding1(vmlr.getReprcdFinding1());
			rrd.setImpression1(vmlr.getReprcdImpression1());
			rrd.setHp(vmlr.getReprcdHp());
		}else if(StringUtils.equals(checkBut, "shxf")) {
			//审核下发
			bCLI.setStatus(CheckStatusEnum.COMPLETE_REVIEW.getStatus());
			bCLI.setStatusCode(CheckStatusEnum.COMPLETE_REVIEW.getStatusCode());
			if(StringUtils.isEmpty(bCLI.getReportDr())) {
				bCLI.setReportTime(new Date());
				bCLI.setReportDr(NameUtils.getLoginCode());
			}
			bCLI.setAuditTime(new Date());
			bCLI.setAuditDr(NameUtils.getLoginCode());
			rrd.setFinding1(vmlr.getReprcdFinding1());
			rrd.setImpression1(vmlr.getReprcdImpression1());
			rrd.setHp(vmlr.getReprcdHp());
		}else if(StringUtils.equalsAny(checkBut, "tc","zc")) {
			//退出  暂存
			rrd.setFinding1(vmlr.getReprcdFinding1());
			rrd.setImpression1(vmlr.getReprcdImpression1());
			rrd.setHp(vmlr.getReprcdHp());
		}else if(StringUtils.equals(checkBut, "jj")) {
			//已拒绝
			bCLI.setStatus(CheckStatusEnum.COMPLETE_REFUSE.getStatus());
			bCLI.setStatusCode(CheckStatusEnum.COMPLETE_REFUSE.getStatusCode());
			SysDictDtl sysDictDtl = sysDictDtlService.queryDictDtlById(vmlr.getCheckRefuseCode());
			bCLI.setRefuseCode(vmlr.getCheckRefuseCode());
			bCLI.setRefuseName(sysDictDtl.getOthervalue());
			if(StringUtils.isNotBlank(vmlr.getCheckRefuseName())) {
				bCLI.setRefuseName(vmlr.getCheckRefuseName());
			}
		}else if(StringUtils.equals(checkBut, "zf")) {
			//作废
			bCLI.setStatus(CheckStatusEnum.COMPLETE_ABANDONED.getStatus());
			bCLI.setStatusCode(CheckStatusEnum.COMPLETE_ABANDONED.getStatusCode());
		}else if(StringUtils.equals(checkBut, "zhz")) {
			//转会诊
			//报告转会诊
			if(StringUtils.equals(statusCode,CheckStatusEnum.REPORTING.getStatusCode())) {
				bCLI.setReportDr(null);
				bCLI.setReportTime(null);
				rrd.setFinding1(null);
				rrd.setImpression1(null);
				rrd.setHp(null);
				bCLI.setStatus(CheckStatusEnum.PENGING_HUIZHENG_REPORT.getStatus());
				bCLI.setStatusCode(CheckStatusEnum.PENGING_HUIZHENG_REPORT.getStatusCode());
				RepGroup repGroup=new RepGroup();
				repGroup.setStatus("1");
				//会诊分组---没有会诊则不修改
				List<RepGroup> repGroups = repGroupMapper.select(repGroup);
				if(!ObjectUtils.isEmpty(repGroups)){
					RepGroup rg = repGroups.get(0);
					bCLI.setRepGroupId(rg.getId());
				}
			//审核转会诊
			}else if(StringUtils.equals(statusCode,CheckStatusEnum.REVIEWING.getStatusCode())) {
				bCLI.setAuditDr(null);
				bCLI.setAuditTime(null);
				bCLI.setStatus(CheckStatusEnum.PENGING_HUIZHENG_REVIEW.getStatus());
				bCLI.setStatusCode(CheckStatusEnum.PENGING_HUIZHENG_REVIEW.getStatusCode());
				
				RepGroup repGroup=new RepGroup();
				repGroup.setStatus("1");
				//会诊分组---没有会诊则不修改
				List<RepGroup> repGroups = repGroupMapper.select(repGroup);
				if(!ObjectUtils.isEmpty(repGroups)){
					RepGroup rg = repGroups.get(0);
					bCLI.setRepGroupId(rg.getId());
				}
				rrd.setFinding1(vmlr.getReprcdFinding1());
				rrd.setImpression1(vmlr.getReprcdImpression1());
				rrd.setHp(vmlr.getReprcdHp());
			}
		}else if(StringUtils.equals(checkBut, "fq")) {
			//放弃 报告中放弃
			if(StringUtils.equals(statusCode,CheckStatusEnum.REPORTING.getStatusCode())) {
				bCLI.setStatus(CheckStatusEnum.PENDING_REPORT.getStatus());
				bCLI.setStatusCode(CheckStatusEnum.PENDING_REPORT.getStatusCode());
				bCLI.setReportDr(null);
				bCLI.setReportTime(null);
				rrd.setFinding1(null);
				rrd.setImpression1(null);
				rrd.setHp(null);
			}else if(StringUtils.equals(statusCode,CheckStatusEnum.REVIEWING.getStatusCode())) {
				//放弃 审核中放弃	
				List<BusinCheckFlowState> flows=businCheckFlowStateMapper.selectFlowRorIndex(bc.getAccessionNum());
				Assert.isNull(flows, "报告流记录异常！");
				BusinCheckFlowState businCheckFlowState = flows.get(0);
				bCLI.setStatus(businCheckFlowState.getStatus());
				bCLI.setStatusCode(businCheckFlowState.getStatusCode());
				bCLI.setAuditDr(businCheckFlowState.getOperationUser());
				bCLI.setAuditTime(businCheckFlowState.getOperationTime());
                if(StringUtils.equalsAny(businCheckFlowState.getRemark(), "ai转审核->待一审","报告中->待一审","审核中->待一审")) {
                	bCLI.setStatus(CheckStatusEnum.PENDING_ONE_REVIEW.getStatus());
    				bCLI.setStatusCode(CheckStatusEnum.PENDING_ONE_REVIEW.getStatusCode());
                	bCLI.setAuditDr(null);
    				bCLI.setAuditTime(null);
				}else if(StringUtils.endsWith(businCheckFlowState.getRemark(), "转会审")) {
					bCLI.setAuditDr(null);
    				bCLI.setAuditTime(null);
				}
				rrd.setFinding1(businCheckFlowState.getFinding());
				rrd.setImpression1(businCheckFlowState.getImpression());
				rrd.setHp(businCheckFlowState.getHp());
			}
		}else if(StringUtils.equals(checkBut, "thcx")) {
			//退回重写
			Example ex =new Example(BusinCheckFlowState.class);
			ex.createCriteria().andEqualTo("accessionNum", bc.getAccessionNum()).andEqualTo("remark", "报告中->待一审");
			ex.setOrderByClause("OPERATION_TIME desc");
			List<BusinCheckFlowState> flows = businCheckFlowStateMapper.selectByExample(ex);
			Assert.isNull(flows, "报告流记录异常！");
			BusinCheckFlowState businCheckFlowState = flows.get(0);
			bCLI.setStatus(CheckStatusEnum.REPORTING.getStatus());
			bCLI.setStatusCode(CheckStatusEnum.REPORTING.getStatusCode());
			bCLI.setAuditDr(null);
			bCLI.setAuditTime(null);
			rrd.setFinding1(businCheckFlowState.getFinding());
			rrd.setImpression1(businCheckFlowState.getImpression());
			rrd.setHp(businCheckFlowState.getHp());
		}
		businChecklistIndexMapper.updateByPrimaryKey(bCLI);
		repRecordMapper.updateByPrimaryKey(rrd);
		updateReportFlow(statusCode,bCLI,rrd);
		log.info(loginUserEntity.getSysOperator().getLogincode()+"：报告(accessnum:"+bCLI.getAccessionNum()+")处理后状态为："+bCLI.getStatus());
	}
	
	//改前的原始状态    改后的index以及报告记录
	private void updateReportFlow(String statusCode, BusinChecklistIndex bCLI, RepRecord rrd) {
		Example ex=new Example(BusinCheckFlowState.class);
		ex.createCriteria().andEqualTo("accessionNum", bCLI.getAccessionNum());
		int count = businCheckFlowStateMapper.selectCountByExample(ex);
		
		BusinCheckFlowState bcfs=new BusinCheckFlowState();
		bcfs.setAccessionNum(bCLI.getAccessionNum());
		bcfs.setNumber(count+1);
		bcfs.setStatus(bCLI.getStatus());
		bcfs.setStatusCode(bCLI.getStatusCode());
		bcfs.setOperationUser(NameUtils.getLoginCode());
		bcfs.setOperationTime(new Date());
		bcfs.setFinding(rrd.getFinding1());
		bcfs.setImpression(rrd.getImpression1());
		bcfs.setHp(rrd.getHp());
		if(count==0) { //报告初创   记录ai的操作
			//初次记录 ：  正常经过ai 到待报告  待审核    更新后到  报告中  审核中
			bcfs.setRemark(CommonStaticValue.getFlowByKey(bCLI.getStatusAiCode()));
			businCheckFlowStateMapper.insertSelective(bcfs);
			log.info("报告流：accessnum:"+bCLI.getAccessionNum()+",remark:"+bcfs.getRemark());
			//初次记录 ： ai 出结果后直接分配的
			if(StringUtils.equalsAny(statusCode, CheckStatusEnum.REPORTING.getStatusCode(), CheckStatusEnum.REVIEWING.getStatusCode())){
				bcfs.setId(null);
				bcfs.setNumber(bcfs.getNumber()+1);
				bcfs.setRemark(CommonStaticValue.getFlowByKey(statusCode));
				businCheckFlowStateMapper.insertSelective(bcfs);
				log.info("报告流：accessnum:"+bCLI.getAccessionNum()+",remark:"+bcfs.getRemark());
			}
		}
		bcfs.setId(null);
		bcfs.setNumber(bcfs.getNumber()+1);
		bcfs.setRemark(CommonStaticValue.getFlowByKey(statusCode+"_"+bCLI.getStatusCode()));
		businCheckFlowStateMapper.insertSelective(bcfs);
		log.info("报告流：accessnum:"+bCLI.getAccessionNum()+",remark:"+bcfs.getRemark());
	}

	/**
	 * 报告流跟踪
	 * @param accessionNum
	 * @param status
	 * @param statusCode
	 */
//	private void updateReportFlow(String accessionNum, String status, String statusCode,int num) {
//		BusinCheckFlowState bcfs=new BusinCheckFlowState();
//		bcfs.setAccessionNum(accessionNum);
//		bcfs.setNumber(num);
//		List<BusinCheckFlowState> businCheckFlowStates = businCheckFlowStateMapper.select(bcfs);
//		bcfs.setStatus(status);
//		bcfs.setStatusCode(statusCode);
//		bcfs.setOperationUser(NameUtils.getLoginCode());
//		bcfs.setOperationTime(new Date());
//		if(CollectionUtils.isEmpty(businCheckFlowStates)) {
//			businCheckFlowStateMapper.insertSelective(bcfs);
//		}else {
//			bcfs.setId(businCheckFlowStates.get(0).getId());
//			businCheckFlowStateMapper.updateByPrimaryKey(bcfs);
//		}
//	}
	
//	private void updateReportFlow(String accessionNum, String status, String statusCode, String finding, String impression, String hp) {
//		Example ex=new Example(BusinCheckFlowState.class);
//		ex.createCriteria().andEqualTo("accessionNum", accessionNum).andNotEqualTo("number", -1);
//		int count = businCheckFlowStateMapper.selectCountByExample(ex);
//		BusinCheckFlowState bcfs=new BusinCheckFlowState();
//		bcfs.setAccessionNum(accessionNum);
//		bcfs.setNumber(count+1);
//		bcfs.setStatus(status);
//		bcfs.setStatusCode(statusCode);
//		bcfs.setOperationUser(NameUtils.getLoginCode());
//		bcfs.setOperationTime(new Date());
//		bcfs.setFinding(finding);
//		bcfs.setImpression(impression);
//		bcfs.setHp(hp);
//		businCheckFlowStateMapper.insertSelective(bcfs);
//	}
	

	@Override
	public List<RepImage> queryRepImageByRepUid(String repUid) {
		Example example = new Example(RepImage.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("repUid", repUid);
		example.setOrderByClause("number asc");
		List<RepImage> repImages = repImageMapper.selectByExample(example);
		return repImages;
	}

	@Override
	public void deleteRepImageByRepImageId(String repImageId) {
		RepImage repImage = repImageMapper.selectByPrimaryKey(repImageId);
		repImageMapper.deleteByPrimaryKey(repImageId);
	}

	@Override
	public int queryViewWorklistIsRemind() {
		List<String> l=new ArrayList<String>();
		l.add(CheckStatusEnum.COMPLETE_PRINT.getStatusCode());
		l.add(CheckStatusEnum.COMPLETE_REFUSE.getStatusCode());
		l.add(CheckStatusEnum.COMPLETE_REVIEW.getStatusCode());
		l.add(CheckStatusEnum.COMPLETE_ABANDONED.getStatusCode());
		Example ex=new Example(ViewWorklist.class);
		ex.createCriteria().andNotIn("checkStatusCode", l);
		int num = viewWorklistMapper.selectCountByExample(ex);
		return num>0?1:0;
	}
	
}
