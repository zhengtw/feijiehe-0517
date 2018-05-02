package com.jfhealthcare.modules.apply.service.impl;


import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.jfhealthcare.common.enums.DCMEnum;
import com.jfhealthcare.common.enums.LabelStatusEnum;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.common.utils.DateUtils;
import com.jfhealthcare.common.utils.HttpClientUtils;
import com.jfhealthcare.common.validator.Assert;
import com.jfhealthcare.modules.apply.entity.ApplyImage;
import com.jfhealthcare.modules.apply.entity.ApplyPatient;
import com.jfhealthcare.modules.apply.entity.ApplySeries;
import com.jfhealthcare.modules.apply.entity.ApplyStudy;
import com.jfhealthcare.modules.apply.entity.ApplyWorklist;
import com.jfhealthcare.modules.apply.mapper.ApplyImageMapper;
import com.jfhealthcare.modules.apply.mapper.ApplyPatientMapper;
import com.jfhealthcare.modules.apply.mapper.ApplySeriesMapper;
import com.jfhealthcare.modules.apply.mapper.ApplyStudyMapper;
import com.jfhealthcare.modules.apply.mapper.ApplyWorklistMapper;
import com.jfhealthcare.modules.apply.service.ApplyMqInitService;
import com.jfhealthcare.modules.label.entity.LabelInfo;
import com.jfhealthcare.modules.label.entity.LabelInfolist;
import com.jfhealthcare.modules.label.mapper.LabelInfoMapper;
import com.jfhealthcare.modules.label.mapper.LabelInfolistMapper;
import com.jfhealthcare.modules.system.entity.SysDictDtl;
import com.jfhealthcare.modules.system.entity.SysOperatorDtl;
import com.jfhealthcare.modules.system.entity.SysOrganization;
import com.jfhealthcare.modules.system.service.SysDictDtlService;
import com.jfhealthcare.modules.system.service.SysOperatorDtlService;
import com.jfhealthcare.modules.system.service.SysOrganizationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApplyMqInitServiceImpl implements ApplyMqInitService {
	
	@Value("${dcm.info.url}")
	private String dcmInfoUrl;
	
	@Autowired
	private ApplyStudyMapper applyStudyMapper;
	@Autowired
	private ApplySeriesMapper applySeriesMapper;
	@Autowired
	private ApplyImageMapper applyImageMapper;
	@Autowired
	private ApplyPatientMapper applyPatientMapper;
	@Autowired
	private SysDictDtlService sysDictDtlService;
	@Autowired
	private ApplyWorklistMapper applyWorklistMapper;
	@Autowired
	private SysOperatorDtlService sysOperatorDtlService;
	@Autowired
	private SysOrganizationService sysOrganizationService;
	@Autowired
	private LabelInfolistMapper labelInfolistMapper;
	@Autowired
	private LabelInfoMapper labelInfoMapper;
	
	@Transactional
	@Override
	public void initApply(String sopUID, String seriesUID, String studyUID, String userId) {
		    //获取机构编号对应的机构信息
			SysOrganization sysorg = new SysOrganization();
			sysorg.setCode(userId);
			sysorg.setIsdelete(Boolean.FALSE);
			List<SysOrganization> queryBySysOrg = sysOrganizationService.queryBySysOrg(sysorg);
			Assert.isListOnlyOne(queryBySysOrg, "机构编号不存在或存在多个！机构号："+userId);
			SysOrganization sysOrganization = queryBySysOrg.get(0);
			
			String logseries="(成功)mq初始化数据，传片机构："+sysOrganization.getName()+",机构号："+userId;
			log.info(logseries+":======dcm:uid信息：studyuid="+studyUID+",seriesUID="+seriesUID+",sopUID="+sopUID);
			String dcmUrl=dcmInfoUrl+"/studies/"+studyUID+"/series/"+seriesUID+"/instances/"+sopUID+"/metadata";
			log.info(logseries+":========调用dcm4che地址： dcmurl:{}",dcmUrl);
			HttpClientUtils instance = HttpClientUtils.getInstance();
			String dcminfo = instance.httpGetByWaitTime(dcmUrl, 20*HttpClientUtils.One_Second, HttpClientUtils.One_Minute);
			Assert.isBlank(dcminfo, logseries+"：===mq-initapply:地址："+dcmUrl+"--调用返回为空");
			List<Map> parseArray = JSON.parseArray(dcminfo,Map.class);
			Assert.isNull(parseArray, logseries+"：===mq-initapply:地址："+dcmUrl+"--调用返回转List<Map>为空");
			Map<String,Object> dcmInfo = parseArray.get(0);
			//检查信息
			String charset=getDcmInfoValue(dcmInfo.get(DCMEnum.CharSet.getDcmKey()),"gbk");
			String studyID=getDcmInfoValue(dcmInfo.get(DCMEnum.StudyID.getDcmKey()),"");
			String accessionNumber=getDcmInfoValue(dcmInfo.get(DCMEnum.AccessionNumber.getDcmKey()),"");
			String studyDate=getDcmInfoValue(dcmInfo.get(DCMEnum.StudyDate.getDcmKey()),"");
			String studyTime=getDcmInfoValue(dcmInfo.get(DCMEnum.StudyTime.getDcmKey()),"");
			String performingPhysicianName=getDcmInfoValue(dcmInfo.get(DCMEnum.PerformingPhysicianName.getDcmKey()),"");
			String studyDescription=getDcmInfoValue(dcmInfo.get(DCMEnum.StudyDescription.getDcmKey()),"");
			//系列信息
			String seriesDate=getDcmInfoValue(dcmInfo.get(DCMEnum.SeriesDate.getDcmKey()),"");
			String seriesTime=getDcmInfoValue(dcmInfo.get(DCMEnum.SeriesTime.getDcmKey()),"");
			String seriesNumber=getDcmInfoValue(dcmInfo.get(DCMEnum.SeriesNumber.getDcmKey()),"");
			String bodyPart=getDcmInfoValue(dcmInfo.get(DCMEnum.BodyPartExamined.getDcmKey()),"");
			String modality=getDcmInfoValue(dcmInfo.get(DCMEnum.Modality.getDcmKey()),"");
			String stationName=getDcmInfoValue(dcmInfo.get(DCMEnum.StationName.getDcmKey()),"");
			//影像信息
			String instanceNumber=getDcmInfoValue(dcmInfo.get(DCMEnum.InstanceNumber.getDcmKey()),"");
//			String storageTransferUID=Objects.toString(dcmInfo.get(DCMEnum.StorageTransferUID.getDcmKey()),"");
			String sopClassUID=getDcmInfoValue(dcmInfo.get(DCMEnum.SOPClassUID.getDcmKey()),"");
			String retrieveAETitle=getDcmInfoValue(dcmInfo.get(DCMEnum.RetrieveAETitle.getDcmKey()),"");
//			String instanceReceiveDateTime=getDcmInfoValue(dcmInfo.get(DCMEnum.InstanceReceiveDateTime.getDcmKey()),"");
//			String storageObjectSize=getDcmInfoValue(dcmInfo.get(DCMEnum.StorageObjectSize.getDcmKey()),"0");
//			String storageTransferSyntaxUID=getDcmInfoValue(dcmInfo.get(DCMEnum.StorageTransferSyntaxUID.getDcmKey()),"0");
			String rows=getDcmInfoValue(dcmInfo.get(DCMEnum.Rows.getDcmKey()),"0");
			String columns=getDcmInfoValue(dcmInfo.get(DCMEnum.Columns.getDcmKey()),"0");
			String bitsAllocated=getDcmInfoValue(dcmInfo.get(DCMEnum.BitsAllocated.getDcmKey()),"0");
			String numberofFrames=getDcmInfoValue(dcmInfo.get(DCMEnum.NumberofFrames.getDcmKey()),"0");
			String privateCreator=getDcmInfoValue(dcmInfo.get(DCMEnum.PrivateCreator.getDcmKey()),"");
			String pixelData=getDcmInfoValue(dcmInfo.get(DCMEnum.PixelData.getDcmKey()),"","BulkDataURI",null);
			String instanceCreationDate=getDcmInfoValue(dcmInfo.get(DCMEnum.InstanceCreationDate.getDcmKey()),"");
			String instanceCreationTime=getDcmInfoValue(dcmInfo.get(DCMEnum.InstanceCreationTime.getDcmKey()),"");
			//患者信息
//			String patientName=getDcmInfoValue(dcmInfo.get(DCMEnum.PatientName.getDcmKey()),"","Value","Alphabetic");
			String patientName=getDcmPatientName(dcmInfo.get(DCMEnum.PatientName.getDcmKey()),"","Value","Ideographic","Alphabetic");
			String patientID=getDcmInfoValue(dcmInfo.get(DCMEnum.PatientID.getDcmKey()),"");
			String patientBirthDate=getDcmInfoValue(dcmInfo.get(DCMEnum.PatientBirthDate.getDcmKey()),"");
			String patientSex=getDcmInfoValue(dcmInfo.get(DCMEnum.PatientSex.getDcmKey()),"O");
			patientSex=StringUtils.equalsAnyIgnoreCase(patientSex, "O","M","F")?patientSex:"O";//去除不规范的性别
			String patientAge=getDcmInfoValue(dcmInfo.get(DCMEnum.PatientAge.getDcmKey()),"");
			String patientSize=getDcmInfoValue(dcmInfo.get(DCMEnum.PatientSize.getDcmKey()),"");
			String patientWeight=getDcmInfoValue(dcmInfo.get(DCMEnum.PatientWeight.getDcmKey()),"");
			log.info(logseries+":========dcm信息收集完成===");
			//dcm原始数据初始化--系列
			//效验    过滤  ct  mr的
			if(StringUtils.isNotEmpty(modality)) {
				List<SysDictDtl> quertDictDtlByName = sysDictDtlService.quertDictDtlByCode("unmodality");
				for (SysDictDtl sysDictDtl : quertDictDtlByName) {
					if(modality.equalsIgnoreCase(sysDictDtl.getOthervalue())) {
						log.info(logseries+":========dcm：接受的类型为：{}，占时不接受===",modality);
						return;
					}
				}
			}
			
			//不接受 CT  MR
			//影像存在则退出
			ApplyImage aimg = applyImageMapper.selectByPrimaryKey(sopUID);
			if(!ObjectUtils.isEmpty(aimg)) {
				log.info(logseries+":========dcm：影像信息****已存在****===");
				return;
			}
			
			//1.判断是否有正常影像 如果没有正常影像 ，覆盖检查 系列等信息
			ApplyWorklist awo=new ApplyWorklist();
			awo.setStudyUid(studyUID);
			List<ApplyWorklist> applyWorklists = applyWorklistMapper.select(awo);
			judeListSize(applyWorklists,"ApplyWorklist数据库中有多个，studyUID{}",studyUID);
			//haveNormalImage 是否有正常的影像
			boolean haveNormalImage=true; 
			if(!ObjectUtils.isEmpty(applyWorklists)) {
				haveNormalImage = applyWorklists.get(0).getIsNormalImage();
			}
			
			//检查信息初始化
			ApplyStudy asty = applyStudyMapper.selectByPrimaryKey(studyUID);
			if(ObjectUtils.isEmpty(asty) || (!ObjectUtils.isEmpty(asty) && !haveNormalImage )) {
				ApplyStudy applyStudy=new ApplyStudy();
				applyStudy.setStudyUid(studyUID);
				applyStudy.setStudyId(studyID);
				applyStudy.setAccessionNumber(accessionNumber);
				applyStudy.setStudyDate(studyDate);
				applyStudy.setStudyTime(studyTime);
				applyStudy.setPhysicianName(performingPhysicianName);
				applyStudy.setCheckDescribe(studyDescription);
				applyStudy.setUserGroup(null);
				
				if(!ObjectUtils.isEmpty(asty) && !haveNormalImage ) {
					applyStudy.setId(asty.getId());
					applyStudy.setUpdTime(new Date());
					applyStudyMapper.updateByPrimaryKeySelective(applyStudy);
				}else {
					applyStudy.setCrtTime(new Date());
					applyStudyMapper.insertSelective(applyStudy);
				}
			}
			log.info(logseries+":========dcm：检查信息初始化完成===");
		
			//系列信息初始化
			ApplySeries asrs = applySeriesMapper.selectByPrimaryKey(seriesUID);
			if(ObjectUtils.isEmpty(asrs) || (!ObjectUtils.isEmpty(asrs) && !haveNormalImage )){
				ApplySeries applySeries=new ApplySeries();
				applySeries.setBodyPart(bodyPart);
				applySeries.setModality(modality);
				applySeries.setModelName(stationName);
				applySeries.setOperatorName(performingPhysicianName);
				applySeries.setSeriesDate(seriesDate);
				applySeries.setSeriesNumber(seriesNumber);
				applySeries.setSeriesTime(seriesTime);
				applySeries.setSeriesUid(seriesUID);
				applySeries.setStudyUid(studyUID);
				if(!ObjectUtils.isEmpty(asrs) && !haveNormalImage ){
					applySeries.setId(asrs.getId());
					applySeries.setUpdTime(new Date());
					applySeriesMapper.updateByPrimaryKey(applySeries);
				}else{
					applySeries.setCrtTime(new Date());
					applySeriesMapper.insertSelective(applySeries);
				}
			}
			log.info(logseries+":========dcm：系列信息初始化完成===");
			
			//影像初始化
			if(ObjectUtils.isEmpty(aimg)){
				ApplyImage applyImage=new ApplyImage();
				applyImage.setInstanceUid(sopUID);
				applyImage.setSeriesUid(seriesUID);
				applyImage.setImageNumber(instanceNumber);
	//			applyImage.setTransferUid(storageTransferUID);
				applyImage.setClassUid(sopClassUID);
				applyImage.setBackupLabel(null);//影像备份标签
				applyImage.setSourceAe(retrieveAETitle);
				applyImage.setRcvdDate(instanceCreationDate);//recv的字段现在采用instance的创建提起和时间
				applyImage.setRcvdTime(instanceCreationTime);//recv的字段现在采用instance的创建提起和时间
	//			applyImage.setFileSize(StringUtils.isNumeric(storageObjectSize)?Integer.parseInt(storageObjectSize):0);
	//			applyImage.setTransactionUid(storageTransferSyntaxUID);
				applyImage.setImgaeDeleted(false);//判断该影像是否有效
				applyImage.setKeyImage(false);//是否关键影像
				applyImage.setNRows(StringUtils.isNumeric(rows)?Integer.parseInt(rows):0);
				applyImage.setNColumns(StringUtils.isNumeric(columns)?Integer.parseInt(columns):0);
				applyImage.setBitsAllocated(StringUtils.isNumeric(bitsAllocated)?Integer.parseInt(bitsAllocated):0);
				applyImage.setNFrames(StringUtils.isNumeric(numberofFrames)?Integer.parseInt(numberofFrames):0);
				applyImage.setPrLabel(null);
				applyImage.setPrDesc(null);
				applyImage.setPrTime(null);
				applyImage.setPrDate(null);
				applyImage.setPrCreator(privateCreator);
				applyImage.setImageFile(pixelData);
				applyImage.setCrtTime(new Date());
				applyImageMapper.insertSelective(applyImage);
			}
			log.info(logseries+":========dcm：影像信息初始化完成===");
			
			//患者信息初始化
			ApplyPatient ptn=new ApplyPatient();
			ptn.setStudyUid(studyUID);
			List<ApplyPatient> applyPatients = applyPatientMapper.select(ptn);
			judeListSize(applyPatients,"ApplyPatient数据库中有多个，studyUID{}",studyUID);
			if(ObjectUtils.isEmpty(applyPatients)){
				ApplyPatient applyPatient=new ApplyPatient();
			    applyPatient.setBirthDate(patientBirthDate);
			    applyPatient.setPtnAge(patientAge);
			    applyPatient.setPtnId(getRandomNum());
			    applyPatient.setPtnName(getTransName(patientName));
			    applyPatient.setPtnSize(patientSize);
			    applyPatient.setPtnWeight(patientWeight);
			    applyPatient.setSex(patientSex);
			    applyPatient.setStudyUid(studyUID);
			    applyPatient.setCrtTime(new Date());
			    applyPatientMapper.insertSelective(applyPatient);
			}
			log.info(logseries+":========dcm：患者信息初始化完成===");
			
			//申请表初始化
			if(ObjectUtils.isEmpty(applyWorklists) || (!ObjectUtils.isEmpty(asrs) && !haveNormalImage )){
				ApplyWorklist applyWorklist=new ApplyWorklist();
				String checkNum=getRandomNum();
				applyWorklist.setCheckNum(checkNum);//检查单号由自己生成
				applyWorklist.setPtnId("pid-"+checkNum);
				applyWorklist.setPtnName(getTransName(patientName));
				applyWorklist.setBirthDate(StringUtils.isEmpty(patientBirthDate)?null:DateUtils.stringToDate(patientBirthDate, DateUtils.patternB));
				SysDictDtl sysDictDtlForSex = sysDictDtlService.quertDictDtlByDictIdAndName("1", StringUtils.isEmpty(patientSex)?"O":patientSex);
				applyWorklist.setSexCode(sysDictDtlForSex.getId());
				applyWorklist.setSex(ObjectUtils.isEmpty(sysDictDtlForSex)?"其他":sysDictDtlForSex.getOthervalue());
				applyWorklist.setHight(patientSize);
				applyWorklist.setWeight(patientWeight);
				applyWorklist.setAnnouncements(null);//医疗注意事项
				applyWorklist.setAllergy(null);//显影剂过敏项目
				applyWorklist.setOccupation(null);//患者职业
				applyWorklist.setPgyStatus(null);//患者怀孕状态
				applyWorklist.setModality(modality);
				applyWorklist.setAeTitle(retrieveAETitle);
				applyWorklist.setStudyTime(StringUtils.isEmpty(studyDate+studyTime)?null:DateUtils.stringToDate(studyDate+studyTime, DateUtils.patternF));
				log.info(logseries+":========dcm：检查date：{}，检查time：{},datetostring:{}",studyDate,studyTime,DateUtils.dateToString(applyWorklist.getStudyTime(), DateUtils.patternD));
				applyWorklist.setPhysicianName(performingPhysicianName);
				applyWorklist.setExam(null);//检查项目
				applyWorklist.setBodyPart(bodyPart);
				applyWorklist.setSummary(null);//检查方法
				applyWorklist.setCosts(null);//金额
				//applyWorklist.setApplyDoc(userId);
				applyWorklist.setApplyOrg(ObjectUtils.isEmpty(sysOrganization.getId())?null:sysOrganization.getId());
				applyWorklist.setApplyStatus("待申请");
				applyWorklist.setApplyStatusCode("6000");
				applyWorklist.setStudyUid(studyUID);
				Map<String,String> ageMap=getPatientByAgeOrBirthDay(patientAge,patientBirthDate);
				applyWorklist.setPtnAge(ageMap.get("ptnAge"));
				applyWorklist.setPtnAgeUnit(ageMap.get("ptnAgeUnit"));
				applyWorklist.setPtnAgeUnitCode(ageMap.get("ptnAgeUnitCode"));
				applyWorklist.setIsNormalImage(true);//是否有正常的影像   true 有   false 没有
				if(!ObjectUtils.isEmpty(asrs) && !haveNormalImage ){
					//如果申请表初始化第一个为错误影像，则采用部分数据其他数据重新录入
					ApplyWorklist applyErrorWorklist=applyWorklist;
					applyWorklist.setUpdTime(new Date());
					applyWorklist.setCheckNum(applyErrorWorklist.getCheckNum());//检查单号由自己生成
					applyWorklist.setPtnId(applyErrorWorklist.getPtnId());
					applyWorklist.setId(applyErrorWorklist.getId());
					applyWorklistMapper.updateByPrimaryKey(applyWorklist);
				}else{
					applyWorklist.setCrtTime(new Date());
					applyWorklistMapper.insertSelective(applyWorklist);	
				}
			}else {
				//申请不为空  如果存在多部位需要更新
				ApplyWorklist applyWorklist = applyWorklists.get(0);
				String applyBodyPart = applyWorklist.getBodyPart();
				if(StringUtils.isBlank(applyBodyPart) && StringUtils.isNotBlank(bodyPart)) {
					applyWorklist.setBodyPart(bodyPart);
					applyWorklist.setUpdTime(new Date());
					applyWorklistMapper.updateByPrimaryKey(applyWorklist);
				}else if(StringUtils.isNoneBlank(applyBodyPart,bodyPart) && !applyBodyPart.contains(bodyPart)) {
					applyWorklist.setBodyPart(applyBodyPart+","+bodyPart);
					applyWorklist.setUpdTime(new Date());
					applyWorklistMapper.updateByPrimaryKey(applyWorklist);
				}
			}
			
			// 标注列表初始化
			LabelInfolist labelInfoList = new LabelInfolist();
			if(ObjectUtils.isEmpty(asrs)){
				labelInfoList.setSeriesUid(asrs.getSeriesUid());
				labelInfoList.setStatusCode(LabelStatusEnum.PENDING_LABEL.getStatusCode());
				labelInfoList.setCrtTime(new Date());
				SysDictDtl queryDictDtlById = sysDictDtlService.queryDictDtlById(LabelStatusEnum.PENDING_LABEL.getStatusCode());
				labelInfoList.setStatus(queryDictDtlById.getOthervalue());
				labelInfolistMapper.insert(labelInfoList);
			}
			//标注信息初始化
			if(ObjectUtils.isEmpty(aimg)){
				LabelInfo labelInfo = new LabelInfo();
				labelInfo.setUid(labelInfoList.getLabelAccnum());
				labelInfo.setStudyUid(studyUID);
				labelInfo.setSeriesUid(seriesUID);
				labelInfo.setImageUid(sopUID);
				labelInfoMapper.insertSelective(labelInfo);
			}
			log.info(logseries+":========dcm：申请表信息初始化完成===");
	}
	
	private String getTransName(String patientName) {
		if(StringUtils.isNotEmpty(patientName)) {
			String replaceName = StringUtils.replaceEach(patientName, new String[]{"^", "*","/"}, new String[]{" ", "", ""});
			String trimName = StringUtils.trimToEmpty(replaceName);
			return trimName;
		}
		return null;
	}

	private String getDcmInfoValue(Object object,String defaultValue) {
		return getDcmInfoValue(object,defaultValue,"Value",null);
	}
	
	private String getDcmPatientName(Object object,String defaultValue,String key,String oneKey,String twoKey) {
		if(!ObjectUtils.isEmpty(object)){
			if (object instanceof Map) {
				Object list = ((Map) object).get(key);
				if(!ObjectUtils.isEmpty(list)){
					if(list instanceof Collection){
						Object value = ((List)list).get(0);
						if(!ObjectUtils.isEmpty(value)){
							if(StringUtils.isNotEmpty(oneKey) && value instanceof Map){
								return Objects.toString(((Map) value).get(oneKey),Objects.toString(((Map) value).get(twoKey),defaultValue));
							}
						}
					}
				}
			}
		}
		return defaultValue;
	}
	
	private String getDcmInfoValue(Object object,String defaultValue,String key,String otherKey) {
		if(!ObjectUtils.isEmpty(object)){
			if (object instanceof Map) {
				Object list = ((Map) object).get(key);
				if(!ObjectUtils.isEmpty(list)){
					if(list instanceof Collection){
						Object value = ((List)list).get(0);
						if(!ObjectUtils.isEmpty(value)){
							if(StringUtils.isNotEmpty(otherKey) && value instanceof Map){
								return Objects.toString(((Map) value).get(otherKey),defaultValue);
							}else if(value instanceof String){
								return Objects.toString(value,defaultValue);
							}
						}
					}else if(list instanceof String){
						return Objects.toString(list,defaultValue);
					}
				}
			}
		}
		return defaultValue;
	}

	@Override
	public void initErrorApply(Map<String, Object> mesge) {
		try {
			
			//mq返回消息解析错误处理
			String studyUID=Objects.toString(mesge.get("studyUID"),"");
			String sopUID=Objects.toString(mesge.get("sopUID"),"");
			String seriesUID=Objects.toString(mesge.get("seriesUID"),"");
			String seriesDate=Objects.toString(mesge.get("seriesDate"),"");
			String seriesTime=Objects.toString(mesge.get("seriesTime"),"");
			String instanceDate=Objects.toString(mesge.get("instanceDate"),"");
			String instanceTime=Objects.toString(mesge.get("instanceTime"),"");
			String dcmFilePath=Objects.toString(mesge.get("dcmFilePath"),"");
			String bodyPart=Objects.toString(mesge.get("bodyPart"),"");
			String patientName=Objects.toString(mesge.get("patientName"),"");
			String userId=Objects.toString(mesge.get("userId"),"");
			String sex=Objects.toString(mesge.get("sex"),"");
			String patientAge=Objects.toString(mesge.get("age"),"");
			String studyTime=Objects.toString(mesge.get("studyTime"),"");
			
			Assert.isAnyBlank("初始化错误mq：studyUID,seriesUID,sopUID为空", studyUID,seriesUID,sopUID);
			//获取机构编号对应的机构信息
			SysOrganization sysorg = new SysOrganization();
			sysorg.setCode(userId);
			sysorg.setIsdelete(Boolean.FALSE);
			List<SysOrganization> queryBySysOrg = sysOrganizationService.queryBySysOrg(sysorg);
			Assert.isListOnlyOne(queryBySysOrg, "机构编号不存在或存在多个！机构号："+userId);
			SysOrganization sysOrganization = queryBySysOrg.get(0);
			
			String logseries="(异常dcm)mq初始化数据,传片机构："+sysOrganization.getName()+",机构号："+userId;
			log.info(logseries+":=======uid信息，studyuid："+studyUID+" seriesuid:"+seriesUID+" instanceuid:"+sopUID);
			//错误-只需要初始化image表以及申请表
			//错误-影像初始化
			ApplyImage aig = applyImageMapper.selectByPrimaryKey(sopUID);
			//image为空：认为这个图像第一次入库并且是错误的图像信息        如果image不为空：默认库里已经有该数据，这里不再做任何操作
			if(ObjectUtils.isEmpty(aig)) {
				//检查初始化
				ApplyStudy asty = applyStudyMapper.selectByPrimaryKey(studyUID);
				if(ObjectUtils.isEmpty(asty)) {
					//ApplyStudy 为空 说明该检查第一次入库。否则一已经在不再入库
					ApplyStudy applyStudy=new ApplyStudy();
					applyStudy.setStudyUid(studyUID);
					applyStudy.setCrtTime(new Date());
					applyStudyMapper.insertSelective(applyStudy);
					log.info(logseries+":=======dcm:检查初始化完成");
				}
				
				//系列信息初始化
				ApplySeries ases = applySeriesMapper.selectByPrimaryKey(seriesUID);
				if(ObjectUtils.isEmpty(ases)){
					//ApplySeries 为空 说明该检查第一次入库。否则一已经在不再入库
					ApplySeries applySeries=new ApplySeries();
					applySeries.setBodyPart(bodyPart);
					applySeries.setSeriesDate(seriesDate);
					applySeries.setSeriesTime(seriesTime);
					applySeries.setSeriesUid(seriesUID);
					applySeries.setStudyUid(studyUID);
					applySeries.setCrtTime(new Date());
					applySeriesMapper.insertSelective(applySeries);
					log.info(logseries+":=======dcm:系列初始化完成");
				}
				
				//影像信息初始化
				ApplyImage applyImage=new ApplyImage();
				applyImage.setInstanceUid(sopUID);
				applyImage.setSeriesUid(seriesUID);
				applyImage.setImgaeDeleted(true);//判断该影像是否无效
				applyImage.setKeyImage(false);//是否关键影像
				applyImage.setRcvdDate(instanceDate);
				applyImage.setRcvdTime(instanceTime);
				applyImage.setCrtTime(new Date());
				applyImage.setImageFile(dcmFilePath);
				applyImageMapper.insertSelective(applyImage);
				log.info(logseries+":=======dcm:影像初始化完成");
				
				//错误-申请表初始化   ApplyWorklist不为空：不做处理，为空：认为是第一次申请，会做简单的初始化
				ApplyWorklist awo=new ApplyWorklist();
				awo.setStudyUid(studyUID);
				List<ApplyWorklist> applyWorklists = applyWorklistMapper.select(awo);
				judeListSize(applyWorklists,"ApplyWorklist数据库中有多个，studyUID{}",studyUID);
				if(ObjectUtils.isEmpty(applyWorklists)){
					ApplyWorklist applyWorklist=new ApplyWorklist();
					String checkNum=getRandomNum();
					applyWorklist.setCheckNum(checkNum);//检查单号由自己生成
					applyWorklist.setPtnId("pid-"+checkNum);
					applyWorklist.setPtnName(getTransName(patientName));
					applyWorklist.setSexCode(StringUtils.isEmpty(sex)?"O":sex);
					SysDictDtl sysDictDtlForSex = sysDictDtlService.quertDictDtlByDictIdAndName("1", StringUtils.isEmpty(sex)?"O":sex);
					applyWorklist.setSex(ObjectUtils.isEmpty(sysDictDtlForSex)?"其他":sysDictDtlForSex.getOthervalue());
					applyWorklist.setSexCode(ObjectUtils.isEmpty(sysDictDtlForSex)?"3426":sysDictDtlForSex.getId());
					applyWorklist.setStudyTime(StringUtils.isEmpty(studyTime)?null:DateUtils.stringToDate(studyTime, DateUtils.patternF));
					applyWorklist.setBodyPart(bodyPart);;
					applyWorklist.setApplyDoc(null);
					applyWorklist.setApplyOrg(sysOrganization.getId());
					applyWorklist.setApplyStatus("待申请");
					applyWorklist.setApplyStatusCode("6000");
					applyWorklist.setStudyUid(studyUID);
					Map<String,String> ageMap=getPatientByAgeOrBirthDay(patientAge,null);
					applyWorklist.setPtnAge(ageMap.get("ptnAge"));
					applyWorklist.setPtnAgeUnit(ageMap.get("ptnAgeUnit"));
					applyWorklist.setPtnAgeUnitCode(ageMap.get("ptnAgeUnitCode"));
					applyWorklist.setCrtTime(new Date());
					applyWorklist.setIsNormalImage(false);
					applyWorklistMapper.insertSelective(applyWorklist);
					log.info(logseries+":=======dcm:申请初始化完成");
				}
			}else{
				log.info(logseries+":=======dcm:图像已经存在，直接返回---");
				log.info(logseries+":=======dcm:申请初始化完成=========");
			}
		} catch (RmisException e) {
			log.error("mq--initErrorApply:初始化错误影像异常", e.getMessage());
		}catch (Exception e) {
			log.error("申请错误影像初始化异常！", e);
		}
	}
	
	private static String getCharSetName(String charset, String name) {
		try {
			String ca=Objects.toString(charset,"gbk");
			if(StringUtils.isBlank(name)){
				return null;
			}else{
				String charname = new String(name.getBytes(ca),"utf8");
				return charname;
			}
		} catch (Exception e) {
			log.error("提交申请时，name值转码异常", e);
		}
		return null;
	}

	private void judeListSize(List<?> lists, String messge,String id) {
		//三张表如果数据多个，记录
		if(lists.size()>0){
			log.error("messge", id);
		}
	}
	
	private static String getRandomNum(){
		String accessionNum = DateUtils.dateToString(new Date(), DateUtils.patternF)+"-"+RandomStringUtils.randomNumeric(5);
		return accessionNum;
	}
	
	private Map<String,String> getPatientByAgeOrBirthDay(String patientAge, String birthDay) {
		String ptnAge="0";
		String ptnAgeUnit="岁";
		String ptnAgeUnitCode="3402";
		
		if(StringUtils.isNotEmpty(patientAge)){
			String age = patientAge.substring(0,patientAge.length()-1);
			String ageUnitCode = patientAge.substring(patientAge.length()-1);
			if(StringUtils.isNotEmpty(ageUnitCode)){
				SysDictDtl sysDictDtlForAge = sysDictDtlService.quertDictDtlByDictIdAndName("8", StringUtils.upperCase(ageUnitCode));	
			    if(!ObjectUtils.isEmpty(sysDictDtlForAge)){
			    	ptnAge=age;
			    	ptnAgeUnit=sysDictDtlForAge.getOthervalue();
			    	ptnAgeUnitCode=sysDictDtlForAge.getId();
			    }
			}
		}else if(StringUtils.isNotEmpty(birthDay)){
			boolean validDate = DateUtils.isValidDate(birthDay, DateUtils.patternB);
			if(validDate) {
			  Date myday = DateUtils.stringToDate(birthDay, DateUtils.patternB);
			  if(new Date().after(myday)) {
				   Calendar cal = Calendar.getInstance(); 
				   int yearNow = cal.get(Calendar.YEAR);  
			       int monthNow = cal.get(Calendar.MONTH);  
			       int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
			       cal.setTime(myday);  
			  
			       int yearBirth = cal.get(Calendar.YEAR);  
			       int monthBirth = cal.get(Calendar.MONTH);  
			       int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
				  
			       int temYear=yearNow-yearBirth;
			       int temMonth=monthNow-monthBirth;
			       int temDay=dayOfMonthNow-dayOfMonthBirth;
				  
			       if (temMonth<=0) {  
			            if (temMonth==0) {  
			                if (temDay<0) temYear--;  
			            }else{  
			            	temYear--;  
			            }  
			        }
			       
			       if(temYear>0) {
			    	    ptnAge=String.valueOf(temYear);
			   		    ptnAgeUnit="岁";
			   		    ptnAgeUnitCode="3402";
			       }else if(temMonth>0){
			    	    ptnAge=String.valueOf(temMonth);
			   		    ptnAgeUnit="月";
			   		    ptnAgeUnitCode="3427";
			       }else if(temDay>0) {
			    	   if(temDay>7) {
			    		   ptnAge=String.valueOf(temDay/7);
				   		   ptnAgeUnit="周";
				   		   ptnAgeUnitCode="6009";
			    	   }else {
			    		   ptnAge=String.valueOf(temDay);
				   		   ptnAgeUnit="天";
				   		   ptnAgeUnitCode="3428";
			    	   }
			       }
			  }
			}
		}
		
		Map<String,String> ageMap=new HashMap<String,String>();
		ageMap.put("ptnAge", getAgeWithNotStartZero(ptnAge));
		ageMap.put("ptnAgeUnit", ptnAgeUnit);
		ageMap.put("ptnAgeUnitCode", ptnAgeUnitCode);
		return ageMap;
	}

	private String getAgeWithNotStartZero(String ptnAge) {
		if(StringUtils.isNotEmpty(ptnAge)) {
			Matcher matcher = Pattern.compile("[1-9]").matcher(ptnAge);
			if(matcher.find()) {
				return ptnAge.substring(matcher.start());
			}else {
				return "0";
			}
		}
		return "0";
	}
}
