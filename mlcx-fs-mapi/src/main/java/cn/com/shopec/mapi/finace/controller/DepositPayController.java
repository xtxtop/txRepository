package cn.com.shopec.mapi.finace.controller;

import java.util.SortedMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.common.DepositConstant;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.service.DepositOrderService;
import cn.com.shopec.core.finace.service.DepositPayService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mapi.common.controller.BaseController;
/**
 * 订单支付接口（支付宝支付和微信支付）
 * */
@Controller
@RequestMapping("/app/deposit")
public class DepositPayController extends BaseController{
	
	@Resource
	private DepositOrderService depositOrderService;
	
	@Resource
	private DepositPayService depositPayService;
	
	@Resource
	private SysParamService sysParamService;
	
	
	/**微信部分（
	*1.app将要支付的订单信息传递给商户后台，后台生成支付订单，将数据返回给app端
	*2.用户点击发起支付操作，进入微信界面，调用微信支付，进入确认支付页面
	*3.用户点击支付，输入密码
	*4.支付完成，微信出现支付详情页面
	*5.回跳商户app中，根据结果显示相应的页面
	*/
	/**商户后台需要进行的操作：
	*1.调用统一下单api(),返回给app端预支付订单信息（prepay_id）
	*2.根据app调起支付的参数，生成签名.(生成带签名的客户端支付信息)
	*3.接受微信返回给商户后台的信息并修改订单信息（异步通知商户支付结果）
	*4.调用微信查询api查询支付结果，并返回给客户端
	 */
	
	/**
	 * 1.调用统一下单api获取prepayId
	 * */
	@RequestMapping("/wxGetPrepayId")
	@ResponseBody
	public ResultInfo<SortedMap<String, Object>> wxGetPrepayId(HttpServletRequest request,HttpServletResponse response,String memberNo){
		//在会员押金表中添加一条记录
				ResultInfo<SortedMap<String, Object>> resultInfo=new ResultInfo<SortedMap<String, Object>>();
				if(memberNo!=null){
					Query q=new Query();
					DepositOrder searchOrder=new DepositOrder();
					searchOrder.setMemberNo(memberNo);
					searchOrder.setPayStatus(1);
					q.setQ(searchOrder);
					if(depositOrderService.getDepositOrderList(q)!=null&&depositOrderService.getDepositOrderList(q).size()>0){
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg(DepositConstant.exit_member);
						return resultInfo;
					}else{
						DepositOrder dOrder=new DepositOrder();
						dOrder.setMemberNo(memberNo);
						SysParam sysParam = sysParamService.getByParamKey(DepositConstant.deposit_amount_key);
						Double amount=Double.parseDouble(sysParam.getParamValue());//单位：元
						dOrder.setDepositAmount(amount);
						dOrder.setPayableAmount(amount);//测试
						dOrder.setPayStatus(0);
						dOrder.setRemainAmount(0d);
						dOrder.setDeductedAmount(0d);
						dOrder.setRefundAmount(0d);
						dOrder.setFrozenAmount(0d);
						ResultInfo<DepositOrder> result=depositOrderService.addDepositOrder(dOrder, getOperator());
						Integer tag=0;
						return depositPayService.getCodeUrl(request, response, result.getData(),tag);
					}
				}else{
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(DepositConstant.no_member);
					return resultInfo;
				}
	}
	/**
	 * 1.调用统一下单api获取prepayId
	 * */
	@RequestMapping("/wxGetPrepayIdIOS")
	@ResponseBody
	public ResultInfo<SortedMap<String, Object>> wxGetPrepayIdIOS(HttpServletRequest request,HttpServletResponse response,String memberNo){
		//在会员押金表中添加一条记录
				ResultInfo<SortedMap<String, Object>> resultInfo=new ResultInfo<SortedMap<String, Object>>();
				if(memberNo!=null){
					Query q=new Query();
					DepositOrder searchOrder=new DepositOrder();
					searchOrder.setMemberNo(memberNo);
					searchOrder.setPayStatus(1);
					q.setQ(searchOrder);
					if(depositOrderService.getDepositOrderList(q)!=null&&depositOrderService.getDepositOrderList(q).size()>0){
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg(DepositConstant.exit_member);
						return resultInfo;
					}else{
						DepositOrder dOrder=new DepositOrder();
						dOrder.setMemberNo(memberNo);
						SysParam sysParam = sysParamService.getByParamKey(DepositConstant.deposit_amount_key);
						Double amount=Double.parseDouble(sysParam.getParamValue());//单位：元
						dOrder.setDepositAmount(amount);
						dOrder.setPayableAmount(amount);//测试
						dOrder.setPayStatus(0);
						dOrder.setRemainAmount(0d);
						dOrder.setDeductedAmount(0d);
						dOrder.setRefundAmount(0d);
						dOrder.setFrozenAmount(0d);
						ResultInfo<DepositOrder> result=depositOrderService.addDepositOrder(dOrder, getOperator());
						Integer tag=1;
						return depositPayService.getCodeUrl(request, response, result.getData(),tag);
					}
				}else{
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(DepositConstant.no_member);
					return resultInfo;
				}
	}
	/**
	 *3. 接受微信返回给商户后台的信息并修改订单信息
	 * */
	@RequestMapping("/wxUpdateOrder")
	@ResponseBody
	public void wxUpdateOrder(HttpServletRequest request, HttpServletResponse response){
		depositPayService.wxUpdateOrder(request, response,getOperator());
	}
	/**
	 * 4.调用微信查询接口查询订单的实际支付结果
	 * */
	@RequestMapping("/wxGetOrderPayResult")
	@ResponseBody
	public ResultInfo<String> wxGetOrderPayResult(HttpServletRequest request,HttpServletResponse response,String orderNo){
		return depositPayService.wxGetOrderPayResult(request, response,orderNo);
	}
	
	
	/**支付宝部分（
	*1.app将要支付的订单信息传递给商户后台，后台生成签名后的订单信息，将签名后的订单信息返回给app端
	*2.用户发起支付，app调用支付接口，请求支付宝，完成支付。
	*3.返回同步支付结果，app进行验签，解析支付结果，
	*4.商户后台调用验证接口，验证同步结果数据。返回给客户端最终的支付结果。
	*5.显示支付结果在客户端
	*6.支付宝服务端一步发送支付通知给商户后台，接受并响应
	*/
	/**商户后台需要进行的操作：
	*1.根据app提供的订单信息，签名订单信息，返回给app签名后的订单信息
	*2.根据app解析后的同步支付结果，调用验证接口，验证同步结果数据，并返回给app最终的支付结果（忽略）
	*3.根据支付宝服务端发来的异步支付通知，进行接收并响应
	 注：由于同步通知和异步通知都可以作为支付完成的凭证，且异步通知支付宝一定会确保发送给商户服务端。
	 为了简化集成流程，商户可以将同步结果仅仅作为一个支付结束的通知（忽略执行校验），
	 实际支付是否成功，完全依赖服务端异步通知。
	 */
	
	/**
	 * 1.后台返回orderStr
	 * */
	@RequestMapping("/alipayGetOrderStr")
	@ResponseBody
	public ResultInfo<String> alipayGetOrderStr(HttpServletRequest request, HttpServletResponse response,String memberNo){
		//在会员押金表中添加一条记录
		ResultInfo<String> resultInfo=new ResultInfo<String>();
		if(memberNo!=null){
			Query q=new Query();
			DepositOrder searchOrder=new DepositOrder();
			searchOrder.setMemberNo(memberNo);
			searchOrder.setPayStatus(1);
			q.setQ(searchOrder);
			if(depositOrderService.getDepositOrderList(q)!=null&&depositOrderService.getDepositOrderList(q).size()>0){
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(DepositConstant.exit_member);
				return resultInfo;
			}else{
				DepositOrder dOrder=new DepositOrder();
				dOrder.setMemberNo(memberNo);
				SysParam sysParam = sysParamService.getByParamKey(DepositConstant.deposit_amount_key);
				Double amount=Double.parseDouble(sysParam.getParamValue());//单位：元
				dOrder.setDepositAmount(amount);
				dOrder.setPayableAmount(amount);//测试
				dOrder.setPayStatus(0);
				dOrder.setRemainAmount(0d);
				dOrder.setDeductedAmount(0d);
				dOrder.setRefundAmount(0d);
				dOrder.setFrozenAmount(0d);
				ResultInfo<DepositOrder> result=depositOrderService.addDepositOrder(dOrder, getOperator());
				return depositPayService.alipayGetOrderStr(request, response,result.getData());
			}
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(DepositConstant.no_member);
			return resultInfo;
		}
		
	}
	/**
	 *3. 支付宝返回给后台的异步通知结果，进行解析并修改订单信息
	 * @throws Exception 
	 * */
	@RequestMapping("/alipayUpdateOrder")
	public void alipayUpdateOrder(HttpServletRequest request, HttpServletResponse response) throws Exception{
		depositPayService.alipayUpdateOrder(request, response);
	}
}
