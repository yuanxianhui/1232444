package com.bolue.oa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bolue.oa.common.Common;
import com.bolue.oa.system.entity.SysButton;
import com.bolue.oa.system.service.SysButtonService;
import com.bolue.oa.util.BaseResultCode;
import com.bolue.oa.util.ResultDO;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/baseinfo/button/")
public class ButtonController {

	@Autowired
	private Common common;
	@Autowired
	private SysButtonService sysButtonService;
	
	@ResponseBody
	@RequestMapping(value="remove")
	public ResultDO<String> remove(String buttonCode){
		ResultDO<String> result = new ResultDO<String>();
		try {
			result = sysButtonService.removeButtonInfo(buttonCode);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="search")
	public List<SysButton> search(SysButton data) {
		List<SysButton> list = new ArrayList<SysButton>();
		try {
			ResultDO<List<SysButton>> result = sysButtonService.getButtonInfos(data);
			if(result.isSuccess()) {
				list = result.getModule();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="saveup")
	public ResultDO<String> saveup(SysButton data){
		ResultDO<String> result = new ResultDO<String>();
		try {
			if(StringUtils.isBlank(data.getMenuCode()) 
					|| StringUtils.isBlank(data.getButtonCode())
					|| StringUtils.isBlank(data.getButtonName()) 
					|| StringUtils.isBlank(data.getButtonUrl()) 
					|| StringUtils.isBlank(data.getValidateFlag())) {
				return new ResultDO<String>(BaseResultCode.FORM_DATA, Boolean.FALSE);
			}
			result = sysButtonService.upButtonInfo(data);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="save")
	public ResultDO<String> save(SysButton data){
		ResultDO<String> result = new ResultDO<String>();
		try {
			if(StringUtils.isBlank(data.getMenuCode()) 
					|| StringUtils.isBlank(data.getButtonCode())
					|| StringUtils.isBlank(data.getButtonName()) 
					|| StringUtils.isBlank(data.getButtonUrl()) 
					|| StringUtils.isBlank(data.getValidateFlag())) {
				return new ResultDO<String>(BaseResultCode.FORM_DATA, Boolean.FALSE);
			}
			
			result = sysButtonService.saveButtonInfo(data);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		
		return result;
	}
	
	@RequestMapping(value="edit")
	public String edit(Model model, String buttonCode) {
		System.out.println("按钮菜单");
		if(StringUtils.isBlank(buttonCode)) {
			return "button/edit";
		}
		try {
			ResultDO<Map<?, ?>> enums = common.getEnumInfos("validateFlag");
			if(enums.isSuccess()) {
				model.addAttribute("validateFlag", enums.getModule().get("validateFlag"));
			}
			ResultDO<JSONObject> menuparent = common.getAllMenuInfos();
			if(menuparent.isSuccess()) {
				model.addAttribute("menuparent", menuparent.getModule());
			}
			
			ResultDO<SysButton> result = sysButtonService.getButtonInfoByButtonCode(buttonCode);
			if(result.isSuccess()) {
				model.addAttribute("data", result.getModule());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "button/edit";
	}
	
	@RequestMapping(value="add")
	public String add(Model model) {
		System.out.println("按钮菜单");
		ResultDO<Map<?, ?>> enums = common.getEnumInfos("validateFlag");
		if(enums.isSuccess()) {
			model.addAttribute("validateFlag", enums.getModule().get("validateFlag"));
		}
		ResultDO<JSONObject> menuparent = common.getAllMenuInfos();
		if(menuparent.isSuccess()) {
			model.addAttribute("menuparent", menuparent.getModule());
		}
		
		return "button/add";
	}
	
	@RequestMapping(value="list")
	public String list(Model model) {
		System.out.println("按钮菜单");
		ResultDO<Map<?, ?>> enums = common.getEnumInfos("validateFlag");
		if(enums.isSuccess()) {
			model.addAttribute("validateFlag", enums.getModule().get("validateFlag"));
		}
		ResultDO<JSONObject> menuparent = common.getAllMenuInfos();
		if(menuparent.isSuccess()) {
			model.addAttribute("menuparent", menuparent.getModule());
		}
		return "button/list";
	}
}
