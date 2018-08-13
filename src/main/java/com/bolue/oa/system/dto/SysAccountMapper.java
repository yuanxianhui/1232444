package com.bolue.oa.system.dto;

import java.util.List;

import com.bolue.oa.system.entity.SysAccount;

public interface SysAccountMapper {
    int deleteByPrimaryKey(String accountCode);

    int insert(SysAccount record);

    int insertSelective(SysAccount record);

    SysAccount selectByPrimaryKey(String accountCode);

    int updateByPrimaryKeySelective(SysAccount record);

    int updateByPrimaryKey(SysAccount record);

    /**
     * 根据账号状态位正常和待审批检索账号集合
     * @return
     */
	List<SysAccount> selectAccounts();
}