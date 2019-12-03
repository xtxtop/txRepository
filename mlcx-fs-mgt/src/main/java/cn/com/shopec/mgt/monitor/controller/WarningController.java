package cn.com.shopec.mgt.monitor.controller;


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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECStringUtils;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.monitor.model.Warning;
import cn.com.shopec.core.monitor.service.WarningService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;
@Controller
@RequestMapping("warning")
public class WarningController extends BaseController{
	@Resource
	private WarningService warningService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private CarService carService;
	@Resource
	private CarStatusService carStatusService;
	/**
	 * 进入车辆警告列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toWarningList")
	public String toWarningList(ModelMap modelMap,Integer type) {
		List<DataDictItem> warningTypes = dataDictItemService.getDataDictItemListByCatCode("WARNING_TYPE");//警告分类
		modelMap.put("warningTypes", warningTypes);
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		modelMap.addAttribute("cities", cities);
		modelMap.put("type", type);
		return "monitor/warning_list";
	}
	/**
	 * 查询车辆警告列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageListWarning")
	@ResponseBody
	public PageFinder<Warning> pageListWarning(@ModelAttribute("warning") Warning warning ,Query query) throws ParseException {
		if(warning.getType() != null && warning.getType()==1){
			warning.setWarningSubType("车辆电压过低警告");
		}
		Query q = new Query(query.getPageNo(),query.getPageSize(),warning);
		return warningService.getWarningPagedList(q);
	}
	/**
	 * 进入车辆警告详情页面
	 * 
	 * @return
	 */
	@RequestMapping("toWarningView")
	public String toWarningView(String warningNo,ModelMap modelMap) {
		Warning warning=null;
		if(warningNo!=null&&warningNo.length()!=0){
			warning=warningService.getWarning(warningNo);
			modelMap.put("warning", warning);
			SysUser sysUser=sysUserService.detail(warning.getOperatorId());
			modelMap.put("sysUser", sysUser);
		}
		return "monitor/warning_view";
	}
	@RequestMapping("updateWarning")
	@ResponseBody
	public ResultInfo<Warning> updateWarning(@ModelAttribute("warning") Warning warning) throws ParseException {
		ResultInfo<Warning> resultInfo = new ResultInfo<Warning>();
		if(warning.getWarningNo()!=null&&warning.getWarningNo().length()!=0){
			resultInfo=warningService.closeWarning(warning, getOperator());
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("当前报警已关闭，请稍后再试");
		}
		return resultInfo;
	}
	
	@RequestMapping("toWarning")
	@ResponseBody
	public ResultInfo<Warning> updateWarning(String warningNo) throws ParseException {
		ResultInfo<Warning> resultInfo = new ResultInfo<Warning>() ;
		if(warningNo!=null&&warningNo.length()!=0){
			Warning warning = warningService.getWarning(warningNo);
			Car car = carService.getCarByPlateNo(warning.getCarPlateNo());
			if(car!=null){
				CarStatus carStatus = carStatusService.getCarStatus(car.getCarNo());
				if(carStatus.getCarStatus()!=null&&carStatus.getCarStatus()==2){
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(warning);
				}else if(carStatus.getCarStatus()!=null&&carStatus.getCarStatus()==1){
					resultInfo.setCode("2");
					resultInfo.setMsg("车辆未熄火！");;
				}else{
					resultInfo.setCode(Constant.FAIL);
				}
			}
		}
		return resultInfo;
	}
	/**
	 * 导出警告信息
	 * */
    @RequestMapping("/exportWarning")
    public void exportWarning(@ModelAttribute("warning")Warning warning,HttpServletRequest request, HttpServletResponse response){
    	try {
			Query q=new Query();
			q.setQ(warning);
			List<Warning> warnings=warningService.getWarningList(q);
			 // 声明一个工作薄  
			String path=request.getRealPath("/")+"res"+File.separator+"warning.xls";
    		File ff=new File(path);
	        InputStream is = new FileInputStream(ff);  
	        HSSFWorkbook workbook =new HSSFWorkbook(is);
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.getSheetAt(0);
	        int i=1;
	        for(Warning warningData:warnings){
	        	 sheet.createRow(i);
	        	 
	        	 setColumnValue(sheet,i,0,ECStringUtils.toStringForNull(warningData.getCityName()));//城市
	        	 String warningLevel="";
	        	 if(warningData.getWarningLevel().equals(1)){
	        		 warningLevel="一级";
	        	 }else if (warningData.getWarningLevel().equals(2)){
	        		 warningLevel="二级";
	        	 }else{
	        		 warningLevel="三级";
	        	 }
	        	 setColumnValue(sheet,i,1,ECStringUtils.toStringForNull(warningLevel));//警告级别
	        	 setColumnValue(sheet,i,2,ECStringUtils.toStringForNull(warningData.getWarningType()));//警告类别
	        	 setColumnValue(sheet,i,3,ECStringUtils.toStringForNull(warningData.getWarningSubType()));//警告子类
	        	 setColumnValue(sheet,i,4,ECDateUtils.formatTime(warningData.getWarningTime()));//警告时间
	        	 setColumnValue(sheet,i,5,ECStringUtils.toStringForNull(warningData.getWarningContent()));//警告内容
	        	 setColumnValue(sheet,i,6,ECStringUtils.toStringForNull(warningData.getParkName()));//相关场站
	        	 setColumnValue(sheet,i,7,ECStringUtils.toStringForNull(warningData.getCarPlateNo()));//相关车辆
	        	 setColumnValue(sheet,i,8,ECStringUtils.toStringForNull(warningData.getMemberName()));//相关会员
	        	 setColumnValue(sheet,i,9,ECStringUtils.toStringForNull(warningData.getOrderNo()));//相关订单
	        	 String isNeedManualClosed="";
	        	 if(warningData.getIsNeedManualClosed().equals(0)){
	        		 isNeedManualClosed="否";
	        	 }else if(warningData.getIsNeedManualClosed().equals(1)){
	        		 isNeedManualClosed="是";
	        	 }
	        	 setColumnValue(sheet,i,10,ECStringUtils.toStringForNull(isNeedManualClosed));//需要人工关闭
	        	 String isClosed="";
	        	 if(warningData.getIsClosed().equals(0)){
	        		 isClosed="未关闭";
	        	 }else if(warningData.getIsClosed().equals(1)){
	        		 isClosed="已关闭";
	        	 }
	        	 setColumnValue(sheet,i,11,ECStringUtils.toStringForNull(isClosed));//是否关闭
				 i++;
	        }
	        response.reset();// 清空输出流   
	        response.setContentType("text/plain;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=warning.xls");
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
