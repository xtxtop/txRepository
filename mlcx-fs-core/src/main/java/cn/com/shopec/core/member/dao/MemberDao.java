package cn.com.shopec.core.member.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberCompanyPerson;
import cn.com.shopec.core.member.model.MemberCountVo;
import cn.com.shopec.core.member.model.MemberDepositOrder;

/**
 * Member 数据库处理类
 */
public interface MemberDao extends BaseDao<Member,String> {

/**
 * 判断注册手机号的唯一性
 * */
	Member getByPhone(String phone);
	
	/**
	 * 根据会员表主键，取的会员的常用基础信息
	 * @param memberNo
	 * @return
	 */
	public Member getMemberBasicInfo(String memberNo);
	
	/**
	 * 查询不是集团会员，确在集团人员里面的会员信息
	 * @return
	 */
	public List<MemberCompanyPerson> getMemberCompany(Query q);

	/**
	 * 查询申请退款会员信息
	 * @param memberNo
	 * @return
	 */
	public MemberDepositOrder payRefund(String memberNo);
/**
 * 后台会员列表方法
 * */
	List<Member> getMemberPagedList(Query q);
/**
 * 后台会员列表总数
 * */
long getMemberPagedListCount(Query q);
/**
 * 运行日报获取会员注册总数
 * */
Long getMemberNum(Date now);
/**
 * 运行日报获取缴费会员数
 * */
Long getMemberFeeNum(Date date1);
/**
 * 运行日报获取日新注册人数
 * */
Long getRegistNum(Date date1,Date date2);
/**
 * 获取截止某一天已认证的会员的总数	
 * @param date
 * @return
 */
Long getCensoredMemberNum(Date date);
//根据 邀请码  查出 推荐人 
public Member getInvitationCode(String invitationCode);
//根据推荐人id  查出推荐人的用户名 真实姓名 手机号码  电话号码
public Member getInvitationNamePhone(String id);

   /**
	 * 根据token获取会员
	 * @param token
	 * @return
	 */
	Member getMemberByToken(String token);

	/**
	 * 查询出指定时间段内的全部会员
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<Member> getmemberListByTime(String startTime, String endTime);
	//根据请求查询出会员认证 待审核的
	Integer countMemberCensorStatus();

	public Integer getmemberListByTime1(String startTime, String endTime);
	
	public Integer getmemberListByTime2(String startTime, String endTime);
	
	public Integer getmemberDepositAmountByTime(String startTime, String endTime);
	
	public Integer getmemberRefundAmountByTime(String startTime, String endTime);

	public List<MemberCountVo> getmemberDepositAmountByTimeMonth(Date startTime, Date endTime);
	public List<MemberCountVo> getmemberRefundAmountByTimeMonth(Date startTime, Date endTime);
	public List<MemberCountVo> getmemberDepositAmountByTimeYear(Date startTime, Date endTime);
	public List<MemberCountVo> getmemberRefundAmountByTimeYear(Date startTime, Date endTime);
	
	public List<MemberCountVo> getmemberListByMonth1(Date startTime, Date endTime);
	public List<MemberCountVo> getmemberListByMonth2(Date startTime, Date endTime);
	
	public List<Member> getMemberAccountAmount(Query q);
	public Integer countMemberAccountAmount(Query q);

	public List<Member> getMemberListCenStatus();
	
	//根据审核状态统计会员总数
	Integer countMemberByCensorStatus(Integer censorStatus);
	
	/**
	 * 获取近10天的注册会员数
	 * 
	 * @return
	 */	
	List<Map<String, Object>> getRegisterDay10CountMap();
	
	/**
	 * 获取近10天的认证会员数
	 * 
	 * @return
	 */	
	List<Map<String, Object>> getCencorDay10CountMap();

	/**
	 * 根据查询条件，分页查询并返回Member的分页结果（包含余额）
	 * @param q
	 * @return
	 */
	List<Member> getMemberBalancePagedList(Query q);

	Member getIdCardMember(String idCard);
}
