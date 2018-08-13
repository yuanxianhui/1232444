package com.bolue.oa.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;

import com.bolue.oa.system.common.CommonConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyFilter implements Filter{
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MyFilter.class);
	
	protected static List<Pattern> patterns = new ArrayList<Pattern>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//初始化不过滤请求
		String exclusionStr = filterConfig.getInitParameter("EXCLUSIONS");
		if (exclusionStr != null && !exclusionStr.isEmpty()) {
			log.debug("exclusionStr:" + exclusionStr);
			String[] inputs = exclusionStr.split(",");
			for (String input : inputs) {
				String regex = input.trim().replace("*", "(.*)").replace("?", "(.{1})");
				Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
				if (pattern != null) {
					patterns.add(pattern);
				}
			}
		}
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		
		String webappContextPath = httpRequest.getContextPath();// WEB应用跟目录
		log.info("webappContextPath: "+webappContextPath);
		
		//取得cookie中保存的登陆信息
		String cookieUserId = null;
		Cookie[] coolies = httpRequest.getCookies();
		if (coolies != null) {
			for (Cookie cook : coolies) {
				if ("BSD_CHANNEL_USER_AUTH".equals(cook.getName())) {
					cookieUserId = cook.getValue();
				}
			}
		}
		
		//获取登陆session信息
		HttpSession session = httpRequest.getSession();
		
		String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		if (url.startsWith("/") && url.length() > 1) {
			url = url.substring(1);
		}else if(url.startsWith("/") && url.length() == 1){
			url = "login";
		}
		
		/*if(cookieUserId != null) {
			HttpSession session = httpRequest.getSession();
			//清空session
			session.removeAttribute("ACCOUNT_INFO_BOLUE");
			
			//请求验证登陆用户信息
			//session.getAttribute(CommonConstant.ACCOUNT_INFO_BOLUE);
			//设置session
			//session.setAttribute("qingqiu", "@@@@@@");
			
			httpResponse.sendRedirect(webappContextPath+"index");
			chain.doFilter(httpRequest, httpResponse);
			return;
		}*/
		//请求不受拦截
		if (isInclude(url)){
			//请求登陆
			if("login".equals(url) || "logincheck".equals(url)) {
				session.removeAttribute(CommonConstant.ACCOUNT_INFO_BOLUE);
			}
			chain.doFilter(httpRequest, httpResponse);
			return;
		}else {
			//请求受拦截
			if (session.getAttribute(CommonConstant.ACCOUNT_INFO_BOLUE) != null){
				// session存在
				chain.doFilter(httpRequest, httpResponse);
				return;
			}else {
				//session不存在 跳转登陆页面
				httpResponse.sendRedirect(webappContextPath+"login");
				//chain.doFilter(httpRequest, httpResponse);
				return;
			}
		}
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 是否需要过滤
	 * @param url
	 * @return
	 */
	private boolean isInclude(String url) {
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(url);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}

}
