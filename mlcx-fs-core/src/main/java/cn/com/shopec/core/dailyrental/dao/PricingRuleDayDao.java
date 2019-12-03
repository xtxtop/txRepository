package cn.com.shopec.core.dailyrental.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.dailyrental.model.PricingRuleDay;

/**
 * 计费规则表 数据库处理类
 */
public interface PricingRuleDayDao extends BaseDao<PricingRuleDay,String> {

	public PricingRuleDay getPricingRuleDayCarModelIdAndCityId(String carModelId,String cityId);
	
}
