package com.isheng.common.base.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 统一异常处理类
 * @author isheng92
 * @date 2019年11月28日 下午10:37:50
 */
public class ExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
	
	public String exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
		logger.error("统一异常处理：", exception);
		if (null != request.getHeader("X-Requested-With") && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
			request.setAttribute("requestHeader", "ajax");
		}
		
		// shiro没有权限异常
//		if (exception instanceof UnauthorizedException) {
//			return "/403.jsp";
//		}
//		
//		// shiro会话已过期异常
//		if (exception instanceof InvalidSessionException) {
//			return "/error.jsp";
//		}
		
		return "/error.jsp";
		
	}
	

}
