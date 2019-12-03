package cn.com.shopec.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 人员年龄计算工具
 *
 */
public class ECPersonAgeCalaculatingUtils {
	
	/**
	 * 人员年龄计算
	 * @param birthdayStr 生日（格式 yyyy-MM-dd）
	 * @param somedayStr 由于计算的日期（格式 yyyy-MM-dd）
	 * @return
	 */
	public static String ageCal(String birthdayStr, String somedayStr) {
		String str = "";
		try {
			Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthdayStr);
			Date someday = new SimpleDateFormat("yyyy-MM-dd").parse(somedayStr);
			
			str = ageCal(birthday, someday);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}

	/**
	 * 人员年龄计算
	 * @param birthday
	 * @param someday
	 * @return
	 */
	public static String ageCal(Date birthday, Date someday) {
		String str = "";
		try {
			if(birthday.after(someday)) { //生日更晚，传入参数有逻辑问题，直接返回空串
				return str;
			}
			birthday = cleanHourMinuteSeconds(birthday); //清除时分秒
			someday = cleanHourMinuteSeconds(someday); //清除时分秒
			
			Calendar birthdayCal = Calendar.getInstance();
			birthdayCal.setTime(birthday);
			
			Calendar somedayCal = Calendar.getInstance();
			somedayCal.setTime(someday);
			
			int day = somedayCal.get(Calendar.DAY_OF_MONTH) - birthdayCal.get(Calendar.DAY_OF_MONTH);   
			int month = somedayCal.get(Calendar.MONTH) - birthdayCal.get(Calendar.MONTH);   
			int year = somedayCal.get(Calendar.YEAR) - birthdayCal.get(Calendar.YEAR);   
			//按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减。
			
			if(day<0){   
				month -= 1;   
				somedayCal.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数。
				
				day = day + somedayCal.getActualMaximum(Calendar.DAY_OF_MONTH);   
			}   
			if(month<0){   
				month = (month+12)%12;   
				year--;   
			}
			
			if(year == 0 && month == 0 && day == 0) {
				str = "0天";
				return str;
			}
			
//			//--此段代码，将 1岁以上的年龄，转为  xx岁yy天的格式，即不考虑月份，如果需要使用 xx岁xx个月xx天的格式，则此段代码可以注释掉
//			if(year > 0 && !(month == 0 && day == 0)) { //非整岁才处理
//				month = 0;
//				birthdayCal = Calendar.getInstance();
//				birthdayCal.setTime(birthday);
//				somedayCal = Calendar.getInstance();
//				somedayCal.setTime(someday);
//				
//				birthdayCal.add(Calendar.YEAR, year);
//				day = differDays(birthdayCal.getTime(), somedayCal.getTime());
//			}
//			//--此段代码，将 1岁以上的年龄，转为  xx岁yy天的格式，即不考虑月份，如果需要使用 xx岁xx个月xx天的格式，则此段代码可以注释掉
			
			StringBuilder sb = new StringBuilder("");
			if(year != 0) {
				sb.append(year);
				sb.append("岁");
			}
			if(month != 0) {
				sb.append(month);
				sb.append("个月");
			}
			if(day != 0) {
				sb.append(day);
				sb.append("天");
			}
			str = sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return str;
	}
	
	/**
	 * 相差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private static int differDays(Date startDate,Date endDate){
		if(null == startDate || null == endDate){
			return 0;
		}
		Long from = startDate.getTime();
		Long to = endDate.getTime();
		Long cd = (from - to)/(1000 * 60 * 60 * 24);
		
		int res = (int)(cd<0?-cd:cd);
		return res;
	}
	
	/**
	 * 清除时分秒
	 * @param date
	 */
	private static Date cleanHourMinuteSeconds(Date date) {
		Date res = null;
		if(date != null) {
			res = new Date(date.getTime());
			res.setHours(0);
			res.setMinutes(0);
			res.setSeconds(0);
		}
		return res;
	}
	
	public static void main(String[] args) throws Exception {
		Date birthday = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2015-01-13 20:40:13");
		Date someday = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2016-01-14 17:29:40");
//		String str = ageCal("2015-01-13", "2016-01-14");
		String str = ageCal(birthday, someday);
		System.out.println(str);
		
    }  
}
