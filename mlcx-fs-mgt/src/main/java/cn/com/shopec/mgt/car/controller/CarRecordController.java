package cn.com.shopec.mgt.car.controller;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarAccident;
import cn.com.shopec.core.car.model.CarRecord;
import cn.com.shopec.core.car.service.CarRecordService;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.scheduling.model.WorkerOrder;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;
/**
 * 
 * @author machao
 *
 */
@Controller
@RequestMapping("carRecord")
public class CarRecordController extends BaseController{
	@Resource
	private CarRecordService carRecordService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private CarService carService;
	/*
	 * 显示用车记录列表页
	 */
	@RequestMapping("toCarRecordList")
	public String toCarRecordList(Model model,String carPlateNo ) {
		model.addAttribute("carPlateNo",carPlateNo);
		return "car/car_record_list";
	}
	/*
	 * 显示用车记录列表分页
	 */
	@RequestMapping("pageListCarRecordList")
	@ResponseBody
	public PageFinder<CarRecord> pageListCarRecordList(@ModelAttribute("CarRecord") CarRecord carRecord,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),carRecord);
		return carRecordService.getCarRecordPagedList(q);
	}
	/**
	 * 用车记录详情
	 * 
	 * @param recordId
	 * @return
	 */
	@RequestMapping("toCarRecordView")
	public String toCarRecordView(@ModelAttribute("recordId") String recordId, Model model) {
		CarRecord carRecord = carRecordService.getCarRecord(recordId);
		model.addAttribute("carRecord", carRecord);
		return "car/car_record_view";
	}
	
}
