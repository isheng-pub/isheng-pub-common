package com.isheng.common.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class ObjectUtil {
	
	/**
	 * 判断java对象为空，空-true, 非空-false
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNull(Object obj) {
		if (null == obj) {
			return true;
		}
		if (obj instanceof String) {
			return ((String)obj).trim().replace("\\s", "").equals("");
			
		} else if (obj instanceof Collection) {
			return ((Collection)obj).isEmpty();
			
		} else if (obj instanceof Object[]) {
			Object[] objs = (Object[])obj;
			if (objs.length == 0) {
				return true;
			}
			boolean isEmpty = true;
			for (Object o : objs) {
				if (!isNull(o)) {
					isEmpty = false;
					break;
				}
			}
			return isEmpty;
		
		} else if (obj.getClass().isArray()) {
			return Array.getLength(obj) == 0;
			
		} else if (obj instanceof Map) {
			return ((Map)obj).isEmpty();
			
		}
		return false;
	}
	
	/**
	 * 判断java对象不为空, true-非空, false-空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}
	
	public static boolean isNull(Object obj, Object...items) {
		if (isNull(obj) || isNull(items)) {
			return true;
		}
		for (Object item : items) {
			if (isNull(item)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNotNull(Object obj, Object...items) {
		return !isNull(obj, items);
	}

}
