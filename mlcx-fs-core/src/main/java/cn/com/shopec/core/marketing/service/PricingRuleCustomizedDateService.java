package cn.com.shopec.core.marketing.service;

import java.util.Date;
import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.PricingRuleCustomizedDate;

/**
 * PricingRuleCustomizedDate 服务接口类
 */
public interface PricingRuleCustomizedDateService extends BaseService {

	/**
	 * 根据查询条件，查询并返回PricingRuleCustomizedDate的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<PricingRuleCustomizedDate> getPricingRuleCustomizedDateList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回PricingRuleCustomizedDate的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<PricingRuleCustomizedDate> getPricingRuleCustomizedDatePagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个PricingRuleCustomizedDate对象
	 * @param id 主键id
	 * @return
	 */
	public PricingRuleCustomizedDate getPricingRuleCustomizedDate(String id);

	/**
	 * 根据主键数组，查询并返回一组PricingRuleCustomizedDate对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<PricingRuleCustomizedDate> getPricingRuleCustomizedDateByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的PricingRuleCustomizedDate记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PricingRuleCustomizedDate> delPricingRuleCustomizedDate(String id, Operator operator);
	
	/**
	 * 新增一条PricingRuleCustomizedDate记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param pricingRuleCustomizedDate 新增的PricingRuleCustomizedDate数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PricingRuleCustomizedDate> addPricingRuleCustomizedDate(PricingRuleCustomizedDate pricingRuleCustomizedDate, Operator operator);
	
	/**
	 * 根据主键，更新一条PricingRuleCustomizedDate记录
	 * @param pricingRuleCustomizedDate 更新的PricingRuleCustomizedDate数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PricingRuleCustomizedDate> updatePricingRuleCustomizedDate(PricingRuleCustomizedDate pricingRuleCustomizedDate, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为PricingRuleCustomizedDate对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(PricingRuleCustomizedDate obj);
	
	/**
	 * 根据主键，删除特定的PricingRuleCustomizedDate记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PricingRuleCustomizedDate> delCustomizedDateByRuleNo(String ruleNo, Operator operator);
	
	/**
	 * 根据主键，查询并返回一个PricingRuleCustomizedDate对象
	 * @param id 主键id
	 * @return
	 */
	public PricingRuleCustomizedDate getPricingRuleCustomizedDate(String ruleNo,Date customizedDate);
	//根据天数查询 自定义的计费规则
	public List<PricingRuleCustomizedDate> getPricingList(String ruleNo, Integer day);
}
