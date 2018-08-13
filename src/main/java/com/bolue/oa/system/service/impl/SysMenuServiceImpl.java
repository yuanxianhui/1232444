package com.bolue.oa.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.support.json.JSONUtils;
import com.bolue.oa.system.dto.SysMenuMapper;
import com.bolue.oa.system.entity.SysMenu;
import com.bolue.oa.system.service.SysMenuService;
import com.bolue.oa.util.BaseResultCode;
import com.bolue.oa.util.ResultDO;

import net.sf.json.JSONObject;

@Service
public class SysMenuServiceImpl implements SysMenuService {

	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	/**
	 * 获取当前账号对应的菜单
	 */
	@Override
	public ResultDO<String> getMenuInfo(String accountCode) {
		ResultDO<String> result = new ResultDO<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		String json = null;
		try {
			List<SysMenu> menus = sysMenuMapper.selectByAccountCode(accountCode);
			//父菜单
			List<String> parents = new ArrayList<String>();
			for(SysMenu menu:menus) {
				if(StringUtils.isNotBlank(menu.getMenuFlag())) {
					JSONObject jsonMenu = JSONObject.fromObject(menu);
					parents.add(jsonMenu.toString());
				}
			}
			//子菜单
			List<String> subs = new ArrayList<String>();
			for(String parent:parents) {
				JSONObject jsonObject = JSONObject.fromObject(parent);
				SysMenu sysMenu = (SysMenu)JSONObject.toBean(jsonObject, SysMenu.class);
				for(SysMenu menu:menus) {
					if(StringUtils.isNotBlank(menu.getMenuParent()) && sysMenu.getMenuFlag().equals(menu.getMenuParent())) {
						JSONObject jsonMenu = JSONObject.fromObject(menu);
						subs.add(jsonMenu.toString());
					}
				}
			}
			
			if(parents.size() > 0) {
				map.put("parents", parents);
			}
			if(subs.size() > 0) {
				map.put("subs", subs);
			}
			if(!map.isEmpty()) {
				json = JSONUtils.toJSONString(map); 
			}
			if(json != null) {
				result.setModule(json);
				result.setSuccess(Boolean.TRUE);
			}else {
				result.setErrorMessage(BaseResultCode.FALSE);
				result.setSuccess(Boolean.FALSE);
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setErrorMessage(BaseResultCode.FALSE);
			result.setSuccess(Boolean.FALSE);
		}
		return result;
	}

	/**
	 * 根据角色集合获取角色对应的权限集合
	 */
	@Override
	public List<SysMenu> findPermissions(String accountCode) {
		List<SysMenu> result = new ArrayList<SysMenu>();
		
		result = sysMenuMapper.selectByAccountCode(accountCode);
		
		return result;
	}

	/**
	 * 保存菜单
	 * @param data
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> addMenuInfo(SysMenu data) {
		try {
			SysMenu model = sysMenuMapper.selectByPrimaryKey(data.getMenuCode());
			if(model != null) {
				return new ResultDO<String>(BaseResultCode.DATA_REPEAT, Boolean.FALSE);
			}
			Subject subject = SecurityUtils.getSubject();
			String account = (String)subject.getPrincipal();
			
			data.setCreateUser(account);
			data.setUpdateUser(account);
			
			int inresult = sysMenuMapper.insertSelective(data);
			if(inresult > 0) {
				return new ResultDO<String>(BaseResultCode.TRUE, Boolean.TRUE);
			}else {
				throw new Exception("add_exception");
			}
		} catch (DuplicateKeyException de) {
			de.printStackTrace();
			return new ResultDO<String>(BaseResultCode.SAVE_FAIL, Boolean.FALSE);
		} catch (Exception e) {
			e.printStackTrace();
			if ("add_exception".equals(e.getMessage())) {
				return new ResultDO<String>(BaseResultCode.SAVE_FAIL, Boolean.FALSE);
			} else {
				return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
			}
		}
	}

	/**
	 * 检索菜单信息
	 * @param data
	 * @return
	 */
	@Override
	public ResultDO<List<SysMenu>> searchMenuIfos(SysMenu data) {
		ResultDO<List<SysMenu>> result = new ResultDO<List<SysMenu>>();
		try {
			List<SysMenu> infos = sysMenuMapper.selectInfosByForm(data);
			if (infos.size() > 0) {
				result.setModule(infos);
				result.setSuccess(Boolean.TRUE);
			} else {
				return new ResultDO<List<SysMenu>>(BaseResultCode.DATA_NIL, Boolean.FALSE);
			}
		}catch(Exception e){
			e.printStackTrace();
			return new ResultDO<List<SysMenu>>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		return result;
	}

	/**
	 * 根据菜单编号获取菜单信息
	 * @param menuCode
	 * @return
	 */
	@Override
	public SysMenu getMenuInfoByMenuCode(String menuCode) {
		SysMenu result = new SysMenu();
		try {
			result = sysMenuMapper.selectByPrimaryKey(menuCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 编辑保存菜单信息
	 * @param data
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> editMenuInfo(SysMenu data) {
		try {
			SysMenu model = sysMenuMapper.selectByPrimaryKey(data.getMenuCode());
			if(model == null) {
				return new ResultDO<String>(BaseResultCode.DATA_NIL, Boolean.FALSE);
			}
			Subject subject = SecurityUtils.getSubject();
			String account = (String)subject.getPrincipal();
			data.setUpdateUser(account);
			int upresult = sysMenuMapper.updateByPrimaryKeySelective(data);
			if(upresult > 0) {
				return new ResultDO<String>(BaseResultCode.SAVE_SUCCESS, Boolean.TRUE);
			}else {
				throw new Exception("up_exception");
			}
		} catch (DuplicateKeyException de) {
			de.printStackTrace();
			return new ResultDO<String>(BaseResultCode.SAVE_FAIL, Boolean.FALSE);
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
	 * 删除菜单信息
	 * @param menuCode
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> removeMenuInfo(String menuCode) {
		try {
			SysMenu model = sysMenuMapper.selectByPrimaryKey(menuCode);
			if(model == null) {
				return new ResultDO<String>(BaseResultCode.DATA_NIL, Boolean.FALSE);
			}
			int del = sysMenuMapper.deleteByPrimaryKey(menuCode);
			if (del > 0) {
				return new ResultDO<String>(BaseResultCode.DEL_SUCCESS, Boolean.TRUE);
			} else {
				throw new Exception("del_exception");
			}
		} catch (DuplicateKeyException de) {
			de.printStackTrace();
			return new ResultDO<String>(BaseResultCode.DEL_FAIL, Boolean.FALSE);
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
