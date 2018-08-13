package com.bolue.oa.system.service;

import com.bolue.oa.system.entity.SysUser;

public interface SysUserService {

	/**
	 * 根据账号获取员工信息
	 * @param accountCode
	 * @return
	 */
	public SysUser getUserInfoByAccount(String accountCode);
}
