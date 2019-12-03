package cn.com.shopec.mgt.statement.controller;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.utils.ECStringUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.statement.model.CashPledge;
import cn.com.shopec.core.statement.model.MemberCount;
import cn.com.shopec.core.statement.service.StatementService;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("cashPledge")
public class CashPledgeController extends BaseController{
	
	@Resource
	private StatementService statementService;
	@Value("${alipay.agent.fee}")
	private Double zfbAgentFee;
	/**
	 * 会员统计报表
	 */
	@RequestMapping("/mainPage")
	public String mainPage(){
		return "statement/cash_pledge";
	}
	/*
	 * 显示财务报表订单汇总信息
	 */
	@RequestMapping("/pageListcashPledge")
	@ResponseBody
	public PageFinder<CashPledge> pageListcashPledge(CashPledge cashPledge,Query query) throws ParseException {
		cashPledge.setZfbAgentFee(zfbAgentFee);
		Query q = new Query(query.getPageNo(),query.getPageSize(),cashPledge);
		return statementService.pageListcashPledge(q);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("tocashPledgeExport")
	@ResponseBody
	public void tocashPledgeExport(CashPledge cashPledge,HttpServletRequest request, HttpServletResponse response){
		try{
			Query q = new Query(cashPledge);
			q.setPageSize(10);
			List<CashPledge> list = statementService.tocashPledgeExport(q);
			
			 // 声明一个工作薄  
			String path=request.getRealPath("/")+"res"+File.separator+"cashPledge.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        int i=1;
	        for(CashPledge sm:list){
	        	 sheet.createRow(i);
	        	 setColumnValue(sheet,i,0,ECStringUtils.toStringForNull(sm.getType()));
	        	 setColumnValue(sheet,i,1,ECStringUtils.toStringForNull(sm.getCashPerCount()));
	        	 setColumnValue(sheet,i,2,ECStringUtils.toStringForNull(sm.getIncome()));
	        	 setColumnValue(sheet,i,3,ECStringUtils.toStringForNull(sm.getCashAgentFee()));
	        	 setColumnValue(sheet,i,4,ECStringUtils.toStringForNull(sm.getCashRefundCount()));
	        	 setColumnValue(sheet,i,5,ECStringUtils.toStringForNull(sm.getRefund()));
	        	 setColumnValue(sheet,i,6,ECStringUtils.toStringForNull(sm.getRefundAgentFee()));
				 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=cashPledge.xls");
            ServletOutputStream os=response.getOutputStream();
            workbook.write(os);
	        os.flush();
		    os.close(); // 关闭流
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
