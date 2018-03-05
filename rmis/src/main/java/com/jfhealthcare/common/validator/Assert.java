package com.jfhealthcare.common.validator;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import com.jfhealthcare.common.exception.RmisException;

/**
 * 数据校验
 * @author xujinma
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RmisException(message);
        }
    }
    
    public static void isAnyBlank(String message, String... css) {
        if (StringUtils.isAnyBlank(css)) {
            throw new RmisException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (ObjectUtils.isEmpty(object)) {
            throw new RmisException(message);
        }
    }
    
    public static void isNotNull(Object object, String message) {
        if (!ObjectUtils.isEmpty(object)) {
            throw new RmisException(message);
        }
    }
    
    public static void isListOnlyOne(List<?> list, String message) {
    	if(ObjectUtils.isEmpty(list) || list.size()>1){
			throw new RmisException(message);
		}
    }
}
