package cn.com.shopec.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** 
 * 日期时间工具类
 *  
 */

public class ECDateUtils {
	/**
	 * 常用时间格式
	 */
	public static final String Format_Date = "yyyy-MM-dd";
	public static final String Format_Date_CN = "yyyyMM";
	public static final String Format_Time = "HH:mm:ss";
	public static final String Format_DateTime = "yyyy-MM-dd HH:mm:ss";
	public static final String Format_DateTime_CH = "MM月dd日 HH时";
	public static final String Format_DateTime_CH_M = "MM月dd日 HH时mm分";
//	private final static SimpleDateFormat Format_DateTime_WX = new SimpleDateFormat("yyyyMMddHHmmss");
//	private final static SimpleDateFormat Format_DateTime_WX_ALIPAY = new SimpleDateFormat("yyyyMMdd");
	/**
	 * 默认的日期格式化器，格式为yyyy-MM-dd
	 */
//	private final static SimpleDateFormat DEFAULT_DATE_FORMATER = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 默认的时间格式化器，格式为yyyy-MM-dd HH:mm:ss
	 */
//	private final static SimpleDateFormat DEFAULT_DATETIME_FORMATER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
//	private final static SimpleDateFormat DEFAULT_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	
	
	public static final String Format_DateRe = "yyyy-MM-dd";
	public static String formatStringTimeWX(Date date) {
		return date == null ? "" : new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}
	
	public static String formatStringTimeWXALIPAY(Date date) {
		return date == null ? "" : new SimpleDateFormat("yyyyMMdd").format(date);
	}
	/**
	 * 用默认的日期时间格式，格式化一个Date对象
	 * 
	 * @param date yyyy-MM-dd HH:mm
	 * @return
	 */
	public static String formatStringDate(Date date) {
		return date == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
	}
	public static Date formatDateToDate(Date date) throws ParseException{
		if (date == null)
			return null;
		else{
//			return DEFAULT_DATETIME_FORMATER.parse(DEFAULT_DATETIME_FORMATER.format(date));
			SimpleDateFormat df = new SimpleDateFormat(Format_DateTime);
			return df.parse(df.format(date));
		}
		
	}
	public static Date formatDateToDateNew(Date date) throws ParseException{
		if (date == null)
			return null;
		else{
			SimpleDateFormat df = new SimpleDateFormat(Format_Date);
			return df.parse(df.format(date));
		}
		
	}
	/**
	 * 用默认的日期时间格式，格式化一个Date对象
	 * 
	 * @param date
	 * @return
	 */
	public static String formatStringTime(Date date) {
//		return date == null ? "" : DEFAULT_DATETIME_FORMATER.format(date);
		return date == null ? "" : new SimpleDateFormat(Format_DateTime).format(date);
	}
	 /**
	 * 格式化时间为HH:mm:ss
	 * @return
	 * @throws ParseException
	 */
	public static String farmatDateToHMS(Date date) throws ParseException{
		return date == null ? "" : new SimpleDateFormat("HH:mm:ss").format(date);
	}
	/**
	 * 取得当前日期（只有日期，没有时间，或者可以说是时间为0点0分0秒）
	 * @return
	 * @throws ParseException 
	 */
	public static Date getCurrentDate() throws ParseException{
		Date date = new Date();
//		date = DEFAULT_DATE_FORMATER.parse(DEFAULT_DATE_FORMATER.format(date));//
		SimpleDateFormat df = new SimpleDateFormat(Format_Date);
		date = df.parse(df.format(date));//
		return date;
	}
	
	/**
	 * 得到当天最后时间
	 * @return
	 * @throws ParseException
	 */
	public static Date getCurrentDateEndTime() throws ParseException{
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 23);
		now.set(Calendar.MINUTE, 59);
		now.set(Calendar.SECOND, 59);
		return now.getTime();
	}
	
	/**
	 * 得到当天开始时间
	 * @return
	 * @throws ParseException
	 */
	public static Date getCurrentDateStartTime() throws ParseException{
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 00);
		now.set(Calendar.MINUTE, 00);
		now.set(Calendar.SECOND, 00);
		return now.getTime();
	}
	
	/**
	 * 取得当前时间（包括日期和时间）
	 * @return
	 */
	public static Date getCurrentDateTime(){
		Date date = new Date();
		return date;
	}
	
	/**
	 * 用默认的日期格式，格式化一个Date对象
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date){	
//		return date == null ? "" : DEFAULT_DATETIME_FORMATER.format(date);
		return date == null ? "" : new SimpleDateFormat(Format_DateTime).format(date);
	}
	
	/**
	 * 根据传入的格式，将日期对象格式化为日期字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format){
		String s = "";
		if(date != null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			s = sdf.format(date);
		}
		
		return s;
	}
	
	/**
	 * 用默认的日期时间格式，格式化一个Date对象
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date) {
		//return date == null ? "" : DEFAULT_DATETIME_FORMATER.format(date);
		return date == null ? "" : new SimpleDateFormat(Format_DateTime).format(date);
	}
	
	/**
	 * 根据传入的格式，将日期对象格式化为时间字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatTime(Date date,String format){
		String s = "";
		if(date != null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			s = sdf.format(date);
		}
		
		return s;
	}
	
	/**
	 * 日期后推
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();	 
	}
	/**
	 * 日期前推
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();	 
	}
	/**
	 * 月后推
	 * @param d
	 * @param month
	 * @return
	 */
	public static Date getDateMonthAfter(Date d, int month) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) + month);
		return now.getTime();	 
	}
	/**
	 * 年后推
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date getDateYearAfter(Date date, int year) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.YEAR, now.get(Calendar.YEAR) + year);
		return now.getTime();	 
	}
	
	
	/**
	 * 利用默认的格式（yyyy-MM-dd）将一个表示日期的字符串解析为日期对象
	 * @param s
	 * @return
	 * @throws RuntimeException
	 */
	public static Date parseDate(String s){
		Date date = null;
		try{
//			date = DEFAULT_DATE_FORMATER.parse(s);
			date = new SimpleDateFormat(Format_Date).parse(s);
		}catch(ParseException e){
			throw new RuntimeException(e);
		}
	    return date;    
	}
	
	public static Date parseDateCN(String s){
		Date date = null;
		try{
//			date = DEFAULT_DATE_FORMATER.parse(s);
			date = new SimpleDateFormat(Format_Date_CN).parse(s);
		}catch(ParseException e){
			throw new RuntimeException(e);
		}
	    return date;    
	}
	
	/**
	 * 将一个字符串，按照特定格式，解析为日期对象
	 * @param s
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String s,String format) {
		Date date = null;
		try{
			date = (new SimpleDateFormat(format)).parse(s);
		}catch(ParseException e){
			throw new RuntimeException(e);
		}
		return date;
	}
	
	
	/**
	 * 利用默认的格式（yyyy-MM-dd HH:mm:ss）将一个表示时间的字符串解析为日期对象
	 * @param s
	 * @return
	 * @throws ParseException
	 */
	public static Date parseTime(String s) throws ParseException {    
	    return new SimpleDateFormat(Format_DateTime).parse(s);    
	}
	
	/**
	 * 得到当前年份
	 * @return
	 */
	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * 得到当前月份（1至12）
	 * @return
	 */
	public static int getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 获取yyyy-MM-dd格式的当前系统日期
	 * 
	 * @return
	 */
	public static String getCurrentDateAsString() {
		return new SimpleDateFormat(Format_Date).format(new Date());
	}

	/**
	 * 获取指定格式的当前系统日期
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(new Date());
	}

	/**
	 * 获取HH:mm:ss格式的当前系统时间
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		return new SimpleDateFormat(Format_Time).format(new Date());
	}

	/**
	 * 获取指定格式的当前系统时间
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentTime(String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(new Date());
	}

	/**
	 * 获取格式为yyyy-MM-dd HH:mm:ss的当前系统日期时间
	 * 
	 * @return
	 */
	public static String getCurrentDateTimeAsString() {
		return getCurrentDateTime(Format_DateTime);
	}

	public static int getDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取星期X中文名称
	 * 
	 * @param date
	 * @return
	 */
	public static String getChineseDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		return getChineseDayOfWeek(cal.getTime());
	}

	/**
	 * 获取星期X中文名称
	 * 
	 * @param date
	 * @return
	 */
	public static String getChineseDayOfWeek(String date) {
		return getChineseDayOfWeek(parseDate(date));
	}

	/**
	 * 获取星期X中文名称
	 * 
	 * @param date
	 * @return
	 */
	public static String getChineseDayOfWeek(Date date) {
		int dateOfWeek = getDayOfWeek(date);
		if (dateOfWeek == Calendar.MONDAY) {
			return "星期一";
		} else if (dateOfWeek == Calendar.TUESDAY) {
			return "星期二";
		} else if (dateOfWeek == Calendar.WEDNESDAY) {
			return "星期三";
		} else if (dateOfWeek == Calendar.THURSDAY) {
			return "星期四";
		} else if (dateOfWeek == Calendar.FRIDAY) {
			return "星期五";
		} else if (dateOfWeek == Calendar.SATURDAY) {
			return "星期六";
		} else if (dateOfWeek == Calendar.SUNDAY) {
			return "星期日";
		}
		return null;
	}

	public static int getDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int getDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int getMaxDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static String getFirstDayOfMonth(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(date));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return new SimpleDateFormat(Format_Date).format(cal.getTime());
	}
	//获取指定日期的月份的第一天
	public static Date getFirstMonthDayOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	public static int getDayOfYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	public static int getDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	public static int getDayOfWeek(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(date));
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static int getDayOfMonth(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(date));
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int getDayOfYear(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDate(date));
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获取指定格式的当前系统日期时间
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDateTime(String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(new Date());
	}

	public static String toString(Date date) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(Format_Date).format(date);
	}

	public static String toDateTimeString(Date date) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(Format_DateTime).format(date);
	}
	
	public static String toDateTimeStringCH(Date date) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(Format_DateTime_CH).format(date);
	}
	
	public static String toDateTimeStringCH_mm(Date date) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(Format_DateTime_CH_M).format(date);
	}
	
	public static String toString(Date date, String format) {
		SimpleDateFormat t = new SimpleDateFormat(format);
		return t.format(date);
	}

	public static String toTimeString(Date date) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(Format_Time).format(date);
	}
	/**
	 * 时间戳转换
	 * @param time
	 * @return
	 */
	public static String longTimeToDateTimeString(Long time) {
		SimpleDateFormat format = new SimpleDateFormat(Format_DateTime);
		String d = format.format(time);
		return d;
	}
	
	/**
	 * 时间戳转换
	 * @param time
	 * @return
	 */
	public static Date longTimeToDateTime(Long time) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat(Format_DateTime);
		String d = format.format(time);
		return parseTime(d);
	}
	/**
	 * 时间转换（string-->date）
	 * @param time
	 * @return
	 */
	public static Date stringTimeToDateTime(String time) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat(Format_DateTime);
		Date date = format.parse(time); 
		return date;
	}
	
	
	/**
	 * 相差的天数(返回正负数，不取绝对值)
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Long differDays2(Date startDate,Date endDate){
		if(null == startDate || null == endDate){
			return 0l;
		}
		Long from = startDate.getTime();
		Long to = endDate.getTime();
		Long cd = (from - to)/(1000L * 60 * 60 * 24);
		return cd;
	}
	
	/**
	 * 相差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Long differDays(Date startDate,Date endDate){
		if(null == startDate || null == endDate){
			return 0l;
		}
		Long from = startDate.getTime();
		Long to = endDate.getTime();
		Long cd = (from - to)/(1000L * 60 * 60 * 24);
		return cd<0?-cd:cd;
	}
	public static Long differHours(Date startDate,Date endDate){
		if(null == startDate || null == endDate){
			return 0l;
		}
		Long from = startDate.getTime();
		Long to = endDate.getTime();
		Long cd = (from - to)/(1000L * 60 * 60);
		return cd<0?-cd:cd;
	}
	public static Long differMinutes(Date startDate,Date endDate){
		if(null == startDate || null == endDate){
			return 0l;
		}
		Long from = startDate.getTime();
		Long to = endDate.getTime();
		Long cd = (from - to)/(1000L * 60);
		return cd<0?-cd:cd;
	}
	public static Long differSeconds(Date startDate,Date endDate){
		if(null == startDate || null == endDate){
			return 0l;
		}
		Long from = startDate.getTime();
		Long to = endDate.getTime();
		Long cd = (from - to)/(1000L);
		return cd<0?-cd:cd;
	}
	   public static int getMinutes(String dateStart,String dateStop) {
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    
	        Date d1 = null;
	        Date d2 = null;
	        int minutes=0;
	        int seconds=0;
	        try {
	            d1 = format.parse(dateStart);
	            d2 = format.parse(dateStop);
	    
	          //毫秒ms
	            Long diff = (d2.getTime() - d1.getTime());
	            Long minutesTemp =(diff/1000)/60;
	            minutes = minutesTemp.intValue();
//	            long diffSeconds = diff / 1000 % 60;
//	            long diffMinutes = diff / (60 * 1000) % 60;
//	            long diffHours = diff / (60 * 60 * 1000) % 24;
//	            long diffDays = diff / (24 * 60 * 60 * 1000);
//	    
//	            System.out.print("两个时间相差：");
//	            System.out.print(diffDays + " 天, ");
//	            System.out.print(diffHours + " 小时, ");
//	            System.out.print(diffMinutes + " 分钟, ");
//	            System.out.print(diffSeconds + " 秒.");
	    
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    return minutes<0?-minutes:minutes;
	    }
	   
	   //订单时长天数（用于计算封顶金额）
//	   public static int getDays(String dateStart,String dateStop) {
//	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	    
//	        Date d1 = null;
//	        Date d2 = null;
//	        int days=0;
//	        try {
//	            d1 = format.parse(dateStart);
//	            d2 = format.parse(dateStop);
//	    
//	            //毫秒ms
//	            int diff = (int) (d2.getTime() - d1.getTime());
//	            long diffSeconds = diff / 1000 % 60;
//	            long diffMinutes = diff / (60 * 1000) % 60;
//	            long diffHours = diff / (60 * 60 * 1000) % 24;
//	            int diffDays = diff / (24 * 60 * 60 * 1000);
//	            	days=diffDays<0?-diffDays:diffDays;
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	    return days;
//	    }
	   /** 
	     * 获取最近12个月     发票开票对账周期
	     */  
	    public static List<String> getLast12Months(int start,int end){
	    	List<String> leadTime=new ArrayList<String>();
	        String[] last12Months = new String[12]; 
	        String[] last12Months1 = new String[12]; 
	        Calendar cal = Calendar.getInstance();  
	        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1); //要先+1,才能把本月的算进去</span>  
	        for(int i=0; i<12; i++){  
	            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1); //逐次往前推1个月  
	            if(cal.get(Calendar.MONTH)+1<10){
	            	if(12-i<10){
	            		last12Months[i] = cal.get(Calendar.YEAR)+ "-0" + (cal.get(Calendar.MONTH)+1)+"第0"+(12-i)+"期";
	            	}else{
	            		last12Months[i] = cal.get(Calendar.YEAR)+ "-0" + (cal.get(Calendar.MONTH)+1)+"第"+(12-i)+"期";	
	            	}
	            }else{
	            	if(12-i<10){
	            		last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH)+1)+"第0"+(12-i)+"期";
	            	}else{
	            		last12Months[i] = cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH)+1)+"第"+(12-i)+"期";	
	            	}
	            }
	            
	            if(cal.get(Calendar.MONTH)==0){
	            	if(cal.get(Calendar.MONTH)+1<10){
	            		last12Months1[i] = (cal.get(Calendar.YEAR)-1)+ "-" + 12+"-" +start +"~"+cal.get(Calendar.YEAR)+ "-0" + (cal.get(Calendar.MONTH)+1)+"-" +end ; 
            		}else{
            			last12Months1[i] = (cal.get(Calendar.YEAR)-1)+ "-" + 12+"-" +start +"~"+cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH)+1)+"-" +end ; 
            		}
	            	
	            }else{
	            	if(cal.get(Calendar.MONTH)<10){
	            		if(cal.get(Calendar.MONTH)+1<10){
	            			last12Months1[i] = cal.get(Calendar.YEAR)+ "-0" + (cal.get(Calendar.MONTH))+"-" +start +"~"+cal.get(Calendar.YEAR)+ "-0" + (cal.get(Calendar.MONTH)+1)+"-" +end ; 	
	            		}else{
	            			last12Months1[i] = cal.get(Calendar.YEAR)+ "-0" + (cal.get(Calendar.MONTH))+"-" +start +"~"+cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH)+1)+"-" +end ; 	
	            		}
	            	}else{
	            		if(cal.get(Calendar.MONTH)+1<10){
	            			last12Months1[i] = cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH))+"-" +start +"~"+cal.get(Calendar.YEAR)+ "-0" + (cal.get(Calendar.MONTH)+1)+"-" +end ; 	
	            		}else{
	            			last12Months1[i] = cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH))+"-" +start +"~"+cal.get(Calendar.YEAR)+ "-" + (cal.get(Calendar.MONTH)+1)+"-" +end ; 	
	            		}
	            	}
	            	
	            }
	            leadTime.add(i,last12Months[i]+" "+ last12Months1[i]);
	        }  
	        return leadTime;  
	    } 
	    
	    /**
	     * Date型，转unix时间戳
	     * @param d
	     * @return
	     */
	    public static long date2UnixTimestamp(Date d) {
	    	long t = d.getTime();
	    	t = t / 1000;
	    	return t;
	    }
	    /**
	     * 获取当前月份的第一天和最后一天
	     * */
	    public static String getFirstAndLastDayOfMonth() {
	    	String day="";
	    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	    	Calendar c = Calendar.getInstance();    
	    	c.add(Calendar.MONTH, 0);
	    	c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	    	String first = format.format(c.getTime());
	    	day=day+first;
	    	System.out.println("===============本月first day:"+first);
	    	//获取当前月最后一天
	    	Calendar ca = Calendar.getInstance();    
	    	ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
	    	String last = format.format(ca.getTime());
	    	System.out.println("===============本月last day:"+last);
	    	day=day+","+last;
	    	return day;
	    }
	    
	    /**
	     * 获取前一月的第一天
	     */
	    public static String getFirstDayOfLastMonth(){
	    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	    	Calendar   cal_1=Calendar.getInstance();//获取当前日期 
	    	cal_1.add(Calendar.MONTH, -1);
	    	cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	    	String firstDay = format.format(cal_1.getTime());
	    	System.out.println("-----1------firstDay:"+firstDay);
	    	return firstDay;
	    }
	    
	    /**
	     * 获取前一月的最后一天
	     */
	    public static String getLastDayOfLastMonth(){
	    	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	    	 Calendar cale = Calendar.getInstance();   
	    	 cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
	    	 String lastDay = format.format(cale.getTime());
	    	 System.out.println("-----2------lastDay:"+lastDay);
	    	 return lastDay;
	    }
	    
	    /**
	     * 获取当前月份的第一天
	     * 
	     */
	    public static String getFirstDayOfMonth() {
	    	String day="";
	    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	    	Calendar c = Calendar.getInstance();    
	    	c.add(Calendar.MONTH, 0);
	    	c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	    	String first = format.format(c.getTime());
	    	day=day+first;
	    	System.out.println("===============本月first day:"+first);
	    	return day;
	    }
	    /**
	     * 获取当前月份的最后一天
	     */
	    public static String getLastDayOfMonth() {
	    	String day="";
	    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	    	//获取当前月最后一天
	    	Calendar ca = Calendar.getInstance();    
	    	ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
	    	String last = format.format(ca.getTime());
	    	System.out.println("===============本月last day:"+last);
	    	day=last;
	    	return day;
	    }
	    /**
	     * 获取指定日期的月份的最后一天
	     */
	    public static Date getLastMonthDayOfDate(Date date) {
	    	//获取当前月最后一天
	    	Calendar ca = Calendar.getInstance(); 
	    	ca.setTime(date);
	    	ca.set(Calendar.HOUR_OF_DAY, 23);
	    	ca.set(Calendar.MINUTE, 59);
	    	ca.set(Calendar.SECOND, 59);
	    	ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
	    	return ca.getTime();
	    }
	    
	    public static Date getNextDay(Date date) {
	    	 Calendar calendar = Calendar.getInstance();
	    	 calendar.setTime(date);
	    	 calendar.add(Calendar.DAY_OF_MONTH, -1);
	    	 calendar.set(Calendar.HOUR_OF_DAY, 23);
	    	 calendar.set(Calendar.MINUTE, 59);
	    	 calendar.set(Calendar.SECOND, 59);
	    	 date = calendar.getTime();
	    	 return date;
	    }
	    public static Date getNextDayFrist(Date date) {
	    	 Calendar calendar = Calendar.getInstance();
	    	 calendar.setTime(date);
	    	 calendar.add(Calendar.DAY_OF_MONTH, -1);
	    	 calendar.set(Calendar.HOUR_OF_DAY, 00);
	    	 calendar.set(Calendar.MINUTE, 00);
	    	 calendar.set(Calendar.SECOND, 00);
	    	 date = calendar.getTime();
	    	 return date;
	    }
	    public static void main(String[] args) {
	    	Calendar now = Calendar.getInstance();
	    	now.setTime(new Date());
	    	System.out.println(now.getTime());
	    	
	    	Calendar nowBefore = Calendar.getInstance();
	    	nowBefore.setTime(new Date());
	    	nowBefore.set(Calendar.MONTH, nowBefore.get(Calendar.MONTH) - 12);
			System.out.println(nowBefore.getTime());
		}
	    /**
		 * 得到某天的最后时间
		 * @return
		 * @throws ParseException
		 */
		public static Date getCurrentDateEndTime(Date date) throws ParseException{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			return calendar.getTime();
		}
		
		/**
		 * 得到某天的开始时间
		 * @return
		 * @throws ParseException
		 */
		public static Date getCurrentDateStartTime(Date date) throws ParseException{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 00);
			calendar.set(Calendar.MINUTE, 00);
			calendar.set(Calendar.SECOND, 00);
			return calendar.getTime();
		}
		

	    public static String getSpecifiedDayBefore(Date specifiedDay,int i) {
	        Calendar c = Calendar.getInstance(); 
	        c.setTime(specifiedDay);
	        int day = c.get(Calendar.DATE);
	        c.set(Calendar.DATE, day - i);

	        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	        return dayBefore;
	    }

	    public static Date getSpecifiedDay(Date specifiedDay,int i) {
	        Calendar c = Calendar.getInstance(); 
	        c.setTime(specifiedDay);
	        int day = c.get(Calendar.DATE);
	        c.set(Calendar.DATE, day - i);

	        Date dayBefore = c.getTime();
	        return dayBefore;
	    }

	    public static int getMonthSpace(String date1, String date2)
	            throws ParseException {

	        int result = 0;

	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

	        Calendar c1 = Calendar.getInstance();
	        Calendar c2 = Calendar.getInstance();

	        c1.setTime(sdf.parse(date1));
	        c2.setTime(sdf.parse(date2));

	        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);

	        return result == 0 ? 1 : Math.abs(result);

	    }
	    //获取指定日期的年份
	    public static int getYearOfDate(Date date) {
	        Calendar c = Calendar.getInstance(); 
	        c.setTime(date);
	        int month = c.get(Calendar.YEAR);
	        return month;
	    }
	    //获取指定日期的月份
	    public static int getMonthOfDate(Date date) {
	        Calendar c = Calendar.getInstance(); 
	        c.setTime(date);
	        int month = c.get(Calendar.MONTH)+1;
	        return month;
	    }
	    
	    public static String getSpecifiedmonth(Date specifiedDay,int i) {
	        Calendar c = Calendar.getInstance(); 
	        c.setTime(specifiedDay);
	        int month = c.get(Calendar.MONTH);
	        c.set(Calendar.MONTH, month - i);

	        String dayBefore = new SimpleDateFormat("yyyy-MM").format(c.getTime());
	        return dayBefore;
	    }
	    

	    public static String getSpecifiedYear(Date specifiedDay,int i) {
	        Calendar c = Calendar.getInstance(); 
	        c.setTime(specifiedDay);
	        int year = c.get(Calendar.YEAR);
	        c.set(Calendar.YEAR, year - i);

	        String dayBefore = new SimpleDateFormat("yyyy").format(c.getTime());
	        return dayBefore;
	    }
	    
	    public static String getSpecifiedWeek(Date specifiedDay,int i) {
	        Calendar c = Calendar.getInstance(); 
	        c.setTime(specifiedDay);
	        int day = c.get(Calendar.DATE);
	        c.set(Calendar.DATE, day - i*7);

	        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	        return dayBefore;
	    }
	    /** 
	     * 获取两个日期相差几个月 
	     * @author 石冬冬-Heil Hilter(dd.shi02@zuche.com) 
	     * @date 2016-11-30 下午7:57:32 
	     * @param start 
	     * @param end 
	     * @return 
	     */  
	    public static int getMonth(Date start, Date end) {  
	        if (start.after(end)) {  
	            Date t = start;  
	            start = end;  
	            end = t;  
	        }  
	        Calendar startCalendar = Calendar.getInstance();  
	        startCalendar.setTime(start);  
	        Calendar endCalendar = Calendar.getInstance();  
	        endCalendar.setTime(end);  
	        Calendar temp = Calendar.getInstance();  
	        temp.setTime(end);  
	        temp.add(Calendar.DATE, 1);  
	  
	        int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);  
	        int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);  
	  
	        if ((startCalendar.get(Calendar.DATE) == 1)&& (temp.get(Calendar.DATE) == 1)) {  
	            return year * 12 + month + 1;  
	        } else if ((startCalendar.get(Calendar.DATE) != 1) && (temp.get(Calendar.DATE) == 1)) {  
	            return year * 12 + month;  
	        } else if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) != 1)) {  
	            return year * 12 + month;  
	        } else {  
	            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);  
	        }  
	    }  
	    
	    public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
	        ArrayList<String> result = new ArrayList<String>();
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

	        Calendar min = Calendar.getInstance();
	        Calendar max = Calendar.getInstance();

	        min.setTime(sdf.parse(minDate));
	        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

	        max.setTime(sdf.parse(maxDate));
	        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

	        Calendar curr = min;
	        while (curr.before(max)) {
	         result.add(sdf.format(curr.getTime()));
	         curr.add(Calendar.MONTH, 1);
	        }

	        return result;
	      }
	    
	    /**
	     * 收集起始时间到结束时间之间所有的时间并以字符串集合方式返回
	     * @param timeStart
	     * @param timeEnd
	     * @return
	     */
	    public static List<String> collectLocalDates(String timeStart, String timeEnd){
	        return collectLocalDates(LocalDate.parse(timeStart), LocalDate.parse(timeEnd));
	    }

	    /**
	     * 收集起始时间到结束时间之间所有的时间并以字符串集合方式返回
	     * @param start
	     * @param end
	     * @return
	     */
	    public static List<String> collectLocalDates(LocalDate start, LocalDate end){
	        // 用起始时间作为流的源头，按照每次加一天的方式创建一个无限流
	        return Stream.iterate(start, localDate -> localDate.plusDays(1))
	                     // 截断无限流，长度为起始时间和结束时间的差+1个
	                     .limit(ChronoUnit.DAYS.between(start, end) + 1)
	                     // 由于最后要的是字符串，所以map转换一下
	                     .map(LocalDate::toString)
	                     // 把流收集为List
	                     .collect(Collectors.toList());
	    }
	    

	    public static int getYearSpace(String date1, String date2)
	            throws ParseException {

	        int result = 0;

	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

	        Calendar c1 = Calendar.getInstance();
	        Calendar c2 = Calendar.getInstance();

	        c1.setTime(sdf.parse(date1));
	        c2.setTime(sdf.parse(date2));

	        result = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);

	        return result == 0 ? 1 : Math.abs(result);

	    }
	    
	    
	    
		   /** 
	     * 两个时间之间相差距离多少天 
	     * @param one 时间参数 1： 
	     * @param two 时间参数 2： 
	     * @return 相差天数 
	     */  
	    public static long getDistanceDays(String str1, String str2) throws Exception{  
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	        Date one;  
	        Date two;  
	        long days=0;  
	        try {  
	            one = df.parse(str1);  
	            two = df.parse(str2);  
	            long time1 = one.getTime();  
	            long time2 = two.getTime();  
	            long diff ;  
	            if(time1<time2) {  
	                diff = time2 - time1;  
	            } else {  
	                diff = time1 - time2;  
	            }  
	            days = diff / (1000L * 60 * 60 * 24);  
	        } catch (ParseException e) {  
	            e.printStackTrace();  
	        }  
	        return days;  
	    }  
		
		//获取指定月份的天数  
	    public static int getDaysByYearMonth(int year, int month) {  
	        Calendar a = Calendar.getInstance();  
	        a.set(Calendar.YEAR, year);  
	        a.set(Calendar.MONTH, month);  
	        a.set(Calendar.DATE, 1);  
	        a.roll(Calendar.DATE, -1);  
	        int maxDate = a.get(Calendar.DATE);  
	        return maxDate;  
	    } 
		
	    /**
	     * 获取这个月所有日期
	     * 
	     * @param month
	     * @return
	     */
		public static List<String> getTheMonthAllDate(Date month) {  
		    Calendar cal = Calendar.getInstance();  
		    cal.setTime(month);//month 为指定月份任意日期  
		    int year = cal.get(Calendar.YEAR);  
		    int m = cal.get(Calendar.MONTH);  
		    int dayNumOfMonth = getDaysByYearMonth(year, m); 
		    cal.set(Calendar.DAY_OF_MONTH, 1);// 从一号开始  
		    List<String> list = new ArrayList<>();
		    for (int i = 0; i < dayNumOfMonth; i++, cal.add(Calendar.DATE, 1)) {  
		        Date d = cal.getTime();  
		        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		        String df = simpleDateFormat.format(d);  
		        list.add(df);
		   }  
		    return list;
		} 
		
		public static Date getTheMonthDate(Date d){
			/*Calendar cal = Calendar.getInstance();
	    	int year = cal.get(Calendar.YEAR);
	    	int month = cal.get(Calendar.MONTH )+1;

	    	String date = year + "-" + ( month<10 ? "0" + month : month);
	    	
	    	return date;*/
	    	
	    	Calendar now = Calendar.getInstance();
    		now.setTime(d);
    		return now.getTime();	 
	    	
		}
		//月前推
		public static Date getMonthBefore(Date d, int month){
			Calendar nowBefore = Calendar.getInstance();
	    	nowBefore.setTime(new Date());
	    	nowBefore.set(Calendar.MONTH, nowBefore.get(Calendar.MONTH) - 12);
	    	
	    	return nowBefore.getTime();
		}
}