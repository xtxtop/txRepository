package cn.com.shopec.core.dailyrental.search.service.impl;

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

import cn.com.shopec.core.dailyrental.search.factory.SolrServerFactory;
import cn.com.shopec.core.dailyrental.search.model.ParkSearchCondition;
import cn.com.shopec.core.dailyrental.search.model.ParkSearchResult;
import cn.com.shopec.core.dailyrental.search.model.ParkSearchResultDoc;
import cn.com.shopec.core.dailyrental.search.service.DayParkSearchService;
import cn.com.shopec.core.resource.common.ParkConstant;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 场站搜索实现类
 *
 */
@Service
public class DayParkSearchServiceImpl implements DayParkSearchService {
	
	private static final Log log = LogFactory.getLog(DayParkSearchService.class);
	
	@Resource(name="parkDaySearchSolrServer")
	private SolrServerFactory solrServerFactory;
	@Resource
	private SysParamService sysParamService;

	/**
	 * 根据位置，搜索一定距离内场站
	 * @param condition
	 * @return
	 */
	public ParkSearchResult searchParkByPosition(ParkSearchCondition condition) {
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
		
		if(condition.getParkNo() != null && condition.getParkNo().length() != 0) {
			solrQuery.addFilterQuery("parkNo:" + condition.getParkNo()); //配置过滤条件（场站编号）
		}
		//系统参数 场站类型0是 圆形场站，1 多边形场站
		SysParam sysParam = sysParamService.getByParamKey(ParkConstant.PARK_TYPE);
		if(sysParam!=null&&sysParam.getParamValue()!=null){
			if(sysParam.getParamValue().equals("0")){
				if(condition.getPosLongitude() != null && condition.getPosLatitude() != null && condition.getRadius() != null && condition.getRadius() > 0.0d) {
					//配置过滤条件（离传入的当做位置一定距离内的场站）
					solrQuery.addFilterQuery("{!geofilt pt="+ condition.getPosLatitude() + "," + condition.getPosLongitude() +" sfield=parkLocation d="+ condition.getRadius() +"}");
					//设置返回的field，除了定义在schema.xml中的field之外，还返回一个pseudo-field: _dist_，其值是当前位置到场站的距离（直线距离，公里数）
					solrQuery.addField("*");
					solrQuery.addField("_dist_:geodist(parkLocation," + condition.getPosLatitude() + "," + condition.getPosLongitude() + ")");
					
					//配置排序
					ORDER order = condition.isDescOrder() ? SolrQuery.ORDER.desc : SolrQuery.ORDER.asc;
					solrQuery.addSort("geodist(parkLocation," + condition.getPosLatitude() + "," +  condition.getPosLongitude() + ")", order); //根据距离排序
				}
				
				if(!fetchDocs) { //如果不需要返回solr中的文档数据
					solrQuery.setRows(0);
				}
			}else{
				if(condition.getPosLongitude() != null && condition.getPosLatitude() != null) {
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
		doc.setParkNo((String) solrDoc.getFieldValue("parkNo")); //场站编号
		doc.setParkName((String) solrDoc.getFieldValue("parkName")); //场站名称
		doc.setParkAddress((String) solrDoc.getFieldValue("parkAddress")); //场站地址
		doc.setParkLongitude((String) solrDoc.getFieldValue("parkLongitude")); //场站经度
		doc.setParkLatitude((String) solrDoc.getFieldValue("parkLatitude")); //场站纬度
		doc.setElecFenceRadius((Integer) solrDoc.getFieldValue("elecFenceRadius")); //电子围栏半径
		if(solrDoc.containsKey("_dist_")){//_dist_是一个pseudo-field，是计算得到的当前点到场站的距离
			doc.setParkDistance((Double)solrDoc.getFieldValue("_dist_")); 
        }
		
		return doc;
	}	
	
}
