package cn.com.shopec.core.common;

/**
 * 电商核心业务-公共常量设置类
 * @author xieyong
 *
 */
public class ECCoreConstant {
	
	/**业务操作人员类型-系统自动操作（如定时任务等）*/
	public static final int OPERATOR_TYPE_SYS_AUTO = 0; 
	
	/**业务操作人员类型-系统平台人员*/
	public static final int OPERATOR_TYPE_SYS_USER = 1;
	
	/**业务操作人员类型-商家用户（建议以后不使用此类型，均改为用OPERATOR_TYPE_MEMBER_USER//0610,lule）*/
	@Deprecated
	public static final int OPERATOR_TYPE_SELLER_USER = 2;
	
	/**业务操作人员类型-买家会员（建议以后不使用此类型，均改为用OPERATOR_TYPE_MEMBER_USER//0610,lule）*/
	@Deprecated
	public static final int OPERATOR_TYPE_BUYER_MEMBER = 3;
	
	/**业务操作人员类型-客户会员用户*/
	public static final int OPERATOR_TYPE_MEMBER_USER = 4;
	/**邮箱验证*/
	public static final String SIGNATURE = "one|health";
	/**修改邮箱请求地址 */
	public static final String CHANGEEMAILURL = "reviewRequestUrl.do?key=";


	
	
}
