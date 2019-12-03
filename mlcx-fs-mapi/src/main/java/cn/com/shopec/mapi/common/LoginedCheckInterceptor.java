package cn.com.shopec.mapi.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * session过期、登录有效性及操作的权限验证拦截器
 * @author lyf
 *
 */
public class LoginedCheckInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		HttpSession session = req.getSession(true);
		String targetPath = req.getScheme() + "://"
				+ req.getServerName() + ":" + req.getServerPort()
				+ req.getContextPath() + "/"+Constant.LOGIN_URL;
		// 取得请求的URL
		req.setCharacterEncoding("utf-8");
		res.setHeader("Pragma", "No-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setHeader("Cache-Control", "no-store");
		
		
		
		
		res.setDateHeader("Expires", 0);
		String url = req.getRequestURL().toString();
		
		//项目默认的首页、登录、注册等不予拦截、直接放行
		//对首页的定价，拍卖，竞价，技术文档，行业动态，关于我们放行
		if (url.indexOf("index") != -1||url.endsWith("b2b-mall/")||url.indexOf("login") != -1||url.indexOf("loginUser") != -1
				||url.indexOf("register") != -1||url.indexOf("memberUserModify") != -1||
				url.indexOf("lastRegister") != -1||url.indexOf("memberDocModify") != -1 
				||url.indexOf("toInquiryListHome") != -1
				||url.indexOf("inquiryDetailHome") != -1
				||url.indexOf("procurerAuction-listHome") != -1
				||url.indexOf("procurerAuction-tradeHome") != -1
				||url.indexOf("toFixedPriceInquiryListHome") != -1
				||url.indexOf("skuHome") != -1
				||url.indexOf("cmsArticleHome") != -1
				||url.indexOf("aboutUs") != -1
				||url.indexOf("cmsArticleDetial") != -1
				||url.indexOf("beforPassword") != -1
				||url.indexOf("beforPasswordSub") != -1
				||url.indexOf("search") != -1
				||url.indexOf("registerMemberId") != -1
				||url.indexOf("getMemberCompanyName") != -1
				||url.indexOf("randImage") != -1
				||url.indexOf("valSendMsg") != -1
				||url.indexOf("getMemberUserPh") != -1
				||url.indexOf("getMemberUserEm") != -1
				||url.indexOf("getMemberUserName") != -1
				||url.indexOf("registerMemberId") != -1
				||url.indexOf("easySendMsg") != -1 
				||url.indexOf("getSku") != -1
				||url.indexOf("registerMemberPage") != -1
				//邮箱请求
				||url.indexOf("reviewRequestUrl.do") != -1
				//首页拍卖异步请求
				||url.indexOf("auctionPenndingBox") != -1
				//首页竞价异步请求
				||url.indexOf("inquiryPenndingBox") != -1
				//shou 
				||url.indexOf("getAuctionDetail") != -1
				//
				||url.indexOf("getInquirysDetail") != -1
				||url.indexOf("procurerGetOrdersDetail") != -1
				
				) {
			return true;
		}
		
		//获取session中登陆用户
		Object o = null;
		o = session.getAttribute(WebConstant.SESSION_KEY_OF_LOGIN_MEMBER);
		//所有ajax请求不予拦截
				if(isAjaxRequest(req)) {
					// 验证Session是否过期
					if (!req.isRequestedSessionIdValid()) {
						
						// session过期,转向session过期提示页,最终跳转至登录页面			
						//res.sendRedirect(req.getContextPath()+Constant.LOGIN_URL);			
						res.setHeader("session_is_timeout", "1");
		    			return false;
					} else {
						if (o == null) {				
							res.setHeader("session_is_timeout", "1");
			    			return false;
						}
					}
//					
				}
		// 验证Session是否过期
		if (!req.isRequestedSessionIdValid()) {
			
			// session过期,转向session过期提示页,最终跳转至登录页面			
			//res.sendRedirect(req.getContextPath()+Constant.LOGIN_URL);			
			res.sendRedirect(targetPath);
			return false;
		} else {
			if (o == null) {				
				res.sendRedirect(targetPath);
				//req.getRequestDispatcher(Constant.LOGIN_URL).forward(req, res);
				return false;
			}
		}
		return true;
	}
	/**
	 * 判断是否ajax请求
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		boolean res = false;
		String requestType = request.getHeader("X-Requested-With");
		if("XMLHttpRequest".equalsIgnoreCase(requestType)) {
			res = true;
		}
		return res;
	}
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
