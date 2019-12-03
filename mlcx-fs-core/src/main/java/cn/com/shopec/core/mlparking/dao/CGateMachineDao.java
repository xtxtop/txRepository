package cn.com.shopec.core.mlparking.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.mlparking.model.CGateMachine;
import cn.com.shopec.core.mlparking.vo.CGateMachineVo;

/**
 * CGateMachine 数据库处理类
 */
public interface CGateMachineDao extends BaseDao<CGateMachine,String> {
	List<CGateMachineVo> pageList_Two(Query query);
	
}
