package cn.com.shopec.core.member.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.Coupon;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberCountVo;
import cn.com.shopec.core.member.model.MemberOneDayVO;
import cn.com.shopec.core.order.model.PricingPackOrder;


/**
 * Member 服务接口类
 */
public interface MemberService extends BaseService {

	/**
	 * 根据查询条件，查询并返回Member的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<Member> getMemberList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回Member的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<Member> getMemberPagedList(Query q);
	/**
	 *根据查询条件，分页查询并返回Member的分页结果（包含余额）
	 * @param q
	 * @return
	 */
	public PageFinder<Member> getMemberBalancePagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个Member对象
	 * @param id 主键id
	 * @return
	 */
	public Member getMember(String id);

	/**
	 * 根据主键数组，查询并返回一组Member对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<Member> getMemberByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的Member记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Member> delMember(String id, Operator operator);
	
	/**
	 * 新增一条Member记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param member 新增的Member数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Member> addMember(Member member, Operator operator);
	
	/**
	 * 根据主键，更新一条Member记录
	 * @param member 更新的Member数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Member> updateMember(Member member, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	
	public String generatePK();
	
	/**
	 * 为Member对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(Member obj);
	
	/**
	* 验证注册手机号的唯一性
	* */
	public Member getMemberByPhone(String phone);
	
	/**
	 * 根据会员表主键，取的会员的常用基础信息
	 * @param memberNo
	 * @return
	 */
	public Member getMemberBasicInfo(String memberNo);
	
	/**
	 * 得到认证状态
	 */
	public ResultInfo<Integer> getCensorStatus(String memberNo);

	/**上传驾照*/
	public ResultInfo<String> uploadLicense(String memberNo, String idCard, String memberName);
	
	/**
	 * 查询不是集团会员，确在集团人员里面的会员信息
	 * @return
	 */
	public ResultInfo<Member> getMemberCompany(Query q);

	/**
	 * 会员申请退款
	 * @param memberNo
	 * @return
	 */
	public ResultInfo<Member> payRefund(String memberNo,String out_refund_no,String refundNo);
/**
 * 获取驾驶证过期的用户并修改审核状态为未审核
 * */
	public void getDriverLicense(Date nowDate);
/**
 * 获取驾驶证过期还有几天过期的用户，并发送消息
 * */
public void getDriverLicenseOneDay(Date nowDateOneDay,Integer days);

public ResultInfo<List<PricingPackOrder>> availablePackageList(String memberNo, int pageNo, int pageSize);

public ResultInfo<List<PricingPackOrder>> disablePackageList(String memberNo, int pageNo, int pageSize);
//根据 邀请码  查出  推荐人Id
public Member getInvitationCode(String invitationCode);
//根据推荐人id  查出推荐人的用户名 真实姓名 手机号码  电话号码
public Member getInvitationNamePhone(String id);
	/**
	 * 根据token获取会员
	 * @param token
	 * @return
	 */
	public Member getMemberByToken(String token);
	
	
	/**
	 * 根据请求时间查询出会员的相关数据
	 * @param startTime   当天开始时间  0分0秒
	 * @param endTime	请求时间
	 * @return MemberOneDayVO
	 */
	public MemberOneDayVO getIndexValue(String startTime, String endTime);
	/**
	 * 根据请求时间查询出会员认证待审核的
	 */
	public Integer getTodoIndexValue();

	/**
	 * 会员运营数据统计-日
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<MemberCountVo> memberOperateCount(Date endTime, int dayParmaeter);

	/**
	 * 会员运营数据统计-周
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<MemberCountVo> weekMemberOperateCount(Date startTime,Date endTime, int weekParmaeter);

	/**
	 * 会员运营数据统计-月
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<MemberCountVo> monthMemberOperateCount(Date startTime,Date endTime, int memberParmaeter);
 
	/**
	 * 会员运营数据统计-年
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<MemberCountVo> yearMemberOperateCount(Date startTime,Date endTime, int yearParmaeter);
	//获取优惠券
	public ResultInfo<List<Coupon>> memberCouponList(String memberNo, int pageNo, int pageSize, Integer type);

	public PageFinder<Member> getMemberAccountAmount(Query q);
	//获取 未审核
	public List<Member> getMemberListCenStatus();
	
	
	
	//根据审核状态统计会员总数
	Integer countMemberByCensorStatus(Integer censorStatus);
	
	/**
	 * 获取近10天的会员数
	 * 
	 * @return
	 */
	Map<String, Object> getMemberDay10CountMap();
	
	Long count(Query q);

	

	public ResultInfo<String> uploadLicenseAndUrl(String memberNo, String idCard, String memberName, String pathUrl1,
			String pathUrl2, String imgPaths, String  licenseId, String type, String paperName, String paperUrl,String enddate);

	public Member getIdCardMember(String idCard);


	/**
	 * 更新会员消费总额
	 * 
	 * @param memberNo
	 * @param payableAmount
	 */
	public void updateMemberRealAmount(String memberNo, double payableAmount);

 
}
