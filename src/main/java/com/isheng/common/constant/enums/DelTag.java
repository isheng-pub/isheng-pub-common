/**
 * Copyright (c) 2011-2019 All Rights Reserved.
 *
 * @description：
 *
 * @fileName：Del.java
 * @author：isheng92 
 * @dateTime：2019年7月28日 下午11:23:11
 */
package com.isheng.common.constant.enums;

import com.isheng.common.base.enums.Enum;

/**
 * @description：
 *
 * @author：isheng92
 * @createTime：2019年7月28日 下午11:23:11
 */
public enum DelTag implements Enum<Integer> {

	/**
	 * 正常未删除
	 */
	UNDEL(0),
	/**
	 * 已删除
	 */
	DELD(1);

	private int code;

	private DelTag(int code) {
		this.code = code;
	}

	@Override
	public Integer getValue() {
		return this.code;
	}

}
