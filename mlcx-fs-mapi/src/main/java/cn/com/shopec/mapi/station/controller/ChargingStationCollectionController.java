package cn.com.shopec.mapi.station.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.ml.service.CChargingStationCollectionService;
import cn.com.shopec.mapi.common.controller.BaseController;

/**
 * app收藏接口
 */
@Controller
@RequestMapping("/app/collection")
public class ChargingStationCollectionController extends BaseController {
	@Resource
	private CChargingStationCollectionService cChargingStationCollectionService;

	/**
	 * 收藏
	 * 
	 * @parma memberNo 会员编号
	 * @param stationNo
	 *            充电站编号
	 */
	@RequestMapping("/collection")
	@ResponseBody
	public ResultInfo collection(String memberNo, String stationNo) {
		return cChargingStationCollectionService.toggleCollectionStatus(memberNo, stationNo);
	}
}
