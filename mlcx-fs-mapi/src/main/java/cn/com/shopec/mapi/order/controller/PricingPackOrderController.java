package cn.com.shopec.mapi.order.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.PricingPackage;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mapi.order.vo.PricingPackOrderInvoiceVo;

@Controller
@RequestMapping("/app/pricingPackOrder")
public class PricingPackOrderController {
	
	@Resource
	private PricingPackOrderService pricingPackOrderService;
	@Resource
	private PricingPackageService packageService;
	@Resource
	private SysParamService sysParamService;

	/**
	 * 查看未开发票的套餐记录
	 * 查询条件：时间（当前一个月内），开票的状态：未开
	 */
//	@RequestMapping("/taocanInvoiceRecord")
//	@ResponseBody
//	public ResultInfo<List<PricingPackOrderInvoiceVo>> orderInvoiceRecord(String memberNo){
//		
//		ResultInfo<List<PricingPackOrderInvoiceVo>> result = new ResultInfo<List<PricingPackOrderInvoiceVo>>();
//		PricingPackOrder or = new  PricingPackOrder();
//		//得到前一个月的时间
////		Date date  = ECDateUtils.getDateMonthAfter(new Date(), -1);
//		or.setMemberNo(memberNo);
//		or.setPayStatus(1);			// 支付状态（0、未支付，1、已支付，默认0）
//		or.setInvoiceStatus(0);		//发票状态(0:不用开，1：申请开发票中 2：已开)
////		or.setVailableTime1Start(date);//查询时间范围起
//		
//		Query q = new Query();
//		q.setQ(or);
//		
//		List<PricingPackOrder> orList = pricingPackOrderService.getPricingPackOrderList(q);
//		List<PricingPackOrder> packOrders=new ArrayList<PricingPackOrder>();
//		for(PricingPackOrder pricingPackOrder:orList){
//			if(pricingPackOrder.getPayableAmount()>0){
//			PricingPackage pricingPackage= packageService.getPricingPackage(pricingPackOrder.getPackageId());
//			if(pricingPackage!=null&&pricingPackage.getPackageType()==2){
//				PricingPackOrder packOrder=new PricingPackOrder();
//				packOrder.setPackOrderNo(pricingPackOrder.getPackOrderNo());
//				packOrder.setPayableAmount(pricingPackOrder.getPayableAmount());
//				packOrder.setVailableTime1(pricingPackOrder.getVailableTime1());
//				packOrder.setPayStatus(pricingPackOrder.getPayStatus());
//				packOrder.setCreateTime(pricingPackOrder.getCreateTime());
//				packOrders.add(packOrder);
//			}
//			}
//		}
//		
//		return orderInvoiceToVo(result, packOrders);
//	}
//	
	/**
	 * 查看未开发票的套餐记录
	 * 开票的状态：未开
	 */
	@RequestMapping("/moreTaocanInvoiceRecord")
	@ResponseBody
	public ResultInfo<List<PricingPackOrderInvoiceVo>> moreOrderInvoiceRecord(String memberNo,@RequestParam("pageNo") int pageNo){
		
		ResultInfo<List<PricingPackOrderInvoiceVo>> result = new  ResultInfo<List<PricingPackOrderInvoiceVo>>();
		PricingPackOrder or = new  PricingPackOrder();
		int pageSize = 10;
		Date date  =new  Date();
		or.setMemberNo(memberNo);
		or.setPayStatus(1);			// 支付状态（0、未支付，1、已支付，默认0）
		or.setInvoiceStatus(0);		//发票状态(0:不用开，1：申请开发票中 2：已开)
		or.setVailableTime1End(date);//查询时间范围止
		
		
		
		//根据系统参数 查找对应参数来获取 
		SysParam  sysParamOrder=sysParamService.getByParamKey(CarConstant.orderPageSize);
		if(sysParamOrder != null){
			pageSize=Integer.parseInt(sysParamOrder.getParamValue());
		}
		Query q = new Query(pageNo, pageSize, or);
		PageFinder<PricingPackOrder> finder = pricingPackOrderService.getPricingPackOrderPagedLists(q);
		List<PricingPackOrder> List = finder.getData();
		List<PricingPackOrder> orList =new ArrayList<PricingPackOrder>() ;
		for (PricingPackOrder pricingPackOrder : List) {
			PricingPackage p=packageService.getPricingPackage(pricingPackOrder.getPackageId());
			if(p!= null && p.getPackageType()==2){
				orList.add(pricingPackOrder);
			}
		}
		
		return orderInvoiceToVo(result, orList);
	}
	
	/**
	 * 方法说明:套餐表对象转自定义的套餐Vo
	 */
	private ResultInfo<List<PricingPackOrderInvoiceVo>> orderInvoiceToVo(ResultInfo<List<PricingPackOrderInvoiceVo>> result, List<PricingPackOrder> orList) {
		if (orList!=null&&orList.size()>0) {
			
			List<PricingPackOrderInvoiceVo> packVOList = new ArrayList<PricingPackOrderInvoiceVo>();
			for (PricingPackOrder pck : orList) {
				PricingPackOrderInvoiceVo vo = new PricingPackOrderInvoiceVo();
				vo.setPackOrderNo(pck.getPackOrderNo());
				vo.setPayableAmount(pck.getPayableAmount());
				vo.setVailableTime1(ECDateUtils.formatStringDate(pck.getVailableTime1()));
					Calendar c = Calendar.getInstance();
					c.setTime(pck.getVailableTime1());
					vo.setYear(c.get(Calendar.YEAR));
				vo.setMonth(pck.getVailableTime1().getMonth()+1);
				vo.setPayStatus(pck.getPayStatus());
				if(pck.getPackageName() != null && !"".equals(pck.getPackageName())){
					vo.setPackageName(pck.getPackageName());
				}else{
					vo.setPackageName("");
				}
				
				vo.setCreateTime(ECDateUtils.formatStringDate(pck.getCreateTime()));
				packVOList.add(vo);
			}
			result.setData(packVOList);
			result.setCode(OrderConstant.success_code);
			result.setMsg("");
		}else {
			result.setCode(OrderConstant.fail_code);
			result.setMsg("未找到可开票的套餐数据！");
		}
		return result;
	}
	/**
	 * 方法说明:套餐详情
	 */
	
}
