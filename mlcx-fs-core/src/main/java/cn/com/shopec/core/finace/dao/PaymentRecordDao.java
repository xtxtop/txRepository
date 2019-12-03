package cn.com.shopec.core.finace.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.finace.model.PaymentRecord;
import cn.com.shopec.core.statement.model.Money;

/**
 * 支付记录表 数据库处理类
 */
public interface PaymentRecordDao extends BaseDao<PaymentRecord, String> {
	/**
	 * 根据支付方式和单号查询是否存在支付记录
	 * 
	 * @param paymentMethod
	 */
	List<PaymentRecord> getByDOrder(Integer objType, String depositOrderNo, Integer paymentMethod);

	/**
	 * 获取预付款支付宝金额
	 */
	Double getDepositAlipayAmount(@Param("money") Money money);

	/*
	 * 获取订单支付金额
	 */
	Double getOrderAlipayAmount(@Param("money") Money money);

	Double getDepositWxAmount(@Param("money") Money money);

	Double getPriceAlipayAmount(@Param("money") Money money);

	Double getOrderWxAmount(@Param("money") Money money);

	Double getPriceWxAmount(@Param("money") Money money);
	
	/**
	 * 根据押金退款申请单号获取支付记录
	 * 
	 * @param depositRefundNo
	 */
	PaymentRecord getByDRefundNo(String depositRefundNo);

	PaymentRecord getPaymentFlowNoById(String paymentFlowNo);
	/**
	 * 正式数据库 支付宝押金手续费
	 */
	Double getDepositAlipayCharge(@Param("money") Money money);
	/**
	 * 正式数据库 支付宝订单手续费
	 */
	Double getOrderAlipayCharge(@Param("money") Money money);
	/**
	 * 正式数据库 支付宝套餐手续费
	 */
	Double getPriceAlipayCharge(@Param("money") Money money);
	//正式 数据库 微信押金手续费
	Double getDepositWxCharge(@Param("money") Money money);
	//正式 微信订单手续费
	Double getOrderWxCharge(@Param("money") Money money);
	//正式 微信 套餐 手续费
	Double getPriceWxCharge( @Param("money") Money money);
	
	/**
	 * 获取支付宝收支对象
	 * 
	 * @param money
	 * @return
	 */
	Map<String, Object> getAlipayIncomeMap( @Param("money") Money money);
	
	/**
	 * 获取微信收支对象
	 * 
	 * @param money
	 * @return
	 */
	Map<String, Object> getWxIncomeMap( @Param("money") Money money);
	/**
	 *供日租统计对账使用-按指定时间统计
	 * @param orderNos
	 * @param paidTimeStart
	 * @param paidTimeEnd
	 * @return
	 */
	Map<String, Object> getMerchantOrderPaidAmount(PaymentRecord paymentRecord);
	/**
	 * 供日租统计对账使用-按月统计
	 * @param orderNos
	 * @return
	 */
	List<Map<String, Object>> getMerchantOrderPaidAmountByMonth(PaymentRecord paymentRecord);
}
