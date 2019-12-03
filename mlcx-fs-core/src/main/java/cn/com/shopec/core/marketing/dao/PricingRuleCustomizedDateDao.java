package cn.com.shopec.core.marketing.dao;

import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.marketing.model.PricingRuleCustomizedDate;

/**
 * PricingRuleCustomizedDate 数据库处理类
 */
public interface PricingRuleCustomizedDateDao extends BaseDao<PricingRuleCustomizedDate,String> {

	int deleteCustomizedDateByRuleNo(String ruleNo);

	PricingRuleCustomizedDate getPricingRuleCustomizedDate(String ruleNo, Date customizedDate);

	List<PricingRuleCustomizedDate> getPricingList(String ruleNo, Integer day);
	
}
