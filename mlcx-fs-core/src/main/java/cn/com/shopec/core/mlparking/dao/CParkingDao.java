package cn.com.shopec.core.mlparking.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.mlparking.model.CParking;
import cn.com.shopec.core.mlparking.vo.CParkingDetailVo;
import cn.com.shopec.core.mlparking.vo.CParkingVo;
import cn.com.shopec.core.mlparking.vo.ParkingVo;

/**
 * 停车场 数据库处理类
 */
public interface CParkingDao extends BaseDao<CParking, String> {
	// 停车场列表vo
	List<ParkingVo> getParkList(Query q);

	// 停车场列表总数
	Long getParkListCount(Query q);

	// 停车场详情
	ParkingVo getParkListNo(String id);

	/**
	 * app端停车场列表
	 * 
	 * @param q
	 * @return
	 */
	public List<CParkingVo> pageCparkingVoList(Query q);

	/**
	 * app端停车场详情
	 * 
	 * @param q
	 * @return
	 */
	public CParkingDetailVo getParkingDetailVo(Query q);
	//更新车位 降锁
	int upParkSpace(@Param("spaceType") Integer spaceType, @Param("parkingNo") String parkingNo);
	//更新车位 升锁
	int upParkSpaceTwo(@Param("spaceType") Integer spaceType, @Param("parkingNo") String parkingNo);


}
