package cn.com.shopec.core.search.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import cn.com.shopec.common.utils.ECGeoCoordinateTransformUtil;
import cn.com.shopec.core.car.dao.CarDao;
import cn.com.shopec.core.car.model.Car;
import cn.com.shopec.core.car.model.CarStatus;
import cn.com.shopec.core.car.service.CarStatusService;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.map.service.BaiduGeocoderApiService;
import cn.com.shopec.core.resource.common.ParkConstant;
import cn.com.shopec.core.resource.dao.ParkDao;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.resource.model.ParkCompanyRel;
import cn.com.shopec.core.resource.service.ParkCompanyRelService;
import cn.com.shopec.core.search.common.SolrDocConstants;
import cn.com.shopec.core.search.service.BaseIndexService;
import cn.com.shopec.core.search.service.BusinessIndexService;
import cn.com.shopec.core.search.service.SearchService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 索引实现类
 *
 */
@Service
public class BusinessIndexServiceImpl implements BusinessIndexService {

	@Resource
	private BaseIndexService baseIndexService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private SearchService searchService;
	@Resource
	private BaiduGeocoderApiService baiduGeocoderApiService;
	@Resource
	public CarStatusService carStatusService;
	@Resource
	public ParkCompanyRelService parkCompanyRelService;

	@Resource
	private CarDao carDao;
	@Resource
	private ParkDao parkDao;

	/**
	 * 更新场站
	 */
	@Override
	public boolean saveOrUpdatePark(String parkNo) {
		Park park = parkDao.get(parkNo);
		// 获取系统设置的场站类型参数
		SysParam sysParam = sysParamService.getByParamKey(ParkConstant.PARK_TYPE);
		// 获取该场站已经所属的集团列表
		ParkCompanyRel parkCompanyRelSearch = new ParkCompanyRel();
		parkCompanyRelSearch.setParkId(parkNo);
		List<ParkCompanyRel> parkCompanyRelList = parkCompanyRelService
				.getParkCompanyRelList(new Query(parkCompanyRelSearch));

		if (park == null || sysParam == null) {
			return false;
		}

		// 转换SolrDoc对象
		SolrInputDocument solrInputDocument = this.convertSolrDoc(park, parkCompanyRelList, sysParam.getParamValue());
		if (solrInputDocument != null) {
			return baseIndexService.update(solrInputDocument);
		}
		return false;
	}

	/**
	 * 更新车辆
	 */
	@Override
	public boolean saveOrUpdateCar(String carNo) {
		Car car = carDao.get(carNo);
		CarStatus carStatus = carStatusService.getCarStatus(carNo);
		if (car == null || carStatus == null) {
			return false;
		}

		// 转换SolrDoc对象
		SolrInputDocument solrInputDocument = this.convertSolrDoc(car, carStatus);
		if (solrInputDocument != null) {
			return baseIndexService.update(solrInputDocument);
		}
		return false;
	}

	/**
	 * car,carstatus对象，转为SolrDoc对象
	 * 
	 * @param car
	 * @param carStatus
	 * @return
	 */
	private SolrInputDocument convertSolrDoc(Car car, CarStatus carStatus) {
		if (car == null || car.getCarNo() == null || car.getCarNo().length() == 0 || carStatus == null) {
			return null;
		}
		SolrInputDocument solrDocment = new SolrInputDocument();
		solrDocment.addField(SolrDocConstants.DOC_ID_FIELD, SolrDocConstants.CAR_ID_PREFIX + car.getCarNo());
		solrDocment.addField("_version_", 0);
		if (car.getCarNo() != null) {// 编号
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", car.getCarNo());
			solrDocment.addField(SolrDocConstants.DATA_NO_FIELD, valueMap);
		}
		if (car.getCarPlateNo() != null) {// 名字
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", car.getCarPlateNo());
			solrDocment.addField(SolrDocConstants.DATA_NAME_FIELD, valueMap);
		}
		if (car.getSeaTing() != null && !"".equals(car.getSeaTing())) {// 座位数
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", car.getSeaTing());
			solrDocment.addField(SolrDocConstants.DATA_SEA_TING, valueMap);
		}
		
		if (car.getCarModelName() != null) {// 车型名称
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", car.getCarModelName());
			solrDocment.addField(SolrDocConstants.CAR_MODELNAME_FIELD, valueMap);
		}
		if (car.getOnlineStatus() != null) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", car.getOnlineStatus());
			solrDocment.addField(SolrDocConstants.IS_AVAILABLE_FIELD, valueMap);
		}
		if (carStatus.getLocationParkNo() == null || carStatus.getLocationParkNo().equals("")) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", 0);
			solrDocment.addField(SolrDocConstants.IS_IN_PARK_FIELD, valueMap);
		} else {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", 1);
			solrDocment.addField(SolrDocConstants.IS_IN_PARK_FIELD, valueMap);
		}
		if (car.getDedicatedCompanyId() != null) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", car.getDedicatedCompanyId());
			solrDocment.addField(SolrDocConstants.COMPANY_ID_FIELD, valueMap);
		}
		if (carStatus.getUserageStatus() != null) {// 车辆使用状态
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", carStatus.getUserageStatus());
			solrDocment.addField(SolrDocConstants.CAR_STATUS_FIELD, valueMap);
		}
		if (carStatus.getPower() != null) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", carStatus.getPower());
			solrDocment.addField(SolrDocConstants.POWER_FIELD, valueMap);
		}

		String longitude = carStatus.getLongitude() == null ? null : String.valueOf(carStatus.getLongitude());
		String latitude = carStatus.getLongitude() == null ? null : String.valueOf(carStatus.getLatitude());
		if (longitude != null && latitude != null) {
			double[] bdCoord = ECGeoCoordinateTransformUtil.wgs84tobd09(carStatus.getLongitude(),
					carStatus.getLatitude());
			longitude = "" + bdCoord[0]; // 经度（百度坐标）
			latitude = "" + bdCoord[1]; // 纬度（百度坐标）
		}

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
		if (longitude != null && latitude != null) { // 多边形几何中心坐标，格式为：纬度,经度
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", latitude + "," + longitude);
			solrDocment.addField(SolrDocConstants.PARK_LOCATION_FIELD, valueMap);
		}

		if (carStatus.getLatitude() != null && carStatus.getLongitude() != null) {
			String address = baiduGeocoderApiService.getAddressByGPS(carStatus.getLatitude(), carStatus.getLongitude());
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", address);
			solrDocment.addField(SolrDocConstants.ADDRESS_FIELD, valueMap);
		}

		Map<String, Object> dataTypeMap = new HashMap<String, Object>();
		dataTypeMap.put("set", SolrDocConstants.DATA_TYPE_CAR);
		solrDocment.addField(SolrDocConstants.DATA_TYPE_FIELD, dataTypeMap);

		Map<String, Object> indexedTimeMap = new HashMap<String, Object>();
		indexedTimeMap.put("set", new Date());
		solrDocment.addField(SolrDocConstants.INDEXED_TIME_FIELD, indexedTimeMap);

		// 以下为场站的字段值,设置默认值方便车辆，场站同时查询
		Map<String, Object> isViewMap = new HashMap<String, Object>();
		isViewMap.put("set", 1);
		solrDocment.addField(SolrDocConstants.IS_VIEW_FIELD, isViewMap);

		return solrDocment;
	}

	/**
	 * Park，ParkCompanyRelList对象，转为SolrDoc对象
	 * 
	 * @param park
	 * @param parkCompanyRelList
	 * @param sysParkType
	 * @return
	 */
	private SolrInputDocument convertSolrDoc(Park park, List<ParkCompanyRel> parkCompanyRelList, String sysParkType) {
		if (park == null || park.getParkNo() == null || park.getParkNo().length() == 0 || sysParkType == null) {
			return null;
		}
		SolrInputDocument solrDocment = new SolrInputDocument();
		solrDocment.addField(SolrDocConstants.DOC_ID_FIELD, SolrDocConstants.PARK_ID_PREFIX + park.getParkNo());
		solrDocment.addField("_version_", 0);
		if (park.getParkNo() != null) {// 编号
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", park.getParkNo());
			solrDocment.addField(SolrDocConstants.DATA_NO_FIELD, valueMap);
		}
		if (park.getParkName() != null) {// 名字
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", park.getParkName());
			solrDocment.addField(SolrDocConstants.DATA_NAME_FIELD, valueMap);
		}
		String longitude = park.getLongitude() == null ? null : String.valueOf(park.getLongitude());
		String latitude = park.getLongitude() == null ? null : String.valueOf(park.getLatitude());
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
		if (longitude != null && latitude != null) { // 多边形几何中心坐标，格式为：纬度,经度
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", latitude + "," + longitude);
			solrDocment.addField(SolrDocConstants.PARK_LOCATION_FIELD, valueMap);
		}

		if (park.getAddrStreet() != null) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", park.getAddrStreet());
			solrDocment.addField(SolrDocConstants.ADDRESS_FIELD, valueMap);
		}
		if (park.getIsView() != null) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", park.getIsView());
			solrDocment.addField(SolrDocConstants.IS_VIEW_FIELD, valueMap);
		}
		if (park.getIsAvailable() != null) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", park.getIsAvailable());
			solrDocment.addField(SolrDocConstants.IS_AVAILABLE_FIELD, valueMap);
		}
		if (park.getParkType() != null) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", park.getParkType());
			solrDocment.addField(SolrDocConstants.PARK_TYPE_FIELD, valueMap);
		}
		if (park.getParkingSpaces() != null) {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", park.getParkingSpaces());
			solrDocment.addField(SolrDocConstants.PARKING_SPACES_FIELD, valueMap);
		}

		if (parkCompanyRelList != null) {
			String companyIds = "";
			for (ParkCompanyRel parkCompanyRel : parkCompanyRelList) {
				companyIds += "," + parkCompanyRel.getCompanyId();
			}
			if (!companyIds.equals("")) {
				companyIds = companyIds.substring(1);
			}
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", companyIds);
			solrDocment.addField(SolrDocConstants.COMPANY_ID_FIELD, valueMap);
		}

		// 系场站类型0是 圆形场站，1 多边形场站
		if (sysParkType.equals("0")) {
			if (park.getElectronicFenceRadius() != null) {
				// 电子围栏半径
				Map<String, Object> valueMap = new HashMap<String, Object>();
				valueMap.put("set", park.getElectronicFenceRadius());
				solrDocment.addField(SolrDocConstants.ELEC_FENCE_RADIUS_FIELD, valueMap);
			}
		} else {
			if (park.getPloygonPoints() != null) {
				// 多边形坐标
				Map<String, Object> valueMap = new HashMap<String, Object>();
				valueMap.put("set", "POLYGON((" + park.getPloygonPoints() + "))");
				solrDocment.addField(SolrDocConstants.PARK_GEO_FIELD, valueMap);
			}
		}

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

		// 设置车辆电量为100（100%的电量）
		Map<String, Object> powerValueMap = new HashMap<String, Object>();
		powerValueMap.put("set", 100d);
		solrDocment.addField(SolrDocConstants.POWER_FIELD, powerValueMap);

		return solrDocment;
	}
}
