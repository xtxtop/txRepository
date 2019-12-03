package cn.com.shopec.core.marketing.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.PricingDeductionRecord;

/**
 * PricingDeductionRecord 服务接口类
 */
public interface PricingDeductionRecordService extends BaseService {

	/**
	 * 根据查询条件，查询并返回PricingDeductionRecord的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<PricingDeductionRecord> getPricingDeductionRecordList(Query q);

	/**
	 * 根据查询条件，分页查询并返回PricingDeductionRecord的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<PricingDeductionRecord> getPricingDeductionRecordPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个PricingDeductionRecord对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public PricingDeductionRecord getPricingDeductionRecord(String id);

	/**
	 * 根据主键数组，查询并返回一组PricingDeductionRecord对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<PricingDeductionRecord> getPricingDeductionRecordByIds(String[] ids);

	/**
	 * 根据主键，删除特定的PricingDeductionRecord记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<PricingDeductionRecord> delPricingDeductionRecord(String id, Operator operator);

	/**
	 * 新增一条PricingDeductionRecord记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param pricingDeductionRecord
	 *            新增的PricingDeductionRecord数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<PricingDeductionRecord> addPricingDeductionRecord(PricingDeductionRecord pricingDeductionRecord,
			Operator operator);

	/**
	 * 根据主键，更新一条PricingDeductionRecord记录
	 * 
	 * @param pricingDeductionRecord
	 *            更新的PricingDeductionRecord数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<PricingDeductionRecord> updatePricingDeductionRecord(
			PricingDeductionRecord pricingDeductionRecord, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为PricingDeductionRecord对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(PricingDeductionRecord obj);

	/**
	 * 套餐抵扣报表查询
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
