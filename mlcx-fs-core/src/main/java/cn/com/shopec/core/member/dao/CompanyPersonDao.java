package cn.com.shopec.core.member.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.member.model.CompanyPerson;
import cn.com.shopec.core.member.model.Member;

/**
 * 集团人员名单表 数据库处理类
 */
public interface CompanyPersonDao extends BaseDao<CompanyPerson,String> {

	/**
	 * 根据手机号，取的集团会员的常用基础信息
	 * @param memberNo
	 * @return
	 */
	public CompanyPerson getCompanyPerson(String mobilePhone); 
}
