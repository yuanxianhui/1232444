package com.bolue.oa.service;

import java.util.List;

import com.bolue.oa.entity.enumer.SysEnumerate;
import com.bolue.oa.entity.enumer.SysEnumerateDetailed;
import com.bolue.oa.util.ResultDO;

public interface SysEnumerateDetailedService {

	/**
	 * 根据枚举类型编号获取对应枚举明细信息
	 * @param enumCode
	 * @return
	 */
	ResultDO<List<SysEnumerateDetailed>> getEnuDInfos(String enumCode, String code);

	/**
	 * 保存枚举明细信息
	 * @param data
	 * @return
	 */
	ResultDO<String> saveEnudInfo(SysEnumerateDetailed data);

	/**
	 * 获取枚举明细信息
	 * @param enumCode
	 * @param code
	 * @return
	 */
	SysEnumerateDetailed getEnudInfo(String enumCode, String code);

	/**
	 * 更新枚举明细信息
	 * @param data
	 * @return
	 */
	ResultDO<String> saveupEnudInfo(SysEnumerateDetailed data);

	/**
	 * 删除枚举明细信息
	 * @param data
	 * @return
	 */
	ResultDO<String> removeEnudInfo(SysEnumerateDetailed data);

}
