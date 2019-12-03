package cn.com.shopec.core.resource.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.ParkingSpace;

/**
 * 车位表 数据库处理类
 */
public interface ParkingSpaceDao extends BaseDao<ParkingSpace,String> {
	/**
	 * 根据条件查询数据
	 * @param q
	 * @return
	 */
	public List<ParkingSpace> pageParkingList(Query q);
	/**
	 * 根据条件查询数据条件
	 * @param q
	 * @return
	 */
	public Long parkingCount(Query q);
}
