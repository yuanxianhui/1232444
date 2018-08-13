package com.bolue.oa.system.service;

import java.util.List;

import com.bolue.oa.system.entity.SysMenu;
import com.bolue.oa.util.ResultDO;

public interface SysMenuService {

	/**
	 * 获取当前账号对应的菜单
	 * @param accountCode
	 * @param accountFlg
	 * @return
	 */
	public ResultDO<String> getMenuInfo(String accountCode);

	/**
	 * 根据账号获取当前账号拥有的权限
	 * @param accountCode
	 * @return
	 */
	public List<SysMenu> findPermissions(String accountCode);

	/**
	 * 新增菜单
	 * @param data
	 * @return
	 */
	public ResultDO<String> addMenuInfo(SysMenu data);

	/**
	 * 检索菜单信息
	 * @param data
	 * @return
	 */
	public ResultDO<List<SysMenu>> searchMenuIfos(SysMenu data);

	/**
	 * 根据菜单编号获取菜单信息
	 * @param menuCode
	 * @return
	 */
	public SysMenu getMenuInfoByMenuCode(String menuCode);

	/**
	 * 编辑保存菜单信息
	 * @param data
	 * @return
	 */
	public ResultDO<String> editMenuInfo(SysMenu data);

	/**
	 * 删除菜单信息
	 * @param menuCode
	 * @return
	 */
	public ResultDO<String> removeMenuInfo(String menuCode);
}
