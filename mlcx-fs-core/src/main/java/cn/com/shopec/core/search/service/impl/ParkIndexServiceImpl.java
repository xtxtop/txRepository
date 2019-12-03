package cn.com.shopec.core.search.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.core.resource.common.ParkConstant;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.search.factory.SolrServerFactory;
import cn.com.shopec.core.search.service.ParkIndexService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 场站索引实现类
 *
 */
@Service
public class ParkIndexServiceImpl implements ParkIndexService {

	private static final Log log = LogFactory.getLog(ParkIndexServiceImpl.class);
	
	@Resource(name="parkSearchSolrServer")
	private SolrServerFactory solrServerFactory;
	@Resource
	private SysParamService sysParamService;
	/**
	 * 保存场站到搜索引擎索引中
	 * @param park
	 * @return
	 */
	public boolean savePark(Park park) {
		boolean res = false;
		SolrInputDocument solrDoc = convert2SolrInputDoc(park); 
		if(solrDoc == null) {
			log.error("Invalid argument, park = " + park);
			return res;
		}
		
		SolrServer server = solrServerFactory.getServer();
		try {
			server.deleteById(park.getParkNo()); //Lucene不支持直接的update，只能先delete，再add
			server.add(solrDoc);
			server.commit();
			res = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return res;
	}
	
	/**
	 * 根据编号，从搜索引擎中删除一个场站
	 * @param parkNo
	 * @return
	 */
	public boolean deletePark(String parkNo) {
		boolean res = false; 
		if(parkNo == null || parkNo.length() == 0) {
			log.error("Invalid argument, parkNo = " + parkNo);
			return res;
		}
		SolrServer server = solrServerFactory.getServer();
		try {
			server.deleteById(parkNo);
			server.commit();
			res = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return res;
	}
	
	/**
	 * Park对象，转为SolrInputDocument对象
	 * @param park
	 * @return
	 */
	private SolrInputDocument convert2SolrInputDoc(Park park) {
		SolrInputDocument solrDoc = null;
		if(park == null || park.getParkNo() == null || park.getParkNo().length() == 0) {
			return solrDoc;
		}
		solrDoc =	new SolrInputDocument();
		//系统参数 场站类型0是 圆形场站，1 多边形场站
		SysParam sysParam = sysParamService.getByParamKey(ParkConstant.PARK_TYPE);
		if(sysParam!=null&&sysParam.getParamValue()!=null){
			if(sysParam.getParamValue().equals("0")){
				solrDoc.addField("id", park.getParkNo()); //场站编号做solrDoc的id
				solrDoc.addField("parkNo", park.getParkNo()); //场站编号
				solrDoc.addField("parkName", park.getParkName()); //场站名称
//		        solrDoc.addField("parkAddress", null); //场站地址
				Double longitude = ECNumberUtils.string2Double(park.getLongitude()); //经度（字符串转Double） 
				Double latitude = ECNumberUtils.string2Double(park.getLatitude()); //纬度（字符串转Double）
				solrDoc.addField("parkLongitude", longitude == null ? null : String.valueOf(longitude)); //经度
				solrDoc.addField("parkLatitude", latitude == null ? null : String.valueOf(latitude)); //纬度
				if(longitude != null && latitude != null) {
					solrDoc.addField("parkLocation", (latitude + "," + longitude)); //坐标，格式为： 纬度,经度
				}
				if(park.getElectronicFenceRadius() != null) {
					solrDoc.addField("elecFenceRadius", park.getElectronicFenceRadius()); //电子围栏半径
				}
				solrDoc.addField("indexedTime", new Date());
			}else{
				solrDoc.addField("id", park.getParkNo()); //场站编号做solrDoc的id
				solrDoc.addField("parkNo", park.getParkNo()); //场站编号
				solrDoc.addField("parkName", park.getParkName()); //场站名称
				Double longitude = ECNumberUtils.string2Double(park.getLongitude()); //经度（字符串转Double） 
				Double latitude = ECNumberUtils.string2Double(park.getLatitude()); //纬度（字符串转Double）
				solrDoc.addField("parkLongitude", longitude == null ? null : String.valueOf(longitude)); //经度
				solrDoc.addField("parkLatitude", latitude == null ? null : String.valueOf(latitude)); //纬度
				if(longitude != null && latitude != null) {
					solrDoc.addField("parkLocation", (latitude + "," + longitude)); //多边形几何中心坐标，格式为： 纬度,经度
				}
				if(park.getPloygonPoints() != null) {
					solrDoc.addField("parkGeo","POLYGON(("+park.getPloygonPoints()+"))"); //多边形坐标
				}
				solrDoc.addField("indexedTime", new Date());
			}
		}
		
		return solrDoc;
	}
}
