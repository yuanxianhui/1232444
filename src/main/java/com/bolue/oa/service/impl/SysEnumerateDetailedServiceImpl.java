package com.bolue.oa.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolue.oa.entity.enumer.SysEnumerate;
import com.bolue.oa.entity.enumer.SysEnumerateDetailed;
import com.bolue.oa.entity.enumer.SysEnumerateDetailedKey;
import com.bolue.oa.entity.enumer.SysEnumerateKey;
import com.bolue.oa.mapper.enumer.SysEnumerateDetailedMapper;
import com.bolue.oa.service.SysEnumerateDetailedService;
import com.bolue.oa.util.BaseResultCode;
import com.bolue.oa.util.ResultDO;

@Service
public class SysEnumerateDetailedServiceImpl implements SysEnumerateDetailedService {

	@Autowired
	private SysEnumerateDetailedMapper sysEnumerateDetailedMapper;
	
	/**
	 * 根据枚举类型编号获取对应枚举明细信息
	 * @param enumCode
	 * @return
	 */
	@Override
	public ResultDO<List<SysEnumerateDetailed>> getEnuDInfos(String enumCode, String code) {
		ResultDO<List<SysEnumerateDetailed>> result = new ResultDO<List<SysEnumerateDetailed>>();
		try {
			SysEnumerateDetailed data =new SysEnumerateDetailed();
			data.setEunmCode(enumCode);
			data.setCode(code);
			List<SysEnumerateDetailed> list = sysEnumerateDetailedMapper.selectEnuDinfos(data);
			if(list.size() > 0) {
				result.setModule(list);
				result.setSuccess(Boolean.TRUE);
			}else {
				return new ResultDO<List<SysEnumerateDetailed>>(BaseResultCode.DATA_NIL, Boolean.FALSE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultDO<List<SysEnumerateDetailed>>(BaseResultCode.COMMON_FAIL, Boolean.FALSE);
		}
		return result;
	}
	
	/**
	 * 保存枚举明细信息
	 * @param data
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> saveEnudInfo(SysEnumerateDetailed data) {
		try {
			SysEnumerateDetailedKey key = new SysEnumerateDetailedKey();
			key.setEunmCode(data.getEunmCode());
			key.setCode(data.getCode());
			SysEnumerateDetailed enu = sysEnumerateDetailedMapper.selectByPrimaryKey(key);
			if (enu != null) {
				return new ResultDO<String>(BaseResultCode.DATA_REPEAT, Boolean.FALSE);
			}
			Subject subject = SecurityUtils.getSubject();
			String account = (String)subject.getPrincipal();
			data.setCreateUser(account);
			data.setUpdateUser(account);
			
			int ins = sysEnumerateDetailedMapper.insertSelective(data);
			if (ins > 0) {
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
	 * 获取枚举明细信息
	 * @param enumCode
	 * @param code
	 * @return
	 */
	@Override
	public SysEnumerateDetailed getEnudInfo(String enumCode, String code) {
		SysEnumerateDetailedKey key = new SysEnumerateDetailedKey();
		key.setEunmCode(enumCode);
		key.setCode(code);
		SysEnumerateDetailed result = sysEnumerateDetailedMapper.selectByPrimaryKey(key);
		return result;
	}

	/**
	 * 更新枚举明细信息
	 * @param data
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> saveupEnudInfo(SysEnumerateDetailed data) {
		try {
			SysEnumerateDetailedKey key = new SysEnumerateDetailedKey();
			key.setEunmCode(data.getEunmCode());
			key.setCode(data.getCode());
			SysEnumerateDetailed enu = sysEnumerateDetailedMapper.selectByPrimaryKey(key);
			if (enu == null) {
				return new ResultDO<String>(BaseResultCode.DATA_NIL, Boolean.FALSE);
			}
			Subject subject = SecurityUtils.getSubject();
			String account = (String)subject.getPrincipal();
			data.setUpdateUser(account);
			
			int ups = sysEnumerateDetailedMapper.updateByPrimaryKeySelective(data);
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
	 * 删除枚举明细信息
	 * @param data
	 * @return
	 */
	@Override
	@Transactional
	public ResultDO<String> removeEnudInfo(SysEnumerateDetailed data) {
		try {
			SysEnumerateDetailedKey key = new SysEnumerateDetailedKey();
			key.setEunmCode(data.getEunmCode());
			key.setCode(data.getCode());
			int del = sysEnumerateDetailedMapper.deleteByPrimaryKey(key);
			if (del > 0) {
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
