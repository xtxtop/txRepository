package cn.com.shopec.mgt.mlparking.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.car.model.CarModel;
import cn.com.shopec.core.car.model.CarPreiod;
import cn.com.shopec.core.car.model.CarSeries;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.mlparking.model.CParkLock;
import cn.com.shopec.core.mlparking.model.CParking;
import cn.com.shopec.core.mlparking.model.CPliesNumber;
import cn.com.shopec.core.mlparking.service.CParkLockService;
import cn.com.shopec.core.mlparking.service.CParkingService;
import cn.com.shopec.core.mlparking.service.CPliesNumberService;
import cn.com.shopec.mgt.common.BaseController;

/**
 *
 * @ClassName: CParkLockController
 * @Description:停车场管理地锁
 * @author: guofei
 * @date: 2018年11月7日 下午1:49:49
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved.
 */
@Controller
@RequestMapping("/cParkLock")
public class CParkLockController extends BaseController {
	@Resource
	private CParkLockService cParkLockService;
	@Resource
	private CParkingService cParkingService;
	@Resource
	private CPliesNumberService pliesNumberService;

	@Override
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	/**
	 *
	 * @Title: mainPage
	 * @Description:进入停车场地锁列表页
	 * @param: @param map
	 * @param: @return
	 * @return: String
	 * @author: guofei
	 * @date: 2018年11月7日 下午2:24:23
	 * @throws
	 */
	@RequestMapping("/toCPackLockList")
	public String mainPage(ModelMap map) {
		// 获取停车场列表
		List<CParking> cParkingList = cParkingService
				.getCParkingList(new Query());
		map.put("cParkingList", cParkingList);
		return "mlpark/cpark_lock_list";
	}

	/**
	 *
	 * @Title: pageListCarSeries
	 * @Description: 获取地锁列表
	 * @param: @param cParkLock
	 * @param: @param query
	 * @param: @return
	 * @return: PageFinder<CarSeries>
	 * @author: guofei
	 * @date: 2018年11月7日 下午1:54:00
	 * @throws
	 */
	@RequestMapping("/pageListcParkLock")
	@ResponseBody
	public PageFinder<CParkLock> pageListCParkLock(
			@ModelAttribute("cParkLock") CParkLock cParkLock, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), cParkLock);
		return cParkLockService.getCParkLockPagedList(q);
	}

	/**
	 *
	 * @Title: toCParkLockAdd
	 * @Description: 进入添加地锁列表
	 * @param: @param map
	 * @param: @return
	 * @return: String
	 * @author: guofei
	 * @date: 2018年11月7日 下午2:07:39
	 * @throws
	 */
	@RequestMapping("/tocParkLockAdd")
	public String toCParkLockAdd(ModelMap map) {
		return "mlpark/cpark_lock_add";
	}

	/**
	 *
	 * @Title: toCParkLockEdit
	 * @Description:进入地锁修改页面
	 * @param: @param parkLockNo
	 * @param: @param map
	 * @param: @return
	 * @return: String
	 * @author: guofei
	 * @date: 2018年11月7日 下午2:12:42
	 * @throws
	 */
	@RequestMapping("/tocParkLockEdit")
	public String toCParkLockEdit(String parkLockNo, String parkingName,
			ModelMap map) {
		CParkLock cParkLock = cParkLockService.getCParkLock(parkLockNo);
		CPliesNumber p = new CPliesNumber();
		p.setParkingNo(cParkLock.getParkingNo());
		p.setSpaceType(cParkLock.getSpaceType());
		List<CPliesNumber> cPliesNumberList = pliesNumberService
				.getCPliesNumberList(new Query(p));
		map.put("cParkLock", cParkLock);
		map.put("parkingName", parkingName);
		map.put("cPliesNumber", cPliesNumberList);
		return "mlpark/cpark_lock_edit";
	}

	/**
	 *
	 * @Title: editCParkLock
	 * @Description: 修改或新增地锁
	 * @param: @param cParkLock
	 * @param: @return
	 * @return: ResultInfo<CParkLock>
	 * @author: guofei
	 * @date: 2018年11月7日 下午2:17:32
	 * @throws
	 */
	@RequestMapping("/editCParkLock")
	@ResponseBody
	public ResultInfo<CParkLock> editCParkLock(
			@ModelAttribute("cParkLock") CParkLock cParkLock) {
		ResultInfo<CParkLock> resultInfo = new ResultInfo<CParkLock>();
		CParkLock p = new CParkLock();
		p.setSpaceNo(cParkLock.getSpaceNo());
		p.setParkingNo(cParkLock.getParkingNo());
		List<CParkLock> cParkLockList = cParkLockService
				.getCParkLockList(new Query(p));
		if (null != cParkLock.getParkLockNo()
				&& cParkLock.getParkLockNo().length() != 0) {
			boolean flag=true;
			if (cParkLockList != null && cParkLockList.size() > 0) {
				for (CParkLock pl : cParkLockList) {
					if (!cParkLock.getParkLockNo().equals(pl.getParkLockNo())
							&& cParkLock.getSpaceNo().equals(pl.getSpaceNo())) {
						resultInfo.setCode("0");
						resultInfo.setMsg("车位号不能重复!");
						flag=false;
						break;
					}
				}
			} 
			if(flag){
				resultInfo = cParkLockService.updateCParkLockBegin(cParkLock,
						getOperator());	
			}
			
		} else {
			if(cParkLockList!=null&&cParkLockList.size()>0){
				for (CParkLock ps : cParkLockList) {
					if (cParkLock.getSpaceNo().equals(ps.getSpaceNo())) {
						resultInfo.setCode("0");
						resultInfo.setMsg("车位号不能重复!");
						break;
					}
				}
			}else{
				resultInfo = cParkLockService.addCParkLockBegin(cParkLock,
						getOperator());
			}
			return resultInfo;
		}
		return resultInfo;
	}

	/**
	 *
	 * @Title: delCParkLock
	 * @Description: 删除对应的地锁
	 * @param: @param parkLockNo
	 * @param: @return
	 * @return: ResultInfo<CParkLock>
	 * @author: guofei
	 * @date: 2018年11月7日 下午4:30:15
	 * @throws
	 */
	@RequestMapping("/delcParkLock")
	@ResponseBody
	public ResultInfo<CParkLock> delcParkLock(String parkLockNo) {
		CParkLock cParkLock = new CParkLock();
		cParkLock.setParkLockNo(parkLockNo);
		return cParkLockService.delCParkLock(parkLockNo, getOperator());
	}

	/**
	 *
	 * @Title: pageListAuditparking
	 * @Description: 添加地锁获取地锁列表
	 * @param: @param parkingName
	 * @param: @param query
	 * @param: @return
	 * @return: PageFinder<CParking>
	 * @author: guofei
	 * @date: 2018年11月8日 上午11:45:26
	 * @throws
	 */
	@RequestMapping("/pageListAuditparking")
	@ResponseBody
	public PageFinder<CParking> pageListAuditparking(CParking parking,
			Query query) {
		return cParkingService.getCParkingPagedList(new Query(
				query.getPageNo(), query.getPageSize(), parking));
	}

	/**
	 * 获取停车场分布信息
	 * 
	 * @param parkingNo
	 * @param spaceType
	 * @return
	 */
	@RequestMapping("/getSpaceType")
	@ResponseBody
	public List<CPliesNumber> getSpaceType(String parkingNo, Integer spaceType) {
		CPliesNumber p = new CPliesNumber();
		p.setParkingNo(parkingNo);
		p.setSpaceType(spaceType);
		List<CPliesNumber> cPliesNumberList = pliesNumberService
				.getCPliesNumberList(new Query(p));
		if (cPliesNumberList != null && cPliesNumberList.size() > 0) {
			return cPliesNumberList;
		}
		return null;
	}
}
