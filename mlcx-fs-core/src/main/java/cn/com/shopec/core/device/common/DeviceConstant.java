package cn.com.shopec.core.device.common;

/**
 * 设备相关常量类
 *
 */
public class DeviceConstant {

	/**
	 * 设备状态-休眠
	 */
	public static final Integer DEVICE_STATUS_SLEEPING = 5;
	
	/**
	 * 设备状态-离线
	 */
	public static final Integer DEVICE_STATUS_OFFLINE = 4;
	
	/**
	 * 设备状态-待机
	 */
	public static final Integer DEVICE_STATUS_STANDBY = 3;
	
	/**
	 * 设备状态-节能模式
	 */
	public static final Integer DEVICE_STATUS_ENERGY_SAVING = 2;
	
	/**
	 * 设备状态-在线
	 */
	public static final Integer DEVICE_STATUS_ONLINE = 1;
	
	
	/**终端设备是否在线*/
	public static final String device_online_time_threshol = "DEVICE_ONLINE_TIME_THRESHOL";
}
