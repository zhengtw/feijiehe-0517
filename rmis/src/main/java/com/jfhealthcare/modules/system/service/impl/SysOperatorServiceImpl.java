package com.jfhealthcare.modules.system.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jfhealthcare.common.entity.LoginUserEntity;
import com.jfhealthcare.common.enums.EncryptionEnum;
import com.jfhealthcare.common.enums.RedisEnum;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.common.utils.NameUtils;
import com.jfhealthcare.common.utils.RSAUtils;
import com.jfhealthcare.common.utils.RedisUtils;
import com.jfhealthcare.common.utils.TransferUtils;
import com.jfhealthcare.common.validator.ValidatorUtils;
import com.jfhealthcare.common.validator.group.Edit;
import com.jfhealthcare.modules.system.entity.SysArmariumOper;
import com.jfhealthcare.modules.system.entity.SysDict;
import com.jfhealthcare.modules.system.entity.SysOperRole;
import com.jfhealthcare.modules.system.entity.SysOperator;
import com.jfhealthcare.modules.system.entity.SysOperatorDtl;
import com.jfhealthcare.modules.system.entity.SysRepGroup;
import com.jfhealthcare.modules.system.entity.SysRole;
import com.jfhealthcare.modules.system.mapper.SysArmariumOperMapper;
import com.jfhealthcare.modules.system.mapper.SysOperRoleMapper;
import com.jfhealthcare.modules.system.mapper.SysOperatorDtlMapper;
import com.jfhealthcare.modules.system.mapper.SysOperatorMapper;
import com.jfhealthcare.modules.system.mapper.SysRepGroupMapper;
import com.jfhealthcare.modules.system.mapper.SysRoleMapper;
import com.jfhealthcare.modules.system.request.OrdinaryOperatorRequest;
import com.jfhealthcare.modules.system.request.SysOperatorAllRequest;
import com.jfhealthcare.modules.system.request.SysOperatorQueryRequest;
import com.jfhealthcare.modules.system.response.OperatorResponse;
import com.jfhealthcare.modules.system.service.SysOperatorService;
import com.jfhealthcare.modules.system.service.SysRightModsetService;
import com.jfhealthcare.modules.system.service.SysRightModuleService;
import com.jfhealthcare.modules.system.service.SysTokenService;

import tk.mybatis.mapper.entity.Example;

@Service
public class SysOperatorServiceImpl implements SysOperatorService {

	@Autowired
	private SysOperatorMapper sysOperatorMapper;
	@Autowired
	private SysTokenService sysTokenService;
	@Autowired
	private SysOperatorDtlMapper sysOperatorDtlMapper;
	@Autowired
	private SysOperRoleMapper sysOperRoleMapper;
	@Autowired
	private SysArmariumOperMapper sysArmariumOperMapper;
	@Autowired
	SysRightModsetService sysRightModsetService;
	@Autowired
	SysRightModuleService sysRightModuleService;
	@Autowired
	SysRepGroupMapper sysRepGroupMapper;
	@Autowired
	SysRoleMapper sysRoleMapper;
	@Autowired
	private RedisUtils redisUtils;

	@Override
	public SysOperator selectSysOperator(String logincode) {
		SysOperator sysOperator = new SysOperator();
		sysOperator.setLogincode(logincode);
		List<SysOperator> sysOperators = sysOperatorMapper.select(sysOperator);
		return sysOperators.isEmpty() ? null : sysOperators.get(0);
	}

	@Override
	public Map<String, Object> login(String logincode, String password) {
		UsernamePasswordToken shiroToken = new UsernamePasswordToken(logincode, password);
		Subject subject = SecurityUtils.getSubject();
		subject.login(shiroToken);
		Map<String, Object> createToken = null;
		if (subject.isAuthenticated()) {
			createToken = sysTokenService.createToken(logincode);
		}
		return createToken;
	}

	@Override
	public void save(String logincode, String password) {
		SysOperator sysOperator = new SysOperator();
		sysOperator.setLogincode(logincode);
		sysOperator.setPassword(password);
		sysOperator.setStatus(true);
		sysOperator.setIsdelete(false);
		sysOperator.setCrtTime(new Date());
		sysOperator.setCrtUser(NameUtils.getLoginCode());
		sysOperatorMapper.insertSelective(sysOperator);
	}

	@Override
	@Transactional
	public void saveOperatorAndDtl(SysOperator sysOperatorNew, SysOperatorDtl sysOperatorDtlNew, String logincode,
			String roleId, String armariums) {
		// 保存人员信息和详情信息
		sysOperatorNew.setIsdelete(Boolean.FALSE);
		sysOperatorNew.setCrtUser(logincode);
		sysOperatorNew.setStatus(Boolean.TRUE);
		sysOperatorNew.setCrtTime(new Date());

		sysOperatorDtlNew.setCrtUser(logincode);
		sysOperatorDtlNew.setCrtTime(new Date());

		//所有插入的
		sysOperatorMapper.insertSelective(sysOperatorNew);
		sysOperatorDtlMapper.insertSelective(sysOperatorDtlNew);
		// 保存人员和医疗器材的权限
		if(StringUtils.isNotBlank(armariums)){
			String[] split = armariums.split(",");
			for (String s1 : split) {
				String[] split2 = s1.split(":");
				if (split2.length < 2) {
					throw new RmisException("器材权限数据错误!");
				}
				SysArmariumOper sysao = new SysArmariumOper();
				sysao.setArmariumId(split2[0]);
				sysao.setArmarium(split2[1]);
				sysao.setStatus(Boolean.TRUE); // 启用
				sysao.setLogincode(sysOperatorNew.getLogincode());
				sysArmariumOperMapper.insertSelective(sysao);
			}
		}
		// 保存人员和角色关系
		if(StringUtils.isNotBlank(roleId)){
			addRoleOperater(sysOperatorNew, roleId);
		}
		

	}

	@Override
	@Transactional
	public void deleteOperator(String[] ids) {
		SysOperator sysOne = new SysOperator();
		for (String id : ids) {
			
			// 物理删除角色 用户表中的记录
			sysOne.setId(id);
			SysOperator sysOper = sysOperatorMapper.selectOne(sysOne);
			if (sysOper == null) {
				throw new RmisException("删除的用户不存在!");
			}
			
			//删除缓存
			redisUtils.delete(RedisEnum.LOGINCODE.getValue()+":"+sysOper.getLogincode());
			
			//删除用户角色关系
			Example example = new Example(SysOperRole.class);
			example.createCriteria().andEqualTo("logincode", sysOper.getLogincode());
			sysOperRoleMapper.deleteByExample(example);
			
			
			//物理删除 角色用户权限表中的记录
			sysRightModsetService.updateSysRightModset(sysOper.getLogincode(),null,1);
			sysRightModuleService.restartSysRightModule(sysOper.getLogincode());
			// 物理删除 用户和机器操作权限表
			Example example2 = new Example(SysArmariumOper.class);
			example2.createCriteria().andEqualTo("logincode", sysOper.getLogincode());
			sysArmariumOperMapper.deleteByExample(example2);
			//物理删除组和人员关系
			Example example3 = new Example(SysRepGroup.class);
			example3.createCriteria().andEqualTo("logincode", sysOper.getLogincode());
			sysRepGroupMapper.deleteByExample(example3);
			
			// 逻辑删除员工
			Example example4 = new Example(SysOperatorDtl.class);
			example4.createCriteria().andEqualTo("logincode", sysOper.getLogincode());
			sysOperatorDtlMapper.deleteByExample(example4);
			sysOperatorMapper.deleteByPrimaryKey(sysOper.getLogincode());
		}

	}
	
	@Override
	public void updateOperator(SysOperatorAllRequest sysOperatorAllRequest) throws Exception {
		
		//查询用户是否存在且只有一个
		SysOperator sysOperatorNew = queryOperatorIsOnlyOne(sysOperatorAllRequest.getId());
		String oldpassword = sysOperatorNew.getPassword();
		
		ValidatorUtils.validateEntity(sysOperatorAllRequest, Edit.class);
		//删除缓存
		redisUtils.delete(RedisEnum.LOGINCODE.getValue()+":"+sysOperatorNew.getLogincode());
		
		String roleId = sysOperatorAllRequest.getRoleIdd();
		String armariums = sysOperatorAllRequest.getArmariums();
		
		SysOperatorDtl sysOperatorDtlNew = sysOperatorDtlMapper.selectByPrimaryKey(sysOperatorAllRequest.getDid());
		
		TransferUtils.copyProperties(sysOperatorAllRequest, sysOperatorNew);
		TransferUtils.copyProperties(sysOperatorAllRequest, sysOperatorDtlNew);
		
//		SysOperator sysOperatorNew = new SysOperator();
//		SysOperatorDtl sysOperatorDtlNew = new SysOperatorDtl();

//		TransferUtils.copyPropertiesIgnoreNull(sysOperatorAllRequest, sysOperatorNew);
//		TransferUtils.copyPropertiesIgnoreNull(sysOperatorAllRequest, sysOperatorDtlNew);
		sysOperatorNew.setStatus(Boolean.TRUE);
		sysOperatorDtlNew.setId(sysOperatorAllRequest.getDid());
		if(StringUtils.isNotBlank(sysOperatorAllRequest.getPassword())){
			//解密前端密码
			String pwdreal = RSAUtils.decryptStringByJs(URLDecoder.decode(sysOperatorAllRequest.getPassword(), "UTF-8"));
			String encryptPassword = getEncryptPassword(sysOperatorAllRequest.getLogincode(), pwdreal);
			sysOperatorNew.setPassword(encryptPassword);
		}else{
			sysOperatorNew.setPassword(oldpassword);
		}
		
		
		//修改仪器权限
		Example sysArmariumOperExample = new Example(SysArmariumOper.class);
		sysArmariumOperExample.createCriteria().andEqualTo("logincode", sysOperatorNew.getLogincode());
		List<SysArmariumOper> sysArmariumOpers = sysArmariumOperMapper.selectByExample(sysArmariumOperExample);
		if(StringUtils.isNotBlank(armariums)){
			//判断一起权限是不是改变
			List<String> newArmariumsList=judgeArmariums(armariums,sysArmariumOpers);
			if(newArmariumsList!=null && newArmariumsList.size()>0){
				//删除该用户的仪器权限
				getDeleteSysArmariumOper(sysOperatorNew);
				for (String s1 : newArmariumsList) {
					String[] split2 = s1.split(":");
					if (split2.length < 2) {
						throw new RmisException("器材权限数据错误!");
					}
					SysArmariumOper sysao = new SysArmariumOper();
					sysao.setArmariumId(split2[0]);
					sysao.setArmarium(split2[1]);
					sysao.setStatus(Boolean.TRUE);
					sysao.setLogincode(sysOperatorNew.getLogincode());
					sysArmariumOperMapper.insertSelective(sysao);
				}
			}
		}else if(StringUtils.isBlank(armariums) && (sysArmariumOpers!=null && sysArmariumOpers.size()>0)){
			getDeleteSysArmariumOper(sysOperatorNew);
		}
		
		//用户角色修改
		Example example = new Example(SysOperRole.class);
		example.createCriteria().andEqualTo("logincode", sysOperatorNew.getLogincode());
		List<SysOperRole> sysOperRoles = sysOperRoleMapper.selectByExample(example);
		if(StringUtils.isNotBlank(roleId)){
			String roles=judgeRole(roleId,sysOperRoles);
			getDeleteRoleOperater(sysOperatorNew);
			if(StringUtils.isNotBlank(roleId)){
				addRoleOperater(sysOperatorNew, roleId);
			}
		}else if(StringUtils.isBlank(roleId) && (sysOperRoles!=null && sysOperRoles.size()>0)){
			getDeleteRoleOperater(sysOperatorNew);
			if(StringUtils.isNotBlank(roleId)){
				addRoleOperater(sysOperatorNew, roleId);
			}
		}
		
		sysOperatorNew.setUpdTime(new Date());
		sysOperatorNew.setUpdUser(NameUtils.getLoginCode());
		sysOperatorDtlNew.setUpdTime(new Date());
		sysOperatorDtlNew.setUpdUser(NameUtils.getLoginCode());
		
		sysOperatorDtlMapper.updateByPrimaryKey(sysOperatorDtlNew);
		sysOperatorMapper.updateByPrimaryKey(sysOperatorNew);
		sysTokenService.deleteToken(sysOperatorNew.getLogincode());
	}
	
	/**
     * 获取加密后的密码
     * @param password 明文密码
     * @param accountName 账户名称
     * @return
     */
    private  String getEncryptPassword(String accountName,String password){
			 return new SimpleHash(EncryptionEnum.ALGORITHMNAME.getValue(),password,
		    			ByteSource.Util.bytes(accountName + EncryptionEnum.SALT.getValue()),Integer.parseInt(EncryptionEnum.HASHITERATIONS.getValue())).toHex();
	 }

	@Override
	@Transactional
	public void updateOperator(SysOperator sysOperatorNew, SysOperatorDtl sysOperatorDtlNew, String roleId, String armariums,LoginUserEntity loginUserEntity) {
		//查询用户是否存在且只有一个
		SysOperator sysOper = queryOperatorIsOnlyOne(sysOperatorNew.getId());
		
		//删除缓存
		redisUtils.delete(RedisEnum.LOGINCODE.getValue()+":"+sysOper.getLogincode());
		
		//修改仪器权限
		Example sysArmariumOperExample = new Example(SysArmariumOper.class);
		sysArmariumOperExample.createCriteria().andEqualTo("logincode", sysOper.getLogincode());
		List<SysArmariumOper> sysArmariumOpers = sysArmariumOperMapper.selectByExample(sysArmariumOperExample);
		if(StringUtils.isNotBlank(armariums)){
			//判断一起权限是不是改变
			List<String> newArmariumsList=judgeArmariums(armariums,sysArmariumOpers);
			if(newArmariumsList!=null && newArmariumsList.size()>0){
				//删除该用户的仪器权限
				getDeleteSysArmariumOper(sysOper);
				for (String s1 : newArmariumsList) {
					String[] split2 = s1.split(":");
					if (split2.length < 2) {
						throw new RmisException("器材权限数据错误!");
					}
					SysArmariumOper sysao = new SysArmariumOper();
					sysao.setArmariumId(split2[0]);
					sysao.setArmarium(split2[1]);
					sysao.setStatus(Boolean.TRUE);
					sysao.setLogincode(sysOper.getLogincode());
					sysArmariumOperMapper.insertSelective(sysao);
				}
			}
		}else if(StringUtils.isBlank(armariums) && (sysArmariumOpers!=null && sysArmariumOpers.size()>0)){
			getDeleteSysArmariumOper(sysOper);
		}
		
		//用户角色修改
		Example example = new Example(SysOperRole.class);
		example.createCriteria().andEqualTo("logincode", sysOper.getLogincode());
		List<SysOperRole> sysOperRoles = sysOperRoleMapper.selectByExample(example);
		if(StringUtils.isNotBlank(roleId)){
			String roles=judgeRole(roleId,sysOperRoles);
			getDeleteRoleOperater(sysOper);
			if(StringUtils.isNotBlank(roleId)){
				addRoleOperater(sysOperatorNew, roleId);
			}
		}else if(StringUtils.isBlank(roleId) && (sysOperRoles!=null && sysOperRoles.size()>0)){
			getDeleteRoleOperater(sysOper);
			if(StringUtils.isNotBlank(roleId)){
				addRoleOperater(sysOperatorNew, roleId);
			}
		}
		
		sysOperatorNew.setUpdTime(new Date());
		sysOperatorNew.setUpdUser(NameUtils.getLoginCode());
		sysOperatorDtlNew.setUpdTime(new Date());
		sysOperatorDtlNew.setUpdUser(NameUtils.getLoginCode());
		
		sysOperatorDtlMapper.updateByPrimaryKey(sysOperatorDtlNew);
		sysOperatorMapper.updateByPrimaryKey(sysOperatorNew);
		sysTokenService.deleteToken(sysOper.getLogincode());
	}

	

	private void getDeleteRoleOperater(SysOperator sysOper) {
		Example example2 = new Example(SysOperRole.class);
		example2.createCriteria().andEqualTo("logincode", sysOper.getLogincode());
		sysOperRoleMapper.deleteByExample(example2);
	}

	private void getDeleteSysArmariumOper(SysOperator sysOper) {
		Example example = new Example(SysArmariumOper.class);
		example.createCriteria().andEqualTo("logincode",sysOper.getLogincode());
		sysArmariumOperMapper.deleteByExample(example);
	}

	private String judgeRole(String roleId, List<SysOperRole> sysOperRoles) {
		List<String> roleIds=new ArrayList<String>();
		for (SysOperRole sysOperRole : sysOperRoles) {
			roleIds.add(sysOperRole.getRoleId());
		}
		String[] split2 = roleId.split(",");
		List<String> asList = Arrays.asList(split2);
		if(sysOperRoles.size()==asList.size()){
			roleIds.removeAll(asList);
			if(roleIds==null && roleIds.size()==0){
				return null;
			}
		}
		return roleId;
	}

	private List<String> judgeArmariums(String armariums, List<SysArmariumOper> sysArmariumOpers) {
			String[] newArmariums = armariums.split(",");
			List<String> newArmariumsList = Arrays.asList(newArmariums);
			
			if(sysArmariumOpers!=null && sysArmariumOpers.size()>0){
				List<String> originArmariums = new ArrayList<String>();
				for(SysArmariumOper sysArmariumOper:sysArmariumOpers){
					originArmariums.add(sysArmariumOper.getArmariumId()+":"+sysArmariumOper.getArmarium());
				}
				if(originArmariums.size()==newArmariumsList.size()){
					originArmariums.removeAll(newArmariumsList);
					if(originArmariums.size()==0){
						return null;
					}
				}
			}
		return newArmariumsList;
	}

	@Override
	public void updateOrdinaryOperator(OrdinaryOperatorRequest ooRequest) {
		
		//查询用户是否存在且只有一个
		SysOperator selectOne = queryOperatorIsOnlyOne(ooRequest.getId());
		selectOne.setUpdUser(selectOne.getLogincode());
		selectOne.setUpdTime(new Date());
		if (StringUtils.isNotBlank(ooRequest.getOldPassword()) && StringUtils.isNotBlank(ooRequest.getNewPassword())) {
			// 修改密码
			if (ooRequest.getOldPassword().equals(selectOne.getPassword())) {
				selectOne.setPassword(ooRequest.getNewPassword());
				sysOperatorMapper.updateByPrimaryKey(selectOne);
			} else {
				throw new RmisException("原始密码不正确!");
			}
		}
		// 修改个人主页
		if(StringUtils.isNotBlank(ooRequest.getHomePage())){
			selectOne.setHomePage(ooRequest.getHomePage());
			sysOperatorMapper.updateByPrimaryKey(selectOne);
		}
		if (StringUtils.isNotBlank(ooRequest.getArmariums())) {
			//修改医疗器械权限
			Example example = new Example(SysArmariumOper.class);
			example.createCriteria().andEqualTo("logincode",selectOne.getLogincode());
			List<SysArmariumOper> selectBe = sysArmariumOperMapper.selectByExample(example);
			String[] split = ooRequest.getArmariums().split(",");
			List<String> asList = Arrays.asList(split);
			for (SysArmariumOper sysao : selectBe) {
				if(asList.contains(sysao.getId())){
					//修改为status true
					sysao.setStatus(Boolean.TRUE);
				}else{
					//修改为status false
					sysao.setStatus(Boolean.FALSE);
				}
				sysArmariumOperMapper.updateByPrimaryKey(sysao);
			}
		}	
		sysTokenService.deleteToken(selectOne.getLogincode());
	}

	@Override
	public PageInfo<OperatorResponse> queryOperator(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<OperatorResponse> selectAll = sysOperatorMapper.queryOperatorList();
		PageInfo<OperatorResponse> pageInfo = new PageInfo<OperatorResponse>(selectAll);
		return pageInfo;
	}

	@Override
	public PageInfo<OperatorResponse> queryOperatorByParameter(SysOperatorQueryRequest sysOqr) {
		PageHelper.startPage(sysOqr.getPageNum(), sysOqr.getPageSize());
		List<OperatorResponse> queryOperatorListByParameter = sysOperatorMapper.queryOperatorListByParameter(sysOqr);
		PageInfo<OperatorResponse> pageInfo = new PageInfo<OperatorResponse>(queryOperatorListByParameter);
		return pageInfo;
	}

	@Override
	public OperatorResponse queryOperatorByLogincode(String logincode) {
		HashMap<String,Object> pmap = new HashMap<String,Object>();
		pmap.put("logincode",logincode);
		List<OperatorResponse> queryOperatorListByParameter = sysOperatorMapper.queryOperatorAllInfoByLogincode(pmap);
		//查询医疗权限
		Example example = new Example(SysArmariumOper.class);
		example.createCriteria().andEqualTo("logincode",logincode);
		List<SysArmariumOper> selectBe = sysArmariumOperMapper.selectByExample(example);
		//查询角色
		Example example2 = new Example(SysOperRole.class);
		example2.createCriteria().andEqualTo("logincode", logincode);
		List<SysOperRole> selectOr = sysOperRoleMapper.selectByExample(example2);
		if(queryOperatorListByParameter!=null&&queryOperatorListByParameter.size()>0){
			OperatorResponse operatorResponse = queryOperatorListByParameter.get(0);
			operatorResponse.setArmariums(selectBe);
			operatorResponse.setRoleIdd(selectOr);
			return operatorResponse;
		}
		return null;
	}
	
	private void addRoleOperater(SysOperator sysOperatorNew, String roleId) {
		String[] split2 = roleId.split(",");
		for (String rd : split2) {
			SysOperRole sysOperRole = new SysOperRole();
			SysRole selectByPrimaryKey = sysRoleMapper.selectByPrimaryKey(rd);
			if (selectByPrimaryKey == null) {
				throw new RmisException("角色不存在!");
			}
			sysOperRole.setLogincode(sysOperatorNew.getLogincode());
			sysOperRole.setRoleId(rd);
			sysOperRoleMapper.insertSelective(sysOperRole);
		}
		//通过角色给用户绑定权限
		sysRightModuleService.restartSysRightModule(sysOperatorNew.getLogincode());
	}

	private SysOperator queryOperatorIsOnlyOne(String id) {
		SysOperator sysOne = new SysOperator();
		// TODO 此处询问健民给我的是啥 最好是logincode
		sysOne.setId(id);
		List<SysOperator> sysOperators = sysOperatorMapper.select(sysOne);
//		SysOperator selectOne = sysOperatorMapper.selectOne(sysOne);
		if (sysOperators == null || sysOperators.size()==0) {
			throw new RmisException("用户不存在!");
		}
		if(sysOperators!=null && sysOperators.size()>1){
			throw new RmisException("用户有多个，请联系客服!");
		}
		return sysOperators.get(0);
	}

	@Override
	public String judgelogin(String logincode) {
		Example ex=new Example(SysOperator.class);
		ex.createCriteria().andEqualTo("logincode", logincode);
		List<SysOperator> sysOperators = sysOperatorMapper.selectByExample(ex);
		return CollectionUtils.isEmpty(sysOperators)?"0":"1";
	}


}
