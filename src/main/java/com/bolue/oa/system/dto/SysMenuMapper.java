package com.bolue.oa.system.dto;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bolue.oa.system.entity.SysMenu;

public interface SysMenuMapper {
    int deleteByPrimaryKey(String menuCode);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(String menuCode);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);
    
    /**
     * 检索当前账号拥有的权限集合
     * @param accountCode
     * @return
     */
	List<SysMenu> selectByAccountCode(String accountCode);
	
	/**
	 * 根据检索条件检索菜单信息
	 * @param record
	 * @return
	 */
	List<SysMenu> selectInfosByForm(@Param("data")SysMenu record);
	
	/**
	 * 检索当前登陆用户对应的菜单编号
	 * @param accountCode
	 * @return
	 */
	List<String> selectParentByAccountCode(String accountCode);

	/**
	 * 检索所有有效菜单信息
	 * @return
	 */
	List<SysMenu> selectAllMenu();
}