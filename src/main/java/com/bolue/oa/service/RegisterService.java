package com.bolue.oa.service;

import com.bolue.oa.system.entity.SysAccount;
import com.bolue.oa.util.ResultDO;

public interface RegisterService {

	/**
	 * 注册账号信息
	 * @param account
	 * @return
	 */
	public ResultDO<String> addAccount(SysAccount account);
}
