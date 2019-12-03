package cn.com.shopec.core.ml.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.CJoin;
import cn.com.shopec.core.ml.vo.JoinVo;

/**
 * CJoin 数据库处理类
 */
public interface CJoinDao extends BaseDao<CJoin,String> {
	//获取加盟列表
	List<JoinVo> getJoin(Query q);
	//列表数量
	long getJoinCount(Query q);
	//详情
	JoinVo getJoinNo(String id);
}
