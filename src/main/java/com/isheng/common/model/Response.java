/**
 * Copyright (c) 2011-2019 All Rights Reserved.
 *
 * @description：
 *
 * @fileName：Response.java
 * @author：isheng92 
 * @dateTime：2019年11月5日 下午9:31:14
 */
package com.isheng.common.model;

import java.io.Serializable;

import com.isheng.common.constant.enums.ErrMsg;
import com.isheng.common.json.JsonMapper;

/**
 * @description：Controller统一返回信息
 *
 * @author：isheng92
 * @createTime：2019年11月5日 下午9:31:14
 */
public class Response<T> implements Serializable {

	private static final long serialVersionUID = -2745690298939302372L;
	
	private static final ErrMsg SUCCESS = ErrMsg.SUCCESS;
	
	private static final ErrMsg ERROR = ErrMsg.FAILED;
	
	/**
	 * 是否执行成功
	 */
	private boolean success;
	
	/**
	 * 状态码
	 */
	private String code;
	
	/**
	 * 提示信息
	 */
	private String msg;
	
	/**
	 * 数据
	 */
	private T data;
	
	public Response() {
		
	}
	
	public Response(boolean success, String code, String msg, T data) {
		this.success = success;
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	/**
	 * 成功
	 */
	public static Response<?> ok() {
		return new Response<>(Boolean.TRUE, SUCCESS.getCode(), SUCCESS.getMsg(), null);
	}
	
	/**
	 * 成功+消息
	 */
	public static Response<?> ok(String msg) {
		return new Response<>(Boolean.TRUE, SUCCESS.getCode(), msg, null);
	}
	
	/**
	 * 成功+数据
	 */
	public static <T> Response<T> ok(T data) {
		return new Response<T>(Boolean.TRUE, SUCCESS.getCode(), SUCCESS.getMsg(), data);
	}
	
	/**
	 * 成功+消息+数据
	 */
	public static <T> Response<T> ok(String msg, T data) {
		return new Response<T>(Boolean.TRUE, SUCCESS.getCode(), msg, data);
	}
	
	/**
	 * 失败+消息
	 */
	public static Response<?> err(String code, String msg) {
		return new Response<>(Boolean.FALSE, code, msg, null); 
	}
	
	/**
	 * 失败+数据
	 */
	public static <T> Response<T> err(String code, T data) {
		return new Response<T>(Boolean.FALSE, code, ERROR.getMsg(), data); 
	}
	
	/**
	 * 失败+消息+数据
	 */
	public static <T> Response<T> err(String code, String msg, T data) {
		return new Response<T>(Boolean.FALSE, code, msg, data); 
	}
	
	/**
	 * 当前对象转json字符串
	 */
	public String toJson() {
		return JsonMapper.toJsonString(this);
	}
	
	public Response<?> setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public Response<?>  setCode(String code) {
		this.code = code;
		return this;
	}

	public Response<?> setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Response<?> setData(T data) {
		this.data = data;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public T getData() {
		return data;
	}
	
	

}
