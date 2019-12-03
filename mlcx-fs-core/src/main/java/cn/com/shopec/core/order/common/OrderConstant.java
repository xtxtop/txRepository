package cn.com.shopec.core.order.common;

public class OrderConstant {
	
	public static final String fail_code = "0";
	public static final String success_code = "1";
	
	/**系统错误*/
	public static final String fail_msg = "系统错误";
	
	/**提示信息*/
	public static final String reminder_msg = "周围没有可查询的场站";
	public static final String reminder_msg2 = "周围没有可查询的场站和车辆";
	
	/**订单删除*/
	public static final String order_del_code = "-1";
	public static final String order_del_msg = "该订单不能删除";
	
	/**参数*/
	public static final String fail_parameters = "参数错误";
	
	/**预约后自动取消订单的时间*/
	public static final String cancel_order_key = "CANCELORDERTIME";


	
	/*** 已有记录*/
	public static final String hasRecord = "已有记录";
	/**当前没有订单 **/
	public static final String noNowOrder = "当前没有订单";
	/**
	 *该车已被租用 
	 */
	public static final String no_code = "-1";
	public static final String  no_msg = "该车已被租用";
	/**当前车型暂无计费规则 **/
	public static final String noRule = "当前车型暂无计费规则 ";
	/**订单不存在 **/
	public static final String noOrderExsit = "订单不存在 ";
	/**订单已结束 **/
	public static final String overOrder = "订单已结束 ";

	/**订单未支付 **/
	public static final String notPayOrder_code = "2";
	public static final String notPayOrder_msg = "订单未支付 ";
	/**订单已预约 **/
	public static final String yuyueOrder_code = "3";
	public static final String yuyueOrder_msg = "有已预约的订单 ";
	/**订单已计费 **/
	public static final String jifeiOrder_code = "3";
	public static final String jifeiOrder_msg = "有已预约的订单 ";
	/**订单评价 **/
	public static final String pingjiaOrder_msg = "评价成功，祝您生活愉快，欢迎下次预定！ ";
	/**测试支付宝和微信时，金额是否为0.01*/
	public static final String IS_Correct_OrderAmount = "IS_Correct_OrderAmount";
	
	/**订单已支付**/
	public static final String alreday_pay = "4";
	public static final String alreday_pay_msg = "该订单已支付！ ";
	/**订单已支付**/
	public static final String disalreday_pay = "5";
	public static final String disalreday_pay_msg = "无套餐！ ";
	
	/**订单冲账抵扣超出应付金额**/
	public static final String order_ge_strike = "2";
	public static final String order_ge_strike_msg = "该订单的冲账金额超出应付金额！ ";
	
	/**订单总冲账抵扣超出总的应付金额**/
	public static final String order_ge_strike_pay = "3";
	public static final String order_ge_strike_pay_msg = "该订单的总冲账金额超出总应付金额！ ";
	
	/**
	 * 首页控制面板展示的订单相关信息
	 */
	public static final String NEW_ORDER = "NEW_ORDER";	//今日订单
	public static final String ESC_ORDER = "ESC_ORDER";	//取消订单
	public static final String FIN_ORDER = "FIN_ORDER";	//已结束订单
	public static final String PAY_ORDER = "PAY_ORDER";	//已支付订单
	
	//订单冲账 待审核
	public static final String OrderConstant_CENSOR_STATUS = "OrderConstant_CENSOR_STATUS";	
	
	public static final String CUSTOMER_COMPANY_CODE = "CUSTOMER_COMPANY_CODE";
	
	public static final String order_fail = "-2";
	
}
