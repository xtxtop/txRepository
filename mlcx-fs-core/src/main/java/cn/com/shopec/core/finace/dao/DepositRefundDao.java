package cn.com.shopec.core.finace.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.finace.model.DepositRefund;
import cn.com.shopec.core.finace.model.MemberDepositRefund;

/**
 * 押金退款表 数据库处理类
 */
public interface DepositRefundDao extends BaseDao<DepositRefund, String> {
	/**
	 * 获取会员已经申请的退款总金额
	 */
	MemberDepositRefund getApplyAmountByMemberNo(String memberNo);

	/**
	 * 会员有已经申请保证金退款并还没有退款成功的记录
	 */
	List<DepositRefund> getNoPayByMemberNo(String memberNo);

	Integer countDepositRefundCensorStatus();

	

}
