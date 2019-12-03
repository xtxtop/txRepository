package cn.com.shopec.core.dailyrental.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.PricingRuleCustomDate;

/**
 * 自定义日期价格表 数据库处理类
 */
public interface PricingRuleCustomDateDao extends BaseDao<PricingRuleCustomDate,String> {
	/**
	 * 
	 * @param id
	 * @return
	 */
	public int deleteByRuleId(String ruleId);
	/**
	 * 按条件统计自定义计费日租行数（后台）
	 * @param q
	 * @return
	 */
	public int countForMgt(Query q);
	/**
	 *  根据查询条件，分页查询并返回PricingRuleCustomDate的分页结果（后台）
	 * @param q
	 * @return
	 */
	public List<PricingRuleCustomDate> pageListForMgt(Query q);
}
