package com.bolue.oa.system.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bolue.oa.system.common.CommonConstant;
import com.bolue.oa.system.common.UserInfo;
import com.bolue.oa.system.entity.SysAccount;
import com.bolue.oa.system.entity.SysDepartment;
import com.bolue.oa.system.entity.SysMenu;
import com.bolue.oa.system.entity.SysUser;
import com.bolue.oa.system.service.SysAccountService;
import com.bolue.oa.system.service.SysDepartmentService;
import com.bolue.oa.system.service.SysMenuService;
import com.bolue.oa.system.service.SysUserService;
import com.bolue.oa.util.BaseResultCode;
import com.bolue.oa.util.ResultDO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {

	@Autowired
	private SysAccountService sysAccountService;
	@Autowired
	private SysUserService SysUserService;
	@Autowired
	private SysDepartmentService sysDepartmentService;
	@Autowired
	private SysMenuService sysMenuService;
	/**
	 * 请求登陆页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		log.info("请求登陆页面");
		// 设置后续请求url的相对路径
		session.setAttribute(CommonConstant.CXT, request.getServletContext().getContextPath());

		model.addAttribute("le", "乐乐");
		return "login";
	}
	
	/**
	 * 请求登陆
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login/logincheck")
	public ResultDO<String> logincheck(@RequestParam("account") String account,
			@RequestParam("password") String password) {
		log.info("请求登陆");
		ResultDO<String> result = new ResultDO<String>();
		
		if (StringUtils.isBlank(account)) {
			return new ResultDO<>(BaseResultCode.LOGIN_ACCOUNT, Boolean.FALSE);
		}
		if (StringUtils.isEmpty(password)) {
			return new ResultDO<>(BaseResultCode.LOGIN_PASSWORD, Boolean.FALSE);
		}

		UsernamePasswordToken token = new UsernamePasswordToken(account, password);
		Subject subject = SecurityUtils.getSubject();

		try {
			subject.login(token);
		} catch (IncorrectCredentialsException ice) {
			// 捕获密码错误异常
			return new ResultDO<>(BaseResultCode.LOGIN_WARN, Boolean.FALSE);
		} catch (UnknownAccountException uae) {
			// 捕获未知用户名异常
			return new ResultDO<>(BaseResultCode.LOGIN_WARN, Boolean.FALSE);
		} catch (ExcessiveAttemptsException eae) {
			// 捕获错误登录过多的异常
			return new ResultDO<>(BaseResultCode.LOGIN_MANY, Boolean.FALSE);
		} catch (LockedAccountException le) {
			// 捕捉账号状态异常
			result.setErrorMessage(le.getMessage());
			result.setSuccess(Boolean.FALSE);
			return result;
		} catch (Exception e) {
			// 捕捉账号状态异常
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}

		return result;
	}

	/**
	 * 请求主页面
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		log.info("请求主页面");
		Subject subject = SecurityUtils.getSubject();
		
		String account = (String)subject.getPrincipal();
		
		//subject.hasRole(account);
		subject.isPermitted(account);
		
		//subject.hasRole(account);
		
		// 根据登陆账号获取对应的员工、菜单信息，及相关信息。
		SysAccount accountInfo = (SysAccount) session.getAttribute(CommonConstant.ACCOUNT_INFO_BOLUE);

		return "index";
	}

	/**
	 * 请求主页面显示内容
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "home")
	public String home(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
		log.info("请求home页面");
		try {
			Subject subject = SecurityUtils.getSubject();
			String account = (String)subject.getPrincipal();
			List<SysDepartment> deparList = sysDepartmentService.findRoles(account);
			Set<String> departments = new HashSet<String>();
			for(SysDepartment sysDepartment:deparList) {
				String str = sysDepartment.getDepartmentCode();
				if(StringUtils.isNotBlank(str)) {
					departments.add(str);
				}
				
			}
			subject.checkRoles(departments);
			
			// 依据当前登陆人显示相对应的内容
			model.addAttribute("user", account);
			//subject.checkPermissions(user.getUserCode());
		}catch(UnauthorizedException ue) {
			//请求角色在shiro中不存在该角色
			log.info(ue.getMessage());
		}
		
		return "home";
	}

	/**
	 * 请求meun菜单
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "menu")
	public ResultDO<String> meun() {
		log.info("请求菜单");
		ResultDO<String> result = new ResultDO<String>();
		try {
			Subject subject = SecurityUtils.getSubject();
			String account = (String)subject.getPrincipal();
			result = sysMenuService.getMenuInfo(account);
		}catch(Exception e){
			e.printStackTrace();
			result.setErrorMessage(BaseResultCode.COMMON_FAIL);
			result.setSuccess(Boolean.FALSE);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="user/init")
	public String init() {
		System.out.println("请求@@@@@@@@@@");
		return "";
	}
}
