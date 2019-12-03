package cn.com.shopec.common.authentication;

import java.util.Map;

/**
 * 认证接口
 * @author Administrator
 *
 */
public interface AuthenticationService {
	public Map<String, Object> validationCard(String imgBase64Str, String type);
	public Map<String, Object> validationCardMember(String memberName, String idCard);
	public boolean sendMsgGetMember(String memberName, String idCard, String appkey);
	public boolean sendMsgGet( String idCard,String licenseId, String appkey);
	public Map<String, Object> validationCardCar(String idCard, String licenseId);
	
	

}
