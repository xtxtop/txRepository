package cn.com.shopec.core.dailyrental.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.PricingRuleDay;

/**
 * 计费规则表 服务接口类
 */
public interface PricingRuleDayService extends BaseService {

	/**
	 * 根据查询条件，查询并返回PricingRuleDay的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<PricingRuleDay> getPricingRuleDayList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回PricingRuleDay的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<PricingRuleDay> getPricingRuleDayPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个PricingRuleDay对象
	 * @param id 主键id
	 * @return
	 */
	public PricingRuleDay getPricingRuleDay(String id);

	/**
	 * 根据主键数组，查询并返回一组PricingRuleDay对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<PricingRuleDay> getPricingRuleDayByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的PricingRuleDay记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PricingRuleDay> delPricingRuleDay(String id, Operator operator);
	
	/**
	 * 新增一条PricingRuleDay记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param pricingRuleDay 新增的PricingRuleDay数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PricingRuleDay> addPricingRuleDay(PricingRuleDay pricingRuleDay, Operator operator);
	
	/**
	 * 根据主键，更新一条PricingRuleDay记录
	 * @param pricingRuleDay 更新的PricingRuleDay数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PricingRuleDay> updatePricingRuleDay(PricingRuleDay pricingRuleDay, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为PricingRuleDay对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(PricingRuleDay obj);

	public PricingRuleDay getPricingRuleDayCarModelIdAndCityId(String carModelId,String cityId);
		
}
