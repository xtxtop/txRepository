package cn.com.shopec.core.order.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.PricingPackOrder;

/**
 * 套餐订单表 数据库处理类
 */
public interface PricingPackOrderDao extends BaseDao<PricingPackOrder,String> {
/**
 * 获取会员套餐列表   zln
 * */
	List<PricingPackOrder> getPricingPackOrderListByUse(Query q);
	/**
	 * 根据条件查询数据
	 * @param q
	 * @return
	 */
	public List<PricingPackOrder> pageLists(Query q);
	
	
	/**
	 * 根据条件查询数据
	 * @param q
	 * @return
	 */
	public List<PricingPackOrder> queryAllForPackageType(Query q);
	
	/**
	 * 根据条件查询数据条件
	 * @param q
	 * @return
	 */
	public Long counts(Query q);
	List<PricingPackOrder> getPricingPackOrderListByDiKou(Query q);
	/**
	 * 根据金额套餐抵扣
	 * @param q
	 * @return
	 */
	List<PricingPackOrder> getPricingPackOrderAmountListByDiKou(Query q);
	
	List<PricingPackOrder> availablePackageList(Query q);
	List<PricingPackOrder> disablePackageList(Query q);
	/*
	 * 查询赠送套餐报表信息
	 */
	public List<PricingPackOrder> eportFormPackageList(Query q);
	/*
	 * 查询赠送套餐报表信息（全部）
	 */
	public List<PricingPackOrder> eportFormPackageListAll();
	/*
	 * 查询赠送套餐报表信息（月份）
	 */
	public List<PricingPackOrder> eportFormPackageListMonth(Query q);
	/*
	 * 查询赠送套餐报表信息（日）
	 */
	public List<PricingPackOrder> eportFormPackageListDay(Query q);
	/**
	 * 获取对应套餐总销售额
	 */
	public Double getPackageSum(String packageId);
	/**
	 * 获取对应套餐数量
	 */
	public Long getPackageCount(String packageId);
	
	//我的余额
	 public   List<PricingPackOrder> getPricingPackOrderListBypo(Query q);
	 
	/**
	 * 统计充值
	 */
	public Integer countRecharge();
		
}
