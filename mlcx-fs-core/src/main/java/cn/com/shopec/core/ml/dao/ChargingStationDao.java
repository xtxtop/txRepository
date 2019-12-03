package cn.com.shopec.core.ml.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.ChargingStation;
import cn.com.shopec.core.ml.vo.ChargingStationDetailsVo;
import cn.com.shopec.core.ml.vo.ChargingStationVo;

/**
 * 充电站 数据库处理类
 */
public interface ChargingStationDao extends BaseDao<ChargingStation, String> {
	// 获取充电站 VO数据
	List<ChargingStationVo> getChargingStationVoList(Query q);

	// 获取充电站总数
	Long getChargingStationVoListCount(Query q);

	// 获取充电站详情
	ChargingStationVo getChargingStationVoList_NO(String id);

	// 获取充电站列表数据（api）
	List<ChargingStationVo> pageChargingStationVoList(Query q);

	List<ChargingStationDetailsVo> getChargingStationDatils(Query q);

	// 获取站类型
	List<ChargingStation> getTypeSort();

	List<ChargingStationVo> searchChargingStation(Query q);

	List<ChargingStationVo> pageChargingStationVoListBycollection(Query q);

}
