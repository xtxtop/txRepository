package cn.com.shopec.core.resource.common;

public class ParkConstant {
	
	/**定位范围（距离）的key*/
	public static final String distance_param_key= "DISTANCE";
	
	/**系统参数key—场站电子围栏半径默认值*/
	public static final String SYS_PARAM_KEY_PARK_ELEC_FENCE_RADIUS_DEF_VAL = "PARK_ELEC_FENCE_RADIUS_DEF_VAL";
	
	/**场站电子围栏半径默认值，单位：米*/
	public static final int PARK_ELEC_FENCE_RADIUS_DEF_VAL = 100;
	/**
	 * ParkConstant.distance_param_key
	 * */
	public static final String power_limit= "POWERLIMIT";
	/**
	 * 暂无场站
	 * */
	public static final String no_park= "该市暂无场站";
	/**系统参数key—场站类型*/
	public static final String PARK_TYPE = "ISPLOYGONPARK";
	
	/**
	 * 修改场站车位数量无效
	 * */
	public static final String FAIL_PARKSPACENUMBER_SET = "修改场站车位数量不能小于已添加的车位数量";
	
	/**
	 * 新增场站车位无效
	 * */
	public static final String FAIL_PARKSPACE_NOEXISTENCE = "该场站车位不存在";
	
	/**
	 * 新增场站车位无效
	 * */
	public static final String FAIL_PARKSPACE_FULL_ADD = "该场站车位数量以满，无法新增车辆";
	
	public static final int PARK_IS_DELETED = 0;
	public static final int PARK_IS_AVAILABLE = 1;
	public static final int CAR_USERAGE_STATUS = 0;
	
}
