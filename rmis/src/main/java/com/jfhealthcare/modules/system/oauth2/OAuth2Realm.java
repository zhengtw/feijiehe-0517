package com.jfhealthcare.modules.system.oauth2;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.modules.system.entity.SysOperator;
import com.jfhealthcare.modules.system.mapper.SysOperatorMapper;
import com.jfhealthcare.modules.system.service.ShiroService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

/**
 * 认证
 * @author xujinma
 */
@Slf4j
@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private ShiroService shiroService;
    
    @Autowired
    private SysOperatorMapper sysOperatorMapper;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 授权(验证权限时调用)
     * 占时用不到  后期做方法权限 再扩展
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	SysOperator user = (SysOperator)principals.getPrimaryPrincipal();
    	String logincode = user.getLogincode();

        //用户权限列表
        Set<String> permsSet = shiroService.getUserPermissions(logincode);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)  {
    	
    	SimpleAuthenticationInfo authenticationInfo;
		UsernamePasswordToken shiroToken = (UsernamePasswordToken)authenticationToken;
		
		String username = shiroToken.getUsername();
		String password = String.valueOf(shiroToken.getPassword());
		
		SysOperator sysOperator=new SysOperator();
		sysOperator.setLogincode(username);
		sysOperator.setPassword(password);
		List<SysOperator> sysOperators = sysOperatorMapper.select(sysOperator);
		if(sysOperators.isEmpty()){
			throw new AccountException("帐号或密码不正确！");
		}else{
			if(sysOperators.size()>1){
				throw new AccountException("帐号重复，请联系运维人员！！");
			}else{
				SysOperator sop = sysOperators.get(0);
				if(!sop.getStatus() ||  sop.getIsdelete()){
					throw new DisabledAccountException("此帐号已经设置为禁止登录！");
				}
			}
		}
		
		authenticationInfo = new SimpleAuthenticationInfo(username,password, getName());
        return authenticationInfo;
    }
}
