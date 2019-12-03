package cn.com.shopec.common.sendmsg;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface SendMsgCommonInterfaceService {
	/**
	 * 发送接口
	 */
	public String doPost(String urlStr,String param) throws IOException;
	
	/**
	 * 发送接口
	 */
	public String doGet(String urlStr,String param) throws IOException;
	
	/**
	 * 发送内容get方法
	 * @param phone 手机号
	 * @param content 传的变量内容
	 * @param tplId 模板id(聚合传值为聚合后台的模板id，浪驰和聚通达为后台配置的模板类型id)
	 * @return
	 * @throws Exception
	 */
	public boolean sendMsgGet(String phone,String content,String tplId)throws Exception;
	/**
	 * 发送内容post方法
	 * @param phone 手机号
	 * @param content 传的变量内容
	 * @param tplId 模板id模板id(聚合传值为聚合后台的模板id，浪驰和聚通达为后台配置的模板类型id)
	 * @return
	 * @throws Exception
	 */
	public boolean sendMsgPost(String phone,String content,String tplId)throws Exception;
	/**
	 * 获取短信接口的配置文件的路径
	 */
	public String getPath() throws UnsupportedEncodingException;
	/**
	 * 根据键值读取配置文件信息
	 * @return
	 */
	public String getString(String key);
	/**
	 * 读取全部配置文件信息
	 * @return
	 */
	public Map<String, String> getPropertiesParams();
	
	/**
	 * 20170613新增叮咚云短信平台，重载短信发送方法，新加形参 type 代表业务流程
	 * 发送内容get方法
	 * @param phone 手机号
	 * @param content 传的变量内容
	 * @param tplId 可为空（即代表指定模板发送、不建议使用此）
	 * @param type 业务流程环节（1、注册/忘记密码获取验证码     2、用户认证资料审核结果通知      3、车辆非正常订单警告     4、营销短信）
	 * @return
	 * @throws Exception
	 */
	public boolean sendMsgGet(String phone,String content,String tplId,String type)throws Exception;
	/**
	 * 20170613新增叮咚云短信平台，重载短信发送方法，新加形参 type 代表业务流程
	 * 发送内容post方法
	 * @param phone 手机号
	 * @param content 传的变量内容
	 * @param tplId 可为空（即代表指定模板发送、不建议使用此）
	 * @param type 业务流程环节（1、注册/忘记密码获取验证码     2、用户认证资料审核结果通知      3、车辆非正常订单警告     4、营销短信）
	 * @return
	 * @throws Exception
	 */
	public boolean sendMsgPost(String phone,String content,String tplId,String type)throws Exception;
	
	
}
