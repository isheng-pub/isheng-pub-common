package com.isheng.common.util;

import java.util.Collections;
import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isheng.common.constant.Constant;

public class PageHelperUtil extends PageHelper{
	
	/**
	 * 构建分页Page对象
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public static Page<?> genPage(Integer pageNum, Integer pageSize) {
		if (null == pageNum || pageNum <= 0) {
			pageNum = Constant.Default.PAGE_NUM;
		}
		if (null == pageSize || pageSize <= 0) {
			pageSize = Constant.Default.PAGE_SIZE;
		}
		return PageHelper.startPage(pageNum, pageSize);
	}
	
	/**
	 * 构建PageInfo对象
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static <T> PageInfo<T> genPageInfo(List<T> list) {
		if (null == list) {
			list = Collections.emptyList();
		}
		return new PageInfo<T>(list);
	}

}
