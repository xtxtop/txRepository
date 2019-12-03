package cn.com.shopec.mapi.resource.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.common.utils.HttpURLRequestUtil;
import cn.com.shopec.core.car.model.CarOrParkVo;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.map.service.BaiduGeocoderApiService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.resource.common.ParkConstant;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.model.ParkRegion;
import cn.com.shopec.core.resource.model.ParkStatus;
import cn.com.shopec.core.resource.service.ParkService;
import cn.com.shopec.core.resource.service.ParkStatusService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.resource.vo.ParkVO;
import cn.com.shopec.mapi.resource.vo.ParkVOAround;
import cn.com.shopec.mapi.resource.vo.ParkVOReturn;
import cn.com.shopec.mapi.resource.vo.ParkVOSearch;
import cn.com.shopec.mapi.resource.vo.ParkVOSearchBaiDu;
import cn.com.shopec.mapi.resource.vo.ParkVOSearchBaiDu.ParkVOBaidu;
import cn.com.shopec.mapi.resource.vo.ParkVOSearchSum;

@Controller
@RequestMapping("/app/park")
public class ParkController extends BaseController{
	
	private static final String requestUrl = null;

	@Resource
	private ParkService parkService;
	
	@Resource
	private SysParamService sysParamService;
	@Resource
	private BaiduGeocoderApiService baiduGeocoderApiService;
	
	@Resource
	private ParkStatusService parkStatusService;
	//百度搜索 链接  
	private final String baiduSearchURI = "http://api.map.baidu.com/place/v2/suggestion";
	
	@Value("${image_path}")
	private String imgPath;
	@Value("${ak}")
	private String ak;
	@Value("${city}")
	private String city;

	/**
	 * 搜索 网店信息
	 * @param address 	客户端输入的文字
	 * @param latitude  经度
	 * @param longitude  纬度
	 * @param isPayment  是否付费网点（0 否  1 点击）
	 * @param isLimit 	是否内部限制网点（0 否  1 点击）
	 * @return
	 */
	@RequestMapping("/searchParkList")
	@ResponseBody
	public ResultInfo<ParkVOSearchSum> searchParkList(String address,String latitude,String longitude,Integer isPayment,Integer isLimit,String memberNo,String cityName,String carPlateNo) {
		ResultInfo <ParkVOSearchSum> result =new ResultInfo<ParkVOSearchSum>();
		return parkVOSearch(result,parkService.getSearchParkList(longitude,latitude, address,memberNo,isPayment,isLimit,carPlateNo), address,longitude,latitude,cityName);
	}
	
	
	
	 /**
		 * 方法说明:将按特定条件查询的记录转换成自定vo对象
		 */
		 ResultInfo<ParkVOSearchSum> parkVOSearch( ResultInfo <ParkVOSearchSum> result,List<Park> searchParkList,String address,String longitude,String latitude,String cityName){
			 	List<ParkVOSearch> parkVOSearchList = new ArrayList<ParkVOSearch>();
				ParkVOSearchSum parkVOSearchSum= new ParkVOSearchSum();
				 if (searchParkList!=null&&searchParkList.size()>0) {
						
						for (Park p : searchParkList) {
							ParkVOSearch parkVOSearch = new ParkVOSearch();
							String addressDetail="";
							if(p.getAddrRegion3Name()!=null&&p.getAddrRegion3Name().length()!=0){
								addressDetail=p.getAddrRegion1Name()+p.getAddrRegion2Name()+p.getAddrRegion3Name()+p.getAddrStreet();
							}else{
								addressDetail=p.getAddrRegion1Name()+p.getAddrRegion2Name()+p.getAddrStreet();
							}
							parkVOSearch.setAddressDetail(addressDetail);
							parkVOSearch.setLatitude(p.getLatitude());
							parkVOSearch.setLongitude(p.getLongitude());
							parkVOSearch.setParkName(p.getParkName());
							parkVOSearch.setParkNo(p.getParkNo());
							parkVOSearch.setParkType(p.getParkType());
							parkVOSearch.setParkPicUrl(imgPath+"/"+p.getParkPicUrl1());
							if(p.getAvailableCarNum()!=null){
								parkVOSearch.setCarNum(p.getAvailableCarNum());
							}else{
								parkVOSearch.setCarNum(0);
							}
							if(p.getDistance()!=null){
								parkVOSearch.setDistance(p.getDistance());
							}else{
								parkVOSearch.setDistance(0d);
							}
							parkVOSearch.setAddressTag(1);
							parkVOSearch.setIsRedPakcetPark(p.getIsRedPakcetPark());
							parkVOSearch.setIsRedPacketTargetPark(p.getIsRedPacketTargetPark());
							parkVOSearchList.add(parkVOSearch);
						}
						
					
						Integer parkTakeNum= searchParkList.size();
						parkVOSearchSum.setParkTakeNum(parkTakeNum);
						
						
					}
				
			 //百度搜索
				String result1 = "";
				//通过http 请求 获取 string
				try {
					String region="";
					String query = URLEncoder.encode(address,"UTF-8");
					
					//String ak="5xg2kqlDtrMlhuMjuhVA5tYZe4OQ68dY";
					String requestMethod="GET";
					if(cityName == null || "".equals(cityName)){
						region = URLEncoder.encode(city,"UTF-8");
					}else{
						 region = URLEncoder.encode(cityName,"UTF-8");
					}
					
					//query=天安门&region=北京市&city_limit=true&output=json&ak=您的密钥ak
					String requestUrl=baiduSearchURI+"?query="+query+"&region="+region+"&city_limit=true&output=json&ak="+ak;

					result1 = HttpURLRequestUtil.doMsgGet(requestUrl);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Gson gson = new Gson();
				ParkVOSearchBaiDu parkVOSearchBaiDu = gson.fromJson(result1, ParkVOSearchBaiDu.class);
				if(parkVOSearchBaiDu != null && parkVOSearchBaiDu.getStatus()==0 && parkVOSearchBaiDu.getResult() != null && parkVOSearchBaiDu.getResult().size()>0 ){
					//取出 要展示的百度搜索地址
						List<ParkVOBaidu> parkVOBaiduss =parkVOSearchBaiDu.getResult();
						for (ParkVOBaidu parkVOBaidu : parkVOBaiduss) {
							ParkVOSearch parkVOSearch=new ParkVOSearch();
							if(!"".equals(parkVOBaidu.getName()) && parkVOBaidu.getName() !=null  ){
								parkVOSearch.setParkName(parkVOBaidu.getName());
							}else{
								parkVOSearch.setParkName("");
							}
							
							parkVOSearch.setAddressTag(2);
							if(!"".equals(parkVOBaidu.getCity()) && parkVOBaidu.getCity() !=null){
								parkVOSearch.setAddressDetail(parkVOBaidu.getCity());
							}
							if(!"".equals(parkVOBaidu.getCity()) && parkVOBaidu.getCity() !=null && !"".equals(parkVOBaidu.getDistrict()) && parkVOBaidu.getDistrict() !=null ){
								parkVOSearch.setAddressDetail(parkVOBaidu.getCity()+parkVOBaidu.getDistrict());
							}
							
							String latitudeBaidu = null;
							if(parkVOBaidu.getLocation()!= null && !"".equals(parkVOBaidu.getLocation().getLat()) && parkVOBaidu.getLocation().getLat() != null ){
								 latitudeBaidu=parkVOBaidu.getLocation().getLat();
							}
							String longitudeBaidu=null;
							if(parkVOBaidu.getLocation()!= null && !"".equals(parkVOBaidu.getLocation().getLng()) && parkVOBaidu.getLocation().getLng() != null){
								longitudeBaidu=parkVOBaidu.getLocation().getLng();
							}
							parkVOSearch.setLatitude(latitudeBaidu);
							parkVOSearch.setLongitude(longitudeBaidu);
							//根据 给的 经纬度 和场站 去 算距离
							if(longitude!=null&&!longitude.equals("")&&latitude!=null&&!latitude.equals("") && longitudeBaidu!=null &&!longitudeBaidu.equals("") && latitudeBaidu!=null &&!latitudeBaidu.equals("")){
								Double distance=parkService.getDistanceByPoint(longitude,latitude,longitudeBaidu,latitudeBaidu);
								if(distance!=null){
									parkVOSearch.setDistance(ECNumberUtils.roundDoubleWithScale(distance, 2));
								}else{
									parkVOSearch.setDistance(0d);
								}
							}else{
								parkVOSearch.setDistance(0d);	
							}
							if(parkVOBaidu.getLocation()!= null && !"".equals(parkVOBaidu.getLocation().getLat()) && parkVOBaidu.getLocation().getLat() != null  && !"".equals(parkVOBaidu.getLocation().getLng()) && parkVOBaidu.getLocation().getLng() != null){
								parkVOSearchList.add(parkVOSearch);
							}
							
						}
					
				}
	
			parkVOSearchSum.setParkVOSearchs(parkVOSearchList);
			result.setData(parkVOSearchSum);
			if(parkVOSearchSum != null && parkVOSearchSum.getParkVOSearchs() !=null  && parkVOSearchSum.getParkVOSearchs().size()>0 ){
				result.setCode(OrderConstant.success_code);
				result.setMsg("");
			}else{
				result.setCode(OrderConstant.fail_code);
				result.setMsg(OrderConstant.reminder_msg);
			}
				
			
			return result;
		}
	
	/**
	 *定位查询周边场站列表
	 * */
	@RequestMapping("/aroundParkList")
	@ResponseBody
	public ResultInfo<List<ParkVOAround>> aroundParkList(String longitude,String latitude,String memberNo) {
		ResultInfo<List<ParkVOAround>> result=new ResultInfo<List<ParkVOAround>>();
		SysParam sysParam = sysParamService.getByParamKey(ParkConstant.distance_param_key);
		Double distance=Double.parseDouble(sysParam.getParamValue());//单位：米
		return parkToVoAround(result,parkService.getParkListByAround(longitude,latitude,distance,memberNo));
	}
	
	/**
	 *按区域选择取车点（手机坐标）
	 * */
	@RequestMapping("/aroundParkListByTake")
	@ResponseBody
	public ResultInfo<List<ParkVOAround>> aroundParkListByTake(String addrRegion1Name,String addrRegion2Name,String addrRegion3Name,String memberNo,String longitude, String latitude) {
		ResultInfo<List<ParkVOAround>> result=new ResultInfo<List<ParkVOAround>>();
		Query q=new Query();
		Park park=new Park();
		if(addrRegion1Name!=null&&!addrRegion1Name.equals("")){
			park.setAddrRegion1Name(addrRegion1Name);
		}
		if(addrRegion2Name!=null&&!addrRegion2Name.equals("")){
			park.setAddrRegion2Name(addrRegion2Name);
		}
		if(addrRegion3Name!=null&&!addrRegion3Name.equals("")){
			park.setAddrRegion3Name(addrRegion3Name);
		}
		park.setIsAvailable(1);
		park.setIsPublic(1);
		park.setIsDeleted(0);
		q.setQ(park);
		return parkToVoAround(result,parkService.getParkListByTake(q,memberNo,longitude,latitude));
	}
	/**
	 *按区域选择还车点（车辆坐标）
	 * */
	@RequestMapping("/aroundParkListByReturn")
	@ResponseBody
	public ResultInfo<List<ParkVOReturn>> aroundParkListByReturn(String addrRegion1Name,String addrRegion2Name,String addrRegion3Name,String longitude, String latitude) {
		ResultInfo<List<ParkVOReturn>> result=new ResultInfo<List<ParkVOReturn>>();
		Member member=getLoginMember();
		String memberNo="";
		if(member!=null){
			memberNo=member.getMemberNo();
		}
		Query q=new Query();
		Park park=new Park();
		if(addrRegion1Name!=null&&!addrRegion1Name.equals("")){
			park.setAddrRegion1Name(addrRegion1Name);
		}
		if(addrRegion2Name!=null&&!addrRegion2Name.equals("")){
			park.setAddrRegion2Name(addrRegion2Name);
		}
		if(addrRegion3Name!=null&&!addrRegion3Name.equals("")){
			park.setAddrRegion3Name(addrRegion3Name);
		}
		park.setIsAvailable(1);
		park.setIsPublic(1);
		park.setIsDeleted(0);
		q.setQ(park);
		return parkToVoReturn(result,parkService.getParkListByReturn(q,longitude,latitude,memberNo));
	}
	 /**
	 * 方法说明:将按特定条件查询的记录转换成自定vo对象
	 */
	 ResultInfo<List<ParkVO>> parkToVo(ResultInfo<List<ParkVO>> result,List<Park> paList){
	
		if (paList!=null&&paList.size()>0) {
			List<ParkVO> voList = new ArrayList<ParkVO>();
			for (Park p : paList) {
				ParkVO po = new ParkVO();
				po.setAddrRegion1Id(p.getAddrRegion1Id());
				po.setAddrRegion1Name(p.getAddrRegion1Name());
				po.setAddrRegion2Id(p.getAddrRegion2Id());
				po.setAddrRegion2Name(p.getAddrRegion2Name());
				po.setAddrRegion3Id(p.getAddrRegion3Id());
				po.setAddrRegion3Name(p.getAddrRegion3Name());
				po.setAddrStreet(p.getAddrStreet());
				po.setCityId(p.getCityId());
				po.setCityName(p.getCityName());
				po.setIsAvailable(p.getIsAvailable());
				po.setIsPublic(p.getIsPublic());
				po.setLatitude(p.getLatitude());
				po.setLongitude(p.getLongitude());
				po.setParkName(p.getParkName());
				po.setParkNo(p.getParkNo());
				po.setParkType(p.getParkType());
				voList.add(po);
			}
			result.setData(voList);
			result.setCode(OrderConstant.success_code);
			result.setMsg("");
		}else {
			result.setCode(OrderConstant.fail_code);
			result.setMsg(OrderConstant.reminder_msg);
		}
		return result;
	}
	 /**
		 * 方法说明:将按特定条件查询的记录转换成自定vo对象
		 */
		 ResultInfo<List<ParkVOAround>> parkToVoAround(ResultInfo<List<ParkVOAround>> result,List<Park> paList){
		
			if (paList!=null&&paList.size()>0) {
				List<ParkVOAround> voList = new ArrayList<ParkVOAround>();
				for (Park p : paList) {
					ParkVOAround po = new ParkVOAround();
					String addressDetail="";
					if(p.getAddrRegion3Name()!=null&&p.getAddrRegion3Name().length()!=0){
						addressDetail=p.getAddrRegion1Name()+p.getAddrRegion2Name()+p.getAddrRegion3Name()+p.getAddrStreet();
					}else{
						addressDetail=p.getAddrRegion1Name()+p.getAddrRegion2Name()+p.getAddrStreet();
					}
					
					
					
					po.setAddressDetail(addressDetail);
					po.setLatitude(p.getLatitude());
					po.setLongitude(p.getLongitude());
					po.setParkName(p.getParkName());
					po.setParkNo(p.getParkNo());
					po.setParkType(p.getParkType());
					if(p.getAvailableCarNum()!=null){
						po.setCarNum(p.getAvailableCarNum());
					}else{
						po.setCarNum(0);
					}
					//获取可用车位数
					if(p.getParkingSpaceNumber()!=null){
						po.setParkingSpaces(p.getParkingSpaceNumber()-po.getCarNum());
						if(po.getParkingSpaces()<0){
							po.setParkingSpaces(0);
						}
					}else{
						po.setParkingSpaces(0);
					}
					if(p.getDistance()!=null){
						po.setDistance(p.getDistance());
					}else{
						po.setDistance(0d);
					}
					voList.add(po);
				}
				result.setData(voList);
				result.setCode(OrderConstant.success_code);
				result.setMsg("");
			}else {
				result.setCode(OrderConstant.fail_code);
				result.setMsg(OrderConstant.reminder_msg);
			}
			return result;
		}
		 	/**
			 * 方法说明:将按特定条件查询的记录转换成自定vo对象
			 */
			 ResultInfo<List<ParkVOReturn>> parkToVoReturn(ResultInfo<List<ParkVOReturn>> result,List<Park> paList){
			
				if (paList!=null&&paList.size()>0) {
					List<ParkVOReturn> voList = new ArrayList<ParkVOReturn>();
					for (Park p : paList) {
						ParkVOReturn po = new ParkVOReturn();
						String addressDetail="";
						if(p.getAddrRegion3Name()!=null&&p.getAddrRegion3Name().length()!=0){
							addressDetail=p.getAddrRegion1Name()+p.getAddrRegion2Name()+p.getAddrRegion3Name()+p.getAddrStreet();
						}else{
							addressDetail=p.getAddrRegion1Name()+p.getAddrRegion2Name()+p.getAddrStreet();
						}
						po.setAddressDetail(addressDetail);
						po.setLatitude(p.getLatitude());
						po.setLongitude(p.getLongitude());
						po.setParkName(p.getParkName());
						po.setParkNo(p.getParkNo());
						po.setParkType(p.getParkType());
						if(p.getDistance()!=null){
							po.setDistance(p.getDistance());
						}else{
							po.setDistance(0d);
						}
						ParkStatus ps=parkStatusService.getParkStatus(p.getParkNo());
						po.setCarSpaceNum(0);
						if(ps!=null){
							if(ps.getFreeParking()!=null){
								po.setCarSpaceNum(ps.getFreeParking());
							}
						}
						voList.add(po);
					}
					result.setData(voList);
					result.setCode(OrderConstant.success_code);
					result.setMsg("");
				}else {
					result.setCode(OrderConstant.fail_code);
					result.setMsg(OrderConstant.reminder_msg);
				}
				return result;
			}
			 	/**
			 	 * 根据省市显示当前市的所有有场站的区域(区域名称，取车点数量)
			 	 * @param memberNo
			 	 * @param addrRegion1Name
			 	 * @param addrRegion2Name
			 	 * @param longitude
			 	 * @param latitude
			 	 * @return
			 	 */
				@RequestMapping("/getParkListByCityTake")
				@ResponseBody
				public ResultInfo<List<ParkRegion>> getParkListByCityTake(String memberNo,String addrRegion1Name,String addrRegion2Name,String longitude, String latitude) {
					//判断当前的省市是否是特殊直辖市 不是为0   是为1
					Integer tag=0;
					Query q=new Query();
					Park park=new Park();
					if(addrRegion1Name!=null&&!addrRegion1Name.equals("")){
						park.setAddrRegion1Name(addrRegion1Name);
						if(addrRegion1Name.equals("北京市")||addrRegion1Name.equals("上海市")||addrRegion1Name.equals("天津市")||addrRegion1Name.equals("重庆市")){
							tag=1;
						}
					}
					if(addrRegion2Name!=null&&!addrRegion2Name.equals("")){
						park.setAddrRegion2Name(addrRegion2Name);
					}
					park.setIsAvailable(1);
					park.setIsPublic(1);
					park.setIsDeleted(0);
					q.setQ(park);
					return parkService.getParkListByCityTake(q,longitude,latitude,tag,memberNo);
				}

	/**
	 *定位查询周边场站，车辆列表
	 *seaTing 座位数 
	 *
	 * */
	@RequestMapping("/getCarAndParkByAround")
	@ResponseBody
	public ResultInfo<Map<String,List<Object>>> getCarAndParkByAround(String longitude,String latitude,String memberNo,String seaTing,String carPlateNo) {
		SysParam sysParam = sysParamService.getByParamKey(ParkConstant.distance_param_key);
		Double distance = Double.parseDouble(sysParam.getParamValue());//单位：米
		if(distance != null){//由米转换成公里
			distance = distance / 1000d;
		}
		ResultInfo<Map<String,List<Object>>> result = new ResultInfo<Map<String,List<Object>>>();
		Map<String,List<Object>> info = parkService.getCarAndParkByAround(longitude,latitude,distance,0,Integer.MAX_VALUE,memberNo,seaTing,carPlateNo);
		if(info.get("car").size() > 0  || info.get("park").size() > 0 ){
			result.setData(info);
			result.setCode(OrderConstant.success_code);
			result.setMsg("");
		}else{
			result.setCode(OrderConstant.fail_code);
			result.setMsg(OrderConstant.reminder_msg2);
		}
		return result;
	}
	
	/**
	 *一键用车   返回最近的场站或车辆
	 * */
	@RequestMapping("/getNearLimitOne")
	@ResponseBody
	public ResultInfo<CarOrParkVo> getNearLimitOne(String longitude,String latitude,String memberNo,String seaTing) {
		SysParam sysParam = sysParamService.getByParamKey(ParkConstant.distance_param_key);
		Double distance = Double.parseDouble(sysParam.getParamValue());//单位：米
		if(distance != null){//由米转换成公里
			distance = distance / 1000d;
		}
		ResultInfo<CarOrParkVo> result = new ResultInfo<CarOrParkVo>();
		CarOrParkVo info = parkService.getNearLimitOne(longitude,latitude,distance,memberNo,seaTing);
		if(info!=null && info.getCarVoAround() != null || info.getParkVoAround() != null ){
			result.setData(info);
			result.setCode(OrderConstant.success_code);
			result.setMsg("");
		}else{
			result.setCode(OrderConstant.fail_code);
			result.setMsg(OrderConstant.reminder_msg2);
		}
		return result;
	}
}
