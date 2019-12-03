package cn.com.shopec.core.finace.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.finace.model.FinaceTest;
import cn.com.shopec.core.statement.model.Money;

/**
 * FinaceTest 数据库处理类
 */
public interface FinaceTestDao extends BaseDao<FinaceTest,String> {
	
	/**
	 * 获取导入的全部预付款支付宝金额
	 */
	Double getDepositAlipayAmountWhole(@Param("money") Money money);
	
	//全部 押金支付宝退款（押金 退款 支付宝）
	Double getDepositAlipayRefundWhole(@Param("money") Money money);

	//全部 订单 支付宝 收入
	Double  getOrderAlipayAmountWhole(@Param("money") Money money);

	//全部 套餐 支付宝 收入
	Double getPriceAlipayAmountWhole(@Param("money") Money money);

	Double getDepositWxAmountWhole(@Param("money") Money money);

	Double getOrderWxAmountWhole(@Param("money") Money money);

	Double getDepositWxRefundWhole(@Param("money") Money money);

	Double getPriceWxAmountWhole(@Param("money") Money money);
	//全部 支付宝押金手续费
	Double getDepositAlipayChargeWhole(@Param("money") Money money);
	//全部  支付宝 订单 手续费
	Double getOrderAlipayChargeWhole(@Param("money") Money money);
	//全部  支付宝 套餐 手续费
	Double getPriceAlipayChargeWhole(@Param("money") Money money);
	//全部 微信 押金 手续费
	Double getDepositWxChargeWhole (@Param("money") Money money);
	//全部 微信 订单 手续费
	Double getOrderWxChargeWhole(@Param("money")  Money money);
	//全部 微信  套餐 手续费
	Double getPriceWxChargeWhole( @Param("money") Money money);

	/**
	 * 获取指定时间段的交易账单数
	 * @param method 交易类型  alipay weixin
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @return
	 */
	Long getCountByTime(String method, String startTime, String endTime);

	/**
	 * 获取支付宝转账退款的金额
	 * @param money
	 * @return
	 */
	Double getAlipayTransRefundWhole(@Param("money") Money money);

	/**
	 * 根据流水单号获取对账单信息
	 * @param payflowNo
	 * @return
	 */
	FinaceTest getByFlowNo(String payflowNo);
	
	/**
	 * 获取支付宝收支合计对象
	 * 
	 * @param money
	 * @return
	 */
	Map<String, Object> getAlipayIncomeWholeMap( @Param("money") Money money);
	
	/**
	 * 获取微信收支合计对象
	 * 
	 * @param money
	 * @return
	 */
	Map<String, Object> getWxIncomeWholeMap( @Param("money") Money money);
	
}
