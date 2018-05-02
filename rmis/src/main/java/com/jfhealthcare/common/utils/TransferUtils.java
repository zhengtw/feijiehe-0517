package com.jfhealthcare.common.utils;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.jfhealthcare.common.validator.Assert;

import lombok.extern.slf4j.Slf4j;

/**
 * 属性转换
 * 
 * @author xujinma
 */
@Slf4j
public class TransferUtils {
	
	public static void copyProperties(Object objectRequest,Object orignal) {
		Assert.isNull(objectRequest, "请求对象不能为空");
		Assert.isNull(orignal, "附属性原对象不能为空");
		BeanUtils.copyProperties(objectRequest, orignal);
    }

	public static void copyPropertiesIgnoreNull(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
	
   public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null || srcValue=="") {
            	emptyNames.add(pd.getName());
            } 
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
