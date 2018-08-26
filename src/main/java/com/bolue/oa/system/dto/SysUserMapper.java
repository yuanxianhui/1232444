package com.bolue.oa.system.dto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bolue.oa.system.entity.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(String userCode);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String userCode);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

	SysUser selectUserInfoByAccount(@Param("accountCode")String accountCode);

	/**
	 * 根据检索条件检索员工信息集合
	 * @param record
	 * @return
	 */
	List<SysUser> selectUserInfosByForm(@Param("data")SysUser record);
}