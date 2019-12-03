package cn.com.shopec.common.constants;

/**
 * 开发框架基础架构的公共常量设置类（行知网络使用此基础框架的项目均可使用此常量类）
 * 
 * @author xieyong
 *
 */
public class Constant {

	/** 整个应用0表示失败 */
	public static final String FAIL = "0";
	/** 整个应用1表示成功 */
	public static final String SECCUESS = "1";
	/** 整个应用3表示无数据 */
	public static final String OTHER = "3";
	/** 有未支付订单 */
	public static final String NOPAY = "4";
	/** 有未结束订单 */
	public static final String NOFINSH = "5";
	/** 有未结算订单 */
	public static final String NOSETTLEMENT = "5";
	/** 余额不足 */
	public static final String NOT_SUFFICIENT_FUNDS = "9";
	/** 未登录 */
	public static final String NO_LOGIN = "-1";
	/** 未授权 */
	public static final String NO_AUTHORITY = "-2";
	/**地锁不可用**/
	public static final String NO_OCCUPY="99";
	/** 整个应用0表示否 */
	public static final int NO = 0;
	/** 整个应用1表示是 */
	public static final int YES = 1;

	/** 待审核 */
	public static final int CENSORED_STATE_NO = 0;
	/** 审核通过 */
	public static final int CENSORED_STATE_PASS = 1;
	/** 审核不通过 */
	public static final int CENSORED_STATE_NOPASS = 2;

	/** 禁用 */
	public static final int DISABLED = 0;
	/** 启用 */
	public static final int ENABLED = 1;

	/** 不删除 */
	public static final int DEL_STATE_NO = 0;
	/** 已删除 */
	public static final int DEL_STATE_YES = 1;

	/** 默认的分页每页记录数 */
	public static final int DEFAULT_PAGE_SIZE = 10;

	/** 错误信息，参数无效或不合要求 */
	public static final String ERR_MSG_INVALID_ARG = "Invalid argument.";

	/** 错误信息，捕获到异常 */
	public static final String ERR_MSG_EXCEPTION_CATCHED = "Exception catched.";

	/** 错误信息，数据不存在 */
	public static final String ERR_MSG_DATA_NOT_EXISTS = "Data not exists.";

	/** 错误重复，数据已存在 */
	public static final String ERR_MSG_DATA_ALEARD_EXISTS = "Data already exists";

	/** 数据查询参数设置 */
	public static final int START_WITH_ZERO = 0;
	public static final int NUM_FOUR = 4;
	public static final int NUM_TEN = 10;

	/** 上传图片路径设置 */
	/*
	 * member_icon （会员头像） member_doc （会员证件） park_photo （场站照片） car_photo （车辆照片）
	 * car_doc （车辆证件） tax_photo(纳税证) feedback_photo（反馈图片） advert_photo(活动图片)
	 */
	public static final String MEMBER_ICON = "member_icon";

	public static final String MEMBER_DOC = "member_doc";

	public static final String PARK_PHOTO = "park_photo";

	public static final String CAR_PHOTO = "car_photo";

	public static final String CAR_DOC = "car_doc";

	public static final String TAX_PHOTO = "tax_photo";

	public static final String FEEDBACK_PHOTO = "feedback_photo";

	public static final String ADVERT_PHOTO = "advert_photo";

	public static final String COUPONPLAN_PHOTO = "couponPlan_photo";

	public static final String ITEM_PHOTO = "item_photo";

	public static final String DEVICE_PHOTO = "device_photo";

	public static final String DEVICE_MP4 = "device_mp4";

	public static final String ORDERSHARE_PHOTO = "orderShare_photo";
	/** 暂无记录 */
	public static final String NO_RECORD = "暂无记录";

	/** 驾驶证有效期距离到期时间天数，发送消息给会员 */
	public static final String driver_license_over_date = "DRIVER_LICENSE_OVER_DATE";

	/** 支付宝转账签名内容 */
	public static final String TRANS_SIGN_CONTENT = "LCGX_TO_BE_ONE";

	/** 系统参数配置的失效时间 */
	public static final String SYS_FAILURE_TIME = "SYS_FAILURE_TIME";

	/** 客户端加密参数KEY */
	public static final String ANDROID_KEY = "TJFS_ANDROID_2017_001";
	public static final String IOS_KEY = "TJFS_IOS_2017_002";

	/** 加盟商收益类型 */
	public static final int PROFIT_CAR_TYPE = 0;
	public static final int PROFIT_PARK_TYPE = 1;
	public static final int PROFIT_TYPE = 2;

	/******************** 以下是猛龙广告TYPE类型分类 **************************************/
	/** 广告中大类型区分(1、总首页) */
	public static final Integer TYPE_1 = 1;
	/** 广告中大类型区分（2、充电桩） */
	public static final Integer TYPE_2 = 2;
	/** 广告中大类型区分（3、无人停车） */
	public static final Integer TYPE_3 = 3;
	/** 广告中大类型区分（4、充电站列表） */
	public static final Integer TYPE_4 = 4;
	/** 广告中大类型区分（5、个人中心） */
	public static final Integer TYPE_5 = 5;
	/** 广告中大类型区分（6、地锁订单列表） */
	public static final Integer TYPE_6 = 6;
	/** 广告中大类型区分（7、地锁订单详情表） */
	public static final Integer TYPE_7 = 7;
	/** 广告中大类型区分（8、地锁订单评论表） */
	public static final Integer TYPE_8 = 8;
	/** 广告中大类型区分（9、地锁订单列表） */
	public static final Integer TYPE_9 = 9;
	/** 广告中大类型区分（10、地锁订单详情表） */
	public static final Integer TYPE_10 = 10;
	/** 广告中大类型区分（11、地锁订单评论表） */
	public static final Integer TYPE_11 = 11;
	/** 广告中大类型区分（12、我的钱包） */
	public static final Integer TYPE_12 = 12;
	/** 广告中大类型区分（13、充值记录） */
	public static final Integer TYPE_13 = 13;
	/** 广告中大类型区分（14、添加爱车） */
	public static final Integer TYPE_14 = 14;
	/** 广告中大类型区分（15、充电站详情） */
	public static final Integer TYPE_15 = 15;
	/** 广告中大类型区分（16、停车场详情） */
	public static final Integer TYPE_16 = 16;
	/** 广告中大类型区分（17、停车场列表） */
	public static final Integer TYPE_17 = 17;
	/** 广告中大类型区分（18、预约停车） */
	public static final Integer TYPE_18 = 18;

	/******************* 以下是我的钱包余额充值的最低限制 *****************************/
	/** 会员余额充电我的钱包最低余额 */
	public static final Double CHARGE_AMOUNT = 50d;
	/** 会员余额地锁（停车）我的钱包最低余额 */
	public static final Double STOP_AMOUNT = 50d;
	/** 充电订单中的服务费，默认为5元 */
	public static final Double SERVICE_FEE = 5d;
}
