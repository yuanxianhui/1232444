package com.bolue.oa.util;

import java.io.Serializable;

/**
 * @ClassName: BaseResultDO
 * @Description: TODO 返回基础对象
 * @author yuanxh
 *  
 */
public class BaseResultDO implements Serializable {
	private static final long serialVersionUID = 4455702538105064491L;
	private boolean success = true;
	private String resultCode;
	protected String errorMessage;
		
	/**
	 *获取success 
	 *@return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 *设置success
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 *获取resultCode 
	 *@return the resultCode
	 */
	public String getResultCode() {
		return resultCode;
	}
	/**
	 *设置resultCode
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	/**
	 *获取errorMessage 
	 *@return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 *设置errorMessage
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
