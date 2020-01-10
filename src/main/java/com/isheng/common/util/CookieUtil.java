package com.isheng.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description：Cookie工具类
 *
 * @author：isheng92
 * @createTime：2019年6月20日 下午11:14:23
 */
public class CookieUtil {
	
	/**
	 * set cookie by 1 day
	 * @param response
	 * @param name
	 * @param value
	 */
	public static void setCookie(HttpServletResponse response, String name, String value) {
		setCookie(response, name, value, 60*60*24);
	}
	
	/**
	 * set cookie by 1 day
	 * @param response
	 * @param name
	 * @param value
	 * @param path
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String path ) {
		setCookie(response, name, value, path, 60*60*24);
	} 
	
	/**
	 * set cookie by second time to root path
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
		setCookie(response, name, value, "/", maxAge);
	}
	
	/**
	 * set cookie by second time to path
	 * @param response
	 * @param name
	 * @param value
	 * @param path
	 * @param maxAge
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
		Cookie cookie = new Cookie(name, null);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		try {
			cookie.setValue(URLEncoder.encode(value, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.addCookie(cookie);
	}
	
	/**
	 * get cookie by assign name
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		return getCookie(request, null, name, false);
	}
	
	/**
	 * get cookie by assign name and remove this cookie
	 * @param request
	 * @param response
	 * @param name
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, HttpServletResponse response,  String name) {
		return getCookie(request, response, name, true);
	}
	
	/**
	 * get cookie by name and whether remove this cookie
	 * @param request
	 * @param response
	 * @param name
	 * @param isRemove
	 * @return
	 */
	public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name, boolean isRemove) {
		String value = null;
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					try {
						value = URLDecoder.decode(cookie.getValue(), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				if (isRemove) {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
		return value;
	}
}
