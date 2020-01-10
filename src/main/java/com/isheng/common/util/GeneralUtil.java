/**
 * Copyright (c) 2011-2019 All Rights Reserved.
 * 文件名：GeneralUtil.java
 * 创建人：isheng92 
 * 创建时间：2019年6月16日 上午9:22:15
 * 文件描述：
 */
package com.isheng.common.util;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：isheng92
 * 时间：2019年6月16日 上午9:22:15
 * 描述：项目通用工具类
 */
public class GeneralUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(GeneralUtil.class);
	
	/**
	 * 获取指定Class类所在项目的resources目录中的文件绝对路径
	 * @return
	 */
	public static String getResourcesPath(Class<?> clazz, String resourceName) {
		try {
			String path = clazz.getClassLoader().getResource(resourceName).getPath();
			return new String(path.getBytes(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("获取指定resource路径异常：Class={}, resourceName={}", clazz, resourceName);
			throw new RuntimeException("获取指定resource文件路径异常");
		}
	}
	
}
