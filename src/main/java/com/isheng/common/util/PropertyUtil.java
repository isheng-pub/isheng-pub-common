/**
 * Copyright (c) 2011-2019 All Rights Reserved.
 * 文件名：PropertyUtil.java
 * 创建人：isheng92 
 * 创建时间：2019年6月18日 下午11:47:37
 * 文件描述：
 */
package com.isheng.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作者：isheng92
 * 时间：2019年6月18日 下午11:47:37
 * 描述：属性工具类
 */
public class PropertyUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
	
	private final Properties properties;
	
	public PropertyUtil(String...resourcesPahts ) {
		properties = loadProperties(resourcesPahts);
	}
	
	public Properties getProperties() {
		return properties;
	}
	
	/**
	 * 获取property，优先取System的，最终取不到返回"";
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		String sysProperties = System.getProperty(key);
		if (null != sysProperties) {
			return sysProperties; 
		}
		if (properties.containsKey(key)) {
			return properties.getProperty(key);
		}
		return "";
	}
	
	/**
	 * 获取String类型的property，优先取System的，如果都为null时报异常
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		String value = getProperty(key);
		if (null == value) {
			throw new NoSuchElementException();
		}
		return value;
	}
	
	/**
	 * 取出Integer类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
	 */
	public Integer getInteger(String key) {
		String value = getValue(key);
		if (null == value) {
			throw new NoSuchElementException();
		}
		return Integer.valueOf(value);
	}
	
	/**
	 * 取出Integer类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
	 */
	public Integer getInteger(String key, Integer defaultValue) {
		String value = getValue(key);
		return (null != value) ? Integer.valueOf(value) : defaultValue;
	}
	
	/**
	 * 取出Double类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
	 */
	public Double getDouble(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Double.valueOf(value);
	}

	/**
	 * 取出Double类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
	 */
	public Double getDouble(String key, Integer defaultValue) {
		String value = getValue(key);
		return value != null ? Double.valueOf(value) : defaultValue;
	}
	
	/**
	 * 取出Boolean类型的Property，但以System的Property优先.如果都为Null抛出异常,如果内容不是true/false则返回false.
	 */
	public Boolean getBoolean(String key) {
		String value = getValue(key);
		if (value == null) {
			throw new NoSuchElementException();
		}
		return Boolean.valueOf(value);
	}

	/**
	 * 取出Boolean类型的Property，但以System的Property优先.如果都为Null则返回Default值,如果内容不为true/false则返回false.
	 */
	public Boolean getBoolean(String key, boolean defaultValue) {
		String value = getValue(key);
		return value != null ? Boolean.valueOf(value) : defaultValue;
	}
	
	/**
	 * 载入多个文件, 
	 */
	private Properties loadProperties(String... resourcesPahts) {
		Properties props = new Properties();
		InputStream inStream = null;
		for (String location : resourcesPahts) {
			try {
				inStream = this.getClass().getResourceAsStream(location);
				props.load(inStream);
			} catch (IOException e) {
				logger.error("Could not load properties from path:{}, {}", location, e.getMessage());
			} finally {
				IOUtils.closeQuietly(inStream);
			}
		}
		return props;
	}
}
