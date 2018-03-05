package com.jfhealthcare.modules.system.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.jfhealthcare.modules.system.entity.RepGroup;
import com.jfhealthcare.modules.system.entity.SysRepGroup;
import com.jfhealthcare.modules.system.request.RepGroupRequest;
import com.jfhealthcare.modules.system.request.SysRepGroupRequest;

public interface RepGroupService {

	public PageInfo<RepGroup> queryRepGroup(RepGroupRequest repGroupRequest) ;

	public List<SysRepGroup> queryLoginCodeByGroupId(String id);

	public void delectRepGroup(String groupId);

	public void saveRepGroup(RepGroup re);

	public void updateRepGroup(RepGroup re);

	public void updateSysRepGroup(SysRepGroupRequest sysRepGroupRequest);

	public RepGroup queryRepGroupByGroupId(String groupId);

	public void deleteRepGroup(String groupId,String logincodes);

	public List<RepGroup> queryRepGroupAll();

	public String queryMaxNindex();


}
