package com.bolue.oa.common;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.bolue.oa.entity.enumer.SysEnumerate;
import com.bolue.oa.entity.enumer.SysEnumerateDetailed;
import com.bolue.oa.mapper.enumer.SysEnumerateDetailedMapper;
import com.bolue.oa.mapper.enumer.SysEnumerateMapper;
import com.bolue.oa.system.dto.AreasMapper;
import com.bolue.oa.system.dto.CitiesMapper;
import com.bolue.oa.system.dto.ProvincesMapper;
import com.bolue.oa.system.dto.SysMenuMapper;
import com.bolue.oa.system.entity.Areas;
import com.bolue.oa.system.entity.Cities;
import com.bolue.oa.system.entity.Provinces;
import com.bolue.oa.system.entity.SysMenu;
import com.bolue.oa.util.ResultDO;

import net.sf.json.JSONObject;

@Service
public class CommonData implements Common {
	@Autowired
	private SysEnumerateMapper SysEnumerateMapper;
	@Autowired
	private SysEnumerateDetailedMapper SysEnumerateDetailedMapper;
	@Autowired
	private SysMenuMapper sysMenuMapper;
	@Autowired
	private ProvincesMapper provincesMapper;
	@Autowired
	private AreasMapper areasMapper;
	@Autowired
	private CitiesMapper citiesMapper;
	
	/**
	 * 根据枚举类型编号获取枚举详细信息集合
	 * @param enumCode
	 * @return
	 */
	@Override
	public ResultDO<Map<?, ?>> getEnumInfos(String enumCode) {
		ResultDO<Map<?, ?>> result = new ResultDO<Map<?, ?>>();
		try {
			List<SysEnumerate> parents = SysEnumerateMapper.selectEnumsByEnumCode(enumCode);
			List<SysEnumerateDetailed> subs = SysEnumerateDetailedMapper.selectEnumsByEnumCode(enumCode);
			Map<String, Map<?, ?>> parentmap = new HashMap<String, Map<?, ?>>();
			for(SysEnumerate parent:parents) {
				JSONObject jsonobj = new JSONObject();
				for(SysEnumerateDetailed sub:subs) {
					if(parent.getEnumCode().equals(sub.getEunmCode())) {
						jsonobj.put(sub.getCode(), sub.getName());
					}
				}
				parentmap.put(parent.getEnumCode(), jsonobj);
			}
			
			result.setModule(parentmap);
			result.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(Boolean.FALSE);
		}
		
		return result;
	}

	/**
	 * 获取所有有效的父菜单信息
	 * @return
	 */
	@Override
	public ResultDO<Set<String>> getMenuInfos() {
		ResultDO<Set<String>> result = new ResultDO<Set<String>>();
		try {
			Subject subject = SecurityUtils.getSubject();
			String accountCode = (String)subject.getPrincipal();
			List<String> menus = sysMenuMapper.selectParentByAccountCode(accountCode);
			Set<String> list = new HashSet<String>();
			for(String menu:menus) {
				if(StringUtils.isNotBlank(menu)) {
					list.add(menu);
				}
			}
			result.setModule(list);
			result.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(Boolean.FALSE);
		}
		
		return result;
	}

	/**
	 * 获取所有有效的菜单信息
	 * @return
	 */
	@Override
	public ResultDO<JSONObject> getAllMenuInfos() {
		ResultDO<JSONObject> result = new ResultDO<JSONObject>();
		try {
			List<SysMenu> menus = sysMenuMapper.selectAllMenu();
			JSONObject jsonobj = new JSONObject();
			for(SysMenu menu:menus) {
				if(StringUtils.isNotBlank(menu.getMenuCode())) {
					jsonobj.put(menu.getMenuCode(), menu.getMenuName());
				}
			}
			result.setModule(jsonobj);
			result.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(Boolean.FALSE);
		}
		
		return result;
	}

	/**
	 * 获取所有省信息
	 * @return
	 */
	@Override
	public ResultDO<JSONObject> getProvince() {
		ResultDO<JSONObject> result = new ResultDO<JSONObject>();
		try {
			List<Provinces> provinces = provincesMapper.selectByPrimaryKey(new Provinces());
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("province", JSONArray.toJSON(provinces));
			
			result.setModule(jsonobj);
			result.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(Boolean.FALSE);
		}
		
		return result;
	}

	/**
	 * 获取所有区信息
	 * @return
	 */
	@Override
	public ResultDO<JSONObject> getArea(String cityid) {
		ResultDO<JSONObject> result = new ResultDO<JSONObject>();
		try {
			Areas area = new Areas();
			if(StringUtils.isNotBlank(cityid)) {
				area.setCityid(cityid);
			}
			
			List<Areas> areas = areasMapper.selectByPrimaryKey(area);
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("area", JSONArray.toJSON(areas));
			
			result.setModule(jsonobj);
			result.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(Boolean.FALSE);
		}
		
		return result;
	}

	/**
	 * 获取所有市信息
	 * @return
	 */
	@Override
	public ResultDO<JSONObject> getCitie(String provinceid) {
		ResultDO<JSONObject> result = new ResultDO<JSONObject>();
		try {
			Cities citie = new Cities();
			if(StringUtils.isNotBlank(provinceid)) {
				citie.setProvinceid(provinceid);
			}
			
			List<Cities> cities = citiesMapper.selectByPrimaryKey(citie);
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("citie", JSONArray.toJSON(cities));
			
			result.setModule(jsonobj);
			result.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(Boolean.FALSE);
		}
		
		return result;
	}

	/**
	 * 获取所有市区信息
	 * @return
	 */
	@Override
	public ResultDO<JSONObject> getCitieArea() {
		ResultDO<JSONObject> result = new ResultDO<JSONObject>();
		try {
			List<Cities> cities = citiesMapper.selectByPrimaryKey(new Cities());
			List<Areas> areas = areasMapper.selectByPrimaryKey(new Areas());
			JSONObject jsonobj = new JSONObject();
			
			jsonobj.put("citie", JSONArray.toJSON(cities));
			jsonobj.put("area", JSONArray.toJSON(areas));
			
			result.setModule(jsonobj);
			result.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(Boolean.FALSE);
		}
		
		return result;
	}
}
