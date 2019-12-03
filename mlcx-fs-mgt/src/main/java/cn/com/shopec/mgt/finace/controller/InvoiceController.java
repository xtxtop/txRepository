package cn.com.shopec.mgt.finace.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import cn.com.shopec.core.finace.model.Invoice;
import cn.com.shopec.core.finace.service.FinaceTestService;
import cn.com.shopec.core.finace.service.InvoiceService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.DataDictCatService;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mgt.common.BaseController;
@Controller
@RequestMapping("/invoice")
public class InvoiceController extends BaseController{
	@Resource
	public InvoiceService invoiceService;
	@Resource
	private DataDictCatService dataDictCatService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private OrderService orderService;
	@Resource
	private PricingPackOrderService pricingPackOrderService;
	@Resource
	private MemberService memberService;
	@Resource
	private FinaceTestService finaceTestService;
	/*
	 * 进入发票开票列表页面
	 */
	@RequestMapping("/toInvoiceList")
	public String mainPage(String invoiceStatus, ModelMap modelMap) {
		//获取对账周期的下拉框列表
		SysParam sysParamStart = sysParamService.getByParamKey("INVOICE_START_TIME");
		int start=Integer.parseInt(sysParamStart.getParamValue());
		SysParam sysParamEnd = sysParamService.getByParamKey("INVOICE_END_TIME");
		int end=Integer.parseInt(sysParamEnd.getParamValue());
		List<String> invoiceTimeList=ECDateUtils.getLast12Months(start,end);
		modelMap.put("invoiceTimeList", invoiceTimeList);
		modelMap.put("invoiceStatus", invoiceStatus);
		return "finace/invoice_list";
	}
	/*
	 * 显示发票开票列表信息
	 */
	@RequestMapping("/pageListInvoice")
	@ResponseBody
	public PageFinder<Invoice> pageListInvoice(@ModelAttribute("invoice") Invoice invoice,Query query) throws ParseException {
		     //判断对账周期
				if(!"".equals(ECDateUtils.formatDate(invoice.getInvoiceTimeLeadStart()))){
					invoice.setInvoiceTimeStart(invoice.getInvoiceTimeLeadStart());
				}
				if(!"".equals(ECDateUtils.formatDate(invoice.getInvoiceTimeLeadEnd()))){
					invoice.setInvoiceTimeEnd(invoice.getInvoiceTimeLeadEnd());
				}
		Query q = new Query(query.getPageNo(),query.getPageSize(),invoice);
		return invoiceService.getInvoicePagedList(q);
	}
	/*
	 * 进入发票开票记录详情页
	 */
	@RequestMapping("/toInvoiceView")
	public String toInvoiceView(String invoiceId,ModelMap modelMap) {
		Invoice invoice=invoiceService.getInvoice(invoiceId);
		modelMap.put("invoice", invoice);
		return "finace/invoice_view";
	}
//	/**
//	 * 车主信息批量导入
//	 * @param file
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("importCarOwner")
//	@ResponseBody
//	public ResultInfo<CarOwner> importItems(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
//		ResultInfo<CarOwner> resultInfo = null;
//		try {
//			resultInfo = carOwnerService.importCarOwner(file, getOperator());
//		} catch (XlsImportException e) {
//			resultInfo = new ResultInfo<CarOwner>();
//			resultInfo.setCode(Constant.FAIL);
//			resultInfo.setMsg(e.getErrorMsg());
//		}
//		
//		return resultInfo;
//	}
	/*
	 * 进入历史开票列表页面
	 */
	@RequestMapping("/toInvoiceHistoryList")
	public String toInvoiceHistoryList(ModelMap modelMap) {
		//获取对账周期的下拉框列表
				SysParam sysParamStart = sysParamService.getByParamKey("INVOICE_START_TIME");
				int start=Integer.parseInt(sysParamStart.getParamValue());
				SysParam sysParamEnd = sysParamService.getByParamKey("INVOICE_END_TIME");
				int end=Integer.parseInt(sysParamEnd.getParamValue());
				List<String> invoiceTimeList=ECDateUtils.getLast12Months(start,end);
				modelMap.put("invoiceTimeList", invoiceTimeList);
		return "finace/invoicerHistory_list";
	}
	/*
	 * 显示历史开票列表信息
	 */
	@RequestMapping("/pageListInvoiceHistory")
	@ResponseBody
	public PageFinder<Invoice> pageListInvoiceHistory(@ModelAttribute("invoice") Invoice invoice,Query query) throws ParseException {
		invoice.setInvoiceStatus(1);//已开票
		  //判断对账周期
		if(!"".equals(ECDateUtils.formatDate(invoice.getInvoiceTimeLeadStart()))){
			//invoice.setOrderTimeStart(invoice.getInvoiceTimeLeadStart());
			invoice.setInvoiceTimeStart(invoice.getInvoiceTimeLeadStart());
		}
		if(!"".equals(ECDateUtils.formatDate(invoice.getInvoiceTimeLeadEnd()))){
			invoice.setInvoiceTimeEnd(invoice.getInvoiceTimeLeadEnd());
		}
		Query q = new Query(query.getPageNo(),query.getPageSize(),invoice);
		return invoiceService.getInvoicePagedList(q);
	}
	/*
	 * 进入历史开票记录详情页
	 */
	@RequestMapping("/toInvoiceHistoryView")
	public String toInvoiceHistoryView(String invoiceId,ModelMap modelMap) {
		Invoice invoice=invoiceService.getInvoice(invoiceId);
		modelMap.put("invoice", invoice);
		return "finace/invoiceHistory_view";
	}
	 /**
     * 导出 历史开票记录详情页
     */
    @RequestMapping("/toInvoiceHistoryExport")
    public void toInvoiceHistoryExport(@ModelAttribute("invoice") Invoice invoice,HttpServletRequest request, HttpServletResponse response){
    	try {
//    		String  createTimeStart=ECDateUtils.formatStringTime(invoice.getInvoiceTimeStart());;
//    		String  createTimeEnd=ECDateUtils.formatStringTime(invoice.getInvoiceTimeEnd());
			Query q=new Query();
			q.setQ(invoice);
			List<Invoice> invoices=invoiceService.getInvoicePagedList(q).getData();
			 // 声明一个工作薄  
			String path=request.getRealPath("/")+"res"+File.separator+"invoiceHistory.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        int i=2;
	        for(Invoice invoiceData:invoices){
	        	 sheet.createRow(i);
	        	 setColumnValue(sheet,i,0,ECStringUtils.toStringForNull(invoiceData.getInvoiceTitle()));
				 setColumnValue(sheet,i,1,ECStringUtils.toStringForNull(invoiceData.getInvoiceType()==1?"增值税普通发票":"增值税专用发票"));
				 setColumnValue(sheet,i,2,ECStringUtils.toStringForNull(invoiceData.getInvoiceAmount()));
				 setColumnValue(sheet,i,3,ECStringUtils.toStringForNull(invoiceData.getBizObjId()));
				 setColumnValue(sheet,i,4,ECStringUtils.toStringForNull(invoiceData.getOrderAmount()));
				 setColumnValue(sheet,i,5,ECDateUtils.formatDate(invoiceData.getOrderTime()));
				 setColumnValue(sheet,i,6,ECDateUtils.formatDate(invoiceData.getInvoiceTime()));
				 setColumnValue(sheet,i,7,ECStringUtils.toStringForNull(invoiceData.getInvoiceStatus()==0?"未开票":"已开票"));
				 setColumnValue(sheet,i,8,ECStringUtils.toStringForNull(invoiceData.getInvoiceNo()));
				 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=invoiceHistory.xls");
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
	 * 批量导入发票开票信息
	 * @param file
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("importinvoice")
	@ResponseBody
	public ResultInfo<Invoice> importinvoice(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
		ResultInfo<Invoice> resultInfo = new ResultInfo<Invoice>();
		if(file != null){
			try {
				resultInfo = invoiceService.importInvoice(file, getOperator());
			} catch (XlsImportException e) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(e.getErrorMsg());
			}
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("请选择文件！");
		}
		
		return resultInfo;
	}
	 /**
     * 导出 开票记录
     */
    @RequestMapping("/toInvoiceExport")
    public void toInvoiceExport(@ModelAttribute("invoice") Invoice invoice,HttpServletRequest request, HttpServletResponse response){
    	try {
			Query q=new Query();
			//查询未开发票的信息
			q.setQ(invoice);
			List<Invoice> invoices=invoiceService.getInvoiceList(q);
			 // 声明一个工作薄  
			String path=request.getRealPath("/")+"res"+File.separator+"invoice.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        int j=1;//单号循环
	        int a=1;//单号循环开始
	        int b=1;//单号循环结束
	        for(Invoice invoiceData:invoices){
	        	String[] bizObjIds=invoiceData.getBizObjId().split(",");
	        	if(bizObjIds.length==1){
	        		b=a;
	        	}else{
	        		b=a+bizObjIds.length-1;
	        	}
	        	//创建数据
	        	//开票类型1订单
	        	if(bizObjIds!=null&&bizObjIds.length>0&&invoiceData.getBizObjType()==1){
	        		for(String bizObjId:bizObjIds){
	        			/*
	        			 * inA.setInvoiceNo(invoiceNo);
							inA.setTaxpayerNo(taxpayerNo);
							inA.setAccountBank(accountBank);
							inA.setAccountNo(accountNo);
							inA.setFax(fax);
							inA.setPostcode(postcode);*/
	        			sheet.createRow(j);//创建一行
	        			Order order=orderService.getOrder(bizObjId);
	        			if(order != null){
	        				setColumnValue(sheet,j,0,ECStringUtils.toStringForNull(bizObjId));
		        			setColumnValue(sheet,j,1,ECStringUtils.toStringForNull(order.getPayableAmount()));
		        			setColumnValue(sheet,j,2,ECStringUtils.toStringForNull(order.getMemberName()==null||order.getMemberName()=="" ?"":order.getMemberName()));
		        			setColumnValue(sheet,j,3,ECDateUtils.formatDate(order.getCreateTime()));
		        			setColumnValue(sheet,j,4,ECStringUtils.toStringForNull(order.getOrderDuration()));
	        			}
	        		    
	        			setColumnValue(sheet,j,5,ECStringUtils.toStringForNull(invoiceData.getInvoiceId()));
	        			if(invoiceData.getInvoiceStatus().equals(1)){
	        				setColumnValue(sheet,j,6,ECStringUtils.toStringForNull(invoiceData.getInvoiceNo()));
	        			}else{
	        				setColumnValue(sheet,j,6,ECStringUtils.toStringForNull(""));
	        			}
	        			setColumnValue(sheet,j,7,ECStringUtils.toStringForNull(invoiceData.getInvoiceType()==1?"增值税普通发票":"增值税专用发票"));
	        			setColumnValue(sheet,j,8,ECStringUtils.toStringForNull(invoiceData.getInvoiceTitle()));
	        			if(invoiceData.getTaxpayerNo() != null && !"".equals(invoiceData.getTaxpayerNo())){
	        				setColumnValue(sheet,j,9,ECStringUtils.toStringForNull(invoiceData.getTaxpayerNo()));
	        			}else{
	        				setColumnValue(sheet,j,9,ECStringUtils.toStringForNull(""));
	        			}
	        			
	        			if(invoiceData.getAccountBank() != null && !"".equals(invoiceData.getAccountBank())){
	        				setColumnValue(sheet,j,10,ECStringUtils.toStringForNull(invoiceData.getAccountBank()));
	        			}else{
	        				setColumnValue(sheet,j,10,ECStringUtils.toStringForNull(""));
	        			}
	        			setColumnValue(sheet,j,11,ECStringUtils.toStringForNull(invoiceData.getAddress()));
	        			if(invoiceData.getInvoiceStatus().equals(1)){
	        				setColumnValue(sheet,j,12,ECStringUtils.toStringForNull(invoiceData.getFax()));
		        			setColumnValue(sheet,j,13,ECStringUtils.toStringForNull(invoiceData.getPostcode()));
	        			}else{
	        				setColumnValue(sheet,j,12,ECStringUtils.toStringForNull(""));
		        			setColumnValue(sheet,j,13,ECStringUtils.toStringForNull(""));
	        			}
	        			setColumnValue(sheet,j,14,ECStringUtils.toStringForNull(memberService.getMember(invoiceData.getMemberNo()).getMemberName()));
	        			setColumnValue(sheet,j,15,ECStringUtils.toStringForNull(invoiceData.getInvoiceStatus()==0?"未开票":"已开票"));
	        			setColumnValue(sheet,j,16,ECStringUtils.toStringForNull(invoiceData.getInvoiceAmount()));
	        			j++;
	        		}
	        	}else if(bizObjIds!=null&&bizObjIds.length>0&&invoiceData.getBizObjType()==2){//开票类型2套餐
	        		for(String bizObjId:bizObjIds){
	        			 sheet.createRow(j);
			        	 PricingPackOrder pricingPackOrder=pricingPackOrderService.getPricingPackOrder(bizObjId);
        				 setColumnValue(sheet,j,0,ECStringUtils.toStringForNull(bizObjId));
        				 setColumnValue(sheet,j,1,ECStringUtils.toStringForNull(pricingPackOrder.getPayableAmount()));
        				 setColumnValue(sheet,j,2,ECStringUtils.toStringForNull(pricingPackOrder.getMemberName()));
        				 setColumnValue(sheet,j,3,ECDateUtils.formatDate(pricingPackOrder.getCreateTime()));
        				 setColumnValue(sheet,j,4,ECStringUtils.toStringForNull(""));
        				 setColumnValue(sheet,j,5,ECStringUtils.toStringForNull(invoiceData.getInvoiceId()));
        				 if(invoiceData.getInvoiceStatus().equals(1)){
        					 setColumnValue(sheet,j,6,ECStringUtils.toStringForNull(invoiceData.getInvoiceNo()));
        				 }else{
        					 setColumnValue(sheet,j,6,ECStringUtils.toStringForNull(""));
        				 }
        				 setColumnValue(sheet,j,7,ECStringUtils.toStringForNull(invoiceData.getInvoiceType()==1?"增值税普通发票":"增值税专用发票"));
    					 setColumnValue(sheet,j,8,ECStringUtils.toStringForNull(invoiceData.getInvoiceTitle()));
    					 if(invoiceData.getInvoiceStatus().equals(1)){
    						 setColumnValue(sheet,j,9,ECStringUtils.toStringForNull(invoiceData.getTaxpayerNo()));
        					 setColumnValue(sheet,j,10,ECStringUtils.toStringForNull(invoiceData.getAccountBank()));
        					 //setColumnValue(sheet,j,11,ECStringUtils.toStringForNull(invoiceData.getAccountNo()));
    					 }else{
    						 setColumnValue(sheet,j,9,ECStringUtils.toStringForNull(""));
        					 setColumnValue(sheet,j,10,ECStringUtils.toStringForNull(""));
        					 //setColumnValue(sheet,j,11,ECStringUtils.toStringForNull(""));
    					 }
    					 setColumnValue(sheet,j,11,ECStringUtils.toStringForNull(invoiceData.getAddress()));
    					 if(invoiceData.getInvoiceStatus().equals(1)){
    						 setColumnValue(sheet,j,12,ECStringUtils.toStringForNull(invoiceData.getFax()));
        					 setColumnValue(sheet,j,13,ECStringUtils.toStringForNull(invoiceData.getPostcode())); 
    					 }else{
    						 setColumnValue(sheet,j,12,ECStringUtils.toStringForNull(""));
        					 setColumnValue(sheet,j,13,ECStringUtils.toStringForNull(""));
    					 }
    					 setColumnValue(sheet,j,14,ECStringUtils.toStringForNull(memberService.getMember(invoiceData.getMemberNo()==null||invoiceData.getMemberNo()=="" ?"":invoiceData.getMemberNo()).getMemberName()));
    					 setColumnValue(sheet,j,15,ECStringUtils.toStringForNull(invoiceData.getInvoiceStatus()==0?"未开票":"已开票"));
    					 setColumnValue(sheet,j,16,ECStringUtils.toStringForNull(invoiceData.getInvoiceAmount()));
    					 j++;
	 	        	}
	        	}
	        	//一个发票号，合并单元格
	        	sheet.addMergedRegion(new CellRangeAddress(a,b,5,5));
	        	sheet.addMergedRegion(new CellRangeAddress(a,b,6,6));
	        	sheet.addMergedRegion(new CellRangeAddress(a,b,7,7));
	        	sheet.addMergedRegion(new CellRangeAddress(a,b,8,8));
	        	sheet.addMergedRegion(new CellRangeAddress(a,b,9,9));
	        	sheet.addMergedRegion(new CellRangeAddress(a,b,10,10));
	        	sheet.addMergedRegion(new CellRangeAddress(a,b,11,11));
	        	sheet.addMergedRegion(new CellRangeAddress(a,b,12,12));
	        	sheet.addMergedRegion(new CellRangeAddress(a,b,13,13));
	        	sheet.addMergedRegion(new CellRangeAddress(a,b,14,14));
	        	sheet.addMergedRegion(new CellRangeAddress(a,b,15,15));
	        	sheet.addMergedRegion(new CellRangeAddress(a,b,16,16));
	        	sheet.addMergedRegion(new CellRangeAddress(a,b,17,17));
	        	sheet.addMergedRegion(new CellRangeAddress(a,b,18,18));
	        	//a,b变化
	        	a=a+bizObjIds.length;
	        	b=b+bizObjIds.length;
	        	 
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=invoice.xls");
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
   	 * 批量导入发票开票信息
   	 * @param file
   	 * @return
   	 * @throws Exception
   	 */
   	
   	@RequestMapping("importFinaceRecord")
   	@ResponseBody
   	public ResultInfo<FinaceTest> importFinaceRecord(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
   		ResultInfo<FinaceTest> resultInfo = null;
   		try {
   			resultInfo = finaceTestService.importInvoice(file, getOperator());
   		} catch (Exception e) {
   			resultInfo = new ResultInfo<FinaceTest>();
   			resultInfo.setCode(Constant.FAIL);
   		}
   		return resultInfo;
   	}
}
