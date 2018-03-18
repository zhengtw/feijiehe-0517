package com.jfhealthcare.modules.apply.service.impl;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.common.entity.MyPageInfo;
import com.jfhealthcare.common.enums.ApplyStatusEnum;
import com.jfhealthcare.common.enums.CheckStatusEnum;
import com.jfhealthcare.common.enums.PrintStatusEnum;
import com.jfhealthcare.common.enums.RedisEnum;
import com.jfhealthcare.common.enums.ReportAiEnum;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.common.utils.DateUtils;
import com.jfhealthcare.common.utils.DistributedLockUtil;
import com.jfhealthcare.common.utils.HttpClientUtils;
import com.jfhealthcare.common.utils.ListUtils;
import com.jfhealthcare.common.utils.NameUtils;
import com.jfhealthcare.common.utils.RedisUtils;
import com.jfhealthcare.common.utils.TransferUtils;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.modules.apply.entity.ApplyImage;
import com.jfhealthcare.modules.apply.entity.ApplyWorklist;
import com.jfhealthcare.modules.apply.mapper.ApplyImageMapper;
import com.jfhealthcare.modules.apply.mapper.ApplyWorklistMapper;
import com.jfhealthcare.modules.apply.request.ApplyWorklistRequest;
import com.jfhealthcare.modules.apply.request.PrintWorklistRequest;
import com.jfhealthcare.modules.apply.response.ApplyWorklistResponse;
import com.jfhealthcare.modules.apply.response.PrintWorklistResponse;
import com.jfhealthcare.modules.apply.service.ApplyWorklistService;
import com.jfhealthcare.modules.basics.AiData;
import com.jfhealthcare.modules.business.entity.BusinChecklistIndex;
import com.jfhealthcare.modules.business.entity.BusinPatient;
import com.jfhealthcare.modules.business.entity.RepImage;
import com.jfhealthcare.modules.business.entity.RepRecord;
import com.jfhealthcare.modules.business.mapper.BusinCheckDmcRecordMapper;
import com.jfhealthcare.modules.business.mapper.BusinChecklistIndexMapper;
import com.jfhealthcare.modules.business.mapper.BusinPatientMapper;
import com.jfhealthcare.modules.business.mapper.RepImageMapper;
import com.jfhealthcare.modules.business.mapper.RepRecordMapper;
import com.jfhealthcare.modules.business.mapper.ViewWorklistMapper;
import com.jfhealthcare.modules.business.request.ViewWorklistRequest;
import com.jfhealthcare.modules.business.response.ViewWorklistResponse;
import com.jfhealthcare.modules.system.entity.RepGroup;
import com.jfhealthcare.modules.system.entity.SysDictDtl;
import com.jfhealthcare.modules.system.entity.SysOrganization;
import com.jfhealthcare.modules.system.mapper.RepGroupMapper;
import com.jfhealthcare.modules.system.service.DistributedLock;
import com.jfhealthcare.modules.system.service.SysDictDtlService;
import com.jfhealthcare.modules.system.service.SysOrganizationService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@Slf4j
@Service
public class ApplyWorklistServiceImpl implements ApplyWorklistService {
	
	@Value("${dcm.image.url}")
	private String startUrl;
	@Value("${ai.host}")
	private String aiHost;
	@Value("${ai.userName}")
	private String aiUserName;
	
	@Autowired
	private ApplyWorklistMapper applyWorklistMapper;
	@Autowired
	private ViewWorklistMapper viewWorklistMapper;
	@Autowired
	private BusinChecklistIndexMapper businChecklistIndexMapper;
	@Autowired
	private SysOrganizationService sysOrganizationServiceImpl;
	@Autowired
	private RepGroupMapper repGroupMapper;
	@Autowired
	private BusinPatientMapper businPatientMapper; 
	@Autowired
	private RepRecordMapper repRecordMapper;
	@Autowired
	private RepImageMapper repImageMapper;
	@Autowired
	private ApplyImageMapper applyImageMapper;
	@Autowired
	private SysDictDtlService sysDictDtlService;
	@Autowired
	private RedisUtils redisUtils;
	
	@Autowired
    private StringRedisTemplate redisTemplate;
	
	@Override
	public PageInfo<ApplyWorklistResponse> queryApplyWorkList(ApplyWorklistRequest awlr) {
		if(awlr.getCheckDate()!=null){
			if(awlr.getStudyStartTime()==null || awlr.getStudyEndTime()==null){
				List<Date> checkTime = DateUtils.getCheckTime(awlr.getCheckDate());
				if(!ObjectUtils.isEmpty(checkTime)){
					awlr.setStudyStartTime(checkTime.get(0));
					awlr.setStudyEndTime(checkTime.get(1));
				}
			}
		}
		PageHelper.startPage(awlr.getPageNum(), awlr.getPageSize());
		List<ApplyWorklistResponse> applyWorklistResponses=applyWorklistMapper.queryApplyWorkList(awlr);
		PageInfo<ApplyWorklistResponse> pageInfo = new PageInfo<ApplyWorklistResponse>(applyWorklistResponses);
		return pageInfo;
	}
	
	
	@Override
	public PageInfo<PrintWorklistResponse> queryPrintWorklist(PrintWorklistRequest vwlr) {
		if(vwlr.getCheckDate()!=null){
			if(vwlr.getApplyStartTime()==null || vwlr.getApplyEndTime()==null){
				List<Date> checkTime = DateUtils.getCheckTime(vwlr.getCheckDate());
				if(!ObjectUtils.isEmpty(checkTime)){
					vwlr.setApplyStartTime(checkTime.get(0));
					vwlr.setApplyEndTime(checkTime.get(1));
				}
			}
		}
		MyPageInfo<PrintWorklistResponse> pageInfo = new MyPageInfo<PrintWorklistResponse>(null);
		/**
		 * 打开状态  1  只查列表  ：返回列表数据
		 * 所有状态  0  只查数量： 返回状态数量
		 * 所有状态  2  数量  列表一起返回
		 */
		if("1".equals(vwlr.getIsOpen())) {
			pageInfo=getPrintList(vwlr);
		}else if("0".equals(vwlr.getIsOpen())){
			getStatusNum(vwlr,pageInfo);
		}else if("2".equals(vwlr.getIsOpen())) {
			pageInfo=getPrintList(vwlr);
			getStatusNum(vwlr,pageInfo);
		}
		return pageInfo;
	}


	private void getStatusNum(PrintWorklistRequest vwlr, MyPageInfo<PrintWorklistResponse> pageInfo) {
		List<Map<String,Object>> statusnum=viewWorklistMapper.queryPrintCountWorklist(vwlr);
		pageInfo.setOtherValue(statusnum);
	}

	private MyPageInfo<PrintWorklistResponse> getPrintList(PrintWorklistRequest vwlr) {
		List<String> printStatusCodes = vwlr.getPrintStatusCodes();
		if(!CollectionUtils.isEmpty(printStatusCodes)){
			List<String> status=new ArrayList<String>();
			for (String sta : printStatusCodes) {
				if(PrintStatusEnum.PENDING_DOING.getStatusCode().equals(sta)){
					status.add(CheckStatusEnum.PENDING_REPORT.getStatusCode());
					status.add(CheckStatusEnum.PENDING_ONE_REVIEW.getStatusCode());
					status.add(CheckStatusEnum.PENDING_TWO_REVIEW.getStatusCode());
					status.add(CheckStatusEnum.PENDING_THREE_REVIEW.getStatusCode());
					status.add(CheckStatusEnum.REPORTING.getStatusCode());
					status.add(CheckStatusEnum.REVIEWING.getStatusCode());
					status.add(CheckStatusEnum.ZANCUNING.getStatusCode());
					status.add(CheckStatusEnum.PENGING_HUIZHENG_REPORT.getStatusCode());
					status.add(CheckStatusEnum.PENGING_HUIZHENG_REVIEW.getStatusCode());
				}else if(PrintStatusEnum.COMPLETE_DOING.getStatusCode().equals(sta)){
					status.add(CheckStatusEnum.COMPLETE_REVIEW.getStatusCode());
				}else if(PrintStatusEnum.COMPLETE_PRINT.getStatusCode().equals(sta)){
					status.add(CheckStatusEnum.COMPLETE_PRINT.getStatusCode());
				}else if(PrintStatusEnum.COMPLETE_REFUSE.getStatusCode().equals(sta)){
					status.add(CheckStatusEnum.COMPLETE_REFUSE.getStatusCode());
					status.add(CheckStatusEnum.COMPLETE_ABANDONED.getStatusCode());
				}
			}
			vwlr.setPrintStatusCodes(status);
		}
		PageHelper.startPage(vwlr.getPageNum(), vwlr.getPageSize());
		List<PrintWorklistResponse> vwls=viewWorklistMapper.queryPrintWorklist(vwlr);
		MyPageInfo<PrintWorklistResponse> pageInfo = new MyPageInfo<PrintWorklistResponse>(vwls);
		return pageInfo;
	}


	@Override
	public ApplyWorklistResponse queryApplyWorkListById(String checkNum) {
		ApplyWorklist selectByPrimaryKey = applyWorklistMapper.selectByPrimaryKey(checkNum);
		Assert.isNull(selectByPrimaryKey, "查询申请时未查到有效申请数据！账号："+NameUtils.getLoginCode());
		ApplyWorklistResponse applyWorklistResponse=new ApplyWorklistResponse();
		TransferUtils.copyPropertiesIgnoreNull(selectByPrimaryKey, applyWorklistResponse);
		List<Map<String, Object>> l=applyWorklistMapper.queryInstanceByStudyUid(selectByPrimaryKey.getStudyUid());
		Example ex=new Example(BusinChecklistIndex.class);
		ex.createCriteria().andEqualTo("checkNum", selectByPrimaryKey.getCheckNum());
		ex.setOrderByClause("CRT_TIME desc");
		List<BusinChecklistIndex> businChecklistIndexs = businChecklistIndexMapper.selectByExample(ex);
		if(CollectionUtils.isNotEmpty(businChecklistIndexs)){
			BusinChecklistIndex bcli = businChecklistIndexs.get(0);
			applyWorklistResponse.setDescribeBq(bcli.getDescribeBq());
		}
		applyWorklistResponse.setImageUrls(l);
		applyWorklistResponse.setUrlStart(startUrl);
		return applyWorklistResponse;
	}

	@Override
	public void updateApplyWorkListStatus(ApplyWorklistRequest applyWorklistRequest, LoginUserEntity loginUserEntity) {
		String logincode = loginUserEntity.getSysOperator().getLogincode();
		ApplyWorklist applyWorklist = applyWorklistMapper.selectByPrimaryKey(applyWorklistRequest.getCheckNum());
		Assert.isNull(applyWorklist, "提交申请时未查到有效申请数据！账号："+logincode);
		String logseries="报告申请初始化-病人姓名："+applyWorklistRequest.getPtnName();
		//1.更新申请数据，生成检查单号
		log.info(logseries+":=========状态更新===========");
		applyWorklist.setPtnName(applyWorklistRequest.getPtnName());
		applyWorklist.setSex(applyWorklistRequest.getSex());
		applyWorklist.setSexCode(applyWorklistRequest.getSexCode());
		applyWorklist.setPtnAge(applyWorklistRequest.getPtnAge());
		applyWorklist.setPtnAgeUnit(applyWorklistRequest.getPtnAgeUnit());
		applyWorklist.setPtnAgeUnitCode(applyWorklistRequest.getPtnAgeUnitCode());
		applyWorklist.setBodyPart(applyWorklistRequest.getBodyPart());
		applyWorklist.setSummary(applyWorklistRequest.getSummary());
		applyWorklist.setExam(applyWorklistRequest.getExam());
		applyWorklist.setApplyDoc(logincode);
		applyWorklist.setApplyStatus(ApplyStatusEnum.COMPLETE_APPLY.getStatus());
		applyWorklist.setApplyStatusCode(ApplyStatusEnum.COMPLETE_APPLY.getStatusCode());
		applyWorklistMapper.updateByPrimaryKey(applyWorklist);
	}

	@Override
	@Transactional
	public void updateApplyWorkListToView(ApplyWorklistRequest applyWorklistRequest, LoginUserEntity loginUserEntity) {
		String logincode = loginUserEntity.getSysOperator().getLogincode();
		SysOrganization org = sysOrganizationServiceImpl.querySingleSysOrganization(loginUserEntity.getSysOperatorDtl().getOrgId());
		String logseries="报告申请初始化-病人姓名："+applyWorklistRequest.getPtnName()+",-申请机构："+org.getName();
		log.info(logseries+":=========初始化开始===========");
		ApplyWorklist applyWorklist = applyWorklistMapper.selectByPrimaryKey(applyWorklistRequest.getCheckNum());
		Assert.isNull(applyWorklist, "提交申请时未查到有效申请数据！账号："+logincode);
		
		//2.初始化viewworklist相关数据
		List<String> instanceUids = applyWorklistRequest.getInstanceUids();
		Assert.isNull(instanceUids, "未选择影像，无法初始化！");
		
		//数据初始化-----系列
		//检查信息初始化
		String checkNum = applyWorklistRequest.getCheckNum();
		Assert.isBlank(checkNum, "检查单号不能为空！");
		
		BusinChecklistIndex bizindex=new BusinChecklistIndex();
		bizindex.setStatus(CheckStatusEnum.PENDING_REPORT.getStatus());
		bizindex.setStatusCode(CheckStatusEnum.PENDING_REPORT.getStatusCode());
		bizindex.setCheckNum(checkNum);
		bizindex.setDescribeBq(applyWorklistRequest.getDescribeBq());//病史描述
		bizindex.setParts(applyWorklistRequest.getBodyPart());//检查部位
		bizindex.setSummary(applyWorklistRequest.getSummary());//检查方法
		bizindex.setExam(applyWorklistRequest.getExam());
//		bizindex.setSummaryCode(applyWorklistRequest.getSummaryCode());//检查方法code
		bizindex.setApplyDoc(loginUserEntity.getSysOperator().getName());//申请医生
		bizindex.setApplyDocCode(logincode);//申请医生账号
		bizindex.setApplyHosp(org.getName());
		bizindex.setApplyHospCode(loginUserEntity.getSysOperatorDtl().getOrgId());
		bizindex.setJzFlag(ObjectUtils.isEmpty(applyWorklistRequest.getCheckJzFlag())?false:applyWorklistRequest.getCheckJzFlag());
		bizindex.setImgNum(instanceUids.size());
		bizindex.setType("DR");
		bizindex.setTypeCode("3406");
		bizindex.setIsdelete(false);
		log.info(logseries+":=========检查信息初始化完成===========");
		//诊断报告初始化
		RepRecord repRecord=new RepRecord();
		repRecord.setRepUid(getAccessionNum());
		repRecord.setPatId(checkNum);
//		repRecord.setAccessionNum(accessNum);
		repRecord.setPatAge(applyWorklistRequest.getPtnAge());
		if(StringUtils.isNotEmpty(applyWorklistRequest.getPtnAgeUnitCode())){
			SysDictDtl dictDtlForAge = sysDictDtlService.queryDictDtlById(applyWorklistRequest.getPtnAgeUnitCode());
			repRecord.setAgeUnit(dictDtlForAge.getOthervalue());
			repRecord.setAgeUnitCode(applyWorklistRequest.getPtnAgeUnitCode());
		}
		
		log.info(logseries+":=========诊断报告初始化完成===========");
		//患者信息初始化
		BusinPatient bp=new BusinPatient();
		bp.setPatId(checkNum);
		bp.setName(applyWorklistRequest.getPtnName());
		bp.setSex(applyWorklistRequest.getSex());
		bp.setSexCode(applyWorklistRequest.getSexCode());
		bp.setIsdelete(false);
		BusinPatient buspat = businPatientMapper.selectByPrimaryKey(checkNum);
		boolean isBPNeed=false;
		if(!ObjectUtils.isEmpty(buspat)){
			isBPNeed=true;
		}
		log.info(logseries+":=========患者初始化完成===========");
		//报告贴图初始化
		List<RepImage> repImages=new ArrayList<RepImage>();
		List<Map<String, Object>> l=applyWorklistMapper.queryInstanceByInstanceUid(instanceUids);
		
		if(CollectionUtils.isNotEmpty(l)){
			try {
				String aiParameter=null;
				int i=0;
				for (Map<String, Object> map : l) {
					RepImage repImage=new RepImage();
					repImage.setRepUid(repRecord.getRepUid());
					repImage.setImgPage((String)map.get("imageUrl"));
					repImage.setNumber(i);
					repImages.add(repImage);
					String jpgurl = "jpgurl="+URLEncoder.encode(startUrl+map.get("imageUrl"),"UTF-8");
					aiParameter=StringUtils.isEmpty(aiParameter)?jpgurl:aiParameter+"&"+jpgurl;
					i++;
				}
				log.info(logseries+":=========报告贴图初始化完成===========");
				//AI 初始化
				long startTime = new Date().getTime();
				HttpClientUtils instance = HttpClientUtils.getInstance();
				
				String httpGet = instance.httpGetByWaitTime(StringUtils.trim(aiHost)+aiParameter, 10000, 20000);
				log.info(logseries+"=====Ai调用地址:{}",StringUtils.trim(aiHost)+aiParameter);
				if(StringUtils.isNotBlank(httpGet)){
					log.info(logseries+"=====Ai调用返回:{}",httpGet);
					AiData aiData = JSON.parseObject(httpGet, AiData.class);
					//AI转报告
					if(ReportAiEnum.TOREPORTE.getAiStatusCode().equals(aiData.getReportStatus())){
						bizindex.setStatusAi(ReportAiEnum.TOREPORTE.getAiStatus());
						bizindex.setStatusAiCode(ReportAiEnum.TOREPORTE.getAiStatusCode());
					//转审核
					}else if(ReportAiEnum.TOREVIEWE.getAiStatusCode().equals(aiData.getReportStatus())){
						bizindex.setStatusAi(ReportAiEnum.TOREVIEWE.getAiStatus());
						bizindex.setStatusAiCode(ReportAiEnum.TOREVIEWE.getAiStatusCode());
						bizindex.setStatus(CheckStatusEnum.PENDING_ONE_REVIEW.getStatus());
						bizindex.setStatusCode(CheckStatusEnum.PENDING_ONE_REVIEW.getStatusCode());
						bizindex.setReportDr(aiUserName);
						bizindex.setReportTime(new Date());
					//转分片	
					}else if(ReportAiEnum.TOFENPIAN.getAiStatusCode().equals(aiData.getReportStatus())){
						bizindex.setStatusAi(ReportAiEnum.TOFENPIAN.getAiStatus());
						bizindex.setStatusAiCode(ReportAiEnum.TOFENPIAN.getAiStatusCode());
					//若AI返回结果不正确，ai状态未为处理
					}else{
						bizindex.setStatusAi(ReportAiEnum.UNTREATED.getAiStatus());
						bizindex.setStatusAiCode(ReportAiEnum.UNTREATED.getAiStatusCode());
						bizindex.setStatus(CheckStatusEnum.PENDING_REPORT.getStatus());
						bizindex.setStatusCode(CheckStatusEnum.PENDING_REPORT.getStatusCode());
					}
					//默认未参与
					if(StringUtils.isNotBlank(aiData.getImging())){
						repRecord.setFinding1(aiData.getImging());
					}
					if(StringUtils.isNotBlank(aiData.getDiagnosisOpinion())){
						repRecord.setImpression1(aiData.getDiagnosisOpinion());
					}
					long endTime = new Date().getTime();
					log.info(logseries+":=========AI申请用时：{}===========",endTime-startTime);
				}else{
					throw new RmisException("AI请求连接超时或处理超时");
				}
			} catch (Exception e) {
				bizindex.setStatusAi(ReportAiEnum.UNTREATED.getAiStatus());
				bizindex.setStatusAiCode(ReportAiEnum.UNTREATED.getAiStatusCode());
				bizindex.setStatus(CheckStatusEnum.PENDING_REPORT.getStatus());
				bizindex.setStatusCode(CheckStatusEnum.PENDING_REPORT.getStatusCode());
				if("AI请求连接超时或处理超时".equals(e.getMessage())) {
					log.error("AI初始化失败！", "AI请求连接超时或处理超时");
				}else {
					log.error("AI初始化失败！", e);
				}
				
			}
		}
		//报告轮训分组
		try {
			log.info(logseries+":=============分组开始================");
			List<RepGroup> repGroups = redisUtils.getList(RedisEnum.REPGROUP.getValue()+":nancang", RepGroup.class);
			if(CollectionUtils.isEmpty(repGroups)) {
				log.info("分组缓存为空，重新加载：repgroups,caches！");
				Example example = new Example(RepGroup.class);
				example.createCriteria().andEqualTo("status", "0");
				example.setOrderByClause("CAST(NINDEX AS DECIMAL) asc");
				repGroups = repGroupMapper.selectByExample(example);
				if(CollectionUtils.isNotEmpty(repGroups)){
					redisUtils.set(RedisEnum.REPGROUP.getValue()+":nancang", repGroups, RedisUtils.NOT_EXPIRE);
				}
			}
			//分组不为空   进入分组分片
			//如果分组只有一个不需要参与轮训分片
			int repGroupsSize = repGroups.size();
			if(CollectionUtils.isEmpty(repGroups) || repGroupsSize==1) {
				if(repGroupsSize==1) {
					log.info(logseries+":====================分组唯一，单一分组====================");
					bizindex.setRepGroupId(repGroups.get(0).getId());	
				}else {
					log.info(logseries+":====================分组为空，不参与分组====================");
				}
				doFinishForWorkList(isBPNeed,bizindex,bp,repRecord,repImages,applyWorklist);
			}else {
			//进入分布式锁  分配分组	
				log.info(logseries+":====================进入分布式锁分组====================");
				//分布式锁实现分组功能
				DistributedLock lock = DistributedLockUtil.getDistributedLock(redisTemplate,RedisEnum.DISTRIBUTELOCK_REPGROUP.getValue());
				try {
				    if (lock.acquire()) {
				    	Integer repGroupNum = redisUtils.get(RedisEnum.REPGROUP_NUM.getValue(),Integer.class);
						if(ObjectUtils.isEmpty(repGroupNum)) {
							repGroupNum=0;
							redisUtils.set(RedisEnum.REPGROUP_NUM.getValue(), 0, RedisUtils.NOT_EXPIRE);
						}
				    	//获取锁成功业务代码
				        if(repGroupNum==repGroupsSize) {
				        	bizindex.setRepGroupId(repGroups.get(0).getId());
				        	redisUtils.set(RedisEnum.REPGROUP_NUM.getValue(), 1, RedisUtils.NOT_EXPIRE);
				        }else {
				        	bizindex.setRepGroupId(repGroups.get(repGroupNum).getId());
				        	redisUtils.set(RedisEnum.REPGROUP_NUM.getValue(), repGroupNum+1, RedisUtils.NOT_EXPIRE);
				        }
				    } else { 
				        //获取锁失败业务代码
				    	bizindex.setRepGroupId(repGroups.get(0).getId());
				    }
				    
				    doFinishForWorkList(isBPNeed,bizindex,bp,repRecord,repImages,applyWorklist);
				 }finally {
				    if (lock != null) {
				        lock.release();
				    }
				}	
			}
		} catch (Exception e) {
			log.error("轮训分组异常!",e);
			//若异常    默认不再参与分组
			doFinishForWorkList(isBPNeed,bizindex,bp,repRecord,repImages,applyWorklist);
		}
		log.info(logseries+":=============分组结束================");
		log.info(logseries+":=========初始化结束===========");
	}
	
	private void doFinishForWorkListByDistributedLock(boolean isBPNeed, BusinChecklistIndex bizindex, BusinPatient bp,
			RepRecord repRecord, List<RepImage> repImages,ApplyWorklist applyWorklist) {
		DistributedLock lock = DistributedLockUtil.getDistributedLock(redisTemplate,RedisEnum.DISTRIBUTELOCK_APPLY.getValue());
		if (lock.acquire()) {
	    	//获取锁成功业务代码
			doFinishForWorkList(isBPNeed, bizindex, bp, repRecord, repImages, applyWorklist);
	    } else { 
	        //获取锁失败业务代码
	    	log.info("分布式锁获取失败(申请),不使用锁直接操作");
	    	doFinishForWorkList(isBPNeed, bizindex, bp, repRecord, repImages, applyWorklist);
	    }
	}
	
	private void doFinishForWorkList(boolean isBPNeed, BusinChecklistIndex bizindex, BusinPatient bp,
			RepRecord repRecord, List<RepImage> repImages,ApplyWorklist applyWorklist) {
	    	//获取锁成功业务代码
			String checkNum=applyWorklist.getCheckNum();
			Example ex=new Example(BusinChecklistIndex.class);
			ex.createCriteria().andEqualTo("checkNum", checkNum);
			int countIndex = businChecklistIndexMapper.selectCountByExample(ex);
			String accessNum=checkNum+(countIndex+1);
			
			bizindex.setAccessionNum(accessNum);
			bizindex.setCrtTime(new Date());
			bizindex.setApplyTime(new Date());
			bp.setCrtTime(new Date());
			
			businChecklistIndexMapper.insertSelective(bizindex);
			if(isBPNeed){//不为空 更新
				businPatientMapper.updateByPrimaryKeySelective(bp);
			}else{
				businPatientMapper.insert(bp);
			}
			repRecord.setAccessionNum(accessNum);
			repRecordMapper.insertSelective(repRecord);
			for (RepImage repImage : repImages) {
				repImageMapper.insertSelective(repImage);
			}
	}

	private static String getAccessionNum(){
		String accessionNum = DateUtils.dateToString(new Date(), DateUtils.patternF)+"-"+RandomStringUtils.randomNumeric(3);
		return accessionNum;
	}

	@Override
	public List<Map<String,Object>> queryPrintWorkListStatus(PrintWorklistRequest printWorklistRequest) {
		PrintWorklistRequest pwlr=new PrintWorklistRequest();
		if(StringUtils.isNotBlank(printWorklistRequest.getApplyOrg())){
			pwlr.setApplyOrg(printWorklistRequest.getApplyOrg());
		}
		List<Map<String,Object>> statusnum=viewWorklistMapper.queryPrintCountWorklist(pwlr);
		return statusnum;
	}

	@Override
	public String queryApplyWorkListToRemind(String checkNum) {
		String remind=viewWorklistMapper.queryApplyWorkListToRemind(checkNum);
		return StringUtils.isEmpty(remind) || "0".equals(remind)?"0":"1";
	}

	@Override
	public String queryApplyWorkToCheckImageByUids(List<String> instanceUids) {
		Example example=new Example(ApplyImage.class);
		example.createCriteria().andIn("instanceUid", instanceUids).andEqualTo("imgaeDeleted", true);
		List<ApplyImage> applyImages = applyImageMapper.selectByExample(example);
		return CollectionUtils.isEmpty(applyImages)?"0":"1";
	}


	
}
