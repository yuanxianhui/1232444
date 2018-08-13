package com.bolue.oa.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bolue.oa.service.RegisterService;
import com.bolue.oa.system.entity.SysAccount;
import com.bolue.oa.util.BaseResultCode;
import com.bolue.oa.util.ResultDO;

@Controller
@RequestMapping(value="/register/")
public class RegisterController {

	@Autowired
	private RegisterService registerService;
	
	/**
	 * 初始化注册界面
	 * @return
	 */
	@RequestMapping(value="list")
	public String list() {
		System.out.println("@@@@@@@@");
		return "register/register";
	}
	
	/**
	 * 保存注册信息
	 * @param account
	 * @param password
	 * @param passwordPare
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="add")
	public ResultDO<String> add(@RequestParam("account")String account, @RequestParam("password")String password, @RequestParam("passwordPare")String passwordPare) {
		ResultDO<String> result = new ResultDO<String>();
		if(StringUtils.isBlank(account)) {
			return new ResultDO<String>(BaseResultCode.LOGIN_ACCOUNT, Boolean.FALSE);
		}
		if(StringUtils.isBlank(password)) {
			return new ResultDO<String>(BaseResultCode.LOGIN_PASSWORD, Boolean.FALSE);
		}
		if(!password.equals(passwordPare)) {
			return new ResultDO<String>(BaseResultCode.DIFF_PASSWORD, Boolean.FALSE);
		}
		
		SysAccount accountInfo = new SysAccount();
		accountInfo.setAccountCode(account);
		accountInfo.setAccountKey(password);
		
		try {
			result = registerService.addAccount(accountInfo);
		}catch(Exception e){
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		return result;
	}
}
