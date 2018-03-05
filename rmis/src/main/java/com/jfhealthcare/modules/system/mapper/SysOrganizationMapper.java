package com.jfhealthcare.modules.system.mapper;

import java.util.List;

import com.jfhealthcare.modules.system.entity.SysOrganization;
import com.jfhealthcare.modules.system.request.SysOrganizationRequest;
import com.jfhealthcare.modules.system.response.OrganizationResponse;
import com.jfhealthcare.tk.mybatis.util.MyMapper;

public interface SysOrganizationMapper extends MyMapper<SysOrganization> {
	List<OrganizationResponse> queryOrganizationList();

	List<OrganizationResponse> queryOrganizationListByParameter(SysOrganizationRequest sysOrg);

}