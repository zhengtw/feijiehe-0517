package com.jfhealthcare.common.utils;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

public class RmisRandomUtils {
	public  static String getRandomNumForOrgCode(){
		return  DateUtils.dateToString(new Date(), DateUtils.patternHo)+RandomStringUtils.randomNumeric(5);
	}
}
