package cn.com.shopec.core.marketing.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.Coupon;

/**
 * 优惠券表 数据库处理类
 */
public interface CouponDao extends BaseDao<Coupon,String> {

	//void addBatch(@Param("coupon") Coupon coupon,@Param("memberNos") String[] memberNos);

	void addBatch(List<Coupon> coupons);

	List<Coupon> queryAllOrder(Query q);

	/**
	 * 按条件得到优惠卷数量（管理端用）
	 * @param q
	 * @return
	 */
	long getMemberCouponCount(Query q);
	/**
	 * 按条件得到优惠卷列表（管理端用）
	 * @param q
	 * @return
	 */
	List<Coupon> getMemberCouponPageList(Query q);
	/**
	 * 根据优惠券方案编号查询该优惠券已方法数量
	 */
	List<Coupon> listCouponByPlanNo(String planNo);
	
}
