package cn.com.shopec.core.marketing.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.PricingRule;

/**
 * 计费规则表 服务接口类
 */
public interface PricingRuleService extends BaseService {

	/**
	 * 根据查询条件，查询并返回PricingRule的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<PricingRule> getPricingRuleList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回PricingRule的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<PricingRule> getPricingRulePagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个PricingRule对象
	 * @param id 主键id
	 * @return
	 */
	public PricingRule getPricingRule(String id);

	/**
	 * 根据主键数组，查询并返回一组PricingRule对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<PricingRule> getPricingRuleByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的PricingRule记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PricingRule> delPricingRule(String id, Operator operator);
	
	/**
	 * 新增一条PricingRule记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param pricingRule 新增的PricingRule数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PricingRule> addPricingRule(PricingRule pricingRule, Operator operator);
	
	/**
	 * 根据主键，更新一条PricingRule记录
	 * @param pricingRule 更新的PricingRule数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<PricingRule> updatePricingRule(PricingRule pricingRule, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为PricingRule对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(PricingRule obj);
/**
 * 获取启用的计费规则 @author zln
 * */
	public PricingRule getPricingRuleUse();
/**
 * 根据车型和会员编号获取对应的计费规则(集团会员)
 * @param string 
 * */
public PricingRule getPricingRuleUseByMM(String carBrandName,String carModelName,String companyId);
/**
 * 普通会员（计费规则）
 * */
public PricingRule getPricingRuleUseByMMP(String carBrandName, String carModelName);
/**
 * 根据车型查询计费规则
 * */
public PricingRule getPricingRuleUseByCar(String carBrandName, String carModelName);
//根据车型查询计费规则 有集团的
public PricingRule getPricingRuleUseByCompanyId(String carBrandName, String carModelName, String companyId);

public PageFinder<PricingRule> getPricingRulePagedLists(Query q);


		
}
