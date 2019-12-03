package cn.com.shopec.core.finace.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.Deposit;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.model.DepositOrderForMgt;
import cn.com.shopec.core.statement.model.Money;

/**
 * 押金支付订单表 数据库处理类
 */
public interface DepositOrderDao extends BaseDao<DepositOrder, String> {
	/**
	 * 获取会员的剩余押金
	 */
	Double getAmountByMemberNo(String memberNo);

	public DepositOrder getMemberNo(String memberNo);

	/**
	 * 根据会员编号获取会员当前的保证金的使用情况
	 */
	Deposit getDepositByMemberNo(String memberNo);

	/**
	 * 根据会员编号查出可以进行退款的保证金支付记录
	 */
	List<DepositOrder> getDepositRefundByMemberNo(String memberNo);

	/**
	 * 根据会员编号获取会员当前保证金中最早缴纳的一笔
	 * @param memberNo
	 * @return
	 */
	DepositOrder getEarliestDepositByMemberNo(String memberNo);
	
	/**
	 * 预付款支付宝金额
	 */
	Double getDepositAlipayAmount(@Param("money") Money money);

	Double getDepositWxAmount(@Param("money") Money money);

	Double getDepositAlipayRefund(@Param("money") Money money);

	Double getDepositWxRefund(@Param("money") Money money);

	Double getWxAmount(@Param("money") Money money);

	Double getAlipayAmount(@Param("money") Money money);

	/**
	 * 获取近10天的缴押金会员数
	 * 
	 * @return
	 */
	List<Map<String, Object>> getDepositMemberDay10CountMap();
	/**
	 * 押金缴纳列表
	 * @param q
	 * @return
	 */
	List<DepositOrderForMgt> getMemberDepositList(Query q);
	
	Long countForMgt(Query q);
}
