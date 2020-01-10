/**
 * Copyright (c) 2011-2019 All Rights Reserved.
 * 文件名：CollectionUtil.java
 * 创建人：isheng92 
 * 创建时间：2019年6月4日 下午10:48:47
 * 文件描述：集合工具类
 */
package com.isheng.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：isheng92
 * 时间：2019年6月4日 下午10:48:47
 * 描述：集合工具类
 */
public class CollectionUtil {
	
	private CollectionUtil() {
		
	}
	
	/**
	 * 判断集合为空
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.size() == 0;
	}
	
	/**
	 * 判断集合不为空
	 * @param collection
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return collection != null && collection.size() > 0;
	}
	
	/**
	 * List排序
	 * @param list
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <T> List<T> sort(List<T> list) {
		if (null != list && !list.isEmpty()) {
			Collections.sort((List)list);
		}
		return list;
	}
	
	/**
	 * 字符串集合排序
	 * @param list
	 * @return
	 */
	public static List<String> sotrSimpeName(List<String> list) {
		if (null != list && list.size() > 0) {
			Collections.sort(list, SIMPLE_NAME_COMPARATOR);
		}
		return list;
	}
	
	private static final Comparator<String> SIMPLE_NAME_COMPARATOR = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			if (o1 == null && o2 == null) {
				return 0;
			}
			if (o1 == null) {
				return -1;
			}
			if (o2 == null) {
				return 1;
			}
			int i1 = o1.lastIndexOf('.');
			if (i1 >= 0) {
				o1 = o1.substring(i1 + 1);
			}
			int i2 = o2.lastIndexOf('.');
			if (i2 >= 0) {
				o2 = o2.substring(i2 + 1);
			}
			
			return o1.compareToIgnoreCase(o2);
		}
	};
	
	public static Map<String, String> split(List<String> list, String separator) {
		Map<String, String> result = new HashMap<String, String>();
		if (null == list || list.size() == 0) {
			return result;
		}
		for (String item : list) {
			int index = item.indexOf(separator);
			if (index == -1) {
				result.put(item, "");
			} else {
				result.put(item.substring(0, index), item.substring(index + 1));
			}
		}
		return result;
	}
	
	public static Map<String, Map<String, String>> splitAll(Map<String, List<String>> list, String separator) {
		if (null == list) {
			return null;
		}
		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
		for (Map.Entry<String, List<String>> entry : list.entrySet()) {
			result.put(entry.getKey(), split(entry.getValue(), separator));
		}
		return result;
	}
	
	public static String join(List<String> list, String separator) {
		StringBuilder sb = new StringBuilder();
		for (String s : list) {
			if (sb.length() > 0) {
				sb.append(separator);
			}
			sb.append(s);
		}
		return sb.toString();
	}
	
	public static List<String> join(Map<String, String> map, String separator) {
		if (null == map) {
			return null;
		}
		List<String> list = new ArrayList<>();
		if (map == null || map.size() == 0) {
			return list;
		}
		String key = "";
		String value = "";
		for (Map.Entry<String, String> entry : map.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			if (value == null || value.length() == 0) {
				list.add(key);
			} else {
				list.add(key + separator + value);
			}
		}
		return list;
	}
	
	public static boolean objectEquals(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null) {
			return true;
		}
		if (obj1 == null || obj2 == null) {
			return false;
		}
		return obj1.equals(obj2);
	}
	
	public static boolean mapEquals(Map<?, ?> map1, Map<?, ?> map2) {
		if (map1 == null && map2 == null) {
			return true;
		}
		if (map1 == null || map2 == null) {
			return false;
		}
		if (map1.size() != map2.size()) {
			return false;
		}
		for (Map.Entry<?, ?> entry : map1.entrySet()) {
			Object key = entry.getKey();
			Object value1 = entry.getValue();
			Object value2 = map2.get(key);
			if (! objectEquals(value1, value2)) {
				return false;
			}
		}
		return true;
	}
	
	public static Map<String, String> toStringMap(String... pairs) {
		Map<String, String> map = new HashMap<String, String>();
		if (pairs.length > 0) {
			if (pairs.length % 2 != 0) {
				throw new IllegalArgumentException("pairs must b even.");
			}
			for (int i = 0; i < pairs.length; i = i + 2) {
				map.put(pairs[i], pairs[i + 1]);
			}
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> toMap(Object... pairs) {
		Map<K, V> ret = new HashMap<K, V>();
		if (pairs == null || pairs.length == 0) {
			return ret;
		}
		if (pairs.length % 2 != 0) {
			throw new IllegalArgumentException("Map pairs can not be odd number.");
		}
		int len = pairs.length / 2;
		for (int i = 0; i < len; i++) {
			ret.put((K) pairs[2 * i], (V) pairs[2 * i + 1]);
		}
		return ret;
	}
	
}
 