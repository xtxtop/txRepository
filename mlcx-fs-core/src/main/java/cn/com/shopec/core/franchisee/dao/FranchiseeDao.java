package cn.com.shopec.core.franchisee.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.franchisee.model.Franchisee;

/**
 * 加盟商表 数据库处理类
 */
public interface FranchiseeDao extends BaseDao<Franchisee, String> {

	public List<Franchisee> pageAuditList(Query q);
	
	public long countAudit(Query q);
	
	public List<Franchisee> auditFranchiseeList();
}
