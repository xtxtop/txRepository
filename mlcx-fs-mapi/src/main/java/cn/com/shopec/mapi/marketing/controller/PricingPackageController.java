package cn.com.shopec.mapi.marketing.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.common.MarketingContant;
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.marketing.vo.PricingPackageVo;

@Controller
@RequestMapping("/app/pricingPackage")
public class PricingPackageController extends BaseController{
	
	@Resource
	private PricingPackageService pricingPackageService;
	@Resource
	private PricingPackOrderService pricingPackOrderService;
		/**
		 *获取套餐列表信息
		 * */
		@RequestMapping("/pricingPackageList")
		@ResponseBody
		public ResultInfo<List<PricingPackageVo>> pricingPackageList() {
			PricingPackage pricingPack=new PricingPackage();
			pricingPack.setIsAvailable(1);//上架
			pricingPack.setCencorStatus(1);//审核通过
			pricingPack.setPackageType(2);//套餐类型(1.赠送类套餐，2.销售套餐)
			Query q=new Query();
			q.setQ(pricingPack);
			ResultInfo<List<PricingPackageVo>> result=new ResultInfo<List<PricingPackageVo>>();
			return  pricingPackageToVo(result,pricingPackageService.getPricingPackageList(q));
		}
		/**
		 * 方法说明:将按特定条件查询的记录转换成自定vo对象
		 */
		 ResultInfo<List<PricingPackageVo>> pricingPackageToVo(ResultInfo<List<PricingPackageVo>> result,List<PricingPackage> pricingPackageList){
		
			if (pricingPackageList!=null&&pricingPackageList.size()>0) {
				List<PricingPackageVo> voList = new ArrayList<PricingPackageVo>();
				for (PricingPackage pp : pricingPackageList) {
					PricingPackageVo ppV = new PricingPackageVo();
					ppV.setAvailableDays(pp.getAvailableDays());
				//	ppV.setCencorStatus(pp.getCencorStatus());
//					ppV.setCityId(pp.getCityId());
//					ppV.setCityName(pp.getCityName());
					//ppV.setIsAvailable(pp.getIsAvailable());
					
					ppV.setPackageName(pp.getPackageName());
					ppV.setPackageNo(pp.getPackageNo());
					ppV.setPrice(pp.getPrice());
				
					ppV.setDeductionCeiling(pp.getDeductionCeiling());
					
					//根据分钟数和面额进行类型区分
					if(pp.getMinutes() != null && pp.getPackAmount() == null){	//分钟套餐
						ppV.setMinutes(pp.getMinutes());
						ppV.setType(1);
					}else if(pp.getMinutes() == null && pp.getPackAmount() != null){	//金额套餐
						ppV.setPackAmount(pp.getPackAmount());
						ppV.setType(2);
					}
					String avaliableDate="";
					Date nowDate=new Date();
					ppV.setAvailableDays(pp.getAvailableDays());
					if(pp.getAvailableDays() != null){
						avaliableDate=ECDateUtils.formatStringTime(ECDateUtils.getDateAfter(nowDate,pp.getAvailableDays()));
					}
					
					ppV.setAvaliableDate(avaliableDate);
					voList.add(ppV);
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
		  * 判断会员套餐产品是否还可购买
		  * @param memberNo
		  * @param packageNo
		  * @return
		  */
		@RequestMapping("judgeIsBuyPricingPackage")
		@ResponseBody
		public ResultInfo<PricingPackageVo> pricingPackageView(String memberNo,String packageNo) {
			ResultInfo<PricingPackageVo> result=new ResultInfo<PricingPackageVo>();
			PricingPackageVo packageVo = new PricingPackageVo();
			//查询已经购买的套餐
			PricingPackOrder packOrder = new PricingPackOrder();
			packOrder.setMemberNo(memberNo);
			packOrder.setPackageId(packageNo);
			packOrder.setPayStatus(1);//支付成功，（充值成功后才限制）
			PricingPackage pricingPack = pricingPackageService.getPricingPackage(packageNo);
			List<PricingPackOrder> packOrderList = pricingPackOrderService.getPricingPackOrderList(new Query(packOrder));
			int totalBuyTimes = -1;//无限制
			if(pricingPack!=null){
				if(pricingPack.getBuyTimes()!=null){
					totalBuyTimes = pricingPack.getBuyTimes();
				}
			}else{
				result.setCode(Constant.FAIL);
				result.setMsg("套餐不存在");
				return  result;
			}
			int buyedTimes = packOrderList.size();
			if(totalBuyTimes==-1){
				packageVo.setIsBuyPackage(1);
				result.setCode(Constant.SECCUESS);
				result.setData(packageVo);
			}else{
				if(buyedTimes<totalBuyTimes){
					packageVo.setIsBuyPackage(1);
					result.setCode(Constant.SECCUESS);
					result.setData(packageVo);
				}else{
					packageVo.setIsBuyPackage(0);
					result.setCode(Constant.FAIL);
					result.setMsg("您已达到购买次数限制");
					result.setData(packageVo);
				}
			}
			return  result;
		}
		 /**
			 *获取套餐详情信息
			 * */
//			@RequestMapping("/pricingPackageView")
//			@ResponseBody
//			public ResultInfo<PricingPackageVo> pricingPackageView(String packageNo) {
//				PricingPackage pricingPack=pricingPackageService.getPricingPackage(packageNo);
//				ResultInfo<PricingPackageVo> result=new ResultInfo<PricingPackageVo>();
//				return  pricingPackageToVoOne(result,pricingPack);
//			}
			/**
			 * 方法说明:将按特定条件查询的记录转换成自定vo对象
			 */
//			 ResultInfo<PricingPackageVo> pricingPackageToVoOne(ResultInfo<PricingPackageVo> result,PricingPackage pricingPackage){
//			
//				if (pricingPackage!=null) {
//					PricingPackageVo ppV = new PricingPackageVo();
//					ppV.setAvailableDays(pricingPackage.getAvailableDays());
//					ppV.setCencorStatus(pricingPackage.getCencorStatus());
////					ppV.setCityId(pp.getCityId());
////					ppV.setCityName(pp.getCityName());
//					//ppV.setIsAvailable(pricingPackage.getIsAvailable());
//					ppV.setMinutes(pricingPackage.getMinutes());
//					ppV.setPackageName(pricingPackage.getPackageName());
//					ppV.setPackageNo(pricingPackage.getPackageNo());
//					ppV.setPrice(pricingPackage.getPrice());
//					result.setData(ppV);
//					result.setCode(MarketingContant.success_code);
//					result.setMsg("");
//				}else {
//					result.setCode(MarketingContant.fail_code);
//					result.setMsg(MarketingContant.fail_behavior_msg);
//				}
//				return result;
//			}
}
