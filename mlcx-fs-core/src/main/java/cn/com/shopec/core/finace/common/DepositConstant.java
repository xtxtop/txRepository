package cn.com.shopec.core.finace.common;

import cn.com.shopec.core.system.model.SysParam;

public class DepositConstant {
	
	/**保证金的key*/
	public static final String deposit_amount_key= "DEPOSIT_AMOUNT";
	public static final String no_member= "传入的参数会员编号为空！";
	public static final String exit_member= "该会员已经缴纳保证金了！";
	
	/**一个是有没有已结束，但未支付订单；一个是有没有已创建，但还未结束/未取消的订单，都不能申请退款*/
	public static final String no_refund= "2";
	public static final String no_refund_msg="有未支付或正在进行中的订单，不能申请保证金退款！";
	
	/**最近结束/取消的订单，距离当前时间，应当要超过20天。这个20天，应该是来自系统参数。未超过20天的也不能申请退款*/
	public static final String no_refund1= "3";
	public static final String no_refund_msg1="最近结束或取消的订单距离当前时间不在允许范围内，不能申请保证金退款！";
	
	/**已有申请的退款记录，未退款。不可再次申请*/
	public static final String no_refund2= "4";
	public static final String no_refund_msg2="已经申请保证金退款，平台正在处理中！";
	
	/**最近结束/取消的订单，距离当前时间，应当要超过20天。这个20天，应该是来自系统参数。未超过20天的也不能申请退款*/
	public static final String no_refund_days= "NO_REFUND_DAYS";
	
	/**没有缴纳保证金*/
	public static final String no_pay_deposit= "没有缴纳保证金";
	
	/**未缴纳保证金，有退款正在处理中*/
	public static final String refund_no_pay_deposit= "未缴纳保证金，有退款正在处理中";
	
	/**保证金已缴纳，有退款正在处理中*/
	public static final String refund_pay_deposit= "保证金已缴纳，有退款正在处理中";
	
	/**已经缴纳，没有处理中的退款*/
	public static final String pay_deposit= "已缴纳保证金";
	
	/**已经缴纳，没有处理中的退款,最新申请审核不通过*/
	public static final String pay_deposit_cencor= "抱歉，您的退款申请审核失败，请重新申请退款或联系客服处理。";
	/***/
	public static final String depositRefund_Censor_Status= "depositRefund_Censor_Status";
	/*押金使用或提现说明*/
	public static final String DEPOSIT_USE= "DEPOSIT_USE";
	
}
