package cn.com.shopec.core.dailyrental.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.PricingRuleCustomDate;

/**
 * 自定义日期价格表 服务接口类
 */
public interface PricingRuleCustomDateService extends BaseService {

	/**
	 * 根据查询条件，查询并返回PricingRuleCustomDate的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<PricingRuleCustomDate> getPricingRuleCustomDateList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回PricingRuleCustomDate的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<PricingRuleCustomDate> getPricingRuleCustomDatePagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个PricingRuleCustomDate对象
	 * @param id 主键id
	 * @return
	 */
	public PricingRuleCustomDate getPricingRuleCustomDate(String id);

	/**
	 * 根据主键数组，查询并返回一组PricingRuleCustomDate对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<PricingRuleCustomDate> getPricingRuleCustomDateByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的PricingRuleCustomDate记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PricingRuleCustomDate> delPricingRuleCustomDate(String id, Operator operator);
	
	public ResultInfo<PricingRuleCustomDate> delPricingRuleCustomDateByRuleId(String ruleId, Operator operator);
	/**
	 * 新增一条PricingRuleCustomDate记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param pricingRuleCustomDate 新增的PricingRuleCustomDate数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PricingRuleCustomDate> addPricingRuleCustomDate(PricingRuleCustomDate pricingRuleCustomDate, Operator operator);
	
	/**
	 * 根据主键，更新一条PricingRuleCustomDate记录
	 * @param pricingRuleCustomDate 更新的PricingRuleCustomDate数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PricingRuleCustomDate> updatePricingRuleCustomDate(PricingRuleCustomDate pricingRuleCustomDate, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为PricingRuleCustomDate对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(PricingRuleCustomDate obj);
	
	/**
	 * 根据查询条件，分页查询并返回PricingRuleCustomDate的分页结果（后台）
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<PricingRuleCustomDate> getPricingRuleCustomDatePagedListForMgt(Query q);
		
}
