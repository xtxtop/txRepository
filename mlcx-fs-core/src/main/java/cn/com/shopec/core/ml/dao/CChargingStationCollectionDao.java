package cn.com.shopec.core.ml.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.CChargingStationCollection;
import cn.com.shopec.core.ml.vo.ChargingCollectionVo;

import java.util.List;

public interface CChargingStationCollectionDao extends BaseDao<CChargingStationCollection, String> {
	List<ChargingCollectionVo> getCollectionInfo(Query q);

	Integer getCollectionSum(Query q);

	List<ChargingCollectionVo> getAllCollection(Query q);

	void addStationCollection(CChargingStationCollection cCollection);

	Integer isExist(CChargingStationCollection cCollection);
}
