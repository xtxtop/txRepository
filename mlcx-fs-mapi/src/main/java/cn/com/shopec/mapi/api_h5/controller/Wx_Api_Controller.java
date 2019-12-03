package cn.com.shopec.mapi.api_h5.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.mapi.api_h5.util.SignUtil;
import cn.com.shopec.mapi.message.controller.SendMessageOrderController;

@Controller
@RequestMapping("/wx_api")
public class Wx_Api_Controller {
	private Log logger = LogFactory.getLog(Wx_Api_Controller.class);
	/**
	 * 前端h5API接口
	 * @author ZhangShiChao
	 * @time 2017/8/11
	 * @param request 微信请求
	 * @return
	 */
	@RequestMapping("/getwx")
	@ResponseBody
	public String wx(HttpServletRequest request) {
		try {
			// 微信加密签名
			String signature = request.getParameter("signature");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");
			// 随机字符串
			String echostr = request.getParameter("echostr");
			//PrintWriter out = response.getWriter();
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
					return  echostr;
					}
		} catch (Exception e) {
			logger.info("h5调用wx接口异常:"+e);
		}
		return "null";	
	}
}
