package cn.com.shopec.mapi.common.email;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class MailInfo {
	
	private MailInfo(){}
	private static final class MailServerConfigEg{
		private static final MailInfo mailserverInfo = new MailInfo();
	}
	
	public static MailInfo getInstance(){
		return MailServerConfigEg.mailserverInfo;
	}
	
	private String mailServerHost;
	
	private String mailServerPort;
	
	private boolean isValidate;
	
	// 邮件发送者的地址
	private String fromAddress;
	
	// 邮件接收者的地址
	private String toAddress;
	
	// 登陆邮件发送服务器的用户名和密码
	private String userName;
	private String password;
	
	// 邮件主题
	private String subject;
	// 邮件的文本内容
	private String content;
	// 邮件附件的文件名
	private String[] attachFileNames;
	
	public Properties getMailProperties(){
		InputStream inStream = null ;
		Properties prop = new Properties();
		try {
			File file = new File(getPath()+"mail.properties");
			inStream = new BufferedInputStream(new FileInputStream(file));
			
			prop.load(inStream);
			this.mailServerHost = prop.getProperty("mail.smtp.host");
			this.mailServerPort = prop.getProperty("mail.smtp.port");
			if ("false".equalsIgnoreCase(prop.getProperty("mail.smtp.auth"))){
				this.isValidate = false;
			}else{
				this.isValidate = true;
			}
			this.fromAddress = prop.getProperty("fromAddress");
			this.userName = prop.getProperty("userName");
			this.password = prop.getProperty("password");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if (null != inStream){
				try {
					inStream.close();
					inStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
	
	public static String getPath() throws IOException {
		String configPath = MailInfo.class.getClassLoader()
				.getResource("/").getPath();
		return java.net.URLDecoder.decode(configPath,"utf-8");
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public boolean isValidate() {
		return isValidate;
	}

	public void setValidate(boolean isValidate) {
		this.isValidate = isValidate;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(String[] attachFileNames) {
		this.attachFileNames = attachFileNames;
	}
	
	
}
