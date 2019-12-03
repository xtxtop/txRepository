package cn.com.shopec.mapi.marketing.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.common.MarketingContant;
import cn.com.shopec.core.marketing.model.Advert;
import cn.com.shopec.core.marketing.service.AdvertService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.marketing.vo.AdvertVo;

@Controller
@RequestMapping("/app/advert")
public class AdvertController extends BaseController{
	@Value("${image_path}")
	private String imgPath;
	@Resource
	private AdvertService advertService;
	
	@Resource
	private CarService carService;
		/**
		 *获取活动列表信息
		 * */
		@RequestMapping("/advertPageList")
		@ResponseBody
		public ResultInfo<List<AdvertVo>> advertPageList(Integer type) {
			Advert advert=new Advert();
			advert.setIsAvailable(1);
			if(type ==null){
				advert.setAdvertMemberType(0);
			}else{
				advert.setAdvertMemberType(type);
			}
			advert.setCensorStatus(1);
			advert.setIsStartAdvert(0);
			Query q=new Query(1,10,advert);
			PageFinder<Advert> pf= advertService.getAdvertPagedList(q);
			ResultInfo<List<AdvertVo>> result=new ResultInfo<List<AdvertVo>>();
			return  advertToVo(result,pf.getData());
		}
		/**
		 * 方法说明:将按特定条件查询的记录转换成自定vo对象
		 */
		 ResultInfo<List<AdvertVo>> advertToVo(ResultInfo<List<AdvertVo>> result,List<Advert> advertList){
		
			if (advertList!=null&&advertList.size()>0) {
				List<AdvertVo> voList = new ArrayList<AdvertVo>();
				for (Advert advert : advertList) {
					AdvertVo advertVo = new AdvertVo();
					 advertVo.setAdvertName(advert.getAdvertName());
					 advertVo.setAdvertNo(advert.getAdvertNo());
					 advertVo.setAdvertPicUrl(imgPath + "/" +advert.getAdvertPicUrl());
					 advertVo.setAdvertType(advert.getAdvertType());
					 advertVo.setCensorStatus(advert.getCensorStatus());
					 advertVo.setIsAvailable(advert.getIsAvailable());
					 if(advert.getExternalLinkUrl() != null && !"".equals(advert.getExternalLinkUrl())){
						 advertVo.setLinkUrl(advert.getExternalLinkUrl());
					 }else{
						 advertVo.setLinkUrl(advert.getLinkUrl());
					 }
					
					 advertVo.setRanking(advert.getRanking());
					 advertVo.setJumpType(advert.getJumpType());
					 advertVo.setNativeUrlType(advert.getNativeUrlType());
					voList.add(advertVo);
					
				}
				result.setData(voList);
				result.setCode(MarketingContant.success_code);
				result.setMsg("");
			}else {
				result.setCode(MarketingContant.fail_code);
				result.setMsg(MarketingContant.fail_behavior_msg);
			}
			return result;
		}
		 /**
		 * 方法说明:将按特定order对象转换成自定vo对象
		 */
		 ResultInfo<AdvertVo> advertToVoOne(ResultInfo<AdvertVo> result,Advert advert){
			 if(advert!=null){
				 AdvertVo advertVo = new AdvertVo();
				 advertVo.setAdvertName(advert.getAdvertName());
				 advertVo.setAdvertNo(advert.getAdvertNo());
				 advertVo.setAdvertPicUrl(advert.getAdvertPicUrl());
				 advertVo.setAdvertType(advert.getAdvertType());
				 advertVo.setCensorStatus(advert.getCensorStatus());
				 advertVo.setIsAvailable(advert.getIsAvailable());
				 advertVo.setLinkUrl(advert.getLinkUrl());
				 advertVo.setRanking(advert.getRanking());
				 result.setData(advertVo);
				 result.setCode(MarketingContant.success_code);
				 result.setMsg(""); 
			 }else{
				 result.setCode(MarketingContant.fail_code);
				 result.setMsg(MarketingContant.fail_msg); 
			 }
				
			return result;
		}
		
}
