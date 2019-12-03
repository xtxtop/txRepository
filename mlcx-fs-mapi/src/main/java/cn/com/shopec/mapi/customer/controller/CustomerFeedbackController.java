package cn.com.shopec.mapi.customer.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.customer.model.CustomerFeedback;
import cn.com.shopec.core.customer.service.CustomerFeedbackService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.resource.model.ParkOpening;
import cn.com.shopec.core.resource.service.ParkOpeningService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.customer.vo.MemberFeedbackListVo;
import cn.com.shopec.mapi.order.vo.OrderVO;

@Controller
@RequestMapping("/app/customerFeedback")
public class CustomerFeedbackController extends BaseController{
	
	@Resource
	private CustomerFeedbackService customerFeedbackService;
	@Resource
	private MemberService memberService;
	@Resource
	private ParkOpeningService parkOpeningService;
	@Resource
	private SysParamService  sysParamService;
	
	@Value("${image_path}")
	private String imgPath;
		/**
		 *问题反馈（添加）
		 * */
		@RequestMapping("/addCustomerFeedback")
		@ResponseBody
		public ResultInfo<CustomerFeedback> addCustomerFeedback(CustomerFeedback customerFeedback) {
//			//标题，内容，图片需要传值
//			customerFeedback.setTitle("车辆清洁问题");
//			customerFeedback.setContent("建议定期进行维护并检查");
//			customerFeedback.setPhotoUrl1("http://local:img/lkl.jpg");
			if(customerFeedback.getPhotoUrl1()!=null&&!customerFeedback.getPhotoUrl1().equals("")){
				customerFeedback.setPhotoUrl1(customerFeedback.getPhotoUrl1().substring(imgPath.length()+1));
			}
			if(customerFeedback.getPhotoUrl2()!=null&&!customerFeedback.getPhotoUrl2().equals("")){
				customerFeedback.setPhotoUrl2(customerFeedback.getPhotoUrl2().substring(imgPath.length()+1));
			}
			if(customerFeedback.getPhotoUrl3()!=null&&!customerFeedback.getPhotoUrl3().equals("")){
				customerFeedback.setPhotoUrl3(customerFeedback.getPhotoUrl3().substring(imgPath.length()+1));
			}
			Member member=getLoginMember();
			customerFeedback.setFeedbackType(1);//反馈类型（问题反馈：1）
			customerFeedback.setCustomerType(1);//反馈人类型（1：会员）
			customerFeedback.setMemberNo(member.getMemberNo());
			customerFeedback.setMemberName(member.getMemberName());
			customerFeedback.setMobilePhone(member.getMobilePhone());
			return customerFeedbackService.addCustomerFeedback(customerFeedback, getOperator());
		}
		
		
		
		/**
		 *意见反馈（添加）
		 * */
		@RequestMapping("/addMemberFeedback")
		@ResponseBody
		public ResultInfo<CustomerFeedback> addMemberFeedback(CustomerFeedback customerFeedback) {
//			if(customerFeedback.getPhotoUrl1()!=null&&!customerFeedback.getPhotoUrl1().equals("")){
//				customerFeedback.setPhotoUrl1(customerFeedback.getPhotoUrl1().substring(imgPath.length()+1));
//			}
//			if(customerFeedback.getPhotoUrl2()!=null&&!customerFeedback.getPhotoUrl2().equals("")){
//				customerFeedback.setPhotoUrl2(customerFeedback.getPhotoUrl2().substring(imgPath.length()+1));
//			}
//			if(customerFeedback.getPhotoUrl3()!=null&&!customerFeedback.getPhotoUrl3().equals("")){
//				customerFeedback.setPhotoUrl3(customerFeedback.getPhotoUrl3().substring(imgPath.length()+1));
//			}
			
			Member member=getLoginMember();
			customerFeedback.setFeedbackType(3);//反馈类型（问题反馈：1）
			customerFeedback.setCustomerType(1);
			//反馈人类型（1：会员）
			if( member!= null){
				customerFeedback.setMemberName(member.getMemberName());
				customerFeedback.setMobilePhone(member.getMobilePhone());
				customerFeedback.setMemberNo(member.getMemberNo());
			}
			
			return customerFeedbackService.addCustomerFeedback(customerFeedback, getOperator());
		}
		
		/**
		 *意见反馈（添加）
		 * */
		@RequestMapping("/addParkOpening")
		@ResponseBody
		public ResultInfo<ParkOpening> addParkOpening(String memberNo,String memo) {
			ParkOpening p=new ParkOpening();
			Member member=memberService.getMember(memberNo);
			if( member!= null){
				p.setMemberName(member.getMemberName());
				p.setMobilePhone(member.getMobilePhone());
				p.setMemberNo(member.getMemberNo());
				p.setMemo(memo);
			}
			
			return  parkOpeningService.addParkOpening(p, getOperator());
		}
		
		
		/**
		 *查看意见反馈
		 * */
		@RequestMapping("/seeMemberFeedback")
		@ResponseBody
		public ResultInfo<List<MemberFeedbackListVo>> seeMemberFeedback(String memberNo,@RequestParam("pageNo") int pageNo) {
			ResultInfo<List<MemberFeedbackListVo>> result = new ResultInfo<List<MemberFeedbackListVo>>();
			List<MemberFeedbackListVo> m=new ArrayList<MemberFeedbackListVo>();
			//查出反馈的信息
			CustomerFeedback cf=new CustomerFeedback();
			cf.setMemberNo(memberNo);
			int pageSize = 10;
			SysParam sysParamOrder = sysParamService.getByParamKey(CarConstant.orderPageSize);
			if (sysParamOrder != null) {
				pageSize = Integer.parseInt(sysParamOrder.getParamValue());
			}
			Query q = new Query(pageNo, pageSize, cf);
			PageFinder<CustomerFeedback> cuss=customerFeedbackService.getCustomerFeedbackPagedLists(q);
			if(cuss != null &&  cuss.getData().size()>0){
				for (CustomerFeedback customerFeedback : cuss.getData()) {
					MemberFeedbackListVo mo=new MemberFeedbackListVo();
					mo.setFeedbackNo(customerFeedback.getFeedbackNo());
					mo.setContent(customerFeedback.getContent());
					mo.setCreateTime(ECDateUtils.formatStringDate(customerFeedback.getCreateTime()));
					mo.setCustomerType(customerFeedback.getCustomerType());
					mo.setReplyContent(customerFeedback.getReplyContent());
					mo.setReplyTime(ECDateUtils.formatStringDate(customerFeedback.getReplyTime()));
					m.add(mo);
				}
				result.setData(m);
				result.setCode("1");
				return  result;
			}else{
				result.setCode("0");
				return  result;
			}
			
		}
		
}
