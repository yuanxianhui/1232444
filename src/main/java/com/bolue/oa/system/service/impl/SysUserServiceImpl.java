package com.bolue.oa.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bolue.oa.system.dto.SysUserMapper;
import com.bolue.oa.system.entity.SysUser;
import com.bolue.oa.system.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	
	/**
	 * 根据账号获取员工信息
	 * @param accountCode
	 * @return
	 */
	@Override
	public SysUser getUserInfoByAccount(String accountCode) {
		// TODO Auto-generated method stub
		
		SysUser result = sysUserMapper.selectUserInfoByAccount(accountCode);
		
		return result;
	}

}
