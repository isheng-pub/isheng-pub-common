/**
 * Copyright (c) 2011-2019 All Rights Reserved.
 *
 * @description：
 *
 * @fileName：DateUtil.java
 * @author：isheng92 
 * @dateTime：2019年6月20日 下午11:38:32
 */
package com.isheng.common.util;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * @description：时间工具类
 *
 * @author：isheng92
 * @createTime：2019年6月20日 下午11:38:32
 */
public class DateUtil extends DateUtils {

	private static final String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
			"yyyy.MM.dd HH:mm", "yyyy.MM" };

	/**
	 * parse date to string, default pattern 'yyyy-MM-dd' if patterns is null;
	 * @param date
	 * @param patterns
	 * @return
	 */
	public static String formatDate(Date date, Object... patterns) {
		String formatDate = null;
		if (null != patterns && patterns.length > 0) {
			formatDate = DateFormatUtils.format(date, patterns[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * get now date string format "yyyy-MM-dd"
	 * @return
	 */
	public static final String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * get now date string format pattern, if pattern is null, default 'yyyy-MM-dd'
	 * @param pattern
	 * @return
	 */
	public static final String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * parse date to dateTime with pattrn 'yyyy-MM-dd HH:mm:ss'
	 * @param date
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * get now time with pattern 'HH:mm:ss'
	 * @return
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * get now dateTime with pattern 'yyyy-MM-dd HH:mm:ss'
	 * @return
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * get now year (yyyy)
	 * @return
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * get now month with pattern(MM);
	 * @return
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * get now day 'dd'
	 * @return
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * get now week (E)
	 * @return
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * parse string date to Date
	 * @param str
	 * @return
	 */
	public static Date parseDate(Object str) {
		if (null == str) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 60 * 1000);
	}

	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 1000);
	}

	/**
	 * 转换为时间（天，时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - (day * 24));
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
	}

	/**
	 * 获取两个日期之间的天数
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

}
