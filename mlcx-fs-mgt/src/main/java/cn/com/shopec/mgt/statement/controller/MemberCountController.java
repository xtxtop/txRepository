package cn.com.shopec.mgt.statement.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.pay.aliPay.UtilDate;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECStringUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.common.MemberConstant;
import cn.com.shopec.core.member.model.MemberCountVo;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.resource.common.ParkConstant;
import cn.com.shopec.core.statement.model.MemberCount;
import cn.com.shopec.core.statement.service.StatementService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService; 
import cn.com.shopec.mgt.common.BaseController; 

@Controller
@RequestMapping("memberCount")
public class MemberCountController extends BaseController{
	
	@Resource
	private StatementService statementService;
	@Resource
	private MemberService memberService;
	@Resource
	private SysParamService sysParamService;
	
	/**
	 * 会员统计报表
	 */
	@RequestMapping("/mainPage")
	public String mainPage(){
		return "statement/member_count";
	}
	/*
	 * 显示财务报表订单汇总信息
	 */
	@RequestMapping("/pageListmemberCount")
	@ResponseBody
	public PageFinder<MemberCount> pageListmemberCount(MemberCount memberCount,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),memberCount);
		return statementService.pageListmemberCount(q);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("tomemberCountExport")
	@ResponseBody
	public void tomemberCountExport(MemberCount memberCount,HttpServletRequest request, HttpServletResponse response){
		try{
			Query q = new Query(memberCount);
			q.setPageSize(10000);
			List<MemberCount> list = statementService.tomemberCountExport(q);
			
			 // 声明一个工作薄  
			String path=request.getRealPath("/")+"res"+File.separator+"memberCount.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        int i=1;
	        for(MemberCount sm:list){
	        	 sheet.createRow(i);
	        	 setColumnValue(sheet,i,0,ECStringUtils.toStringForNull(sm.getMemberName()));
	        	 setColumnValue(sheet,i,1,ECStringUtils.toStringForNull(sm.getOrderCount()));
	        	 setColumnValue(sheet,i,2,ECStringUtils.toStringForNull(sm.getOrderDuration()));
	        	 setColumnValue(sheet,i,3,ECStringUtils.toStringForNull(sm.getOrderAmount()));
	        	 setColumnValue(sheet,i,4,ECStringUtils.toStringForNull(sm.getPaidAmount()));
	        	 setColumnValue(sheet,i,5,ECStringUtils.toStringForNull(sm.getPaidDuration()));
	        	 setColumnValue(sheet,i,6,ECStringUtils.toStringForNull(sm.getPackDiscountAmount()));
	        	 setColumnValue(sheet,i,7,ECStringUtils.toStringForNull(sm.getPackMinuteAmount()));
	        	 setColumnValue(sheet,i,8,ECStringUtils.toStringForNull(sm.getPackMinuteDuration()));
	        	 setColumnValue(sheet,i,9,ECStringUtils.toStringForNull(sm.getStrikeDiscountAmount()));
	        	 setColumnValue(sheet,i,10,ECStringUtils.toStringForNull(sm.getStrikeBalanceAmount()));
	        	 setColumnValue(sheet,i,11,ECStringUtils.toStringForNull(sm.getWxOrderCount()));
	        	 setColumnValue(sheet,i,12,ECStringUtils.toStringForNull(sm.getWxOrderAmount()));
	        	 setColumnValue(sheet,i,13,ECStringUtils.toStringForNull(sm.getWxpayAmount()));
	        	 setColumnValue(sheet,i,14,ECStringUtils.toStringForNull(sm.getWxproFee()));
	        	 setColumnValue(sheet,i,15,ECStringUtils.toStringForNull(sm.getZfbOrderCount()));
	        	 setColumnValue(sheet,i,16,ECStringUtils.toStringForNull(sm.getZfbOrderAmount()));
	        	 setColumnValue(sheet,i,17,ECStringUtils.toStringForNull(sm.getZfbpayAmount()));
	        	 setColumnValue(sheet,i,18,ECStringUtils.toStringForNull(sm.getZfbproFee()));
				 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=memberCount.xls");
            ServletOutputStream os=response.getOutputStream();
            workbook.write(os);
	        os.flush();
		    os.close(); // 关闭流
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 会员运营统计报表
	 */
	@RequestMapping("/mainOperatePage")
	public String mainOperatePage(){
		return "operateCount/member_operate_count";
	}
	
	/*
	 * 显示会员运营报表信息-按日
	 */
	@RequestMapping("/pageListmemberOperateCount")
	@ResponseBody
	public ResultInfo<List<MemberCountVo>> pageListmemberOperateCount(Date startTime,Date endTime,Query query) throws Exception {
		
		ResultInfo<List<MemberCountVo>> memberCount  = new ResultInfo<List<MemberCountVo>>();
		 
		SysParam sysParam = sysParamService.getByParamKey(MemberConstant.day_parameter_key);
		int dayParmaeter = Integer.parseInt(sysParam.getParamValue());//单位：天
		
		//获取系统当前日期 
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		

		if(endTime == null ||  "".equals(endTime)){

			if(startTime == null ||  "".equals(startTime)){
				endTime = new Date();
				endTime = dft.parse(dft.format(endTime));
			}else{
				String endTimeStr = ECDateUtils.getSpecifiedDayBefore(startTime,dayParmaeter*-1);
				endTime = dft.parse(endTimeStr);
			}
			
		}else{
			if(startTime == null ||  "".equals(startTime)){
				String startTimeStr = ECDateUtils.getSpecifiedDayBefore(endTime,dayParmaeter);
				startTime = dft.parse(startTimeStr);
			}
			
			dayParmaeter = Integer.parseInt(String.valueOf(getDistanceDays(dft.format(startTime),dft.format(endTime))));
		}
		
		memberCount.setData(memberService.memberOperateCount(endTime, dayParmaeter));
		
		return memberCount;
	}
	
	/*
	 * 显示会员运营报表信息-按周
	 */
	@RequestMapping("/weekMemberOperateCount")
	@ResponseBody
	public ResultInfo<List<MemberCountVo>> weekMemberOperateCount(Date startTime,Date endTime,Query query) throws ParseException {
		
		ResultInfo<List<MemberCountVo>> memberCount  = new ResultInfo<List<MemberCountVo>>();
		
		SysParam sysParam = sysParamService.getByParamKey(MemberConstant.week_parameter_key);
		int weekParmaeter = Integer.parseInt(sysParam.getParamValue());//单位：周
		
		//获取系统当前日期 
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		
//		if(endTime == null ||  "".equals(endTime)){
//			endTime = new Date();
//			endTime = dft.parse(dft.format(endTime));
//			
//			startTime = ECDateUtils.getSpecifiedDay(endTime,weekParmaeter*7);
//		}else{
//		   
//            long from = dft.parse(dft.format(startTime)).getTime();
//            long to = dft.parse(dft.format(endTime)).getTime();
//           
//            weekParmaeter = (int) ((to-from)/(1000*3600*24*7)) + 1;
//		}
		
		if(endTime == null ||  "".equals(endTime)){

			if(startTime == null ||  "".equals(startTime)){
				endTime = new Date();
				endTime = dft.parse(dft.format(endTime));
				
				startTime = ECDateUtils.getSpecifiedDay(endTime,weekParmaeter*7);
			}else{
				endTime = ECDateUtils.getSpecifiedDay(startTime,weekParmaeter*7*-1);
			}
			
		}else{
			if(startTime == null ||  "".equals(startTime)){ 
				startTime = ECDateUtils.getSpecifiedDay(endTime,weekParmaeter*7);
			}

            long from = dft.parse(dft.format(startTime)).getTime();
            long to = dft.parse(dft.format(endTime)).getTime();
           
            weekParmaeter = (int) ((to-from)/(1000*3600*24*7)) + 1;
		}
		
		memberCount.setData(memberService.weekMemberOperateCount(startTime,endTime, weekParmaeter));
		
		return memberCount;
	}
	
	/*
	 * 显示会员运营报表信息-按月
	 */
	@RequestMapping("/monthMemberOperateCount")
	@ResponseBody
	public ResultInfo<List<MemberCountVo>> monthMemberOperateCount(Date startTime,Date endTime,Query query) throws ParseException {
		
		ResultInfo<List<MemberCountVo>> memberCount  = new ResultInfo<List<MemberCountVo>>();
		 
		SysParam sysParam = sysParamService.getByParamKey(MemberConstant.month_parameter_key);
		int monthParmaeter = Integer.parseInt(sysParam.getParamValue());//单位：月
		
		//获取系统当前日期 
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM");
		
//		if(endTime == null ||  "".equals(endTime)){
//			endTime = new Date();
//			endTime = dft.parse(dft.format(endTime));
//			startTime = dft.parse(ECDateUtils.getSpecifiedmonth(endTime,monthParmaeter));
//		}else{
//			monthParmaeter = ECDateUtils.getMonthSpace(dft.format(startTime),dft.format(endTime)) +1;
//		}
		
		if(endTime == null ||  "".equals(endTime)){

			if(startTime == null ||  "".equals(startTime)){
				endTime = new Date();
				endTime = dft.parse(dft.format(endTime));

				startTime = dft.parse(ECDateUtils.getSpecifiedmonth(endTime,monthParmaeter));
			}else{
				endTime = dft.parse(ECDateUtils.getSpecifiedmonth(startTime,monthParmaeter*-1)); 
			}
			
		}else{
			if(startTime == null ||  "".equals(startTime)){ 
				startTime = dft.parse(ECDateUtils.getSpecifiedmonth(endTime,monthParmaeter));
			}
 
			monthParmaeter = ECDateUtils.getMonthSpace(dft.format(startTime),dft.format(endTime)) +1;
		}
		
		memberCount.setData(memberService.monthMemberOperateCount(startTime,endTime, monthParmaeter));
		
		return memberCount;
	}
	
	/*
	 * 显示会员运营报表信息-按年
	 */
	@RequestMapping("/yearMemberOperateCount")
	@ResponseBody
	public ResultInfo<List<MemberCountVo>> yearMemberOperateCount(Date startTime,Date endTime,Query query) throws ParseException {
		
		ResultInfo<List<MemberCountVo>> memberCount  = new ResultInfo<List<MemberCountVo>>();
		 
		SysParam sysParam = sysParamService.getByParamKey(MemberConstant.year_parameter_key);
		int yearParmaeter = Integer.parseInt(sysParam.getParamValue());//单位：年
		
		//获取系统当前日期 
		SimpleDateFormat dft = new SimpleDateFormat("yyyy");
		
//		if(endTime == null ||  "".equals(endTime)){
//			endTime = new Date();
//			endTime = dft.parse(dft.format(endTime));
//			startTime = dft.parse(ECDateUtils.getSpecifiedYear(endTime,yearParmaeter));
//		}else{
//			yearParmaeter = ECDateUtils.getYearSpace(dft.format(startTime),dft.format(endTime)) +1;
//		}
		
		
		if(endTime == null ||  "".equals(endTime)){

			if(startTime == null ||  "".equals(startTime)){
				endTime = new Date();
				endTime = dft.parse(dft.format(endTime));

				startTime = dft.parse(ECDateUtils.getSpecifiedYear(endTime,yearParmaeter));
			}else{
				endTime = dft.parse(ECDateUtils.getSpecifiedYear(startTime,yearParmaeter*-1)); 
			}
			
		}else{
			if(startTime == null ||  "".equals(startTime)){ 
				startTime = dft.parse(ECDateUtils.getSpecifiedYear(endTime,yearParmaeter)); 
			}

			yearParmaeter = ECDateUtils.getYearSpace(dft.format(startTime),dft.format(endTime)) +1;
		}
		
		
		memberCount.setData(memberService.yearMemberOperateCount(startTime,endTime, yearParmaeter));
		
		return memberCount;
	}
	
	   /** 
     * 两个时间之间相差距离多少天 
     * @param one 时间参数 1： 
     * @param two 时间参数 2： 
     * @return 相差天数 
     */  
    public static long getDistanceDays(String str1, String str2) throws Exception{  
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
        Date one;  
        Date two;  
        long days=0;  
        try {  
            one = df.parse(str1);  
            two = df.parse(str2);  
            long time1 = one.getTime();  
            long time2 = two.getTime();  
            long diff ;  
            if(time1<time2) {  
                diff = time2 - time1;  
            } else {  
                diff = time1 - time2;  
            }  
            days = diff / (1000 * 60 * 60 * 24);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return days;  
    }  
}
