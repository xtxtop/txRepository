package cn.com.shopec.common.sendmsg;

import java.io.IOException;

import cn.com.shopec.common.Operator;
import cn.com.shopec.core.quartz.model.RunDaily;

/**
 * 浪驰短信推送接口
 * @author Administrator
 *
 */
public interface SendMsgService {
	
	/**
	 * 简易版发送单条短信	--  无需登录
	 * @throws IOException 
	 */
	public String easySendMsg(String phone,String content) throws IOException;
	
	/**
	 * 群发	-- 无需登录 
	 * @param phones  要发送的手机号们
	 * @param content  要发送的内容
	 * @return
	 * @throws IOException
	 */
	public String easySendMsgs(String[] phones,String content) throws IOException;
	/**
	 * 获取已回复客户相关信息
	 * @throws IOException
	 */
	public String queryReplyUserInfo() throws IOException;
	/**
	 * 公共群发	-- 无需登录 
	 * @param members  要发送的人列表
	 * @param content  要发送的内容 
	 * @param receiverType 接收人类型（1、商家，2、会员）
	 * @return
	 * @throws IOException
	 */
//	public String easyCommonSendMsgs(MemberUser members,int receiverType,String content,Operator op) throws IOException;


	public String easySendMsgTemplate(String mobilePhone, String validCode, Integer i,Operator op) throws IOException;
	
	public String easySendMsgTemplate(String mobilePhone,String replaceStr, String content, Integer i,Operator op) throws IOException;

	String easySendMsgTemplate(String phone, RunDaily rd, Integer templateType, Operator operator) throws IOException;
	
	/**
	 * 短信群发
	 * @throws IOException 
	 * @throws Exception 
	 */
	//public String sendManyMsg(String[] phones) throws Exception;
	
	

	/**
	 * 登录
	 * @throws IOException 
	 */
	//public String login() throws IOException;
	
	/**
	 * 发送单条短信  -- 登录有发送
	 * @throws IOException 
	 */
	//public String sendOneMsg(String phone) throws IOException;
	

}
