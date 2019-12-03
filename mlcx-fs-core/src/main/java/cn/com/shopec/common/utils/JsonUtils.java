package cn.com.shopec.common.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 * JSON工具类
 * 
 */

public class JsonUtils {
	/**日期配置*/
	private static  final  SerializeConfig   dateConfig;
	
	/**空值配置*/
	private static  final  SerializerFeature[] nullFeatures = {
		SerializerFeature.WriteMapNullValue,       //  Map输出空置字段
		SerializerFeature.WriteNullListAsEmpty,    //  list字段如果为null，输出为[]，而不是null
		SerializerFeature.WriteNullNumberAsZero,   //  数值字段如果为null，输出为0，而不是null
		SerializerFeature.WriteNullBooleanAsFalse, //  Boolean字段如果为null，输出为false，而不是null
		SerializerFeature.WriteNullStringAsEmpty   //  字符类型字段如果为null，输出为""，而不是null
	};
	
	static {
		dateConfig = new SerializeConfig();
//		ObjectSerializer dataFormat = new ObjectSerializer(){
//			@Override
//			public void write(JSONSerializer jsonserializer, Object obj, Object obj1, Type type, int arg4) throws IOException {
//				if(obj == null){
//					jsonserializer.getWriter().writeNull();
//		            return;
//		        } else {
//		            Date date = (Date)obj;
//		            /**默认的时间格式化器，格式为yyyy-MM-dd HH:mm:ss:*/
//		        	SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
//		            jsonserializer.write(defaultFormat.format(date));
//		            return;
//		        }
//			}
//		};
		String dateFormat = "yyyy-MM-dd HH:mm:ss"; 
		dateConfig.put(java.util.Date.class,new SimpleDateFormatSerializer(dateFormat));
		dateConfig.put(java.sql.Date.class, new SimpleDateFormatSerializer(dateFormat));
	}
	
	/**
	 * 将对象转换成Json字符串
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String toJsonString(Object object)throws Exception{
		return JSON.toJSONString(object, dateConfig,SerializerFeature.WriteMapNullValue);
	}
	
	/**
	 * 将对象转换成Json字符串，对null对象进行格式化<br/>
	 * Map输出空置字段(null)<br/>
	 * list字段如果为null，输出为[]，而不是null<br/>
	 * 数值字段如果为null，输出为0，而不是null<br/>
	 * Boolean字段如果为null，输出为false，而不是null<br/>
	 * 字符类型字段如果为null，输出为""，而不是null<br/>
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String toJsonStringFormatNull(Object object)throws Exception{
		return JSON.toJSONString(object, dateConfig,nullFeatures);
	}
	
	
	/**
	 * 将字符串转换成指定的对象
	 * @param <T>
	 * @param jsonStr
	 * @param t
	 * @return
	 */
	public static <T> T parse2Object(String jsonStr,Class<T> t){
		return JSON.parseObject(jsonStr, t);
	}
	
	/**
	 * 将Json数组字符串转换成数组对象
	 * @param <T>
	 * @param arrayJsonStr
	 * @param t
	 * @return
	 */
	public static <T> List<T> parse2ListObject(String arrayJsonStr,Class<T> t){
		return JSON.parseArray(arrayJsonStr,t);
	}
}
