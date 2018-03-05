package com.jfhealthcare.demo;

import java.util.Date;
import java.util.List;

import com.jfhealthcare.common.utils.DateUtils;

public class DateTest {

	public static void main(String[] args) {
		
//		Date someDaysBeforeAfter = DateUtils.getSomeDaysBeforeAfter(new Date(), 1);
//		System.out.println(DateUtils.dateToString1(someDaysBeforeAfter,null));
//		Date someMonthsBeforeAfter = DateUtils.getSomeMonthsBeforeAfter(new Date(),-5);
//		System.out.println(DateUtils.dateToString1(someMonthsBeforeAfter,null));
//		Date someYearsBeforeAfter = DateUtils.getSomeYearsBeforeAfter(new Date(),-1);
//		System.out.println(DateUtils.dateToString1(someYearsBeforeAfter,null));
		List<Date> checkTime = DateUtils.getCheckTime(1650);
		System.out.println(DateUtils.dateToString1(checkTime.get(0),null));
		System.out.println(DateUtils.dateToString1(checkTime.get(1),null));
	}
}
