package com.isheng.common.constant.enums;

import com.isheng.common.base.enums.Enum;

/**
 * @description：
 *
 * @author：isheng92
 * @createTime：2019年7月28日 下午10:39:34
 */
public enum Status implements Enum<String>{
	
	/**
	 * 禁用
	 */
	DISABLED(0, "禁用"),
	/**
	 * 启用
	 */
	ENDBLED(1, "启用");
	
	private Integer code;
	
	private String text;
	
	Status(int code, String text) {
		this.code = code;
		this.text = text;
	}

	public int getCode() {
		return code;
	}

	public static Status getEnum(int code) {
		for (Status e : values()) {
			if (e.code == code) {
				return e;
			}
		}
		return null;
	}

	@Override
	public String getValue() {
		return this.text;
	}
	
}
