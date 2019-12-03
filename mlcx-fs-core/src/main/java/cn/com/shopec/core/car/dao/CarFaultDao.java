package cn.com.shopec.core.car.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;

import java.util.List;

import cn.com.shopec.core.car.model.CarFault;

/**
 * CarFault 数据库处理类
 */
public interface CarFaultDao extends BaseDao<CarFault,String> {
	/**
	 * @author lj
	 * 后台
	 * 根据条件查询数据
	 * @param q
	 * @return
	 */
	public List<CarFault> getCarMalfunctionList(Query q);
	/**
	 * @author lj
	 * 后台
	 * 根据条件查询数据条件
	 * @param q
	 * @return
	 */
	public Long getCarMalfunctionCount(Query q);
}
