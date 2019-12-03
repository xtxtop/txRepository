package cn.com.shopec.core.resource.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.resource.model.ParkStatus;

/**
 * 场站状态表 数据库处理类
 */
public interface ParkStatusDao extends BaseDao<ParkStatus,String> {
	/**
	 * 后台根据条件查询数据
	 * @param q
	 * @return
	 */
	public List<ParkStatus> getParkStatusPageList(Query q);
	/**
	 * 后台根据条件查询数据条件
	 * @param q
	 * @return
	 */
	public Long countParkStatus(Query q);
}
