package cn.com.shopec.mgt.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.component.ECCustomerDateEditor;
//import cn.com.shopec.common.sendmsg.SendMsgService;
import cn.com.shopec.core.common.ECCoreConstant;
import cn.com.shopec.core.common.Query;
//import cn.com.shopec.core.customer.model.MailTemplate;
//import cn.com.shopec.core.customer.model.SendMail;
//import cn.com.shopec.core.customer.model.SmsTemplate;
//import cn.com.shopec.core.customer.service.MailTemplateService;
//import cn.com.shopec.core.customer.service.SendMailService;
//import cn.com.shopec.core.customer.service.SmsTemplateService;
//import cn.com.shopec.core.member.model.MemberUser;
import cn.com.shopec.core.system.model.SysOpLog;
import cn.com.shopec.core.system.model.SysRole;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.model.SysUserRoleRel;
import cn.com.shopec.core.system.service.SysOpLogService;
import cn.com.shopec.core.system.service.SysParamService;
//import cn.com.shopec.mgt.email.MailInfo;
//import cn.com.shopec.mgt.email.SimpleMailSender;
import cn.com.shopec.core.system.service.SysRoleService;
import cn.com.shopec.core.system.service.SysUserRoleRelService;

public class BaseController {

	@Resource
	HttpServletRequest request;
	@Resource
	public SysOpLogService opLogService;
//	@Resource
//	private SmsTemplateService smsTemplateService;
//	@Resource
//	private MailTemplateService mailTemplateService;
//	@Resource
//	private SendMailService sendMailService;
	@Resource
	private SysParamService sysParamService;
//	@Resource
//	private SendMsgService msgService;
	@Resource
	private SysUserRoleRelService sysUserRoleRelService;
	@Resource
	private SysRoleService sysRoleService;
	
	@InitBinder    
	public void initBinder(WebDataBinder binder) {    
		binder.registerCustomEditor(Date.class, new ECCustomerDateEditor());
//	         binder.registerCustomEditor(int.class, new IntEditor());
//	         binder.registerCustomEditor(long.class, new LongEditor());  
//	         binder.registerCustomEditor(double.class, new DoubleEditor());  
//	         binder.registerCustomEditor(float.class, new FloatEditor());  
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

	/**
	 * 取HttpSession对象
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return request.getSession();
	}

	/**
	 * 设置用户的登录信息
	 * 
	 * @Title: setLoginSysUser
	 * @Description: TODO
	 * @param request
	 * @param sysUser
	 * @return
	 */
	public static boolean setLoginSysUser(HttpServletRequest request, SysUser sysUser) {
		try {
			request.getSession().setAttribute(ECMgtConstant.SESSION_KEY_OF_LOGIN_SYS_USER, sysUser);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 取已登录的系统人员用户对象（数据来自session）
	 * 
	 * @return
	 */
	public SysUser getLoginSysUser() {
		SysUser sysUser = null;
		HttpSession session = getSession();
		if (session != null) {
			sysUser = (SysUser) session.getAttribute(ECMgtConstant.SESSION_KEY_OF_LOGIN_SYS_USER); // 从session中取已登录的系统人员用户对象
		}
		return sysUser;
	}
	/**
	 * 设置用户的角色是否含系统管理员的标识信息
	 * 
	 * @Title: setLoginSysUser
	 * @Description: TODO
	 * @param request
	 * @param sysUser
	 * @return
	 */
	public boolean setLoginSysUserRoleAdmin(HttpServletRequest request,SysUser sysUser) {
		try {
			Integer roleAdminTag=0;
			//判断当前登录用户的角色是不是系统管理员
			if(sysUser!=null){
				List<SysUserRoleRel> list=sysUserRoleRelService.getByUserId(sysUser.getUserId());
				if(list!=null&&list.size()>0){
					SysRole sysRole=new SysRole();
					sysRole.setRoleName("系统管理员");
					List<SysRole> roleList=sysRoleService.queryAll(new Query(sysRole));
					if(roleList!=null&&roleList.size()>0){
						sysRole=roleList.get(0);
						for(SysUserRoleRel surr:list){
							if(surr.getRoleId().equals(sysRole.getRoleId())){
								roleAdminTag=1;
							}
						}
					}
				}
			}
			request.getSession().setAttribute(ECMgtConstant.SESSION_KEY_OF_LOGIN_SYS_ROLE_ADMIN_TAG, roleAdminTag);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 取已登录的系统人员用户角色是否含系统管理员的标识信息（数据来自session）
	 * 
	 * @return
	 */
	public Integer getLoginSysUserRoleAdmin() {
		Integer roleAdminTag=0;
		HttpSession session = getSession();
		if (session != null) {
			roleAdminTag = (Integer) session.getAttribute(ECMgtConstant.SESSION_KEY_OF_LOGIN_SYS_ROLE_ADMIN_TAG); // 从session中取已登录的系统人员用户对象
		}
		return roleAdminTag;
	}
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

	/**
	 * 移除登陆用户
	 * 
	 * @Title: removeSessionAttribut
	 * @Description: TODO
	 * @param request
	 * @param key
	 */
	public static void removeSessionUser(HttpServletRequest request) {
		removeSessionAttribut(request, ECMgtConstant.SESSION_KEY_OF_LOGIN_SYS_USER);

	}

	/**
	 * 将系统平台人员用户对象，转为通用操作员对象
	 * 
	 * @param sysUser
	 * @return
	 */
	public Operator convertSysUser2Operator(SysUser sysUser) {
		Operator op = null;
		if (sysUser != null) {
			op = new Operator(ECCoreConstant.OPERATOR_TYPE_SYS_USER, sysUser.getUserId(), sysUser.getUserName(),
					sysUser.getRealName(), sysUser.getEmail(), sysUser.getMobilePhone());
		}
		return op;
	}

	/**
	 * 取通用操作员对象（数据来自session中的SysUser对象，并转为Operator对象）
	 * 
	 * @return
	 */
	public Operator getOperator() {
		return convertSysUser2Operator(getLoginSysUser());
	}

	/**
	 * 
	 * @param systemType
	 *            系统类型，说明是在哪个系统操作产生的日志（0、运营平台，1、商家后台，2、商城前台）
	 * @param opType
	 *            操作类型（C，新建、U，更新、D，删除、O，其他操作/组合操作）
	 * @param moduleName
	 *            模块名称
	 * @param operatorUserName
	 *            //操作人用户名（根据操作人类型会对应不同的用户名）
	 * @return
	 */
	public ResultInfo<SysOpLog> add(Integer systemType, String opType, String moduleName, String operatorUserName,
			Operator operator) {
		// 日志记录
		SysOpLog sysOpLog = new SysOpLog();
		sysOpLog.setSystemType(systemType);
		sysOpLog.setOpType(opType);
		sysOpLog.setModuleName(moduleName);
		sysOpLog.setOperatorUserName(operatorUserName);
		return opLogService.add(sysOpLog, operator);
	}
	
	 //在模版的第i行、j列上写入 value值
  	public void setColumnValue(HSSFSheet sh,int i,int j,String value) throws Exception
  	{
      	try
      	{
  			HSSFRow row=sh.getRow(i);;
  			HSSFCell cell=row.createCell(j);
  			cell.setCellValue(value);		
      	}catch(Exception e)
  		{
      		e.printStackTrace();
  		}

  	}     
//	/**
//	 * 短息发送
//	 * @param emplateType 模板类型(0系统消息 1注册 2修改密码 3订单通知)
//	 * @param name 发给谁
//	 * @param operation 操作
//	 * @param value 内容&验证码（比如验证码3412）
//	 * @param reason 原因
//	 * @param number 编号
//	 * @param price 价格
//	 * @return
//	 */
//	public String getSmsTemplateType(Integer emplateType, String name, String value, String number,String operation,String price,String reason)
//	{
//		SmsTemplate smsTemplate = smsTemplateService.getSmsTemplateType(emplateType); // 短信模板。
//		String emplateContent = smsTemplate.getEmplateContent();
//		if (name.length() != 0)
//		{
//			emplateContent = emplateContent.replace("{real_name}", name);
//		}
//		if (operation != null && !"".equals(operation))
//		{
//			emplateContent = emplateContent.replace("{operation}", operation);
//		}
//		if (value != null && !"".equals(value))
//		{
//			emplateContent = emplateContent.replace("{value}", value);
//		}
//		if (number != null && !"".equals(number))
//		{
//			emplateContent = emplateContent.replace("{number}", number);
//		}
//		if (price != null && !"".equals(price))
//		{
//			emplateContent = emplateContent.replace("{price}", price);
//		}
//		if (reason != null && !"".equals(reason))
//		{
//			emplateContent = emplateContent.replace("{reason}", reason);
//		}
//		return emplateContent;
//	}
	
//	/**
//	 * 短息发送 - 统一名称
//	 * @param emplateType 模板类型(0系统消息 1注册 2修改密码 3订单通知)
//	 * @param name 发给谁
//	 * @param operation 操作
//	 * @param value 内容&验证码（比如验证码3412）
//	 * @param reason 原因
//	 * @param number 编号
//	 * @param price 价格
//	 * @return
//	 */
//	public String getSmsTemplateTypeForMany(Integer emplateType, List<MemberUser> list, String value, String number,String operation,String price,String reason)
//	{
//		SmsTemplate smsTemplate = smsTemplateService.getSmsTemplateType(emplateType); // 短信模板。
//		String emplateContent = smsTemplate.getEmplateContent();
//		if (operation != null && !"".equals(operation))
//		{
//			emplateContent = emplateContent.replace("{operation}", operation);
//		}
//		if (value != null && !"".equals(value))
//		{
//			emplateContent = emplateContent.replace("{value}", value);
//		}
//		if (number != null && !"".equals(number))
//		{
//			emplateContent = emplateContent.replace("{number}", number);
//		}
//		if (price != null && !"".equals(price))
//		{
//			emplateContent = emplateContent.replace("{price}", price);
//		}
//		if (reason != null && !"".equals(reason))
//		{
//			emplateContent = emplateContent.replace("{reason}", reason);
//		}
//		for (MemberUser memberUser : list) {
//		   if(memberUser.getRealName()!=null && !"".equals(memberUser.getRealName())){
//			   emplateContent = emplateContent.replace("{real_name}", memberUser.getRealName());
//		   }	
////		   //发送短信-发送单条
////		   try {
////			msgService.easyCommonSendMsgs(memberUser, 18, emplateContent, new Operator());
////			} catch (IOException e) {
////				e.printStackTrace();
////			}
//		}
//		
//		return emplateContent;
//	}
	
	
	

	public HttpServletRequest getRequest() {
		return request;
	}
	
//	/**
//	 * 邮件发送
//	 * @param mailType 邮件类型(0系统消息  1注册  2修改密码  3订单通知)
//	 * @param name 发给谁
//	 * @param operation  类型（比如：注册）
//	 * @param value  发送的信息（比如验证码3412）
//	 * @return
//	 */
//	public String getEmailTemplateType(Integer mailType,MemberUser memberUser,String value,String number,String operation,String price,String reason,Operator operator){
//		String a="0";
//		MailTemplate mailTemplate=mailTemplateService.getMailTemplateType(mailType);
//		String emplateContent=mailTemplate.getEmplateContent();
//		MailInfo mailServerInfo = MailInfo.getInstance();
//		if(memberUser.getMemberUserName().length()!=0){
//			emplateContent=emplateContent.replace("{real_name}",memberUser.getRealName());
//		}
//		//设置邮件标题
//		mailServerInfo.setSubject(mailTemplate.getTemplateTitle());
//		
//		if (operation != null && !"".equals(operation))
//		{
//			emplateContent = emplateContent.replace("{operation}", operation);
//		}
//		if (value != null && !"".equals(value))
//		{
//			emplateContent = emplateContent.replace("{value}", value);
//		}
//		if (number != null && !"".equals(number))
//		{
//			emplateContent = emplateContent.replace("{number}", number);
//		}
//		if (price != null && !"".equals(price))
//		{
//			emplateContent = emplateContent.replace("{price}", price);
//		}
//		if (reason != null && !"".equals(reason))
//		{
//			emplateContent = emplateContent.replace("{reason}", reason);
//		}
//		if(memberUser.getEmail()!=null && !"".equals(memberUser.getEmail())){
//			//去发邮件
////			mailServerInfo.getMailProperties();
//			// 判断邮件开关
//			String isOff = sysParamService.getNameByKey("邮件开关").getParamValue();
//			if("1".equals(isOff)){
//			
//				mailServerInfo.setMailServerHost(sysParamService.getNameByKey("emailHost").getParamValue());
//				mailServerInfo.setMailServerPort(sysParamService.getNameByKey("emailPort").getParamValue());
//				mailServerInfo.setFromAddress(sysParamService.getNameByKey("emailAddress").getParamValue());
//				mailServerInfo.setValidate(true);
//				mailServerInfo.setUserName(sysParamService.getNameByKey("emailName").getParamValue());
//				mailServerInfo.setPassword(sysParamService.getNameByKey("emailPassword").getParamValue());
//				mailServerInfo.setToAddress(memberUser.getEmail());
//				mailServerInfo.setContent(emplateContent);
//				SimpleMailSender sms = new SimpleMailSender();
//				sms.sendTextMail(mailServerInfo);// 发送文体格式
//				a="1";
//				//保存在邮件发送列表
//				SendMail sendMail=new SendMail();
//				sendMail.setReceiverType(1);
//				sendMail.setReceiverId(memberUser.getMemberUserId());
//				sendMail.setEmplateId(mailTemplate.getEmpId());
//				sendMail.setReceiverAddr(memberUser.getEmail());
//				sendMail.setMailContent(emplateContent);
//				sendMail.setMailTitle(mailTemplate.getTemplateTitle());
//				sendMailService.addSendMail(sendMail, operator).getCode();
//			}
//		}
//		return a;
//	}
	
//	/**
//	 * 邮件发送-群发
//	 * @param mailType 邮件类型(0系统消息  1注册  2修改密码  3订单通知)
//	 * @param name 发给谁
//	 * @param operation  类型（比如：注册）
//	 * @param value  发送的信息（比如验证码3412）
//	 * @return
//	 */
//	public String getEmailTemplateTypeForMany(Integer mailType,List<MemberUser> memberUsers,String value,String num,String operation,String price,String reason,Operator operator){
//		String aa = "";
//		if(memberUsers !=null){
//			// 判断邮件开关
//			String isOff = sysParamService.getNameByKey("邮件开关").getParamValue();
//			if("1".equals(isOff)){
//				for (MemberUser memberUser : memberUsers) {
//					//调用单条发送方法
//					aa = getEmailTemplateType(mailType,memberUser,value,num,operation,price,reason,operator);
//				}
//			}
//		}
//		return aa;
//	}

}
