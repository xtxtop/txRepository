package cn.com.shopec.common.sendmsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import cn.com.shopec.common.Operator;
import cn.com.shopec.core.quartz.model.RunDaily;
import cn.com.shopec.core.system.model.SendSms;
import cn.com.shopec.core.system.model.SmsTemplate;
import cn.com.shopec.core.system.service.SendSmsService;
import cn.com.shopec.core.system.service.SmsTemplateService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 发送短信实现类
 * 注：目前此类中发送短信的方式为两种：单发和群发、只需要调用两个方法即可。其他方法为未完善功能、请勿动
 * @author Administrator
 *
 */
@Service
public class SendMessage implements SendMsgService{
	
	private static final Log log = LogFactory.getLog(SendMessage.class);
	
	@Resource
	SmsTemplateService smsTemplateService;
	@Resource
	SendSmsService sendsmsService;
		//登录   	--参数  UserID  Account  Password
		public static final String LOGIN = "http://www.lanz.net.cn/LANZGateway/Login.asp";
		//注销	--参数  ActiveID 登录时获得的ActiveID
		public static final String LOGOFF = "http://www.lanz.net.cn/LANZGateway/Logoff.asp";
		//服务器网管连接状态设置	--参数	ActiveID
		public static final String HEART_BEAT = "http://www.lanz.net.cn/LANZGateway/HeartBeat.asp";
		//查询当前账户可发送短信数量	--参数	ActiveID
		public static final String GET_SMS_STOCK = "http://www.lanz.net.cn/LANZGateway/GetSMSStock.asp";
		//发送单条短信 	--参数	ActiveID SMSType Phone Content	
		public static final String SEND_SMS = "http://www.lanz.net.cn/LANZGateway/SendSMS.asp";
		//群发短信申请		--参数	ActiveID SMSType Content 
		public static final String MESS_SMS_QUERY = "http://www.lanz.net.cn/LANZGateway/MessSMSQuery.asp";
		//群发短信	--参数	ActiveID JobID Phones	
		public static final String SEND_MESS_SMS = "http://www.lanz.net.cn/LANZGateway/SendMessSMS.asp";
		//获取短信发送的结果	--参数	ActiveID
		public static final String FETCH_SEND_STAT = "http://www.lanz.net.cn/LANZGateway/FetchSendStat.asp";
		//接收回复的短信	--参数	ActiveID
		public static final String FETCH_SMS = "http://www.lanz.net.cn/LANZGateway/DirectFetchSMS.asp";
		//简易形式发送短信 不需要登录退出   --参数  UserID Account Password SMSType Content Phones 
		public static final String DIRECT_SEND_SMSS	= "http://www.lanz.net.cn/LANZGateway/DirectSendSMSs.asp";
		
		//测试发送短信内容
		public static final String MESSAGE_TEST = "你好、这里是我的地盘在测试";
		@Resource
		private SysParamService sysParamService;
//		@Resource
//		private SendSmsDao sendSmsDao;
		
		//落款
		public static final String INSCRIBED = "【行知出行】";
		
		static String[] sessionId;		//session数组
		static String Activeid;		//登录凭证 -- 登录后返回凭证
		static String session_value;	//session
		
		String userId;	//用户ID
		String account;	//用户账户
		String password;	//用户密码
		
		
		/**
		 * 发送单条短信
		 */
		@Override
		public String easySendMsg(String phone,String content) throws IOException {
			//获取账户信息
			Map<String,String> userInfo = getAdminMessage();	
			userId = userInfo.get("UserId");
			account = userInfo.get("Account");
			password = userInfo.get("Password");
			
			//创建请求连接并处理文本内容
			String sendMsg = "";
			URL url = new URL(DIRECT_SEND_SMSS);
			sendMsg = INSCRIBED+editContent(content);
			String result="";
			if(phone!=null && !"".equals(phone)){
				HttpURLConnection sendMsgConnection = (HttpURLConnection)url.openConnection();
				result = doMsgPost(url,sendMsgConnection,"UserID="+userId+"&Account="+account+"&Password="+password+"&Phones="+phone+"&Content="+sendMsg);
			}
			return result;
		}
		@Override
		public String easySendMsgTemplate(String phone,String validCode,Integer templateType,Operator operator) throws IOException {
			//获取账户信息
			Map<String,String> userInfo = getAdminMessage();	
			userId = userInfo.get("UserId");
			account = userInfo.get("Account");
			password = userInfo.get("Password");
			
			//创建请求连接并处理文本内容
			String sendMsg = "";
			URL url = new URL(DIRECT_SEND_SMSS);
			SmsTemplate st=smsTemplateService.getSmsTemplateByTemplateType(templateType);
			sendMsg = INSCRIBED+editContent(getSmsTemplateTypeContent(st,phone,"{validCode}",validCode));
			String result="";
			if(phone!=null && !"".equals(phone)){
				HttpURLConnection sendMsgConnection = (HttpURLConnection)url.openConnection();
				result = doMsgPost(url,sendMsgConnection,"UserID="+userId+"&Account="+account+"&Password="+password+"&Phones="+phone+"&Content="+sendMsg);
			}
			try{
				//将短信发送的内容存入后台的短信发送列表
				SendSms ss=new SendSms();
				ss.setMobilePhone(phone);
				ss.setSendTime(new Date());
				ss.setSmsContent(sendMsg);
				ss.setTemplateId(Long.parseLong(st.getTemplateId()));
				ss.setTemplateType(st.getTemplateType());
				sendsmsService.addSendSms(ss, operator);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return result;
		}
		
		@Override
		public String easySendMsgTemplate(String phone,String replaceStr,String content,Integer templateType,Operator operator) throws IOException {
			//获取账户信息
			Map<String,String> userInfo = getAdminMessage();	
			userId = userInfo.get("UserId");
			account = userInfo.get("Account");
			password = userInfo.get("Password");
			
			//创建请求连接并处理文本内容
			String sendMsg = "";
			URL url = new URL(DIRECT_SEND_SMSS);
			SmsTemplate st=smsTemplateService.getSmsTemplateByTemplateType(templateType);
			sendMsg = INSCRIBED+editContent(getSmsTemplateTypeContent(st,phone,replaceStr,content));
			String result="";
			if(phone!=null && !"".equals(phone)){
				HttpURLConnection sendMsgConnection = (HttpURLConnection)url.openConnection();
				result = doMsgPost(url,sendMsgConnection,"UserID="+userId+"&Account="+account+"&Password="+password+"&Phones="+phone+"&Content="+sendMsg);
			}
			try{
				//将短信发送的内容存入后台的短信发送列表
				SendSms ss=new SendSms();
				ss.setMobilePhone(phone);
				ss.setSendTime(new Date());
				ss.setSmsContent(sendMsg);
				ss.setTemplateId(Long.parseLong(st.getTemplateId()));
				ss.setTemplateType(st.getTemplateType());
				sendsmsService.addSendSms(ss, operator);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return result;
		}
		//运行日报
		@Override
		public String easySendMsgTemplate(String phone,RunDaily rd,Integer templateType,Operator operator) throws IOException {
			//获取账户信息
			Map<String,String> userInfo = getAdminMessage();	
			userId = userInfo.get("UserId");
			account = userInfo.get("Account");
			password = userInfo.get("Password");
			
			//创建请求连接并处理文本内容
			String sendMsg = "";
			URL url = new URL(DIRECT_SEND_SMSS);
			SmsTemplate st=smsTemplateService.getSmsTemplateByTemplateType(templateType);
			String emplateContent = st.getTemplateContent();
			emplateContent = emplateContent.replace("{memberNum}",rd.getMemberNum().toString());
			emplateContent = emplateContent.replace("{memberFeeNum}",rd.getMemberFeeNum().toString());
			emplateContent = emplateContent.replace("{memberCensoredNum}",rd.getMemberCensoredNum().toString());
			emplateContent = emplateContent.replace("{curOnlineCarsNum}",rd.getCurOnlineCarsNum().toString());
			emplateContent = emplateContent.replace("{registNum}",rd.getRegistNum().toString());
			emplateContent = emplateContent.replace("{orderNum}",rd.getOrderNum().toString());
			emplateContent = emplateContent.replace("{alPayOrderNum}",rd.getAlPayOrderNum().toString());
			emplateContent = emplateContent.replace("{payAmount}",rd.getPayAmount().toString());
			emplateContent = emplateContent.replace("{time}",rd.getTime().toString());
			sendMsg = INSCRIBED+editContent(emplateContent);
			String result="";
			if(phone!=null && !"".equals(phone)){
				HttpURLConnection sendMsgConnection = (HttpURLConnection)url.openConnection();
				result = doMsgPost(url,sendMsgConnection,"UserID="+userId+"&Account="+account+"&Password="+password+"&Phones="+phone+"&Content="+sendMsg);
				log.info("RunDaily sms,result="+result);
			}
			try{
				//将短信发送的内容存入后台的短信发送列表
				SendSms ss=new SendSms();
				ss.setMobilePhone(phone);
				ss.setSendTime(new Date());
				ss.setSmsContent(sendMsg);
				ss.setTemplateId(Long.parseLong(st.getTemplateId()));
				ss.setTemplateType(st.getTemplateType());
				sendsmsService.addSendSms(ss, operator);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return result;
		}
		/**
		 * 根据短信模板获取所要发送的内容
		 * */
		public String getSmsTemplateTypeContent(SmsTemplate st, String phone,String replaceStr, String validCode)
		{
			String emplateContent = st.getTemplateContent();
			if (validCode != null && !"".equals(validCode))
			{
				emplateContent = emplateContent.replace(replaceStr, validCode);
			}
			return emplateContent;
		}
		/**
		 * 发送多条短信
		 */
		@Override
		public String easySendMsgs(String[] phones,String content) throws IOException {
			//获取账户信息
			Map<String,String> userInfo = getAdminMessage();
			userId = userInfo.get("UserId");
			account = userInfo.get("Account");
			password = userInfo.get("Password");
			//数组字符转换
			StringBuffer tels = new StringBuffer();
		        for(int i=0;i<phones.length;i++){
		        	if(phones[i] != null && !"".equals(phones[i])){
		        		if(i == phones.length-1){
		        			tels.append(phones[i]);
		        			//tels.append("185979");
		        		}else{
		        			tels.append(phones[i]+";");
		        			//tels.append("1879"+";");
		        		}
		        	}
		        }
		        
		    //创建请求连接并处理文本内容
			URL url = new URL(DIRECT_SEND_SMSS);
			String sendMsg = editContent(content);
			HttpURLConnection sendMsgConnection = (HttpURLConnection)url.openConnection();
			String result = doMsgPost(url,sendMsgConnection,"UserID="+userId+"&Account="+account+"&Password="+password+"&Phones="+tels.toString()+"&Content="+sendMsg);
			return result;
		}
		
//		/**
//		 * 发送多条短信--公共方法
//		 */
//		@Override
//		public String easyCommonSendMsgs(MemberUser member,int smsType,String content,Operator op) throws IOException {
//	    	
//	    	//String aa=getSmsTemplateType(0,"尊敬的芯动商城客户","拍卖单号："+orderNo+"审核成功！");
//			//判断短信开关是否打开
//			String isOff = sysParamService.getNameByKey("短信开关").getParamValue();
//			if("1".equals(isOff)){
//				String result=easySendMsg(member.getMobilePhone(),content);
//		    	//插入发短信记录
//		    		SendSms sendSms = new SendSms();
//		    		sendSms.setSmsId(System.currentTimeMillis()); 
//		    		sendSms.setReceiverId(member.getMemberId());
//		    		sendSms.setMobilePhone(member.getMobilePhone());
//		    		sendSms.setSmsContent(content);
//		    		sendSms.setSendTime(new Date());
//		    		sendSms.setEmplateType(smsType);
//		    		// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
//					if (op != null) {
//						sendSms.setOperatorType(op.getOperatorType());
//						sendSms.setOperatorId(op.getOperatorId());
//					}
//
//					// 设置创建时间和更新时间为当前时间
//					Date now = new Date();
//					sendSms.setCreateTime(now);
//					sendSms.setUpdateTime(now);
//		    		
//		    		sendSmsDao.add(sendSms); 
//		    	return result;
//			}
//	    	
//			return "";
//		}
		
		
		/**
		 * 获取用户信息
		 */
		public static Map<String,String> getAdminMessage(){
			//定义userInfo存储账户信息
			Map<String,String> userInfo = new HashMap<String,String>();
			String userId = "";
			String account = "";
			String password = "";
			InputStream in = SendMessage.class.getResourceAsStream("/lanz-info.properties");
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
		public static String editContent(String content){
			String result = "";
			StringBuffer sub = new StringBuffer();
			String editResult = content.replaceAll("\\s*", ""); 
			result = sub.append( editResult).toString();
			return result;
		}
		
		/**
		 * 登录
		 * @throws IOException 
		 */
		public static String login() throws IOException {
			Map<String,String> userInfo = getAdminMessage();	//获取账户信息
			String userId = userInfo.get("UserId");
			String account = userInfo.get("Account");
			String password = userInfo.get("Password");
			//登录
			URL url = new URL(LOGIN);
			HttpURLConnection loginConnection = (HttpURLConnection)url.openConnection();
			String sTotalString = doMsgPost(url,loginConnection,"UserID="+userId+"&Account="+account+"&Password="+password);
			
			//获取session信息
			session_value = loginConnection.getHeaderField("Set-Cookie");
			sessionId = session_value.split(";");
			Activeid = sTotalString.substring(82,98);	//获取登录凭证
			
			return sTotalString;
		}

		/**
		 * 注销
		 * @throws IOException 
		 */
		public static String logOff() throws IOException{
			//注销
			URL url = new URL(LOGOFF);
			HttpURLConnection loginConnection = (HttpURLConnection)url.openConnection();
			String sTotalString = doMsgPost(url,loginConnection,"ActiveID=6306136697568467");
			return sTotalString;
		}
		
		/**
		 * 群发短信申请
		 * @param Activeid
		 * @throws IOException 
		 */
		public static String getJobId(String Activeid) throws IOException{
			URL url = new URL(MESS_SMS_QUERY);
			HttpURLConnection queryConnection = (HttpURLConnection)url.openConnection();
			queryConnection.setRequestProperty( "Cookie", sessionId[0]);
			String result = doMsgPost(url,queryConnection,"ActiveID="+Activeid+"&Content="+MESSAGE_TEST);
			return result;
		}
		
		/**
		 * 发送单条短信  --  登录后发送
		 * @throws IOException 
		 */
		public static String sendOneMsg(String phone) throws IOException {
			URL url = new URL(SEND_SMS);
			HttpURLConnection sendOneConnection = (HttpURLConnection)url.openConnection();
			sendOneConnection.setRequestProperty( "Cookie", sessionId[0]);
			String result = doMsgPost(url,sendOneConnection,"ActiveID="+Activeid+"&Phone="+phone+"&Content="+MESSAGE_TEST);
			return result;
		}

		/**
		 * 简易发送单条短信
		 * @throws IOException 
		 */
		public static String easySendOne(String phone) throws IOException {
			Map<String,String> userInfo = getAdminMessage();	//获取账户信息
			String userId = userInfo.get("UserId");
			String account = userInfo.get("Account");
			String password = userInfo.get("Password");
			URL url = new URL(DIRECT_SEND_SMSS);
			HttpURLConnection sendOneConnection = (HttpURLConnection)url.openConnection();
			sendOneConnection.setRequestProperty( "Cookie", sessionId[0]);
			String result = doMsgPost(url,sendOneConnection,"UserID="+userId+"&Account="+account+"&Password="+password+"&Phones="+phone+"&Content="+MESSAGE_TEST);
			return result;
		}

		/**
		 * 短信群发	首先申请群发任务
		 * 注意：因接口返回值类型无法取值、此处jobId暂定为虚拟值。
		 * @throws IOException 
		 */
		public String sendManyMsg(String[] phones) throws Exception {
			String jobId = getJobId(Activeid);
			URL url = new URL(SEND_MESS_SMS);
			HttpURLConnection sendConnection = (HttpURLConnection)url.openConnection();
			sendConnection.setRequestProperty( "Cookie", sessionId[0]);
			String result = doMsgPost(url,sendConnection,"ActiveID="+Activeid+"&JobID="+jobId+"&phones="+phones);
			return result;
		}
		
		
		
		
		/**
		 * 通用实现
		 */
		public static String doMsgPost(URL url,HttpURLConnection connection,String message) throws IOException{
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
		    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "GB2312");
			out.write(message);
			out.flush();
			out.close();
			
			String sCurrentLine = "";
			String sTotalString = "";
			InputStream l_urlStream;
			l_urlStream = connection.getInputStream();
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));
			while ((sCurrentLine = l_reader.readLine()) != null) 
			{
				sTotalString += sCurrentLine + "\r\n";
			}
			System.out.println(sTotalString);
			return sTotalString;
		}
		
		/**
		 * 获取发送回复信息的客户信息
		 * 返回JSON串
		 */
		@Override
		public String queryReplyUserInfo() throws IOException {
			//获取账户信息
			Map<String,String> userInfo = getAdminMessage();	
			userId = userInfo.get("UserId");
			account = userInfo.get("Account");
			password = userInfo.get("Password");
		    //创建请求连接并处理文本内容
			URL url = new URL(FETCH_SMS);
			HttpURLConnection sendMsgConnection = (HttpURLConnection)url.openConnection();
			String result = doMsgPost(url,sendMsgConnection,"UserID="+userId+"&Account="+account+"&Password="+password+"&ReturnXJ=1");
			return result;
		}
		

}
