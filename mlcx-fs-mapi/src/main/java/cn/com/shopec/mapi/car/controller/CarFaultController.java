package cn.com.shopec.mapi.car.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.car.service.CarFaultService;
import cn.com.shopec.mapi.car.vo.CarFaultVo;
import cn.com.shopec.mapi.common.controller.BaseController;

@Controller
@RequestMapping("/app/carFault")
public class CarFaultController extends BaseController{

	@Resource
	private CarFaultService carFaultService;
	
	
	/**
	 * 方法说明：查看一个月内的违章情况
	 */
	
	@RequestMapping("/carAccident")
	@ResponseBody
	public ResultInfo<CarFaultVo> carAccident(){
		
		
		return null;
	}
	
}
