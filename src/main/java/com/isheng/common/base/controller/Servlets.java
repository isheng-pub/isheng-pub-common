package com.isheng.common.base.controller;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.Validate;
import org.apache.commons.text.StringTokenizer;

import com.google.common.net.HttpHeaders;
import com.isheng.common.codec.EncodeUtil;

/**
 * Http与Servlet工具类
 * @author isheng92
 * @date 2019年11月24日 下午5:47:00
 */
public class Servlets {
	
	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;
	
	/**
	 * 设置客户端缓存过期时间的header
	 * @param response
	 * @param expiresSeconds
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		response.setDateHeader(HttpHeaders.EXPIRES, System.currentTimeMillis() + expiresSeconds * 1000);
		response.setHeader(HttpHeaders.CACHE_CONTROL, "private, max-age=" + expiresSeconds);
	}
	
	/**
	 * 设置禁止客户端缓存的header
	 * @param response
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		response.setDateHeader(HttpHeaders.EXPIRES, 1L);
		response.addHeader(HttpHeaders.PRAGMA, "no-cache");
		// Http 1.1 header
		response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, max-age=0");
	}
	
	/**
	 * 设置LastModified Header
	 * @param response
	 * @param lastModifyDate
	 */
	public static void setLastModifyHeader(HttpServletResponse response, long lastModifyDate) {
		response.setDateHeader(HttpHeaders.LAST_MODIFIED, lastModifyDate);
	}
	
	/**
	 * 设置Etag Header.
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader(HttpHeaders.ETAG, etag);
	}
	
	/**
	 * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
	 * 
	 * 如果无修改, checkIfModify返回false ,设置304 not modify status.
	 * 
	 * @param lastModified 内容的最后修改时间.
	 */
	public static boolean checkIfModifySince(HttpServletRequest request, HttpServletResponse response,
			long lastModified) {
		long ifModifySince = request.getDateHeader(HttpHeaders.IF_MODIFIED_SINCE);
		if ((ifModifySince != -1) && (lastModified < ifModifySince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}
	
	/**
	 * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
	 * 
	 * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
	 * 
	 * @param etag 内容的ETag.
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader(HttpHeaders.IF_NONE_MATCH);
		if (null != headerValue) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");
				while(!conditionSatisfied && commaTokenizer.hasNext()) {
					String currentToken = commaTokenizer.next();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}
			
			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader(HttpHeaders.ETAG, etag);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 设置让浏览器弹出下载对话框的header
	 * @param response
	 * @param fileName 下载后的文件名
	 */
	public static void setFileDownHeader(HttpServletResponse response, String fileName) {
		try {
			//中文文件名支持
			String encodedFileName = new String(fileName.getBytes(), "iso-8859-1");
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + encodedFileName + "\"");
		} catch (UnsupportedEncodingException e) {
			e.getMessage();
		}
	}
	
	/**
	 * 取得带相同前缀的rquest parameters
	 * 
	 * 返回的结果的Parameter名已去除前缀
	 * @param request
	 * @param prefix
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
		Validate.notNull(request, "request must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map<String, Object> params = new TreeMap<String, Object>();
		String pre = prefix;
		if (null == pre) {
			pre = "";
		} 
		while(paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(pre) || paramName.startsWith(pre)) {
				String unprefixed = paramName.substring(pre.length());
				String[] values = request.getParameterValues(paramName);
				if (null == values || values.length == 0) {
					values = new String[] {};
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		
		return params;
	}
	
	/**
	 * 组合Parameters生成Query String的Parameter部分,并在paramter name上加上prefix.
	 * 
	 */
	public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix) {
		StringBuilder queryStringBuilder = new StringBuilder();

		String pre = prefix;
		if (pre == null) {
			pre = "";
		}
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			queryStringBuilder.append(pre).append(entry.getKey()).append("=").append(entry.getValue());
			if (it.hasNext()) {
				queryStringBuilder.append("&");
			}
		}
		return queryStringBuilder.toString();
	}
	
	/**
	 * 客户端对Http Basic验证的Header进行编码
	 * @param userName
	 * @param password
	 * @return
	 */
	public static String encodeHttpBasic(String userName, String password) {
		String encode = userName + ":" + password;
		return "Basic" + EncodeUtil.encodeBase64(encode.getBytes());
	}
	
	/**
	 * 是否是Ajax异步请求
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String accept = request.getHeader("accept");
		String xRequestWith = request.getHeader("X-Requested-With");
//		Principal principal = UserUtils.
		return (accept != null && accept.indexOf("application/json") != -1) 
				|| (xRequestWith != null && xRequestWith.indexOf("XMLHttpRequest") != -1);
//				|| (principal != null && principal.isMobileLogin());
	}
	
	/**
	 * 得到当前请求对象
	 * @return
	 */
//	public static HttpServletRequest getRequest() {
//		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//	}

	/**
	 * 判断访问URI是否是静态文件请求
	 * @param uri
	 * @return
	 */
	public static boolean isStaticFile(String uri) {
//		if (staticFiles == null) {
//			throw new Exception("检测到配置文件中未配置静态文件信息");
//		}
//		if (StringUtils.endsWith(uri, staticFiles) && !StringUtils.endsWith(uri, urlSuffix)
//				&& !StringUtils.endsWithAny(uri, ".jsp") && !StringUtils.endsWithAny(uri, ".java")) {
//			return true;
//		}
		return false;
	}
}
