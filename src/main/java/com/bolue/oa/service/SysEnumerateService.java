package com.bolue.oa.service;

import java.util.List;
import java.util.Map;

import com.bolue.oa.entity.enumer.SysEnumerate;
import com.bolue.oa.entity.enumer.SysEnumerateDetailed;
import com.bolue.oa.entity.enumer.SysEnumerateKey;
import com.bolue.oa.util.ResultDO;

public interface SysEnumerateService {

	/**
	 * 获取枚举类型列表集合
	 * @param data
	 * @return
	 */
	ResultDO<List<SysEnumerate>> getEnuInfos(SysEnumerate data);

	/**
	 * 保存枚举类型
	 * @param data
	 * @return
	 */
	ResultDO<String> saveEnuInfo(SysEnumerate data);

	/**
	 * 根据枚举类型编号获取信息
	 * @param enumCode
	 * @return
	 */
	SysEnumerate getEnuInfo(String enumCode);

	/**
	 * 更新枚举类型
	 * @param data
	 * @return
	 */
	ResultDO<String> saveupEnuInfo(SysEnumerate data);

	/**
	 * 删除枚举类型
	 * @param enumCode
	 * @return
	 */
	ResultDO<String> removeEnuInfo(String enumCode);

}
