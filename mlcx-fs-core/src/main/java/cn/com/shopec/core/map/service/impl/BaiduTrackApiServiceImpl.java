package cn.com.shopec.core.map.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.HttpURLRequestUtil;
import cn.com.shopec.common.utils.JsonUtils;
import cn.com.shopec.common.utils.httpClient.HttpClientUtil;
import cn.com.shopec.core.map.model.Track;
import cn.com.shopec.core.map.model.TrackQuery;
import cn.com.shopec.core.map.model.TrackResultInfo;
import cn.com.shopec.core.map.service.BaiduGeocoderApiService;
import cn.com.shopec.core.map.service.BaiduTrackApiService;
import cn.com.shopec.core.order.service.OrderService;

/**
 * 终端设备服务器接口的service实现
 *
 */
@Service
public class BaiduTrackApiServiceImpl implements BaiduTrackApiService {

	private static final Log log = LogFactory.getLog(BaiduTrackApiServiceImpl.class);
	// 百度ak
	@Value("${ak}")
	private String AK;
	// 鹰眼服务service_id
	@Value("${serviceId}")
	private Integer SERVICEID;
	// 添加tracck属性uri
	private final String addTrackURI = "http://api.map.baidu.com/trace/v2/track/addpoint";
	// 添加一组track的uri
	private final String addTracksURI = "http://api.map.baidu.com/trace/v2/track/addpoints";
//	private final String addTracksURI = "http://localhost:8080/fs-mapi/app/testmember/testUpload.do";
	// 查询entity属性uri
	private final String getHistoryURI = "http://api.map.baidu.com/trace/v2/track/gethistory";
	
	@Resource
	private BaiduGeocoderApiService baiduGeocoderApiService;
	
	@Resource
	private OrderService orderService;

	@Override
	public ResultInfo<TrackResultInfo> addPoint(Track track) {
		ResultInfo<TrackResultInfo> resultInfo = new ResultInfo<TrackResultInfo>();
		if (track != null) {
			if (track.getLatitude() == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("经度为空");
				return resultInfo;
			} else if (track.getLongitude() == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("纬度为空");
				return resultInfo;
			} else if (track.getCoordType() == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("坐标类型为空");
				return resultInfo;
			} else if (track.getEntityName() == null || "".equals(track.getEntityName())) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("entity唯一标识entity_name为空");
				return resultInfo;
			} else if (track.getLocTime() == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("轨迹点采集的GPS时间为空");
				return resultInfo;
			} else {
				Map<String, Object> paramMap = new HashMap<String ,Object>();
				paramMap.put("ak", AK);
				paramMap.put("service_id", SERVICEID);
				paramMap.put("entity_name",track.getEntityName());
				paramMap.put("latitude", track.getLatitude());
				paramMap.put("longitude",track.getLongitude());
				paramMap.put("coord_type",track.getCoordType());
				paramMap.put("entity_name",track.getEntityName());
				paramMap.put("loc_time",ECDateUtils.date2UnixTimestamp(track.getLocTime()));
				if(track.getDirection() != null){
					paramMap.put("direction",track.getDirection());
				}
				if(track.getSpeed() != null){
					paramMap.put("speed",track.getSpeed());
				}
				if(track.getCarStatus() != null){
					paramMap.put("carStatus",track.getCarStatus());
				}
				if(track.getCarUserageStatus() != null){
					paramMap.put("carUserageStatus",track.getCarUserageStatus());
				}
				if(track.getDocumentNo() != null){
					paramMap.put("documentNo",track.getDocumentNo());
				}
				if(track.getMemberName() != null){
					paramMap.put("memberName",track.getMemberName());
				}
				if(track.getPhone() != null){
					paramMap.put("phone",track.getPhone());
				}
				if(track.getPower() != null){
					paramMap.put("power",track.getPower());
				}
				if(track.getCarPlateNo() != null){
					paramMap.put("carPlateNo",track.getCarPlateNo());
				}
				if(track.getMileage() != null){
					paramMap.put("mileage",track.getMileage());
				}
				
				if(track.getLatitude() != null && track.getLongitude() != null){
					String address = baiduGeocoderApiService.getAddressByGPS(track.getLatitude(), track.getLongitude());
					paramMap.put("address", address);
				}
				
				String result = "";
				try {
					result = HttpClientUtil.post(this.addTrackURI, paramMap);
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
				
				if(result != null && !result.equals("")) {
					Gson gson = new Gson();
					TrackResultInfo trackResultInfo = gson.fromJson(result, TrackResultInfo.class);
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(trackResultInfo);
				} else {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("提交数据至百度失败");
				}
			}
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("track为空");
		}
		return resultInfo;
	}
	
	/**
	 * 为一个entity，增加一组轨迹数据
	 * @param entityName
	 * @param tracks
	 * @return
	 */
	public ResultInfo<TrackResultInfo> addPoints(String entityName, List<Track> tracks) {
		ResultInfo<TrackResultInfo> resultInfo = new ResultInfo<TrackResultInfo>();
		if(entityName == null || "".equals(entityName)) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("entity唯一标识entity_name为空");
			return resultInfo;
		}
		if(tracks == null  || tracks.isEmpty()) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("轨迹参数为空");
			return resultInfo;
		}
		List<Track> validTracks = new ArrayList<Track>(tracks.size());
		for(Track track : tracks) {
			if (track.getLatitude() == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("经度为空");
				return resultInfo;
			} else if (track.getLongitude() == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("纬度为空");
				return resultInfo;
			} else if (track.getCoordType() == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("坐标类型为空");
				return resultInfo;
			} else if (track.getLocTime() == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("轨迹点采集的GPS时间为空");
				return resultInfo;
			}
			validTracks.add(track);
		}
		
		if(validTracks.isEmpty()) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("无有效轨迹点");
			return resultInfo;
		}
		
		Map<String, String> textParams = new HashMap<String, String>();
		textParams.put("ak", AK);
		textParams.put("service_id", String.valueOf(SERVICEID));
		textParams.put("entity_name", entityName);
		
		Map<String, Map<String, byte[]>> fileParams = new HashMap<String, Map<String, byte[]>>();
		Map<String, byte[]> fileData = new HashMap<String, byte[]>();
		fileData.put(entityName+ ECDateUtils.formatTime(new Date(), "yyyyMMddHHmmss") + ".csv", createCsvFileContent(validTracks));
		fileParams.put("point_list", fileData);
		
		String result = "";
		try {
			result = HttpURLRequestUtil.doMsgPost(this.addTracksURI, textParams, fileParams, 1000, 3000);
			Gson gson = new Gson();
			TrackResultInfo trackResultInfo = gson.fromJson(result, TrackResultInfo.class);
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(trackResultInfo);
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(result);
			log.error(e.getMessage(), e);
		}
		
		return resultInfo;
	}

	/**
	 * 默认纠偏
	 */
	@Override
	public ResultInfo<TrackResultInfo> getHistory(TrackQuery trackQuery)throws Exception {
		ResultInfo<TrackResultInfo> resultInfo = new ResultInfo<TrackResultInfo>();
		if (trackQuery != null) {
			if (trackQuery.getStartTime() == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("开始时间为空");
			} else if (trackQuery.getEndTime() == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("结束时间为空");
			} else if (trackQuery.getEntityName() == null || "".equals(trackQuery.getEntityName())) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("entity唯一标识entity_name为空");
			} else if (trackQuery.getPageSize() == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("pageSize为空");
			} else {
				String param = "";
				String result = "";
				if(trackQuery.getEndTime().after(new Date())){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("结束时间不能大于当前时间");
				}else if(trackQuery.getEndTime().before(trackQuery.getStartTime())){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("结束时间不能早于起始时间");
				}else if(trackQuery.getEndTime().getTime()-trackQuery.getStartTime().getTime()>(24L*60*60*1000)){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("结束时间与起始时间差在24小时之内");
				}else{
					if (trackQuery.getSortType() != null) {
						param = this.getHistoryURI + "?ak=" + AK + "&service_id=" + SERVICEID + "&start_time="
								+ ECDateUtils.date2UnixTimestamp(trackQuery.getStartTime()) + "&end_time=" + ECDateUtils.date2UnixTimestamp(trackQuery.getEndTime()) + "&entity_name="
								+ trackQuery.getEntityName()
								+ "&is_processed=1&process_option=need_denoise=1,need_vacuate=1,need_mapmatch=1"
								+ "&sort_type=" + trackQuery.getSortType() + "&page_size=" + trackQuery.getPageSize();
					} else if (trackQuery.getSimpleReturn() != null) {
						param = this.getHistoryURI + "?ak=" + AK + "&service_id=" + SERVICEID + "&start_time="
								+ ECDateUtils.date2UnixTimestamp(trackQuery.getStartTime()) + "&end_time=" + ECDateUtils.date2UnixTimestamp(trackQuery.getEndTime()) + "&entity_name="
								+ trackQuery.getEntityName()
								+ "&is_processed=1&process_option=need_denoise=1,need_vacuate=1,need_mapmatch=1"
								+ "&simple_return=" + trackQuery.getSimpleReturn() + "&page_size="
								+ trackQuery.getPageSize();
					} else if (trackQuery.getSimpleReturn() != null && trackQuery.getSortType() != null) {
						param = this.getHistoryURI + "?ak=" + AK + "&service_id=" + SERVICEID + "&start_time="
								+ ECDateUtils.date2UnixTimestamp(trackQuery.getStartTime()) + "&end_time=" + ECDateUtils.date2UnixTimestamp(trackQuery.getEndTime()) + "&entity_name="
								+ trackQuery.getEntityName()
								+ "&is_processed=1&process_option=need_denoise=1,need_vacuate=1,need_mapmatch=1"
								+ "&sort_type=" + trackQuery.getSortType() + "&simple_return="
								+ trackQuery.getSimpleReturn() + "&page_size=" + trackQuery.getPageSize();
					} else {
						param = this.getHistoryURI + "?ak=" + AK + "&service_id=" + SERVICEID + "&start_time="
								+ ECDateUtils.date2UnixTimestamp(trackQuery.getStartTime()) + "&end_time=" + ECDateUtils.date2UnixTimestamp(trackQuery.getEndTime()) + "&entity_name="
								+ trackQuery.getEntityName()
								+ "&is_processed=1&process_option=need_denoise=1,need_vacuate=1,need_mapmatch=1"
								+ "&page_size=" + trackQuery.getPageSize();
					}
//					System.err.println(param);
				}
				try {
					result = HttpClientUtil.get(param);
					TrackResultInfo trackResultInfo = JsonUtils.parse2Object(result, TrackResultInfo.class);
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(trackResultInfo);
				} catch (Exception e) {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(result);
					log.error("getHistory异常" + result);
				}
			}
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("trackQuery为空");
		}
		return resultInfo;
	}
	
	/**
	 * 创建轨迹的csv文件内容
	 * @param tracks
	 * @return
	 */
	private byte[] createCsvFileContent(List<Track> tracks) {
		byte[] buf = null;
		StringBuilder sb = new StringBuilder();
		sb.append("longitude,latitude,loc_time,coord_type,speed,direction,height,radius");
		sb.append("\r\n");
		
		if(tracks != null && !tracks.isEmpty()) {
			for(Track track : tracks) {
				sb.append(track.getLongitude());
				sb.append(",");
				sb.append(track.getLatitude());
				sb.append(",");
				sb.append(ECDateUtils.date2UnixTimestamp(track.getLocTime()));
				sb.append(",");
				sb.append(track.getCoordType());
				sb.append(",");
				sb.append(track.getSpeed() == null ? "" : track.getSpeed());
				sb.append(",");
				sb.append(track.getDirection() == null ? "" : track.getDirection());
				sb.append(",");
//				sb.append(track.getHeight() == null ? "" : track.getHeight());
				sb.append("");
				sb.append(",");
				sb.append(track.getRadius() == null ? "" : track.getRadius());
				sb.append("\r\n");
			}
		}
		buf = sb.toString().getBytes();
		return buf;
	}
	
}
