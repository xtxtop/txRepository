package cn.com.shopec.mapi.message.controller;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.marketing.model.SendMessage;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.marketing.service.SendMessageService;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.mapi.marketing.vo.SendMessageVo;
import cn.com.shopec.mapi.message.vo.PricingPackageVo;



/**
 * 我的通知接口
 * */
@Controller
@RequestMapping("/app/message")
public class SendMessageController{
	
	@Resource
	private SendMessageService sendMessageService;
	@Resource
	private PricingPackageService pricingPackageService;
	/**
	 * 我的通知，消息中心
	 * @param memberNo
	 * @return
	 */
	@RequestMapping("/getMessage")
	@ResponseBody
	public ResultInfo<List<SendMessageVo>> getsendMessage(String memberNo){
		ResultInfo<List<SendMessageVo>> result=new ResultInfo<List<SendMessageVo>>();
		SendMessage message=new SendMessage();
		if(memberNo!=null&&memberNo.length()!=0){
			message.setMemberNo(memberNo);
			Query q = new Query(message);
			List<SendMessage> sendMessages=sendMessageService.getSendMessageList(q);
			if(sendMessages!=null&&sendMessages.size()>0){
//				sendMessages.add(sendMessages.get(0));
				result=sendMessageTovo(result, sendMessages);
			}else{
				result.setCode(OrderConstant.success_code);
				result.setMsg("无数据！"); 
			}
		}else{
			result.setCode(OrderConstant.fail_code);
			result.setMsg(OrderConstant.fail_parameters);
		}
		return result;
	}
	
	public ResultInfo<List<SendMessageVo>> sendMessageTovo(ResultInfo<List<SendMessageVo>> result,List<SendMessage> sendMessages){
		if(sendMessages!=null&&sendMessages.size()>0){
			List<SendMessageVo> list=new ArrayList<SendMessageVo>();
			for(SendMessage sendMessage:sendMessages){
				SendMessageVo sendMessageVo=new SendMessageVo();
				sendMessageVo.setMessageId(sendMessage.getMessageId());
				sendMessageVo.setIsRead(sendMessage.getIsRead());
				sendMessageVo.setMemberNo(sendMessage.getMemberNo());
				sendMessageVo.setMessageContent(sendMessage.getMessageContent());
				sendMessageVo.setMessageType(sendMessage.getMessageType());
				sendMessageVo.setCreateTime(ECDateUtils.formatTime(sendMessage.getCreateTime()));
				sendMessageVo.setUpdateTime(ECDateUtils.formatTime(sendMessage.getUpdateTime()));
				list.add(sendMessageVo);
			}
			result.setData(list);
			result.setCode(OrderConstant.success_code);
			result.setMsg(""); 
		}else{
			 result.setCode(OrderConstant.fail_code);
			 result.setMsg(OrderConstant.fail_parameters); 
		 }
		return result;
	}
	/**
	 * 我的通知，活动中心
	 * @param memberNo
	 * @return
	 */
//	@RequestMapping("/getpricingPackage")
//	@ResponseBody
//	public ResultInfo<List<PricingPackageVo>> getPricingPackage(String cityName){
//		ResultInfo<List<PricingPackageVo>> result=new ResultInfo<List<PricingPackageVo>>();
//		PricingPackage pricingPackage=new PricingPackage();
//		if(cityName!=null&&cityName.length()!=0){
//			pricingPackage.setCityName(cityName);
//		}
////		pricingPackage.setAvailabelUpdateTimeStart(new Date());
//		
//			Query q = new Query(pricingPackage);
//			List<PricingPackage> pricingPackages=pricingPackageService.getPricingPackageList(q);
//			if(pricingPackages!=null&&pricingPackages.size()>0){
//				result=pricingPackageToVo(result, pricingPackages);
//			}else{
//				result.setCode(OrderConstant.success_code);
//				result.setMsg("无数据！");
//			}
//		return result;
//	}
//	public ResultInfo<List<PricingPackageVo>> pricingPackageToVo(ResultInfo<List<PricingPackageVo>> result,List<PricingPackage> pricingPackages){
//		if(pricingPackages!=null&&pricingPackages.size()>0){
//			List<PricingPackageVo> list=new ArrayList<PricingPackageVo>();
//			for(PricingPackage pricingPackage:pricingPackages){
//				PricingPackageVo pricingPackageVo=new PricingPackageVo();
//				pricingPackageVo.setCityName(pricingPackage.getCityName());
//				pricingPackageVo.setMinutes(pricingPackage.getMinutes());
//				pricingPackageVo.setPackageName(pricingPackage.getPackageName());
//				pricingPackageVo.setPrice(pricingPackage.getPrice());
//				list.add(pricingPackageVo);
//			}
//			result.setData(list);
//			result.setCode(OrderConstant.success_code);
//			result.setMsg("");
//		}else{
//			 result.setCode(OrderConstant.fail_code);
//			 result.setMsg(OrderConstant.fail_parameters); 
//		}
//		return result;
//	}
}
