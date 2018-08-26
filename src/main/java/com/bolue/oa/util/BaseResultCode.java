package com.bolue.oa.util;

import java.util.HashMap;
import java.util.Map;
/**
 * 设置提示消息
 * @author yuanx
 *
 */
public abstract class BaseResultCode {
	public static Map<String, String> map = new HashMap<String, String>();
	
	public final static String TRUE = "0000";
	public final static String COMMON_FAIL = "0001";
	public final static String LOGIN_ACCOUNT = "0002";
	public final static String LOGIN_PASSWORD = "0003";
	public final static String LOGIN_WARN = "0004";
	public final static String LOGIN_MANY = "0005";
	public final static String DIFF_PASSWORD = "0006";
	public final static String SAVE_SUCCESS = "0007";
	public final static String SAVE_FAIL = "0008";
	public final static String SAVE_REPEAT = "0009";
	public final static String FALSE = "0010";
	public final static String DATA_REPEAT = "0011";
	public final static String DATA_NIL = "0012";
	public final static String FORM_DATA = "0013";
	public final static String DEL_SUCCESS = "0014";
	public final static String DEL_FAIL = "0015";
	public final static String UP_ACCOUNT_FAIL = "0016";
	static {
		map.put(TRUE, "操作成功！");
		map.put(COMMON_FAIL, "操作失败，请重试操作或联系客服人员！");
		map.put(LOGIN_ACCOUNT, "账号为空！");
		map.put(LOGIN_PASSWORD, "密码为空！");
		map.put(LOGIN_WARN, "账号或密码不正确！");
		map.put(LOGIN_MANY, "登陆次数过多！");
		map.put(DIFF_PASSWORD, "两次密码不相同！");
		map.put(SAVE_SUCCESS, "保存成功！");
		map.put(SAVE_FAIL, "保存失败！");
		map.put(SAVE_REPEAT, "已存在该账号！");
		map.put(FALSE, "操作失败！");
		map.put(DATA_REPEAT, "数据已存在！");
		map.put(DATA_NIL, "数据不存在！");
		map.put(FORM_DATA, "请检查必填项！");
		map.put(DEL_SUCCESS, "删除成功！");
		map.put(DEL_FAIL, "删除失败！");
		map.put(UP_ACCOUNT_FAIL, "账号信息更新不成功！");
	}
	
	public static String getValueWithKey(String key) {
		return map.get(key);
	}
}
