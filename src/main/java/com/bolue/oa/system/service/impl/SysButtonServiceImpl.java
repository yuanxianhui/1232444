package com.bolue.oa.system.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolue.oa.system.dto.SysButtonMapper;
import com.bolue.oa.system.entity.SysButton;
import com.bolue.oa.system.service.SysButtonService;
import com.bolue.oa.util.BaseResultCode;
import com.bolue.oa.util.ResultDO;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@Service
public class SysButtonServiceImpl implements SysButtonService {

	@Autowired
	private SysButtonMapper sysButtonMapper;
	
	/**
	 * 根据账号获取权限内的按钮信息
	 * @param account
	 * @return
	 */
	@Override
	public List<SysButton> findButtonPermissions(String accountCode) {
		List<SysButton> result = new ArrayList<SysButton>();
		
		result = sysButtonMapper.selectButtonInfoByAccount(accountCode);
		
		
		return result;
	}

	/**
	 * 保存新增按钮信息
	 * @param data
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> saveButtonInfo(SysButton data) {
		try {
			Subject subject = SecurityUtils.getSubject();
			String account = (String)subject.getPrincipal();
			data.setCreateUser(account);
			data.setUpdateUser(account);
			
			int ins = sysButtonMapper.insertSelective(data);
			if(ins > 0) {
				return new ResultDO<String>(BaseResultCode.SAVE_SUCCESS, Boolean.TRUE);
			} else {
				throw new Exception("save_exception");
			}
		} catch (DuplicateKeyException mye) {
			return new ResultDO<String>(BaseResultCode.SAVE_FAIL, Boolean.FALSE);
		} catch (Exception e) {
			e.printStackTrace();
			if ("save_exception".equals(e.getMessage())) {
				return new ResultDO<String>(BaseResultCode.SAVE_FAIL, Boolean.FALSE);
			} else {
				return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
			}
		}
	}

	/**
	 * 获取按钮信息集合
	 * @param data
	 * @return
	 */
	@Override
	public ResultDO<List<SysButton>> getButtonInfos(SysButton data) {
		ResultDO<List<SysButton>> result  = new ResultDO<List<SysButton>>();
		
		try {
			List<SysButton> buttons = sysButtonMapper.selectButtonInfosByForm(data);
			result.setModule(buttons);
			result.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<List<SysButton>>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		return result;
	}

	/**
	 * 根据按钮编号获取按钮信息
	 * @param buttonCode
	 * @return
	 */
	@Override
	public ResultDO<SysButton> getButtonInfoByButtonCode(String buttonCode) {
		ResultDO<SysButton> result  = new ResultDO<SysButton>();
		try {
			SysButton button = sysButtonMapper.selectByPrimaryKey(buttonCode);
			result.setModule(button);
			result.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<SysButton>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		return result;
	}

	/**
	 * 更新按钮信息
	 * @param data
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> upButtonInfo(SysButton data) {
		try {
			SysButton button = sysButtonMapper.selectByPrimaryKey(data.getButtonCode());
			if(button == null) {
				return new ResultDO<String>(BaseResultCode.DATA_NIL, Boolean.FALSE);
			}
			Subject subject = SecurityUtils.getSubject();
			String account = (String)subject.getPrincipal();
			data.setUpdateUser(account);
			
			int up = sysButtonMapper.updateByPrimaryKeySelective(data);
			if(up > 0) {
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

	/**
	 * 删除按钮信息
	 * @param buttonCode
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> removeButtonInfo(String buttonCode) {
		try {
			SysButton info = sysButtonMapper.selectByPrimaryKey(buttonCode);
			if (info == null) {
				return new ResultDO<String>(BaseResultCode.DATA_NIL, Boolean.FALSE);
			}
			
			int del = sysButtonMapper.deleteByPrimaryKey(buttonCode);
			
			if(del > 0) {
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

}
