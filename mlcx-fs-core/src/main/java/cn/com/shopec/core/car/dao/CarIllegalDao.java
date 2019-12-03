package cn.com.shopec.core.car.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.car.model.CarIllegal;

/**
 * CarIllegal 数据库处理类
 */
public interface CarIllegalDao extends BaseDao<CarIllegal,String> {
/**
 * 获取会员的违章记录数
 * */
	Long getIllegalNumByMemberNo(String memberNo);

    
	
}
