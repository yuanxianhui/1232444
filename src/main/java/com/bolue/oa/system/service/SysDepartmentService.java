package com.bolue.oa.system.service;

import java.util.List;

import com.bolue.oa.system.entity.SysDepartment;
import com.bolue.oa.util.ResultDO;

public interface SysDepartmentService {

	/**
	 * 根据账号查询当前账号拥有的角色
	 * @param accountCode
	 * @return
	 */
	List<SysDepartment> findRoles(String accountCode);

	/**
	 * 根据部门编号获取部门信息
	 * @param departmentCode
	 * @return
	 */
	ResultDO<SysDepartment> getDepartmentInfoByCode(String departmentCode);

	/**
	 * 保存部门信息
	 * @param data
	 * @return
	 */
	ResultDO<String> saveDepartmentInfo(SysDepartment data);

	/**
	 * 根据检索条件获取部门信息集合
	 * @param data
	 * @return
	 */
	ResultDO<List<SysDepartment>> getDepartmentInfosByForm(SysDepartment data);

	/**
	 * 更新部门信息
	 * @param data
	 * @return
	 */
	ResultDO<String> saveupDepartmentInfo(SysDepartment data);

	/**
	 * 删除部门信息
	 * @param departmentCode
	 * @return
	 */
	ResultDO<String> removeDepartmentInfo(String departmentCode);

}
