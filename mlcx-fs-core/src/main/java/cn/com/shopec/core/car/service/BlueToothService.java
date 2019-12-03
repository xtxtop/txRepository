package cn.com.shopec.core.car.service;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.car.model.CarStatusBlue;
import cn.com.shopec.core.car.model.CarStatusVo;
import cn.com.shopec.core.common.BaseService;

/**
 * Car 服务接口类
 */
public interface BlueToothService extends BaseService {

	ResultInfo<CarStatusVo> blueToothControl(CarStatusBlue carStatusBlue);

}
