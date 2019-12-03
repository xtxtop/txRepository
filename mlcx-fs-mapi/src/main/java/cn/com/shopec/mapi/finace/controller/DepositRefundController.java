package cn.com.shopec.mapi.finace.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.finace.model.DepositRefund;
import cn.com.shopec.core.finace.service.DepositRefundService;
import cn.com.shopec.mapi.car.vo.CarVo;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.finace.vo.DepositRefundVo;

/**
 * 押金退款申请
 * */
@Controller
@RequestMapping("/app/depositRefund")
public class DepositRefundController extends BaseController{

	@Resource
	public DepositRefundService depositRefundService;
	
	
	/**
	 * 提交押金退款申请，添加押金退款申请记录
	 */
	@RequestMapping("/addDepositRefund")
	@ResponseBody
	public ResultInfo<DepositRefundVo> addDepositRefund(DepositRefund depositRefund){
		ResultInfo<DepositRefund> resultInfo=new ResultInfo<DepositRefund>();
		depositRefund.setRefundStatus(0);//未退款
		resultInfo=depositRefundService.addDepositRefund(depositRefund, getOperator());
		ResultInfo<DepositRefundVo> result=new ResultInfo<DepositRefundVo>();
	    return depositRefundToVoOne(result,resultInfo.getData());
	}
	
	/**
	 * 方法说明:将按特定order对象转换成自定vo对象
	 */
	 ResultInfo<DepositRefundVo> depositRefundToVoOne(ResultInfo<DepositRefundVo> result,DepositRefund depositRefund){
		 if(depositRefund!=null){
			 DepositRefundVo or = new DepositRefundVo();
			 or.setApplyTime(depositRefund.getApplyTime());
			 or.setDepositOrderNo(depositRefund.getDepositOrderNo());
			 or.setMemberName(depositRefund.getMemberName());
			 or.setMemberNo(depositRefund.getMemberNo());
			 or.setMobilePhone(depositRefund.getMobilePhone());
			 or.setRefundAmount(depositRefund.getRefundAmount());
			 or.setRefundNo(depositRefund.getRefundNo());
			 or.setRefundStatus(depositRefund.getRefundStatus());
			 result.setData(or);
			 result.setCode(CarConstant.success_code);
			 result.setMsg(""); 
		 }else{
			 result.setCode(CarConstant.fail_code);
			 result.setMsg(CarConstant.fail_msg); 
		 }
			
		return result;
	}
}
