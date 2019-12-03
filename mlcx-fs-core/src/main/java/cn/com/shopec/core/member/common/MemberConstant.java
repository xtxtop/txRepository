package cn.com.shopec.core.member.common;

public class MemberConstant {
	
	public static final String fail_code = "0";
	public static final String success_code = "1";
	public static final String phone_code = "2";
	public static final String password_code = "3";
	
	/**已注册*/
	public static final String already_register_code = "-1";
	public static final String already_register_msg = "已注册";
	
	/**系统错误*/
	public static final String fail_msg = "系统错误";
	
	/**检测账户是否注册*/
	public static final String fail_msg_phone = "该账户未注册";
	
	/**登陆*/
	public static final String login_fail_msg = "用户名或密码错误！请重新输入";
	public static final String login_success_msg = "登录成功";

	/**
	 * 会员基础信息在缓存中的过期时间（秒数）
	 */
	public static final int EXPIRES_TIME_OF_MEMBER_BASIC_INFO_IN_CACHE = 60 * 60;
	
	/**图片上传*/
	public static final String photo_seccess_msg = "图片上传成功";
	
	
	/**验证密码*/
	public static final String fail_psd_chek_code = "-1";
	public static final String fail_psd_chek_msg ="原密码输入错误";
	
	/**密码修改成功*/
	public static final String success_update_psd_msg = "密码修改成功";
	
	/**手机号已注册*/
	public static final String phone_exsit_msg = "手机号已注册";
	/**手机号已存在*/
	public static final String phone_exsit1_msg = "手机号已存在";
	/**手机号不存在*/
	public static final String phone_exsit2_msg = "手机号不存在";
	/**手机号未注册*/
	public static final String phone_exsit3_msg = "手机号未注册";
	/**验证码输入有误*/
	public static final String ver_code_msg = "验证码输入有误";
	
	/**会员姓名验证*/
	public static final String MEMBER_NO_EXISTENCE = "请输入正确的姓名";
	
	/**是否加盟*/
	public static final String join_success_code = "1";
	public static final String join_success_msg = "申请成功";
	
	public static final String joined_code = "2";
	public static final String joined_msg = "您已加盟";
	
	public static final String joined_shengqi_code = "3";
	public static final String joined_shengqi_msg = "加盟申请中";
	
	/**输入验证码	 */
	public static final String null_code_code = "3";
	public static final String null_code_msg = "验证码为空";
	
	public static final String fail_code_code = "4";
	public static final String fail_code_msg = "验证码错误";
	
	/**各个验证码的有效时长，以秒为单位*/
	public static final int regCodeExpire = 120;	//注册验证码的过期时间为120秒；
	
	/**本地账户丢失，请先登录*/
	public static final String fail_lose_code = "2";
	public static final String fail_lose_msg = "请先登录";
	
	/**预付费*/
	public static final String PREPAYMENT = "DEPOSIT_AMOUNT";
	
	/**身份证验证*/
	public static final String fail_idCard_Check = "请输入正确的身份证号码";
	
	/**会员姓名验证*/
	public static final String fail_memberName_Check = "请输入正确的姓名";
	
	/**移入黑名单*/
	public static final String member_isBlackList = "该会员被移入黑名单，不能进行下单操作";
	
	/**
	 * 首页控制面板展示的会员相关信息
	 */
	public static final String NEW_REGISTER = "NEW_REGISTER";	//今日注册
	public static final String NEW_SIGN = "NEW_SIGN";	//缴费认证
	public static final String NO_CENSOR_STATUS = "NO_CENSOR_STATUS";	//会员认证待审核

	/** 会员运营统计 日统计天数 的key*/
	public static final String day_parameter_key= "DAYPARAMETER";

	/** 会员运营统计 周统计 的key*/
	public static final String week_parameter_key= "WEEKPARAMETER";

	/** 会员运营统计 月统计天数 的key*/
	public static final String month_parameter_key= "MONTHPARAMETER";

	/** 会员运营统计 年统计天数 的key*/
	public static final String year_parameter_key= "YEARPARAMETER";
	
	/**图片上传*/
	public static final String validation_card_fail_msg = "证件验证失败";
}
