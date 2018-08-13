package com.bolue.oa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bolue.oa.system.entity.SysMenu;
import com.bolue.oa.system.service.SysMenuService;
import com.bolue.oa.common.Common;
import com.bolue.oa.service.SysEnumerateService;
import com.bolue.oa.util.BaseResultCode;
import com.bolue.oa.util.ResultDO;

@Controller
@RequestMapping(value="/baseinfo/menu/")
public class MenuController {

	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private SysEnumerateService sysEnumerateService;
	@Autowired
	private Common common;
	
	@RequestMapping(value="list")
	public String list(Model model) {
		System.out.println("菜单");
		ResultDO<Map<?, ?>> resutl = common.getEnumInfos("validateFlag");
		if(resutl.isSuccess()) {
			model.addAttribute("validateFlag", resutl.getModule().get("validateFlag"));
		}
		return "menu/list";
	}
	
	@RequestMapping(value="add")
	public String add(Model model) {
		System.out.println("菜单");
		ResultDO<Map<?, ?>> resutl = common.getEnumInfos("validateFlag");
		if(resutl.isSuccess()) {
			model.addAttribute("validateFlag", resutl.getModule().get("validateFlag"));
		}
		ResultDO<Set<String>> menu = common.getMenuInfos();
		if(menu.isSuccess()) {
			model.addAttribute("menu", menu.getModule());
		}
		return "menu/add";
	}
	
	@RequestMapping(value="edit")
	public String edit(Model model, String menuCode) {
		System.out.println("编辑");
		SysMenu result = sysMenuService.getMenuInfoByMenuCode(menuCode);
		if(result == null) {
			model.addAttribute("data", new SysMenu());
		}else {
			model.addAttribute("data", result);
		}
		ResultDO<Map<?, ?>> resutl = common.getEnumInfos("validateFlag");
		if(resutl.isSuccess()) {
			model.addAttribute("validateFlag", resutl.getModule().get("validateFlag"));
		}
		ResultDO<Set<String>> menu = common.getMenuInfos();
		if(menu.isSuccess()) {
			model.addAttribute("menu", menu.getModule());
		}
		return "menu/edit";
	}
	
	@ResponseBody
	@RequestMapping(value="save")
	public ResultDO<String> save(SysMenu data) {
		System.out.println("菜单");
		ResultDO<String> result = new ResultDO<String>();
		try {
			if(StringUtils.isBlank(data.getMenuCode()) 
					|| StringUtils.isBlank(data.getMenuName())
					|| data.getSerialNumber() == null
					|| StringUtils.isBlank(data.getIconClass())
					|| StringUtils.isBlank(data.getMenuFlag())
					|| StringUtils.isBlank(data.getMenuParent())) {
				
				result.setErrorMessage("必填项不可以为空！");
				result.setSuccess(Boolean.FALSE);
				return result;
			}
			result = sysMenuService.addMenuInfo(data);
		}catch(Exception e){
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="saveup")
	public ResultDO<String> saveup(SysMenu data) {
		System.out.println("更新保存");
		ResultDO<String> result = new ResultDO<String>();
		try {
			if(StringUtils.isBlank(data.getMenuCode()) 
					|| StringUtils.isBlank(data.getMenuName())
					|| data.getSerialNumber() == null
					|| StringUtils.isBlank(data.getIconClass())
					|| StringUtils.isBlank(data.getMenuFlag())
					|| StringUtils.isBlank(data.getMenuParent())) {
				
				result.setErrorMessage("必填项不可以为空！");
				result.setSuccess(Boolean.FALSE);
				return result;
			}
			result = sysMenuService.editMenuInfo(data);
		}catch(Exception e){
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="remove")
	public ResultDO<String> remove(String menuCode) {
		System.out.println("菜单");
		ResultDO<String> result = new ResultDO<String>();
		try {
			result = sysMenuService.removeMenuInfo(menuCode);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="search")
	public List<SysMenu> search(SysMenu data) {
		System.out.println("菜单");
		ResultDO<List<SysMenu>> result = new ResultDO<List<SysMenu>>();
		List<SysMenu> list = new ArrayList<SysMenu>();
		try {
			result = sysMenuService.searchMenuIfos(data);
			if(result.getModule() != null) {
				list = result.getModule();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
