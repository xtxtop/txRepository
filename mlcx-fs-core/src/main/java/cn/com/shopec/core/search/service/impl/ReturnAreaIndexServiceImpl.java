package cn.com.shopec.core.search.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import cn.com.shopec.common.utils.ECGeoCoordinateTransformUtil;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.core.resource.model.ReturnArea;
import cn.com.shopec.core.resource.service.ReturnAreaService;
import cn.com.shopec.core.search.factory.SolrServerFactory;
import cn.com.shopec.core.search.model.ParkSearchCondition;
import cn.com.shopec.core.search.model.ParkSearchResult;
import cn.com.shopec.core.search.model.ParkSearchResultDoc;
import cn.com.shopec.core.search.model.SearchCondition;
import cn.com.shopec.core.search.service.ReturnAreaIndexService;
@Service
public class ReturnAreaIndexServiceImpl implements ReturnAreaIndexService {
	
	private static final Log log = LogFactory.getLog(ReturnAreaIndexServiceImpl.class);
	@Resource(name = "parkSearchSolrServer")
	private SolrServerFactory solrServerFactory;
	@Resource
	private ReturnAreaService returnAreaService;
	
	@Override
	public boolean saveOrUpdateReturnArea(String returnAreaId) {
		ReturnArea returnArea = returnAreaService.getReturnArea(returnAreaId);
		SolrInputDocument solrDoc = null;
		if(returnArea == null || returnArea.getLatitude() == null || returnArea.getPloygonPoints().length() == 0) {
			return false;
		}
		solrDoc =	new SolrInputDocument();
		solrDoc.addField("id", "R"+returnArea.getReturnAreaId()); //还车区域编号做solrDoc的id
		solrDoc.addField("dataType",3); //类型为3，表示还车区域
		solrDoc.addField("isAvailable", returnArea.getIsAvailable()); //是否可用
		Double longitude = ECNumberUtils.string2Double(returnArea.getLongitude()); //经度（字符串转Double） 
		Double latitude = ECNumberUtils.string2Double(returnArea.getLatitude()); //纬度（字符串转Double）
		if(longitude != null && latitude != null) {
			solrDoc.addField("parkLocation", (latitude + "," + longitude)); //多边形几何中心坐标，格式为： 纬度,经度
		}
		if(returnArea.getPloygonPoints() != null) {
			solrDoc.addField("parkGeo","POLYGON(("+returnArea.getPloygonPoints()+"))"); //多边形坐标
		}
		solrDoc.addField("indexedTime", new Date());
		SolrServer server = solrServerFactory.getServer();
		try {
			server.deleteById("R"+returnArea.getReturnAreaId()); //Lucene不支持直接的update，只能先delete，再add
			server.add(solrDoc);
			server.commit();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	@Override
	public boolean deleteReturnArea(String returnAreaId) {
		SolrServer server = solrServerFactory.getServer();
		try {
			server.deleteById("R"+returnAreaId); 
			server.commit();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}	
	@Override
	public boolean searchReturnAreaByPosition(Double longitude, Double latitude) {
		if(longitude != null && longitude != 0.0d && latitude != null && latitude != 0.0d) { //有效位置
			//GPS坐标系（wgs84标准）转为百度坐标系（bd09）
			ParkSearchCondition condition = new ParkSearchCondition();
			double[] bdCoord = ECGeoCoordinateTransformUtil.wgs84tobd09(longitude, latitude);
			condition.setPosLongitude(bdCoord[0]); //经度（百度坐标）
			condition.setPosLatitude(bdCoord[1]); //纬度（百度坐标）
			ParkSearchResult result = searchReturnAreaByPosition(condition);
			if(result.getTotal() > 0) {
				return true;
			}
		}
		return false;
	}
	private ParkSearchResult searchReturnAreaByPosition(ParkSearchCondition condition) {
		ParkSearchResult result = null;
		
		SolrQuery solrQuery = new SolrQuery();
		this.configSolrQuery4SearchParkByPosition(solrQuery, condition, true); //依据传入的OutletQuery，配置SolrQuery
		
		QueryResponse response = null;
		
		try {
			response = this.solrServerFactory.getServer().query(solrQuery); //使用solr进行搜索
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		if(response != null) { //有搜索结果
			result = new ParkSearchResult();
			this.fetchSearchResult4SearchParkByPosition(result, response); //取出搜索结果
		}
		return result;
	}
	/**
	 * 针对根据位置，搜索一定距离内场站的搜索功能，配置Solr查询对象
	 * @param solrQuery
	 * @param condition
	 * @param fetchDocs
	 */
	private void configSolrQuery4SearchParkByPosition(SolrQuery solrQuery, ParkSearchCondition condition, boolean fetchDocs) {
		solrQuery.setQuery("*:*"); //查询全部，无查询词（即不根据关键词搜索）
		if(condition == null) {
			return;
		}
		if(condition.getPosLongitude() != null && condition.getPosLatitude() != null) {
			solrQuery.addFilterQuery("dataType:3"); //配置过滤条件（dataType为还车区域类型）
			
			String point = condition.getPosLongitude()+" "+condition.getPosLatitude();
			solrQuery.setQuery("*:*");
			solrQuery.addFilterQuery("parkGeo:\"Intersects("+point+")\"");
			solrQuery.addField("*");
			solrQuery.addField("_dist_:geodist(parkLocation," + condition.getPosLatitude() + "," + condition.getPosLongitude() + ")");
			
			//配置排序
			ORDER order = SolrQuery.ORDER.asc;
			solrQuery.addSort("geodist(parkLocation," + condition.getPosLatitude() + "," +  condition.getPosLongitude() + ")", order); //根据距离排序
		}
		if(!fetchDocs) { //如果不需要返回solr中的文档数据
			solrQuery.setRows(0);
		}
	}
	/**
	 * 取出搜索结果
	 * @param result
	 * @param response
	 */
	private void fetchSearchResult4SearchParkByPosition(ParkSearchResult result, QueryResponse response) {
		if(response != null) {
			SolrDocumentList solrDocList = response.getResults();
			
			List<ParkSearchResultDoc> resDocs = new ArrayList<ParkSearchResultDoc>(solrDocList.size());
			
			for(Iterator<SolrDocument> it = solrDocList.iterator(); it.hasNext() ; ) {
				SolrDocument solrDoc = it.next();
				ParkSearchResultDoc resDoc = this.convert2SearchResultDoc(solrDoc);
				resDocs.add(resDoc);
			}
			result.setParks(resDocs); //搜索结果文档
			result.setTotal(solrDocList.getNumFound()); //命中数
			result.setCostTime(response.getQTime()); //搜索花费时间
		}		
	}
	/**
	 * 将SolrDocument对象转为ParkSearchResultDoc对象
	 * @param solrDoc
	 * @return
	 */
	private ParkSearchResultDoc convert2SearchResultDoc(SolrDocument solrDoc) {
		if(solrDoc == null) {
			return null;
		}
		ParkSearchResultDoc doc = new ParkSearchResultDoc();
		
		doc.setDocId((String) solrDoc.getFieldValue("id")); //文档id
		if(solrDoc.containsKey("_dist_")){//_dist_是一个pseudo-field，是计算得到的当前点到场站的距离
			doc.setParkDistance((Double)solrDoc.getFieldValue("_dist_")); 
        }
		return doc;
	}

}
