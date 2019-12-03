package cn.com.shopec.mapi.marketing.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.common.MarketingContant;
import cn.com.shopec.core.marketing.model.Advert;
import cn.com.shopec.core.marketing.model.SendMessage;
import cn.com.shopec.core.marketing.service.AdvertService;
import cn.com.shopec.core.marketing.service.SendMessageService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.marketing.vo.AdvertVo;
import cn.com.shopec.mapi.marketing.vo.InformVo;
import cn.com.shopec.mapi.marketing.vo.SendMessageVo;

@Controller
@RequestMapping("/app/inform")
public class InformController  extends BaseController{

	@Resource
	private SendMessageService sendMessageService;
	@Resource
	private AdvertService advertService;
	
	@RequestMapping("/getInform")
	@ResponseBody
	public ResultInfo<InformVo> getMyInform(String memberNo){
		//根据memberNo得到10条消息记录
		Query q1 = new Query();
		SendMessage s1 = new SendMessage();
		s1.setMemberNo(memberNo);
		q1.setPageSize(MarketingContant.msg_pageSize);
		q1.setQ(s1);
		
		Query q2 = new Query();
		Advert ad = new Advert();
		ad.setIsAvailable(1);
		ad.setCensorStatus(1);
		q2.setQ(ad);
		q2.setPageSize(MarketingContant.adv_pageSize);
		
		PageFinder<SendMessage> finder1 =  sendMessageService.getSendMessagePagedList(q1);
		PageFinder<Advert> finder2 =  advertService.getAdvertPagedList(q2);
		
		ResultInfo<InformVo> result = new ResultInfo<>();
		List<SendMessageVo> msgListVo = new ArrayList<>();
		List<AdvertVo> advListVo = new ArrayList<>();
		
		return informToListVo(finder1,msgListVo,finder2,advListVo,result);
	}

	/**
	 *  将查询到的信息转换为特定的list 
	 */
	private ResultInfo<InformVo> informToListVo(PageFinder<SendMessage> finder1, List<SendMessageVo> msgListVo,
			PageFinder<Advert> finder2, List<AdvertVo> advListVo,ResultInfo<InformVo> result) {
		
		List<SendMessage> msgList = finder1.getData();
		List<Advert> advList = finder2.getData();
		InformVo informVo = new InformVo();
		//判断并遍历分页查询的值
		if (msgList!=null&&msgList.size()>0) {
			for (SendMessage sendMessage : msgList) {
				SendMessageVo vo = new SendMessageVo();
				vo.setCreateTime(ECDateUtils.formatTime(sendMessage.getCreateTime()));
				vo.setIsRead(sendMessage.getIsRead());
				vo.setMemberNo(sendMessage.getMemberNo());
				vo.setMessageId(sendMessage.getMessageId());
				vo.setMessageType(sendMessage.getMessageType());
				//将遍历所得的值转化成自定义的vo，然后再添加到list里面
				msgListVo.add(vo);
			}
			//将这个list添加到自定义的informVo
			informVo.setSendMessageList(msgListVo);
		}
		if (advList!=null&&advList.size()>0) {
			for (Advert advert : advList) {
				AdvertVo vo = new  AdvertVo();
				vo.setAdvertName(advert.getAdvertName());
				vo.setAdvertNo(advert.getAdvertNo());
				vo.setAdvertPicUrl(advert.getAdvertPicUrl());
				vo.setAdvertType(advert.getAdvertType());
				vo.setCensorStatus(advert.getCensorStatus());
				vo.setIsAvailable(advert.getIsAvailable());
				vo.setLinkUrl(advert.getLinkUrl());
				vo.setRanking(advert.getRanking());
				advListVo.add(vo);
			}
			informVo.setAdvertList(advListVo);
		}
		
		result.setCode(MarketingContant.fail_code);
		result.setMsg(MarketingContant.fail_msg);
		
		if (informVo!=null) {
			result.setCode(MarketingContant.success_code);
			result.setData(informVo);
			result.setMsg("");
		}
	
		return result;
	}
	
	
}
