package cn.com.shopec.mapi.car.controller;

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
import cn.com.shopec.core.car.model.CarIllegal;
import cn.com.shopec.core.car.service.CarIllegalService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mapi.car.vo.CarIllegalListVo;
import cn.com.shopec.mapi.car.vo.CarIllegalVo;
import cn.com.shopec.mapi.common.controller.BaseController;


@Controller
@RequestMapping("/app/carIllegal")
public class CarIllegalController extends BaseController{

	@Resource
	private CarIllegalService carIllegalService;
	@Resource
	private SysParamService sysParamService;
	
	/**
	 * 方法说明：查看一个月内的违章记录,只针对用户，不考虑调度员
	 */
	
//	@RequestMapping("/carIllegal")
//	@ResponseBody
//	public ResultInfo<List<CarIllegalVo>> carIllegal(String memberNo){
//
//		ResultInfo<List<CarIllegalVo>> result = new ResultInfo<List<CarIllegalVo>>();
//		//得到一个月前的 时间
//		Date illegalTimeStart = ECDateUtils.getDateMonthAfter(new Date(), -1);
//		
//		if (memberNo!=null&&memberNo.trim().length()>0) {
//			CarIllegal cill = new CarIllegal();
//			cill.setDriverId(memberNo);
//			cill.setIllegalTimeStart(illegalTimeStart);
//			
//			//根据订单ID，查找一个月内的订单
//			Query q = new Query();
//			q.setQ(cill);
//			List<CarIllegal> list = carIllegalService.getCarIllegalList(q);
//			result = carIllegalToVo(result,list);
//		}else {
//			result.setCode(CarConstant.fail_code);
//			result.setMsg(CarConstant.fail_paramters);
//		}
//		return result;
//	}
//	
	

	/**
 	 * 方法说明:查看违章详情
	 */
	@RequestMapping("/carIllegalDetail")
	@ResponseBody
	public ResultInfo<CarIllegalVo> carIllegalDetail(String memberNo,String illegalID){
		ResultInfo<CarIllegalVo>  result=new ResultInfo<CarIllegalVo>();
		CarIllegal carIllegal=carIllegalService.getCarIllegal(illegalID);
		if(carIllegal != null){
			CarIllegalVo carIllegalVo=new CarIllegalVo();
			result.setData(carIllegalVo);
			result.getData().setIllegalLocation(carIllegal.getIllegalLocation());
			result.getData().setIllegalTime(ECDateUtils.formatStringDate(carIllegal.getIllegalTime()));
			result.getData().setIllegalFines(carIllegal.getIllegalFines());
			result.getData().setPointsDeduction(carIllegal.getPointsDeduction());
			if(carIllegal.getIllegalDetail() != null && !"".equals(carIllegal.getIllegalDetail())){
				result.getData().setIllegalDetail(carIllegal.getIllegalDetail());
			}
			//违章类型 (1、未系安全带2、压禁止标线 3、违停4、闯红灯5、不服从指挥6、超速行驶7、未设警告标志8、未停车让行9、未保持车距10、未按道行驶)
			if(carIllegal.getIllegalType().equals("1")){
				result.getData().setIllegalType("未系安全带");
			}else if(carIllegal.getIllegalType().equals("2")){
				result.getData().setIllegalType("压禁止标线");
			}else if(carIllegal.getIllegalType().equals("3")){
				result.getData().setIllegalType("违停");
			}else if(carIllegal.getIllegalType().equals("4")){
				result.getData().setIllegalType("闯红灯");
			}else if(carIllegal.getIllegalType().equals("5")){
				result.getData().setIllegalType("不服从指挥");
			}else if(carIllegal.getIllegalType().equals("6")){
				result.getData().setIllegalType("超速行驶");
			}else if(carIllegal.getIllegalType().equals("8")){
				result.getData().setIllegalType("未停车让行");
			}else if(carIllegal.getIllegalType().equals("9")){
				result.getData().setIllegalType("未保持车距");
			}else if(carIllegal.getIllegalType().equals("10")){
				result.getData().setIllegalType("未按道行驶");
			}
			result.getData().setProcessingAgency(carIllegal.getProcessingAgency());
			//处理状态（0、未处理，1、处理中，2、已处理，默认0）
			if(carIllegal.getProcessingStatus()==0){
				result.getData().setProcessingStatus("未处理");
			}else if(carIllegal.getProcessingStatus()==1){
				result.getData().setProcessingStatus("处理中");
			}else if(carIllegal.getProcessingStatus()==2){
				result.getData().setProcessingStatus("已处理");
			}
			result.setCode(CarConstant.success_code);
			result.setMsg("查看违章详情");
		}else{
			result.setCode(CarConstant.fail_code);
			result.setMsg("无违章");
		}
			return result;
		}
	
	
	/**
 	 * 方法说明:查看更多违章记录
	 */
	@RequestMapping("/carIllegalPage")
	@ResponseBody
	public ResultInfo<CarIllegalListVo> carIllegalPage(String memberNo,@RequestParam("pageNo") int pageNo){
		ResultInfo<CarIllegalListVo> results = new ResultInfo<CarIllegalListVo>();
		Date illegalTimeEnd = new Date();
		
		if (memberNo!=null&&memberNo.trim().length()>0) {
			CarIllegal cill = new CarIllegal();
			cill.setDriverId(memberNo);
			cill.setIllegalTimeEnd(illegalTimeEnd);
			int pageSize = 10;
			//根据系统参数 查找对应参数来获取 
			SysParam  sysParamOrder=sysParamService.getByParamKey(CarConstant.orderPageSize);
			if(sysParamOrder != null){
				pageSize=Integer.parseInt(sysParamOrder.getParamValue());
			}
			//根据电话号，查找一个月内的订单
			Query q = new Query(pageNo, pageSize, cill);
			//根据条件，分页查找CarIllegal记录
            PageFinder<CarIllegal>	finder = carIllegalService.getCarIllegalPagedList(q);
			List<CarIllegal> list = finder.getData();
			results = carIllegalToVo(results, list);
		}else {
			results.setCode(CarConstant.fail_code);
			results.setMsg(CarConstant.fail_paramters);
		}
			return results;
		}
	
	
	/**
	 * 方法说明:将按特定条件查询的记录转换成自定vo对象
	 */
	ResultInfo<CarIllegalListVo> carIllegalToVo(ResultInfo<CarIllegalListVo> results,List<CarIllegal> list){
		CarIllegalListVo carIllegalListVo =new CarIllegalListVo();
		if (list!=null&&list.size()>0) {
			List<CarIllegalVo> voList = new ArrayList<>();
			List<CarIllegalVo> voListStatus = new ArrayList<>();
			for (CarIllegal carIllegal : list) {
				CarIllegalVo vo = new CarIllegalVo();
				vo.setIllegalFines(carIllegal.getIllegalFines());
				vo.setIllegalId(carIllegal.getIllegalId());
				vo.setIllegalLocation(carIllegal.getIllegalLocation());
				vo.setIllegalTime(ECDateUtils.formatStringDate(carIllegal.getIllegalTime()));
				//违章类型 (1、未系安全带2、压禁止标线 3、违停4、闯红灯5、不服从指挥6、超速行驶7、未设警告标志8、未停车让行9、未保持车距10、未按道行驶)
//				if(carIllegal.getIllegalType().equals("1")){
//					vo.setIllegalType("未系安全带");
//				}else if(carIllegal.getIllegalType().equals("2")){
//					vo.setIllegalType("压禁止标线");
//				}else if(carIllegal.getIllegalType().equals("3")){
//					vo.setIllegalType("违停");
//				}else if(carIllegal.getIllegalType().equals("4")){
//					vo.setIllegalType("闯红灯");
//				}else if(carIllegal.getIllegalType().equals("5")){
//					vo.setIllegalType("不服从指挥");
//				}else if(carIllegal.getIllegalType().equals("6")){
//					vo.setIllegalType("超速行驶");
//				}else if(carIllegal.getIllegalType().equals("8")){
//					vo.setIllegalType("未停车让行");
//				}else if(carIllegal.getIllegalType().equals("9")){
//					vo.setIllegalType("未保持车距");
//				}else if(carIllegal.getIllegalType().equals("10")){
//					vo.setIllegalType("未按道行驶");
//				}
				vo.setProcessingAgency(carIllegal.getProcessingAgency());
				//处理状态（0、未处理，1、处理中，2、已处理，默认0）
				if(carIllegal.getProcessingStatus()==0){
					vo.setProcessingStatus("未处理");
				}else if(carIllegal.getProcessingStatus()==1){
					vo.setProcessingStatus("处理中");
				}else if(carIllegal.getProcessingStatus()==2){
					vo.setProcessingStatus("已处理");
				}
				if(carIllegal.getIllegalDetail() != null && !"".equals(carIllegal.getIllegalDetail())){
					vo.setIllegalDetail(carIllegal.getIllegalDetail());
				}
				if(carIllegal.getCarBrandName() != null && !"".equals(carIllegal.getCarBrandName())){
					vo.setCarBrandName(carIllegal.getCarBrandName());
				}
				vo.setCarModelName(carIllegal.getCarModelName());
				vo.setCarPlateNo(carIllegal.getCarPlateNo());
				voList.add(vo);
				if(carIllegal.getProcessingStatus()==0){
					voListStatus.add(vo);
				}
			}
			carIllegalListVo.setCarIllegalVos(voList);
			carIllegalListVo.setIllegalSize(list.size());
			if(voListStatus != null && voListStatus.size()>0){
				carIllegalListVo.setIllStatusSize(voListStatus.size());
			}else{
				carIllegalListVo.setIllStatusSize(0);
			}
			results.setData(carIllegalListVo);
			results.setCode("1");
			results.setMsg("");
		}else {
			carIllegalListVo.setIllegalSize(0);
			carIllegalListVo.setIllStatusSize(0);
			results.setData(carIllegalListVo);
			results.setCode("0");
			results.setMsg("");
		}
		
		return results;
	}


	
	
}
