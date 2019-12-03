package cn.com.shopec.core.marketing.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.CouponPlan;

/**
 * 优惠券方案表 服务接口类
 */
public interface CouponPlanService extends BaseService {

	/**
	 * 根据查询条件，查询并返回Coupon的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CouponPlan> getCouponPlanList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回Coupon的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CouponPlan> getCouponPlanPagedList(Query q);
	
	/**
	 * 根据查询条件，分页查询优惠券方案数据
	 * 过滤条件：可使用、审核通过、未过期且没有方法完的优惠券方案
	 */
	public PageFinder<CouponPlan> queryCouponPlanPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个Coupon对象
	 * @param id 主键id
	 * @return
	 */
	public CouponPlan getCouponPlan(String id);

	/**
	 * 根据主键数组，查询并返回一组Coupon对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CouponPlan> getCouponPlanByIds(String[] ids);
	
	/**
	 * 新增一条Coupon记录，执行成功后传入对象及返回对update象的主键属性值不为null
	 * @param Coupon 新增的Coupon数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CouponPlan> addCouponPlan(CouponPlan couponPlan, Operator operator);
	
	
	/**
	 * 根据主键，更新CouponPlan(普通)
	 * @param couponPlan 更新CouponPlan数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CouponPlan> update(CouponPlan couponPlan, Operator operator);

	
	/**
	 * 根据主键，更新CouponPlan(业务级别)
	 * @param couponPlan 更新CouponPlan数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CouponPlan> updateForBusiness(CouponPlan couponPlan, Operator operator);
	
	/**
	 * 根据主键，审核一条CouponPlan
	 * @param couponPlan 更新的CouponPlan数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CouponPlan> updateCouponPlanCensor(CouponPlan couponPlan, Operator operator);

	
	/**
	 * 根据主键，启动或停用一条CouponPlan
	 * @param couponPlan 更新的CouponPlan数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CouponPlan> updateCouponPlanAvailable(CouponPlan couponPlan, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();

	/**
	 * 为CouponPlan对象设置更新时必要变更的值
	 * 
	 * @param obj
	 */
	void fillUpdateValues(CouponPlan couponPlan, Operator operator);

	
	/**
	 * 得到兑换券下的优惠方案
	 * @param redCode
	 * @return
	 */
	public List<CouponPlan> getCodePlanByRedCode(String redCode);


	

	
}
