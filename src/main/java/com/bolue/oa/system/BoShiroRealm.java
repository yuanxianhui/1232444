package com.bolue.oa.system;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.bolue.oa.system.entity.SysAccount;
import com.bolue.oa.system.entity.SysButton;
import com.bolue.oa.system.entity.SysDepartment;
import com.bolue.oa.system.entity.SysMenu;
import com.bolue.oa.system.service.SysAccountService;
import com.bolue.oa.system.service.SysDepartmentService;
import com.bolue.oa.system.service.SysMenuService;
import com.bolue.oa.system.service.SysButtonService;

public class BoShiroRealm extends AuthorizingRealm {

	@Autowired
	private SysAccountService sysAccountService;
	@Autowired
	private SysDepartmentService sysDepartmentService;
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysButtonService sysButtonService;
	
	/**
	 * AuthorizationInfo代表了角色的权限信息集合
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//获取用户的信息
		String accountCode = (String)principals.getPrimaryPrincipal();
		//返回的对象
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		
		//根据账号获取角色集合
		List<SysDepartment> sysDepartments = sysDepartmentService.findRoles(accountCode);
		Set<String> roleNames = new HashSet<String>();
		for(SysDepartment sysDepartment:sysDepartments) {
			String DepartmentCode = sysDepartment.getDepartmentCode();
			if(StringUtils.isNotBlank(DepartmentCode)) {
				roleNames.add(DepartmentCode);
			}
		}
		// 将角色名称提供给info
		authorizationInfo.setRoles(roleNames);
		
		//根据角色集合获取角色对应的权限集合
		List<SysMenu> sysMenus = sysMenuService.findPermissions(accountCode);
		Set<String> permissionNames = new HashSet<String>();
		for(SysMenu sysMenu:sysMenus) {
			//存储按钮对应的url
			String urls = sysMenu.getMenuUrl();
			String code = sysMenu.getMenuCode();
			if(StringUtils.isNotBlank(code) && StringUtils.isNotBlank(urls)) {
				//截取url
				String[] urlarray = urls.split(",");
				for(String url:urlarray) {
					if(StringUtils.isNotBlank(url)) {
						permissionNames.add(url);
					}
				}
			}
		}
		List<SysButton> sysButtons = sysButtonService.findButtonPermissions(accountCode);
		for(SysButton sysButton:sysButtons) {
			//存储按钮对应的url
			String urls = sysButton.getButtonUrl();
			String code = sysButton.getButtonCode();
			if(StringUtils.isNotBlank(code) && StringUtils.isNotBlank(urls)) {
				//截取url
				String[] urlarray = urls.split(",");
				for(String url:urlarray) {
					if(StringUtils.isNotBlank(url)) {
						permissionNames.add(url);
					}
				}
			}
		}
		
		// 将权限名称提供给info
		authorizationInfo.setStringPermissions(permissionNames);
		
		return authorizationInfo;
	}

	/**
	 * AuthenticationInfo代表了用户的角色信息集合
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//获取用户的输入的账号
		String accountCode = (String)token.getPrincipal();
		
		//根据用户名去数据库查询是否有该用户
		SysAccount accountInfo = sysAccountService.findByAccount(accountCode);
		
		if (accountInfo == null) {
			// 用户名不存在抛出异常
			throw new UnknownAccountException();
		}
		if (accountInfo.getLocked() != 0) {
			// 用户被管理员锁定抛出异常
			throw new LockedAccountException("该账号已被锁定，请联系管理员！");
		}
		
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				accountInfo.getAccountCode(), //用户名
				accountInfo.getAccountKey(), //密码
				getName() //realm name
				);
		//doGetAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
		return authenticationInfo;
	}
}

