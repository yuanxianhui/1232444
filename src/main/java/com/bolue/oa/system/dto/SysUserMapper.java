package com.bolue.oa.system.dto;

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
}