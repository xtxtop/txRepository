package cn.com.shopec.mgt.mlparking.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.mlparking.model.CGateMachine;
import cn.com.shopec.core.mlparking.model.CParkBilling;
import cn.com.shopec.core.mlparking.service.CGateMachineService;
import cn.com.shopec.core.mlparking.service.CParkBillingService;
import cn.com.shopec.core.mlparking.service.CParkingService;
import cn.com.shopec.core.mlparking.vo.CGateMachineVo;
import cn.com.shopec.mgt.common.BaseController;

/**
 * @author daiyuanbao
 * @category 闸机
 *
 */
@Controller
@RequestMapping("gateMachine")
public class GateMachineController extends BaseController{

	@Resource private CGateMachineService gateMachineService;
	@Resource
	private CParkingService parkingService;
	@Resource
	private CParkBillingService parkBillingService;
	/**
	 * 进入闸机列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/getGateMachine")
	public String getGateMachine(Model model){
		//计费方案
		model.addAttribute("billing", parkBillingService.getCParkBillingList(new Query()));
		//停车场
		model.addAttribute("park", parkingService.getCParkingList(new Query()));
		return "mlpark/gateMachine_list";
	}
	/**
	 * 获取闸机列表
	 * @param gateMachine
	 * @param query
	 * @return
	 */
	@RequestMapping("/gateMachineList")
	@ResponseBody
	private PageFinder<CGateMachineVo> gateMachineList(@ModelAttribute("CGateMachineVo") CGateMachineVo gateMachine,
            Query query){
		return gateMachineService.getCGateMachinePagedList(new Query(query.getPageNo(), query.getPageSize(),gateMachine));
	}
	/**
	 * 进入新增闸机
	 * @param model
	 * @return
	 */
	@RequestMapping("/togateMachineAdd")
	public String togateMachineAdd(Model model){
		//停车场
		model.addAttribute("park", parkingService.getCParkingList(new Query()));
		return "mlpark/gateMachine_add";
	}
	/**
	 * 新增/编辑闸机
	 * @param gateMachine
	 * @return
	 */
	@RequestMapping("/upASaddgateMachine")
	@ResponseBody
	public ResultInfo<CGateMachine> doaddgateMachine(CGateMachine gateMachine){
		ResultInfo<CGateMachine> resultInfo=new ResultInfo<CGateMachine>();
		if(gateMachine.getGateNo()!=null&&!"".equals(gateMachine.getGateNo())){//编辑
			return gateMachineService.updateCGateMachine(gateMachine, getOperator());
		}else{//新增
			CGateMachine gm=new CGateMachine();
			gm.setGateName(gateMachine.getGateName());
			List<CGateMachine> cGateMachineList = gateMachineService.getCGateMachineList(new Query(gm));
			cGateMachineList = cGateMachineList == null ? new ArrayList<CGateMachine>(0) : cGateMachineList;
			if(cGateMachineList.size()>0){
				resultInfo.setMsg("闸机名称不能重复!");
				resultInfo.setCode("0");
				return resultInfo;
			}
			return gateMachineService.addCGateMachine(gateMachine, getOperator());
		}
	}
	/**
	 * 进入编辑闸机
	 * @param gateNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/togateMachineEdit")
	public String togateMachineEdit(String gateNo ,Model model){
		model.addAttribute("gate", gateMachineService.getCGateMachine(gateNo));
		//停车场
		model.addAttribute("park", parkingService.getCParkingList(new Query()));
		return "mlpark/gateMachine_edit";
	}
	/**
	 * 删除闸机
	 * @param gateNo
	 * @return
	 */
	@RequestMapping("/delgateMachine")
	@ResponseBody
	public ResultInfo<CGateMachine> delgateMachine(String gateNo){
		return gateMachineService.delCGateMachine(gateNo, getOperator());
	}
}
