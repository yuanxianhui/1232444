package com.bolue.oa.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义过滤器
 * @author yuanx
 *
 */
//@Configuration //TODO 去掉//启用自定义过滤器
public class MyFilterConfiguration {
	/**
	 * 配置过滤器
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		List<String> urlPatterns = new ArrayList<String>();

		// 设置需要过滤的url
		urlPatterns.add("/*");

		registration.setFilter(myFilter());
		// registration.addUrlPatterns("/*");
		registration.setUrlPatterns(urlPatterns);
		registration.addInitParameter("EXCLUSIONS",
				"login,logincheck,druid/*,hplus/*,*.js,*.gif,*.jpg,*.png,*.css,*.ico");
		registration.setName("myFilter");

		return registration;
	}

	/**
	 * 创建Filter bean
	 * 
	 * @return
	 */
	@Bean
	public Filter myFilter() {
		return new MyFilter();
	}
	
}
