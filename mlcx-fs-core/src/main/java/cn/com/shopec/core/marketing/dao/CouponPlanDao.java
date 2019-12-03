package cn.com.shopec.core.marketing.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.CouponPlan;

/**
 * 优惠券表 数据库处理类
 */
public interface CouponPlanDao extends BaseDao<CouponPlan,String> {

	/**
	 * 业务级别的update，对业务允许修改空的字段不做非空判
	 * @param couponPlan
	 * @return
	 */
	int updateForBusiness(CouponPlan couponPlan);

	List<CouponPlan> getCodePlanByRedCode(String redCode);
	
	List<CouponPlan> queryList(Query q);
	long queryCount(Query q);
	
}
