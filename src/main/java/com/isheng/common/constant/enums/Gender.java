/**
 * Copyright (c) 2011-2019 All Rights Reserved.
 *
 * @description：
 *
 * @fileName：Gender.java
 * @author：isheng92 
 * @dateTime：2019年7月28日 下午10:40:15
 */
package com.isheng.common.constant.enums;

/**
 * @description：性别枚举类
 *
 * @author：isheng92
 * @createTime：2019年7月28日 下午10:40:15
 */
public enum Gender {

	/**
	 * 保密
	 */
	SECRET(-1, "保密"),
	/**
	 * 男
	 */
	MALE(1, "男"),
	/**
	 * 女
	 */
	FEMALE(0, "女");

	private int code;

	private String text;

	private Gender(int code, String text) {
		this.code = code;
		this.text = text;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public static final Gender getGender(int code) {
		Gender g = Gender.SECRET;
		for (Gender e : values()) {
			if (e.code == code) {
				g = e;
				break;
			}
		}
		return g;
	}

}
