package cn.com.shopec.mgt.finace.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.Region;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
import cn.com.shopec.core.car.model.CarOwner;
import cn.com.shopec.core.car.service.CarOwnerService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.CheckAccounts;
import cn.com.shopec.core.finace.model.CheckAccountsView;
import cn.com.shopec.core.finace.model.Invoice;
import cn.com.shopec.core.finace.service.InvoiceService;
import cn.com.shopec.core.member.model.CompanyPerson;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.monitor.model.Warning;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.PricingPackOrder;
import cn.com.shopec.core.order.service.OrderService;
import cn.com.shopec.core.order.service.PricingPackOrderService;
import cn.com.shopec.core.resource.common.ParkConstant;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.DataDictCatService;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mgt.common.BaseController;
/**
 * 财务对账列表
 * */
@Controller
@RequestMapping("/checkAccounts")
public class CheckAccountsController extends BaseController{
	
	@Resource
	private OrderService orderService;
	
	@Resource
	private MemberService memberService;
	
	@Resource
	private InvoiceService invoiceService;
	
	/*
	 * 进入财务对账列表页面
	 */
	@RequestMapping("/toCheckAccounts")
	public String mainPage(ModelMap model) {
		String days=ECDateUtils.getFirstAndLastDayOfMonth();
		if(days!=null&&!days.equals("")){
			String[] temp=days.split(",");
			model.addAttribute("createTime",temp[0]);
			model.addAttribute("finishTime",temp[1]);	
		}
		return "finace/check_accounts_list";
	}
	/*
	 * 显示财务对账列表信息
	 */
	@RequestMapping("/pageListCheckAccounts")
	@ResponseBody
	public PageFinder<CheckAccounts> pageListCheckAccounts(CheckAccounts checkAccounts,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),checkAccounts);
		return orderService.getCheckAccountsPagedList(q);
	}
	/**
	 * 导出财务对账列表信息
	 * */
    @RequestMapping("/toCheckAccountsExport")
    public void toCheckAccountsExport(CheckAccounts checkAccounts,HttpServletRequest request, HttpServletResponse response){
    	try {
    		List<CheckAccounts> list = orderService.getCheckAccountsList(checkAccounts);
			 // 声明一个工作薄  
			String path=request.getRealPath("/")+"res"+File.separator+"checkAccounts.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        int i=1;
	        for(CheckAccounts checkAccount:list){
	        	 sheet.createRow(i);
	        	 //对账周期，客户姓名，手机号，订单数，订单金额，应付金额，已付金额，开票金额
	        	 if(checkAccount.getOrderNum()==null){
	        		 checkAccount.setOrderNum(0);
	        	 }
	        	 if(checkAccount.getOrderAmount()==null){
	        		 checkAccount.setOrderAmount(0d);
	        	 }
	        	 if(checkAccount.getPayableAmount()==null){
	        		 checkAccount.setPayableAmount(0d);;
	        	 }
	        	 if(checkAccount.getAlreadyPayAmount()==null){
	        		 checkAccount.setAlreadyPayAmount(0d);;
	        	 }
	        	 if(checkAccount.getInvoiceAmount()==null){
	        		 checkAccount.setInvoiceAmount(0d);;
	        	 }
	        	 setColumnValue(sheet,i,0,ECStringUtils.toStringForNull(ECDateUtils.toString(checkAccount.getCheckDateRange1())+"——"+ECDateUtils.toString(checkAccount.getCheckDateRange2())));
	        	 setColumnValue(sheet,i,1,ECStringUtils.toStringForNull(checkAccount.getMemberName()));
	        	 setColumnValue(sheet,i,2,ECStringUtils.toStringForNull(checkAccount.getOrderNum()));
	        	 setColumnValue(sheet,i,3,ECStringUtils.toStringForNull(checkAccount.getOrderAmount()));
	        	 setColumnValue(sheet,i,4,ECStringUtils.toStringForNull(checkAccount.getPayableAmount()));
	        	 setColumnValue(sheet,i,5,ECStringUtils.toStringForNull(checkAccount.getAlreadyPayAmount()));
	        	 setColumnValue(sheet,i,6,ECStringUtils.toStringForNull(checkAccount.getInvoiceAmount()));
				 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=checkAccounts.xls");
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
     * 查看财务对账明细
     * */
    @RequestMapping("/toCheckAccountsView")
	public String toCheckAccountsView(CheckAccountsView checkAccountsView,ModelMap model) {
    	if(checkAccountsView.getMemberNo()!=null){
    		Member member=memberService.getMember(checkAccountsView.getMemberNo());
    		if(member!=null){
    			checkAccountsView.setMemberName(member.getMemberName());
    		}
    	}
    	model.addAttribute("checkAccountsView",checkAccountsView);
		return "finace/check_accounts_view";
	}
    /*
	 * 显示财务对账明细信息
	 */
	@RequestMapping("/pageListCheckAccountsDetail")
	@ResponseBody
	public PageFinder<CheckAccountsView> pageListCheckAccountsDetail(CheckAccountsView checkAccountsView,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),checkAccountsView);
		return orderService.getCheckAccountsViewPagedList(q);
	}
	/**
	 * 导出财务对账明细信息
	 * */
    @RequestMapping("/toCheckAccountsViewExport")
    public void toCheckAccountsViewExport(CheckAccountsView checkAccountsView,HttpServletRequest request, HttpServletResponse response){
    	try {
    		List<CheckAccountsView> list = orderService.getCheckAccountsViewList(checkAccountsView);
			 // 声明一个工作薄  
			String path=request.getRealPath("/")+"res"+File.separator+"checkAccountsView.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        int i=1;
	        for(CheckAccountsView checkAccountView:list){
	        	 sheet.createRow(i);
	        	 // 订单号,客户姓名,手机号,城市,车型,车牌号,开始时间,结束时间,订单状态,订单金额(元),应付金额(元),支付状态,发票号,开票金额
	        	 setColumnValue(sheet,i,0,ECStringUtils.toStringForNull(checkAccountView.getOrderNo()));
	        	 setColumnValue(sheet,i,1,ECStringUtils.toStringForNull(checkAccountView.getMemberName()));
	        	 setColumnValue(sheet,i,2,ECStringUtils.toStringForNull(checkAccountView.getCityName()));
	        	 setColumnValue(sheet,i,3,ECStringUtils.toStringForNull(checkAccountView.getCarModelName()));
	        	 setColumnValue(sheet,i,4,ECStringUtils.toStringForNull(checkAccountView.getCarPlateNo()));
	        	 setColumnValue(sheet,i,5,ECStringUtils.toStringForNull(ECDateUtils.toString(checkAccountView.getStartTime())));
	        	 setColumnValue(sheet,i,6,ECStringUtils.toStringForNull(ECDateUtils.toString(checkAccountView.getFinishTime())));
	    		 String orderStatus="";
	        	 if(checkAccountView.getOrderStatus().equals(1)){
	        		 orderStatus="已预约";
	        	 }else if(checkAccountView.getOrderStatus().equals(2)){
	        		 orderStatus="已计费";
	        	 }else if(checkAccountView.getOrderStatus().equals(3)){
	        		 orderStatus="已结束";
	        	 }else if(checkAccountView.getOrderStatus().equals(4)){
	        		 orderStatus="已取消";
	        	 }
	        	 setColumnValue(sheet,i,7,ECStringUtils.toStringForNull(orderStatus));
	        	 setColumnValue(sheet,i,8,ECStringUtils.toStringForNull(checkAccountView.getOrderAmount()));
	        	 setColumnValue(sheet,i,9,ECStringUtils.toStringForNull(checkAccountView.getPayableAmount()));
	        	 String payStatus="";
	        	 if(checkAccountView.getPayStatus().equals(0)){
	        		 payStatus="未支付";
	        	 }else if(checkAccountView.getPayStatus().equals(1)){
	        		 payStatus="已支付";
	        	 }
	        	 setColumnValue(sheet,i,10,ECStringUtils.toStringForNull(payStatus));
	        	 setColumnValue(sheet,i,11,ECStringUtils.toStringForNull(checkAccountView.getInvoiceNo()));
	        	 setColumnValue(sheet,i,12,ECStringUtils.toStringForNull(checkAccountView.getInvoiceAmount()));
				 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=checkAccountsView.xls");
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
