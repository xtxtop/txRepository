package cn.com.shopec.mgt.monitor.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.service.CarService;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.map.model.Points;
import cn.com.shopec.core.map.model.TrackQuery;
import cn.com.shopec.core.map.model.TrackResultInfo;
import cn.com.shopec.core.map.service.BaiduTrackApiService;
import cn.com.shopec.core.monitor.model.CarTrack;
import cn.com.shopec.core.monitor.service.CarTrackService;
import cn.com.shopec.mgt.common.BaseController;
import cn.com.shopec.mgt.monitor.vo.EntityNames;

/**
 * 集团人员管理
 * 
 * @author machao
 *
 */
@Controller
@RequestMapping("/carTrack")
public class CarTrackController extends BaseController {

	@Resource
	private CarTrackService carTrackService;
	@Resource
	private CarService carService;
	@Resource
	private BaiduTrackApiService baiduTrackApiService;
	/**
	 * 进入车辆监控列表页面
	 * 
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("toCarTrackList")
	public String toCarTrackList(Model model, String carPlateNo, String finishTime, String createTime,String flag)
			throws ParseException {
		if (finishTime != null && finishTime.length() > 0 && !"null".equals(finishTime) && !"".equals(finishTime)) {
			model.addAttribute("carPlateNo", carPlateNo);
			model.addAttribute("flag", "1");
			Date endTime = new Date(Long.parseLong(finishTime));
			Date ceTime = new Date(Long.parseLong(createTime));
			Date startTime = new Date(endTime.getTime() - 24 * 60 * 60 * 1000);

			long between = endTime.getTime() - ceTime.getTime();

			if (between > (24 * 3600000)) {
				model.addAttribute("startTime", startTime);
				model.addAttribute("endTime", endTime);
			} else {
				model.addAttribute("startTime", ceTime);
				model.addAttribute("endTime", endTime);
			}

		} else {
			model.addAttribute("carPlateNo", carPlateNo);
			Date endTime = new Date();
			Date startTime = new Date(endTime.getTime() - 24 * 60 * 60 * 1000);
			model.addAttribute("startTime", startTime);
			model.addAttribute("endTime", endTime);
			model.addAttribute("flag", flag);
		}
		return "monitor/car_track";
	}

	/**
	 * 查询车辆监控列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("pageListCarTrack")
	@ResponseBody
	public List<CarTrack> pageListCarTrack(@ModelAttribute("carTrack") CarTrack carTrack) throws ParseException, IllegalAccessException, InvocationTargetException {
		Query q = new Query(carTrack);
		List<CarTrack> carTrackList  = carTrackService.getCarTrackList(q);
		return carTrackList;
	}
	@RequestMapping("getCarByPlateNo")
	@ResponseBody
	public ResultInfo<Car> getCarByPlateNo(String carPlateNo){
		ResultInfo<Car> resultInfo = new ResultInfo<Car>();
		Car car = carService.getCarByPlateNo(carPlateNo);
		if(car!=null){
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(car);
		}else{
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}
//	  0: '请求成功'
//	  1: '请求服务出现错误，请稍后再试',
//    2: '相关参数为空,无法查询轨迹信息',
//    101: 'AK参数不存在,未查询到相关服务',
//    200: 'AK参数错误，请检查在重试',
//    201: 'AK被禁用,请到<a href="http://lbsyun.baidu.com/apiconsole/key" target="_blank">控制台</a>解禁',
//    3003: '未查询到相关轨迹信息',
//    4005: '未查询到相关鹰眼服务',
//    9999: '<i class="fa fa-exclamation-triangle"></i>  最多同时选择10条记录'
	
//	@RequestMapping("getHistoryByEntityName")
//	@ResponseBody
//	public ResultInfo<TrackResultInfo> getHistoryByEntityName(String entityName,String startTime,String endTime, HttpServletResponse response) throws Exception{
//		TrackQuery trackQuery = new TrackQuery();
//		trackQuery.setEntityName(entityName);
//		trackQuery.setStartTime(ECDateUtils.parseTime(startTime));
//		trackQuery.setEndTime(ECDateUtils.parseTime(endTime));
//		ResultInfo<TrackResultInfo> result = baiduTrackApiService.getHistory(trackQuery);
//		return result;
//	}
	/**
	 * 轨迹查询接口 不限制时间
	 * @param entityName
	 * @param startTime
	 * @param endTime
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getHistoryByEntityName")
	@ResponseBody
	public ResultInfo<TrackResultInfo> getHistoryByEntityNameNew(String entityName,String startTime,String endTime, HttpServletResponse response) throws Exception{
		ResultInfo<TrackResultInfo> result = new ResultInfo<TrackResultInfo>();
		TrackResultInfo trackResult = new TrackResultInfo();
		List<Points> allPoints = new ArrayList<Points>();
		TrackQuery trackQuery = new TrackQuery();
		trackQuery.setEntityName(entityName);
		Date oriStartDate = ECDateUtils.parseTime(startTime);
		Date oriEndDate = ECDateUtils.parseTime(endTime);
		int diffMinutes = ECDateUtils.differMinutes(oriEndDate,oriStartDate).intValue();
		int minutesOfDay = 1440;
		if(diffMinutes>minutesOfDay){
			int days = diffMinutes/minutesOfDay;
			int minutes = diffMinutes%minutesOfDay;
			int length = 0;
			Date tempEndDate = oriStartDate;
			Date tempStartDate = oriStartDate;
			if(minutes==0){
				length = days;
				for(int i=0;i<length;i++){
					tempEndDate = ECDateUtils.getDateAfter(tempStartDate, 1);
					trackQuery.setStartTime(tempStartDate);
					trackQuery.setEndTime(tempEndDate);
					result = baiduTrackApiService.getHistory(trackQuery);
					if(result.getData()!=null){
						trackResult = result.getData();
						allPoints.addAll(result.getData().getPoints());
					}
					tempStartDate=tempEndDate;
				}
			}else{
				length = days+1;
				for(int i=0;i<length;i++){
					if(i==length-1){
						trackQuery.setStartTime(tempStartDate);
						trackQuery.setEndTime(oriEndDate);
						result = baiduTrackApiService.getHistory(trackQuery);
						if(result.getData()!=null){
							trackResult = result.getData();
							allPoints.addAll(result.getData().getPoints());
						}
					}else{
						tempEndDate = ECDateUtils.getDateAfter(tempStartDate, 1);
						trackQuery.setStartTime(tempStartDate);
						trackQuery.setEndTime(tempEndDate);
						result = baiduTrackApiService.getHistory(trackQuery);
						if(result.getData()!=null){
							trackResult = result.getData();
							allPoints.addAll(result.getData().getPoints());
						}
						tempStartDate=tempEndDate;
					}
				}
			}
			trackResult.setPoints(allPoints);
			result.setData(trackResult);
		}else{
			trackQuery.setStartTime(oriStartDate);
			trackQuery.setEndTime(oriEndDate);
			result = baiduTrackApiService.getHistory(trackQuery);
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.err.println(2881%1440);
	}
}
