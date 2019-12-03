package cn.com.shopec.core.dailyrental.search.common;

/**
 * solr文档字段常量类
 *
 */
public class SolrDocConstants {

	/** 数据类型为场站 **/
	public static final int DATA_TYPE_PARK = 1;
	/** 数据类型为车辆 **/
	public static final int DATA_TYPE_CAR = 2;
	/** 场站DOCID前缀 **/
	public static final String PARK_ID_PREFIX = "P";
	/** 车辆DOCID前缀 **/
	public static final String CAR_ID_PREFIX = "C";
	
	
	// 在solr的schema.xml里面的定义字段名称

	/** 主键 **/
	public static final String DOC_ID_FIELD = "id";
	/** 编号 **/
	public static final String DATA_NO_FIELD = "dataNo";
	/** 名称 **/
	public static final String DATA_NAME_FIELD = "dataName";
	/** 类型 1. 场站 2车辆 **/
	public static final String DATA_TYPE_FIELD = "dataType";
	/** 车型 **/
	public static final String CAR_MODELNAME_FIELD = "carModelName";
	/** 车剩余电量 **/
	public static final String POWER_FIELD = "power";
	/** 所属商家id **/
	public static final String MERCHANT_ID_FIELD = "merchantId";
	/** 城市id **/
	public static final String CITY_ID_FIELD = "cityId";
	/** 创建时间 **/
	public static final String INDEXED_TIME_FIELD = "indexedTime";
	/** 是否可见 **/
	public static final String IS_VIEW_FIELD = "isView";
	/** 车辆状态 **/
	public static final String CAR_STATUS_FIELD = "carStatus";
	/** 上下线，启停用状态 **/
	public static final String IS_AVAILABLE_FIELD = "isAvailable";
	/** 场站剩余车位 **/
	public static final String PARKING_SPACES_FIELD = "parkingSpaces";
	/** 车辆是否在场站内 **/
	public static final String IS_IN_PARK_FIELD= "isInPark";
	/** 场站类型 **/
	public static final String PARK_TYPE_FIELD = "parkType";
	/** 经度 **/
	public static final String LONGITUDE_FIELD = "longitude";
	/** 纬度 **/
	public static final String LATITUDE_FIELD = "latitude";
	/** 地址 **/
	public static final String ADDRESS_FIELD = "address";
	/** 距离 **/
	public static final String DISTANCE_FIELD = "distance";
	/** 场站电子围栏半径 **/
	public static final String ELEC_FENCE_RADIUS_FIELD = "elecFenceRadius";
	/** 场站多边形坐标 **/
	public static final String PARK_GEO_FIELD = "parkGeo";
	/** 多边形几何中心坐标，格式为：纬度,经度 **/
	public static final String PARK_LOCATION_FIELD = "parkLocation";
	
}
