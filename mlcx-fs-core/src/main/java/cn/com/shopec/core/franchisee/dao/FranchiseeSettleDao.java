package cn.com.shopec.core.franchisee.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.franchisee.model.FranchiseeSettle;

/**
 * 加盟商结算表 数据库处理类
 */
public interface FranchiseeSettleDao extends BaseDao<FranchiseeSettle,String> {

	public FranchiseeSettle getFranchiseeNo(String franchiseeNo);
	
}
