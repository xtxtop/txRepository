package cn.com.shopec.core.marketing.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.Notice;

/**
 * Notice 数据库处理类
 */
public interface NoticeDao extends BaseDao<Notice,String> {

	public List<Notice> pageLists(Query q);
	
}
