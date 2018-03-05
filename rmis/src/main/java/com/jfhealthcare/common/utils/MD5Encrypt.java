package com.jfhealthcare.common.utils;
import java.security.MessageDigest;

import org.apache.commons.lang3.StringUtils;

public class MD5Encrypt {
 
 
    public static String byteArrayToString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToNumString(b[i]));
        }
        return resultSb.toString();
    }
 
    private static String byteToNumString(byte b) {
        int _b = b;
        if (_b < 0) {
            _b = 256 + _b;
        }
        return String.valueOf(_b);
    }
 
 
    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToString(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
 
        }
        return resultString;
    }
 
    public static String MD5Encode32(String origin) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToString(md.digest(resultString.getBytes()));
            if(StringUtils.isNotBlank(resultString)) {
            	if(resultString.length()>32) {
            		resultString=resultString.substring(0, 32);
            	}
            }else {
            	return null;
            }
        } catch (Exception ex) {
 
        }
        return resultString;
    }
    
}