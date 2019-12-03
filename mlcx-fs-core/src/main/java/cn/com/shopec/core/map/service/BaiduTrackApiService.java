package cn.com.shopec.core.map.service;

import java.util.List;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.map.model.Track;
import cn.com.shopec.core.map.model.TrackQuery;
import cn.com.shopec.core.map.model.TrackResultInfo;

/**
 * 百度操作Entity接口的service
 *
 */
public interface BaiduTrackApiService {

	/**
	 * 添加轨迹(单个轨迹点)
	 * 
	 * @param track
	 * @return
	 */
	public ResultInfo<TrackResultInfo> addPoint(Track track);
	
	/**
	 * 为一个entity，增加一组轨迹数据
	 * @param entityName
	 * @param tracks
	 * @return
	 */
	public ResultInfo<TrackResultInfo> addPoints(String entityName, List<Track> tracks);
	
	/**
	 * 查询轨迹
	 * @param trackQuery
	 * @return
	 */
	public ResultInfo<TrackResultInfo> getHistory(TrackQuery trackQuery)throws Exception;
	
}
