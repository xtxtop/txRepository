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
import cn.com.shopec.core.statement.model.PackageCount;
import cn.com.shopec.core.statement.service.StatementService;
import cn.com.shopec.mgt.common.BaseController;
/**
 * 修改 套餐汇总报表  
 * @author LiHuan
 * Date 2018年4月10日 下午3:20:27
 */
@Controller
@RequestMapping("packageCount")
public class PackageCountController extends BaseController{
	
	@Resource
	private StatementService statementService;
	@Value("${alipay.agent.fee}")
	private Double zfbAgentFee;
	/**
	 * 进入套餐汇总报表页
	 */
	@RequestMapping("/mainPage")
	public String mainPage(){
		return "statement/package_count";
	}
	/*
	 * 显示财务报表套餐汇总信息
	 */
	@RequestMapping("/pageListpackageCount")
	@ResponseBody
	public PageFinder<PackageCount> pageListpackageCount(PackageCount packageCount,Query query) throws ParseException {
//		packageCount.setZfbAgentFee(zfbAgentFee);
		Query q = new Query(query.getPageNo(),query.getPageSize(),packageCount);
		return statementService.pageListPackageCount(q);
//		return statementService.pageListpackageCount(q);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("topackageCountExport")
	@ResponseBody
	public void toPackageCountExport(PackageCount packageCount,HttpServletRequest request, HttpServletResponse response){
		try{
			Query q = new Query(packageCount);
			q.setPageSize(10000);
			List<PackageCount> list = statementService.toPackageCountExport(q);
			
			 // 声明一个工作薄  
			String path=request.getRealPath("/")+"res"+File.separator+"packagStateCount.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        workbook.setSheetName(0, "套餐汇总");
	        int i=1;
	        for(PackageCount sm:list){
	        	 sheet.createRow(i);
	        	 setColumnValue(sheet,i,0,ECStringUtils.toStringForNull(sm.getPackageNo()));
	        	 setColumnValue(sheet,i,1,ECStringUtils.toStringForNull(sm.getPackageName()));
	        	 setColumnValue(sheet,i,2,ECStringUtils.toStringForNull(sm.getPrice()));
	        	 setColumnValue(sheet,i,3,ECStringUtils.toStringForNull(sm.getPackAmount()));
	        	 setColumnValue(sheet,i,4,ECStringUtils.toStringForNull(sm.getPackageType()));
	        	 setColumnValue(sheet,i,5,ECStringUtils.toStringForNull(sm.getPackOrderCount()));
	        	 setColumnValue(sheet,i,6,ECStringUtils.toStringForNull(sm.getPackRealAmount()));
	        	 setColumnValue(sheet,i,7,ECStringUtils.toStringForNull(sm.getTotalPackAmount()));
	        	 setColumnValue(sheet,i,8,ECStringUtils.toStringForNull(sm.getDicountOrderNum()));
	        	 setColumnValue(sheet,i,9,ECStringUtils.toStringForNull(sm.getDicountOrderCount()));
/*	        	 setColumnValue(sheet,i,8,ECStringUtils.toStringForNull(sm.getWxAmount()));
	        	 setColumnValue(sheet,i,9,ECStringUtils.toStringForNull(sm.getWxAgentFee()));
	        	 setColumnValue(sheet,i,10,ECStringUtils.toStringForNull(sm.getZfbAmount()));
	        	 setColumnValue(sheet,i,11,ECStringUtils.toStringForNull(sm.getZfbAgentFee()));*/
				 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=packagStateCount.xls");
            ServletOutputStream os=response.getOutputStream();
            workbook.write(os);
	        os.flush();
		    os.close(); // 关闭流
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
