package cn.com.shopec.core.order.service;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.ml.vo.OrderSimpleVo;

/**
 * 充电下单 服务接口类
 */
public interface PlaceAnOrderService extends BaseService {
	/**
	 * 充电下单接口
	 */
	public ResultInfo<OrderSimpleVo> createAnOrder(String lockid, String memberNo, String deviceTp, String longitude,
			String latitude);
}
