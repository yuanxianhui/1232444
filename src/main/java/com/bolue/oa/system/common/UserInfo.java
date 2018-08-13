package com.bolue.oa.system.common;

import java.util.List;

import com.bolue.oa.system.entity.SysAccount;
import com.bolue.oa.system.entity.SysDepartment;
import com.bolue.oa.system.entity.SysMenu;
import com.bolue.oa.system.entity.SysUser;

public class UserInfo{

	public static SysUser sysUser;
	
	public static List<SysMenu> sysMenu;
	
	public static SysAccount sysAccount;
	
	public static SysDepartment sysDepartment;
	
	public void setSysAccount(SysAccount sysAccount) {
		UserInfo.sysAccount = sysAccount;
	}
	
}
