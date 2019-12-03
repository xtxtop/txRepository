package cn.com.shopec.mapi.finace.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.common.InvoiceConstant;
import cn.com.shopec.core.finace.model.Invoice;
import cn.com.shopec.core.finace.service.InvoiceService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.finace.vo.InvoiceDetailVo;
import cn.com.shopec.mapi.finace.vo.InvoiceListVo;
import cn.com.shopec.mapi.finace.vo.InvoiceTypeVo;
import cn.com.shopec.mapi.finace.vo.InvoiceVo;

@Controller
@RequestMapping("/app/invoice")
public class InvoiceController extends BaseController {

	@Resource
	public InvoiceService invoiceService;

	@Resource
	private OrderService orderService;
	@Resource
	private PricingPackOrderService pricingPackOrderService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private MemberService memberService;

	@Value("${image_path}")
	private String imgPath;

	/**
	 * 从系统参数 获取发票的种类
	 * 
	 * @param memberNo
	 * @return
	 */
	@RequestMapping("/getInvoiceBizObjType")
	@ResponseBody
	public ResultInfo<InvoiceTypeVo> getInvoiceBizObjType() {
		InvoiceTypeVo invoiceTypeVo = new InvoiceTypeVo();
		ResultInfo<InvoiceTypeVo> result = new ResultInfo<InvoiceTypeVo>();
		SysParam sysParam = sysParamService.getByParamKey(InvoiceConstant.INVOICETYPE);
		if (sysParam != null && sysParam.getParamValue() != null && !"".equals(sysParam.getParamValue())) {
			String[] arr = sysParam.getParamValue().split(",");
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].equals("1")) {
					invoiceTypeVo.setInvoiceType1("纸质发票");
				}
				if (arr[i].equals("2")) {
					invoiceTypeVo.setInvoiceType2("专用发票");
				}
				if (arr[i].equals("3")) {
					invoiceTypeVo.setInvoiceType3("电子发票");
				}

			}
			result.setCode("1");
			result.setData(invoiceTypeVo);
			return result;
		}
		result.setCode("0");
		return result;
	}

	/**
	 * 提交发票信息，添加发票记录
	 */
	@RequestMapping("/addInvoice")
	@ResponseBody
	public ResultInfo<String> addInvoice(InvoiceVo invoiceVo) {
		ResultInfo<String> result = new ResultInfo<>();
		Double   invoiceAmount=0d;
		if (invoiceVo != null && invoiceVo.getBizObjIds() != null && invoiceVo.getBizObjIds().length != 0) {
			String BizObjIds = StringUtils.join(invoiceVo.getBizObjIds(), ",");
			String BizObjId = BizObjIds;
			if (BizObjIds.indexOf('[') != -1) {
				BizObjId = BizObjIds.substring(BizObjIds.indexOf('[') + 1, BizObjIds.indexOf(']'));
			}
			BizObjId = BizObjId.replace("\"", "");
			String[] biz = BizObjId.split(",");
			for (int i = 0; i < biz.length; i++) {
				if (invoiceVo.getBizObjType() == 1) {
					Order o = orderService.getOrder(biz[i]);
					if (o != null && o.getIsInvoiceIssued() == 1) {
						result.setCode(InvoiceConstant.fail_code);
						result.setMsg(InvoiceConstant.fail_msg_order_invoice + o.getPayableAmount());
						return result;
					}else{
						invoiceAmount+=o.getPayableAmount();
					}
					
				}
				if (invoiceVo.getBizObjType() == 2) {
					PricingPackOrder p = pricingPackOrderService.getPricingPackOrder(biz[i]);
					if (p != null && p.getInvoiceStatus() == 1) {
						result.setCode(InvoiceConstant.fail_code);
						result.setMsg(InvoiceConstant.fail_msg_order_invoice + p.getPayableAmount());
						return result;
					}else{
						invoiceAmount+=p.getPayableAmount();
					}
				}
				
				
			}
			Invoice invoice = new Invoice();
			invoice.setInvoiceType(invoiceVo.getInvoiceType());
			invoice.setInvoiceAmount(invoiceAmount);
			invoice.setInvoiceTitle(invoiceVo.getInvoiceTitle());
			invoice.setName(invoiceVo.getName());
			invoice.setPhone(invoiceVo.getPhone());
			invoice.setTel(invoiceVo.getTel());
			invoice.setInvoiceTime(new Date());
			invoice.setAddress(invoiceVo.getAddress());
			invoice.setEmailAddress(invoiceVo.getEmailAddress());
			invoice.setCompanyAddress(invoiceVo.getCompanyAddress());
			invoice.setHeaderCategories(invoiceVo.getHeaderCategories());
			invoice.setTaxpayerNo(invoiceVo.getTaxpayerNo());
			invoice.setAccountBank(invoiceVo.getAccountBank());
			invoice.setAccountNo(invoiceVo.getAccountNo());
			String imgInfo = invoiceVo.getInvoiceInfo();
			String imgInfoPic = (imgInfo != null && !imgInfo.equals("")) ? imgInfo.substring(imgPath.length() + 1)
					: null;
			invoice.setInvoiceInfo(imgInfoPic);
			String img = invoiceVo.getTaxpayerNoticePic();
			String taxpayerNoticePic = (img != null && !img.equals("")) ? img.substring(imgPath.length() + 1) : null;
			invoice.setTaxpayerNoticePic(taxpayerNoticePic);

			invoice.setBizObjType(invoiceVo.getBizObjType());
			invoice.setBizObjId(BizObjId);

			invoice.setCreateTime(new Date());

			invoice.setMemberNo(invoiceVo.getMemberNo());

			// 添加发票，默认的状态为：未开票
			invoice.setInvoiceStatus(0);
			ResultInfo<Invoice> rs = invoiceService.addInvoice(invoice, null);
			if (rs.getCode().equals("1")) {
				for (int i = 0; i < biz.length; i++) {
					if (invoiceVo.getBizObjType() == 1) {
						Order order = orderService.getOrder(biz[i]);
						if (order != null) {
							order.setInvioceId(rs.getData().getInvoiceId()); // 发票id
							order.setInvioceNo(rs.getData().getInvoiceNo());// 发票号
							order.setIsNeedInvoice(1);// 是否需开票（0，不开票，1、需开票，默认0）
							order.setIsInvoiceIssued(0);// 是否已开发票（0，未开票，1，已开票，默认0）
							order.setInvoiceTime(new Date());// 开票时间
							orderService.updateOrder(order, null);
							result.setCode(InvoiceConstant.success_code);
							result.setMsg("");
						} else {
							result.setCode(InvoiceConstant.fail_code);
							result.setMsg(InvoiceConstant.fail_msg_order);
						}
					} else if (invoiceVo.getBizObjType() == 2) {
						PricingPackOrder pricingPackOrder = pricingPackOrderService.getPricingPackOrder(biz[i]);
						if (pricingPackOrder != null) {
							pricingPackOrder.setInvoiceId(rs.getData().getInvoiceId());
							pricingPackOrder.setInvoiceNo(rs.getData().getInvoiceNo());// 发票号
							pricingPackOrder.setInvoiceStatus(1);
							;// 是否已开发票（0，未开票，1，已开票，默认0）
							pricingPackOrderService.updatePricingPackOrder(pricingPackOrder, null);
							result.setCode(InvoiceConstant.success_code);
							result.setMsg("");
						} else {
							result.setCode(InvoiceConstant.fail_code);
							result.setMsg(InvoiceConstant.fail_msg_order);
						}
					}
				}
			} else {
				result.setCode(InvoiceConstant.fail_code);
				result.setMsg(InvoiceConstant.fail_msg);
			}
		} else {
			result.setCode(InvoiceConstant.fail_code);
			result.setMsg(InvoiceConstant.fail_msg);
		}
		return result;
	}

	/**
	 * 查看开票记录（查看一个月内的开票记录） 业务：根据会员ID得到相关的发票， 查询条件：时间(当前时间一个月内、开票的状态、类型
	 */
	// //开票号、发票抬头、时间、金额
	// @RequestMapping("/getOneMonthMyInvoice")
	// @ResponseBody
	// public ResultInfo<List<InvoiceVo>> getOneMonthMyInvoice(String memberNo){
	// ResultInfo<List<InvoiceVo>> result = new ResultInfo<List<InvoiceVo>>();
	// Date date = ECDateUtils.getDateMonthAfter(new Date(), -1);
	//// String month = (date.getMonth()+1)+"";
	// Invoice in=new Invoice();
	//// in.setInvoiceStatus(1);//已开票66
	// in.setCreateTimeStart(date);//当前月份（一个月内的）
	// in.setMemberNo(memberNo);//会员编号
	// Query q=new Query(in);
	// q.setQ(in);
	// return invoiceToVo(result,
	// invoiceService.getInvoiceListByMemberMonth(q));
	// }

	/**
	 * 查看更多开票历史记录 业务：根据会员ID得到相关的发票， 查询条件：时间(、开票的状态、类型
	 */
	@RequestMapping("/getMoreMonthMyInvoice")
	@ResponseBody
	public ResultInfo<List<InvoiceListVo>> getMoreMonthMyInvoice(String memberNo, int pageNo) {
		ResultInfo<List<InvoiceListVo>> result = new ResultInfo<List<InvoiceListVo>>();
		Invoice in = new Invoice();
		// in.setInvoiceStatus(1);//已开票
		Date date = new Date();
		in.setMemberNo(memberNo);// 会员编号
		in.setCreateTimeEnd(date);
		// 根据系统参数 查找对应参数来获取
		int pageSize = 10;
		SysParam sysParamOrder = sysParamService.getByParamKey(CarConstant.orderPageSize);
		if (sysParamOrder != null) {
			pageSize = Integer.parseInt(sysParamOrder.getParamValue());
		}
		Query q = new Query(pageNo, pageSize, in);
		return invoiceToVo(result, invoiceService.getMoreMonthMyInvoice(q).getData());
	}

	/**
	 * 方法说明:将按特定条件查询的记录转换成自定vo对象
	 */
	public ResultInfo<List<InvoiceListVo>> invoiceToVo(ResultInfo<List<InvoiceListVo>> result,
			List<Invoice> invoiceList) {

		if (invoiceList != null && invoiceList.size() > 0) {
			List<InvoiceListVo> voList = new ArrayList<InvoiceListVo>();
			for (Invoice in : invoiceList) {
				InvoiceListVo ivo = new InvoiceListVo();
				ivo.setInvoiceAmount(in.getInvoiceAmount());
				ivo.setInvoiceId(in.getInvoiceId());
				ivo.setInvoiceNo(in.getInvoiceNo());
				ivo.setBizObjType(in.getBizObjType());
				// 这里取的是开票申请时间
				ivo.setInvoiceTime(ECDateUtils.formatTime(in.getCreateTime()));
				ivo.setInvoiceStatus(in.getInvoiceStatus());

				// 月份
				if (in.getInvoiceTime() != null) {
					ivo.setMonth((in.getInvoiceTime().getMonth() + 1));
					Calendar c = Calendar.getInstance();
					c.setTime(in.getInvoiceTime());
					ivo.setYear(c.get(Calendar.YEAR));

				}

				// 月份
				// ivo.setMonth(in.getInvoiceTime().getMonth() + 1);
				// Calendar c = Calendar.getInstance();
				// c.setTime(in.getInvoiceTime());
				// ivo.setYear(c.get(Calendar.YEAR));

				ivo.setInvoiceType(in.getInvoiceType());
				// ivo.setMemberNo(in.getMemberNo());
				// 1、增值税普通发票纸质版，2、增值税专用发票3.增值税普通发票电子版）
				if (in.getInvoiceType() != null) {
					if (in.getInvoiceType() == 1) {
						ivo.setInvoiceName("增值税普通发票纸质版");
					} else if (in.getInvoiceType() == 2) {
						ivo.setInvoiceName("增值税专用发票");
					} else {
						ivo.setInvoiceName("增值税普通发票电子版");
					}
				}

				voList.add(ivo);
			}
			result.setData(voList);
			result.setCode(Constant.SECCUESS);
		} else {
			result.setCode(Constant.FAIL);
			result.setMsg(Constant.NO_RECORD);
		}
		return result;
	}
	// public static void main(String[] args) {
	// String a="http://123.56.158.122:9080/image-server";
	// String b="http://123.56.158.122:9080/image-server/lujian";
	// int c=b.indexOf(a);
	// System.out.println(c);
	// System.err.println(b.substring(a.length()+1));
	// }

	/**
	 * 查看发票历史详情
	 * 
	 * @param memberNo
	 * @param invoiceId
	 * @return
	 */
	@RequestMapping("/getMyDetailInvoice")
	@ResponseBody
	public ResultInfo<InvoiceDetailVo> getMyDetailInvoice(String memberNo, String invoiceId) {
		ResultInfo<InvoiceDetailVo> result = new ResultInfo<InvoiceDetailVo>();
		Invoice in = invoiceService.getInvoice(invoiceId);
		InvoiceDetailVo invoiceDetailVo = new InvoiceDetailVo();
		result.setData(invoiceDetailVo);
		if (in != null) {
			result.getData().setInvoiceId(invoiceId);
			result.getData().setInvoiceType(in.getInvoiceType());
			result.getData().setInvoiceAmount(in.getInvoiceAmount());
			result.getData().setInvoiceTitle(in.getInvoiceTitle());
			if (in.getAddress() != null && !"".equals(in.getAddress())) {
				result.getData().setAddress(in.getAddress());
			} else if (in.getEmailAddress() != null && !"".equals(in.getEmailAddress())) {
				result.getData().setAddress(in.getEmailAddress());
			} else {
				result.getData().setAddress("");
			}
			if (in.getTaxpayerNo() != null && !"".equals(in.getTaxpayerNo())) {
				result.getData().setTaxpayerNo(in.getTaxpayerNo());
			} else {
				result.getData().setTaxpayerNo("");
			}
			result.getData().setHeaderCategories(in.getHeaderCategories());
			result.getData().setBizObjType(in.getBizObjType());
			result.getData().setInvoiceTime(ECDateUtils.formatTime(in.getInvoiceTime()));
			result.getData().setInvoiceNo(in.getInvoiceNo());
			result.getData().setName(in.getName());
			result.getData().setInOrderSize(in.getBizObjId().split(",").length);
			result.getData().setPhone(in.getPhone());
			result.setCode(Constant.SECCUESS);
		} else {
			result.setCode(Constant.FAIL);
			result.setMsg("无数据");
		}

		return result;

	}
}
