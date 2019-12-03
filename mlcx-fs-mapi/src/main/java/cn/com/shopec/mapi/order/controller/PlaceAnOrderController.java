package cn.com.shopec.mapi.order.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.ml.vo.OrderSimpleVo;
import cn.com.shopec.core.order.service.PlaceAnOrderService;

/**
 * 降锁
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/app/placeAnOrder")
public class PlaceAnOrderController {
	@Resource
	private PlaceAnOrderService placeAnOrderService;

	/**
	 * 地锁下单(降锁)
	 */
	@RequestMapping("/createAnOrder")
	@ResponseBody
	public ResultInfo<OrderSimpleVo> createAnOrder(String lockid, String memberNo, String deviceTp, String longitude,
			String latitude) {
		return placeAnOrderService.createAnOrder(lockid, memberNo, deviceTp, longitude, latitude);
	}
}
