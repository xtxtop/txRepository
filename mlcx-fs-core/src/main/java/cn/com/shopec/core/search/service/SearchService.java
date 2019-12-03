package cn.com.shopec.core.search.service;

import cn.com.shopec.core.search.model.SearchCondition;
import cn.com.shopec.core.search.model.SearchResult;
import cn.com.shopec.core.search.model.SolrDoc;

/**
 * 搜索接口
 *
 */
public interface SearchService {

	/**
	 * 搜索唯一的车辆数据
	 * @param condition
	 * @return
	 */
	public SolrDoc searchUniqueCar(String carNo);
	
	/**
	 * 搜索唯一的场站数据
	 * @param condition
	 * @return
	 */
	public SolrDoc searchUniquePark(String parkNo);
	
	/**
	 * 搜索唯一的数据
	 * @param condition
	 * @return
	 */
	public SolrDoc searchUniqueData(String docId);
	
	/**
	 * 根据位置，搜索一定距离内场站
	 * @param condition
	 * @return
	 */
	public SearchResult searchParkByPosition(SearchCondition condition);

	/**
	 * 根据位置，搜索一定距离内场站，车辆
	 * @param condition
	 * @return
	 */
	public SearchResult searchAround(SearchCondition condition);

	/**
	 * 设置查询周围的有效的车辆和场站的默认值
	 * @param condition
	 * @return
	 */
	public SearchCondition setDefaultParmForQueryAround(SearchCondition condition);
	
}
