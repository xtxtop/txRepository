package cn.com.shopec.core.dailyrental.common;

/**
 * 日租订单常量
 * 
 * @author 许瑞伟
 *
 */
public class OrderConstant {

	/*** 商家订单状态（0：待签订单） */
	public static final Integer MERCHANT_ORDER_STATUS_NO_SIGN = 0;

	/*** 商家订单状态（1：接收订单） */
	public static final Integer MERCHANT_ORDER_STATUS_RECEIVE = 1;

	/*** 商家订单状态（2：拒绝订单） */
	public static final Integer MERCHANT_ORDER_STATUS_REFUSE = 2;

	/*** 商家订单状态（3：交车订单） */
	public static final Integer MERCHANT_ORDER_STATUS_GIVE = 3;

	/*** 商家订单状态（4：换车订单） */
	public static final Integer MERCHANT_ORDER_STATUS_CHANGE = 4;

	/*** 商家订单状态（5：验车订单） */
	public static final Integer MERCHANT_ORDER_STATUS_CHECK = 5;
	
	
	/*** 会员订单状态（0：已提交） */
	public static final Integer MEMBER_ORDER_STATUS_SUBMIT = 0;

	/*** 会员订单状态（1：已预约） */
	public static final Integer MEMBER_ORDER_STATUS_RESERVATION = 1;

	/*** 会员订单状态（2：进行中） */
	public static final Integer MEMBER_ORDER_STATUS_INUSE = 2;

	/*** 会员订单状态（3：结束） */
	public static final Integer MEMBER_ORDER_STATUS_END = 3;

	/*** 会员订单状态（4：已取消） */
	public static final Integer MEMBER_ORDER_STATUS_CANCEL = 4;

}
