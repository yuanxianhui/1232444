package com.bolue.oa.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyIntercept implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Boolean flg = true;
		// 如果已经登录返回true。
		// 如果没有登录没有登录，可以使用 reponse.send() 跳转页面。后面要跟return false,否则无法结束;

		// 为了测试，打印一句话
		System.out.println("拦截器01");
		
		try {
			Subject subject = SecurityUtils.getSubject();
			String url = request.getRequestURI().substring(request.getContextPath().length());
			url = url.substring(1);
			
			System.out.println("url:"+url);
			
			subject.checkPermissions(url);
			
		} catch(UnauthorizedException ue) {
			flg = false;
			System.out.println(ue.getMessage());
		} catch(Exception e) {
			flg = false;
			System.out.println(e.getMessage());
		}
		
		if(!flg) {
			response.sendRedirect(request.getContextPath() + "/login");
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("拦截器02");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("拦截器03");
	}
}
