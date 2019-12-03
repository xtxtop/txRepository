package cn.com.shopec.core.map.common;

/**
 * 百度api接口常量类
 *
 */
public class BaiDuApiConstants {
	/**
	 * 成功
	 */
	public static final String SUCCESS = "0";
	
	/**
	 *  服务器内部错误
	 */
	public static final String INTENAL_ERROR = "1";
	
	/**
	 * http method错误
	 */
	public static final String HTTP_MEHTOD_ERROR = "3";
	
	/**
	 * http content-type错误
	 */
	public static final String HTTP_CONTENT_TYPE_ERROR = "4";

	/**
	 * track的column_key重复
	 */
	public static final String TRACK_COLUMN__REPEAT_ERROR = "2001";
	
	/**
	 * column_key是保留字段
	 */
	public static final String TRACK_COLUMN_REMAIN_ERROR = "2002";
	
	/**
	 * track的属性数量达到了最大值
	 */
	public static final String TRACK_MAXLIMIT_ERROR = "2003";
	
	/**
	 *指定entity不存在
	 */
	public static final String ENTITY_NO_EXISTS = "3003";
	/**
	 * track_ids的数量或值不对
	 */
	public static final String TRACK_IDS_NUM_ERROR = "3004";

	/**
	 * entity已存在
	 */
	public static final String ENTITY_EXISTS = "3005";
	
	/**
	 * entity的column_key重复
	 */
	public static final String ENTITY_COLUMN_REPEAT = "7001";	

	/**
	 * 指定的entity属性不存在 
	 */
	public static final String ENTITY_COLUMN_NO_EXISTS = "7002";
	
	/**
	 * 坐标系类型——GPS坐标系（wgs84标准）
	 */
	public static final int COORD_TYPE_GPS = 1;
	
	/**
	 * 坐标系类型——国测局加密坐标系
	 */
	public static final int COORD_TYPE_GCJ = 2;
	
	/**
	 * 坐标系类型——百度加密坐标系
	 */
	public static final int COORD_TYPE_BD = 3;
}
