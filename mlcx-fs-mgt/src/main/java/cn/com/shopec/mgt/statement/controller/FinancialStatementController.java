package cn.com.shopec.mgt.statement.controller;

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

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.exception.xls.XlsImportException;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECStringUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.FinaceTest;
import cn.com.shopec.core.finace.service.FinaceTestService;
import cn.com.shopec.core.finace.service.InvoiceService;
import cn.com.shopec.core.marketing.service.PricingPackageService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.order.vo.OrderStatisticalVo;
import cn.com.shopec.core.statement.model.CarRpts;
import cn.com.shopec.core.statement.model.Details;
import cn.com.shopec.core.statement.model.GroupRpts;
import cn.com.shopec.core.statement.model.Money;
import cn.com.shopec.core.statement.model.MoreBusinessSummary;
import cn.com.shopec.core.statement.model.ParkRpts;
import cn.com.shopec.core.statement.model.Pricing;
import cn.com.shopec.core.statement.service.StatementService;
import cn.com.shopec.mgt.common.BaseController;
/**
 * 财务报表
 * */
@Controller
@RequestMapping("/financialStatement")
public class FinancialStatementController extends BaseController{
	
	@Resource
	private OrderService orderService;
	
	@Resource
	private MemberService memberService;
	
	@Resource
	private InvoiceService invoiceService;
	
	@Resource
	private PricingPackageService pricingPackageService;
	
	@Resource
	private FinaceTestService finaceTestService;
	
	@Resource
	private StatementService statementService;
	
	/*
	 * 进入财务报表订单汇总页面
	 */
/*	@RequestMapping("/toSummary")
	public String toSummary(ModelMap model) {
		String days=ECDateUtils.getFirstAndLastDayOfMonth();
		if(days!=null&&!days.equals("")){
			String[] temp=days.split(",");
			model.addAttribute("startTime",temp[0]);
			model.addAttribute("endTime",temp[1]);	
		}
		return "statement/summary_list";
	}
	
	 * 显示财务报表订单汇总信息
	 
	@RequestMapping("/pageListSummary")
	@ResponseBody
	public PageFinder<Summary> pageListSummary(Summary summary,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),summary);
		return orderService.getSummaryPagedList(q);
	}
	*//**
	 * 导出财务报表订单汇总信息
	 * *//*
    @RequestMapping("/toSummaryExport")
    public void toSummaryExport(Summary summary,HttpServletRequest request, HttpServletResponse response){
    	try {
    		List<Summary> list = orderService.getSummaryList(summary);
			 // 声明一个工作薄  
			String path=request.getRealPath("/")+"res"+File.separator+"summary.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        int i=1;
	        for(Summary sm:list){
	        	 sheet.createRow(i);
	        	 //订单数，时长，订单金额，实收金额，实收金额对应时长，套餐抵扣订单数，套餐抵扣金额，套餐抵扣时长，
	        	 //冲账订单数，冲账金额，微信订单数，微信订单金额，微信实收金额，微信手续费，支付宝订单数，支付宝订单金额，支付宝实收金额，支付宝手续费
	        	 setColumnValue(sheet,i,0,ECStringUtils.toStringForNull(sm.getOrderNum()));
	        	 setColumnValue(sheet,i,1,ECStringUtils.toStringForNull(sm.getOrderDuration()));
	        	 setColumnValue(sheet,i,2,ECStringUtils.toStringForNull(sm.getOrderAmount()));
	        	 setColumnValue(sheet,i,3,ECStringUtils.toStringForNull(sm.getAlreadyAmount()));
	        	 setColumnValue(sheet,i,4,ECStringUtils.toStringForNull(sm.getAlreadyDuration()));
	        	 setColumnValue(sheet,i,5,ECStringUtils.toStringForNull(sm.getPackOrderNum()));
	        	 setColumnValue(sheet,i,6,ECStringUtils.toStringForNull(sm.getPackAmount()));
	        	 setColumnValue(sheet,i,7,ECStringUtils.toStringForNull(sm.getPackDuration()));
	        	 setColumnValue(sheet,i,8,ECStringUtils.toStringForNull(sm.getStrikeBalanceOrderNum()));
	        	 setColumnValue(sheet,i,9,ECStringUtils.toStringForNull(sm.getStrikeBalanceAmount()));
	        	 setColumnValue(sheet,i,10,ECStringUtils.toStringForNull(sm.getWxOrderNum()));
	        	 setColumnValue(sheet,i,11,ECStringUtils.toStringForNull(sm.getWxAmount()));
	        	 setColumnValue(sheet,i,12,ECStringUtils.toStringForNull(sm.getWxRealAmount()));
	        	 setColumnValue(sheet,i,13,ECStringUtils.toStringForNull(sm.getWxCharge()));
	        	 setColumnValue(sheet,i,14,ECStringUtils.toStringForNull(sm.getAlipayOrderNum()));
	        	 setColumnValue(sheet,i,15,ECStringUtils.toStringForNull(sm.getAlilpayAmount()));
	        	 setColumnValue(sheet,i,16,ECStringUtils.toStringForNull(sm.getAlilpayRealAmount()));
	        	 setColumnValue(sheet,i,17,ECStringUtils.toStringForNull(sm.getAlipayCharge()));
				 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=summary.xls");
            ServletOutputStream os=response.getOutputStream();
            workbook.write(os);
	        os.flush();
		    os.close(); // 关闭流
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    	
    }*/
    /*
	 * 进入财务报表明细页面
	 */
	@RequestMapping("/toDetails")
	public String toDetails(ModelMap model) {
		String days=ECDateUtils.getFirstAndLastDayOfMonth();
		if(days!=null&&!days.equals("")){
			String[] temp=days.split(",");
			model.addAttribute("finishTimeStart",temp[0]);
			model.addAttribute("finishTimeEnd",temp[1]);	
		}
		return "statement/details_list";
	}
	/*
	 * 显示财务报表明细信息
	 */
	@RequestMapping("/pageListDetails")
	@ResponseBody
	public PageFinder<Details> pageListDetails(Details details,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),details);
		return orderService.getDetailsPagedList(q);
	}
	/**
	 * 导出财务报表明细信息
	 * */
    @RequestMapping("/toDetailsExport")
    public void toDetailsExport(Details details,HttpServletRequest request, HttpServletResponse response){
    	try {
    		List<Details> list = orderService.getDetailsList(details);
			 // 声明一个工作薄  
			String path=request.getRealPath("/")+"res"+File.separator+"details.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        int i=1;
	        for(Details d:list){
	        	 sheet.createRow(i);
	        	 // 订单号,姓名,手机号,订单预定时间，订单开始时间，订单结束时间，订单时长，取车场站，还车场站，
	        	 //车辆编号，订单金额，抵扣金额，冲账金额，应收金额，实收金额，优惠金额，支付方式，支付流水号
	        	 setColumnValue(sheet,i,0,ECStringUtils.toStringForNull(d.getOrderNo()));
	        	 setColumnValue(sheet,i,1,ECStringUtils.toStringForNull(d.getMemberName()));
	        	 setColumnValue(sheet,i,2,ECStringUtils.toStringForNull(ECDateUtils.toDateTimeString(d.getAppointmentTime())));
	        	 setColumnValue(sheet,i,3,ECStringUtils.toStringForNull(ECDateUtils.toDateTimeString(d.getCreateTime())));
	        	 setColumnValue(sheet,i,4,ECStringUtils.toStringForNull(ECDateUtils.toDateTimeString(d.getFinishTime())));
	        	 setColumnValue(sheet,i,5,ECStringUtils.toStringForNull(d.getOrderDuration()));
	        	 setColumnValue(sheet,i,6,ECStringUtils.toStringForNull(d.getActualTakeLoacton()));
	        	 setColumnValue(sheet,i,7,ECStringUtils.toStringForNull(d.getActualTerminalLoacton()));
	        	 setColumnValue(sheet,i,8,ECStringUtils.toStringForNull(d.getCarPlateNo()));
	        	 setColumnValue(sheet,i,9,ECStringUtils.toStringForNull(d.getOrderAmount()));
	        	 setColumnValue(sheet,i,10,ECStringUtils.toStringForNull(d.getPackAmountDiscountAmount()));
	        	 setColumnValue(sheet,i,11,ECStringUtils.toStringForNull(d.getStrikeBalanceAmount()));
	        	 setColumnValue(sheet,i,12,ECStringUtils.toStringForNull(d.getPayableAmount()));
	        	 setColumnValue(sheet,i,13,ECStringUtils.toStringForNull(d.getAlreadyAmount()));
	        	 setColumnValue(sheet,i,14,ECStringUtils.toStringForNull(d.getDiscountAmount()));
	        	 String payMethod="";
	        	 if(d.getPaymentMethod()!=null){
	        		 if(d.getPaymentMethod().equals(1)){
		        		 payMethod="支付宝"; 
		        	 }else if(d.getPaymentMethod().equals(2)){
		        		 payMethod="微信"; 
		        	 } 
	        	 }
	        	 setColumnValue(sheet,i,15,ECStringUtils.toStringForNull(payMethod));
	        	 setColumnValue(sheet,i,16,ECStringUtils.toStringForNull(d.getPaymentFlowNo()));
				 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=details.xls");
            ServletOutputStream os=response.getOutputStream();
            workbook.write(os);
	        os.flush();
		    os.close(); // 关闭流
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    	
    }
    
    /*
	 * 进入财务报表场站汇总页面
	 */
	@RequestMapping("/toParkRpts")
	public String toParkRpts(ModelMap model) {
		String days=ECDateUtils.getFirstAndLastDayOfMonth();
		if(days!=null&&!days.equals("")){
			String[] temp=days.split(",");
			model.addAttribute("startTime",temp[0]);
			model.addAttribute("endTime",temp[1]);	
		}
		return "statement/parkRpts_list";
	}
	
	/*
	 * 显示财务报表场站汇总信息
	 */
	@RequestMapping("/pageListParkRpts")
	@ResponseBody
	public PageFinder<ParkRpts> pageListStations(ParkRpts parkRpts,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),parkRpts);
		return orderService.pageListParkSummary(q);
//		return orderService.getParkRptsPagedList(q);
	}
	
	/**
	 * 导出财务报表场站汇总信息
	 * */
    @RequestMapping("/toParkRptsExport")
    public void toParkRptsExport(ParkRpts parkRpts,HttpServletRequest request, HttpServletResponse response){
    	try {
    		List<ParkRpts> list = orderService.listParkSummaryExport(parkRpts);
			 // 声明一个工作薄  
			@SuppressWarnings("deprecation")
			String path=request.getRealPath("/")+"res"+File.separator+"parkSummary.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        workbook.setSheetName(0, "场站汇总");
	        int i=1;
	        for(ParkRpts pr:list){
	        	 sheet.createRow(i);
	        	 // 场站名称,场站号,时长(分钟),订单金额(元)，实际金额，实收金额对应时长，套餐抵扣订单数，套餐抵扣金额，套餐抵扣时长，
	        	 //冲账订单数，冲账金额，微信订单数，微信订单金额，微信实收金额，微信手续费，支付宝订单数，支付宝订单金额 支付宝实收金额 支付宝手续费
	  /*      	 setColumnValue(sheet,i,0,ECStringUtils.toStringForNull(pr.getParkName()));
	        	 setColumnValue(sheet,i,1,ECStringUtils.toStringForNull(pr.getStartParkNo()));
	        	 setColumnValue(sheet,i,2,ECStringUtils.toStringForNull(pr.getOrderDuration()));
	        	 setColumnValue(sheet,i,3,ECStringUtils.toStringForNull(pr.getOrderAmount()));
	        	 setColumnValue(sheet,i,4,ECStringUtils.toStringForNull(pr.getAlreadyAmount()));
	        	 setColumnValue(sheet,i,5,ECStringUtils.toStringForNull(pr.getAlreadyDuration()));
	        	 setColumnValue(sheet,i,6,ECStringUtils.toStringForNull(pr.getPackOrderNum()));
	        	 setColumnValue(sheet,i,7,ECStringUtils.toStringForNull(pr.getPackAmount()));
	        	 setColumnValue(sheet,i,8,ECStringUtils.toStringForNull(pr.getPackDuration()));
	        	 setColumnValue(sheet,i,9,ECStringUtils.toStringForNull(pr.getStrikeBalanceOrderNum()));
	        	 setColumnValue(sheet,i,10,ECStringUtils.toStringForNull(pr.getStrikeBalanceAmount()));
	        	 setColumnValue(sheet,i,11,ECStringUtils.toStringForNull(pr.getWxOrderNum()));
	        	 setColumnValue(sheet,i,12,ECStringUtils.toStringForNull(pr.getWxAmount()));
	        	 setColumnValue(sheet,i,13,ECStringUtils.toStringForNull(pr.getWxRealAmount()));
	        	 setColumnValue(sheet,i,14,ECStringUtils.toStringForNull(pr.getWxCharge()));
	        	 setColumnValue(sheet,i,15,ECStringUtils.toStringForNull(pr.getAlipayOrderNum()));
	        	 setColumnValue(sheet,i,16,ECStringUtils.toStringForNull(pr.getAlilpayAmount()));
	        	 setColumnValue(sheet,i,17,ECStringUtils.toStringForNull(pr.getAlilpayRealAmount()));
	        	 setColumnValue(sheet,i,18,ECStringUtils.toStringForNull(pr.getAlipayCharge()));*/
	        	 setColumnValue(sheet,i,0,ECStringUtils.toStringForNull(pr.getStartParkNo()));
	        	 setColumnValue(sheet,i,1,ECStringUtils.toStringForNull(pr.getParkName()));
	        	 setColumnValue(sheet,i,2,ECStringUtils.toStringForNull(pr.getOrderNum()));
	        	 setColumnValue(sheet,i,3,ECStringUtils.toStringForNull(pr.getOrderAmount()));
	        	 setColumnValue(sheet,i,4,ECStringUtils.toStringForNull(pr.getServiceFeeBack()));
	        	 setColumnValue(sheet,i,5,ECStringUtils.toStringForNull(pr.getServiceFeeGet()));
	        	 setColumnValue(sheet,i,6,ECStringUtils.toStringForNull(pr.getAlreadyAmount()));
	        	 setColumnValue(sheet,i,7,ECStringUtils.toStringForNull(pr.getBalanceNum()));
	        	 setColumnValue(sheet,i,8,ECStringUtils.toStringForNull(pr.getBalanceDiscountAmount()));
	        	 setColumnValue(sheet,i,9,ECStringUtils.toStringForNull(pr.getDiscountAmountNum()));
	        	 setColumnValue(sheet,i,10,ECStringUtils.toStringForNull(pr.getDiscountAmount()));
	        	 setColumnValue(sheet,i,11,ECStringUtils.toStringForNull(pr.getStrikeBalanceOrderNum()));
	        	 setColumnValue(sheet,i,12,ECStringUtils.toStringForNull(pr.getStrikeBalanceAmount()));
				 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=parkSummary.xls");
            ServletOutputStream os=response.getOutputStream();
            workbook.write(os);
	        os.flush();
		    os.close(); // 关闭流
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    	
    }
    
	
    
    /*
   	 * 进入财务报表车辆汇总页面
   	 */
   	@RequestMapping("/toCarRpts")
   	public String toCarRpts(ModelMap model) {
   		String days=ECDateUtils.getFirstAndLastDayOfMonth();
   		if(days!=null&&!days.equals("")){
   			String[] temp=days.split(",");
   			model.addAttribute("startTime",temp[0]);
   			model.addAttribute("endTime",temp[1]);	
   		}
   		return "statement/carRpts_list";
   	}
	
   	/*
	 * 显示财务报表车辆汇总信息
	 */
	@RequestMapping("/pageListCarRpts")
	@ResponseBody
	public PageFinder<CarRpts> pageListCarRpts(CarRpts carRpts,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),carRpts);
		return orderService.getCarRptsPagedList(q);
	}
   	
   	
	/**
	 * 导出财务报表车辆汇总信息
	 * */
    @RequestMapping("/tocCarRptsExport")
    public void toCarRptsExport(CarRpts carRpts,HttpServletRequest request, HttpServletResponse response){
    	try {
    	
    		List<CarRpts> list = orderService.getCarRptsList(carRpts);
			 // 声明一个工作薄  
			String path=request.getRealPath("/")+"res"+File.separator+"carRpts.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        int i=1;
	        for(CarRpts pr:list){
	        	 sheet.createRow(i);
	        	 // 车牌号,订单数,时长(分钟),订单金额(元)，实际金额，实收金额对应时长，套餐抵扣订单数，套餐抵扣金额，套餐抵扣时长，
	        	 //冲账订单数，冲账金额，微信订单数，微信订单金额，微信实收金额，微信手续费，支付宝订单数，支付宝订单金额 支付宝实收金额 支付宝手续费
	        	 setColumnValue(sheet,i,0,ECStringUtils.toStringForNull(pr.getCarPlateNo()));
	        	 setColumnValue(sheet,i,1,ECStringUtils.toStringForNull(pr.getOrderNum()));
	        	 setColumnValue(sheet,i,2,ECStringUtils.toStringForNull(pr.getOrderDuration()));
	        	 setColumnValue(sheet,i,3,ECStringUtils.toStringForNull(pr.getOrderAmount()));
	        	 setColumnValue(sheet,i,4,ECStringUtils.toStringForNull(pr.getAlreadyAmount()));
	        	 setColumnValue(sheet,i,5,ECStringUtils.toStringForNull(pr.getAlreadyDuration()));
	        	 setColumnValue(sheet,i,6,ECStringUtils.toStringForNull(pr.getPackOrderNum()));
	        	 setColumnValue(sheet,i,7,ECStringUtils.toStringForNull(pr.getPackAmount()));
	        	 setColumnValue(sheet,i,8,ECStringUtils.toStringForNull(pr.getPackDuration()));
	        	 setColumnValue(sheet,i,9,ECStringUtils.toStringForNull(pr.getStrikeBalanceOrderNum()));
	        	 setColumnValue(sheet,i,10,ECStringUtils.toStringForNull(pr.getStrikeBalanceAmount()));
	        	 setColumnValue(sheet,i,11,ECStringUtils.toStringForNull(pr.getWxOrderNum()));
	        	 setColumnValue(sheet,i,12,ECStringUtils.toStringForNull(pr.getWxAmount()));
	        	 setColumnValue(sheet,i,13,ECStringUtils.toStringForNull(pr.getWxRealAmount()));
	        	 setColumnValue(sheet,i,14,ECStringUtils.toStringForNull(pr.getWxCharge()));
	        	 setColumnValue(sheet,i,15,ECStringUtils.toStringForNull(pr.getAlipayOrderNum()));
	        	 setColumnValue(sheet,i,16,ECStringUtils.toStringForNull(pr.getAlilpayAmount()));
	        	 setColumnValue(sheet,i,17,ECStringUtils.toStringForNull(pr.getAlilpayRealAmount()));
	        	 setColumnValue(sheet,i,18,ECStringUtils.toStringForNull(pr.getAlipayCharge()));
				 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=carRpts.xls");
            ServletOutputStream os=response.getOutputStream();
            workbook.write(os);
	        os.flush();
		    os.close(); // 关闭流
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    	
    }
    
    
    /*
   	 * 进入财务报表 集团 汇总页面
   	 */
   	@RequestMapping("/toGroupRpts")
   	public String toGroupRpts(ModelMap model) {
   		String days=ECDateUtils.getFirstAndLastDayOfMonth();
   		if(days!=null&&!days.equals("")){
   			String[] temp=days.split(",");
   			model.addAttribute("startTime",temp[0]);
   			model.addAttribute("endTime",temp[1]);	
   		}
   		return "statement/groupRpts_list";
   	}
	
	
 	/*
	 * 显示财务报表 集团 汇总信息
	 */
	@RequestMapping("/pageListGroupRpts")
	@ResponseBody
	public PageFinder<GroupRpts> pageListGroupRpts(GroupRpts groupRpts,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),groupRpts);
		return orderService.getGroupRptsPagedList(q);
	}
   	
   	
	/**
	 * 导出财务报表 集团 汇总信息
	 * */
    @RequestMapping("/toGroupRptsExport")
    public void tocGroupRptsExport(GroupRpts groupRpts,HttpServletRequest request, HttpServletResponse response){
    	try {
    	
    		List<GroupRpts> list = orderService.getGroupRptsList(groupRpts);
			 // 声明一个工作薄  
			String path=request.getRealPath("/")+"res"+File.separator+"groupRpts.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        int i=1;
	        for(GroupRpts pr:list){
	        	 sheet.createRow(i);
	        	 // 集团名称 ,订单数,时长(分钟),订单金额(元)，实际金额，实收金额对应时长，套餐抵扣订单数，套餐抵扣金额，套餐抵扣时长，
	        	 //冲账订单数，冲账金额，微信订单数，微信订单金额，微信实收金额，微信手续费，支付宝订单数，支付宝订单金额 支付宝实收金额 支付宝手续费  时长平均值  金额平均值
	        	 setColumnValue(sheet,i,0,ECStringUtils.toStringForNull(pr.getCompanyName()));
	        	 setColumnValue(sheet,i,1,ECStringUtils.toStringForNull(pr.getOrderNum()));
	        	 setColumnValue(sheet,i,2,ECStringUtils.toStringForNull(pr.getOrderDuration()));
	        	 setColumnValue(sheet,i,3,ECStringUtils.toStringForNull(pr.getOrderAmount()));
	        	 setColumnValue(sheet,i,4,ECStringUtils.toStringForNull(pr.getAlreadyAmount()));
	        	 setColumnValue(sheet,i,5,ECStringUtils.toStringForNull(pr.getAlreadyDuration()));
	        	 setColumnValue(sheet,i,6,ECStringUtils.toStringForNull(pr.getPackOrderNum()));
	        	 setColumnValue(sheet,i,7,ECStringUtils.toStringForNull(pr.getPackAmount()));
	        	 setColumnValue(sheet,i,8,ECStringUtils.toStringForNull(pr.getPackDuration()));
	        	 setColumnValue(sheet,i,9,ECStringUtils.toStringForNull(pr.getStrikeBalanceOrderNum()));
	        	 setColumnValue(sheet,i,10,ECStringUtils.toStringForNull(pr.getStrikeBalanceAmount()));
	        	 setColumnValue(sheet,i,11,ECStringUtils.toStringForNull(pr.getWxOrderNum()));
	        	 setColumnValue(sheet,i,12,ECStringUtils.toStringForNull(pr.getWxAmount()));
	        	 setColumnValue(sheet,i,13,ECStringUtils.toStringForNull(pr.getWxRealAmount()));
	        	 setColumnValue(sheet,i,14,ECStringUtils.toStringForNull(pr.getWxCharge()));
	        	 setColumnValue(sheet,i,15,ECStringUtils.toStringForNull(pr.getAlipayOrderNum()));
	        	 setColumnValue(sheet,i,16,ECStringUtils.toStringForNull(pr.getAlilpayAmount()));
	        	 setColumnValue(sheet,i,17,ECStringUtils.toStringForNull(pr.getAlilpayRealAmount()));
	        	 setColumnValue(sheet,i,18,ECStringUtils.toStringForNull(pr.getAlipayCharge()));
	        	 setColumnValue(sheet,i,19,ECStringUtils.toStringForNull(pr.getOrderDurationAvg()));
	        	 setColumnValue(sheet,i,20,ECStringUtils.toStringForNull(pr.getOrderAmountAvg()));
				 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=groupRpts.xls");
            ServletOutputStream os=response.getOutputStream();
            workbook.write(os);
	        os.flush();
		    os.close(); // 关闭流
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    	
    }
    
	
	
    /*
  	 * 进入财务报表资金页面
  	 */
  	@RequestMapping("/toMoney")
  	public String toMoney(ModelMap model) {
  		String days=ECDateUtils.getFirstAndLastDayOfMonth();
  		if(days!=null&&!days.equals("")){
  			String[] temp=days.split(",");
  			model.addAttribute("startTime",temp[0]);
  			model.addAttribute("endTime",temp[1]);	
  		}
  		return "statement/money_list";
  	}
  	/*
  	 * 显示财务报表资金信息
  	 */
  	@RequestMapping("/moneyDetails")
  	@ResponseBody
  	public ResultInfo<Money> moneyDetails(Money money) throws ParseException {
  		return orderService.getMoneyDetails(money);
  	}
  	/**
	 * 导出财务报表资金信息
	 * */
    @RequestMapping("/toMoneyExport")
    public void toMoneyExport(Money money,HttpServletRequest request, HttpServletResponse response){
    	try {
    		ResultInfo<Money> resultInfo = orderService.getMoneyDetails(money);
			 // 声明一个工作薄  
			String path=request.getRealPath("/")+"res"+File.separator+"money.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        setColumnValue(sheet,2,2,ECStringUtils.toStringForNull(money.getOrderAlipayAmount()));
	        setColumnValue(sheet,2,3,ECStringUtils.toStringForNull(money.getPriceAlipayAmount()));
	        setColumnValue(sheet,2,4,ECStringUtils.toStringForNull(money.getDepositAlipayAmount()));
	        setColumnValue(sheet,2,5,ECStringUtils.toStringForNull(money.getDepositAlipayRefund()));
	        setColumnValue(sheet,2,6,ECStringUtils.toStringForNull(money.getOrderAlipayCharge()));
	        setColumnValue(sheet,2,7,ECStringUtils.toStringForNull(money.getPriceAlipayCharge()));
	        setColumnValue(sheet,2,8,ECStringUtils.toStringForNull(money.getDepositAlipayCharge()));
	        setColumnValue(sheet,2,10,ECStringUtils.toStringForNull(money.getAlipayAmount()));
	        
	        setColumnValue(sheet,6,2,ECStringUtils.toStringForNull(money.getOrderWxAmount()));
	        setColumnValue(sheet,6,3,ECStringUtils.toStringForNull(money.getPriceWxAmount()));
	        setColumnValue(sheet,6,4,ECStringUtils.toStringForNull(money.getDepositWxAmount()));
	        setColumnValue(sheet,6,5,ECStringUtils.toStringForNull(money.getDepositWxRefund()));
	        setColumnValue(sheet,6,6,ECStringUtils.toStringForNull(money.getOrderWxCharge()));
	        setColumnValue(sheet,6,7,ECStringUtils.toStringForNull(money.getPriceWxCharge()));
	        setColumnValue(sheet,6,8,ECStringUtils.toStringForNull(money.getDepositWxCharge()));
	        setColumnValue(sheet,6,9,ECStringUtils.toStringForNull(money.getChargeRefund()));
	        setColumnValue(sheet,6,10,ECStringUtils.toStringForNull(money.getWxAmount()));
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=money.xls");
            ServletOutputStream os=response.getOutputStream();
            workbook.write(os);
	        os.flush();
		    os.close(); // 关闭流
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    	
    }
    /*
	 * 进入财务报表套餐汇总页面
	 */
	@RequestMapping("/toPricing")
	public String toPricing(ModelMap model) {
		String days=ECDateUtils.getFirstAndLastDayOfMonth();
		if(days!=null&&!days.equals("")){
			String[] temp=days.split(",");
			model.addAttribute("startTime",temp[0]);
			model.addAttribute("endTime",temp[1]);	
		}
		return "statement/pricing_list";
	}
	/*
	 * 显示财务报表套餐汇总信息
	 */
	@RequestMapping("/pageListPricing")
	@ResponseBody
	public PageFinder<Pricing> pageListPricing(Pricing pricing,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),pricing);
		return pricingPackageService.getPricingPagedList(q);
	}    	
	
    /*
  	 * 进入财务报表收支汇总页面
  	 */
  	@RequestMapping("/toIncomeTotal")
  	public String toIncomeTotal(ModelMap model) {
  		String days=ECDateUtils.getFirstAndLastDayOfMonth();
  		if(days!=null&&!days.equals("")){
  			String[] temp=days.split(",");
  			model.addAttribute("startTime",temp[0]);
  			model.addAttribute("endTime",temp[1]);	
  		}
  		return "statement/income_total";
  	}
	
	/**
	 * 批量导入转账数据
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("importTransferOfAccount")
	@ResponseBody
	public ResultInfo<FinaceTest> importTransferOfAccount(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		ResultInfo<FinaceTest> resultInfo = null;
		try {
			resultInfo = finaceTestService.importTransferOfAccount(file);
		} catch (XlsImportException e) {
			resultInfo = new ResultInfo<FinaceTest>();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.getErrorMsg());
		}
		return resultInfo;
	}
	
  	/**
  	 * 查询收支汇总
  	 * 
  	 * @param money
  	 * @return
  	 * @throws ParseException
  	 */
  	@RequestMapping("/getIncomeTotal")
  	@ResponseBody
  	public ResultInfo<Money> getIncomeTotal(Money money) throws ParseException {
  		return orderService.getIncomeTotal(money);
  	}
  	
  	/**
  	 * 订单汇总---新修改
  	 */
  	@RequestMapping("/toOrderStatistical")
	public String toOrderStatistical(ModelMap model) {
		String days=ECDateUtils.getFirstAndLastDayOfMonth();
		if(days != null && !days.equals("")){
			String[] temp=days.split(",");
			model.addAttribute("startTime",temp[0]);
			model.addAttribute("endTime",temp[1]);	
		}
		return "statement/orderStat_list";
	}
  	
  	@RequestMapping("/pageListOrderStat")
	@ResponseBody
	public PageFinder<OrderStatisticalVo> pageListOrderStat(OrderStatisticalVo order,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),order);
		return orderService.pageListOrderStat(q);
	}
  	
    @RequestMapping("/toOrderStatisticalExport")
    public void toOrderStatisticalExport(OrderStatisticalVo orderVo,HttpServletRequest request, HttpServletResponse response){
    	try {
    		List<OrderStatisticalVo> list = orderService.getOrderExportStatList(orderVo);
			 // 声明一个工作薄  
			@SuppressWarnings("deprecation")
			String path=request.getRealPath("/")+"res"+File.separator+"orderSummary.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        workbook.setSheetName(0, "订单汇总");
	        int i=1;
	        for(OrderStatisticalVo order:list){
	        	 sheet.createRow(i);
	        	 //订单数，总金额（元），已实收金额（元），余额抵扣单数，余额抵扣额（元），券抵扣单数，券抵扣额（元）
	        	 //冲账单数，冲账额（元），未支付单数，未支付金额（元）
	        	 setColumnValue(sheet,i,0,ECStringUtils.toStringForNull(order.getOrderNo()));
	        	 setColumnValue(sheet,i,1,ECStringUtils.toStringForNull(order.getOrderAmount()));
	        	 setColumnValue(sheet,i,2,ECStringUtils.toStringForNull(order.getPayableAmount()));
	        	 setColumnValue(sheet,i,3,ECStringUtils.toStringForNull(order.getBalanceNum()));
	        	 setColumnValue(sheet,i,4,ECStringUtils.toStringForNull(order.getBalanceDiscountAmount()));
	        	 setColumnValue(sheet,i,5,ECStringUtils.toStringForNull(order.getDiscountAmountNum()));
	        	 setColumnValue(sheet,i,6,ECStringUtils.toStringForNull(order.getDiscountAmount()));
	        	 setColumnValue(sheet,i,7,ECStringUtils.toStringForNull(order.getStrikeBalanceOrderNum()));
	        	 setColumnValue(sheet,i,8,ECStringUtils.toStringForNull(order.getStrikeBalanceAmount()));
	        	 setColumnValue(sheet,i,9,ECStringUtils.toStringForNull(order.getNoPayOrderNum()));
	        	 setColumnValue(sheet,i,10,ECStringUtils.toStringForNull(order.getNoPayAmount()));
				 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=orderSummary.xls");
            ServletOutputStream os=response.getOutputStream();
            workbook.write(os);
	        os.flush();
		    os.close(); // 关闭流
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    	
    }
    
    /**
     * 加载资金汇总报表页
     * 默认查询最近一月的资金汇总
     */
    @RequestMapping("/toMoneySummaryList")
	public String toMoneySummaryList(ModelMap model) {
		String days=ECDateUtils.getFirstAndLastDayOfMonth();
		if(days!=null&&!days.equals("")){
			String[] temp=days.split(",");
			model.addAttribute("finishTimeStart",temp[0]);
			model.addAttribute("finishTimeEnd",temp[1]);	
		}
		return "statement/moneySummary_list";
	}
	/**
	 * 获取资金汇总报表分页数据
	 */
	@RequestMapping("/pageListMoneySummary")
	@ResponseBody
	public PageFinder<MoreBusinessSummary> pageListMoneySummary(MoreBusinessSummary moreBusinessSummary,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),moreBusinessSummary);
		return statementService.pageListMoneySummary(q);
	}
	
	/**
	 * 获取资金汇总最近12个月的数据，按月份倒排
	 */
	@RequestMapping("/pageListMoneySummaryMonth")
	@ResponseBody
	public PageFinder<MoreBusinessSummary> pageListMoneySummaryMonth(MoreBusinessSummary moreBusinessSummary,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),moreBusinessSummary);
		if(StringUtils.isBlank(moreBusinessSummary.getStartMonth())){
			Date startTime = ECDateUtils.getMonthBefore(new Date(), 12);
			Date endTime = ECDateUtils.getTheMonthDate(new Date());
			String sMonth = ECDateUtils.formatDate(startTime, "yyyy-MM-dd");
			String eMonth = ECDateUtils.formatDate(endTime, "yyyy-MM-dd");
			moreBusinessSummary.setStartMonth(sMonth.substring(0, 7));
			moreBusinessSummary.setEndMonth(eMonth.substring(0, 7));
		} 
		return statementService.pageListMoneySummaryMonth(q);
	}
	
	/**
	 * 获取资金汇总 默认查询最近30天数据；按日期倒排
	 */
	@RequestMapping("/pageListMoneySummaryDay")
	@ResponseBody
	public PageFinder<MoreBusinessSummary> pageListMoneySummaryDay(MoreBusinessSummary moreBusinessSummary,Query query) throws ParseException {
		if(moreBusinessSummary.getStartTime() == null){
			String days=ECDateUtils.getFirstAndLastDayOfMonth();
			if(days!=null&&!days.equals("")){
				String[] temp=days.split(",");
				moreBusinessSummary.setStartTime(ECDateUtils.parseDate(temp[0]));
				moreBusinessSummary.setEndTime(ECDateUtils.parseDate(temp[1]));
			}
		}
		Query q = new Query(query.getPageNo(),query.getPageSize(),moreBusinessSummary);
		return statementService.pageListMoneySummaryDay(q);
	}
	
	/**
	 * 默认最近一个月资金汇总数据导出
	 */
	@RequestMapping("/moneySummaryDefaultExport")
    public void moneySummaryDefaultExport(MoreBusinessSummary moreBusinessSummary,HttpServletRequest request, HttpServletResponse response){
    	try {
    		if(moreBusinessSummary.getStartTime() == null){
    			String days=ECDateUtils.getFirstAndLastDayOfMonth();
    			if(days!=null&&!days.equals("")){
    				String[] temp=days.split(",");
    				moreBusinessSummary.setStartTime(ECDateUtils.parseDate(temp[0]));
    				moreBusinessSummary.setEndTime(ECDateUtils.parseDate(temp[1]));
    			}
    		}
    		List<MoreBusinessSummary> list = statementService.listMoneySummaryDefault(moreBusinessSummary);
			 // 声明一个工作薄  
			@SuppressWarnings("deprecation")
			String path=request.getRealPath("/")+"res"+File.separator+"moneySummaryDefault.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        workbook.setSheetName(0, "资金汇总");
	        int i=1;
	        for(MoreBusinessSummary mbs:list){
	        	 sheet.createRow(i);
	        	 //订单数，订单实收金额(元) 充值笔数 充值实收金额（元） 交押金笔数 交押金金额（元） 退押金笔数 退押金金额（元）
	        	 setColumnValue(sheet,i,0,ECStringUtils.stringForNull(mbs.getOrderNum()));
	        	 setColumnValue(sheet,i,1,ECStringUtils.stringForNull(mbs.getAlreadyAmount()));
	        	 setColumnValue(sheet,i,2,ECStringUtils.stringForNull(mbs.getPackageRechargeNum()));
	        	 setColumnValue(sheet,i,3,ECStringUtils.stringForNull(mbs.getPackageAlreadyAmount()));
	        	 setColumnValue(sheet,i,4,ECStringUtils.stringForNull(mbs.getPayDepositNum()));
	        	 setColumnValue(sheet,i,5,ECStringUtils.stringForNull(mbs.getPayDepositAmount()));
	        	 setColumnValue(sheet,i,6,ECStringUtils.stringForNull(mbs.getRefundDepositNum()));
	        	 setColumnValue(sheet,i,7,ECStringUtils.stringForNull(mbs.getRefundDepositAmount()));
				 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=moneySummaryDefault.xls");
            ServletOutputStream os=response.getOutputStream();
            workbook.write(os);
	        os.flush();
		    os.close(); // 关闭流
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    	
    }
	
	/**
	 * 资金汇总 按月分组导出数据
	 */
	@RequestMapping("/moneySummaryMonthExport")
    public void moneySummaryMonthExport(MoreBusinessSummary moreBusinessSummary,HttpServletRequest request, HttpServletResponse response){
    	try {
    		if(StringUtils.isBlank(moreBusinessSummary.getStartMonth())){
    			Date startTime = ECDateUtils.getMonthBefore(new Date(), 12);
    			Date endTime = ECDateUtils.getTheMonthDate(new Date());
    			String sMonth = ECDateUtils.formatDate(startTime, "yyyy-MM-dd");
    			String eMonth = ECDateUtils.formatDate(endTime, "yyyy-MM-dd");
    			moreBusinessSummary.setStartMonth(sMonth.substring(0, 7));
    			moreBusinessSummary.setEndMonth(eMonth.substring(0, 7));
    		} 
    		List<MoreBusinessSummary> list = statementService.listMoneySummaryMonthExport(moreBusinessSummary);
			 // 声明一个工作薄  
			@SuppressWarnings("deprecation")
			String path=request.getRealPath("/")+"res"+File.separator+"moneySummaryMonth.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        workbook.setSheetName(0, "资金汇总");
	        int i=1;
	        for(MoreBusinessSummary mbs:list){
	        	 sheet.createRow(i);
	        	 //月份 订单数，订单实收金额(元) 充值笔数 充值实收金额（元） 交押金笔数 交押金金额;
	        	 setColumnValue(sheet,i,0,ECStringUtils.stringForNull(mbs.getMonths()));
	        	 setColumnValue(sheet,i,1,ECStringUtils.stringForNull(mbs.getOrderNum()));
	        	 setColumnValue(sheet,i,2,ECStringUtils.stringForNull(mbs.getAlreadyAmount()));
	        	 setColumnValue(sheet,i,3,ECStringUtils.stringForNull(mbs.getPackageRechargeNum()));
	        	 setColumnValue(sheet,i,4,ECStringUtils.stringForNull(mbs.getPackageAlreadyAmount()));
	        	 setColumnValue(sheet,i,5,ECStringUtils.stringForNull(mbs.getPayDepositNum()));
	        	 setColumnValue(sheet,i,6,ECStringUtils.stringForNull(mbs.getPayDepositAmount()));
	        	 setColumnValue(sheet,i,7,ECStringUtils.stringForNull(mbs.getRefundDepositNum()));
	        	 setColumnValue(sheet,i,8,ECStringUtils.stringForNull(mbs.getRefundDepositAmount()));
				 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=moneySummaryMonth.xls");
            ServletOutputStream os=response.getOutputStream();
            workbook.write(os);
	        os.flush();
		    os.close(); // 关闭流
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    	
    }
	
	/**
	 * 资金汇总 默认30天数据查询导出  也可根据页面查询时间导出数据
	 */
	@RequestMapping("/moneySummaryDayExport")
    public void moneySummaryDayExport(MoreBusinessSummary moreBusinessSummary,HttpServletRequest request, HttpServletResponse response){
    	try {
    		if(moreBusinessSummary.getStartTime() == null){
    			String days=ECDateUtils.getFirstAndLastDayOfMonth();
    			if(days!=null&&!days.equals("")){
    				String[] temp=days.split(",");
    				moreBusinessSummary.setStartTime(ECDateUtils.parseDate(temp[0]));
    				moreBusinessSummary.setEndTime(ECDateUtils.parseDate(temp[1]));
    			}
    		}
    		List<MoreBusinessSummary> list = statementService.listMoneySummaryDayExport(moreBusinessSummary);
			 // 声明一个工作薄  
			@SuppressWarnings("deprecation")
			String path=request.getRealPath("/")+"res"+File.separator+"moneySummaryDay.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        workbook.setSheetName(0, "资金汇总");
	        int i=1;
	        for(MoreBusinessSummary mbs:list){
	        	 sheet.createRow(i);
	        	 //日期 订单数，订单实收金额(元) 充值笔数 充值实收金额（元） 交押金笔数 交押金金额));
	        	 setColumnValue(sheet,i,0,ECStringUtils.stringForNull(mbs.getDays()));
	        	 setColumnValue(sheet,i,1,ECStringUtils.stringForNull(mbs.getOrderNum()));
	        	 setColumnValue(sheet,i,2,ECStringUtils.stringForNull(mbs.getAlreadyAmount()));
	        	 setColumnValue(sheet,i,3,ECStringUtils.stringForNull(mbs.getPackageRechargeNum()));
	        	 setColumnValue(sheet,i,4,ECStringUtils.stringForNull(mbs.getPackageAlreadyAmount()));
	        	 setColumnValue(sheet,i,5,ECStringUtils.stringForNull(mbs.getPayDepositNum()));
	        	 setColumnValue(sheet,i,6,ECStringUtils.stringForNull(mbs.getPayDepositAmount()));
	        	 setColumnValue(sheet,i,7,ECStringUtils.stringForNull(mbs.getRefundDepositNum()));
	        	 setColumnValue(sheet,i,8,ECStringUtils.stringForNull(mbs.getRefundDepositAmount()));
				 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=moneySummaryDay.xls");
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