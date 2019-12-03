package cn.com.shopec.mgt.franchisee.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.franchisee.model.Franchisee;
import cn.com.shopec.core.franchisee.model.FranchiseeProfit;
import cn.com.shopec.core.franchisee.model.FranchiseeProfitVo;
import cn.com.shopec.core.franchisee.model.FranchiseeSettle;
import cn.com.shopec.core.franchisee.service.FranchiseeProfitService;
import cn.com.shopec.core.franchisee.service.FranchiseeService;
import cn.com.shopec.core.franchisee.service.FranchiseeSettleService;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 加盟商管理
 * 
 */
@Controller
@RequestMapping("/franchisee")
public class FranchiseeController extends BaseController {
	private static final Log log = LogFactory.getLog(FranchiseeController.class);
	
	@Resource
	private FranchiseeService franchiseeService;
	@Resource
	private FranchiseeProfitService franchiseeProfitService;
	@Resource
	private FranchiseeSettleService franchiseeSettleService;
	@Resource
	private OrderService orderService;
	@Resource
	private ParkService  parkService;

	/**
	 * 进入加盟商列表页面
	 * 
	 */
	@RequestMapping("toFranchiseeList")
	public String tofranchiseeList() {

		return "/franchisee/franchisee_list";
	}

	/**
	 * 查询加盟商列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageListFranchisee")
	@ResponseBody
	public PageFinder<Franchisee> pageListfranchisee(@ModelAttribute("franchisee") Franchisee franchisee,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) throws ParseException {
		Query q = new Query(pageNo, pageSize, franchisee);
		return franchiseeService.getFranchiseePagedList(q);
	}

	/**
	 * 查询已经审核的加盟商数据
	 */
	@RequestMapping("pageListAuditfranchisee")
	@ResponseBody
	public PageFinder<Franchisee> pageListAuditfranchisee(@ModelAttribute("franchisee") Franchisee franchisee,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) throws ParseException {
		Query q = new Query(pageNo, pageSize, franchisee);
		return franchiseeService.getAuditFranchiseePagedList(q);
	}
	
	/**
	 * 进入加盟商新增页面
	 * 
	 */
	@RequestMapping("toFranchiseeAdd")
	public String toFranchiseeAdd() {

		return "/franchisee/franchisee_add";
	}

	/**
	 * 进入加盟商编辑页面
	 * 
	 */
	@RequestMapping("toFranchiseeEdit")
	public String toFranchiseeEdit(String franchiseeNo, ModelMap map) {
		Franchisee franchisee = franchiseeService.getFranchisee(franchiseeNo);
		map.put("franchisee", franchisee);

		return "/franchisee/franchisee_edit";
	}

	/**
	 * 进入加盟商详情页面
	 * 
	 */
	@RequestMapping("toFranchiseeView")
	public String toFranchiseeView(String franchiseeNo, ModelMap map) {
		Franchisee franchisee = franchiseeService.getFranchisee(franchiseeNo);
		map.put("franchisee", franchisee);

		return "/franchisee/franchisee_view";
	}

	/**
	 * 进入加盟商审核页面
	 * 
	 */
	@RequestMapping("toCencorfranchisee")
	public String toCencorfranchisee(String franchiseeNo, ModelMap map) {
		Franchisee franchisee = franchiseeService.getFranchisee(franchiseeNo);
		map.put("franchisee", franchisee);

		return "/franchisee/franchisee_cencor";
	}

	/*
	 * 新增或修改加盟商
	 */
	@RequestMapping("/editFranchisee")
	@ResponseBody
	public ResultInfo<Franchisee> editFranchisee(@ModelAttribute("franchisee") Franchisee franchisee) {

		if (null == franchisee.getFranchiseeNo()) {
			return franchiseeService.addFranchisee(franchisee, getOperator());
		} else {
			return franchiseeService.updateFranchisee(franchisee, getOperator());
		}

	}

	/**
	 * 删除加盟商
	 * 
	 */
	@RequestMapping("delFranchisee")
	@ResponseBody
	public ResultInfo<Franchisee> delFranchisee(String franchiseeNo) {

		return franchiseeService.delFranchisee(franchiseeNo, getOperator());
	}

	/**
	 * 加盟商结算单列表
	 * 
	 */
	@RequestMapping("toFranchiseeSettlementList")
	public String toFranchiseeSettlementList(ModelMap model) {
		Franchisee f = new Franchisee();
		// 已审核
		f.setCensorStatus(1);
		model.put("fs", franchiseeService.getFranchiseeList(new Query(f)));

		return "/franchisee/franchisee_settlement_list";
	}

	/**
	 * 查询加盟商结算列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageListFranchiseeSettlement")
	@ResponseBody
	public PageFinder<FranchiseeSettle> pageListFranchiseeSettle(
			@ModelAttribute("franchiseeSettle") FranchiseeSettle franchiseeSettle, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) throws ParseException {
		Query q = new Query(pageNo, pageSize, franchiseeSettle);
		return franchiseeSettleService.getFranchiseeSettlePagedList(q);
	}

	/**
	 * 加盟商结算单详情
	 * 
	 */
	@RequestMapping("toFranchiseeSettleView")
	public String toFranchiseeSettleView(String franchiseeSettleNo, ModelMap model) {
		//查询结算单
		FranchiseeSettle franchiseeSettle = this.franchiseeSettleService.getFranchiseeSettle(franchiseeSettleNo);
		if(franchiseeSettle != null){
			Double carShareAmount = 0d;
			Double carShareOrderAmount = 0d;
//			//根据加盟商编号、分润类型统计车辆分润数据
//			FranchiseeProfit profitCar = new FranchiseeProfit();
//			profitCar.setFranchiseeNo(franchiseeSettle.getFranchiseeNo());
//			profitCar.setProfitType(Constant.PROFIT_CAR_TYPE);
//			List<FranchiseeProfit> listFranchiseeProfitCar = franchiseeProfitService.listFranchiseeProfitIseeNo(new Query(profitCar));
//			if(listFranchiseeProfitCar != null && listFranchiseeProfitCar.size()>0){
//				for (FranchiseeProfit franchiseeProfit : listFranchiseeProfitCar) {
//					FranchiseeSettle settleCarBean = new FranchiseeSettle();
//					settleCarBean.setFranchiseeSettleNo(franchiseeSettle.getFranchiseeSettleNo());
//					settleCarBean.setCarshareOrderCount(Double.parseDouble(String.valueOf(listFranchiseeProfitCar.size())));
//					carShareAmount += franchiseeProfit.getCarProfit();
//					settleCarBean.setCarShareAmount(carShareAmount);
//					if(franchiseeProfit.getOrderAmount() != null){
//						carShareOrderAmount += franchiseeProfit.getOrderAmount();
//						settleCarBean.setCarshareOrderAmount(carShareOrderAmount);
//					}else{
//						settleCarBean.setCarshareOrderAmount(0d);
//					}
//					model.put("settleCarBean", settleCarBean);
//				}
//			}
			//根据加盟商编号、分润类型统计场站分润数据
//			Double parkShareAmount = 0d;
//			Double parkShareOrderAmount = 0d;
//			FranchiseeProfit profitPark = new FranchiseeProfit();
//			profitPark.setFranchiseeNo(franchiseeSettle.getFranchiseeNo());
//			profitPark.setProfitType(Constant.PROFIT_PARK_TYPE);
//			List<FranchiseeProfit> listFranchiseeProfitPark = franchiseeProfitService.listFranchiseeProfitIseeNo(new Query(profitPark));
//			if(listFranchiseeProfitPark != null && listFranchiseeProfitPark.size()>0){
//				for (FranchiseeProfit franchiseeProfit : listFranchiseeProfitPark) {
//					FranchiseeSettle settleParkBean = new FranchiseeSettle();
//					settleParkBean.setFranchiseeSettleNo(franchiseeSettle.getFranchiseeSettleNo());
//					settleParkBean.setParkshareOrderCount(Double.parseDouble(String.valueOf(listFranchiseeProfitPark.size())));
//					parkShareAmount += franchiseeProfit.getParkProfit();
//					settleParkBean.setParkShareAmount(parkShareAmount);
//					if(franchiseeProfit.getOrderAmount()!=null){
//						parkShareOrderAmount += franchiseeProfit.getOrderAmount();
//						settleParkBean.setParkshareOrderAmount(parkShareOrderAmount);
//					}else{
//						settleParkBean.setParkshareOrderAmount(0d);
//					}
//					model.put("settleParkBean", settleParkBean);
//				}
//			}
			model.put("franchiseeSettle", franchiseeSettle);
		}
		return "/franchisee/franchisee_settlement_view";
	}
	
	/**
	 * 加盟商收益明细列表
	 */
	@RequestMapping("toFranchiseeProfitList")
	public String toFranchiseeProfitList(ModelMap model) {
		return "/franchisee/franchisee_profit_list";
	}
	
	@RequestMapping("pageListFranchiseeProfit")
	@ResponseBody
	public PageFinder<FranchiseeProfit> pageListFranchiseeProfit(
			@ModelAttribute("franchiseeProfit") FranchiseeProfit franchiseeProfit, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) throws ParseException {
		Query q = new Query(pageNo, pageSize, franchiseeProfit);
		return franchiseeProfitService.getFranchiseeProfitPagedList(q);
	}
	
	/**
	 * 根据加盟商编号和订单编号查询本期收益明细
	 */
	@RequestMapping("pageListEarningsRecord")
	@ResponseBody
	public PageFinder<FranchiseeProfit> pageListEarningsRecord(
			@ModelAttribute("franchiseeProfit") FranchiseeProfit franchiseeProfit, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize,@RequestParam("settleDay") String settleDay) throws ParseException {
		if(settleDay != null && !"".equals(settleDay)){
			String[] strs=settleDay.split("至");
			for (int i = 0; i < strs.length; i++) {
				String start=strs[0].toString();
				String end=strs[1].toString();
				franchiseeProfit.setCreateTimeStart(ECDateUtils.parseTime(start+" 00:00:00"));
				franchiseeProfit.setCreateTimeEnd(ECDateUtils.parseTime(end+" 23:59:59"));
			}
		}
		Query q = new Query(pageNo, pageSize, franchiseeProfit);
		return franchiseeProfitService.getFranchiseeProfitPagedList(q);
	}
	
	/**
	 * 按月度统计加盟商收益
	 */
	@RequestMapping("toFranchiseeProfitListByMonths")
	public String toFranchiseeProfitListByMonths(ModelMap model) {
		return "/franchisee/franchisee_profit_list_months";
	}
	
	@RequestMapping("pageListFranchiseeProfitByMonths")
	@ResponseBody
	public PageFinder<FranchiseeProfitVo> pageListFranchiseeProfitByMonths(@ModelAttribute("franchiseeProfit") FranchiseeProfit franchiseeProfit, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize)throws ParseException{
		Query q = new Query(pageNo, pageSize, franchiseeProfit);
		return franchiseeProfitService.getFranchiseeProfitPagedListByMonths(q);
	}
	
	/**
	 * 查询月度统计加盟商收益详情
	 */
	@RequestMapping("toFranchiseeProfitByMonthsView")
	public String toFranchiseeProfitByMonthsView(String months,ModelMap model,String franchiseeNo){
		//根据列表月份查询详细数据
		FranchiseeProfitVo franchiseeProfitVo = franchiseeProfitService.getFranchiseeProfitVoByMonths(months,franchiseeNo);
		if(franchiseeProfitVo != null){
			String yearMonth = franchiseeProfitVo.getMonths().substring(0, 4)+"年"+franchiseeProfitVo.getMonths().substring(4, 6)+"月";
			franchiseeProfitVo.setYear(yearMonth);
			model.put("franchiseeProfitVo", franchiseeProfitVo);
		}
		return "/franchisee/franchisee_profit_view_months";
	}
	
	/**
	 * 按季度统计加盟商收益
	 */
	@RequestMapping("toFranchiseeProfitListByQuarter")
	public String toFranchiseeProfitListByQuarter(ModelMap model) {
		return "/franchisee/franchisee_profit_list_quarter";
	}
	
	@RequestMapping("pageListFranchiseeProfitByQuarter")
	@ResponseBody
	public PageFinder<FranchiseeProfitVo> pageListFranchiseeProfitByQuarter(@ModelAttribute("franchiseeProfit") FranchiseeProfit franchiseeProfit, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize)throws ParseException{
		Query q = new Query(pageNo, pageSize, franchiseeProfit);
		return franchiseeProfitService.getFranchiseeProfitPagedListByQuarter(q);
	}
	
	/**
	 * 查询按季度统计加盟商收益详情
	 */
	@RequestMapping("toFranchiseeProfitByQuarterView")
	public String toFranchiseeProfitByQuarterView(String quarter,ModelMap model,String franchiseeNo,String year){
		FranchiseeProfitVo franchiseeProfitVo = franchiseeProfitService.getFranchiseeProfitVoByQuarter(quarter,year,franchiseeNo);
		if(franchiseeProfitVo != null){
			if(StringUtils.isNotBlank(franchiseeProfitVo.getQuarter())){
				franchiseeProfitVo.setQuarter(franchiseeProfitVo.getYear()+"年"+quarter+"季度");
			}
			model.put("franchiseeProfitVo", franchiseeProfitVo);
		}
		return "/franchisee/franchisee_profit_view_quarter";
	}
	
	/**
	 * 加盟商收益趋势统计
	 */
	@RequestMapping("toFranchiseeEarningsTrend")
	public String toFranchiseeEarningsTrend(ModelMap model){
		return "/franchisee/franchisee_profit_earnings_trend";
	}
	
	
	/**
	 * 按月统计加盟商收益
	 * Echarts图表展示 bar柱状图展示
	 */
	@RequestMapping("franchProfitMonthStatistics")
	@ResponseBody
	public ResultInfo<Map<String,Object>> franchProfitMonthStatistics(String franchiseeName){
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<Map<String, Object>>();
		String dateArray[] = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		Double orderAmountArray[] = new Double[]{0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00};
		Double profitAmountArray[] = new Double[]{0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00};
		Double carProfitArray[] = new Double[]{0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00};
		Double parkProfitArray[] = new Double[]{0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00,0.00};
		for(int i = 0; i < dateArray.length; i++){
			FranchiseeProfit franchiseeProfit = new FranchiseeProfit();
			franchiseeProfit.setFranchiseeName(franchiseeName);
			int currentYear = ECDateUtils.getCurrentYear();//当前年
			String nowTime = String.valueOf(currentYear)+dateArray[i];
			log.info("查询当前时间参数为："+nowTime);
			franchiseeProfit.setQueryTime(nowTime);
			log.info("设置日期"+franchiseeProfit.getQueryTime());
			PageFinder<FranchiseeProfitVo> page = franchiseeProfitService.getFranchiseeProfitPagedListByMonths(new Query(franchiseeProfit));
			if(page.getData() != null && page.getData().size()>0){
				List<FranchiseeProfitVo> listFranchiseeProfit = page.getData();
				
				for (FranchiseeProfitVo franchiseeProfitVo : listFranchiseeProfit) {
					String month = franchiseeProfitVo.getMonths().substring(4, 6);
					if(dateArray[i].equals(month)){
						orderAmountArray[i] = franchiseeProfitVo.getOrderAmount();
						profitAmountArray[i] = franchiseeProfitVo.getProfitAmount();
						carProfitArray[i] = franchiseeProfitVo.getCarProfit();
						parkProfitArray[i] = franchiseeProfitVo.getParkProfit();
					}
					
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dateArray", dateArray);
		map.put("orderAmountArray", orderAmountArray);
		map.put("profitAmountArray", profitAmountArray);
		map.put("carProfitArray", carProfitArray);	
		map.put("parkProfitArray", parkProfitArray);
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}
	
	/**
	 * 按季度统计加盟商收益
	 * Echarts bar柱状图展示
	 */
	@RequestMapping("franchProfitQuarterStatistics")
	@ResponseBody
	public ResultInfo<Map<String,Object>> franchProfitQuarterStatistics(String franchiseeName){
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<Map<String, Object>>();
		String dateArray[] = {"1季度","2季度","3季度","4季度"};
		Double orderAmountArray[] = new Double[]{0.00,0.00,0.00,0.00};
		Double profitAmountArray[] = new Double[]{0.00,0.00,0.00,0.00};
		Double carProfitArray[] = new Double[]{0.00,0.00,0.00,0.00};
		Double parkProfitArray[] = new Double[]{0.00,0.00,0.00,0.00};
		for(int i = 0; i < dateArray.length; i++){
			FranchiseeProfit franchiseeProfit = new FranchiseeProfit();
			franchiseeProfit.setFranchiseeName(franchiseeName);
			PageFinder<FranchiseeProfitVo> page = franchiseeProfitService.getFranchiseeProfitPagedListByQuarter(new Query(franchiseeProfit));
			if(page.getData() != null && page.getData().size()>0){
				List<FranchiseeProfitVo> listFranchiseeProfit = page.getData();
				
				for (FranchiseeProfitVo franchiseeProfitVo : listFranchiseeProfit) {
					String dataQuarter = dateArray[i].substring(0, 1);
					String quarter = franchiseeProfitVo.getQuarter().substring(0, 1);
					if(dataQuarter.equals(quarter)){
						orderAmountArray[i] = franchiseeProfitVo.getOrderAmount();
						profitAmountArray[i] = franchiseeProfitVo.getProfitAmount();
						carProfitArray[i] = franchiseeProfitVo.getCarProfit();
						parkProfitArray[i] = franchiseeProfitVo.getParkProfit();
					}
					
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dateArray", dateArray);
		map.put("orderAmountArray", orderAmountArray);
		map.put("profitAmountArray", profitAmountArray);
		map.put("carProfitArray", carProfitArray);	
		map.put("parkProfitArray", parkProfitArray);
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}
	
	/**
	 * 查询加盟商车辆运营统计
	 */
	@RequestMapping("toFranchiseeCarStatisticsList")
	public String toFranchiseeCarStatisticsList(ModelMap model) {
		return "/franchisee/franchisee_car_statistics_list";
	}
	
	@RequestMapping("pageListFranchiseeCarStatistics")
	@ResponseBody
	public PageFinder<FranchiseeProfitVo> pageListFranchiseeCarStatistics(@ModelAttribute("franchiseeProfit") FranchiseeProfit franchiseeProfit, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize)throws ParseException{
		franchiseeProfit.setProfitType(0);
		Query q = new Query(pageNo, pageSize, franchiseeProfit);
		return franchiseeProfitService.getFranchiseeCarStatisticsPagedList(q);
	}
	
	/**
	 * 查询加盟商车辆/场站运营统计相关订单
	 */
	@RequestMapping("pageListFranchiseeCarOrParkOrders")
	@ResponseBody
	public PageFinder<Order> pageListFranchiseeCarOrParkOrders(@ModelAttribute("franchiseeProfit") FranchiseeProfit franchiseeProfit,@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize,String carOrParkNo)throws ParseException{
		PageFinder<Order> pageList = new PageFinder<Order>();
		List<Order> orders=new ArrayList<Order>();
		//先查询该加盟商提供车辆是否存在收益明细
		List<FranchiseeProfit> listFranchiseeProfit = franchiseeProfitService.listFranchiseeProfitIseeNo(new Query(franchiseeProfit));
		if(listFranchiseeProfit != null && listFranchiseeProfit.size()>0){
			for (FranchiseeProfit franchisee : listFranchiseeProfit) {
				if(StringUtils.isNotBlank(franchisee.getOrderNo())){
					//查询相关订单
//					Query q = new Query(pageNo, pageSize, order);
//					pageList = orderService.getOrderPagedList(q);
					Order order = orderService.getOrder(franchisee.getOrderNo());
					if(order.getActualTakeLoacton()!= null && !"".equals(order.getActualTakeLoacton())){
						order.setStartAddress(order.getActualTakeLoacton());
					}else{
						order.setStartAddress(order.getStartAddress());
					}
					
					if(order.getActualTerminalParkNo() != null &&  !"".equals(order.getActualTerminalParkNo())){
						Park p=parkService.getPark(order.getActualTerminalParkNo());
						if(p != null){
							order.setTerminalAddress(p.getParkName());
						}
					}else{
						order.setTerminalAddress(order.getTerminalAddress());
					}
					orders.add(order);
				}
			}
		}
		pageList.setRowCount(orders.size());
		pageList.setData(orders);
		return pageList;
	}
	
	/**
	 * 查询加盟商场站运营统计
	 */
	@RequestMapping("toFranchiseeParkStatisticsList")
	public String toFranchiseeParkStatisticsList(ModelMap model) {
		return "/franchisee/franchisee_park_statistics_list";
	}
	
	@RequestMapping("pageListFranchiseeParkStatistics")
	@ResponseBody
	public PageFinder<FranchiseeProfitVo> pageListFranchiseeParkStatistics(@ModelAttribute("franchiseeProfit") FranchiseeProfit franchiseeProfit, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize)throws ParseException{
		franchiseeProfit.setProfitType(1);
		Query q = new Query(pageNo, pageSize, franchiseeProfit);
		return franchiseeProfitService.getFranchiseeParkStatisticsPagedList(q);
	}
}
