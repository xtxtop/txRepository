package cn.com.shopec.mgt.finace.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.common.utils.ECStringUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.model.DepositRefund;
import cn.com.shopec.core.finace.model.PaymentRecord;
import cn.com.shopec.core.finace.model.TransToAccount;
import cn.com.shopec.core.finace.service.DepositOrderService;
import cn.com.shopec.core.finace.service.DepositRefundService;
import cn.com.shopec.core.finace.service.PaymentRecordService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictCatService;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;
/**
 * 押金退款
 * */

@Controller
@RequestMapping("/depositRefund")
public class DepositRefundController extends BaseController{
	
	private static final Log log = LogFactory.getLog(DepositRefundController.class);
	@Resource
	public DepositRefundService depositRefundService;
	@Resource
	private DataDictCatService dataDictCatService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private SysParamService sysParamService;

	@Resource
	private MemberService memberService;
	
	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private DepositOrderService depositOrderService;
	@Resource
	private PaymentRecordService paymentRecordService;
	@Resource
	private HttpServletRequest request;
	
	/*
	 * 进入押金退款列表页面
	 */
	@RequestMapping("/toDepositRefundList")
	public String mainPage(String cencorStatus,ModelMap modelMap) {
		modelMap.put("cencorStatus", cencorStatus);
		return "finace/depositRefund_list";
	}
	
	/*
	 * 首页待办事项 点击  进入押金退款列表页面
	 */
	@RequestMapping("/toDepositRefundListTodo")
	public String toDepositRefundListTodo(ModelMap model) {
		Integer censorStatus=2;
		model.addAttribute("censorStatus", censorStatus);
		return "finace/depositRefund_list";
	}
	
	/*
	 * 显示押金退款列表信息
	 */
	@RequestMapping("/pageListDepositRefund")
	@ResponseBody
	public PageFinder<DepositRefund> pageListInvoice(@ModelAttribute("depositRefund") DepositRefund depositRefund,Query query) throws ParseException {
//		if (!"".equals(applyTime1)) {
//			Date applyTime2 = ECDateUtils.parseTime(applyTime1 + " 00:00:00");
//			depositRefund.setApplyTimeStart(applyTime2);
//			Date applyTime3 = ECDateUtils.parseTime(applyTime1 + " 23:59:59");
//			depositRefund.setApplyTimeEnd(applyTime3);
//		}
//		if (!"".equals(refundTime1)) {
//			Date refundTime2 = ECDateUtils.parseTime(refundTime1 + " 00:00:00");
//			depositRefund.setRefundTimeStart(refundTime2);
//			Date refundTime3 = ECDateUtils.parseTime(refundTime1 + " 23:59:59");
//			depositRefund.setRefundTimeEnd(refundTime3);
//		}
		Query q = new Query(query.getPageNo(),query.getPageSize(),depositRefund);
		return depositRefundService.getDepositRefundPagedList(q);
	}
	/*
	 * 进入退款详情页
	 */
	@RequestMapping("/toDepositRefundView")
	public String toDepositRefundView(String refundNo,ModelMap modelMap) {
		DepositRefund depositRefund=depositRefundService.getDepositRefund(refundNo);
		modelMap.put("depositRefund", depositRefund);
		modelMap.put("cencorName", "");
		if(depositRefund.getCencorStatus() != null && depositRefund.getCencorStatus() != 0){
			SysUser user = sysUserService.detail(depositRefund.getCencorId());
			if(user != null && user.getUserName() != null){
				modelMap.put("cencorName", user.getUserName());
			}
		}
		if(depositRefund.getRefundStatus() != null && depositRefund.getRefundStatus() ==1){
			SysUser user = sysUserService.detail(depositRefund.getRefundOperatorId());//退款操作人
			if(user != null && user.getUserName() != null){
				modelMap.put("operatorName", user.getUserName());
			}
		}
		return "finace/depositRefund_view";
	}
	/*
	 * 进入退款审核页
	 */
	@RequestMapping("/toDepositRefundCencor")
	public String toDepositRefundCencor(String refundNo,ModelMap modelMap) {
		DepositRefund depositRefund=depositRefundService.getDepositRefund(refundNo);
		modelMap.put("depositRefund", depositRefund);
		return "finace/depositRefund_cencor";
	}
	/*
	 * 退款信息审核提交
	 */
	@RequestMapping("/cencorDepositRefund")
	@ResponseBody
	public ResultInfo<DepositRefund> cencorDepositRefund(@ModelAttribute("depositRefund") DepositRefund depositRefund) {
		ResultInfo<DepositRefund> resultInfo = new ResultInfo<DepositRefund>();
		//审核通过，进行退款操作
		//审核不通过，将保证金支付表信息还原
		if(depositRefund.getCencorStatus().equals(3)){
			DepositRefund dR=depositRefundService.getDepositRefund(depositRefund.getRefundNo());
			if(dR!=null){
				DepositOrder dOrder=depositOrderService.getDepositOrder(dR.getDepositOrderNo());
				if(dOrder.getFrozenAmount()==null){
					dOrder.setFrozenAmount(0d);
				}
				dOrder.setFrozenAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getFrozenAmount()-dR.getRefundAmount(),2));
			    depositOrderService.updateDepositOrder(dOrder, getOperator());
			}
		}
		Operator operator = getOperator();
		if (operator!=null) {
			depositRefund.setCencorId(operator.getOperatorId());
		}else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("请重新登录");
			return resultInfo;
		}
		return  depositRefundService.updateDepositRefund(depositRefund, operator);
	}
	/**
	 * 线下退款
	 * */
	@RequestMapping("/depositRefundPayMemo")
	@ResponseBody
	public ResultInfo<DepositRefund> depositRefundPayMemo(DepositRefund depositRefund) {
		ResultInfo<DepositRefund> resultInfo = new ResultInfo<>();
		DepositRefund dRefund=depositRefundService.getDepositRefund(depositRefund.getRefundNo());
		dRefund.setRefundMemo(depositRefund.getRefundMemo());
		Operator operator = getOperator();
		if (operator!=null) {
			depositRefund.setCencorId(operator.getOperatorId());
		}else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("请重新登录");
			return resultInfo;
		}
		return depositRefundService.depositRefundPayMemo(dRefund,operator);
	}
	/*
	 * 退款微信
	 */
	@RequestMapping("/toDepositRefundWXPay")
	@ResponseBody
	public ResultInfo<DepositRefund> toDepositRefundWXPay(String depositRefundNo) {
		DepositRefund dRefund=depositRefundService.getDepositRefund(depositRefundNo);
		DepositOrder dOrder=depositOrderService.getDepositOrder(dRefund.getDepositOrderNo());
		if(dRefund!=null&&dOrder!=null ){
			Operator operator = getOperator();
			return depositRefundService.payRefundWX(dRefund,dOrder,operator);// 退款功能
		}else{
			return null;
		}
	}
	
	/*
	 * 转账微信
	 */
	@RequestMapping("/toDepositRefundWXPayZ")
	@ResponseBody
	public ResultInfo<DepositRefund> toDepositRefundWXPayZ(String depositRefundNo) {
		DepositRefund dRefund=depositRefundService.getDepositRefund(depositRefundNo);
		DepositOrder dOrder=depositOrderService.getDepositOrder(dRefund.getDepositOrderNo());
		//查出支付记录表
		PaymentRecord  paymentRecord=new PaymentRecord();
		if(dOrder !=null && dOrder.getPaymentFlowNo() != null){
			 paymentRecord=paymentRecordService.getPaymentFlowNoById(dOrder.getPaymentFlowNo());
		}
		if(dRefund!=null&&dOrder!=null &&paymentRecord != null){
			return depositRefundService.payRefundWXZM(dRefund,dOrder,paymentRecord, request);// 退款功能
		}else{
			return null;
		}
	}
	/**
	 * 押金退款退款  -- 支付宝方式，首先判断是否支持退款，否则进行转账 
	 * @param depositRefundNo
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/toDepositRefundPay")
	@ResponseBody
	public ResultInfo<String> toDepositRefundPay(String depositRefundNo) throws Exception {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		resultInfo.setCode(Constant.FAIL);
		resultInfo.setMsg("系统错误，请稍后再试");
		//判断当前押金是否支持退款--调用支付宝单笔交易
		SysParam memoKey = sysParamService.getByParamKey("refundMemo");
		String remark = (memoKey == null || memoKey.getParamValue()==null)?"协商押金退款":memoKey.getParamValue();		//通过系统参数获取退款备注
		Operator operator = getOperator();
		if(null == operator){
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("登录信息过期，请重新登录后进行操作");
			return resultInfo;
		}
		//验证当前订单是否在有效期内
		Map<String, String> tradeInfo = new HashMap<String, String>();
		ResultInfo<String> result = depositRefundService.signOrder(depositRefundNo, tradeInfo);
		
		log.info("## tradeInfo=" + tradeInfo);
		
		if(result == null) {
			log.error("signOrder fail ,result is null");
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("系统异常，请稍后再试");
			return resultInfo;
		}
		log.info("result.code = " + result.getCode() + ",result.data=" + result.getData() + ",result.msg=" + result.getMsg());
		//查询成功，在有效期，进入退款页面
		if(Constant.SECCUESS.equals(result.getCode()) && "TRADE_SUCCESS".equals(result.getData())){		
			ResultInfo<DepositRefund> depositRefundResult = depositRefundService.toDepositRefundAvoidPwdPay(depositRefundNo,operator, remark);
			if ("1".equals(depositRefundResult.getCode())) {
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg("退款成功");
			}else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(depositRefundResult.getMsg());
			}
			return resultInfo;
		//查询成功，不在有效期，进入转账页面
		}else if(Constant.SECCUESS.equals(result.getCode()) && ("TRADE_FINISHED".equals(result.getData()) || "TRADE_HAS_FINISHED".equals(result.getData()) )){	
			//转账
			ResultInfo<TransToAccount> transResult =  depositRefundService.transAccount(depositRefundNo,remark,tradeInfo.get("buyer_id"), tradeInfo.get("buyer_email"),operator);
			if ("1".equals(transResult.getCode())) {
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg("转账成功");
			}else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(transResult.getMsg());
			}
			return resultInfo;
		}else {
			return resultInfo;
		}
	}
	
//	/**
//	 * 押金退款退款  -- 支付宝方式，首先判断是否支持退款，否则进行转账 
//	 * @param depositRefundNo
//	 * @param request
//	 * @param response
//	 * @param model
//	 * @throws Exception
//	 */
//	@RequestMapping("/toDepositRefundPay")
//	public void toDepositRefundPay(String depositRefundNo,HttpServletRequest request,HttpServletResponse response,ModelMap model) throws Exception {
//		//判断当前押金是否支持退款--调用支付宝单笔交易
//		SysParam memoKey = sysParamService.getByParamKey("refundMemo");
//		String remark = (memoKey == null || memoKey.getParamValue()==null)?"协商押金退款":memoKey.getParamValue();		//通过系统参数获取退款备注
//		Operator operator = getOperator();
//		if(null == operator){
//			response.reset();
//			response.setContentType("text/html;charset=UTF-8");   
//			response.setCharacterEncoding("UTF-8");
//			response.getWriter().write("登录信息过期，请重新登录后进行操作");
//			response.getWriter().flush();
//			response.getWriter().close();
//			return;
//		}
//		//验证当前订单是否在有效期内
//		Map<String, String> tradeInfo = new HashMap<String, String>();
//		ResultInfo<String> result = depositRefundService.signOrder(depositRefundNo, tradeInfo);
//		
//		log.info("## tradeInfo=" + tradeInfo);
//		
//		if(result == null) {
//			log.error("signOrder fail ,result is null");
//			response.reset();
//			response.setContentType("text/html;charset=UTF-8");   
//			response.setCharacterEncoding("UTF-8");
//			response.getWriter().write("系统异常，请稍后再试");
//			response.getWriter().flush();
//			response.getWriter().close();
//			return;
//		}
//		log.info("result.code = " + result.getCode() + ",result.data=" + result.getData() + ",result.msg=" + result.getMsg());
//			//查询成功，在有效期，进入退款页面
//			if(Constant.SECCUESS.equals(result.getCode()) && "TRADE_SUCCESS".equals(result.getData())){		
//				String sHtmlText=(String) depositRefundService.toDepositRefundPay(depositRefundNo,response,remark).getData();
//				response.reset();
//				response.setContentType("text/html;charset=UTF-8");   
//				response.setCharacterEncoding("UTF-8");
//				((HttpServletResponse)response).setHeader("alipay", "true");
//				response.getWriter().write(sHtmlText);
//				response.getWriter().flush();
//				response.getWriter().close();
//				model.put("form",sHtmlText);
//				return;
//			//查询成功，不在有效期，进入转账页面
//			}else if(Constant.SECCUESS.equals(result.getCode()) && ("TRADE_FINISHED".equals(result.getData()) || "TRADE_HAS_FINISHED".equals(result.getData()) )){	
//				//转账
//				ResultInfo<TransToAccount> transResult =  depositRefundService.transAccount(depositRefundNo,remark,tradeInfo.get("buyer_id"), tradeInfo.get("buyer_email"),operator);
//				response.reset();
//				response.setContentType("text/html;charset=UTF-8");   
//				response.setCharacterEncoding("UTF-8");
//				response.getWriter().write(transResult.getMsg());
//				response.getWriter().flush();
//				response.getWriter().close();
//				return;
//			}else if(Constant.FAIL.equals(result.getCode())){
//				response.reset();
//				response.setContentType("text/html;charset=UTF-8");   
//				response.setCharacterEncoding("UTF-8");
//				response.getWriter().write(result.getData());
//				response.getWriter().flush();
//				response.getWriter().close();
//				return;
//			}
////			else if("2".equals(result.getCode())){
//			//其他情况，全算系统异常
//			response.reset();
//			response.setContentType("text/html;charset=UTF-8");   
//			response.setCharacterEncoding("UTF-8");
//			response.getWriter().write("系统异常，请稍后再试");
//			response.getWriter().flush();
//			response.getWriter().close();
//			return;
//			
////		}
//	}
	

	
	//支付宝退款完成后的回调方法
	@RequestMapping("/depositRefundAlipayUpdate")
	@ResponseBody
	public void depositRefundAlipayUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Operator operator = getOperator();
		depositRefundService.depositRefundAlipayUpdate(request,response,operator);
	}
	//进行退款操作前先判断该记录是否已经退款
	@RequestMapping("/getDepositRefundStatus")
	@ResponseBody
	public ResultInfo<DepositRefund> getDepositRefundStatus(String depositRefundNo){
		ResultInfo<DepositRefund> resultInfo=new ResultInfo<DepositRefund>();
		if(depositRefundService.getDepositRefund(depositRefundNo)!=null){
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(depositRefundService.getDepositRefund(depositRefundNo));
		}else{
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}
	/**
	 * 导出退款信息
	 * */
    @RequestMapping("/exportDepositRefund")
    public void exportDepositRefund(@ModelAttribute("depositRefund")DepositRefund depositRefund,HttpServletRequest request, HttpServletResponse response){
    	try {
			Query q=new Query();
			q.setQ(depositRefund);
			List<DepositRefund> depositRefunds=depositRefundService.getDepositRefundList(q);
			 // 声明一个工作薄  
			String path=request.getRealPath("/")+"res"+File.separator+"depositRefund.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        int i=1;
	        for(DepositRefund depositRefundData:depositRefunds){
	        	 sheet.createRow(i);
	        	 //客户名称，手机号，退款金额。申请时间。审核状态，退款状态，退款方式，退款流水号，退款时间
	        	 setColumnValue(sheet,i,0,ECStringUtils.toStringForNull(depositRefundData.getMemberName()));//客户名称
	        	 setColumnValue(sheet,i,1,ECStringUtils.toStringForNull(depositRefundData.getMobilePhone()));//手机号
	        	 setColumnValue(sheet,i,2,ECStringUtils.toStringForNull(depositRefundData.getRefundAmount()));//退款金额
	        	 setColumnValue(sheet,i,3,ECDateUtils.formatTime(depositRefundData.getApplyTime()));//申请时间
	        	 String censorStatus="";
	        	 if(depositRefundData.getCencorStatus().equals(0)){
	        		 censorStatus="未审核";
	        	 }else if (depositRefundData.getCencorStatus().equals(1)){
	        		 censorStatus="已审核";
	        	 }else if (depositRefundData.getCencorStatus().equals(2)){
	        		 censorStatus="待审核";
	        	 }else if (depositRefundData.getCencorStatus().equals(3)){
	        		 censorStatus="未通过";
	        	 }
	        	 setColumnValue(sheet,i,4,ECStringUtils.toStringForNull(censorStatus));//审核状态
	        	 String refundStatus="";
	        	 if(depositRefundData.getRefundStatus().equals(0)){
	        		 refundStatus="未退款";
	        	 }else if (depositRefundData.getRefundStatus().equals(1)){
	        		 refundStatus="已退款";
	        	 }else if (depositRefundData.getRefundStatus().equals(2)){
	        		 refundStatus="退款失败";
	        	 }
	        	 setColumnValue(sheet,i,5,ECStringUtils.toStringForNull(refundStatus));//退款状态
	        	 String refundMethod="";
	        	 if(depositRefundData.getRefundMethod().equals(1)){
	        		 refundMethod="支付宝";
	        	 }else if (depositRefundData.getRefundMethod().equals(2)){
	        		 refundMethod="微信";
	        	 }else if (depositRefundData.getRefundMethod().equals(3)){
	        		 refundMethod="线下退款";
	        	 }
	        	 setColumnValue(sheet,i,6,ECStringUtils.toStringForNull(refundMethod));//退款方式
	        	 setColumnValue(sheet,i,7,ECStringUtils.toStringForNull(depositRefundData.getRefundFlowNo()));//退款流水号
	        	 setColumnValue(sheet,i,8,ECDateUtils.formatTime(depositRefundData.getRefundTime()));//退款时间
				 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=depositRefund.xls");
            ServletOutputStream os=response.getOutputStream();
            workbook.write(os);
	        os.flush();
		    os.close(); // 关闭流
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    	
    }
}
