package cn.com.shopec.core.ml.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.CAccountBalance;
import cn.com.shopec.core.ml.vo.AccountBalanceVo;

/**
 * CAccountBalance 数据库处理类
 */
public interface CAccountBalanceDao extends BaseDao<CAccountBalance,String> {
	//获取会员账户列表
	List<AccountBalanceVo> pageListTwo(Query q);
	Long pageListTwoCount(Query q);
	//获取用户信息
	AccountBalanceVo pageListTwo_No(String id);
	
}
