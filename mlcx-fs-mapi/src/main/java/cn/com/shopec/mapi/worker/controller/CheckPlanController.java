package cn.com.shopec.mapi.worker.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.car.common.CarConstant;
import cn.com.shopec.core.resource.model.CheckPlan;
import cn.com.shopec.core.resource.model.CheckResult;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.service.CheckPlanService;
import cn.com.shopec.core.resource.service.CheckResultService;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.worker.vo.CheckPlanDetailVo;
import cn.com.shopec.mapi.worker.vo.CheckPlanFinishVo;
import cn.com.shopec.mapi.worker.vo.CheckPlanResultVo;
import cn.com.shopec.mapi.worker.vo.CheckPlanVo;
import cn.com.shopec.mapi.worker.vo.CheckResultDetailVo;
import cn.com.shopec.mapi.worker.vo.CheckResultVo;
import cn.com.shopec.mapi.worker.vo.DataDictItemVo;

@Controller
@RequestMapping("/app/checkPlan")
public class CheckPlanController extends BaseController{

	@Resource
	private CheckPlanService checkPlanService;
	
	@Resource
	private ParkService parkService;
	
	@Resource
	private CheckResultService checkResultService;
	
	@Resource
	private DataDictItemService dataDictItemService;
	/**
	 * 调度员的巡检任务列表
	 * planStatus 0(未完成)1（已完成）
	 * */
	@RequestMapping("/myCheckPlanList")
	@ResponseBody
	public ResultInfo<List<CheckPlanVo>> myCheckPlanList(String workerNo,Integer planStatus) {
		ResultInfo<List<CheckPlanVo>> result=new ResultInfo<List<CheckPlanVo>>();
		List<CheckPlan> list=checkPlanService.getCheckPlanListByWorkerNo(workerNo,planStatus);
		return checkPlanToVo(result,list);
	}
	/**
	 * 巡检任务详情
	 * */
	@RequestMapping("/checkPlanDetail")
	@ResponseBody
	public ResultInfo<CheckPlanDetailVo> checkPlanDetail(String checkPlanNo) {
		ResultInfo<CheckPlanDetailVo> result=new ResultInfo<CheckPlanDetailVo>();
		CheckPlan wo=checkPlanService.getCheckPlan(checkPlanNo);
		return checkPlanDetailToVo(result,wo);
	}
	/**
	 * 进入巡检结果添加页面
	 * */
	@RequestMapping("/toCheckResultAdd")
	@ResponseBody
	public ResultInfo<CheckPlanResultVo> toCheckResultAdd(String checkPlanNo) {
		ResultInfo<CheckPlanResultVo> result=new ResultInfo<CheckPlanResultVo>();
		CheckPlan checkPlan=checkPlanService.getCheckPlan(checkPlanNo);
		if(checkPlan!=null){
			CheckPlanResultVo cpv=new CheckPlanResultVo();
			cpv.setCheckPlanNo(checkPlan.getCheckPlanNo());
			Park park=null;
			if(checkPlan.getParkNo()!=null&&!checkPlan.getParkNo().equals("")){
				park=parkService.getPark(checkPlan.getParkNo());
			}
			
			String address="";
			if(park!=null){
				if(park.getAddrRegion3Name()!=null&&park.getAddrRegion3Name().length()!=0){
					address=park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrRegion3Name()+park.getAddrStreet();
				}else{
					address=park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrStreet();
				}
			}
			cpv.setParkAddress(address);
			cpv.setPlanDate(ECDateUtils.toString(checkPlan.getPlanDate()));
			cpv.setPlanStatus(checkPlan.getPlanStatus());
			List<DataDictItem> items= dataDictItemService.getDataDictItemListByCatCode("CHECK_ITEM");//巡检项
			ResultInfo<List<DataDictItemVo>> dataResult=new ResultInfo<List<DataDictItemVo>>();
			dataResult=dataDictItemToVo(dataResult,items);
			cpv.setCheckItems(dataResult.getData());
			result.setData(cpv);
			result.setCode(Constant.SECCUESS);
		}else{
			result.setCode(Constant.FAIL);
			result.setMsg(Constant.ERR_MSG_DATA_NOT_EXISTS);
		}
		return result;
	}
	/**
	 * 巡检结果提交
	 * */
	@RequestMapping("/addCheckResult")
	@ResponseBody
	public ResultInfo<CheckResultDetailVo> addCheckResult(CheckResult checkResult) {
		ResultInfo<CheckResultDetailVo> result=new ResultInfo<CheckResultDetailVo>();
		return checkResultDetailToVo(result,checkResultService.addCheckResult(checkResult, getOperator1()).getData());
	}
	/**
	 * 巡检结果删除
	 * */
	@RequestMapping("/deleteCheckResult")
	@ResponseBody
	public ResultInfo<CheckResult> deleteCheckResult(String checkResultNo) {
		return checkResultService.delCheckResult(checkResultNo, getOperator1());
	}
	/**
	 * 巡检录入结果列表
	 * */
	@RequestMapping("/checkResultList")
	@ResponseBody
	public ResultInfo<List<CheckResultVo>> checkResultList(String checkPlanNo) {
		ResultInfo<List<CheckResultVo>> result1=new ResultInfo<List<CheckResultVo>>();
		List<CheckResult> crList=checkResultService.getCheckResultListByCheckPlanNo(checkPlanNo);
		return checkResultToVo(result1,crList);
	}
	/**
	 * 方法说明:将按特定条件查询的记录转换成自定vo对象
	 */
	 ResultInfo<List<CheckPlanVo>> checkPlanToVo(ResultInfo<List<CheckPlanVo>> result,List<CheckPlan> cpList){
	
		if (cpList!=null&&cpList.size()>0) {
			List<CheckPlanVo> voList = new ArrayList<CheckPlanVo>();
			for (CheckPlan c : cpList) {
				CheckPlanVo or = new CheckPlanVo();
				or.setCheckPlanNo(c.getCheckPlanNo());
				Park park=null;
				if(c.getParkNo()!=null&&!c.getParkNo().equals("")){
					park=parkService.getPark(c.getParkNo());	
				}
				
				String address="";
				if(park!=null){
					if(park.getAddrRegion3Name()!=null&&park.getAddrRegion3Name().length()!=0){
						address=park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrRegion3Name()+park.getAddrStreet();
					}else{
						address=park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrStreet();
					}
				}
				or.setParkAddress(address);
				or.setPlanDate(ECDateUtils.toString(c.getPlanDate()));
				or.setPlanStatus(c.getPlanStatus());
				voList.add(or);
			}
			result.setData(voList);
			result.setCode(CarConstant.success_code);
		}else {
			result.setCode(CarConstant.fail_code);
			result.setMsg(Constant.NO_RECORD);
		}
		return result;
	}
	 /**
		 * 方法说明:将按特定条件查询的记录转换成自定vo对象
		 */
		 ResultInfo<CheckPlanDetailVo> checkPlanDetailToVo(ResultInfo<CheckPlanDetailVo> result,CheckPlan cp){
		
			if (cp!=null) {
				CheckPlanDetailVo or = new CheckPlanDetailVo();
				Park park=null;
				if(cp.getParkNo()!=null&&!cp.getParkNo().equals("")){
					park=parkService.getPark(cp.getParkNo());	
				}
				
				String address="";
				if(park!=null){
					if(park.getAddrRegion3Name()!=null&&park.getAddrRegion3Name().length()!=0){
						address=park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrRegion3Name()+park.getAddrStreet();
					}else{
						address=park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrStreet();
					}
				}
				or.setAddress(address);
				or.setCheckItem(cp.getCheckItem());
				or.setCheckPlanNo(cp.getCheckPlanNo());
				or.setPlanDate(ECDateUtils.toString(cp.getPlanDate()));
				or.setWorkerName(cp.getWorkerName());
				result.setData(or);
				result.setCode(CarConstant.success_code);
			}else {
				result.setCode(CarConstant.fail_code);
				result.setMsg(Constant.NO_RECORD);
			}
			return result;
		}
		 /**
			 * 方法说明:将按特定条件查询的记录转换成自定vo对象
			 */
			 ResultInfo<CheckPlanFinishVo> checkPlanFinishToVo(ResultInfo<CheckPlanFinishVo> result,CheckPlan cp){
				if (cp!=null) {
					CheckPlanFinishVo or = new CheckPlanFinishVo();
					
					result.setData(or);
					result.setCode(CarConstant.success_code);
				}else {
					result.setCode(CarConstant.fail_code);
					result.setMsg(Constant.NO_RECORD);
				}
				return result;
			}
			 /**
				 * 方法说明:将按特定条件查询的记录转换成自定vo对象
				 */
				 ResultInfo<List<CheckResultVo>> checkResultToVo(ResultInfo<List<CheckResultVo>> result,List<CheckResult> crList){
				
					if (crList!=null&&crList.size()>0) {
						List<CheckResultVo> voList = new ArrayList<CheckResultVo>();
						for (CheckResult c : crList) {
							CheckResultVo or = new CheckResultVo();
							or.setCheckPlanNo(c.getCheckPlanNo());
							CheckPlan cp=checkPlanService.getCheckPlan(c.getCheckPlanNo());
							Park park=null;
							if(cp.getParkNo()!=null&&!cp.getParkNo().equals("")){
								park=parkService.getPark(cp.getParkNo());
							}
							
							String address="";
							if(park!=null){
								if(park.getAddrRegion3Name()!=null&&park.getAddrRegion3Name().length()!=0){
									address=park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrRegion3Name()+park.getAddrStreet();
								}else{
									address=park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrStreet();
								}
							}
							or.setAddress(address);
							or.setCheckResultId(c.getCheckResultId());
							or.setCreateTime(ECDateUtils.formatTime(c.getCreateTime()));
							voList.add(or);
						}
						result.setData(voList);
						result.setCode(CarConstant.success_code);
					}else {
						result.setCode(CarConstant.fail_code);
						result.setMsg(Constant.NO_RECORD);
					}
					return result;
				}
				 /**
					 * 方法说明:将按特定条件查询的记录转换成自定vo对象
					 */
					 ResultInfo<CheckResultDetailVo> checkResultDetailToVo(ResultInfo<CheckResultDetailVo> result,CheckResult cr){
					
						if (cr!=null) {
							CheckResultDetailVo or = new CheckResultDetailVo();
								or.setCheckPlanNo(cr.getCheckPlanNo());
								CheckPlan cp=checkPlanService.getCheckPlan(cr.getCheckPlanNo());
								Park park=null;
								if(cp.getParkNo()!=null&&!cp.getParkNo().equals("")){
									park=parkService.getPark(cp.getParkNo());
								}
								
								String address="";
								if(park!=null){
									if(park.getAddrRegion3Name()!=null&&park.getAddrRegion3Name().length()!=0){
										address=park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrRegion3Name()+park.getAddrStreet();
									}else{
										address=park.getAddrRegion1Name()+park.getAddrRegion2Name()+park.getAddrStreet();
									}	
								}
								or.setAddress(address);
								or.setCheckResultId(cr.getCheckResultId());
								or.setAbnormalDetail(cr.getAbnormalDetail());
								or.setCheckResult(cr.getCheckResult());
								or.setCheckItemId(cr.getCheckItemId());
								or.setCheckResultId(cr.getCheckResultId());
								or.setDeviceNo(cr.getDeviceNo());
							result.setData(or);
							result.setCode(CarConstant.success_code);
						}else {
							result.setCode(CarConstant.fail_code);
							result.setMsg(Constant.NO_RECORD);
						}
						return result;
					}
					
					 /**
						 * 方法说明:将按特定条件查询的记录转换成自定vo对象
						 */
						 ResultInfo<List<DataDictItemVo>>  dataDictItemToVo(ResultInfo<List<DataDictItemVo>> result,List<DataDictItem> itList){
						
							if (itList!=null&&itList.size()>0) {
								List<DataDictItemVo> voList = new ArrayList<DataDictItemVo>();
								for (DataDictItem c : itList) {
									DataDictItemVo or = new DataDictItemVo();
									or.setDataDictItemId(c.getDataDictItemId());
									or.setItemValue(c.getItemValue());
									voList.add(or);
								}
								result.setData(voList);
								result.setCode(CarConstant.success_code);
							}else {
								result.setCode(CarConstant.fail_code);
								result.setMsg(Constant.NO_RECORD);
							}
							return result;
						}
}
