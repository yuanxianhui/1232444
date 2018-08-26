package com.bolue.oa.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolue.oa.entity.user.SysUserDto;
import com.bolue.oa.system.dto.SysAccountMapper;
import com.bolue.oa.system.dto.SysUserMapper;
import com.bolue.oa.system.entity.SysAccount;
import com.bolue.oa.system.entity.SysUser;
import com.bolue.oa.system.service.SysUserService;
import com.bolue.oa.util.BaseResultCode;
import com.bolue.oa.util.ResultDO;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysAccountMapper sysAccountMapper;
	
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

	/**
	 * 保存员工信息
	 * @param data
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> saveUserInfo(SysUserDto data) {
		try {
			SysUser suerinfo = sysUserMapper.selectByPrimaryKey(data.getUserCode());
			if (suerinfo != null) {
				return new ResultDO<String>(BaseResultCode.DATA_REPEAT, Boolean.TRUE);
			}
			
			SysUser record = new SysUser();
			BeanUtils.copyProperties(data,record);
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date currentTime = formatter.parse(data.getEntryTime());
			record.setEntryTime(currentTime);
			
			Subject subject = SecurityUtils.getSubject();
			String account = (String)subject.getPrincipal();
			record.setUpdateUser(account);
			
			int ins = sysUserMapper.insertSelective(record);
			if (ins > 0) {
				//更新账号状态
				SysAccount accountinfo = new SysAccount();
				accountinfo.setLocked(0);
				accountinfo.setUpdateUser(account);
				int ups = sysAccountMapper.updateByPrimaryKeySelective(accountinfo);
				if (ups <= 0) {
					return new ResultDO<String>(BaseResultCode.UP_ACCOUNT_FAIL, Boolean.FALSE);
				}
				
				return new ResultDO<String>(BaseResultCode.SAVE_SUCCESS, Boolean.TRUE);
			} else {
				throw new Exception("ins_exception");
			}
		} catch (DuplicateKeyException de) {
			de.printStackTrace();
			return new ResultDO<String>(BaseResultCode.SAVE_FAIL, Boolean.FALSE);
		} catch (Exception e) {
			e.printStackTrace();
			if ("ins_exception".equals(e.getMessage())) {
				return new ResultDO<String>(BaseResultCode.SAVE_FAIL, Boolean.FALSE);
			} else {
				return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
			}
		}
	}

	/**
	 * 根据检索条件获取员工信息
	 * @param data
	 * @return
	 */
	@Override
	public ResultDO<List<SysUser>> getUserInfos(SysUserDto data) {
		ResultDO<List<SysUser>> result = new ResultDO<List<SysUser>>();
		try {
			
			SysUser record = new SysUser ();
			BeanUtils.copyProperties(data, record);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if (StringUtils.isNotBlank(data.getEntryTime())) {
				Date currentTime = formatter.parse(data.getEntryTime());
				record.setEntryTime(currentTime);
			}
			if (StringUtils.isNotBlank(data.getOfficialTime())) {
				Date currentTime = formatter.parse(data.getOfficialTime());
				record.setOfficialTime(currentTime);
			}
			
			List<SysUser> list = sysUserMapper.selectUserInfosByForm(record);
			if (list.size() > 0) {
				result.setModule(list);
				result.setSuccess(Boolean.TRUE);
			} else {
				return new ResultDO<List<SysUser>>(BaseResultCode.DATA_NIL, Boolean.FALSE);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<List<SysUser>>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		
		return result;
	}

	/**
	 * 删除员工信息
	 * @param data
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> removeUserInfo(SysUserDto data) {
		try {
			SysUser userInfo = sysUserMapper.selectByPrimaryKey(data.getUserCode());
			if (userInfo == null) {
				return new ResultDO<String>(BaseResultCode.DATA_NIL, Boolean.FALSE);
			}
			
			int del = sysUserMapper.deleteByPrimaryKey(data.getUserCode());
			if (del > 0) {
				//更新账号状态
				SysAccount accountinfo = new SysAccount();
				accountinfo.setLocked(3);
				accountinfo.setUpdateUser(data.getAccountCode());
				int ups = sysAccountMapper.updateByPrimaryKeySelective(accountinfo);
				if (ups <= 0) {
					return new ResultDO<String>(BaseResultCode.UP_ACCOUNT_FAIL, Boolean.FALSE);
				}
				
				return new ResultDO<String>(BaseResultCode.DEL_SUCCESS, Boolean.TRUE);
			} else {
				throw new Exception("del_exception");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if ("del_exception".equals(e.getMessage())) {
				return new ResultDO<String>(BaseResultCode.DEL_FAIL, Boolean.FALSE);
			} else {
				return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
			}
		}
	}

	/**
	 * 获取用户信息
	 * @param data
	 * @return
	 */
	@Override
	public ResultDO<SysUser> getUserInfo(String userCode) {
		ResultDO<SysUser> result = new ResultDO<SysUser>();
		try {
			SysUser userInfo = sysUserMapper.selectByPrimaryKey(userCode);
			result.setModule(userInfo);
			result.setSuccess(Boolean.TRUE);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<SysUser>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
	}

	/**
	 * 更新保存员工信息
	 * @param data
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> saveupUserInfo(SysUserDto data) {
		try {
			SysUser userInfo = sysUserMapper.selectByPrimaryKey(data.getUserCode());
			if (userInfo == null) {
				return new ResultDO<String>(BaseResultCode.DATA_NIL, Boolean.FALSE);
			}
			
			SysUser record = new SysUser();
			BeanUtils.copyProperties(data, record);
			
			Subject subject = SecurityUtils.getSubject();
			String account = (String)subject.getPrincipal();
			record.setUpdateUser(account);
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if (StringUtils.isNotBlank(data.getEntryTime())) {
				Date currentTime = formatter.parse(data.getEntryTime());
				record.setEntryTime(currentTime);
			}
			if (StringUtils.isNotBlank(data.getOfficialTime())) {
				Date currentTime = formatter.parse(data.getOfficialTime());
				record.setOfficialTime(currentTime);
			}
			if (StringUtils.isNotBlank(data.getDimissionTime())) {
				Date currentTime = formatter.parse(data.getDimissionTime());
				record.setDimissionTime(currentTime);
			}
			
			int up = sysUserMapper.updateByPrimaryKeySelective(record);
			if (up > 0) {
				return new ResultDO<String>(BaseResultCode.SAVE_SUCCESS, Boolean.TRUE);
			} else {
				throw new Exception("up_exception");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if ("up_exception".equals(e.getMessage())) {
				return new ResultDO<String>(BaseResultCode.SAVE_FAIL, Boolean.FALSE);
			} else {
				return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
			}
		}
	}

}
