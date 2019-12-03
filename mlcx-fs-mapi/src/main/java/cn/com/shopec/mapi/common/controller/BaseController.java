package cn.com.shopec.mapi.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.shopec.common.Operator;
import cn.com.shopec.core.common.ECCoreConstant;
//import cn.com.shopec.common.sendmsg.SendMsgService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.scheduling.model.Worker;
import cn.com.shopec.core.scheduling.service.WorkerService;
//import cn.com.shopec.core.customer.model.MailTemplate;
//import cn.com.shopec.core.customer.model.SendMail;
//import cn.com.shopec.core.customer.model.SendSms;
//import cn.com.shopec.core.customer.model.SmsTemplate;
//import cn.com.shopec.core.customer.service.MailTemplateService;
//import cn.com.shopec.core.customer.service.SendMailService;
//import cn.com.shopec.core.customer.service.SendSmsService;
//import cn.com.shopec.core.customer.service.SmsTemplateService;
//import cn.com.shopec.core.member.model.Member;
//import cn.com.shopec.core.member.model.MemberUser;
import cn.com.shopec.core.system.service.SysParamService;
//import cn.com.shopec.mapi.common.email.MailInfo;
//import cn.com.shopec.mapi.common.email.SimpleMailSender;

public class BaseController {
	//创建日志文件
	private static Logger log = Logger.getLogger(BaseController.class);
	
	@Resource
	HttpServletRequest request;
	@Resource
	private MemberService memberService;
	@Resource
	private WorkerService workerService;
	@Resource
	private SysParamService sysParamService;
//	@Resource
//	private SmsTemplateService smsTemplateService;
//	@Resource
//	private MailTemplateService mailTemplateService;
//	@Resource
//	private SendMailService sendMailService;
//	@Resource
//	private SendSmsService sendSmsService;
//	@Resource
//	private SendMsgService msgService;
	/**
	 * 登录信息放到session
	 * */
	public static boolean setLoginMember(HttpServletRequest request, Member member) {
		try {
			if(member!=null){
				request.getSession().setAttribute("LOGIN_MEMBER", member);
				return true;
			}else{
				request.getSession().setAttribute("LOGIN_MEMBER", null);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Map<String, Object> getParamerMap() {
		// Map<String, String[]> mp = request.getParameterMap();
		Map<String, Object> mv = new HashMap<String, Object>();

		// for (Map.Entry<String, String[]> entry : mp.entrySet()) {
		// String keyName = entry.getKey();
		// String[] mvs = entry.getValue();
		// if (mvs == null) {
		// continue;
		// }
		// if (mvs.length <= 1) {
		// mv.put(keyName, mvs[0]);
		// }
		// else {
		// mv.put(keyName, mvs);
		// }
		// }
		// int curPage = p.getCurPage();
		// if (mv.get("curPage") == null) {
		// }
		// else {
		// curPage = Integer.valueOf((String) mv.get("curPage"));
		// }
		// mv.put("curPage", curPage);
		// mv.put("start", (curPage - 1) * p.getPageSize());
		// mv.put("pageSize", p.getPageSize());
		return mv;
	}

//	/**
//	 * 取HttpSession对象
//	 * 
//	 * @return
//	 */
//	public HttpSession getSession() {
//		return request.getSession();
//	}
//
//	public HttpServletRequest getRequest() {
//		return request;
//	}
	 
//	/**
//	 * 设置用户的登录信息
//	 * 
//	 * @Title: setLoginSysUser
//	 * @Description: TODO
//	 * @param request
//	 * @param sysUser
//	 * @return
//	 */
//	public static boolean setLoginSysUser(HttpServletRequest request, SysUser sysUser) {
//		try {
//			request.getSession().setAttribute(ECMgtConstant.SESSION_KEY_OF_LOGIN_SYS_USER, sysUser);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	/**
//	 * 取已登录的系统人员用户对象（数据来自session）
//	 * 
//	 * @return
//	 */
//	public SysUser getLoginSysUser() {
//		SysUser sysUser = null;
//		HttpSession session = getSession();
//		if (session != null) {
//			sysUser = (SysUser) session.getAttribute(ECMgtConstant.SESSION_KEY_OF_LOGIN_SYS_USER); // 从session中取已登录的系统人员用户对象
//		}
//		return sysUser;
//	}

	/**
	 * 移除session 属性
	 * 
	 * @Title: removeSessionAttribut
	 * @Description: TODO
	 * @param request
	 * @param key
	 */
	public static void removeSessionAttribut(HttpServletRequest request, String key) {
		request.getSession().removeAttribute(key);
	}

//	/**
//	 * 移除登陆用户
//	 * 
//	 * @Title: removeSessionAttribut
//	 * @Description: TODO
//	 * @param request
//	 * @param key
//	 */
//	public static void removeSessionUser(HttpServletRequest request) {
//		removeSessionAttribut(request, WebConstant.SESSION_KEY_OF_LOGIN_SYS_USER);
//
//	}
	

	/**
	 * 取通用操作员对象
	 * 会员
	 * @return
	 */
	public Operator getOperator() {
		return convertMember2Operator(getLoginMember());
	}
	/**
	 * 取通用操作员对象
	 * 调度员
	 * @return
	 */
	public Operator getOperator1() {
		return convertWorker2Operator(getLoginWorker());
	}
//	/**
//	 * 设置会员的登录信息
//	 * 
//	 * @Title: setLoginSysUser
//	 * @Description: TODO
//	 * @param request
//	 * @param sysUser
//	 * @return
//	 */
//	public static boolean setLoginMember(HttpServletRequest request, Member member) {
//		try {
//			request.getSession().setAttribute(ECMgtConstant.SESSION_KEY_OF_LOGIN_MEMBER, member);
//			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return false;
//	}

	/**
	 * 取登录的会员用户对象（注意！本工程为Api工程，因此不是从session获取登陆用户数据，而是根据参数中是否有memberNo，从DB/Cache获取，）
	 * 
	 * @return
	 */
	public Member getLoginMember() {
		Member member = null;
		String memberNo = this.request.getParameter("memberNo");
		if(memberNo != null && memberNo.length() != 0) {
			member = memberService.getMemberBasicInfo(memberNo);
		}
		
		return member;
	}

	
	/**
	 * 将会员登录对象，转为通用操作员对象
	 * 
	 * @param member
	 * @return
	 */
	public Operator convertMember2Operator(Member member) {
		Operator op = null;
		if (member != null) {
			op = new Operator(ECCoreConstant.OPERATOR_TYPE_MEMBER_USER, member.getMemberNo(), member.getMemberName(), member.getMemberName(), null, member.getMobilePhone());
		}
		return op;
	}	
	/**
	 * 取登录的会员用户对象（注意！本工程为Api工程，因此不是从session获取登陆用户数据，而是根据参数中是否有memberNo，从DB/Cache获取，）
	 * 
	 * @return
	 */
	public Worker getLoginWorker() {
		Worker worker = null;
		String workerNo = this.request.getParameter("workerNo");
		if(workerNo != null && workerNo.length() != 0) {
			worker = workerService.getWorkerBasicInfo(workerNo);
		}
		
		return worker;
	}

	
	/**
	 * 将会员登录对象，转为通用操作员对象
	 * 
	 * @param member
	 * @return
	 */
	public Operator convertWorker2Operator(Worker worker) {
		Operator op = null;
		if (worker != null) {
			op = new Operator(ECCoreConstant.OPERATOR_TYPE_MEMBER_USER, worker.getWorkerNo(), worker.getWorkerName(), worker.getWorkerName(), null, worker.getMobilePhone());
		}
		return op;
	}	
}
