package com.bolue.oa.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bolue.oa.common.Common;
import com.bolue.oa.system.entity.SysAccount;
import com.bolue.oa.system.service.SysAccountService;
import com.bolue.oa.util.ResultDO;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/baseinfo/user/")
public class UserController {

	@Autowired
	private Common common;
	@Autowired
	private SysAccountService sysAccountService;
	
	@RequestMapping(value="add")
	public String add(Model model) {
		System.out.println("员工新增");
		
		ResultDO<List<SysAccount>> accountResult = sysAccountService.getAccounts();
		if(accountResult.isSuccess()) {
			model.addAttribute("accounts", accountResult.getModule());
		}
		ResultDO<Map<?, ?>> enuResult = common.getEnumInfos(null);
		if (enuResult.isSuccess()) {
			model.addAttribute("sexFlag", enuResult.getModule().get("sexFlag"));
			model.addAttribute("staffFlgs", enuResult.getModule().get("staffFlag"));
		}
		ResultDO<JSONObject> pac = common.getProvince();
		if (pac.isSuccess()) {
			model.addAttribute("province", pac.getModule().get("province"));
		}
		
		return "user/add";
	}
	
	@RequestMapping(value="list")
	public String list() {
		System.err.println("员工基本信息");
		
		return "user/list";
	}
}
