package cn.com.shopec.core.car.dao;

import cn.com.shopec.core.car.model.CarRecord;
import cn.com.shopec.core.car.vo.CarRecordWkVo;
import cn.com.shopec.core.common.BaseDao;

/**
 * 用车记录表 数据库处理类
 */
public interface CarRecordDao extends BaseDao<CarRecord, String> {
	/**
	 * 根据单据号和用车类型查询用车记录
	 */
	CarRecord getCarRecordByDocumentNo(String orderNo, Integer i);

	/**
	 * 调度端查看用车最近一次的记录
	 */
	public CarRecordWkVo carUsageLog(String carPlateNo);

}
