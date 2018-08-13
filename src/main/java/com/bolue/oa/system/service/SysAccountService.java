package com.bolue.oa.system.service;

import java.util.List;

import com.bolue.oa.system.entity.SysAccount;
import com.bolue.oa.util.ResultDO;

public interface SysAccountService {

	/**
	 * 根据账号获取用户信息
	 * @param accountCode
	 * @return
	 */
	ResultDO<SysAccount> getAccountInfoByAccount(String accountCode, String passwordCode);
	
	/**
	 * 获取账号信息
	 * @param info
	 * @return
	 */
	SysAccount findByAccount(String accountCode);
	
	/**
	 * 获取正常使用和待审批账号集合
	 * @return
	 */
	ResultDO<List<SysAccount>> getAccounts();
}
