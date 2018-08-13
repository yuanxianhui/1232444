package com.bolue.oa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolue.oa.service.RegisterService;
import com.bolue.oa.system.common.EndecryptUtils;
import com.bolue.oa.system.dto.SysAccountMapper;
import com.bolue.oa.system.entity.SysAccount;
import com.bolue.oa.util.BaseResultCode;
import com.bolue.oa.util.ResultDO;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private SysAccountMapper sysAccountMapper;
	
	/**
	 * 注册账号信息
	 * @param account
	 * @return
	 */
	@Override
	public ResultDO<String> addAccount(SysAccount account) {
		try {
			
			SysAccount accountInfo = sysAccountMapper.selectByPrimaryKey(account.getAccountCode());
			if(accountInfo != null) {
				return new ResultDO<String>(BaseResultCode.SAVE_REPEAT, Boolean.FALSE);
			}
			
			String password = EndecryptUtils.md5Password(account.getAccountKey(), "");
			account.setAccountKey(password);
			account.setLocked(3);
			
			int reInt = sysAccountMapper.insert(account);
			
			if(reInt > 0) {
				return new ResultDO<String>(BaseResultCode.SAVE_SUCCESS, Boolean.TRUE);
			}else{
				return new ResultDO<String>(BaseResultCode.SAVE_FAIL, Boolean.FALSE);
			}
		}catch(Exception e){
			return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
	}

}
