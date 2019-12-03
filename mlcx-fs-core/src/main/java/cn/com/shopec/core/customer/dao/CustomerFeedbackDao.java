package cn.com.shopec.core.customer.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.customer.model.CustomerFeedback;

/**
 * 客户反馈表 数据库处理类
 */
public interface CustomerFeedbackDao extends BaseDao<CustomerFeedback,String> {

	List<CustomerFeedback> pageListCus(Query q);
	
}
