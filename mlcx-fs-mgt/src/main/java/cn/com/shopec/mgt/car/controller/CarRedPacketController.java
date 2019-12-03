package cn.com.shopec.mgt.car.controller;

import java.util.ArrayList;
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
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.Advert;
import cn.com.shopec.core.marketing.model.CarRedPacket;
import cn.com.shopec.core.marketing.service.CarRedPacketService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("carRedPacket")
public class CarRedPacketController extends BaseController {

	@Resource
	private CarRedPacketService carRedPacketService;
	@Resource
	private CarStatusService carStatusService;
	/*
	 * 显示红包车列表页
	 */
	@RequestMapping("toCarRedPacketList")
	public String toCarStatusList(Model model) {
		return "marketing/car_red_packet_list";
	}

	/*
	 * 显示红包车列表分页
	 */
	@RequestMapping("pageListCarIdleStatus")
	@ResponseBody
	public PageFinder<CarRedPacket> pageListCarStatus(@ModelAttribute("carRedPacket") CarRedPacket carRedPacket, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), carRedPacket);
		return carRedPacketService.getCarRedPacketPagedList(q);
	}
	
	/**
	 * 红包更新启用状态
	 */
	@RequestMapping("/carRedPacketIsAvailable")
	@ResponseBody
	public ResultInfo<CarRedPacket> updateCarRedPacket(String carRedPacketId, Integer isAvailable) {
		ResultInfo<CarRedPacket> result=new ResultInfo<CarRedPacket>();
		if(isAvailable ==1){
			CarRedPacket c=carRedPacketService.getCarRedPacket(carRedPacketId);
			if(c != null ){
				CarRedPacket cr = carRedPacketService.getByCarPlateNo(c.getCarPlateNo());
				if(cr != null){
					result.setCode("0");
					result.setMsg("相同的红包车已经启用,不能启用");
					return result;
				}else{
					CarStatus ct=new CarStatus();
					ct.setCarPlateNo(c.getCarPlateNo());
					ct.setUserageStatus(2);
					List<CarStatus> cs=carStatusService.getCarStatusList(new Query(ct));
					if(cs != null && cs.size()>0){
						result.setCode("0");
						result.setMsg("此车已经在订单中！");
						return result;
					}
					CarStatus cts=new CarStatus();
					cts.setCarPlateNo(c.getCarPlateNo());
					cts.setUserageStatus(1);
					List<CarStatus> css=carStatusService.getCarStatusList(new Query(cts));
					if(css != null && css.size()>0){
						result.setCode("0");
						result.setMsg("此车已预占！");
						return result;
					}
				}
			}
		}
		
		CarRedPacket carRedPacket = new CarRedPacket();
		carRedPacket.setCarRedPacketId(carRedPacketId);
		carRedPacket.setIsAvailable(isAvailable);
		if(isAvailable==0){
			carRedPacket.setCarRedPacketStatus(0);
		}else{
			carRedPacket.setCarRedPacketStatus(1);
		}
		return carRedPacketService.updateCarRedPacket(carRedPacket, getOperator());
	}
	
	
	/**
	 * 红包编辑
	 * 
	 * @return
	 */
	@RequestMapping("/updateCarRedPacket")
	@ResponseBody
	public ResultInfo<CarRedPacket> updateCarRedPacket(@ModelAttribute("CarRedPacket") CarRedPacket carRedPacket) {
		if(carRedPacket.getIsOrderAmountLimit()==0){
			carRedPacket.setOrderAmountLimit(0.0);
		}
		ResultInfo<CarRedPacket> result = carRedPacketService.updateCarRedPacket(carRedPacket, getOperator());
		return result;
	}
	
	
	
	/*
	 * 跳转至红包添加表页
	 */
	@RequestMapping("toCarRedPacketAdd")
	public String toCarRedPacketAdd(Model model,String carPlateNo) {
		model.addAttribute("carPlateNo", carPlateNo);
		return "marketing/car_red_packet_add";
	}
	
	
	/**
	 * 红包编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/toCarRedPacketEdit")
	public String toCarRedPacketEdit(String carRedPacketId, ModelMap model) {
		CarRedPacket carRedPacket = carRedPacketService.getCarRedPacket(carRedPacketId);
		model.addAttribute("carRedPacket", carRedPacket);
		return "marketing/car_red_packet_edit";
	}
	
	
	/**
	 * 添加红包车
	 * @param carRedPacket
	 * @return
	 */
	@RequestMapping("addCarRedPacket")
	@ResponseBody
	public ResultInfo<CarRedPacket> pageListCarStatus(@ModelAttribute("carRedPacket") CarRedPacket carRedPacket) {
		ResultInfo<CarRedPacket> result =  new ResultInfo<CarRedPacket>();
		//根据车牌号查询有红包已生效或者进行中
		CarRedPacket crp=carRedPacketService.getByCarPlateNo(carRedPacket.getCarPlateNo());
		if(crp != null){
			result.setCode(Constant.FAIL);
			result.setMsg("该车辆有已红包生效或者进行中,不能添加红包");
			return result;
		}
		return carRedPacketService.addCarRedPacket(carRedPacket, getOperator());
	}
}
