package com.bolue.oa.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolue.oa.system.dto.SysDepartmentMapper;
import com.bolue.oa.system.entity.SysDepartment;
import com.bolue.oa.system.service.SysDepartmentService;
import com.bolue.oa.util.BaseResultCode;
import com.bolue.oa.util.ResultDO;

@Service
public class SysDepartmentServiceImpl implements SysDepartmentService {

	@Autowired
	private SysDepartmentMapper sysDepartmentMapper;
	
	/**
	 * 根据账号查询当前账号拥有的角色
	 */
	@Override
	public List<SysDepartment> findRoles(String accountCode) {
		List<SysDepartment> result = new ArrayList<SysDepartment>();
		
		result = sysDepartmentMapper.selectByAccountCode(accountCode);
		
		return result;
	}

	/**
	 * 根据部门编号获取部门信息
	 * @param departmentCode
	 * @return
	 */
	@Override
	public ResultDO<SysDepartment> getDepartmentInfoByCode(String departmentCode) {
		ResultDO<SysDepartment> result = new ResultDO<SysDepartment>();
		try {
			SysDepartment info = sysDepartmentMapper.selectByPrimaryKey(departmentCode);
			if(info == null) {
				info = new SysDepartment();
			}
			result.setModule(info);
			result.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<SysDepartment>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		
		return result;
	}

	/**
	 * 保存部门信息
	 * @param data
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> saveDepartmentInfo(SysDepartment data) {
		try {
			SysDepartment info = sysDepartmentMapper.selectByPrimaryKey(data.getDepartmentCode());
			if(info != null) {
				return new ResultDO<String>(BaseResultCode.DATA_REPEAT, Boolean.FALSE);
			}
			
			Subject subject = SecurityUtils.getSubject();
			String account = (String)subject.getPrincipal();
			data.setCreateUser(account);
			data.setUpdateUser(account);
			
			int ins = sysDepartmentMapper.insertSelective(data);
			if(ins > 0) {
				return new ResultDO<String>(BaseResultCode.SAVE_SUCCESS, Boolean.TRUE);
			}else {
				throw new Exception("in_exception");
			}
		} catch (DuplicateKeyException de) {
			return new ResultDO<String>(BaseResultCode.SAVE_FAIL, Boolean.FALSE);
		} catch (Exception e) {
			e.printStackTrace();
			if("in_exception".equals(e.getMessage())) {
				return new ResultDO<String>(BaseResultCode.SAVE_FAIL, Boolean.FALSE);
			}else {
				return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
			}
		}
	}

	/**
	 * 根据检索条件获取部门信息集合
	 * @param data
	 * @return
	 */
	@Override
	public ResultDO<List<SysDepartment>> getDepartmentInfosByForm(SysDepartment data) {
		ResultDO<List<SysDepartment>> result = new ResultDO<List<SysDepartment>>();
		try {
			List<SysDepartment> list = sysDepartmentMapper.selectDepartmentsByForm(data);
			if(list.size() > 0) {
				result.setModule(list);
				result.setSuccess(Boolean.TRUE);
			}else {
				return new ResultDO<List<SysDepartment>>(BaseResultCode.DATA_NIL, Boolean.FALSE);
			}
		} catch (DuplicateKeyException de) {
			return new ResultDO<List<SysDepartment>>(BaseResultCode.FALSE, Boolean.FALSE);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<List<SysDepartment>>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		
		return result;
	}

	/**
	 * 更新部门信息
	 * @param data
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> saveupDepartmentInfo(SysDepartment data) {
		try {
			SysDepartment info = sysDepartmentMapper.selectByPrimaryKey(data.getDepartmentCode());
			if(info == null) {
				return new ResultDO<String>(BaseResultCode.DATA_NIL, Boolean.FALSE);
			}
			
			Subject subject = SecurityUtils.getSubject();
			String account = (String)subject.getPrincipal();
			data.setUpdateUser(account);
			
			int up = sysDepartmentMapper.updateByPrimaryKeySelective(data);
			if(up > 0) {
				return new ResultDO<String>(BaseResultCode.SAVE_SUCCESS, Boolean.TRUE);
			}else {
				throw new Exception("up_exception");
			}
		} catch (DuplicateKeyException de) {
			return new ResultDO<String>(BaseResultCode.SAVE_FAIL, Boolean.FALSE);
		} catch (Exception e) {
			e.printStackTrace();
			if("up_exception".equals(e.getMessage())) {
				return new ResultDO<String>(BaseResultCode.SAVE_FAIL, Boolean.FALSE);
			}else {
				return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
			}
		}
	}

	/**
	 * 删除部门信息
	 * @param departmentCode
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> removeDepartmentInfo(String departmentCode) {
		try {
			SysDepartment info = sysDepartmentMapper.selectByPrimaryKey(departmentCode);
			if(info == null) {
				return new ResultDO<String>(BaseResultCode.DATA_NIL, Boolean.FALSE);
			}
			int del = sysDepartmentMapper.deleteByPrimaryKey(departmentCode);
			if(del > 0) {
				return new ResultDO<String>(BaseResultCode.DEL_SUCCESS, Boolean.TRUE);
			}else {
				throw new Exception("del_exception");
			}
		} catch (DuplicateKeyException de) {
			return new ResultDO<String>(BaseResultCode.DEL_FAIL, Boolean.FALSE);
		} catch (Exception e) {
			e.printStackTrace();
			if("del_exception".equals(e.getMessage())) {
				return new ResultDO<String>(BaseResultCode.DEL_FAIL, Boolean.FALSE);
			}else {
				return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
			}
		}
	}

}
