package cn.com.shopec.core.car.common;

public class CarConstant {

	public static final String fail_code = "0";
	public static final String fail_code_close = "-1";
	public static final String success_code = "1";
	public static final String car_NOFlameout = "2";
	public static final String car_NOInPark = "3";
	public static final String car_NOoutPark = "4";
	public static final String park_NO = "5";

	/** 系统错误 */
	public static final String fail_msg = "系统错误";
	/**车辆已占用*/
	public static final String usered_car_msg = "车辆已占用，请重新选择";
	/** 无可用车辆 */
	public static final String available_car_msg = "暂无可用车辆";
	/** 场站不可用 */
	public static final String available_park_msg_clsoe = "场站暂时不可用";

	/** 参数错误 */
	public static final String fail_paramters = "参数错误";

	/** 倒计时的key */
	public static final String countdown_param_key = "COUNTDOWN";

	/** 预约时间的key **/
	public static final String prebook_param_key = "PREBOOK";

	/** 下单后自动取消订单时长key */
	public static final String cancelordertime_param_key = "CANCELORDERTIME";

	/** 租车时系统配置每分钟多少钱 */
	public static final String piece_price_param_key = "PIECEPRICE";

	/** 资费说明 */
	public static final String expenses_param_key = "EXPENSES";

	/** 车辆状态-已启动 */
	public static final Integer CAR_STATUS_STARTUP = 1;

	/** 车辆状态-已熄火 */
	public static final Integer CAR_STATUS_SHUTDOWN = 2;

	/** 车辆使用状态-空闲 */
	public static final int CAR_USERAGE_STATUS_FREE = 0;

	/** 车辆使用状态-已预占 */
	public static final int CAR_USERAGE_STATUS_RESERVED = 1;

	/** 车辆使用状态-订单中 */
	public static final int CAR_USERAGE_STATUS_IN_ORDER = 2;

	/** 车辆使用状态-调度中 */
	public static final int CAR_USERAGE_STATUS_IN_WORKER_ORDER = 3;

	/** 开车门 */
	public static final String UNLOCKCARDOOR = "UNLOCKCARDOOR";

	/** 开车门距离限制 */
	public static final String UNLOCKCARDOOR_DISTINCE_LIMIT = "UNLOCKCARDOOR_DISTINCE_LIMIT";

	/** 关车门 */
	public static final String LOCKCARDOOR = "LOCKCARDOOR";

	/** 找车 */
	public static final String FINDCAR = "FINDCAR";
	/** 电话号码 */
	public static final String PHONE = "PHONE";

	/** 电量限制 */
	public static final String power_limit = "POWERLIMIT";

	/** 该车型暂无计费规则，或计费规则未启用 */
	public static final String noCarRule = "该车型暂无计费规则，或计费规则未启用!请先添加或编辑计费规则";

	/** 车辆不是空闲状态 */
	public static final String car_no_free = "2";

	/** 车辆不是空闲状态 */
	public static final String car_no_free_msg = "该车辆不是空闲状态，无法进行下线操作！";

	/** 车辆不存在 */
	public static final String no_car_exsit_msg = "该车辆不存在！";

	/** 车辆上线成功 */
	public static final String on_line_msg = "该车辆上线成功！";

	/** 车辆下线成功 */
	public static final String off_line_msg = "该车辆下线成功！";

	/** 车辆在场站外是否可以下调度单 */
	public static final String is_allow_scheduling = "IS_ALLOW_SCHEDULING";

	/** 订单超时转计费 */
	public static final String order_overTime_charging = "ORDER_OVERTIME_CHARGING";
	/** 终端设备是否在线 */
	public static final String car_online_time_threshol = "CAR_ONLINE_TIME_THRESHOL";
	/** 终端设备报文 倒退时间 */
	public static final String push_down_time = "PUSH_DOWN_TIME";
	/** app端订单列表分页 每页展示的数量 */
	public static final String orderPageSize = "orderPageSize";
	/** app端订单列表分页 每页展示的数量 */
	public static final String deviceUploadlogPagesize = "DEVICEUPLOADLOGPAGESIZE";
	/** 车辆添加数量 */
	public static final String CAR_NUM = "CAR_NUM";
	/** 日租异地还车费 */
	public static final String TAXI_FARE = "TAXI_FARE";
	/** 自定义计费规则查询时间 */
	public static final String PRICING_DATE = "PRICING_DATE";
	/** 是否支持不计免赔 */
	public static final String IS_REGARDLESS = "IS_REGARDLESS";
	/** 不计免赔金额 */
	public static final String REGARDLESS_FRANCHISE = "REGARDLESS_FRANCHISE";
	
	
}
