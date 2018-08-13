package com.bolue.oa.system;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * 使用shiro安全验证框架
 * @author yuanx
 *
 */
@Configuration
public class ShiroConfiguration {
	
	/**
	 * ShiroFilterFactoryBean 处理拦截资源文件问题。
	 * 注意：若ShiroFilterFactoryBean未初始化SecurityManager出现错误信息。
	 * 
	 * Filter Chain定义说明：
	 * 1、一个URL可以配置多个Filter，使用逗号分隔。
	 * 2、当设置多个过滤器时，全部验证通过，才视为通过。
	 * 3、部分过滤器可指定参数，如perms，roles.
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();
		
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//指定登陆url
		shiroFilterFactoryBean.setLoginUrl("/login");
		//登陆成功跳转url
		shiroFilterFactoryBean.setSuccessUrl("/index");
		//未授权跳转url
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		//请求url配置
		//roles[admin],即:需要用户有用admin权限
		//authc,即:用户必须登录才能访问
		//anon,即:任何人都可以访问
		Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/css/**","anon");
		filterChainDefinitionMap.put("/hplus/**","anon");
		filterChainDefinitionMap.put("/img/**","anon");
		filterChainDefinitionMap.put("/js/**","anon");
		filterChainDefinitionMap.put("/favicon.ico", "anon");
		filterChainDefinitionMap.put("/login/**","anon");
		filterChainDefinitionMap.put("/register/**","anon");
		filterChainDefinitionMap.put("/**", "authc");
		
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}
	
	/**
	 * 注入shiro管理器
	 * @return
	 */
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		//设置realm.
		securityManager.setRealm(boShiroRealm());
		return securityManager;
	}
	
	/**
	 * 注入自定义身份认证realm.
	 * 这个需要自己写，账号密码校验；权限等
	 * @return
	 */
	@Bean
	public BoShiroRealm boShiroRealm(){
		BoShiroRealm boShiroRealm = new BoShiroRealm();
		boShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return boShiroRealm;
	}
	
	/**
	 * 凭证匹配器
	 * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
	 *  所以我们需要修改下doGetAuthenticationInfo中的代码;
	 * ）
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher(){
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(1);//散列的次数，比如散列两次，相当于 md5(md5(""));
		hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);//storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
		return hashedCredentialsMatcher;
	}
	
	/**
	 * 开启shiro aop注解支持.
	 * 使用代理方式;所以需要开启代码支持;
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
	
	/**
	 * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
	 * @return
	 */
	/*@Bean
	public ShiroDialect shiroDialect(){
		return new ShiroDialect();
	}*/
	
	/**
	 * 管理shiro bean生命周期
	 * @return
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}
	
	/**
	 * 实现spring的自动代理
	 * @return
	 */
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
		proxyCreator.setProxyTargetClass(true); // this SETTING
		return proxyCreator;
	}
	
	/**
	 * 
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager());
		return advisor;
	}
}
