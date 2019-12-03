package cn.com.shopec.core.car.common;

public class OrderDayConstant {

	/** 整个应用0表示失败 */
	public static final String FAIL = "0";
	/** 整个应用1表示成功 */
	public static final String SECCUESS = "1";

	/** 系统错误 */
	public static final String fail_msg = "系统错误";
	/** 系统正常 */
	public static final String normal_msg = "获取信息成功";
	/** 异地还车费用错误 */
	public static final String taxiFare_msg = "未获取到异地还车费";
	/** 场站信息错误 */
	public static final String park_msg = "未获取到场站信息";
	/** 无可用车辆 */
	public static final String available_car_msg = "暂无可用车辆";
	/** 该车型暂无计费规则，或计费规则未启用 */
	public static final String noCarRule = "该车型暂无计费规则，或计费规则未启用!请先添加或编辑计费规则";
	/** 无库存车辆 */
	public static final String cy_msg = "库存暂无车辆";
	/** 客户所选时间段无车辆 */
	public static final String od_cy_msg = "所选时间段暂无车辆";
	/** 日租取车最大日期 */
	public static final String take_the_car_time = "take_the_car_time";
	/** 日租还车最大日期 */
	public static final String return_the_car_time = "return_the_car_time";
	/** 日租最大取车天数 */
	public static final String car_rental_days = "car_rental_days";
	/** 车辆没有设置附加费用 */
	public static final String noCarExtraCosts = "车辆没有设置附加费用，请先添加设置附加费用";
	/** 车辆库存唯一性 */
	public static final String checkCarExtraCosts = "当前车辆库存已存在，如需改动请选择‘编辑’功能。";
	/** 订单已预约 **/
	public static final String yuyueOrder_code = "3";
	public static final String yuyueOrder_msg = "已有预约的订单 ";
	/** 订单进行中 **/
	public static final String jifeiOrder_code = "4";
	public static final String jifeiOrder_msg = "已有进行中的订单 ";
	/** 暂无订单 **/
	public static final String no_Order_code = "5";
	public static final String no_Order_msg = "暂无订单";
	/** 日租租车客户所选时间正常 **/
	public static final String carRentalTime_msg = "客户所选时间正常 ";
	/** 订单提交 **/
	public static final String order_submit_success_msg = "订单提交成功";
	public static final String order_submit_fail_msg = "订单提交失败";
}
