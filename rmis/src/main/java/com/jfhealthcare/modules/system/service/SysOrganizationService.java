package com.jfhealthcare.modules.system.service;


import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.system.entity.SysOrganization;
import com.jfhealthcare.modules.system.request.SysOrganizationRequest;
import com.jfhealthcare.modules.system.response.OrganizationResponse;

public interface SysOrganizationService {

	PageInfo<OrganizationResponse> queryByCondition(SysOrganizationRequest sysOrganizationRequest);

	void insertSysOrganization(SysOrganizationRequest sysOrganizationRequest);

	void updateSysOrganization(SysOrganizationRequest sysOrganizationRequest);

	void deleteSysOrganization(String id);

	OrganizationResponse querySingleSysOrganization(String id);

	List<OrganizationResponse> queryAllSysOrganization();

	List<SysOrganization> queryBySysOrg(SysOrganization sysorg);
	

}
