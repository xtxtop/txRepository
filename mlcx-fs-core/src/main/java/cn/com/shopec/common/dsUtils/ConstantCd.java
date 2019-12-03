package cn.com.shopec.common.dsUtils;

public class ConstantCd {

	// 请求地址
	public static String headUrl = "http://hardware.parking.cnool.net";
	// 签名
	public static String appSecret = "0DPc2lyXrVXDKPuY";
	// 商户ID
	public static String appId = "appid_menglong";
	// 1表示大写。
	public static final String upCase = "1";
	// 0表示小写
	public static final String smCase = "0";

	/** 网关版 **/
	// 控制锁上升(无等待确认)
	public static final String moveLockUp = "/Api/Tcp/MoveLockUp_NoCheck";
	// 控制锁下降(无等待确认)
	public static final String moveLockDown = "/Api/Tcp/MoveLockDown_NoCheck";
	/** NB版 **/
	// 控制地锁上升(仅发送命令,无状态等待)
	public static final String MoveLockUp_NoCheck = "/Api/Nbiot/MoveLockUp_NoCheck";
	// 控制地锁下降(仅发送命令,无状态等待)
	public static final String MoveLockDown_NoCheck = "/Api/Nbiot/MoveLockDown_NoCheck";

}
