package com.bolue.oa.system.dto;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.bolue.oa.system.entity.SysButton;

@Mapper
public interface SysButtonMapper {
    int deleteByPrimaryKey(String buttonCode);

    int insert(SysButton record);

    int insertSelective(SysButton record);

    SysButton selectByPrimaryKey(String buttonCode);

    int updateByPrimaryKeySelective(SysButton record);

    int updateByPrimaryKey(SysButton record);

    /**
     * 根据账号获取权限内的按钮信息
     * @param account
     * @return
     */
	List<SysButton> selectButtonInfoByAccount(String accountCode);
	
	/**
	 * 根据检索条件检索按钮信息集合
	 * @param data
	 * @return
	 */
	List<SysButton> selectButtonInfosByForm(@Param("data")SysButton record);
}