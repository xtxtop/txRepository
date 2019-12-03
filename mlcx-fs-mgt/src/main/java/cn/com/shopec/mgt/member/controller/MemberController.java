package cn.com.shopec.mgt.member.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.sendmsg.MsgConstant;
import cn.com.shopec.common.sendmsg.SendMessage;
import cn.com.shopec.common.sendmsg.SendMsgCommonInterfaceService;
import cn.com.shopec.common.sendmsg.SendMsgService;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECStringUtils;
import cn.com.shopec.common.zmxy.ZhimaUtil;
import cn.com.shopec.core.car.service.CarIllegalService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.service.DepositOrderService;
import cn.com.shopec.core.member.model.Company;
import cn.com.shopec.core.member.model.CompanyPerson;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberLevel;
import cn.com.shopec.core.member.model.MemberZhimaScore;
import cn.com.shopec.core.member.service.CompanyPersonService;
import cn.com.shopec.core.member.service.CompanyService;
import cn.com.shopec.core.member.service.MemberLevelService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.member.service.MemberZhimaScoreService;
import cn.com.shopec.core.member.service.impl.MemberZhimaScoreServiceImpl;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.system.dao.SysUserDao;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 会员管理
 * 
 * @author machao
 *
 */
@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {
	
	private static final Log log = LogFactory.getLog(MemberZhimaScoreServiceImpl.class);

	@Resource
	private MemberService memberService;
	@Resource
	private CompanyPersonService companyPersonService;
	@Resource
	private DepositOrderService depositOrderService;
	@Resource
	private OrderService orderService;
	@Resource
	private CarIllegalService carIllegalService;
	@Resource
	private CompanyService companyService;
	@Resource
	private MemberLevelService memberLevelService;
	@Resource
	private SendMsgService msgService;
	@Resource
	private SendMessage sendMsg;
	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private SysUserService sysUserService;

	@Value("${base_path}")
	private String basePath;
	 @Value("${tpl_centor_id}")
	 private String tplId;

	@Resource
	private SendMsgCommonInterfaceService sendMsgCommonInterfaceService;

	/**
	 * 进入客户操作员列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toMemberList")
	public String toMemberList(String memberName, String cencorStatus, ModelMap model) {
		Query q = new Query();
		MemberLevel memberLevel = new MemberLevel();
		memberLevel.setIsAvailable(1);
		memberLevel.setIsDeleted(0);
		q.setQ(memberLevel);
		List<MemberLevel> memberLevelList = memberLevelService.getMemberLevelList(q);
		model.addAttribute("memberLevelList", memberLevelList);
		model.addAttribute("memberName", memberName);
		model.addAttribute("cencorStatus", cencorStatus);
		model.addAttribute("roleAdminTag",getLoginSysUserRoleAdmin()); // 权限
		return "/member/member_list";
	}

	/**
	 * 通过待办事项 进入客户操作员列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toMemberListTodo")
	public String toMemberListTodo(ModelMap model) {
		Query q = new Query();
		MemberLevel memberLevel = new MemberLevel();
		memberLevel.setIsAvailable(1);
		memberLevel.setIsDeleted(0);
		q.setQ(memberLevel);
		List<MemberLevel> memberLevelList = memberLevelService.getMemberLevelList(q);
		model.addAttribute("memberLevelList", memberLevelList);
		Integer censorStatus = 2;
		model.addAttribute("censorStatus", censorStatus);
		model.addAttribute("roleAdminTag",getLoginSysUserRoleAdmin()); // 权限
		return "/member/member_list";
	}

	/**
	 * 查询会员列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageListMember")
	@ResponseBody
	public PageFinder<Member> pageListMember(@ModelAttribute("member") Member member,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) throws ParseException {
		Query q = new Query(pageNo, pageSize, member);
		PageFinder<Member> memberPage =  memberService.getMemberBalancePagedList(q);
		return memberPage;
	}

	/**
	 * 会员详情
	 * 
	 * @param memberNo
	 * @return
	 */
	@RequestMapping("toMemberView")
	public String toMemberView(@ModelAttribute("memberNo") String memberNo, Model model) {
		Member member = memberService.getMember(memberNo);
		if (member.getMemberType() != null && member.getMemberType() == 2) {
			CompanyPerson companyPerson = new CompanyPerson();
			companyPerson.setMemberNo(member.getMemberNo());
			List<CompanyPerson> list = companyPersonService.getCompanyPersonList(new Query(companyPerson));
			if (list != null && list.size() > 0) {
				model.addAttribute("companyPerson", list.get(0));
			}
		}
		// 剩余押金
		Double residueDeposit = depositOrderService.getAmountByMemberNo(memberNo);
		member.setResidueDeposit(residueDeposit);
		// 欠款金额
		Double noPayAmount = orderService.getAmountByMemberNo(memberNo);
		member.setNoPayAmount(noPayAmount);
		// 会员订单数
		Long orderNum = orderService.getOrderNumByMemberNo(memberNo);
		member.setOrderNum(orderNum);
		// 违章记录数
		Long illegalNum = carIllegalService.getIllegalNumByMemberNo(memberNo);
		member.setIllegalNum(illegalNum);
		if (member.getMemberLevelId() != null && !member.getMemberLevelId().equals("")) {
			MemberLevel memberLevel = memberLevelService.getMemberLevel(member.getMemberLevelId());
			if (memberLevel != null) {
				member.setLevelName(memberLevel.getLevelName());
			}
		}
		if (member.getMemberType().equals(2)) {
			if (member.getCompanyId() != null && !member.getCompanyId().equals("")) {
				Company company = companyService.getCompany(member.getCompanyId());
				if (company != null) {
					member.setCompanyName(company.getCompanyName());
				}
			}
		}

		// 昵称memberNick为空的时候，显示为姓名
		if ("".equals(member.getMemberNick()) || member.getMemberNick() == null) {
			member.setMemberNick(member.getMemberName());
		}
		Member memberReferee = memberService.getInvitationNamePhone(member.getRefereeId());
		SysUser sysUser = sysUserDao.get(member.getCencorId());
		model.addAttribute("member", member);
		model.addAttribute("sysUser", sysUser);
		model.addAttribute("memberReferee", memberReferee);
		Member mb = new Member();
		mb.setRefereeId(member.getMemberNo());
		Query q = new Query(mb);
		List<Member> mbs = memberService.getMemberList(q);
		if (mbs != null && mbs.size() > 0) {
			model.addAttribute("mbs", mbs.size());
		} else {
			model.addAttribute("mbs", 0);
		}
		return "/member/member_view";
	}

	/**
	 * 会员页面
	 * 
	 * @param memberNo
	 * @return
	 */
	@RequestMapping("toUpdateMember")
	public String toUpdateMember(@ModelAttribute("memberNo") String memberNo, Model model) {
		Member member = memberService.getMember(memberNo);
		if (member.getMemberType() != null && member.getMemberType() == 2) {
			CompanyPerson companyPerson = new CompanyPerson();
			companyPerson.setMemberNo(member.getMemberNo());
			;
			List<CompanyPerson> list = companyPersonService.getCompanyPersonList(new Query(companyPerson));
			if (list != null && list.size() > 0) {
				model.addAttribute("companyPerson", list.get(0));
			}
		}
		// 剩余押金
		Double residueDeposit = depositOrderService.getAmountByMemberNo(memberNo);
		member.setResidueDeposit(residueDeposit);
		// 欠款金额
		Double noPayAmount = orderService.getAmountByMemberNo(memberNo);
		member.setNoPayAmount(noPayAmount);
		// 会员订单数
		Long orderNum = orderService.getOrderNumByMemberNo(memberNo);
		member.setOrderNum(orderNum);
		// 违章记录数
		Long illegalNum = carIllegalService.getIllegalNumByMemberNo(memberNo);
		member.setIllegalNum(illegalNum);
		// 会员等级名称
		if (member.getMemberLevelId() != null && !member.getMemberLevelId().equals("")) {
			MemberLevel memberLevel = memberLevelService.getMemberLevel(member.getMemberLevelId());
			if (memberLevel != null) {
				member.setLevelName(memberLevel.getLevelName());
			}
		}

		// 昵称memberNick为空的时候，显示为姓名
		if ("".equals(member.getMemberNick()) || member.getMemberNick() == null) {
			member.setMemberNick(member.getMemberName());
		}
		model.addAttribute("member", member);

		// 审核通过后的集团列表
		Query q = new Query();
		Company company = new Company();
		company.setCencorStatus(3);// 已审核
		q.setQ(company);
		List<Company> companyList = companyService.getCompanyList(q);
		// 邀请人数
		Member mb = new Member();
		mb.setRefereeId(member.getMemberNo());
		Query qs = new Query(mb);
		List<Member> mbs = memberService.getMemberList(qs);
		if (mbs != null && mbs.size() > 0) {
			model.addAttribute("mbs", mbs.size());
		} else {
			model.addAttribute("mbs", 0);
		}
		model.addAttribute("companyList", companyList);
		return "/member/member_edit";
	}

	/**
	 * 会员修改
	 * 
	 * @param member
	 * @return
	 */
	@RequestMapping("updateMember")
	@ResponseBody
	public ResultInfo<Member> updateMember(@ModelAttribute("member") Member member) {
		if (member.getCompanyId() != null && member.getCompanyId() != "") {
			member.setMemberType(2);
		} else {
			member.setMemberType(1);
			member.setCompanyId("");
		}
		return memberService.updateMember(member, getOperator());
	}

	/**
	 * 会员审核页面
	 * 
	 * @param memberNo
	 * @return
	 */
	@RequestMapping("toCencorMember")
	public String toCencorMember(@ModelAttribute("memberNo") String memberNo, Model model) {
		Member member = memberService.getMember(memberNo);
		int typeUp = 0;
		int typeDown = 0;
		// 昵称memberNick为空的时候，显示为姓名
		if ("".equals(member.getMemberNick()) || member.getMemberNick() == null) {
			member.setMemberNick(member.getMemberName());
		}
		// 查找所有未审核的会员
		List<Member> members = memberService.getMemberListCenStatus();
		if (members != null && members.size() > 0) {
			if (members.size() != 1) {
				for (int i = 0; i < members.size(); i++) {
					if (members.get(i).getMemberNo().equals(memberNo)) {
						if (i == 0) {
							int s = i + 1;
							typeDown = 1;
							Member mb = members.get(s);
							model.addAttribute("memberNextNo", mb.getMemberNo());// 下一个
						} else if (i == members.size() - 1) {
							typeUp = 1;
							int s = i - 1;
							Member mr = members.get(s);
							model.addAttribute("memberLowNo", mr.getMemberNo());// 上一个

						} else {
							int s = i + 1;// 下一个坐标
							int n = i - 1;// 上一个坐标
							typeUp = 1;
							typeDown = 1;
							Member mb = members.get(s);
							model.addAttribute("memberNextNo", mb.getMemberNo());// 下一个
							Member mr = members.get(n);
							model.addAttribute("memberLowNo", mr.getMemberNo());// 上一个
						}

					}
				}
			} else {

			}

		}
		model.addAttribute("typeUp", typeUp);
		model.addAttribute("typeDown", typeDown);
		model.addAttribute("member", member);
		return "/member/member_cencor";
	}

	/**
	 * 会员审核
	 * 
	 * @param member
	 * @return
	 */
	@RequestMapping("cencorMember")
	@ResponseBody
	public ResultInfo<Member> cencorMember(@ModelAttribute("member") Member member) {
		ResultInfo<Member> rs = new ResultInfo<Member>();
		// 会员审核通过、不通过，发送短信给用户
		try {
			String censorStatus = "";
			Date now = new Date();
			member.setCencorTime(now);

			if (member.getCensorStatus().equals(1)) {// 已审核
				censorStatus = "已通过";
			} else if (member.getCensorStatus().equals(3)) {// 未通过
				censorStatus = "未通过";
			}
			SysUser user = getLoginSysUser();
			Member m = memberService.getMember(member.getMemberNo());
			if (user != null) {
				member.setCencorId(user.getUserId());
			}

			if (member.getCensorStatus() == 1) {
				if (member.getExpirationDate().getTime() > new Date().getTime()) {
					rs = memberService.updateMember(member, getOperator());
				} else {
					rs.setCode("0");
					rs.setMsg("驾照有效期小于当前时间 请重新输入");
					return rs;
				}
			}
			if (member.getCensorStatus() == 3) {
				rs = memberService.updateMember(member, getOperator());
			}
			if (null != rs && rs.getCode().equals("1") && null != m && m.getMobilePhone() != null
					&& !m.getMobilePhone().equals("")) {
				boolean sate = false;
				try {
					String phoneNo = m.getMobilePhone();
					String msg = "";
					if (member.getCensorStatus().equals(1)) {// 已审核
						msg = censorStatus;
					} else if (member.getCensorStatus().equals(3)) {// 未通过
						msg = censorStatus + ",原因：" + member.getCencorMemo();
					}
					// 聚合
//					 sate = sendMsgCommonInterfaceService.sendMsgPost(phoneNo,
//					 censorStatus, tplId);
					// 聚通达,浪驰,中正世纪融合通信，云通讯
					sate = sendMsgCommonInterfaceService.sendMsgPost(phoneNo, msg, MsgConstant.MEMBERCENCORTYPE);

					// 叮咚云
					// sate = sendMsgCommonInterfaceService.sendMsgPost(phoneNo,
					// censorStatus, "","2"); //2代表 通知类的短信
					System.err.println("发送结果！！！！！！！！！！！！！！" + sate);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 会员移入移出黑名单
	 * 
	 * @param member
	 * @return
	 */
	@RequestMapping("toBlacklist")
	@ResponseBody
	public ResultInfo<Member> toBlacklist(@ModelAttribute("member") Member member) {
		return memberService.updateMember(member, getOperator());
	}

	/*
	 * 异步查询member对象
	 */
	@RequestMapping("getMemberByMemberNo")
	@ResponseBody
	public ResultInfo<Member> getMemberByMemberNo(@ModelAttribute("memberNo") String memberNo) {
		ResultInfo<Member> resultInfo = new ResultInfo<Member>();
		Member member = null;
		if (memberNo != null && memberNo.length() != 0) {
			member = memberService.getMember(memberNo);
		}
		if (member != null) {
			resultInfo.setCode("1");
			resultInfo.setData(member);
		}
		return resultInfo;
	}

	@RequestMapping("getMemberCompany")
	@ResponseBody
	public ResultInfo<Member> getMemberCompany(Query q) {

		return memberService.getMemberCompany(q);
	}

	@RequestMapping("toMemberExport")
	public void toMemberExport(@ModelAttribute("member") Member member, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Query q = new Query();
			q.setQ(member);
			List<Member> list = memberService.getMemberList(q);
			// 声明一个工作薄
			String path = request.getRealPath("/") + "res" + File.separator + "member.xls";
			File ff = new File(path);
			InputStream is = new FileInputStream(ff);
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			// 生成一个表格
			HSSFSheet sheet = workbook.getSheetAt(0);
			int i = 1;
			for (Member memberData : list) {
				sheet.createRow(i);
				// 性别（0、女，1、男）
				String sex = ECStringUtils
						.parseStrNull(memberData.getSex() == null ? "" : memberData.getSex().toString());
				if (sex.equals("0")) {
					sex = "女";
				} else if (sex.equals("1")) {
					sex = "男";
				} else {
					sex = "";
				}
				// 会员类型（1、普通会员，2、集团会员）
				String memberType = ECStringUtils
						.parseStrNull(memberData.getMemberType() == null ? "" : memberData.getMemberType().toString());
				String companyName = "";
				if (memberType.equals("1")) {
					memberType = "普通会员";
				} else if (memberType.equals("2")) {
					memberType = "集团会员";
					if (memberData.getCompanyId() != null && !memberData.getCompanyId().equals("")) {
						Company company = companyService.getCompany(memberData.getCompanyId());
						if (company != null) {
							companyName = company.getCompanyName();
						}
					}
				} else {
					memberType = "";
				}
				// 会员等级
				// String
				// levelName=ECStringUtils.parseStrNull(memberData.getLevelName()==null?"":memberData.getLevelName().toString());

				// 认证情况（0、未审核/未认证，1、已审核/已认证，2、待审核/待认证，3、未通过，默认0）
				String censorStatus = ECStringUtils.parseStrNull(
						memberData.getCensorStatus() == null ? "" : memberData.getCensorStatus().toString());
				if (censorStatus.equals("0")) {
					censorStatus = "未认证";
				} else if (censorStatus.equals("1")) {
					censorStatus = "已认证";
				} else if (censorStatus.equals("2")) {
					censorStatus = "待认证";
				} else {
					censorStatus = "未通过";
				}

				// 状态（0、非黑名单，1、黑名单，默认0）
				String isBlacklist = ECStringUtils.parseStrNull(
						memberData.getIsBlacklist() == null ? "" : memberData.getIsBlacklist().toString());
				if (isBlacklist.equals("0")) {
					isBlacklist = "正常";
				} else {
					isBlacklist = "黑名单";
				}
				// 是否加盟（0、未加盟，1、已加盟，2、加盟申请中，默认0）
				/*
				 * String
				 * isJoined=ECStringUtils.parseStrNull(memberData.getIsJoined()=
				 * =null?"":memberData.getIsJoined().toString());
				 * if(isJoined.equals("1")){ isJoined="已加盟"; }else
				 * if(isJoined.equals("2")){ isJoined="加盟申请中"; }else{
				 * isJoined="未加盟"; }
				 */
				setColumnValue(sheet, i, 0, ECStringUtils.toStringForNull(memberData.getMemberNo()));
				setColumnValue(sheet, i, 1, ECStringUtils.toStringForNull(memberData.getMemberName()));
				setColumnValue(sheet, i, 2, ECStringUtils.toStringForNull(memberData.getMobilePhone()));
				setColumnValue(sheet, i, 3, ECStringUtils.toStringForNull(sex));
				setColumnValue(sheet, i, 4, ECStringUtils.toStringForNull(memberData.getIdCard()));
				setColumnValue(sheet, i, 5, ECStringUtils.toStringForNull(memberData.getInvitationCode()));
				setColumnValue(sheet, i, 6, ECStringUtils.toStringForNull(memberType));
				setColumnValue(sheet, i, 7, ECStringUtils.toStringForNull(memberData.getLevelName()));
				setColumnValue(sheet, i, 8, ECStringUtils.toStringForNull(censorStatus));
				setColumnValue(sheet, i, 9, ECStringUtils.toStringForNull(isBlacklist));
				setColumnValue(sheet, i, 10, ECStringUtils.toStringForNull(companyName));
				setColumnValue(sheet, i, 11, ECDateUtils.formatTime(memberData.getCreateTime()));
				// setColumnValue(sheet,i,9,ECStringUtils.toStringForNull(isJoined));
				i++;
				// System.out.println("--------------"+memberData+"\n");
			}
			response.reset();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=member.xls");
			ServletOutputStream os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close(); // 关闭流
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	// 查看图片旋转
	@RequestMapping("getImg")
	public String getImg(String imgSrc, ModelMap model) {
		model.addAttribute("imgSrc", imgSrc);
		model.addAttribute("basePath", basePath);
		return "/member/member_img_show";
	}

	// 会员订单列表
	@RequestMapping("getMemberOrder")
	public String getMemberOrder(@ModelAttribute("memberNo") String memberNo, ModelMap model) {
		Member member = memberService.getMember(memberNo);
		model.addAttribute("member", member);
		return "/member/member_order";
	}

	// 会员跳转订单（未支付已完成）
	@RequestMapping("getMemberPayAmountOrder")
	public String getMemberPayAmountOrder(@ModelAttribute("memberNo") String memberNo, ModelMap model) {
		Member member = memberService.getMember(memberNo);
		model.addAttribute("member", member);
		model.addAttribute("orderStatus", 3);
		model.addAttribute("payStatus", 0);
		return "/member/member_order";
	}

	// 查看邀请人的列表
	@RequestMapping("toMemberListMbs")
	public String toMemberListMbs(@ModelAttribute("refereeId") String refereeId, ModelMap model) {
		model.addAttribute("refereeId", refereeId);
		return "/member/member_list";
	}

	// 会员未支付订单列表
	@RequestMapping("getMemberNoPayOrder")
	public String getMemberNoPayOrder(@ModelAttribute("memberNo") String memberNo, ModelMap model) {
		Member member = memberService.getMember(memberNo);
		model.addAttribute("member", member);
		return "/member/member_notPay_order";
	}

	// 会员违章记录列表
	@RequestMapping("getMemberCarIllegal")
	public String getMemberCarIllegal(@ModelAttribute("memberNo") String memberNo, ModelMap model) {
		Member member = memberService.getMember(memberNo);
		model.addAttribute("member", member);
		return "/member/member_car_illegal_list";
	}
	
}
