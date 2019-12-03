package cn.com.shopec.mgt.member.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECStringUtils;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.service.CarIllegalService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.service.DepositOrderService;
import cn.com.shopec.core.member.model.Company;
import cn.com.shopec.core.member.model.CompanyPerson;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberLevel;
import cn.com.shopec.core.member.model.MemberPointsRecord;
import cn.com.shopec.core.member.model.MemberPointsRule;
import cn.com.shopec.core.member.service.CompanyPersonService;
import cn.com.shopec.core.member.service.CompanyService;
import cn.com.shopec.core.member.service.MemberLevelService;
import cn.com.shopec.core.member.service.MemberPointsRecordService;
import cn.com.shopec.core.member.service.MemberPointsRuleService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 会员积分记录管理
 * 
 * @author zln
 *
 */
@Controller
@RequestMapping("/memberPointsRecord")
public class MemberPointsRecordController extends BaseController {

	@Resource
	private MemberPointsRecordService memberPointsRecordService;
	
	@Resource
	private MemberService memberService;
	
	@Value("${base_path}")
	private String basePath;
	/**
	 * 进入会员积分记录列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toMemberPointsRecord")
	public String toMemberPointsRecord() {
		return "/member/member_points_record_list";
	}

	/**
	 * 查询会员积分记录列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageListMemberPointsRecord")
	@ResponseBody
	public PageFinder<MemberPointsRecord> pageListMemberPointsRecord(@ModelAttribute("memberPointsRecord") MemberPointsRecord memberPointsRecord,@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) throws ParseException {
		Query q = new Query(pageNo, pageSize, memberPointsRecord);
		return memberPointsRecordService.getMemberPointsRecordPagedList(q);
	}

	/**
	 * 会员积分规则详情
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("toMemberPointsRecordView")
	public String toMemberPointsRecordView(String recordId, ModelMap model) {
		MemberPointsRecord memberPointsRecord = memberPointsRecordService.getMemberPointsRecord(recordId);
		if(memberPointsRecord.getMemberNo()!=null&&!memberPointsRecord.getMemberNo().equals("")){
			Member member = memberService.getMember(memberPointsRecord.getMemberNo());
			if(member!=null){
				memberPointsRecord.setMemberName(member.getMemberName());
			}
		}
		model.addAttribute("memberPointsRecord", memberPointsRecord);
		return "/member/member_points_record_view";
	}
}
