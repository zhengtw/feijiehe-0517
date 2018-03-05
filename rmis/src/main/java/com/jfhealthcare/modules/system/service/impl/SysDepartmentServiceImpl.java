package com.jfhealthcare.modules.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.utils.NameUtils;
import com.jfhealthcare.common.utils.TransferUtils;
import com.jfhealthcare.modules.system.entity.SysDepartment;
import com.jfhealthcare.modules.system.entity.SysOperatorDtl;
import com.jfhealthcare.modules.system.mapper.SysDepartmentMapper;
import com.jfhealthcare.modules.system.mapper.SysOperatorDtlMapper;
import com.jfhealthcare.modules.system.request.SysDepartmentRequest;
import com.jfhealthcare.modules.system.service.SysDepartmentService;


@Service
public class SysDepartmentServiceImpl implements SysDepartmentService {
	
	@Autowired
	private SysDepartmentMapper sysDepartmentMapper;
	
	@Autowired
	private SysOperatorDtlMapper sysOperatorDtlMapper;

	@Override
	public PageInfo<Map<String,Object>> querySysDepartment(SysDepartmentRequest sysDepartmentRequest) {
		SysDepartment sysDepartment=new SysDepartment();
		TransferUtils.copyPropertiesIgnoreNull(sysDepartmentRequest, sysDepartment);
		Page<Object> startPage = PageHelper.startPage(sysDepartmentRequest.getPageNum(), sysDepartmentRequest.getPageSize());
		List<Map<String,Object>> sysDepartments =sysDepartmentMapper.queryDepartmentBycondition(sysDepartment);
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(sysDepartments);
		return pageInfo;
	}
	
	@Override
	public List<SysDepartment> queryAllSysDepartment() {
		List<SysDepartment> all = sysDepartmentMapper.selectAll();
		return all;
	}
	
	@Override
	public SysDepartment querySysDepartment(String id) {
		SysDepartment selectByPrimaryKey = sysDepartmentMapper.selectByPrimaryKey(id);
		return selectByPrimaryKey;
	}

	@Override
	public void insertSysDepartment(SysDepartmentRequest sysDepartmentRequest) {
		SysDepartment sysDepartment=new SysDepartment();
		TransferUtils.copyPropertiesIgnoreNull(sysDepartmentRequest, sysDepartment);
		sysDepartment.setStatus(true);
		sysDepartment.setCrtTime(new Date());
		sysDepartment.setCrtUser(NameUtils.getLoginCode());
		sysDepartment.setIsdelete(false);
		sysDepartmentMapper.insertSelective(sysDepartment);
	}

	@Override
	public void updateSysDepartment(SysDepartmentRequest sysDepartmentRequest) {
		SysDepartment sysDepartment=new SysDepartment();
		TransferUtils.copyPropertiesIgnoreNull(sysDepartmentRequest, sysDepartment);
		sysDepartmentMapper.updateByPrimaryKey(sysDepartment);
	}

	@Override
	@Transactional
	public void deleteSysDepartment(String isDelete,String id) {
		if(StringUtils.equals("1", isDelete)){
			SysOperatorDtl sysOperatorDtl=new SysOperatorDtl();
			sysOperatorDtl.setDepId(id);
			List<SysOperatorDtl> sysOperatorDtls = sysOperatorDtlMapper.select(sysOperatorDtl);
			if(!CollectionUtils.isEmpty(sysOperatorDtls)){
				for (SysOperatorDtl sdl : sysOperatorDtls) {
					sdl.setDepId(null);
					sysOperatorDtlMapper.updateByPrimaryKey(sdl);
				}
			}
		}
		sysDepartmentMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<SysDepartment> querySysDepartmentByOrg(String orgId) {
		SysDepartment sysDepartment =new SysDepartment();
		sysDepartment.setOrgId(orgId);
		sysDepartment.setIsdelete(false);
		List<SysDepartment> sysDepartments = sysDepartmentMapper.select(sysDepartment);
		return sysDepartments;
	}

	

	
}
