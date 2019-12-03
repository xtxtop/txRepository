package cn.com.shopec.common.utils;

import java.math.BigDecimal;

/**
 * 数字操作工具类
 *
 */
public class ECNumberUtils extends org.apache.commons.lang3.math.NumberUtils {

	
	/**
	 * 基于特定精度，对float做四舍五入处理
	 * @param value
	 * @param scale
	 * @return
	 */
	public static float roundFloatWithScale(float value, int scale) {
		return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue(); 
	}
	
	/**
	 * 基于特定精度，对double做四舍五入处理
	 * @param value
	 * @param scale
	 * @return
	 */
	public static double roundDoubleWithScale(double value, int scale) {
		return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue(); 
	}
	/**
	 * 基于特定精度，对double 直接去掉尾数
	 * @param value
	 * @param scale
	 * @return
	 */
	public static double roundDown(double value ,int scale){
		return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_DOWN).doubleValue(); 
	}
	/**
	 * 十进制转换成３２进制
	 * @param value
	 * @return  
	 * @throws 
	 * @author: yong
	 * @date: 2013-9-20下午12:04:22
	 */
	public static String to32Hex(Long value){
		return toUnsignedString(value,5,digits_32);
	}
	
	final static char[] digits_32 = {
		'0' , '1' , '2' , '3' , '4' , '5' ,
		'6' , '7' , '8' , '9' , 'A' , 'B' ,
		'C' , 'D' , 'E' , 'F' , 'G' , 'H' ,
		'J' , 'K' , 'M' , 'N' , 'P' , 'Q' ,
		'R' , 'S' , 'T' , 'U' , 'V' , 'W' , 
		'X' , 'Y'
		};

	private static String toUnsignedString(Long i, int shift,char[] digits) {
		char[] buf = new char[32];
		int charPos = 32;
		long radix = 1 << shift;
		long mask = radix - 1;
		do {
		buf[--charPos] = digits[(int)(i & mask)];
		i >>>= shift;
		} while (i != 0);

		return new String(buf, charPos, (32 - charPos));
	}
	
	/**
	 * 字符串转为整型数
	 * @param nStr
	 * @return
	 */
	public static Integer string2Integer(String nStr) {
		Integer res = null;
		if(nStr != null && nStr.length() != 0) {
			try {
				res = Integer.parseInt(nStr);
			} catch (Exception e) {
				e.printStackTrace();;
			}
		}
		return res;
	}
	
	/**
	 * 字符串转为双精度浮点数
	 * @param nStr
	 * @return
	 */
	public static Double string2Double(String nStr) {
		Double res = null;
		if(nStr != null && nStr.length() != 0) {
			try {
				res = Double.parseDouble(nStr);
			} catch (Exception e) {
				e.printStackTrace();;
			}
		}
		return res;
	}
	
	public static void main(String[]argw){
		System.out.println(ECNumberUtils.to32Hex(21474836767648l));
	}
}
