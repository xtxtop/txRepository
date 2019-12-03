package cn.com.shopec.core.search.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;

import cn.com.shopec.core.resource.common.ParkConstant;
import cn.com.shopec.core.search.common.SolrDocConstants;
import cn.com.shopec.core.search.factory.SolrServerFactory;
import cn.com.shopec.core.search.model.SearchCondition;
import cn.com.shopec.core.search.model.SearchResult;
import cn.com.shopec.core.search.model.SolrDoc;
import cn.com.shopec.core.search.service.SearchService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 搜索实现类
 *
 */
@Service
public class SearchServiceImpl implements SearchService {

	private static final Log log = LogFactory.getLog(SearchServiceImpl.class);

	@Resource(name = "parkSearchSolrServer")
	private SolrServerFactory solrServerFactory;
	@Resource
	private SysParamService sysParamService;

	/**
	 * 根据位置，搜索一定距离内场站
	 * 
	 * @param condition
	 * @return
	 */
	public SearchResult searchParkByPosition(SearchCondition condition) {
		SearchResult result = null;

		SolrQuery solrQuery = new SolrQuery();
		this.configSolrQuery4SearchParkByPosition(solrQuery, condition, true); // 依据传入的OutletQuery，配置SolrQuery

		QueryResponse response = null;

		try {
			response = this.solrServerFactory.getServer().query(solrQuery); // 使用solr进行搜索
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		if (response != null) { // 有搜索结果
			result = new SearchResult();
			this.fetchSearchResult4SearchParkByPosition(result, response); // 取出搜索结果

		}

		return result;
	}

	/**
	 * 取出搜索结果
	 * 
	 * @param result
	 * @param response
	 */
	private void fetchSearchResult4SearchParkByPosition(SearchResult result, QueryResponse response) {
		if (response != null) {
			SolrDocumentList solrDocList = response.getResults();
			List<SolrDoc> resDocs = new ArrayList<SolrDoc>(solrDocList.size());
			for (Iterator<SolrDocument> it = solrDocList.iterator(); it.hasNext();) {
				SolrDocument solrDoc = it.next();
				SolrDoc resDoc = this.convert2SearchResultDoc(solrDoc);
				resDocs.add(resDoc);
			}
			result.setDatas(resDocs); // 搜索结果文档
			result.setTotal(solrDocList.getNumFound()); // 命中数
			result.setCostTime(response.getQTime()); // 搜索花费时间

		}
	}

	/**
	 * 针对根据位置，搜索一定距离内场站的搜索功能，配置Solr查询对象
	 * 
	 * @param solrQuery
	 * @param condition
	 * @param fetchDocs
	 */
	private void configSolrQuery4SearchParkByPosition(SolrQuery solrQuery, SearchCondition condition,
			boolean fetchDocs) {
		solrQuery.setQuery(SolrDocConstants.DATA_TYPE_FIELD + ":" + SolrDocConstants.DATA_TYPE_PARK); // 配置过滤条件（场站编号）
		if (condition == null) {
			return;
		}

		if (condition.getDataNo() != null && condition.getDataNo().length() != 0) {
			solrQuery.addFilterQuery(SolrDocConstants.DOC_ID_FIELD + ":" + SolrDocConstants.PARK_ID_PREFIX + condition.getDataNo()); // 配置过滤条件（场站编号）
		}
		// 系统参数 场站类型0是 圆形场站，1 多边形场站
		SysParam sysParam = sysParamService.getByParamKey(ParkConstant.PARK_TYPE);
		if (sysParam != null && sysParam.getParamValue() != null) {
			if (sysParam.getParamValue().equals("0")) {
				if (condition.getLongitude() != null && condition.getLatitude() != null && condition.getRadius() != null
						&& condition.getRadius() > 0.0d) {
					// 配置过滤条件（离传入的当做位置一定距离内的场站）
					solrQuery.addFilterQuery("{!geofilt pt=" + condition.getLatitude() + "," + condition.getLongitude()
							+ " sfield=" + SolrDocConstants.PARK_LOCATION_FIELD + " d=" + condition.getRadius() + "}");
					// 设置返回的field，除了定义在schema.xml中的field之外，还返回一个pseudo-field:
					// _dist_，其值是当前位置到场站的距离（直线距离，公里数）
					solrQuery.addField("*");
					solrQuery.addField("_dist_:geodist(" + SolrDocConstants.PARK_LOCATION_FIELD + "," + condition.getLatitude()
							+ "," + condition.getLongitude() + ")");

					// 配置排序
					ORDER order = condition.isDescOrder() ? SolrQuery.ORDER.desc : SolrQuery.ORDER.asc;
					solrQuery.addSort("geodist(" + SolrDocConstants.PARK_LOCATION_FIELD + "," + condition.getLatitude() + ","
							+ condition.getLongitude() + ")", order); // 根据距离排序
				}

				if (!fetchDocs) { // 如果不需要返回solr中的文档数据
					solrQuery.setRows(0);
				}
			} else {
				if (condition.getLongitude() != null && condition.getLatitude() != null) {
//					solrQuery.setQuery("*:*");
					String point = condition.getLongitude() + " " + condition.getLatitude();
					solrQuery.addFilterQuery(SolrDocConstants.PARK_GEO_FIELD + ":\"Intersects(" + point + ")\"");
					solrQuery.addField("*");
					solrQuery.addField("_dist_:geodist(parkLocation," + condition.getLatitude() + ","
							+ condition.getLongitude() + ")");

					// 配置排序
					ORDER order = SolrQuery.ORDER.asc;
					solrQuery.addSort("geodist(" + SolrDocConstants.PARK_LOCATION_FIELD + "," + condition.getLatitude() + ","
							+ condition.getLongitude() + ")", order); // 根据距离排序
				}
				if (!fetchDocs) { // 如果不需要返回solr中的文档数据
					solrQuery.setRows(0);
				}
			}
		}
	}

	/**
	 * 将SolrDocument对象转为ParkSearchResultDoc对象
	 * 
	 * @param solrDoc
	 * @return
	 */
	private SolrDoc convert2SearchResultDoc(SolrDocument solrDoc) {
		if (solrDoc == null) {
			return null;
		}
		SolrDoc doc = new SolrDoc();

		doc.setDocId((String) solrDoc.getFieldValue(SolrDocConstants.DOC_ID_FIELD)); // 文档id
		doc.setDataNo((String) solrDoc.getFieldValue(SolrDocConstants.DATA_NO_FIELD)); // 场站或车辆编号
		doc.setDataName((String) solrDoc.getFieldValue(SolrDocConstants.DATA_NAME_FIELD)); // 场站名称/车辆识别号
		doc.setAddress((String) solrDoc.getFieldValue(SolrDocConstants.ADDRESS_FIELD)); //所在地址
		doc.setLongitude((String) solrDoc.getFieldValue(SolrDocConstants.LONGITUDE_FIELD)); // 所在经度
		doc.setLatitude((String) solrDoc.getFieldValue(SolrDocConstants.LATITUDE_FIELD)); // 所在纬度
		doc.setDataType((Integer) solrDoc.getFieldValue(SolrDocConstants.DATA_TYPE_FIELD)); // 类型（1.场站，2车辆识别号）
		doc.setIsView((Integer) solrDoc.getFieldValue(SolrDocConstants.IS_VIEW_FIELD)); // 是否显示
		doc.setIsInPark((Integer) solrDoc.getFieldValue(SolrDocConstants.IS_IN_PARK_FIELD)); // 是否在场站内
		doc.setCompanyId((String) solrDoc.getFieldValue(SolrDocConstants.COMPANY_ID_FIELD)); // 集团id
		doc.setIsAvailable((Integer) solrDoc.getFieldValue(SolrDocConstants.IS_AVAILABLE_FIELD)); // 是否上线，启停用
		doc.setCarModelName((String) solrDoc.getFieldValue(SolrDocConstants.CAR_MODELNAME_FIELD)); // 车型
		doc.setCarStatus((Integer) solrDoc.getFieldValue(SolrDocConstants.CAR_STATUS_FIELD)); // 车辆状态
		doc.setParkType((Integer) solrDoc.getFieldValue(SolrDocConstants.PARK_TYPE_FIELD)); // 场站类型
		doc.setParkingSpaces((Integer) solrDoc.getFieldValue(SolrDocConstants.PARKING_SPACES_FIELD)); // 场站类型
		doc.setPower((Double) solrDoc.getFieldValue(SolrDocConstants.POWER_FIELD));
		doc.setElecFenceRadius((Integer) solrDoc.getFieldValue(SolrDocConstants.ELEC_FENCE_RADIUS_FIELD)); // 场站电子围栏半径
		if (solrDoc.containsKey("_dist_")) {// _dist_是一个pseudo-field，是计算得到的当前点到场站的距离
			doc.setDistance((Double) solrDoc.getFieldValue("_dist_"));
		}
		return doc;
	}

	@Override
	public SolrDoc searchUniquePark(String carNo) {
		return searchUniqueData(SolrDocConstants.CAR_ID_PREFIX + carNo);
	}

	@Override
	public SolrDoc searchUniqueCar(String parkNo) {
		return searchUniqueData(SolrDocConstants.PARK_ID_PREFIX + parkNo);
	}

	@Override
	public SolrDoc searchUniqueData(String docId) {
		SearchResult result = null;
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("*:*"); // 查询全部
		// 配置过滤条件
		if (docId != null && docId.length() != 0) {
			solrQuery.addFilterQuery(docId);
		}
		QueryResponse response = null;
		try {
			response = this.solrServerFactory.getServer().query(solrQuery); // 使用solr进行搜索
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (response != null) { // 有搜索结果
			result = new SearchResult();
			this.fetchSearchResult4SearchParkByPosition(result, response); // 取出搜索结果
			if (result != null && result.getTotal() > 0) {
				return result.getDatas().get(0);
			}
		}
		return null;
	}

	
	
	/**
	 * 根据位置，搜索一定距离内场站
	 * 
	 * @param condition
	 * @return
	 */
	public SearchResult searchAround(SearchCondition condition) {
		SearchResult result = null;

		SolrQuery solrQuery = new SolrQuery();
		this.configSolrQuery(solrQuery, condition);
		QueryResponse response = null;

		try {
			response = this.solrServerFactory.getServer().query(solrQuery);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		if (response != null) { // 有搜索结果
			result = new SearchResult();
			this.fetchSearchResult4SearchParkByPosition(result, response); // 取出搜索结果

		}

		return result;
	}
	
	/**
	 * 针对根据位置，搜索一定距离内场站和车辆的搜索功能
	 * 
	 * @param solrQuery
	 * @param condition
	 * @param fetchDocs
	 */
	private void configSolrQuery(SolrQuery solrQuery, SearchCondition condition) {
		String queryString = "";
		if(condition.getDataName() != null){//场站名称，车牌号
			queryString += " AND " + SolrDocConstants.DATA_NAME_FIELD + ":" + condition.getDataName();
		}
		if(condition.getDataType() != null){//数据类型（1.场站  2.车辆  3.区域）
			queryString += " AND " + SolrDocConstants.DATA_TYPE_FIELD + ":" + condition.getDataType();
		}
		if(condition.getIsInPark() != null){//车辆是否在场站内(0 否 1 是)
			queryString += " AND " + SolrDocConstants.IS_IN_PARK_FIELD + ":" + condition.getIsInPark();
		}
		if(condition.getCarStatus() != null){//0，空闲、1，已预占、2，订单中、3，调度中
			queryString += " AND " + SolrDocConstants.CAR_STATUS_FIELD + ":" + condition.getCarStatus();
		}
		if(condition.getIsView() != null){// 0：隐藏 1：可见
			queryString += " AND " +SolrDocConstants.IS_VIEW_FIELD + ":" + condition.getIsView(); 
		}
		if(condition.getIsAvailable() != null){// 0：下线，停用 1：上线，启用 
			queryString += " AND " +SolrDocConstants.IS_AVAILABLE_FIELD + ":" + condition.getIsAvailable(); 
		}
		if(condition.getPower() != null){//剩余电量
			queryString += " AND " + SolrDocConstants.POWER_FIELD + ":" + "[ "+condition.getPower()+" TO * ]";
		}
		solrQuery.setQuery(queryString.substring(5));
		
		if(null !=condition.getStarRow()){
			solrQuery.setStart(condition.getStarRow());
		}
		
		if(null !=condition.getRowNum()){
			solrQuery.setRows(condition.getRowNum());
		}
		
		//solrQuery.setQuery("*:*"); // 查询全部，无查询词（即不根据关键词搜索）

		// 系统参数 场站类型0是 圆形场站，1 多边形场站
		if (condition.getLongitude() != null && condition.getLatitude() != null && condition.getRadius() != null
				&& condition.getRadius() > 0.0d) {
			if(condition.getLongitude() != null && condition.getLatitude() != null && condition.getRadius() != null && condition.getRadius() > 0.0d) {
				//配置过滤条件（离传入的当做位置一定距离内的场站）
				// 配置过滤条件（离传入的当做位置一定距离内的场站）
				solrQuery.addFilterQuery("{!geofilt pt=" + condition.getLatitude() + "," + condition.getLongitude()
						+ " sfield=" + SolrDocConstants.PARK_LOCATION_FIELD + " d=" + condition.getRadius() + "}");
				// 设置返回的field，除了定义在schema.xml中的field之外，还返回一个pseudo-field:
				// _dist_，其值是当前位置到场站的距离（直线距离，公里数）
				
				if(condition.getSeaTing()!= null && !"".equals(condition.getSeaTing())) {
					solrQuery.addFilterQuery("seaTing:" + condition.getSeaTing()); //配置过滤条件（座位数）
				}
				solrQuery.addField("*");
				solrQuery.addField("_dist_:geodist(" + SolrDocConstants.PARK_LOCATION_FIELD + "," + condition.getLatitude()+ "," + condition.getLongitude() + ")");

				// 配置排序
				ORDER order = condition.isDescOrder() ? SolrQuery.ORDER.desc : SolrQuery.ORDER.asc;
				solrQuery.addSort("geodist(" + SolrDocConstants.PARK_LOCATION_FIELD + "," + condition.getLatitude() + ","
						+ condition.getLongitude() + ")", order); // 根据距离排序
			}
			
			
			
		}
	}
	
	
	/**
	 * 设置查询时车辆，场战的默认查询
	 * （车辆状态为空闲，未上线，不在场站内;场站为已启用，可见）
	 */
	@Override
	public SearchCondition setDefaultParmForQueryAround(SearchCondition condition){
		
		SysParam returnCarType = sysParamService.getByParamKey("RETURN_CAR_OUTSIDE_SWITCH");
		String returnCar = returnCarType == null ? "1" :returnCarType.getParamValue();	//默认 模式为场站租还车，
		if(returnCar.equals("1")){	//只有场站模式，车辆必须在场站内进行租还车， 查询条件
			condition.setIsInPark(0);	//车辆在场站
			condition.setDataType(1);	//type1 场站   2车辆   3区域
		}else if(returnCar.equals("3")){	//任意点   没有场站概念  只查车
			condition.setDataType(2);	//type   2  车，不用判断车辆是否在场站内因为没有场站概念
			condition.setCarStatus(0);	// 空闲
			SysParam sysParam = sysParamService.getByParamKey(ParkConstant.power_limit);
			Double power = Double.parseDouble(sysParam.getParamValue());// 单位：%
			condition.setPower(power);// 电量
		}else if(returnCar.equals("2")){	//场站+任意点   租还车      只查询datatype  为  1和2的，逻辑待实现
			condition.setCarStatus(0);	// 空闲
			SysParam sysParam = sysParamService.getByParamKey(ParkConstant.power_limit);
			Double power = Double.parseDouble(sysParam.getParamValue());// 单位：%
			condition.setPower(power);// 电量	场站也有电量，添加的时候默认是100 不更新
										//设置类型为空， 为空的时候即为查询 datatype不为3的情况
		}
		condition.setIsAvailable(1);// 上线，启用
		condition.setIsView(1);// 可见
	
	
		
		return condition;
	}
}
