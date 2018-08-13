package com.bolue.oa.system.common;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

	public static final String NULL_STRING = null;

	public static byte[] stringToByte(String str) {
		if (str == null) {
			return null;
		}
		byte[] byteArray = str.getBytes();
		return byteArray;
	}
}
