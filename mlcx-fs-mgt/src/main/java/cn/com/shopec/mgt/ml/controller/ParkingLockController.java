package cn.com.shopec.mgt.ml.controller;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.ChargingPile;
import cn.com.shopec.core.ml.model.ChargingStation;
import cn.com.shopec.core.ml.model.LockBillingScheme;
import cn.com.shopec.core.ml.model.ParkingLock;
import cn.com.shopec.core.ml.service.ChargingPileService;
import cn.com.shopec.core.ml.service.ChargingStationService;
import cn.com.shopec.core.ml.service.LockBillingSchemeService;
import cn.com.shopec.core.ml.service.ParkingLockService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * @category 地锁
 */
@Controller
@RequestMapping("parkingLock")
public class ParkingLockController extends BaseController {

	@Resource
	private ParkingLockService parkingLockService;
	@Resource
	private ChargingStationService chargingStationService;
	@Resource
	private LockBillingSchemeService lockBillingSchemeService;
	@Resource
	private ChargingPileService chargingPileService;

	@RequestMapping("/getParkingLockList")
	public String getChargingStation(ModelMap model) {
		List<ChargingStation> csList = chargingStationService.getChargingStationList(new Query());
		model.put("csList", csList);
		return "ml/parkingLock_list";
	}

	// 列表展示
	@RequestMapping("/pageListparkingLock")
	@ResponseBody
	public PageFinder<ParkingLock> pageListparkingLock(@ModelAttribute("ParkingLock") ParkingLock parkingLock,
			Query query) throws ParseException {
		Query q = new Query(query.getPageNo(), query.getPageSize(), parkingLock);
		return parkingLockService.getParkingLockPagedList(q);
	}

	// 新增页面
	@RequestMapping("/toAddparkingLock")
	public String toAddparkingLock(ModelMap model) {
		ChargingStation cs = new ChargingStation();
		cs.setIsDeleted(0);
		cs.setIsAvailable(1);
		List<ChargingStation> csList = chargingStationService.getChargingStationList(new Query(cs));
		model.put("csList", csList);
		LockBillingScheme lbs = new LockBillingScheme();
		lbs.setStatus(1);
		List<LockBillingScheme> bsList = lockBillingSchemeService.getLockBillingSchemeList(new Query(lbs));
		model.put("bsList", bsList);
		return "ml/parkingLock_add";
	}

	// 新增地锁操作
	@RequestMapping("/doaddparkingLock")
	@ResponseBody
	public ResultInfo<ParkingLock> doaddparkingLock(@ModelAttribute("ParkingLock") ParkingLock parkingLock) {
		ResultInfo<ParkingLock> result = new ResultInfo<ParkingLock>();
		ParkingLock cp = new ParkingLock();
		cp.setParkingLockSerialNumber(parkingLock.getParkingLockSerialNumber());
		List<ParkingLock> cpList = parkingLockService.getParkingLockList(new Query(cp));
		if (cpList.size() > 0) {
			result.setCode(Constant.FAIL);
			result.setMsg("地锁序列号不能重复");
			return result;
		}
		parkingLock.setChargingPileName(
				chargingPileService.getChargingPile(parkingLock.getChargingPileNo()).getChargingPileName());
		parkingLock
				.setStationName(chargingStationService.getChargingStation(parkingLock.getStationNo()).getStationName());
		return parkingLockService.addParkingLock(parkingLock, getOperator());
	}

	// 编辑页面
	@RequestMapping("/toEditparkingLock")
	public String toEditparkingLock(ModelMap model, String parkingLockNo) {
		ParkingLock cp = parkingLockService.getParkingLock(parkingLockNo);
		model.put("cp", cp);
		ChargingStation cs = new ChargingStation();
		cs.setIsDeleted(0);
		cs.setIsAvailable(1);
		List<ChargingStation> csList = chargingStationService.getChargingStationList(new Query(cs));
		model.put("csList", csList);
		LockBillingScheme lbs = new LockBillingScheme();
		lbs.setStatus(1);
		List<LockBillingScheme> bsList = lockBillingSchemeService.getLockBillingSchemeList(new Query(lbs));
		model.put("bsList", bsList);
		return "ml/parkingLock_edit";
	}

	// 编辑地锁操作
	@RequestMapping("/doeditparkingLock")
	@ResponseBody
	public ResultInfo<ParkingLock> doeditparkingLock(@ModelAttribute("ParkingLock") ParkingLock parkingLock) {
		ResultInfo<ParkingLock> result = new ResultInfo<ParkingLock>();
		ParkingLock cp = new ParkingLock();
		cp.setParkingLockSerialNumber(parkingLock.getParkingLockSerialNumber());
		List<ParkingLock> cpList = parkingLockService.getParkingLockList(new Query(cp));
		if (cpList.size() > 1) {
			result.setCode(Constant.FAIL);
			result.setMsg("地锁序列号不能重复");
			return result;
		}
		ChargingPile chp = chargingPileService.getChargingPile(parkingLock.getChargingPileNo());
		parkingLock.setChargingPileName(chp.getChargingPileName());
		ChargingStation cs = chargingStationService.getChargingStation(parkingLock.getStationNo());
		parkingLock.setStationName(cs.getStationName());
		return parkingLockService.updateParkingLock(parkingLock, getOperator());
	}

	/*
	 * 全部商品类型（页面树形展示）
	 */
	@RequestMapping("chargingPileTree")
	@ResponseBody
	public List<ChargingPile> chargingPileTree(String stationNo) {
		ChargingPile cp = new ChargingPile();
		cp.setStationNo(stationNo);
		cp.setStatus(1);
		return chargingPileService.getChargingPileList(new Query(cp));
	}
}
