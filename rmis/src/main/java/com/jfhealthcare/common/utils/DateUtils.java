package com.jfhealthcare.common.utils;
import java.text.DateFormat;  
import java.text.ParseException;  
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;  
import java.util.Date;  
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;  
  
/** 
 * 说明:日期工具类 
 * @version 1.0 
 */  
@Slf4j
public class DateUtils {  
  
    /** 
     * @param yyyy-MM-dd 
     * @return 
     */  
    public static final String patternA = "yyyy-MM-dd";  
    /** 
     * @param yyyyMMdd 
     * @return 
     */  
    public static final String patternB = "yyyyMMdd";  
    /** 
     * @param yyyy-MM-dd HH-mm-ss 
     * @return 
     */  
    public static final String patternC = "yyyy-MM-dd HH-mm-ss";  
    /** 
     * @param yyyy:MM:dd HH:mm:ss 
     * @return 
     */  
    public static final String patternD = "yyyy-MM-dd HH:mm:ss";  
    /** 
     * @param yyyy-MM-dd HH:mm:ss 
     * @return 
     */  
    public static final String patternE = "yyyy-MM-dd HH:mm";  
      
    /** 
     * @param yyyyMMddHHmmss 
     * @return 
     */  
    public static final String patternF = "yyyyMMddHHmmss";  
    public static final String patternHo = "yyyyMMddHHmm";  
      
    public static final String patternG = "yyyy年MM月dd日";  
      
    /** 
     * @param yyyy-MM 
     * @return 
     */  
    public static final String patternH = "yyyy-MM";  
      
    public static final String patternI = "yyyyMM";  
      
      
    /** 
     * 格式化日期为yyyy-MM-dd 
     * @param date 
     * @return 
     */  
    public static String formateDate(Date date){  
        return dateToString(date,patternA);  
    }  
      
    /** 
     * 格式化日期为yyyy-MM 
     * @param date 
     * @return 
     */  
    public static String formateYearAndMonth(Date date){  
        return dateToString(date,patternH);  
    }  
      
    /** 
     * @param 取当天日期 
     * @return 
     */  
    public static Date getDate() {  
        return Calendar.getInstance().getTime();  
    }  
    /** 
     * @param 取指定年月日的日期,格式为yyyy-MM-dd,HH-mm-ss 00-00-00 
     * @return 
     */  
    public static Date getDate(int year, int month, int day) {  
        Calendar cal = Calendar.getInstance();  
        cal.set(year, month-1, day,0,0,0);  
        return cal.getTime();  
          
    }  
    /** 
     * @param 取指定年,月,日,小时,分,秒的时间 
     * @return 
     */  
    public static Date getDate(int year,int month,int date,int hour,int mintue,int second)  
    {  
        Calendar cal=Calendar.getInstance();  
        cal.set(Calendar.YEAR,year);  
        cal.set(Calendar.MONTH,month-1);  
        cal.set(Calendar.DATE,date);  
        cal.set(Calendar.HOUR_OF_DAY,hour);  
        cal.set(Calendar.MINUTE,mintue);  
        cal.set(Calendar.SECOND,second);  
        return cal.getTime();  
    }  
  
  
    /** 
     * @param days=n n为-,则取n天前,n为+,则取n天后的日期 
     * @param date 
     * @param days 
     * @return 
     */  
    public static Date getSomeDaysBeforeAfter(Date date, int days){  
        GregorianCalendar gc =new GregorianCalendar();  
        gc.setTime(date);  
        gc.add(Calendar.DATE, days);  
        gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));  
        return gc.getTime();  
    }  
    
    /** 
     * @param months=n n为-,则取n月前,n为+,则取n月后的日期 
     * @param date 
     * @param months 
     * @return 
     */
    public static Date getSomeMonthsBeforeAfter(Date date, int months){  
        GregorianCalendar gc =new GregorianCalendar();  
        gc.setTime(date);  
        gc.add(Calendar.MONTH, months);  
        gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));  
        return gc.getTime();  
    }  
    
    /** 
     * @param years=n n为-,则取n年前,n为+,则取n年后的日期 
     * @param date 
     * @param years 
     * @return 
     */
    public static Date getSomeYearsBeforeAfter(Date date, int years){  
        GregorianCalendar gc =new GregorianCalendar();  
        gc.setTime(date);  
        gc.add(Calendar.YEAR, years);  
        gc.set(gc.get(Calendar.YEAR),gc.get(Calendar.MONTH),gc.get(Calendar.DATE));  
        return gc.getTime();  
    }
    /** 
     * @param 取指定日期年份 
     * @return 
     */  
    public static int getDateYear(Date date){  
          
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        return c.get(Calendar.YEAR);  
    }  
    /** 
     * @param 取指定日期月份 
     * @return 
     */  
    public static int getDateMonth(Date date){  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        return c.get(Calendar.MONTH)+1;  
    }  
    /** 
     * @param 取指定日期日份 
     * @return 
     */  
    public static int getDateDay(Date date){  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        return c.get(Calendar.DATE);  
    }     
    /** 
     * @param 取指定日期小时 
     * @return 
     */  
    public static int getDateHour(Date date){  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        return c.get(Calendar.HOUR_OF_DAY);  
    }  
    /** 
     * @param 取指定日期分钟 
     * @return 
     */  
    public static int getDateMinute(Date date){  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        return c.get(Calendar.MINUTE);  
    }  
    /** 
     * @param 取指定日期的第二天的开始时间,小时,分,秒为00:00:00 
     * @return 
     */  
    public static Date getNextDayStartTime(Date date) {  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        return getNextDayStart(c.get(Calendar.YEAR),  
                c.get(Calendar.MONTH)+1, c.get(Calendar.DATE));  
    }  
  
  
    /** 
     * @param 取指定年,月,日的下一日的开始时间,小时,分,秒为00:00:00 
     * @param 主要是用来取跨月份的日期 
     * @return 
     */  
    public static Date getNextDayStart(int year, int monthIn, int date) {  
        int month = monthIn - 1;  
        boolean lastDayOfMonth = false;  
        boolean lastDayOfYear = false;  
          
        Calendar time = Calendar.getInstance();  
        time.set(year, month, date, 0, 0, 0);  
        Calendar nextMonthFirstDay = Calendar.getInstance();  
        nextMonthFirstDay.set(year, month + 1, 1, 0, 0, 0);  
          
        if (time.get(Calendar.DAY_OF_YEAR) + 1 == nextMonthFirstDay.get(Calendar.DAY_OF_YEAR)) {
        	lastDayOfMonth = true; 
        }  
            
          
        if (time.get(Calendar.DAY_OF_YEAR) == time.getMaximum(Calendar.DATE)) {
        	lastDayOfYear = true; 
        }  
          
        time.roll(Calendar.DATE, 1);  
          
        if (lastDayOfMonth) {
        	time.roll(Calendar.MONTH, 1); 
        }  
             
          
        if (lastDayOfYear) {
        	time.roll(Calendar.YEAR, 1); 
        }  
          
        return time.getTime();  
    }  
  
    /** 
     * @param 取指定日期的下一日的时间 
     * @return 
     */  
    public static Date nextDate(Date date)  
    {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        cal.add(Calendar.DATE,1);  
        return cal.getTime();  
    }  
  
    /** 
     * @param 指定日期的下一日的开始时间,小时,分,秒为00:00:00 
     * @return 
     */  
    public static Date getStartDateNext(Date date)  
    {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        cal.add(Calendar.DATE,1);  
        cal.set(Calendar.HOUR_OF_DAY,0);  
        cal.set(Calendar.MINUTE,0);  
        cal.set(Calendar.SECOND,0);  
        return cal.getTime();  
    }  
  
    /** 
     * @param 指定日期的开始时间,小时,分,秒为00:00:00 
     * @return 
     */  
    public static Date getStartDateDay(Date date)  
    {  
        if(date == null) {
        	return null;
        }  
        Calendar cal=Calendar.getInstance();  
        cal.setTime(date);  
        cal.set(Calendar.HOUR_OF_DAY,0);  
        cal.set(Calendar.MINUTE,0);  
        cal.set(Calendar.SECOND,0);  
        cal.set(Calendar.MILLISECOND,0);  
        return cal.getTime();  
    }  
  
    /** 
     * @param 指定日期的结束时间,小时,分,秒为23:59:59 
     * @return 
     */  
    public static Date getEndDateDay(Date date)  
    {  
        if(date == null) {
        	return null; 
        }  
        Calendar cal=Calendar.getInstance();  
        cal.setTime(date);  
        cal.set(Calendar.HOUR_OF_DAY,23);  
        cal.set(Calendar.MINUTE,59);  
        cal.set(Calendar.SECOND,59); 
        cal.set(Calendar.MILLISECOND,0); 
        return cal.getTime();  
    }  
      
    /** 
     * @param 将指定日期,以指定pattern格式,输出String值 
     * @return 
     */  
    public static String dateToString(Date date ,String pattern) {  
        if (date == null) {  
            return "";  
        } else {  
            SimpleDateFormat format = new SimpleDateFormat(pattern);  
            return format.format(date);  
        }  
    }  
      
    public static String dateToString1(Date date, String formatIn) {  
        String format=formatIn;  
        if (date == null) {  
          return "";  
        }  
        if (format == null) {  
          format = patternD;  
        }  
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format);  
        return sdf.format(date);  
      }  
  
  
    /** 
     * @param 将指定年,月,日的日期转为字符型,格式为yyyy-MM-dd 
     * @return 
     */  
    public static String dateToString(int year, int month, int day, String pattern) {  
        return dateToString(getDate(year, month, day), pattern);  
    }  
  
  
    /** 
     * @param 将指定字符型日期转为日期型,,格式为指定的pattern 
     * @return 
     */  
    public static Date stringToDate(String string, String pattern){  
        SimpleDateFormat format = (SimpleDateFormat)DateFormat.getDateInstance();  
        format.applyPattern(pattern);  
        try {  
            return format.parse(string);  
        } catch (ParseException e) {  
            return null;  
        }  
    }  
  
    /** 
     * @param 将指定字符型日期转为日期型,指定格式为yyyy-MM-dd 
     * @return 
     */  
    public static Date stringToDate(String string){  
        return stringToDate(string, patternA);  
    }  
  
    /** 
     * 获得两个日期之间间隔的天数 
     * @param startDate 起始年月日 
     * @param endDate 结束年月日 
     * @return int 
     */  
    public static int getDays(Date startDate, Date endDate) {  
        int elapsed = 0;  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(startDate);  
        Date d1 = cal.getTime();  
          
        cal.setTime(endDate);  
        Date d2 = cal.getTime();  
          
        long daterange = d2.getTime() - d1.getTime();  
        long time = 1000*3600*24; //一天的毫秒数  
        elapsed = (int) (daterange/time);  
        return elapsed;  
   }  
    /** 
     * 获得两个日期之间间隔的时间 
     * @param startDate 起始年月日 
     * @param endDate 结束年月日 
     * @return int 
     */  
    public static long getTimes(Date startDate, Date endDate) {  
        int elapsed = 0;  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(startDate);  
        Date d1 = cal.getTime();  
          
        cal.setTime(endDate);  
        Date d2 = cal.getTime();  
          
        long daterange = d2.getTime() - d1.getTime();  
        return daterange;  
   }
    /** 
     * @param date 
     * @param startTime 格式为0800，表示上午8点00分 
     * @param endTime格式为2200 
     * @return 
     */  
    public static boolean isWorkHour(Date date,String startTimeIn,String endTimeIn){//是否是工作时间  
        String startTime=startTimeIn;  
        String endTime=endTimeIn;  
        if(StringUtils.isEmpty(startTime)) {
        	startTime = "0800"; 
        } 
        if(StringUtils.isEmpty(endTime)) {
        	endTime = "2200"; 
        } 
        int start = Integer.parseInt(startTime);  
        int end = Integer.parseInt(endTime);  
        int hour = getDateHour(date);  
        int m = getDateMinute(date);  
        String hstr = hour<=9?"0"+hour:hour+"";  
        String mstr = m<=9?"0"+m:m+"";  
        int dateInt = Integer.parseInt(hstr+mstr);  
        if(dateInt>=start&&dateInt<=end){  
            return true;  
        }  
        return false;  
    }  
      
    /** 
     * @param  根据传入日期，返回此月有多少天 
     * @param date 格式为  201408 
     * @return 
     */  
    public static  int getDayOfMonth(String date){  
        int year = Integer.parseInt(date.substring(0, 3));  
        int month = Integer.parseInt(date.substring(date.length()-1, date.length()));  
          
        Calendar cal = Calendar.getInstance();   
        cal.set(Calendar.YEAR,year);   
        cal.set(Calendar.MONTH, month-1);//Java月份才0开始算  6代表上一个月7月   
        int dateOfMonth = cal.getActualMaximum(Calendar.DATE);  
        return dateOfMonth;  
    }  
      
    /** 
     * @param 取指定日期月份前一月 
     * @return 
     */  
    public static int getLastDateMonth(Date date){  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        return c.get(Calendar.MONTH);  
    }  
    //取日期的当前月第一天  
    public static Date getMonthFirstDay(Date date){  
        return getDate(getDateYear(date), getDateMonth(date), 1);  
    }  
      
    //前月第一天  
    public static Date getLastDateMonthDay(Date date){  
        return getDate(getDateYear(date), getLastDateMonth(date), 1);  
    }  
    
    //根据时间范围的时间值取出对应的时间上下限
    public static List<Date> getCheckTime(Integer timeID){ 
    	List<Date> ds=new ArrayList<Date>();
    	switch(timeID){
	    	case 1601://今天
	    		ds.add(getStartDateDay(new Date()));
	    		ds.add(getEndDateDay(new Date()));
	    		break; 
	    	case 1602://昨天
	    		ds.add(getStartDateDay(getSomeDaysBeforeAfter(new Date(), -1)));
	    		ds.add(getEndDateDay(getSomeDaysBeforeAfter(new Date(), -1)));
	    		break; 
	    	case 1603://最近三天
	    		ds.add(getStartDateDay(getSomeDaysBeforeAfter(new Date(), -3)));
	    		ds.add(getEndDateDay(new Date()));
	    		break; 
	    	case 1604://最近一周
	    		ds.add(getStartDateDay(getSomeDaysBeforeAfter(new Date(), -6)));
	    		ds.add(getEndDateDay(new Date()));
	    		break;
	    	case 1605://最近一月
	    		ds.add(getStartDateDay(getSomeMonthsBeforeAfter(new Date(), -1)));
	    		ds.add(getEndDateDay(new Date()));
	    		break;
	    	case 1606://最近三月
	    		ds.add(getStartDateDay(getSomeMonthsBeforeAfter(new Date(), -3)));
	    		ds.add(getEndDateDay(new Date()));
	    		break;
	    	case 1607://最近六月
	    		ds.add(getStartDateDay(getSomeMonthsBeforeAfter(new Date(), -6)));
	    		ds.add(getEndDateDay(new Date()));
	    		break;
	    	case 1608://最近一年
	    		ds.add(getStartDateDay(getSomeYearsBeforeAfter(new Date(), -1)));
	    		ds.add(getEndDateDay(new Date()));
	    		break;
	    	case 1609://最近三年
	    		ds.add(getStartDateDay(getSomeYearsBeforeAfter(new Date(), -3)));
	    		ds.add(getEndDateDay(new Date()));
	    		break;
	    	default:
	    		log.error("时间范围异常！");
	    		ds=null;
	    		break; 
    	}
        return ds;  
    }
    
    //效验字符日期格式是否匹配
    public static boolean isValidDate(String s,String pattern){  
        try {  
            // 指定日期格式为四位年/两位月份/两位日期，注意yyyy-MM-dd其中MM为大写  
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);  
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2004/02/29会被接受，并转换成2004/03/01  
            dateFormat.setLenient(false);  
            dateFormat.parse(s);  
            return true;  
         }catch (Exception e) {  
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对  
            return false;  
        }  
    } 
} 