package cn.com.shopec.core.map.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeConvertUtil {
	public static long getUnixTimestamp(Date date) throws ParseException {
		// Unix时间戳（Unix
		// timestamp）是一种时间表示方法，定义为从格林威治时间1970年01月01日00时00分00秒起至现在的总秒数。
		// Date the milliseconds since January 1, 1970, 00:00:00 GMT.
		return date.getTime() / 1000;
	}

	public static String getDate(String unixDate) {
		SimpleDateFormat fm1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat fm2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		long unixLong = 0;
		String date = "";
		try {
			unixLong = Long.parseLong(unixDate) * 1000;
		} catch (Exception ex) {
			System.out.println("String转换Long错误，请确认数据可以转换！");
		}
		try {
			date = fm1.format(unixLong);
			date = fm2.format(new Date(date));
		} catch (Exception ex) {
			System.out.println("String转换Date错误，请确认数据可以转换！");
		}
		return date;
	}
}
