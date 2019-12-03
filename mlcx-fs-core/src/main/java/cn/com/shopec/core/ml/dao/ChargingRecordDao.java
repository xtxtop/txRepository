package cn.com.shopec.core.ml.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.ChargingRecord;
import cn.com.shopec.core.ml.vo.ChargingRecordVo;

import java.util.List;

/**
 * 充电记录 数据库处理类
 */
public interface ChargingRecordDao extends BaseDao<ChargingRecord,String> {
    List<ChargingRecordVo> getChargingRecordVo(Query q);

    Integer getChargingRecordVoCount(Query q);
}
