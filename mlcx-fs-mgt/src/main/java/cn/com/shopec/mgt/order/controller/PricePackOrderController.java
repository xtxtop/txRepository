package cn.com.shopec.mgt.order.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.Accounts;
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.marketing.service.AccountsService;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.mgt.common.BaseController;
import cn.com.shopec.mgt.order.vo.PricingPackOrderVo;
@Controller
@RequestMapping("pricingPackOrder")
public class PricePackOrderController extends BaseController{
	@Resource
	private PricingPackOrderService pricingPackOrderService;
	@Resource
	private PricingPackageService pricingPackageService;
	@Resource
	private MemberService memberService;
	@Resource
	private AccountsService accountsService;
	private static final String SEP1 = "#";  
    private static final String SEP2 = "|";
    
	/**
	 * 套餐订单页面
	 * @return
	 */
	@RequestMapping("toPricingPackOrderList")
	public String toPricingPackOrderList(ModelMap modelMap, String memberNo, Integer isRecharge,Integer packageType, String today){
		if(today != null && today.equals("yes")){
			modelMap.put("today", ECDateUtils.formatDate(new Date(), ECDateUtils.Format_Date));
		}
		if(packageType != null && !packageType.equals("")){
			modelMap.put("packageType",packageType);
		}
		List<PricingPackage> pricingPackageList = pricingPackageService.getPricingPackageList(new Query());
		modelMap.put("pricingPackageList", pricingPackageList);
		modelMap.put("memberNo", memberNo);
		modelMap.put("isRecharge", isRecharge);
		return "order/pricing_pack_order_list";
	}
	/**
	 * 套餐订单页面分页
	 */
    @RequestMapping("pageListPricingPackOrder")
    @ResponseBody
    public PageFinder<PricingPackOrder> pageListPricingPackOrder(@ModelAttribute("order")PricingPackOrder pricingPackOrder,Query query){
    	pricingPackOrder.setIsAvailable(1);
    	Query q = new Query(query.getPageNo(),query.getPageSize(),pricingPackOrder);
    	return pricingPackOrderService.getPricingPackOrderPagedList(q);
    }
    /**
	 * 套餐订单详情
	 * @return
	 */
	@RequestMapping("toPricingPackOrderView")
	public String toPricingPackOrderView(String packOrderNo,ModelMap modelMap){
		PricingPackOrder pricingPackOrder = pricingPackOrderService.getPricingPackOrder(packOrderNo);
		modelMap.put("pricingPackOrder", pricingPackOrder);
		return "order/pricing_pack_order_view";
	}
	/**
	 * 套餐订单添加页面
	 * @return
	 */
	@RequestMapping("toPricingPackOrderAdd")
	public String toPricingPackOrderAdd(String[] memberNos,Model model){
//		Member member = memberService.getMember(memberNo);
		List<String> stringList = Arrays.asList(memberNos);  
		
//		String listmemberNo = String.valueOf(stringList);
		
		  StringBuilder result=new StringBuilder();
	        boolean flag=false;
	        for (String string : stringList) {
	            if (flag) {
	                result.append(",");
	            }else {
	                flag=true;
	            }
	            result.append(string);
	        } 
		 
		model.addAttribute("memberNos", result.toString());
		PricingPackage pricePackage = new PricingPackage();
		pricePackage.setCencorStatus(1);
		pricePackage.setIsAvailable(1);
		pricePackage.setPackageType(1);//套餐类型，1.赠送类套餐
		List<PricingPackage> pricePackageList =  pricingPackageService.getPricingPackageList(new Query(pricePackage));
		model.addAttribute("pricePackageList", pricePackageList);
		return "order/price_pack_order_add";
	}
	
	/**
	 * 套餐订单添加
	 * 
	 * @param pricePackage
	 * @return
	 */
	@RequestMapping("addPricingPackOrder")
	@ResponseBody
	public ResultInfo<PricingPackOrder> addPricingPackOrder(@ModelAttribute("PricingPackOrderVo") PricingPackOrderVo pricingPackOrderVo) {
		ResultInfo<PricingPackOrder> res = new ResultInfo<PricingPackOrder>();
		PricingPackOrder pricingPackOrder = new PricingPackOrder();
		if(pricingPackOrderVo.getSendQuantity() == null || pricingPackOrderVo.getSendQuantity() < 1){
			res.setCode(Constant.FAIL);
			res.setMsg("发放数量不能小于1！");
			return res;
		}
		for(String memberNo : pricingPackOrderVo.getMemberNos()){
			Member member = memberService.getMember(memberNo);
			if(member == null){
				res.setCode(Constant.FAIL);
				res.setMsg("会员编号不存在！");
				return res;	
			}
			if(pricingPackOrderVo !=null && pricingPackOrderVo.getPackageId() != null){
				//发放之前判断该套餐是否限制发放数量
				PricingPackage pricingPackage = pricingPackageService.getPricingPackage(pricingPackOrderVo.getPackageId());
				//获取该套餐产品的发放数量
				long orderCount = pricingPackOrderService.getPackageCount(pricingPackage.getPackageNo());
				if(pricingPackage.getIsLimitTimes() != null && pricingPackage.getIsLimitTimes() == 1 ){
					int sendQuantity = pricingPackage.getBuyTimes();//套餐产品购买次数
					long remainQuantity = pricingPackage.getBuyTimes() - orderCount;//剩余可发放次数
					if(pricingPackOrderVo.getSendQuantity() > sendQuantity){
						res.setCode(Constant.FAIL);
						res.setMsg("发放数量不能大于该套餐产品购买次数！");
						return res;
					} else if (orderCount == pricingPackage.getBuyTimes()){
						res.setCode(Constant.FAIL);
						res.setMsg("该套餐产品购买次数已经发放完毕，不能继续发放！");
						return res;
					} else if (pricingPackOrderVo.getSendQuantity() > remainQuantity){
						res.setCode(Constant.FAIL);
						res.setMsg("该套餐产品剩余可发放次数为"+remainQuantity+"次！");
						return res;
					} else {
						for(int i = 0; i < pricingPackOrderVo.getSendQuantity(); i++){
							pricingPackOrder.setPackOrderNo(null);
							pricingPackOrder.setMemberNo(member.getMemberNo());
							pricingPackOrder.setMemberName(member.getMemberName());
							pricingPackOrder.setMobilePhone(member.getMobilePhone());
							pricingPackOrder.setPackageId(pricingPackOrderVo.getPackageId());
							pricingPackOrder.setPackageName(pricingPackOrderVo.getPackageName());
							pricingPackOrder.setPackAmount(pricingPackOrderVo.getPackAmount());
							pricingPackOrder.setPackMinute(pricingPackOrderVo.getPackMinute()); 
							
							PricingPackage pricePackage = pricingPackageService.getPricingPackage(pricingPackOrderVo.getPackageId());
							pricingPackOrder.setUserdMinute(0);
							pricingPackOrder.setPaymentMethod(4); //赠送的套餐，支付方式为其他。
							pricingPackOrder.setPayStatus(1);;
							pricingPackOrder.setIsAvailable(1);
							pricingPackOrder.setPackOrderAmount(pricePackage.getPackAmount());
							pricingPackOrder.setUseredOrderAmount(0d);
							pricingPackOrder.setPayableAmount(pricePackage.getPrice());
							pricingPackOrder.setVailableTime1(new Date());
							pricingPackOrder.setInvoiceStatus(0);
							//有效期的起止时间
							if(pricingPackOrderVo.getVailableTime2()!= null){
								pricingPackOrder.setVailableTime2(pricingPackOrderVo.getVailableTime2());
							}
							res = pricingPackOrderService.addPricingPackOrder(pricingPackOrder, getOperator());
							if(res.getCode().equals(Constant.SECCUESS)){
								//查询我的余额
								Query qr = new Query();
								PricingPackOrder ppor = new PricingPackOrder();
								ppor.setPayStatus(1);// 已经支付的
								ppor.setIsAvailable(1);// 可用
								ppor.setNowTime(new Date());
								ppor.setMemberNo(pricingPackOrder.getMemberNo());
								qr.setQ(ppor);
								List<PricingPackOrder>  pporList = pricingPackOrderService.getPricingPackOrderListBypo(qr);
								Double poa=0.0;
								Double uoa=0.0;
								for (PricingPackOrder packOrder : pporList) {
									if(!packOrder.getPackageId().equals(pricingPackOrder.getPackageId())){
										if(packOrder.getPackOrderAmount() != null ){
											poa=ECCalculateUtils.add(poa,packOrder.getPackOrderAmount());
										}
										if(packOrder.getUseredOrderAmount() != null){
											uoa=ECCalculateUtils.add(uoa,packOrder.getUseredOrderAmount());
										}
									}
								}
								//金额套餐赠送成功后给记账表添加记录
								Double ye = ECCalculateUtils.sub(poa, uoa);
								Accounts ac = new Accounts();
								ac.setMemberNo(pricingPackOrder.getMemberNo());
								ac.setBusinessNo(pricingPackOrder.getPackOrderNo());
								ac.setBusinessType(2);
								ac.setAccountType(2);
								ac.setAccountMoney(pricingPackOrder.getPackOrderAmount());
								ac.setAccountBeforeMoney(ye);
								ac.setAccountOverMoney(ECCalculateUtils.add(ye, ac.getAccountMoney()));
								ac.setAccountTime(new Date());
								accountsService.addAccounts(ac, null);
							}
						}
					}
				}else {
					for(int i = 0; i < pricingPackOrderVo.getSendQuantity(); i++){
						pricingPackOrder.setPackOrderNo(null);
						pricingPackOrder.setMemberNo(member.getMemberNo());
						pricingPackOrder.setMemberName(member.getMemberName());
						pricingPackOrder.setMobilePhone(member.getMobilePhone());
						pricingPackOrder.setPackageId(pricingPackOrderVo.getPackageId());
						pricingPackOrder.setPackageName(pricingPackOrderVo.getPackageName());
						pricingPackOrder.setPackAmount(pricingPackOrderVo.getPackAmount());
						pricingPackOrder.setPackMinute(pricingPackOrderVo.getPackMinute()); 
						
						PricingPackage pricePackage = pricingPackageService.getPricingPackage(pricingPackOrderVo.getPackageId());
						pricingPackOrder.setUserdMinute(0);
						pricingPackOrder.setPaymentMethod(4); //赠送的套餐，支付方式为其他。
						pricingPackOrder.setPayStatus(1);;
						pricingPackOrder.setIsAvailable(1);
						pricingPackOrder.setPackOrderAmount(pricePackage.getPackAmount());
						pricingPackOrder.setUseredOrderAmount(0d);
						pricingPackOrder.setPayableAmount(pricePackage.getPrice());
						pricingPackOrder.setVailableTime1(new Date());
						pricingPackOrder.setInvoiceStatus(0);
						//有效期的起止时间
						if(pricingPackOrderVo.getVailableTime2()!= null){
							pricingPackOrder.setVailableTime2(pricingPackOrderVo.getVailableTime2());
						}else{
							if(pricePackage.getAvailableDays() != null){
								pricingPackOrder.setVailableTime2(ECDateUtils.getDateAfter(new Date(), pricePackage.getAvailableDays()));
							}
						}
						res = pricingPackOrderService.addPricingPackOrder(pricingPackOrder, getOperator());
						if(res.getCode().equals(Constant.SECCUESS)){
							//查询我的余额
							Query qr = new Query();
							PricingPackOrder ppor = new PricingPackOrder();
							ppor.setPayStatus(1);// 已经支付的
							ppor.setIsAvailable(1);// 可用
							ppor.setNowTime(new Date());
							ppor.setMemberNo(pricingPackOrder.getMemberNo());
							qr.setQ(ppor);
							List<PricingPackOrder>  pporList = pricingPackOrderService.getPricingPackOrderListBypo(qr);
							Double poa=0.0;
							Double uoa=0.0;
							for (PricingPackOrder packOrder : pporList) {
								if(!packOrder.getPackageId().equals(pricingPackOrder.getPackageId())){
									if(packOrder.getPackOrderAmount() != null ){
										poa=ECCalculateUtils.add(poa,packOrder.getPackOrderAmount());
									}
									if(packOrder.getUseredOrderAmount() != null){
										uoa=ECCalculateUtils.add(uoa,packOrder.getUseredOrderAmount());
									}
								}
							}
							//金额套餐赠送成功后给记账表添加记录
							Double ye = ECCalculateUtils.sub(poa, uoa);
							Accounts ac = new Accounts();
							ac.setMemberNo(pricingPackOrder.getMemberNo());
							ac.setBusinessNo(pricingPackOrder.getPackOrderNo());
							ac.setBusinessType(2);
							ac.setAccountType(2);
							ac.setAccountMoney(pricingPackOrder.getPackOrderAmount());
							ac.setAccountBeforeMoney(ye);
							ac.setAccountOverMoney(ECCalculateUtils.add(ye, ac.getAccountMoney()));
							ac.setAccountTime(new Date());
							accountsService.addAccounts(ac, null);
						}
					}
				}
			}else{
				res.setCode(Constant.FAIL);
				res.setMsg("套餐数据为空！");
			}
		}
		
		return res;
	}
    
	/**
	 * 取消套餐订单
	 * @param packOrderNo
	 * @return
	 */
	 @RequestMapping("/pricingPackOrderOver")
     @ResponseBody
     public ResultInfo<PricingPackOrder> pricingPackOrderOver(String packOrderNo){
    	 ResultInfo<PricingPackOrder> resultInfo=new ResultInfo<PricingPackOrder>();
    	 PricingPackOrder pricingPackOrder = new PricingPackOrder();
    	 pricingPackOrder.setPackOrderNo(packOrderNo);
    	 //取消赠送类套餐，变为不可用
    	 pricingPackOrder.setIsAvailable(0);;
    	 resultInfo = pricingPackOrderService.updatePricingPackOrder(pricingPackOrder,null);
    	
		 return resultInfo;
		 
     }
}
