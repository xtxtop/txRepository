package cn.com.shopec.message.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import cn.com.shopec.core.system.model.SendSms;
import cn.com.shopec.core.system.model.SmsTemplate;
import cn.com.shopec.core.system.service.SendSmsService;
import cn.com.shopec.core.system.service.SmsTemplateService;

/**
 * 
 */
// @Component("sendMessageLCServiceImpl")
public class SendMessageZZServiceImpl extends SendMsgCommonInterfaceServiceImpl {

	@Resource
	private SmsTemplateService smsTemplateService;
	@Resource
	private SendSmsService sendsmsService;

	// 简易形式发送短信 不需要登录退出 --参数 UserID Account Password SMSType Content Phones
//	public static final String DIRECT_SEND_SMSS = "http://www.lanz.net.cn/LANZGateway/DirectSendSMSs.asp";

	String userId; // 用户ID
	String account; // 用户账户
	String password; // 用户密码

	@Override
	public boolean sendMsgGet(String phone, String content, String templateType) throws IOException {
		boolean flag = false;// 发送成功标志

		// 获取账户信息
		String uid = getString("uid");
		String password = getString("upwd");
		String url = getString("usendUrl");

		// 创建请求连接并处理文本内容
		SmsTemplate st = smsTemplateService.getSmsTemplateByTemplateType(Integer.parseInt(templateType));
		String sendMsg = getSmsTemplateTypeContent(st, content);
		String result = "";
		if (phone != null && !"".equals(phone)) {
			result = doGet(url, "id=" + uid + "&pwd=" + password + "&to=" + phone + "&content="
					+ URLEncoder.encode(content, "gb2312") + "&time=" + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			System.out.println(result);
		}

		if (result.contains("000")) {
			flag = true;
			try {
				// 将短信发送的内容存入后台的短信发送列表
				SendSms ss = new SendSms();
				ss.setMobilePhone(phone);
				ss.setSendTime(new Date());
				ss.setSmsContent(sendMsg);
				if (st != null) {
					ss.setTemplateId(Long.parseLong(st.getTemplateId()));
					ss.setTemplateType(st.getTemplateType());
				}
				sendsmsService.addSendSms(ss, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return flag;
	}

	@Override
	public boolean sendMsgPost(String phone, String content, String tplId) throws Exception {
		boolean flag = false;// 发送成功标志
		// 获取账户信息
		String uid = getString("uid");
		String password = getString("upwd");
		String url = getString("usendUrl");

		// 创建请求连接并处理文本内容
		SmsTemplate st = smsTemplateService.getSmsTemplateByTemplateType(Integer.parseInt(tplId));
		String sendMsg = editContent(getSmsTemplateTypeContent(st, content));
		String result = "";
		if (phone != null && !"".equals(phone)) {
			result = doPost(url, "id=" + uid + "&pwd=" + password + "&to=" + phone + "&content="
					+ URLEncoder.encode(sendMsg, "gb2312") + "&time=" + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			System.out.println(result);
		}

		if (result.contains("000")) {
			flag = true;
			try {
				// 将短信发送的内容存入后台的短信发送列表
				SendSms ss = new SendSms();
				ss.setMobilePhone(phone);
				ss.setSendTime(new Date());
				ss.setSmsContent(sendMsg);
				if (st != null) {
					ss.setTemplateId(Long.parseLong(st.getTemplateId()));
					ss.setTemplateType(st.getTemplateType());
				}
				sendsmsService.addSendSms(ss, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return flag;
	}

	/**
	 * 替换短信模板中的变量内容
	 */
	public String getSmsTemplateTypeContent(SmsTemplate st, String content) {
		String emplateContent = st.getTemplateContent();
		String replaceStr = emplateContent.substring(emplateContent.indexOf("{"), emplateContent.lastIndexOf("}") + 1);
		if (content != null && !"".equals(content)) {
			emplateContent = emplateContent.replace(replaceStr, content);
		}
		return emplateContent;
	}

	/**
	 * 获取用户信息
	 */
	public static Map<String, String> getAdminMessage() {
		// 定义userInfo存储账户信息
		Map<String, String> userInfo = new HashMap<String, String>();
		String userId = "";
		String account = "";
		String password = "";
		InputStream in = SendMessageZZServiceImpl.class.getResourceAsStream("/message.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
			userId = prop.getProperty("UserID").trim();
			account = prop.getProperty("Account").trim();
			password = prop.getProperty("Password").trim();

			userInfo.put("UserId", userId);
			userInfo.put("Account", account);
			userInfo.put("Password", password);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userInfo;
	}

	/**
	 * 编辑发送内容
	 */
	public static String editContent(String content) {
		String result = "";
		StringBuffer sub = new StringBuffer();
		String editResult = content.replaceAll("\\s*", "");
		result = sub.append(editResult).toString();
		return result;
	}

	@Override
	public boolean sendMsgGet(String phone, String content, String tplId, String type) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendMsgPost(String phone, String content, String tplId, String type) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public String formatDate(Date date, String format) {
		String s = "";
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			s = sdf.format(date);
		}

		return s;
	}

}
