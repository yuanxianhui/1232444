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
import com.bolue.oa.entity.enumer.SysEnumerate;
import com.bolue.oa.entity.enumer.SysEnumerateDetailed;
import com.bolue.oa.entity.enumer.SysEnumerateKey;
import com.bolue.oa.service.SysEnumerateDetailedService;
import com.bolue.oa.service.SysEnumerateService;
import com.bolue.oa.util.BaseResultCode;
import com.bolue.oa.util.ResultDO;

@Controller
@RequestMapping(value="/baseinfo/enum/")
public class EnumerController {

	@Autowired
	private SysEnumerateService sysEnumerateService;
	@Autowired
	private SysEnumerateDetailedService sysEnumerateDetailedService;
	@Autowired
	private Common common;

	@ResponseBody
	@RequestMapping(value="removed")
	public ResultDO<String> removed(SysEnumerateDetailed data){
		ResultDO<String> result = new ResultDO<String>();
		try {
			result = sysEnumerateDetailedService.removeEnudInfo(data);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="remove")
	public ResultDO<String> remove(String enumCode){
		ResultDO<String> result = new ResultDO<String>();
		try {
			result = sysEnumerateService.removeEnuInfo(enumCode);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="savedup")
	public ResultDO<String> savedup(SysEnumerateDetailed data){
		ResultDO<String> result = new ResultDO<String>();
		try {
			if (StringUtils.isBlank(data.getEunmCode()) || 
					StringUtils.isBlank(data.getCode()) || 
					StringUtils.isBlank(data.getName()) || 
					StringUtils.isBlank(data.getValidateFlag())) {
				return new ResultDO<String>(BaseResultCode.FORM_DATA, Boolean.FALSE); 
			}
			result = sysEnumerateDetailedService.saveupEnudInfo(data);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE); 
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="saveup")
	public ResultDO<String> saveup(SysEnumerate data){
		ResultDO<String> result = new ResultDO<String>();
		try {
			if (StringUtils.isBlank(data.getEnumCode()) || 
					StringUtils.isBlank(data.getEnumName()) || 
					StringUtils.isBlank(data.getValidateFlag())) {
				return new ResultDO<String>(BaseResultCode.FORM_DATA, Boolean.FALSE); 
			}
			result = sysEnumerateService.saveupEnuInfo(data);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE); 
		}
		
		return result;
	}
	

	@ResponseBody
	@RequestMapping(value="saved")
	public ResultDO<String> saved(SysEnumerateDetailed data){
		ResultDO<String> result = new ResultDO<String>();
		try {
			if (StringUtils.isBlank(data.getEunmCode()) || 
					StringUtils.isBlank(data.getCode()) || 
					StringUtils.isBlank(data.getName()) || 
					StringUtils.isBlank(data.getValidateFlag())) {
				return new ResultDO<String>(BaseResultCode.FORM_DATA, Boolean.FALSE); 
			}
			
			result = sysEnumerateDetailedService.saveEnudInfo(data);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE); 
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="save")
	public ResultDO<String> save(SysEnumerate data){
		ResultDO<String> result = new ResultDO<String>();
		try {
			if (StringUtils.isBlank(data.getEnumCode()) || 
					StringUtils.isBlank(data.getEnumName()) || 
					StringUtils.isBlank(data.getValidateFlag())) {
				return new ResultDO<String>(BaseResultCode.FORM_DATA, Boolean.FALSE); 
			}
			
			result = sysEnumerateService.saveEnuInfo(data);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE); 
		}
		
		return result;
	}

	@ResponseBody
	@RequestMapping(value="searchD")
	public List<SysEnumerateDetailed> searchD(String enumCode, String code){
		List<SysEnumerateDetailed> list = new ArrayList<SysEnumerateDetailed>();
		ResultDO<List<SysEnumerateDetailed>> result = sysEnumerateDetailedService.getEnuDInfos(enumCode, code);
		if(result.isSuccess()) {
			list = result.getModule();
		}
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="search")
	public List<SysEnumerate> search(String enumCode){
		List<SysEnumerate> list = new ArrayList<SysEnumerate>();
		SysEnumerate data = new SysEnumerate();
		data.setEnumCode(enumCode);
		ResultDO<List<SysEnumerate>> result = sysEnumerateService.getEnuInfos(data);
		if(result.isSuccess()) {
			list = result.getModule();
		}
		return list;
	}
	
	@RequestMapping(value="editd")
	public String editd(Model model, String enumCode, String code) {
		ResultDO<Map<?, ?>> enums = common.getEnumInfos("validateFlag");
		if(enums.isSuccess()) {
			model.addAttribute("validateFlag", enums.getModule().get("validateFlag"));
		}
		SysEnumerateDetailed info = sysEnumerateDetailedService.getEnudInfo(enumCode, code);
		if(info != null) {
			model.addAttribute("data", info);
		}
		return "enumer/editd";
	}
	
	@RequestMapping(value="edit")
	public String edit(Model model, String enumCode) {
		ResultDO<Map<?, ?>> enums = common.getEnumInfos("validateFlag");
		if(enums.isSuccess()) {
			model.addAttribute("validateFlag", enums.getModule().get("validateFlag"));
		}
		SysEnumerate info = sysEnumerateService.getEnuInfo(enumCode);
		if(info != null) {
			model.addAttribute("data", info);
		}
		return "enumer/edit";
	}
	
	@RequestMapping(value="addd")
	public String addd(Model model, String enumCode) {
		ResultDO<Map<?, ?>> enums = common.getEnumInfos("validateFlag");
		if(enums.isSuccess()) {
			model.addAttribute("validateFlag", enums.getModule().get("validateFlag"));
		}
		model.addAttribute("enumCode", enumCode);
		
		return "enumer/addd";
	}
	
	@RequestMapping(value="add")
	public String add(Model model) {
		ResultDO<Map<?, ?>> enums = common.getEnumInfos("validateFlag");
		if(enums.isSuccess()) {
			model.addAttribute("validateFlag", enums.getModule().get("validateFlag"));
		}
		return "enumer/add";
	}
	
	@RequestMapping(value="list")
	public String list(Model model) {
		
		return "/enumer/list";
	}
	
	@RequestMapping(value="popup")
	public String popup() {
		System.out.println("909090");
		return "enumer/popup";
	}
}
