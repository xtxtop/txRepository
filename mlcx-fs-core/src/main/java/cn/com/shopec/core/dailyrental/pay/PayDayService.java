package cn.com.shopec.core.dailyrental.pay;

import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;

/**
 * 订单支付
 */
public interface PayDayService extends BaseService {

	/**
	 * 微信统一下单
	 * 
	 * @param tag
	 */
	public ResultInfo<SortedMap<String, Object>> getCodeUrl(HttpServletRequest request, HttpServletResponse response,
			String payNo, String memberNo, Integer tag);

	/**
	 * 根据app调起支付的参数，生成签名
	 */
	public ResultInfo<SortedMap<String, Object>> getCodeUrlApp(HttpServletRequest request, HttpServletResponse response,
			String prepay_id, String nonceStr, String timeStamp);

	/**
	 * 调用微信查询订单支付结果接口
	 */
	public ResultInfo<String> wxGetOrderPayResult(HttpServletRequest request, HttpServletResponse response,
			String orderNo);

	/**
	 * 接受微信返回给商户后台的信息并修改订单信息
	 */
	public void wxUpdateOrder(HttpServletRequest request, HttpServletResponse response, Operator operator);

	/**
	 * 支付宝商户后台返回orderStr给app端
	 */
	public ResultInfo<String> alipayGetOrderStr(HttpServletRequest request, HttpServletResponse response, String payNo,
			String memberNo);

	/**
	 * 支付宝返回给商户后台的异步支付结果信息，并修改订单信息
	 */
	public void alipayUpdateOrder(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
