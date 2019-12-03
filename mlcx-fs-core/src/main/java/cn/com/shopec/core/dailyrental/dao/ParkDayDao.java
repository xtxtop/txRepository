package cn.com.shopec.core.dailyrental.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.dailyrental.model.ParkDay;
import cn.com.shopec.core.dailyrental.vo.ParkDayRegionVo;



/**
 * 商家门店 数据库处理类
 */
public interface ParkDayDao extends BaseDao<ParkDay,String> {

	public List<ParkDay> getParkDayListByAround(String cityId);

	public List<ParkDayRegionVo> getParkDayListByCityTake1(Query q);

	public List<ParkDayRegionVo> getParkDayListByCityTake(Query q);

	public Double getDistanceByPoint(String longitude, String latitude, String longitude2, String latitude2);

	public List<ParkDay> getParDaykListBySearch(Query q);
	/**
	 * 后台统计门店行数
	 * @param q
	 * @return
	 */
	public long countForMgt(Query q);
	/**
	 * 根据查询条件，分页查询并返回ParkDay的分页结果(后台)
	 * @param q
	 * @return
	 */
	public List<ParkDay> pageListForMgt(Query q);
}
