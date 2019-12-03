package cn.com.shopec.core.marketing.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.PricingDeductionRecord;

/**
 * PricingDeductionRecord 数据库处理类
 */
public interface PricingDeductionRecordDao extends BaseDao<PricingDeductionRecord, String> {

	/**
	 * 套餐抵扣报表查询（全部）
	 *
	 */
	public List<PricingDeductionRecord> eportFormPackageDeductibleAll();

	/**
	 * 套餐抵扣报表查询（年）
	 *
	 */
	public List<PricingDeductionRecord> eportFormPackageDeductibleList(Query q);

	/**
	 * 套餐抵扣报表查询（月）
	 *
	 */
	public List<PricingDeductionRecord> eportFormPackageDeductibleMonth(Query q);

	/**
	 * 套餐抵扣报表查询（天）
	 *
	 */
	public List<PricingDeductionRecord> eportFormPackageDeductibleDay(Query q);
}
