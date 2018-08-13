package com.bolue.oa.system.dto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bolue.oa.system.entity.SysDepartment;

public interface SysDepartmentMapper {
    int deleteByPrimaryKey(String departmentCode);

    int insert(SysDepartment record);

    int insertSelective(SysDepartment record);

    SysDepartment selectByPrimaryKey(String departmentCode);

    int updateByPrimaryKeySelective(SysDepartment record);

    int updateByPrimaryKey(SysDepartment record);

    /**
     * 检索账号查询当前账号拥有的角色
     * @param accountCode
     * @return
     */
	List<SysDepartment> selectByAccountCode(String accountCode);

	/**
	 * 根据检索条件检索部门信息
	 * @param data
	 * @return
	 */
	List<SysDepartment> selectDepartmentsByForm(@Param("data")SysDepartment data);
}