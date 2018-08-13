package com.bolue.oa.common;

import java.util.Map;
import java.util.Set;

import com.bolue.oa.util.ResultDO;

import net.sf.json.JSONObject;

public interface Common {
	
	/**
	 * 根据枚举类型编号获取枚举详细信息集合
	 * @param enumCode
	 * @return
	 */
	public ResultDO<Map<?, ?>> getEnumInfos(String enumCode);
	
	/**
	 * 获取所有有效的父菜单信息
	 * @return
	 */
	public ResultDO<Set<String>> getMenuInfos();
	
	/**
	 * 获取所有有效的菜单信息
	 * @return
	 */
	public ResultDO<JSONObject> getAllMenuInfos();
	
	/**
	 * 获取所有省信息
	 * @return
	 */
	public ResultDO<JSONObject> getProvince();
	
	/**
	 * 获取所有市信息
	 * @return
	 */
	public ResultDO<JSONObject> getArea(String cityid);
	
	/**
	 * 获取所有区信息
	 * @return
	 */
	public ResultDO<JSONObject> getCitie(String provinceid);
	
	/**
	 * 获取所有市区信息
	 * @return
	 */
	public ResultDO<JSONObject> getCitieArea();
}
