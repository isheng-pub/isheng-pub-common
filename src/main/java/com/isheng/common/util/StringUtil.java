/**
 * Copyright (c) 2011-2019 All Rights Reserved.
 * 文件名：StringUtil.java
 * 创建人：isheng92 
 * 创建时间：2019年6月2日 下午11:08:35
 * 文件描述：
 */
package com.isheng.common.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 作者：isheng92
 * 时间：2019年6月2日 下午11:08:35
 * 描述：字符串工具类
 */
public class StringUtil {
	
	private static final Pattern INT_PATTERN = Pattern.compile("^\\d+$");
	
	/**
	 * 判断两个字符串是否相等
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean equals(String s1, String s2) {
		if (s1 == null && s2 == null) {
			return true;
		}
		if (s1 == null || s2 == null) {
			return false;
		}
		return s1.equals(s2);
	}
	
	/**
	 * 判断字符串为空，或者空格
	 * @param s
	 * @return
	 */
	public static boolean isBlank(String s) {
		if (null == s || s.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串不为空，且不为空格
	 * @param s
	 * @return
	 */
	public static boolean isNotBlank(String s) {
		return !isBlank(s);
	}
	
	/**
	 * 判断字符串不为空
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (null == s || s.length() == 0) 
			return true;
		return false;
	}
	
	/**
	 * 判断字符串不为空
	 * @param s
	 * @return
	 */
	public static boolean isNotEmpty(String s) {
		return s != null && s.length() > 0;
	}
	
	public static boolean isContains(String[] ss, String s) {
		if (null != ss && ss.length > 0 && null != s && s.length() > 0) {
			for (String v : ss) {
				if (s.equals(v)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean isInteger(String s) {
		if (null == s || s.length() == 0) {
			return false;
		}
		return INT_PATTERN.matcher(s).matches();
	}
	
	public static int parseInteger(String s) {
		if (! isInteger(s)) {
			return 0;
		}
		return Integer.parseInt(s);
	}
	
	public static String empty() {
		return StringUtils.EMPTY;
	}

}



