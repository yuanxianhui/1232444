package com.bolue.oa.system;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc配置类
 * 
 * @author yuanx
 *
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

	/**
	 * 自定义拦截器
	 * 
	 * @return
	 */
	@Bean
	MyIntercept myIntercept() {
		return new MyIntercept();
	}

	/**
	 * 添加拦截器
	 * 
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 添加一个拦截器
		registry.addInterceptor(myIntercept()).excludePathPatterns(Arrays.asList("/login/**", "/index", "/home", "/menu", "/logout", "/register/**", "/css/**", "/hplus/**", "/img/**", "/js/**", "/favicon.ico"));
	}

/*	@Bean
	public FilterRegistrationBean delegatingFilterProxy() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		DelegatingFilterProxy proxy = new DelegatingFilterProxy();
		proxy.setTargetFilterLifecycle(true);
		proxy.setTargetBeanName("shirFilter");
		filterRegistrationBean.setFilter(proxy);
		return filterRegistrationBean;
	}*/
}
