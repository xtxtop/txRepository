package cn.com.shopec.mapi.order.controller;

import java.util.List;
import java.util.SortedMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.ml.service.AdvertMengLongService;
import cn.com.shopec.core.ml.service.COrderService;
import cn.com.shopec.core.ml.vo.OrderDetailVo;
import cn.com.shopec.core.ml.vo.OrderSimpleVo;
import cn.com.shopec.core.mlorder.service.ChargeOrderService;
import cn.com.shopec.core.mlorder.service.LockOrderService;
import cn.com.shopec.mapi.common.PayUtil;
import cn.com.shopec.mapi.common.controller.BaseController;

/**
 * 订单接口
 */
@Controller
@RequestMapping("/app/corder")
public class COrderController extends BaseController {
	@Resource
	private COrderService cOrderService;
	@Resource
	private AdvertMengLongService advertMengLongService;
	@Resource
	private ChargeOrderService chargeOrderService;
	@Resource
	private PayUtil payUtil;
	@Resource
	private LockOrderService lockOrderService;

	/**
	 * 获取地锁订单列表
	 */
	@RequestMapping("/lockOrderList")
	@ResponseBody
	public ResultInfo<List<OrderSimpleVo>> lockOrderList(String pageNo, String pageSize, String memberNo) {
		ResultInfo<List<OrderSimpleVo>> resultInfo = new ResultInfo<>();
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setMsg("操作成功");
		OrderSimpleVo search = new OrderSimpleVo();
		search.setMemberNo(memberNo);
		search.setTp("1");
		try {
			List<OrderSimpleVo> lst = cOrderService.searchOrdersByVo(Integer.valueOf(pageNo), Integer.valueOf(pageSize),
					search);
			if (lst.isEmpty()) {
				resultInfo.setCode(Constant.OTHER);
				resultInfo.setMsg("无数据");
			} else {
				resultInfo.setData(lst);
			}
		} catch (NumberFormatException e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("参数有误");
		}
		return resultInfo;
	}

	/**
	 * 获取充电订单列表
	 */
	@RequestMapping("/chargeOrderList")
	@ResponseBody
	public ResultInfo<List<OrderSimpleVo>> chargeOrderList(String pageNo, String pageSize, String memberNo) {
		ResultInfo<List<OrderSimpleVo>> resultInfo = new ResultInfo<>();
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setMsg("操作成功");
		OrderSimpleVo search = new OrderSimpleVo();
		search.setMemberNo(memberNo);
		search.setTp("2");
		try {
			List<OrderSimpleVo> lst = cOrderService.searchOrdersByVo(Integer.valueOf(pageNo), Integer.valueOf(pageSize),
					search);
			if (lst.isEmpty()) {
				resultInfo.setCode(Constant.OTHER);
				resultInfo.setMsg("无数据");
			} else {
				resultInfo.setData(lst);
			}
		} catch (NumberFormatException e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("参数有误");
		}
		return resultInfo;
	}

	/**
	 * 获取地锁订单详情
	 */
	@RequestMapping("/lockOrderDetail")
	@ResponseBody
	public ResultInfo<OrderDetailVo> lockOrderDetail(String orderNo) {
		ResultInfo<OrderDetailVo> resultInfo = new ResultInfo<>();
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setMsg("操作成功");
		if (orderNo == null || orderNo.length() < 1) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("参数有误");
		} else {
			OrderDetailVo search = new OrderDetailVo();
			search.setOrderNo(orderNo);
			search.setTp("1");
			OrderDetailVo data = cOrderService.findOrderDetailByVo(search);
			if (data == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("数据有误");
			} else {
				data.setBanners(advertMengLongService.getBannerByPos("7", "1").getData());
				resultInfo.setData(data);
			}
		}
		return resultInfo;
	}

	/**
	 * 获取充电订单详情
	 */
	@RequestMapping("/chargeOrderDetail")
	@ResponseBody
	public ResultInfo<OrderDetailVo> chargeOrderDetail(String orderNo) {
		ResultInfo<OrderDetailVo> resultInfo = new ResultInfo<>();
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setMsg("操作成功");
		if (orderNo == null || orderNo.length() < 1) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("参数有误");
		} else {
			OrderDetailVo search = new OrderDetailVo();
			search.setOrderNo(orderNo);
			search.setTp("2");
			OrderDetailVo data = cOrderService.findOrderDetailByVo(search);
			if (data == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("数据有误");
			} else {
				data.setBanners(advertMengLongService.getBannerByPos("10", "1").getData());
				resultInfo.setData(data);
			}
		}
		return resultInfo;
	}

	/**
	 * 停止充电
	 * 
	 * @param 订单编号
	 */
	@RequestMapping("/stopCharge")
	@ResponseBody
	public ResultInfo<OrderDetailVo> stopCharge(String orderNo) {
		ResultInfo<OrderDetailVo> resultInfo = chargeOrderService.stopChargeOrder(orderNo);
		return resultInfo;
	}

	/**
	 * 开始充电
	 * 
	 * @param chargingGunNo
	 *            充电枪编号
	 * @param deviceTp
	 *            设备类型（1、地锁 2、充电）
	 */
	@RequestMapping("/startCharge")
	@ResponseBody
	public ResultInfo<OrderSimpleVo> startCharge(String memberNo, String chargingGunNo, String deviceTp) {
		ResultInfo<OrderSimpleVo> resultInfo = chargeOrderService.startChargeOrder(memberNo, chargingGunNo, deviceTp);
		return resultInfo;
	}

	/**
	 * 订单详情微信支支付接口
	 * 
	 * @param request
	 * @param response
	 * @param memberNo
	 *            会员接口
	 * @param tag
	 *            （0、安卓 1、ios）
	 * @param dealType
	 *            （1、充电 2、地锁）
	 * @param payAccount
	 *            支付金额
	 * @return
	 */
	@RequestMapping("/payMent")
	@ResponseBody
	public ResultInfo<SortedMap<String, Object>> payMent(HttpServletRequest request, HttpServletResponse response,
			String memberNo, Integer tag, String dealType, String orderNo) {

		return payUtil.getCodeUrl(request, response, memberNo, tag, dealType, orderNo);
	}

	/**
	 * 接受微信返回给商户后台的信息并修改订单信息
	 */
	@RequestMapping("/wxUpdateOrder")
	@ResponseBody
	public void wxUpdateOrder(HttpServletRequest request, HttpServletResponse response) {

		payUtil.wxUpdateOrder(request, response, getOperator());
	}

	/**
	 * 订单详情支付宝支付接口
	 * 
	 * @param request
	 * @param response
	 * @param memberNo
	 *            会员编号
	 * @param tag
	 *            （0、安卓 1、ios）
	 * @param dealType
	 *            （1、充电 2、地锁）
	 * @param orderNo
	 *            订单号
	 * @return
	 */
	@RequestMapping("/apliyPayMent")
	@ResponseBody
	public ResultInfo<String> apliyPayMent(HttpServletRequest request, HttpServletResponse response, String memberNo,
			Integer tag, String dealType, String orderNo) {

		return payUtil.alipayGetOrderStr(request, response, memberNo, dealType, orderNo);
	}

	/**
	 * 支付宝返回给后台的异步通知结果，进行解析并修改订单信息
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/alipayUpdateOrder")
	public void alipayUpdateOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {

		payUtil.alipayUpdateOrder(request, response);
	}
}
