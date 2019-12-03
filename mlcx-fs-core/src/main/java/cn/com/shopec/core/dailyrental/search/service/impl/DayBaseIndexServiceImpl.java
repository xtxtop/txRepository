package cn.com.shopec.core.dailyrental.search.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import cn.com.shopec.core.dailyrental.search.common.SolrDocConstants;
import cn.com.shopec.core.dailyrental.search.factory.SolrServerFactory;
import cn.com.shopec.core.dailyrental.search.service.DayBaseIndexService;
import cn.com.shopec.core.dailyrental.search.service.DaySearchService;
import cn.com.shopec.core.map.service.BaiduGeocoderApiService;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 索引实现类
 *
 */
@Service
public class DayBaseIndexServiceImpl implements DayBaseIndexService {

	private static final Log log = LogFactory.getLog(DayBaseIndexServiceImpl.class);

	@Resource(name = "parkDaySearchSolrServer")
	private SolrServerFactory solrServerFactory;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private DaySearchService searchService;
	@Resource
	private BaiduGeocoderApiService baiduGeocoderApiService;

	@Override
	public boolean add(SolrInputDocument solrDoc) {
		SolrServer server = solrServerFactory.getServer();
		try {
			server.add(solrDoc);
			server.commit();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * 根据id从搜索引擎中删除一条数据
	 * 
	 * @param
	 * @return
	 */
	@Override
	public boolean delete(String docId) {
		boolean res = false;
		try {
			SolrServer server = solrServerFactory.getServer();
			server.deleteById(docId);
			server.commit();
			res = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return res;
	}

	/**
	 * 更新某字段
	 * 
	 * @param id
	 * @param fieldName
	 * @param value
	 * @throws Exception
	 */
	@Override
	public boolean updateValue(String id, String fieldName, Object value) {
		SolrServer server = solrServerFactory.getServer();
		try {
			Map<String, Object> valueMap = new HashMap<String, Object>();
			valueMap.put("set", value);

			SolrInputDocument solrDoc = new SolrInputDocument();
			solrDoc.addField(SolrDocConstants.DOC_ID_FIELD, id);
			solrDoc.addField(fieldName, valueMap);
			server.add(solrDoc);
			server.commit();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public boolean update(SolrInputDocument solrDoc) {
		SolrServer server = solrServerFactory.getServer();
		try {
			server.add(solrDoc);
			server.commit();
			return true;
		}catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

}
