package com.bolue.oa.system.service;

import java.util.List;

import com.bolue.oa.entity.user.SysUserDto;
import com.bolue.oa.system.entity.SysUser;
import com.bolue.oa.util.ResultDO;

public interface SysUserService {

	/**
	 * 根据账号获取员工信息
	 * @param accountCode
	 * @return
	 */
	public SysUser getUserInfoByAccount(String accountCode);

	/**
	 * 保存员工信息
	 * @param data
	 * @return
	 */
	public ResultDO<String> saveUserInfo(SysUserDto data);

	/**
	 * 根据检索条件获取员工信息集合
	 * @param data
	 * @return
	 */
	public ResultDO<List<SysUser>> getUserInfos(SysUserDto data);

	/**
	 * 删除员工信息
	 * @param data
	 * @return
	 */
	public ResultDO<String> removeUserInfo(SysUserDto data);

	/**
	 * 获取用户信息
	 * @param data
	 * @return
	 */
	public ResultDO<SysUser> getUserInfo(String userCode);

	/**
	 * 更新保存员工信息
	 * @param data
	 * @return
	 */
	public ResultDO<String> saveupUserInfo(SysUserDto data);
}
