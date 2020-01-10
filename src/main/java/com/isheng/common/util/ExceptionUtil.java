/**
 * Copyright (c) 2011-2019 All Rights Reserved.
 *
 * @description：
 *
 * @fileName：ExceptionUtil.java
 * @author：isheng92 
 * @dateTime：2019年6月21日 上午12:13:31
 */
package com.isheng.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

/**
 * @description：异常处理工具类
 *
 * @author：isheng92
 * @createTime：2019年6月21日 上午12:13:31
 */
public class ExceptionUtil {
  
	/**
	 * 将CheckedException异常转换为UncheckedException
	 * @param e
	 * @return
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException)e;
		} else {
			return new RuntimeException(e);
		}
	}
	
	/**
	 * 将ErrorStack转化为String.
	 * @param e
	 * @return
	 */
	public static String getStackTraceAsString(Throwable e) {
		if (null == e) {
			return "";
		}
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}
	
	/**
	 * 判断异常是否由某些底层的异常引起.
	 */
	@SafeVarargs
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex.getCause();
		while(cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}
	
	/**
	 * 在request中获取异常类
	 * @param request
	 * @return
	 */
	public static Throwable getThrowable(HttpServletRequest request) {
		Throwable ex = null;
		if (request.getAttribute("exception") != null) {
			ex = (Throwable) request.getAttribute("exception");
		} else if (request.getAttribute("javax.servlet.error.exception") != null){
			ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
		}
		return ex;
	}
}
