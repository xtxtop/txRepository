package cn.com.shopec.core.marketing.dao;

import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.PricingRule;

/**
 * 计费规则表 数据库处理类
 */
public interface PricingRuleDao extends BaseDao<PricingRule,String> {
/**
 * 获取可用的计费规则   @author zln
 * */
	PricingRule getPricingRuleUse();
/**
 * 获取集团用户+车型查出的计费规则
 * @param nowTime 
 * */
PricingRule getPricingRuleUseByMM(String string, String companyId, Date nowTime);
/**
 * 获取普通用户的计费规则
 * @param nowTime 
 * */
PricingRule getPricingRuleUseByMMP(String string, Date nowTime);
/**
 * 根据车型查询计费规则
 * */
PricingRule getPricingRuleUseByCar(String string);
//根据车型查询计费规则
PricingRule getPricingRuleUseByCompanyId(String string, String companyId);
PricingRule getPricingRuleUseByCarIsb(String string);
List<PricingRule> pageLists(Query q);
	long countPagedLists(Query q);
}
