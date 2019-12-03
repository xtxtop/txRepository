package cn.com.shopec.common.pay.aliPay;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.order.model.Order;

public class AliPay {
	
	// 支付宝支付请求（商品名称，总金额，会员，orderNo）
		public String aliPay( String subject,String total_fee,Member member, HttpServletRequest request,String out_trade_no) {
			// 支付类型 必填，不能修改
			String payment_type = "1";

			// 服务器异步通知页面路径 //需http://格式的完整路径，不能加?id=123这类自定义参数
			String notify_url = AlipayConfig.notify_url;

			// 页面跳转同步通知页面路径
			// //需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
			String return_url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath() + AlipayConfig.return_url;

			// 订单描述
			String body = "";

			// 商品展示地址 需以http://开头的完整路径，例如：http://www.xxx.com/myorder.html
			String show_url = "";

			// 防钓鱼时间戳 若要使用请调用类文件submit中的query_timestamp函数
			String anti_phishing_key = "";

			//////////////////////////////////////////////////////////////////////////////////
			String exter_invoke_ip = "";

			// 把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("_input_charset", AlipayConfig.input_charset);

			sParaTemp.put("out_trade_no", out_trade_no);
			sParaTemp.put("partner", AlipayConfig.partner);
			sParaTemp.put("payment_type", payment_type);

			sParaTemp.put("seller_email", AlipayConfig.seller_email);
			sParaTemp.put("seller_id", AlipayConfig.seller_id);
			sParaTemp.put("service", "create_direct_pay_by_user");
			sParaTemp.put("subject", subject);
			sParaTemp.put("total_fee", total_fee);
			sParaTemp.put("notify_url", notify_url);
			sParaTemp.put("return_url", return_url);

			sParaTemp.put("body", body);
			sParaTemp.put("show_url", show_url);
			sParaTemp.put("anti_phishing_key", anti_phishing_key);
			sParaTemp.put("exter_invoke_ip", exter_invoke_ip);

			// 建立请求
			return AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
		}

		// 支付宝返回数据的处理（异步请求）
		public void backAliPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
			// 获取支付宝POST过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				params.put(name, valueStr);
			}

			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			// 商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

			// 支付宝交易流水号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

			// 交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

			// 买家账号
//			String buyer_email = new String(request.getParameter("buyer_email").getBytes("ISO-8859-1"), "UTF-8");

			// 购买时间
			String notify_time = new String(request.getParameter("notify_time").getBytes("ISO-8859-1"), "UTF-8");

			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			if(!AlipayNotify.verify(params) || !(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS"))){
				return;
			
			}
			//订单信息修改
			
			
			
			// 后台通知是Upop异步把交易状态为成功的交易通知给商户。商户验签
			// 解析后应立即返回应答报文。仅当POST返回码为200时，才认为商户已经成功收到并且能正确解析后台通知，其他返回码则被认为通知失败。
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(HttpServletResponse.SC_OK);
		}

		// 同步请求(仅适用于立即返回结果的接口)
		public ModelAndView frontAliPay(HttpServletRequest request, HttpServletResponse response, ModelMap model)
				throws Exception {
			// 获取支付宝GET过来反馈信息
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}

			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			// 商户订单号

			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

			// 支付宝交易号

//			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

			// 交易状态
//			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

			// 交易金额
			String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");

			// 购买时间
			String notify_time = new String(request.getParameter("notify_time").getBytes("ISO-8859-1"), "UTF-8");
			try {
				
				
				// 处理订单信息
				
				
				
			} catch (Exception e) {
				return new ModelAndView("mall/pay/payFail", model);
			}
			return null;
		}
}
