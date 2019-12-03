package cn.com.shopec.mapi.car.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import cn.com.shopec.core.car.model.CarAccident;
import cn.com.shopec.core.car.service.CarAccidentService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mapi.car.vo.CarAccidentVo;
import cn.com.shopec.mapi.common.controller.BaseController;

@RequestMapping("/app/carAccident")
@Controller
public class CarAccidentController extends BaseController{

	@Resource
	private CarAccidentService accidentService;
	@Resource
	private SysParamService sysParamService;
	
	/**
	 * 方法说明：查看一个月内的事故记录,只针对用户，不考虑调度员
	 */
	
	@RequestMapping("/carAccident")
	@ResponseBody
	public ResultInfo<List<CarAccidentVo>> carAccident(String memberNo){

		ResultInfo<List<CarAccidentVo>> result = new ResultInfo<List<CarAccidentVo>>();
		//得到一个月前的 时间
		Date recordAccidentTimeStart = ECDateUtils.getDateMonthAfter(new Date(), -1);
		
		if (memberNo!=null&&memberNo.trim().length()>0) {
			CarAccident cill = new CarAccident();
			cill.setDriverId(memberNo);
			cill.setRecordAccidentTimeStart(recordAccidentTimeStart);
			
			//根据电话号，查找一个月内的订单
			Query q = new Query();
			q.setQ(cill);
			List<CarAccident> list = accidentService.getCarAccidentList(q);
			result = carAccidentToVo(result,list);
		}
		else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(CarConstant.fail_paramters);
		}
		return result;
	}
	
	/**
 	 * 方法说明:查看更多事故记录
	 */
	@RequestMapping("/carAccidenPage")
	@ResponseBody
	public ResultInfo<List<CarAccidentVo>> carIllegalPage(String memberNo,@RequestParam("pageNo") int pageNo){
		
		ResultInfo<List<CarAccidentVo>> result = new ResultInfo<List<CarAccidentVo>>();
		//得到一个月前的 时间
		Date recordAccidentTimeEnd = ECDateUtils.getDateMonthAfter(new Date(), -1);
		int pageSize = 10;
 		if (memberNo!=null&&memberNo.trim().length()>0) {
			CarAccident cill = new CarAccident();
			cill.setDriverId(memberNo);
			cill.setRecordAccidentTimeEnd(recordAccidentTimeEnd);
			//根据系统参数 查找对应参数来获取 
			SysParam  sysParamOrder=sysParamService.getByParamKey(CarConstant.orderPageSize);
			if(sysParamOrder != null){
				pageSize=Integer.parseInt(sysParamOrder.getParamValue());
			}
			//根据电话号，查找一个月内的订单
			Query q = new Query(pageNo, pageSize, cill);
			//根据条件，分页查找CarIllegal记录
			PageFinder<CarAccident>	finder = accidentService.getCarAccidentPagedList(q);
			List<CarAccident> list = finder.getData();
			result =  carAccidentToVo(result, list);
		}else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(CarConstant.fail_paramters);
		}
		return result;
	}
	
	
	/**
	 * 方法说明:将按特定条件查询的记录转换成自定vo对象
	 */
	ResultInfo<List<CarAccidentVo>> carAccidentToVo(ResultInfo<List<CarAccidentVo>> result,List<CarAccident> list){
	
		if (list!=null&&list.size()>0) {
			List<CarAccidentVo> voList = new ArrayList<>();
			for (CarAccident carAccident : list) {
				CarAccidentVo vo = new CarAccidentVo();
				vo.setAccidentId(carAccident.getAccidentId());
				//事故等级（1、一般事故，2、轻微事故，3、重大事故，4、特大事故）
				if(carAccident.getAccidentLevel()==1){
					vo.setAccidentLevel("一般事故");
				}else if(carAccident.getAccidentLevel()==2){
					vo.setAccidentLevel("轻微事故");
				}else if(carAccident.getAccidentLevel()==3){
					vo.setAccidentLevel("重大事故");
				}else if(carAccident.getAccidentLevel()==4){
					vo.setAccidentLevel("特大事故");
				}
				vo.setAccidentLocation(carAccident.getAccidentLocation());
				//保险进度（0、未处理，1、处理中，2、已处理，默认0）
				if(carAccident.getAccidentStatus()!=null){
					if(carAccident.getAccidentStatus()==0){
						vo.setAccidentStatus("未处理");
					}else if(carAccident.getAccidentStatus()==1){
						vo.setAccidentStatus("处理中");
					}else if(carAccident.getAccidentStatus()==2){
						vo.setAccidentStatus("已处理");
					}
				}
				vo.setInsuranceCompany(carAccident.getInsuranceCompany());
				vo.setRecordAccidentTime(ECDateUtils.formatStringDate(carAccident.getRecordAccidentTime()));
				voList.add(vo);
			}
			
			result.setData(voList);
			result.setCode("1");
			result.setMsg("");
		}else {
			result.setCode("0");
			result.setMsg("没有数据");
		}
		return result;
	}
	
	
}
