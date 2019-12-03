package cn.com.shopec.message.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;

import cn.com.shopec.core.marketing.model.SmsSendRequest;
import cn.com.shopec.core.system.model.SendSms;
import cn.com.shopec.core.system.model.SmsTemplate;
import cn.com.shopec.core.system.service.SendSmsService;
import cn.com.shopec.core.system.service.SmsTemplateService;
import net.sf.json.JSONObject;

/**
 * 云通讯短信接口实现
 */
// @Component("sendMessageLCServiceImpl")
public class SendMessageYTXServiceImpl extends SendMsgCommonInterfaceServiceImpl {

	@Resource
	private SmsTemplateService smsTemplateService;
	@Resource
	private SendSmsService sendsmsService;

	// 简易形式发送短信 不需要登录退出 --参数 UserID Account Password SMSType Content Phones
	// public static final String DIRECT_SEND_SMSS =
	// "http://www.lanz.net.cn/LANZGateway/DirectSendSMSs.asp";

	String userId; // 用户ID
	String account; // 用户账户
	String password; // 用户密码

	@Override
	public boolean sendMsgGet(String phone, String content, String templateType) throws IOException {
		return false;
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

		// 状态报告
		String report = "true";

		try {
			SmsSendRequest smsSingleRequest = new SmsSendRequest(uid, password, sendMsg, phone, report);

			String requestJson = JSON.toJSONString(smsSingleRequest);

			System.out.println("before request string is: " + requestJson);

			String response = sendSmsByPost(url, requestJson);
			if (response.contains("\"code\":\"0\"")) {
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
			// TODO 处理返回值,参见HTTP协议文档
		} catch (Exception e) {
			// TODO 处理异常
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 
	 * @author tianyh @Description @param path @param postContent @return
	 *         String @throws
	 */
	public static String sendSmsByPost(String path, String postContent) {
		URL url = null;
		try {
			url = new URL(path);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("POST");// 提交模式
			httpURLConnection.setConnectTimeout(10000);// 连接超时 单位毫秒
			httpURLConnection.setReadTimeout(2000);// 读取超时 单位毫秒
			// 发送POST请求必须设置如下两行
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type", "application/json");

			// PrintWriter printWriter = new
			// PrintWriter(httpURLConnection.getOutputStream());
			// printWriter.write(postContent);
			// printWriter.flush();

			httpURLConnection.connect();
			OutputStream os = httpURLConnection.getOutputStream();
			os.write(postContent.getBytes("UTF-8"));
			os.flush();

			StringBuilder sb = new StringBuilder();
			int httpRspCode = httpURLConnection.getResponseCode();
			if (httpRspCode == HttpURLConnection.HTTP_OK) {
				// 开始获取数据
				BufferedReader br = new BufferedReader(
						new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				br.close();
				return sb.toString();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
		InputStream in = SendMessageYTXServiceImpl.class.getResourceAsStream("/message.properties");
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
