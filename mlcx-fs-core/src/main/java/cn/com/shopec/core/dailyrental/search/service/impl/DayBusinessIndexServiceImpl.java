package cn.com.shopec.core.dailyrental.search.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import cn.com.shopec.core.dailyrental.dao.ParkDayDao;
import cn.com.shopec.core.dailyrental.model.ParkDay;
import cn.com.shopec.core.dailyrental.search.common.SolrDocConstants;
import cn.com.shopec.core.dailyrental.search.service.DayBaseIndexService;
import cn.com.shopec.core.dailyrental.search.service.DayBusinessIndexService;
import cn.com.shopec.core.dailyrental.search.service.DaySearchService;
import cn.com.shopec.core.map.service.BaiduGeocoderApiService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 索引实现类
 *
 */
@Service
public class DayBusinessIndexServiceImpl implements DayBusinessIndexService {

	@Resource
	private DayBaseIndexService baseIndexService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private DaySearchService searchService;
	@Resource
	private BaiduGeocoderApiService baiduGeocoderApiService;
	@Resource
	private ParkDayDao parkDayDao;

	/**
	 * 更新场站
	 */
	@Override
	public boolean saveOrUpdatePark(String parkId) {
		ParkDay parkDay = parkDayDao.get(parkId);
		if (parkDay == null) {
			return false;
		}
		// 转换SolrDoc对象
		SolrInputDocument solrInputDocument = this.convertSolrDoc(parkDay);
		if (solrInputDocument != null) {
			return baseIndexService.update(solrInputDocument);
		}
		return false;
	}
	

	/**
	 * ParkDay，转为SolrDoc对象
	 * 
	 * @param park
	 * @param parkCompanyRelList
	 * @param sysParkType
	 * @return
	 */
	private SolrInputDocument convertSolrDoc(ParkDay parkDay) {
		if (parkDay == null || parkDay.getParkId() == null || parkDay.getParkId().length() == 0) {
			return null;
		}
		SolrInputDocument solrDocment = new SolrInputDocument();
		solrDocment.addField(SolrDocConstants.DOC_ID_FIELD, SolrDocConstants.PARK_ID_PREFIX + parkDay.getParkId());
		if (parkDay.getParkId() != null) {// 编号
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", parkDay.getParkId());
			solrDocment.addField(SolrDocConstants.DATA_NO_FIELD, valueMap);
		}
		if (parkDay.getParkName() != null) {// 名字
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", parkDay.getParkName());
			solrDocment.addField(SolrDocConstants.DATA_NAME_FIELD, valueMap);
		}
		String longitude = parkDay.getLongitude() == null ? null : String.valueOf(parkDay.getLongitude());
		String latitude = parkDay.getLongitude() == null ? null : String.valueOf(parkDay.getLatitude());
		if (latitude != null) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", latitude);
			solrDocment.addField(SolrDocConstants.LATITUDE_FIELD, valueMap);
		}
		if (longitude != null) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", longitude);
			solrDocment.addField(SolrDocConstants.LONGITUDE_FIELD, valueMap);
		}
		if (longitude != null && latitude != null) { // 门店位置，格式为：纬度,经度
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", latitude + "," + longitude);
			solrDocment.addField(SolrDocConstants.PARK_LOCATION_FIELD, valueMap);
		}

		if (parkDay.getAddrStreet() != null) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			//拼接地址
			parkDay.setAddrStreet(parkDay.getAddrRegion1Name()+parkDay.getAddrRegion2Name()+parkDay.getAddrRegion3Name()+parkDay.getAddrStreet());
			valueMap.put("set", parkDay.getAddrStreet());
			solrDocment.addField(SolrDocConstants.ADDRESS_FIELD, valueMap);
		}
		if (parkDay.getIsVailable() != null) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", parkDay.getIsVailable());
			solrDocment.addField(SolrDocConstants.IS_AVAILABLE_FIELD, valueMap);
		}
		//cityId
		if(parkDay.getCityId()!=null){
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", parkDay.getCityId());
			solrDocment.addField(SolrDocConstants.CITY_ID_FIELD, valueMap);
		}
		//商家id
		if(parkDay.getMerchantId()!=null){
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", parkDay.getMerchantId());
			solrDocment.addField(SolrDocConstants.MERCHANT_ID_FIELD, valueMap);
		}
		//圆形场站 电子围栏半径
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.put("set", "500");
		solrDocment.addField(SolrDocConstants.ELEC_FENCE_RADIUS_FIELD, valueMap);

		Map<String, Object> dataTypeMap = new HashMap<String, Object>();
		dataTypeMap.put("set", SolrDocConstants.DATA_TYPE_PARK);
		solrDocment.addField(SolrDocConstants.DATA_TYPE_FIELD, dataTypeMap);

		Map<String, Object> indexedTimeMap = new HashMap<String, Object>();
		indexedTimeMap.put("set", new Date());
		solrDocment.addField(SolrDocConstants.INDEXED_TIME_FIELD, indexedTimeMap);

		// 以下为车辆的字段值,设置默认值方便车辆，场站同时查询
		// 设置车辆状态为0（空闲）
		Map<String, Object> carStatusValueMap = new HashMap<String, Object>();
		carStatusValueMap.put("set", 0);
		solrDocment.addField(SolrDocConstants.CAR_STATUS_FIELD, carStatusValueMap);

		// 设置车辆是否场站内状态为0(不在场站内)
		Map<String, Object> isInParkValueMap = new HashMap<String, Object>();
		isInParkValueMap.put("set", 0);
		solrDocment.addField(SolrDocConstants.IS_IN_PARK_FIELD, isInParkValueMap);


		return solrDocment;
	}
}
