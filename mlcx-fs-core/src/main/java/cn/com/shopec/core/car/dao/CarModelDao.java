package cn.com.shopec.core.car.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.car.model.CarModel;

/**
 * 车辆车型 数据库处理类
 */
public interface CarModelDao extends BaseDao<CarModel,String> {
	
	public List<CarModel> queryAllForMapi(Query q);
	
}
