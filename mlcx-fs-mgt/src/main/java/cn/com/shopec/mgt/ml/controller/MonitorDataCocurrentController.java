package cn.com.shopec.mgt.ml.controller;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.BillingScheme;
import cn.com.shopec.core.ml.model.ChargingGunInfo;
import cn.com.shopec.core.ml.model.ChargingPile;
import cn.com.shopec.core.ml.model.ChargingStation;
import cn.com.shopec.core.ml.model.MonitorDataCocurrent;
import cn.com.shopec.core.ml.model.MonitorDataInterflow;
import cn.com.shopec.core.ml.service.BillingSchemeService;
import cn.com.shopec.core.ml.service.ChargingPileService;
import cn.com.shopec.core.ml.service.ChargingStationService;
import cn.com.shopec.core.ml.service.MonitorDataCocurrentService;
import cn.com.shopec.core.ml.service.MonitorDataInterflowService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * @category 充电桩
 */
@Controller
@RequestMapping("cocurrent")
public class MonitorDataCocurrentController extends BaseController {
	@Resource
	private MonitorDataCocurrentService monitorDataCocurrentService;

	
		// 直流列表展示
		@RequestMapping("/pageListMonitorDataCocurrent")
		@ResponseBody
		public PageFinder<MonitorDataCocurrent> pageListMonitoringData(@ModelAttribute("MonitorDataCocurrent") MonitorDataCocurrent monitorDataCocurrent,
				Query query) throws ParseException {
			Query q = new Query(query.getPageNo(), query.getPageSize(), monitorDataCocurrent);
			return monitorDataCocurrentService.getMonitorDataCocurrentPagedList(q);
		}
}
