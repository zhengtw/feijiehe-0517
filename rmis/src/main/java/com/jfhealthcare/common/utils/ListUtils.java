package com.jfhealthcare.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xujinma
 */
@Slf4j
public class ListUtils {
	public static List getFiledList(List list, String filed) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List filedList = new ArrayList();
		try {

			for (Object obj : list) {
				Class clazz = obj.getClass();// 获取集合中的对象类型
				Field[] fds = clazz.getDeclaredFields();// 获取他的字段数组
				for (Field field : fds) {// 遍历该数组
					String fdname = field.getName();// 得到字段名，

					Method method = clazz.getMethod("get" + change(fdname),null);// 根据字段名找到对应的get方法，null表示无参数

					if (null != method && filed.equals(fdname)) {
						Object val = method.invoke(obj, null);
						filedList.add(val);
					}

				}
			}

		} catch (Exception e) {
			log.error("list取属性异常！", e);
		} 
		return filedList;
	}
	
	/**
	 * @param src
	 *            源字符串
	 * @return 字符串，将src的第一个字母转换为大写，src为空时返回null
	 */
	public static String change(String src) {
		if (src != null) {
			StringBuffer sb = new StringBuffer(src);
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			return sb.toString();
		} else {
			return null;
		}
	}
}
