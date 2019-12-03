package cn.com.shopec.core.member.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.member.model.Company;

/**
 * 集团表 数据库处理类
 */
public interface CompanyDao extends BaseDao<Company,String> {

	String getByName(String name);
	
}
