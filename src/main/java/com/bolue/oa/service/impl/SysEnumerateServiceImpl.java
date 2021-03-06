package com.bolue.oa.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolue.oa.mapper.enumer.SysEnumerateMapper;
import com.bolue.oa.entity.enumer.SysEnumerate;
import com.bolue.oa.entity.enumer.SysEnumerateDetailed;
import com.bolue.oa.entity.enumer.SysEnumerateDetailedKey;
import com.bolue.oa.entity.enumer.SysEnumerateKey;
import com.bolue.oa.mapper.enumer.SysEnumerateDetailedMapper;
import com.bolue.oa.service.SysEnumerateService;
import com.bolue.oa.util.BaseResultCode;
import com.bolue.oa.util.ResultDO;

import net.sf.json.JSONObject;

@Service
public class SysEnumerateServiceImpl implements SysEnumerateService {

	@Autowired
	private SysEnumerateMapper sysEnumerateMapper;
	@Autowired
	private SysEnumerateDetailedMapper sysEnumerateDetailedMapper;
	
	/**
	 * 获取枚举类型列表集合
	 * @param data
	 * @return
	 */
	@Override
	public ResultDO<List<SysEnumerate>> getEnuInfos(SysEnumerate data) {
		ResultDO<List<SysEnumerate>> result = new ResultDO<List<SysEnumerate>>();
		
		try {
			List<SysEnumerate> list = sysEnumerateMapper.selectEnuInfosByForm(data);
			if (list.size() > 0) {
				result.setModule(list);
				result.setSuccess(Boolean.TRUE);
			} else {
				return new ResultDO<List<SysEnumerate>>(BaseResultCode.DATA_NIL, Boolean.FALSE);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<List<SysEnumerate>>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		return result;
	}

	/**
	 * 保存枚举类型
	 * @param data
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> saveEnuInfo(SysEnumerate data) {
		try {
			SysEnumerate enu = sysEnumerateMapper.selectByPrimaryKey(data);
			if (enu != null) {
				return new ResultDO<String>(BaseResultCode.DATA_REPEAT, Boolean.FALSE);
			}
			Subject subject = SecurityUtils.getSubject();
			String account = (String)subject.getPrincipal();
			data.setCreateUser(account);
			data.setUpdateUser(account);
			
			int ups = sysEnumerateMapper.insertSelective(data);
			if (ups > 0) {
				return new ResultDO<String>(BaseResultCode.SAVE_SUCCESS, Boolean.TRUE);
			} else {
				throw new Exception("in_exception");
			}
		} catch (DuplicateKeyException de) {
			return new ResultDO<String>(BaseResultCode.SAVE_FAIL, Boolean.FALSE);
		} catch (SQLException se) {
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
	 * 根据枚举类型编号获取信息
	 * @param enumCode
	 * @return
	 */
	@Override
	public SysEnumerate getEnuInfo(String enumCode) {
		SysEnumerate result = new SysEnumerate();
		try {
			SysEnumerateKey data = new SysEnumerateKey();
			data.setEnumCode(enumCode);
			result = sysEnumerateMapper.selectByPrimaryKey(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 更新枚举类型
	 * @param data
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> saveupEnuInfo(SysEnumerate data) {
		try {
			SysEnumerate enu = sysEnumerateMapper.selectByPrimaryKey(data);
			if (enu == null) {
				return new ResultDO<String>(BaseResultCode.DATA_NIL, Boolean.FALSE);
			}
			Subject subject = SecurityUtils.getSubject();
			String account = (String)subject.getPrincipal();
			data.setUpdateUser(account);
			
			int ups = sysEnumerateMapper.updateByPrimaryKeySelective(data);
			if (ups > 0) {
				return new ResultDO<String>(BaseResultCode.SAVE_SUCCESS, Boolean.TRUE);
			} else {
				throw new Exception("ups_exception");
			}
		} catch (DuplicateKeyException de) {
			return new ResultDO<String>(BaseResultCode.SAVE_FAIL, Boolean.FALSE);
		} catch (SQLException se) {
			return new ResultDO<String>(BaseResultCode.SAVE_FAIL, Boolean.FALSE);
		} catch (Exception e) {
			e.printStackTrace();
			if("ups_exception".equals(e.getMessage())) {
				return new ResultDO<String>(BaseResultCode.SAVE_FAIL, Boolean.FALSE);
			}else {
				return new ResultDO<String>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
			}
		}
	}

	/**
	 * 删除枚举类型
	 * @param enumCode
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> removeEnuInfo(String enumCode) {
		try {
			SysEnumerateKey data = new SysEnumerateKey();
			data.setEnumCode(enumCode);
			int ups = sysEnumerateMapper.deleteByPrimaryKey(data);
			if (ups > 0) {
				return new ResultDO<String>(BaseResultCode.DEL_SUCCESS, Boolean.TRUE);
			} else {
				throw new Exception("del_exception");
			}
		} catch (DuplicateKeyException de) {
			return new ResultDO<String>(BaseResultCode.DEL_FAIL, Boolean.FALSE);
		} catch (SQLException se) {
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
