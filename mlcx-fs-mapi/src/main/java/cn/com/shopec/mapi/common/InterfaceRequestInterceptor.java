package cn.com.shopec.mapi.common;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 客户端请求接口验证拦截器（时间戳，签名验证）
 *
 */
public class InterfaceRequestInterceptor implements HandlerInterceptor {
	@Resource
	private SysParamService sysParamService;
	@Resource
	private MemberService memberService;

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		String errorInfoPath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
				+ req.getContextPath() + "/" + Constant.ERROR_URL;
		req.setCharacterEncoding("utf-8");
		res.setHeader("Pragma", "No-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setHeader("Cache-Control", "no-store");
		res.setDateHeader("Expires", 0);

		// 验证token
		String validateToken = req.getHeader("token");
		if (validateToken != null && !"".equals(validateToken)) {
			Member member = memberService.getMemberByToken(validateToken);
			if (member == null) {
				res.sendRedirect(errorInfoPath + "?type=5");
				return false;
			}
		}

		String checkSwitch = sysParamService.getByParamKey(Constant.CHECK_SWITCH).getParamValue();
		if (Constant.CHECK_SWITCH_ON.equals(checkSwitch)) {
			// 验证token
			String token = req.getHeader("token");
			if (token != null && !"".equals(token)) {
				Member member = memberService.getMemberByToken(token);
				String tokenExpDateDays = sysParamService.getByParamKey(Constant.TOKEN_EXP_DATE).getParamValue();
				if (tokenExpDateDays != null) {
					Date failureDate = ECDateUtils.getDateAfter(member.getTokenGenerateTime(),
							Integer.parseInt(tokenExpDateDays));
					Date nowTime = ECDateUtils.getCurrentDateTime();
					if (failureDate.getTime() > nowTime.getTime()) {
						res.sendRedirect(errorInfoPath + "?type=3");
						return false;
					}
				}

			}
			// 验证时间戳
			String timestamp = req.getParameter("timestamp");
			String sign = req.getParameter("sign");
			String failTime = sysParamService.getByParamKey(Constant.SYS_FAILURE_TIME).getParamValue();
			if (failTime != null && !"".equals(failTime)) {
				Integer sysFailureTime = Integer.parseInt(failTime);
				Long reqTime = new Long(timestamp);
				Integer diffMin = ECDateUtils.differMinutes(new Date(), new Date(reqTime)).intValue();
				Integer resu = Math.abs(diffMin);
				// 时间戳不符合，不能请求
				if (resu > sysFailureTime) {
					res.sendRedirect(errorInfoPath + "?type=1");
					return false;
				}
			}
			String secret = sysParamService.getByParamKey(Constant.SECRET).getParamValue();
			if (secret != null && !"".equals(secret)) {
				// 验证签名
				String mySign = SignUtil.signParam(req, secret);
				if (!mySign.equals(sign)) {
					res.sendRedirect(errorInfoPath + "?type=2");
					return false;
				}
			}
		}

		String requestURI = req.getRequestURI();
		if (requestURI != null && !"".equals(requestURI)) {
			requestURI = requestURI.replace(req.getContextPath() + "/", "");
		}
		ServletContext context = req.getSession().getServletContext();
		// 服务器启动时context里已经存放的需要处理的请求路径
		ConcurrentHashMap<String, String> uriMap = (ConcurrentHashMap<String, String>) context
				.getAttribute("interceptorURIMap");
		// 存放要处理请求路径
		ConcurrentHashMap<String, String> requestURIMap = (ConcurrentHashMap<String, String>) context
				.getAttribute("requestURIMap");
		for (String key : uriMap.keySet()) {
			String uriValue = uriMap.get(key);
			// 当初始化context中map有请求的uri时，分不同业务类型处理
			if (uriValue.equals(requestURI)) {
				// 还车，先从requestURIMap以会员编号和订单号组成的键取值，有的话不许访问
				if (key.equals("returnCar")) {
					String returnCarURI = "returnCar" + "_" + req.getParameter("orderNo");
					String value = requestURIMap.get(returnCarURI);
					if (value == null) {
						requestURIMap.put(returnCarURI, req.getParameter("orderNo"));
						break;
					} else {
						res.sendRedirect(errorInfoPath + "?type=4");
						return false;
					}
					// 微信支付
				} else if (uriValue.equals("wxpayURI")) {// payNo,memberNo,packageNo,type,tag
					String wxpayURI = "wxpayURI" + req.getParameter("payNo") + "_" + req.getParameter("memberNo") + "_"
							+ req.getParameter("packageNo") + "_" + req.getParameter("type") + "_"
							+ req.getParameter("tag");
					String value = requestURIMap.get(wxpayURI);
					if (value == null) {
						requestURIMap.put(wxpayURI,
								req.getParameter("payNo") + "_" + req.getParameter("memberNo") + "_"
										+ req.getParameter("packageNo") + "_" + req.getParameter("type") + "_"
										+ req.getParameter("tag"));
					} else {
						res.sendRedirect(errorInfoPath + "?type=4");
						return false;
					}
					// 支付宝支付
				} else if (uriValue.equals("alipayURI")) {// payNo,memberNo,packageNo,type,tag
					String alipayURI = "alipayURI" + req.getParameter("payNo") + "_" + req.getParameter("memberNo")
							+ "_" + req.getParameter("packageNo") + "_" + req.getParameter("type") + "_"
							+ req.getParameter("tag");
					String value = requestURIMap.get(alipayURI);
					if (value == null) {
						requestURIMap.put(alipayURI,
								req.getParameter("payNo") + "_" + req.getParameter("memberNo") + "_"
										+ req.getParameter("packageNo") + "_" + req.getParameter("type") + "_"
										+ req.getParameter("tag"));
					} else {
						res.sendRedirect(errorInfoPath + "?type=4");
						return false;
					}
				}
			}
		}
		return true;

	}

	/**
	 * 判断是否ajax请求
	 * 
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		boolean res = false;
		String requestType = request.getHeader("X-Requested-With");
		if ("XMLHttpRequest".equalsIgnoreCase(requestType)) {
			res = true;
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		ServletContext context = request.getSession().getServletContext();
		Map<String, String> uriMap = (Map<String, String>) context.getAttribute("interceptorURIMap");
		Map<String, String> requestURIMap = (Map<String, String>) context.getAttribute("requestURIMap");
		String requestURI = request.getRequestURI();
		if (requestURI != null && !"".equals(requestURI)) {
			requestURI = requestURI.replace(request.getContextPath() + "/", "");
		}
		for (String key : uriMap.keySet()) {
			String uriValue = uriMap.get(key);
			// 当初始化context中map有请求的uri时，分不同业务类型处理
			if (uriValue.equals(requestURI)) {
				// 还车，先从requestuestURIMap以会员编号和订单号组成的键取值，访问完成之后把map中对应的键的值赋为空
				if (key.equals("returnCar")) {
					String returnCarURI = "returnCar" + "_" + request.getParameter("orderNo");
					String value = requestURIMap.get(returnCarURI);
					if (value != null) {
						requestURIMap.remove(returnCarURI);
					}
					// 微信支付
				} else if (uriValue.equals("wxpayURI")) {// payNo,memberNo,packageNo,type,tag
					String wxpayURI = "wxpayURI" + request.getParameter("payNo") + "_"
							+ request.getParameter("memberNo") + "_" + request.getParameter("packageNo") + "_"
							+ request.getParameter("type") + "_" + request.getParameter("tag");
					String value = requestURIMap.get(wxpayURI);
					if (value != null) {
						requestURIMap.remove(wxpayURI);
					}
					// 支付宝支付
				} else if (uriValue.equals("alipayURI")) {// payNo,memberNo,packageNo,type,tag
					String alipayURI = "alipayURI" + request.getParameter("payNo") + "_"
							+ request.getParameter("memberNo") + "_" + request.getParameter("packageNo") + "_"
							+ request.getParameter("type") + "_" + request.getParameter("tag");
					String value = requestURIMap.get(alipayURI);
					if (value != null) {
						requestURIMap.remove(alipayURI);
					}
				}
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
