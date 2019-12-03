package cn.com.shopec.core.marketing.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.marketing.model.RedeemCouponPlan;


/**
 * RedeemCode 数据库处理类
 */
public interface RedeemCouponPlanDao extends BaseDao<RedeemCouponPlan,String> {
	
	
	public int deleteByRedCode(String redCode);

	public List<RedeemCouponPlan> getAllByRedCode(String redCode);
}
