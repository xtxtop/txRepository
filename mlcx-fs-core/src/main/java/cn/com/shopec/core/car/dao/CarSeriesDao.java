package cn.com.shopec.core.car.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.car.model.CarSeries;

/**
 * 车系表 数据库处理类
 */
public interface CarSeriesDao extends BaseDao<CarSeries,String> {
	
	public List<CarSeries> pageListForMgt(Query q);
	
	public Long countForMgt(Query q);

	public List<CarSeries> getSeaTing();
}
