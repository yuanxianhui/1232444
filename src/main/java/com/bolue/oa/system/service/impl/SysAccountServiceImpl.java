package com.bolue.oa.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolue.oa.system.dto.SysAccountMapper;
import com.bolue.oa.system.entity.SysAccount;
import com.bolue.oa.system.service.SysAccountService;
import com.bolue.oa.util.BaseResultCode;
import com.bolue.oa.util.ResultDO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SysAccountServiceImpl implements SysAccountService {

	@Autowired
	private SysAccountMapper sysAccountMapper;
	//账号状态（0：正常 1：冻结 2：停用 3：待审批。默认：3）
	private static int ACCOUNT_FLG = 0;
	
	/**
	 * 根据账号获取用户信息
	 * @param accountCode
	 * @return
	 */
	@Override
	public ResultDO<SysAccount> getAccountInfoByAccount(String accountCode, String passwordCode) {
		log.debug("当前登陆账号：" + accountCode);
		ResultDO<SysAccount> result = new ResultDO<SysAccount>();
		
		try {
			SysAccount accountInfo = sysAccountMapper.selectByPrimaryKey(accountCode);
			if(accountInfo != null) {
				if(ACCOUNT_FLG != accountInfo.getLocked()) {
					result.setSuccess(Boolean.FALSE);
					result.setErrorMessage(String.valueOf(accountInfo.getLocked()));//TODO 匹配枚举类，设置账号当前状态
					return result;
				}
				
				//TODO 密码是否相同比较加密
				String accountKey = accountInfo.getAccountKey();
				if(passwordCode.equals(accountKey)) {
					result.setModule(accountInfo);
					result.setSuccess(Boolean.TRUE);
				}else {
					return new ResultDO<SysAccount>(BaseResultCode.LOGIN_WARN, Boolean.FALSE);
				}
			}else {
				//账号或密码错误
				return new ResultDO<SysAccount>(BaseResultCode.LOGIN_WARN, Boolean.FALSE);
			}
		}catch(Exception e) {
			log.error(e.getMessage());
			return new ResultDO<SysAccount>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		return result;
	}

	/**
	 * 获取账号信息
	 */
	@Override
	public SysAccount findByAccount(String accountCode) {
		SysAccount result = new SysAccount();
		
		result = sysAccountMapper.selectByPrimaryKey(accountCode);
		
		return result;
	}

	/**
	 * 获取正常使用和待审批账号集合
	 * @return
	 */
	@Override
	public ResultDO<List<SysAccount>> getAccounts() {
		ResultDO<List<SysAccount>> result = new ResultDO<List<SysAccount>>();
		try {
			List<SysAccount> list = sysAccountMapper.selectAccounts();
			
			if(list.size() <= 0) {
				list = new ArrayList<SysAccount>();
			}
			
			result.setModule(list);
			result.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<List<SysAccount>>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		return result;
	}

}
