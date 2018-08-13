package com.bolue.oa.util;

import java.io.Serializable;
import java.util.List;


import lombok.Data;

/**
 * 存储分页数据
 * @author yuanxh
 *
 * @param <T>
 */
@Data
public class PageDTO<T> implements Serializable {
	private static final long serialVersionUID = 5780766505173762684L;
	private Integer total;
	private List<T> rows;

}
