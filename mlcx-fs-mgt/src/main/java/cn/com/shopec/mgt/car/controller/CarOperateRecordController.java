package cn.com.shopec.mgt.car.controller;

import java.text.ParseException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.core.car.model.CarOperateRecord;
import cn.com.shopec.core.car.service.CarOperateRecordService;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("carOperateRecord")
public class CarOperateRecordController extends BaseController{
	@Resource
	private CarOperateRecordService carOperateRecordService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private CarService carService;
	/**
	 * 跳转用车指令记录列表页
	 * @param model
	 * @param carPlateNo
	 * @return
	 */
	@RequestMapping("toCarOperateRecordList")
	public String toCarOperateRecordList(Model model,String carPlateNo ) {
		model.addAttribute("carPlateNo",carPlateNo);
		return "car/car_operate_record_list";
	}
	/**
	 * 得到用车指令记录列表分页
	 * @param carOperateRecord
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageListCarOperateRecordList")
	@ResponseBody
	public PageFinder<CarOperateRecord> pageListCarOperateRecordList(@ModelAttribute("CarOperateRecord") CarOperateRecord carOperateRecord,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),carOperateRecord);
		return carOperateRecordService.getCarOperateRecordPagedList(q);
	}
	/**
	 * 用车记录详情
	 * 
	 * @param recordId
	 * @return
	 */
	@RequestMapping("toCarOperateRecordView")
	public String toCarOperateRecordView(@ModelAttribute("recordId") String recordId, Model model) {
		CarOperateRecord carOperateRecord = carOperateRecordService.getCarOperateRecord(recordId);
		model.addAttribute("carOperateRecord", carOperateRecord);
		return "car/car_operate_record_view";
	}
	
}
