package cn.com.shopec.mgt.mlorder.controller;

import java.text.ParseException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.service.ChargingStationService;
import cn.com.shopec.core.ml.vo.OrderDetailVo;
import cn.com.shopec.core.mlorder.model.ChargeOrder;
import cn.com.shopec.core.mlorder.service.ChargeOrderService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * @category 充电订单
 */
@Controller
@RequestMapping("chargeOrder")
public class ChargeOrderController extends BaseController {

	@Resource
	private ChargeOrderService chargeOrderServiceImpl;
	@Resource
	private ChargingStationService chargingStationServiceImpl;

	/**
	 * 进入充电订单页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/getChargeOrder")
	public String getChargeOrder(Model model) {
		model.addAttribute("csList", chargingStationServiceImpl.getChargingStationList(new Query()));
		return "mlorder/chargeOrder_list";
	}

	/**
	 * 充电订单列表展示
	 * 
	 * @param chargeOrder
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/pageListchargeOrder")
	@ResponseBody
	public PageFinder<ChargeOrder> pageListchargeOrder(@ModelAttribute("ChargeOrder") ChargeOrder chargeOrder,
			Query query) throws ParseException {
		return chargeOrderServiceImpl
				.getChargeOrderPagedList(new Query(query.getPageNo(), query.getPageSize(), chargeOrder));
	}

	/**
	 * 查看详情
	 * 
	 * @param orderNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/tochargeOrderView")
	public String tochargeOrderView(String orderNo, Model model) {
		model.addAttribute("chargeOrder", chargeOrderServiceImpl.getChargeOrder(orderNo));
		return "mlorder/chargeOrder_view";
	}

	/**
	 * 结束充电
	 * 
	 * @param chargeOrder
	 * @return
	 */

	@RequestMapping("/disablechargeOrder")
	@ResponseBody
	public ResultInfo<ChargeOrder> disablechargeOrder(@ModelAttribute("ChargeOrder") ChargeOrder chargeOrder) {
		ResultInfo<ChargeOrder> resultInfo = new ResultInfo<ChargeOrder>();
		ResultInfo<OrderDetailVo> resultInfo2 = chargeOrderServiceImpl.stopChargeOrder(chargeOrder.getOrderNo());
		if (Constant.SECCUESS.equals(resultInfo2.getCode())) {

			return chargeOrderServiceImpl.updateChargeOrder_two(chargeOrder, getOperator());
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(resultInfo2.getMsg());
			return resultInfo;
		}
	}
}
