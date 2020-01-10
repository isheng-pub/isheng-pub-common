/**
 * Copyright (c) 2011-2019 All Rights Reserved.
 *
 * @description：
 *
 * @fileName：SysConstant.java
 * @author：isheng92 
 * @dateTime：2019年11月5日 下午10:10:03
 */
package com.isheng.common.constant;

/**
 * @description：<系统常量>
 *
 * @author：isheng92
 * @createTime：2019年11月5日 下午10:10:03
 */
public interface Constant {
	
	interface Default {

		/**
		 * 基本包路径：com.isheng
		 */
		String BASE_PACKAGE = "com.isheng";
		
		/**
		 * 用户session前缀
		 */
		String SESSION = "SESSION:";
		
		/**
		 * 空字符串
		 */
		String EMPTY = "";
		
		/**
		 * 分页：默认起始页码
		 */
		Integer PAGE_NUM = 1;
		
		/**
		 * 分页：默认每页记录数
		 */
		Integer PAGE_SIZE = 10;
	}
	
	interface Result {
		/**
		 * 调用失败返回的code
		 */
		String FAILED_CODE = "-1";
		
		/**
		 * 失败后返回的消息
		 */
		String FAILED_MSG = "failed";
		
		/**
		 * 成功code
		 */
		String SUCCESS_CODE = "0";
		
		/**
		 * 成功后的消息
		 */
		String SUCCESS_MSG = "success";
	}
	
	interface Symbol {
		
		/**
		 * 包层级标识：.
		 */
		String DOT = ".";
		
		/**
		 * 路径层级标识：/
		 */
		String SLASH = "/";
		
		/**
		 * 中划线：-
		 */
		String STRIKE = "-";
		
		/**
		 * 下划线：_
		 */
		String UNDERLINE = "_";
		
		/**
		 * 换行符：\r\n
		 */
		String LINE_BREAK = "\r\n";
	}
	
	interface Second {
		
		/**
		 * 1分钟
		 */
		int MINUTE = 60;
		/**
		 * 30分钟
		 */
		int MINUTE30 = 60 * 30;
		/**
		 * 1小时
		 */
	    int HOUR = 60 * 60;
	    /**
	     * 1天
	     */
	    int DAY = 60 * 60 * 24;
	    /**
	     * 1周
	     */
	    int WEEK = 60 * 60 * 24 * 7;
	    /**
	     * 1个月
	     */
	    int MONTH = 60 * 60 * 24 * 7 * 30;
	    /**
	     * 1年
	     */
	    int YEAR = 60 * 60 * 24 * 7 * 30 * 12;
	}
	
}
