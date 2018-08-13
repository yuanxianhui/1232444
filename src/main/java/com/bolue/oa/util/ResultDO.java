package com.bolue.oa.util;

/**
 * 存储返回结果
 * 
 * @ClassName: ResultDO
 * @Description: TODO 返回参数对象
 * @author yuanxh
 * 
 * @param <T>
 */
public class ResultDO<T> extends BaseResultDO {
	private static final long serialVersionUID = -3434272908589805662L;

	private T data;

	public ResultDO() {
	}

	public ResultDO(String key, boolean result) {
		setResultCode(key);
		setErrorMessage(BaseResultCode.getValueWithKey(key));
		setSuccess(result);
	}

	public ResultDO(T data) {
		this.data = data;
	}

	public static <T> ResultDO<T> getResult() {
		return new ResultDO<T>();
	}

	public T getModule() {
		return data;
	}

	public void setModule(T data) {
		this.data = data;
	}
}
