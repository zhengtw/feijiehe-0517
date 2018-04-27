package com.ailimq.demo;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.jfhealthcare.common.exception.RmisException;
import com.jfhealthcare.common.utils.HttpClientUtils;


public class ConsumerTest {
	
	public static void main(String[] args) {
		HttpClientUtils instance = HttpClientUtils.getInstance();
		String httpGet = instance.httpGetByWaitTime(StringUtils.trim("http://180.167.46.105:8915/tb?")+"jpgurl=http%3A%2F%2Flocalhost%2Fimg%2Ftb.png", 10000, 20000);
		if(StringUtils.isNotBlank(httpGet)){
			Map aiResult = JSON.parseObject(httpGet, Map.class);
			Map tbMap = (Map) aiResult.get("tb_consistency");
			String tbOj = (String) tbMap.get("cn");
			if(StringUtils.equalsAny(tbOj, "高度符合", "中度符合")) {
				System.out.println("疑似结核，请进行进一步检查以明确诊断");	
			}
		}else{
			throw new RmisException("AI请求连接超时或处理超时");
		}
//		String age="008";
//		
//		Matcher matcher = Pattern.compile("[1-9]").matcher(age);
//		if(matcher.find()) {
//			System.out.println(matcher.start());
//			System.out.println(age.substring(matcher.start()));
//		}
//		
		
		
//		boolean startsWithAny = StringUtils.startsWithAny(age, new String[] {"0","00"});
//		System.out.println(startsWithAny);
//		boolean startsWith = age.startsWith("0");
//		System.out.println(startsWith);
//		int length = age.length();
//		System.out.println(length);
//		String substring = age.substring(1);
//		System.out.println(substring);
	}
	
//	public static void main(String[] args) {
//		String ptnAge=null;
//		String ptnAgeUnit=null;
//		String ptnAgeUnitCode=null;
//		String birthDay="19431225";
//		boolean validDate = DateUtils.isValidDate(birthDay, DateUtils.patternB);
//		if(validDate) {
//		  Date myday = DateUtils.stringToDate(birthDay, DateUtils.patternB);
//		  Calendar cal = Calendar.getInstance();  
//		  if(cal.after(myday)) {
//			   int yearNow = cal.get(Calendar.YEAR);  
//		       int monthNow = cal.get(Calendar.MONTH);  
//		       int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);  
//		       cal.setTime(myday);  
//		  
//		       int yearBirth = cal.get(Calendar.YEAR);  
//		       int monthBirth = cal.get(Calendar.MONTH);  
//		       int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);  
//			  
//		       int temYear=yearNow-yearBirth;
//		       int temMonth=monthNow-monthBirth;
//		       int temDay=dayOfMonthNow-dayOfMonthBirth;
//			  
//		       if (temMonth<=0) {  
//		            if (temMonth==0) {  
//		                if (temDay<0) temYear--;  
//		            }else{  
//		            	temYear--;  
//		            }  
//		        }
//		       
//		       if(temYear>0) {
//		    	    ptnAge=String.valueOf(temYear);
//		   		    ptnAgeUnit="岁";
//		   		    ptnAgeUnitCode="3402";
//		       }else if(temMonth>0){
//		    	    ptnAge=String.valueOf(temMonth);
//		   		    ptnAgeUnit="月";
//		   		    ptnAgeUnitCode="3427";
//		       }else if(temDay>0) {
//		    	   if(temDay>7) {
//		    		   ptnAge=String.valueOf(temDay/7);
//			   		   ptnAgeUnit="周";
//			   		   ptnAgeUnitCode="6009";
//		    	   }else {
//		    		   ptnAge=String.valueOf(temDay);
//			   		   ptnAgeUnit="天";
//			   		   ptnAgeUnitCode="3428";
//		    	   }
//		       }
//		  }
//		
//		}
//    }

//	public static void main(String[] args) {
//		 String birthDay="19431225";
//		 Date myday = DateUtils.stringToDate(birthDay, DateUtils.patternB);
//		 boolean after = new Date().after(myday);
//		 if(after) {
//			 System.out.println("myday " + myday + " is before current date.");
//		 }
//	      // create calendar objects.
//	      Calendar cal = Calendar.getInstance();
//	      Calendar future = Calendar.getInstance();
//
//	      // print the current date
//	      System.out.println("Current date: " + cal.getTime());
//
//	      // change year in future calendar
//	      future.set(Calendar.YEAR, 2015);
//	      System.out.println("Year is " + future.get(Calendar.YEAR));
//
//	      // check if calendar date is after current date
//	      Date time = future.getTime();
//	      if (future.after(cal)) {
//	         System.out.println("Date " + time + " is after current date.");
//	      }else {
//	    	  System.out.println("Date " + time + " is before current date."); 
//	      }
//
//	   }
}
