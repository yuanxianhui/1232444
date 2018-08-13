package com.bolue.oa.system.service;

import java.util.List;

import com.bolue.oa.system.entity.SysButton;
import com.bolue.oa.util.ResultDO;

public interface SysButtonService {

	/**
	 * 根据账号获取权限内的按钮信息
	 * @param account
	 * @return
	 */
	public List<SysButton> findButtonPermissions(String accountCode);

	/**
	 * 保存新增按钮信息
	 * @param data
	 * @return
	 */
	public ResultDO<String> saveButtonInfo(SysButton data);

	/**
	 * 获取按钮信息集合
	 * @param data
	 * @return
	 */
	public ResultDO<List<SysButton>> getButtonInfos(SysButton data);

	/**
	 * 根据按钮编号获取按钮信息
	 * @param buttonCode
	 * @return
	 */
	public ResultDO<SysButton> getButtonInfoByButtonCode(String buttonCode);

	/**
	 * 更新按钮信息
	 * @param data
	 * @return
	 */
	public ResultDO<String> upButtonInfo(SysButton data);

	/**
	 * 删除按钮信息
	 * @param buttonCode
	 * @return
	 */
	public ResultDO<String> removeButtonInfo(String buttonCode);
}
