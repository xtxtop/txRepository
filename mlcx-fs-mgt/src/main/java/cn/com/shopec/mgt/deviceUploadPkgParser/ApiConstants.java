package cn.com.shopec.mgt.deviceUploadPkgParser;

/**
 * 车载设备api接口常量类
 *
 */
public class ApiConstants {

	/**
	 * 设备上传数据包的包头
	 */
	public static final String DEVICE_UPLOAD_PACKAGE_HEAD = "$E6";
	
	/**
	 * 设备上传数据包的包尾
	 */
	public static final String DEVICE_UPLOAD_PACKAGE_TAIL = "\r\n";
	
	/**
	 * 设备设置命令数据包的包头
	 */
	public static final String DEVICE_SETTING_COMMAND_HEAD = "AT+";
	
	/**
	 * 设备设置命令数据包的包尾
	 */
	public static final String DEVICE_SETTING_COMMAND_TAIL = "\r\n";

	/**
	 * 设备设置命令-请求终端设备信息
	 */
	public static final String DEVICE_SETTING_CMD_SRI = "SRI";
	
	/**
	 * 设备设置命令-请求终端时间
	 */
	public static final String DEVICE_SETTING_CMD_SRRTC = "SRRTC";
	
	/**
	 * 设备设置命令-设置终端时间
	 */
	public static final String DEVICE_SETTING_CMD_SCRTC = "SCRTC";
	
	/**
	 * 设备设置命令-请求当前车辆电瓶电压
	 */
	public static final String DEVICE_SETTING_CMD_SRBAT = "SRBAT";
	
	/**
	 * 设备设置命令-请求当前GSM卡信息
	 */
	public static final String DEVICE_SETTING_CMD_SRGSM = "SRGSM";

	/**
	 * 设备设置命令-请求当前GSM网络信号值
	 */
	public static final String DEVICE_SETTING_CMD_SRCSQ = "SRCSQ";
	
	/**
	 * 设备设置命令-密钥更新指令
	 */
	public static final String DEVICE_SETTING_CMD_SCAKEY = "SCAKEY";	

	/**
	 * 设备设置命令-查询设备当前密钥
	 */
	public static final String DEVICE_SETTING_CMD_SRAKEY = "SRAKEY";
	
	/**
	 * 设备设置命令-重启设备
	 */
	public static final String DEVICE_SETTING_CMD_SCWS = "SCWS";
	
	/**
	 * 设备设置命令-重启设备蓝牙模块
	 */
	public static final String DEVICE_SETTING_CMD_SCPBTRST = "SCPBTRST";

	/**
	 * 设备设置命令-重启PLUS盒子
	 */
	public static final String DEVICE_SETTING_CMD_SCPLUSRST = "SCPLUSRST";
	
	/**
	 * 设备设置命令-清除缓存记录
	 */
	public static final String DEVICE_SETTING_CMD_SCHIS = "SCHIS";

	/**
	 * 设备设置命令-恢复出厂设置
	 */
	public static final String DEVICE_SETTING_CMD_SCZ = "SCZ";

	/**
	 * 设备设置命令-请求车辆信息
	 */
	public static final String DEVICE_SETTING_CMD_SRVI = "SRVI";
	
	/**
	 * 设备设置命令-远程更新
	 */
	public static final String DEVICE_SETTING_CMD_SCFTPS = "SCFTPS";

	/**
	 * 设备设置命令-C01（服务器远程开锁）命令
	 */
	public static final String DEVICE_SETTING_CMD_C01 = "C01";
	
	/**
	 * 设备设置命令-C06（服务器远程寻车）命令
	 */
	public static final String DEVICE_SETTING_CMD_C06 = "C06";
	
	/**
	 * 设备设置命令-C81（服务器远程落锁）命令
	 */
	public static final String DEVICE_SETTING_CMD_C81 = "C81";
	
	/**
	 * 设备设置命令-SCVBR（服务器远程用车）命令
	 */
	public static final String DEVICE_SETTING_CMD_SCVBR = "SCVBR";
	
	/**
	 * 设备响应命令-3（服务器远程用车）响应命令
	 */
	public static final String DEVICE_SETTING_CMD_3 = "3";
	
	/**
	 * 设备命令-SCVRT（服务器远程还车）命令
	 */
	public static final String DEVICE_SETTING_CMD_SCVRT = "SCVRT";
	
	/**
	 * 设备响应命令-SCVRT（服务器远程还车）响应命令
	 */
	public static final String DEVICE_SETTING_CMD_4 = "4";

	/**
	 * 开关动力控制命令
	 */
	public static final String DEVICE_SETTING_CMD_SCENG = "SCENG";
	
	/**
	 * App指令数据包的包头
	 */
	public static final String APP_CMD_PACKAGE_HEAD = "$APP";

	/**
	 * App指令数据包的包尾
	 */
	public static final String APP_CMD_PACKAGE_TAIL = "\r\n";
	
	/**
	 * App控制车辆失败码-无效签名
	 */
	public static final String APP_CAR_CTRL_ERR_INVALID_SIGN = "-1";
	
	/**
	 * App控制车辆失败码-无效设备序列号
	 */
	public static final String APP_CAR_CTRL_ERR_INVALID_DEVICE_SN = "-2";
	
	/**
	 * App控制车辆失败码-无效时间
	 */
	public static final String APP_CAR_CTRL_ERR_INVALID_TIME = "-3";
	
	/**
	 * App控制车辆失败码-发送命令失败
	 */
	public static final String APP_CAR_CTRL_ERR_SEND_FAIL = "-4";

	/**
	 * App控制车辆失败码-指令执行结果返回超时
	 */
	public static final String APP_CAR_CTRL_ERR_CMD_RET_OVERTIME = "-5";
	
	/**
	 * App控制车辆失败码-无效订单号
	 */
	public static final String APP_CAR_CTRL_ERR_INVALID_ORDER_NO = "-6";
	
	/**
	 * 固件类型，E6固件
	 */
	public static final int FIRMWARE_TYPE_E6 = 6;
	
	/**
	 * 固件类型，PLUS盒子固件
	 */
	public static final int FIRMWARE_TYPE_PLUS_BOX = 5;
	
	/**
	 * 车辆控制指令返回结果——成功
	 */
	public static final String CAR_CTRL_CMD_RES_OK = "500";
	
	/**
	 * 车辆控制指令返回结果——设备号检验错误
	 */
	public static final String CAR_CTRL_CMD_RES_INVALID_DEVICESN = "501";
	
	/**
	 * 车辆控制指令返回结果——密钥无效
	 */
	public static final String CAR_CTRL_CMD_RES_ERROR_KEY = "502";
	
	/**
	 * 车辆控制指令返回结果——控制指令错误
	 */
	public static final String CAR_CTRL_CMD_RES_ERROR_CMD = "503";
	
	/**
	 * 车辆控制指令返回结果——控制指令发送发回超时
	 */
	public static final String CAR_CTRL_CMD_RES_OVERTIME = "504";
	
	/**
	 * 车辆控制指令返回结果——车门未关闭
	 */
	public static final String CAR_CTRL_CMD_RES_DOOR_UNCLOSED = "505";
	
	/**
	 * 上报车辆用车还车指令的执行结果——成功
	 */
	public static final String USE_AND_RESTURN_CAR_CTRL_RES_OK = "200";
	/**
	 * 上报车辆用车还车指令的执行结果——失败
	 */
	public static final String USE_AND_RESTURN_CAR_CTRL_RES_FAIL = "205";
	
	/**
	 * 上报车辆用车还车指令的执行结果——超时
	 */
	public static final String USE_AND_RESTURN_CAR_CTRL_RES_OVERTIME = "206";
	
	/**
	 * 上报车辆用车还车指令的执行结果——车速不为0
	 */
	public static final String USE_AND_RESTURN_CAR_CTRL_SPEED_NO_ZERO = "207";
	
	/**
	 * 上报车辆用车还车指令的执行结果——手刹未拉起
	 */
	public static final String USE_AND_RESTURN_CAR_CTRL_HANDBRAKE_NO_PULLUP = "208";
	
	/**
	 * 上报车辆用车还车指令的执行结果——档位不为空挡
	 */
	public static final String USE_AND_RESTURN_CAR_CTRL_STALLS_NO_NEUTRAL = "209";
	
	/**
	 * 上报车辆用车还车指令的执行结果——钥匙不在线
	 */
	public static final String USE_AND_RESTURN_CAR_CTRL_KEY_OFFLINE = "210";
	
	/**
	 * 上报车辆用车还车指令的执行结果——车门未关闭
	 */
	public static final String USE_AND_RESTURN_CAR_CTRL_DOOR_NO_CLOSE = "211";
	
	/**
	 * 上报车辆用车还车指令的执行结果——车窗未关闭
	 */
	public static final String USE_AND_RESTURN_CAR_CTRL_WINDOW_NO_CLOSE = "212";
	/**
	 * 上报车辆用车还车指令的执行结果——车辆未熄火
	 */
	public static final String USE_AND_RESTURN_CAR_CTRL_RES_NOFLAMEOUT = "213";
	/**
	 * 上报车辆用车还车指令的执行结果——车灯未关闭
	 */
	public static final String USE_AND_RESTURN_CAR_CTRL_RES_CAR_LIGHTS_NO_CLOSED = "214";
	/**
	 * 上报车辆用车还车指令的执行结果——后尾箱未关闭
	 */
	public static final String USE_AND_RESTURN_CAR_CTRL_RES_CAR_REAR_TRUNK_NO_CLOSED = "215";
	/**
	 * 上报车辆用车还车指令的执行结果——当前设备不支持此指令
	 */
	public static final String USE_AND_RESTURN_CAR_CTRL_DEIVCE_NO_SUPPORT = "255";
	
	/**
	 * App操作人类型——会员
	 */
	public static final String APP_OPERATOR_TYPE_MEMBER = "1";
	
	/**
	 * App操作人类型——调度员
	 */
	public static final String APP_OPERATOR_TYPE_WORKER = "2";
	/**
	 * 硬件是否真实响应开关
	 */
	public static final String DEVICE_CMD_FLAG = "DEVICECMDFLAG";
	
}
