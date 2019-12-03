package cn.com.shopec.core.car.dao;

import cn.com.shopec.core.common.BaseDao;

import java.util.Date;

import cn.com.shopec.core.car.model.CarTrip;

/**
 * 车辆行程表 数据库处理类
 */
public interface CarTripDao extends BaseDao<CarTrip,String> {
/**
 * 获取订单总里程
 * */
	Double getMileageByOrderTime(String carNo, Date startBillingTime, Date finishTime);
	
}
