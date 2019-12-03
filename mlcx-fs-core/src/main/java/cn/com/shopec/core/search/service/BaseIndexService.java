package cn.com.shopec.core.search.service;

import org.apache.solr.common.SolrInputDocument;

import cn.com.shopec.core.search.model.SolrDoc;

/**
 * 基础索引接口
 *
 */
public interface BaseIndexService {

	/**
	 * 删除
	 * 
	 * @param docId
	 * @return
	 */
	boolean delete(String docId);

	/**
	 * 增加
	 * 
	 * @param solrDoc
	 * @return
	 */
	boolean add(SolrInputDocument solrDoc);

	
	/**
	 * 增加
	 * 
	 * @param solrDoc
	 * @return
	 */
	boolean update(SolrInputDocument solrDoc);
	
	/**
	 * 更新某字段
	 * 
	 * @param id
	 * @param fieldName
	 * @param value
	 * @return
	 * @throws Exception
	 */
	boolean updateValue(String id, String fieldName, Object value);

}
