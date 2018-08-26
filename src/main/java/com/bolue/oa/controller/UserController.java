package com.bolue.oa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bolue.oa.common.Common;
import com.bolue.oa.entity.user.SysUserDto;
import com.bolue.oa.system.entity.SysAccount;
import com.bolue.oa.system.entity.SysUser;
import com.bolue.oa.system.service.SysAccountService;
import com.bolue.oa.system.service.SysUserService;
import com.bolue.oa.util.BaseResultCode;
import com.bolue.oa.util.ResultDO;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/baseinfo/user/")
public class UserController {

	@Autowired
	private Common common;
	@Autowired
	private SysAccountService sysAccountService;
	@Autowired
	private SysUserService sysUserService;
	
	@ResponseBody
	@RequestMapping(value="saveup")
	public ResultDO<String> saveup(SysUserDto data){
		ResultDO<String> result = new ResultDO<String>();
		
		try {
			result = sysUserService.saveupUserInfo(data);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="remove")
	public ResultDO<String> remove(SysUserDto data){
		ResultDO<String> result = new ResultDO<String>();
		try {
			result = sysUserService.removeUserInfo(data);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		
		return  result;
	}
	
	@ResponseBody
	@RequestMapping(value="search")
	public List<SysUser> search(SysUserDto data){
		List<SysUser> list = new ArrayList<SysUser>();
		ResultDO<List<SysUser>> result = sysUserService.getUserInfos(data);
		if(result.isSuccess()) {
			list = result.getModule();
		}
		
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="save")
	public ResultDO<String> save(SysUserDto data) {
		ResultDO<String> result = new ResultDO<String>();
		try {
			if (StringUtils.isBlank(data.getAccountCode()) 
					|| StringUtils.isBlank(data.getUserCode()) 
					|| StringUtils.isBlank(data.getUserName()) 
					|| StringUtils.isBlank(data.getUserSex()) 
					|| StringUtils.isBlank(data.getEntryTime()) 
					|| StringUtils.isBlank(data.getUserCardId()) 
					|| StringUtils.isBlank(data.getAddress()) 
					|| StringUtils.isBlank(data.getPhoneNumber())) {
				return new ResultDO<String>(BaseResultCode.FORM_DATA, Boolean.FALSE);
			}
			
			result = sysUserService.saveUserInfo(data);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		
		return result;
	}
	
	@RequestMapping(value="edit")
	public String edit(Model model, String userCode) {
		
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
		
		ResultDO<SysUser> userInfo = sysUserService.getUserInfo(userCode);
		if (userInfo.isSuccess()) {
			model.addAttribute("data", userInfo.getModule());
		}
		
		return "user/edit";
	}
	
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
	public String list(Model model) {
		System.err.println("员工基本信息");
		ResultDO<Map<?, ?>> enuResult = common.getEnumInfos(null);
		if (enuResult.isSuccess()) {
			model.addAttribute("staffFlgs", enuResult.getModule().get("staffFlag"));
		}
		return "user/list";
	}
}
