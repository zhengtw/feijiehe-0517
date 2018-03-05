package com.jfhealthcare.modules.business.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.common.enums.RedisEnum;
import com.jfhealthcare.common.utils.NameUtils;
import com.jfhealthcare.common.utils.RedisUtils;
import com.jfhealthcare.common.utils.TransferUtils;
import com.jfhealthcare.modules.business.entity.SetReportDb;
import com.jfhealthcare.modules.business.entity.SetReportPrivate;
import com.jfhealthcare.modules.business.mapper.SetReportDbMapper;
import com.jfhealthcare.modules.business.mapper.SetReportPrivateMapper;
import com.jfhealthcare.modules.business.request.SetReportRequest;
import com.jfhealthcare.modules.business.response.SetReportResponse;
import com.jfhealthcare.modules.business.service.SetReportService;
import com.jfhealthcare.modules.system.entity.SysArmariumOper;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Slf4j
@Service
public class SetReportServiceImpl implements SetReportService {

	@Autowired
	private SetReportDbMapper setReportDbMapper;
	@Autowired
	private SetReportPrivateMapper setReportPrivateMapper;
	@Autowired
	private RedisUtils redisUtils;
	
	@Override
	public List<SetReportResponse> querySetReportWithArmarium(String isPublic,LoginUserEntity loginUserEntity) {
		List<SysArmariumOper> sysArmariumOpers = loginUserEntity.getSysArmariumOpers();
		if(sysArmariumOpers!=null && sysArmariumOpers.size()>0){
			/**
			 *    ***重要***目前权限只有DR   先写死DR   后期加仪器权限再优化知识库
			 */
//			Set<String> armariumNames=new HashSet<String>();
//			for (SysArmariumOper sysArmariumOper : sysArmariumOpers) {
//				armariumNames.add(sysArmariumOper.getArmarium());
//			}
//			if(armariumNames.size()>0){
			    //公共
				if("0".equals(isPublic)){
					Date start=new Date();
					List<SetReportResponse> srrs = redisUtils.get(RedisEnum.PUBLICDR.getValue(),List.class);
					Date end=new Date();
					if(CollectionUtils.isEmpty(srrs)) {
						log.info("知识库模板缓存获取失败，查询获取！");
						Example example = new Example(SetReportDb.class);
						Criteria createCriteria = example.createCriteria();
						createCriteria.andEqualTo("isdelete", false).andEqualTo("upper", "0");
						createCriteria.andEqualTo("type", "DR");
						example.setOrderByClause("CAST(NINDEX AS DECIMAL) asc");
						List<SetReportDb> srds = setReportDbMapper.selectByExample(example);
						srrs = getSetReport(srds);
						redisUtils.set(RedisEnum.PUBLICDR.getValue(), srrs, RedisUtils.NOT_EXPIRE);
					}else {
						log.info("知识库模板缓存获取成功，查询获取！用时："+(end.getTime()-start.getTime()));
					}
					return srrs;
					
			    //私有
				}else if("1".equals(isPublic)){
					Example example = new Example(SetReportPrivate.class);
					Criteria createCriteria = example.createCriteria();
					createCriteria.andEqualTo("isdelete", false).andEqualTo("upper", "0").andEqualTo("logincode", NameUtils.getLoginCode());
					createCriteria.andEqualTo("type", "DR");
					example.setOrderByClause("CAST(NINDEX AS DECIMAL) asc");
					List<SetReportPrivate> srds = setReportPrivateMapper.selectByExample(example);
					List<SetReportResponse> srrs = getSetReportPrivate(srds);
					return srrs;
				}
//			}
		}
		return null;
	}
	
	/**
	 * 1.如果是文件夹  返回   文件夹+子文件
	 * 2.如果是文件  返回  文件
	 * @param setReportDbs
	 * @return
	 */
	public List<SetReportResponse> getSetReport(List<SetReportDb> setReportDbs){
		List<SetReportResponse> childrens=new ArrayList<SetReportResponse>();
		if(setReportDbs !=null && setReportDbs.size()>0){
			for (SetReportDb setReportDb : setReportDbs) {
				SetReportResponse setReportResponse=new SetReportResponse();
				TransferUtils.copyPropertiesIgnoreNull(setReportDb, setReportResponse);
				if(!setReportResponse.getFileflag()){
					childrens.add(setReportResponse);
				}else{
					Example ex = new Example(SetReportDb.class);
					Criteria cc = ex.createCriteria();
					cc.andEqualTo("isdelete", false).andEqualTo("upper", setReportDb.getId());
					ex.setOrderByClause("CAST(NINDEX AS DECIMAL) asc");
					List<SetReportDb> srdChildrens = setReportDbMapper.selectByExample(ex);
					List<SetReportResponse> srrChildrens=getSetReport(srdChildrens);
					setReportResponse.setChildrens(srrChildrens);
					childrens.add(setReportResponse);
				}
			}
		}
		return childrens;
	}
	
	/**
	 * 1.如果是文件夹  返回   文件夹+子文件
	 * 2.如果是文件  返回  文件
	 * @param setReportPrivates
	 * @return
	 */
	public List<SetReportResponse> getSetReportPrivate(List<SetReportPrivate> setReportPrivates){
		List<SetReportResponse> childrens=new ArrayList<SetReportResponse>();
		if(setReportPrivates !=null && setReportPrivates.size()>0){
			for (SetReportPrivate setReportPrivate : setReportPrivates) {
				SetReportResponse setReportResponse=new SetReportResponse();
				TransferUtils.copyPropertiesIgnoreNull(setReportPrivate, setReportResponse);
				if(!setReportResponse.getFileflag()){//是文件
					childrens.add(setReportResponse);
				}else{//是文件夹
					Example ex = new Example(SetReportPrivate.class);
					Criteria cc = ex.createCriteria();
					cc.andEqualTo("isdelete", false).andEqualTo("upper", setReportPrivate.getId()).andEqualTo("logincode", NameUtils.getLoginCode());
					ex.setOrderByClause("CAST(NINDEX AS DECIMAL) asc");
					List<SetReportPrivate> srdChildrens = setReportPrivateMapper.selectByExample(ex);
					List<SetReportResponse> srrChildrens=getSetReportPrivate(srdChildrens);
					setReportResponse.setChildrens(srrChildrens);
					childrens.add(setReportResponse);
				}
			}
		}
		return childrens;
	}
	
	@Override
	public SetReportResponse querySetReportByRepId(String isPublic,String repId) {
		
		SetReportResponse SetReportResponse=new SetReportResponse();
		//公共
		if("0".equals(isPublic)){
			SetReportDb setReportDb = setReportDbMapper.selectByPrimaryKey(repId);
			TransferUtils.copyPropertiesIgnoreNull(setReportDb, SetReportResponse);
		//私有
		}else if("1".equals(isPublic)){
			SetReportPrivate setReportPrivate = setReportPrivateMapper.selectByPrimaryKey(repId);
			TransferUtils.copyPropertiesIgnoreNull(setReportPrivate, SetReportResponse);
		}
		return SetReportResponse;
	}

	@Override
	public void saveSetReport(SetReportRequest setReportRequest) {
		//公共
		if("0".equals(setReportRequest.getIsPublic())){
			redisUtils.delete(RedisEnum.PUBLICDR.getValue());
			SetReportDb setReportDb=new SetReportDb();
			TransferUtils.copyPropertiesIgnoreNull(setReportRequest,setReportDb);
			setReportDb.setCrtTime(new Date());
			setReportDb.setCrtUser(NameUtils.getLoginCode());
			setReportDb.setIsdelete(false);
			setReportDbMapper.insertSelective(setReportDb);
		//私有
		}else if("1".equals(setReportRequest.getIsPublic())){
			SetReportPrivate setReportPrivate=new SetReportPrivate();
			TransferUtils.copyPropertiesIgnoreNull(setReportRequest,setReportPrivate);
			setReportPrivate.setCrtTime(new Date());
			setReportPrivate.setCrtUser(NameUtils.getLoginCode());
			setReportPrivate.setIsdelete(false);
			setReportPrivateMapper.insertSelective(setReportPrivate);
		}
	}

	@Override
	@Transactional
	public void deleteSetReport(String isPublic, String repId) {
	       //公共
			if("0".equals(isPublic)){
				redisUtils.delete(RedisEnum.PUBLICDR.getValue());
				SetReportDb setReportDb = setReportDbMapper.selectByPrimaryKey(repId);
				List<SetReportDb> setReportDbs=new ArrayList<SetReportDb>();
				setReportDbs.add(setReportDb);
				List<SetReportResponse> setReport = getSetReport(setReportDbs);
				Set<String> ids=getReportIds(setReport);
				if(ids.size()>0){
					for (String id : ids) {
						setReportDbMapper.deleteByPrimaryKey(id);
					}
				}
			//私有
			}else if("1".equals(isPublic)){
				SetReportPrivate setReportPrivate = setReportPrivateMapper.selectByPrimaryKey(repId);
				List<SetReportPrivate> reportPrivates=new ArrayList<SetReportPrivate>();
				reportPrivates.add(setReportPrivate);
				List<SetReportResponse> srps = getSetReportPrivate(reportPrivates);
				Set<String> ids=getReportIds(srps);
				if(ids.size()>0){
					for (String id : ids) {
						setReportPrivateMapper.deleteByPrimaryKey(id);
					}
				}
				
			}
	}

	private Set<String> getReportIds(List<SetReportResponse> setReport) {
		Set<String> ids=new HashSet<String>();
		for (SetReportResponse setReportResponse : setReport) {
			ids.add(setReportResponse.getId());
			getRIds(ids,setReportResponse);
		}
		return ids;
	}

	private void getRIds(Set<String> ids, SetReportResponse setReportResponse) {
		List<SetReportResponse> childrens = setReportResponse.getChildrens();
		if(childrens!=null && childrens.size()>0){
			for (SetReportResponse srr : childrens) {
				getRIds(ids,srr);
			}
		}else{
			ids.add(setReportResponse.getId());
		}
		
	}

	@Override
	@Transactional
	public void updateSetReport(SetReportRequest setReportRequest) {
	        //公共
			if("0".equals(setReportRequest.getIsPublic())){
				redisUtils.delete(RedisEnum.PUBLICDR.getValue());
				SetReportDb setReportDb = setReportDbMapper.selectByPrimaryKey(setReportRequest.getId());
				TransferUtils.copyPropertiesIgnoreNull(setReportRequest,setReportDb);
				setReportDb.setFinding(setReportRequest.getFinding());
				setReportDb.setImpression(setReportRequest.getImpression()); 
				setReportDb.setUpdTime(new Date());
				setReportDb.setUpdUser(NameUtils.getLoginCode());
				setReportDbMapper.updateByPrimaryKey(setReportDb);
			//私有
			}else if("1".equals(setReportRequest.getIsPublic())){
				SetReportPrivate setReportPrivate = setReportPrivateMapper.selectByPrimaryKey(setReportRequest.getId());
				TransferUtils.copyPropertiesIgnoreNull(setReportRequest,setReportPrivate);
				setReportPrivate.setFinding(setReportRequest.getFinding());
				setReportPrivate.setImpression(setReportRequest.getImpression());
				setReportPrivate.setUpdTime(new Date());
				setReportPrivate.setUpdUser(NameUtils.getLoginCode());
				setReportPrivateMapper.updateByPrimaryKey(setReportPrivate);
			}
	}

	@Override
	public List queryUpperForSetReport(String isPublic,LoginUserEntity loginUserEntity) {
		List<SysArmariumOper> sysArmariumOpers = loginUserEntity.getSysArmariumOpers();
		if(sysArmariumOpers!=null && sysArmariumOpers.size()>0){
			Set<String> armariumNames=new HashSet<String>();
			for (SysArmariumOper sysArmariumOper : sysArmariumOpers) {
				armariumNames.add(sysArmariumOper.getArmarium());
			}
			//公共
			if("0".equals(isPublic)){
				Example example = new Example(SetReportDb.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("isdelete", false).andEqualTo("fileflag", true);
				createCriteria.andIn("type", armariumNames);
				example.setOrderByClause("CAST(NINDEX AS DECIMAL) asc");
				List<SetReportDb> srds = setReportDbMapper.selectByExample(example);
				return srds;
		    //私有
			}else if("1".equals(isPublic)){
				Example example = new Example(SetReportPrivate.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("isdelete", false).andEqualTo("fileflag", true).andEqualTo("logincode", NameUtils.getLoginCode());
				createCriteria.andIn("type", armariumNames);
				example.setOrderByClause("CAST(NINDEX AS DECIMAL) asc");
				List<SetReportPrivate> srds = setReportPrivateMapper.selectByExample(example);
				return srds;
			}
		}
		return null;
	}

	@Override
	public String queryNindexForSetReport(String isPublic,String repId,LoginUserEntity loginUserEntity) {
		List<SysArmariumOper> sysArmariumOpers = loginUserEntity.getSysArmariumOpers();
		if(sysArmariumOpers!=null && sysArmariumOpers.size()>0){
			Set<String> armariumNames=new HashSet<String>();
			for (SysArmariumOper sysArmariumOper : sysArmariumOpers) {
				armariumNames.add(sysArmariumOper.getArmarium());
			}
			//公共
			if("0".equals(isPublic)){
				Example example = new Example(SetReportDb.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("isdelete", false).andEqualTo("upper", repId);
				createCriteria.andIn("type", armariumNames);
				example.setOrderByClause("CAST(NINDEX AS DECIMAL) desc");
				List<SetReportDb> srds = setReportDbMapper.selectByExample(example);
				Integer nindex=0;
				if(srds!=null && srds.size()>0){
					SetReportDb setReportDb = srds.get(0);
					nindex=StringUtils.isEmpty(setReportDb.getNindex())?0:Integer.parseInt(setReportDb.getNindex())+1;
				}
				return String.valueOf(nindex);
		    //私有
			}else if("1".equals(isPublic)){
				Example example = new Example(SetReportPrivate.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("isdelete", false).andEqualTo("upper", repId).andEqualTo("logincode", NameUtils.getLoginCode());
				createCriteria.andIn("type", armariumNames);
				example.setOrderByClause("CAST(NINDEX AS DECIMAL) desc");
				List<SetReportPrivate> srds = setReportPrivateMapper.selectByExample(example);
				Integer nindex=0;
				if(srds!=null && srds.size()>0){
					SetReportPrivate setReportPrivate = srds.get(0);
					nindex=StringUtils.isEmpty(setReportPrivate.getNindex())?0:Integer.parseInt(setReportPrivate.getNindex())+1;
				}
				return String.valueOf(nindex);
			}
		}
		return null;
	}

	
	
	
}
