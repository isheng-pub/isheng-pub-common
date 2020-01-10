/**
 * Copyright (c) 2011-2019 All Rights Reserved.
 *
 * @description：
 *
 * @fileName：Enum.java
 * @author：isheng92 
 * @dateTime：2019年11月5日 下午10:27:16
 */
package com.isheng.common.base.enums;

/**
 * @description：枚举基类
 *
 * @author：isheng92
 * @createTime：2019年11月5日 下午10:27:16
 */
public interface Enum <V> {
	
	/**
	 * 存储至
	 * @return
	 */
	V getValue();
	
	/**
	 * 等于
	 * @param value
	 * @return
	 */
	default boolean is(Object value) {
		return this.getValue().equals(value);
	}
	
	/**
	 * 值转换
	 * @param enumType
	 * @param value
	 * @return
	 */
	static <V, E extends Enum<V>> E valueOf(Class<E> enumType, Object value) {
		for (E e : enumType.getEnumConstants()) {
			if (e.is(value)) {
				return e;
			}
		}
		return null;
	}

}
