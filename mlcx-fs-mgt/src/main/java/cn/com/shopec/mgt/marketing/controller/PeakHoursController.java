package cn.com.shopec.mgt.marketing.controller;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.PeakHours;
import cn.com.shopec.core.marketing.service.PeakHoursService;
import cn.com.shopec.core.marketing.service.PricingRuleService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 高峰时段数据维护业务层
 * @author LiHuan
 * Date 2017年7月27日 下午5:04:00
 */
@Controller
@RequestMapping("/peakHours")
public class PeakHoursController extends BaseController{
	private static Logger log = Logger.getLogger(PeakHoursController.class);
	
	@Resource
	private PeakHoursService peakHoursService;
	
	@Resource
	private PricingRuleService pricingRuleService;
	
	/**
	 * 高峰时段数据列表展示
	 */
	@RequestMapping("/pageListPeakHours")
	@ResponseBody
	public PageFinder<PeakHours> pageListPeakHours(@ModelAttribute("PeakHours") PeakHours peakHours, Query query){
		log.info("加载高峰时段数据...");
		Query q = new Query(query.getPageNo(), query.getPageSize(), peakHours);
		return peakHoursService.getPeakHoursPagedList(q);
	}
	
	/**
	 * 保存高峰时段信息
	 */
	@SuppressWarnings("unused")
	@RequestMapping("savePeakHours")
	@ResponseBody
	public ResultInfo<PeakHours> savePeakHours(@ModelAttribute("peakHours") PeakHours peakHours,String ruleNo){
		ResultInfo<PeakHours> resultInfo = new ResultInfo<>();
		String peakStartHours = peakHours.getPeakStartHours();
		String peakEndHours = peakHours.getPeakEndHours();
		String peakTemp = peakStartHours+"-"+peakEndHours;
		
		int startHours = Integer.parseInt(peakStartHours);
		int endHours = Integer.parseInt(peakEndHours);
		PeakHours peakBean = new PeakHours();
		peakBean.setRuleNo(ruleNo);
		Query q = new Query(peakBean);
		List<PeakHours> list = this.peakHoursService.getPeakHoursList(q);
		if(StringUtils.isBlank(peakHours.getPeakHourId())){
			//同一计费规则下高峰时段开始时间和结束时间不能重复、也不能直接包含，应该区分开来
			if(list != null && list.size()>0){
				for (PeakHours peakHour : list) {
					int peakStart = Integer.parseInt(peakHour.getPeakStartHours());
					int peakEnd = Integer.parseInt(peakHour.getPeakEndHours());
					if(peakTemp.equals(peakHour.getPeakHours())){
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("对不起、您输入的高峰时段时间区间已经存在，请重新设置！");
						return resultInfo;
					}else if(peakStartHours.equals(peakHour.getPeakStartHours())){
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("对不起、您输入的高峰时段开始时间已经存在，请重新设置！");
						return resultInfo;
					}else if(peakEndHours.equals(peakHour.getPeakEndHours())){
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("对不起、您输入的高峰时段结束时间已经存在，请重新设置！");
						return resultInfo;
					}else if((startHours>peakStart) && (startHours<peakEnd)){
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("对不起、您输入的高峰时段开始时间已经有区间存在，请重新设置！");
						return resultInfo;
					}else if((endHours>peakStart) && (endHours<peakEnd)){
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("对不起、您输入的高峰时段结束时间已经有区间存在，请重新设置！");
						return resultInfo;
					}else if((peakStart>startHours) && (peakEnd <endHours )){
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("对不起、您输入的高峰时段结束时间已经有区间存在，请重新设置！");
						return resultInfo;
					}
				}
			}
			peakHours.setPeakHourId(peakHoursService.generatePK());
			peakHours.setRuleNo(ruleNo);
			peakHours.setPeakHours(peakHours.getPeakStartHours()+"-"+peakHours.getPeakEndHours());
			resultInfo = peakHoursService.addPeakHours(peakHours);
			if(peakHours != null){
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg("新增高峰时段信息成功！");
				resultInfo.setData(peakHours);
				return resultInfo;
			}else{
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("新增高峰时段信息失败！");
				return resultInfo;
			} 
		}else{
			//编辑
			PeakHours peakHoursBean = this.peakHoursService.getPeakHours(peakHours.getPeakHourId());
			//若做了修改 则需要和之前的数据做校验
			List<PeakHours> listPeakHours = this.peakHoursService.queryPeakHoursList(ruleNo,peakHours.getPeakHourId());
			if(list != null && list.size()>0){
				if(list.size() == 1){
					peakHoursBean.setPeakStartHours(peakStartHours);
					peakHoursBean.setPeakEndHours(peakEndHours);
					peakHoursBean.setPeakHours(peakTemp);
					peakHoursBean.setPriceOfMinute(peakHours.getPriceOfMinute());
					peakHoursBean.setPriceOfKm(peakHours.getPriceOfKm());
					resultInfo = peakHoursService.updatePeakHours(peakHoursBean);
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setMsg("更新高峰时段信息成功！");
					return resultInfo;
				}else{
					if(listPeakHours != null && listPeakHours.size()>0){
						for (PeakHours peak : listPeakHours) {
							int peakStart = Integer.parseInt(peak.getPeakStartHours());
							int peakEnd = Integer.parseInt(peak.getPeakEndHours());
							if((startHours>peakStart) && (startHours<peakEnd)){
								resultInfo.setCode(Constant.FAIL);
								resultInfo.setMsg("对不起、您输入的高峰时段开始时间已经有区间存在，请重新设置！");
								return resultInfo;
							}else if((endHours>peakStart) && (endHours<peakEnd)){
								resultInfo.setCode(Constant.FAIL);
								resultInfo.setMsg("对不起、您输入的高峰时段结束时间已经有区间存在，请重新设置！");
								return resultInfo;
							}else if((peakStart>startHours) && (peakEnd <endHours )){
								resultInfo.setCode(Constant.FAIL);
								resultInfo.setMsg("对不起、您输入的高峰时段结束时间已经有区间存在，请重新设置！");
								return resultInfo;
							}else if((peakStart>startHours) && (peakEnd <= endHours )){
								resultInfo.setCode(Constant.FAIL);
								resultInfo.setMsg("对不起、您输入的高峰时段结束时间已经有区间存在，请重新设置！");
								return resultInfo;
							}
						}
					}
					peakHoursBean.setPeakStartHours(peakStartHours);
					peakHoursBean.setPeakEndHours(peakEndHours);
					peakHoursBean.setPeakHours(peakTemp);
					peakHoursBean.setPriceOfMinute(peakHours.getPriceOfMinute());
					peakHoursBean.setPriceOfKm(peakHours.getPriceOfKm());
					resultInfo = peakHoursService.updatePeakHours(peakHoursBean);
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setMsg("更新高峰时段信息成功！");
					return resultInfo;
				}
			}else{
				peakHoursBean.setPeakStartHours(peakStartHours);
				peakHoursBean.setPeakEndHours(peakEndHours);
				peakHoursBean.setPeakHours(peakTemp);
				peakHoursBean.setPriceOfMinute(peakHours.getPriceOfMinute());
				peakHoursBean.setPriceOfKm(peakHours.getPriceOfKm());
				resultInfo = peakHoursService.updatePeakHours(peakHoursBean);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg("更新高峰时段信息成功！");
				return resultInfo;
			}
		}
	}

	/**
	 * 高峰时段信息单选删除
	 * @return
	 */
	@RequestMapping("/delPeakHours")
	@ResponseBody
	public ResultInfo<PeakHours> delPeakHours(@ModelAttribute("peakHourId")String peakHourId){
		ResultInfo<PeakHours> result=new ResultInfo<PeakHours>();
		PeakHours peakHours = peakHoursService.getPeakHours(peakHourId); 
		if(peakHours != null){
			result= peakHoursService.delPeakHours(peakHourId);
			result.setCode(Constant.SECCUESS);
			return result;
		}else{
			result.setCode(Constant.FAIL);
			return result;
		}
	}
	
	/**
	 * 编辑高峰时段信息
	 * */
	@RequestMapping("updatePeakHours")
	@ResponseBody
	public ResultInfo<PeakHours> updatePeakHours(String peakHourId){
		ResultInfo<PeakHours> res = new ResultInfo<>();
		PeakHours ph = peakHoursService.getPeakHours(peakHourId);
		if(ph != null){
			res.setCode(Constant.SECCUESS);
			res.setData(ph);
		}else{
			res.setCode(Constant.FAIL);
		}
		return res;
	}
}
