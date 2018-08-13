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
import com.bolue.oa.system.entity.SysDepartment;
import com.bolue.oa.system.service.SysDepartmentService;
import com.bolue.oa.util.BaseResultCode;
import com.bolue.oa.util.ResultDO;

@Controller
@RequestMapping(value="/baseinfo/role/")
public class RoleController {

	@Autowired
	private Common common;
	@Autowired
	private SysDepartmentService sysDepartmentService;
	
	@ResponseBody
	@RequestMapping(value="remove")
	public ResultDO<String> remove(String departmentCode){
		ResultDO<String> result = new ResultDO<String>();
		try {
			result = sysDepartmentService.removeDepartmentInfo(departmentCode);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value="saveup")
	public ResultDO<String> saveup(SysDepartment data){
		ResultDO<String> result = new ResultDO<String>();
		try {
			if(StringUtils.isBlank(data.getDepartmentCode())
					||StringUtils.isBlank(data.getDepartmentName())
					||StringUtils.isBlank(data.getValidateFlag())) {
				return new ResultDO<String>(BaseResultCode.FORM_DATA, Boolean.FALSE);
			}
			
			result = sysDepartmentService.saveupDepartmentInfo(data);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="search")
	public List<SysDepartment> search(SysDepartment data){
		List<SysDepartment> list = new ArrayList<SysDepartment>();
		
		ResultDO<List<SysDepartment>> result = sysDepartmentService.getDepartmentInfosByForm(data);
		
		if(result.isSuccess()) {
			list = result.getModule();
		}
		
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="save")
	public ResultDO<String> save(SysDepartment data){
		ResultDO<String> result = new ResultDO<String>();
		try {
			if(StringUtils.isBlank(data.getDepartmentCode())
					||StringUtils.isBlank(data.getDepartmentName())
					||StringUtils.isBlank(data.getValidateFlag())) {
				return new ResultDO<String>(BaseResultCode.FORM_DATA, Boolean.FALSE);
			}
			
			result = sysDepartmentService.saveDepartmentInfo(data);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		return result;
	}
	
	@RequestMapping(value="edit")
	public String edit(Model model, String departmentCode) {
		
		ResultDO<SysDepartment> result = sysDepartmentService.getDepartmentInfoByCode(departmentCode);
		if(result.isSuccess()) {
			model.addAttribute("data", result.getModule());
		}
		
		ResultDO<Map<?, ?>> enums = common.getEnumInfos("validateFlag");
		if(enums.isSuccess()) {
			model.addAttribute("validateFlag", enums.getModule().get("validateFlag"));
		}
		
		return  "role/edit";
	}
	
	@RequestMapping(value="add")
	public String add(Model model) {
		
		ResultDO<Map<?, ?>> enums = common.getEnumInfos("validateFlag");
		if(enums.isSuccess()) {
			model.addAttribute("validateFlag", enums.getModule().get("validateFlag"));
		}
		
		return  "role/add";
	}
	
	@RequestMapping(value="list")
	public String list(Model model) {
		System.out.println("部门菜单");
		
		ResultDO<Map<?, ?>> enums = common.getEnumInfos("validateFlag");
		if(enums.isSuccess()) {
			model.addAttribute("validateFlag", enums.getModule().get("validateFlag"));
		}
		
		return "role/list";
	}
}
