package cn.com.shopec.core.dailyrental.search.service;

import cn.com.shopec.core.dailyrental.search.model.SearchCondition;
import cn.com.shopec.core.dailyrental.search.model.SearchResult;
import cn.com.shopec.core.dailyrental.search.model.SolrDoc;

/**
 * 搜索接口
 *
 */
public interface DaySearchService {

	
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
	 * 根据位置，搜索一定距离内门店
	 * @param condition
	 * @return
	 */
	public SearchResult searchParkByPosition(SearchCondition condition);


	/**
	 * 设置查询周围的有效的车辆和场站的默认值
	 * @param condition
	 * @return
	 */
	public SearchCondition setDefaultParmForQueryAround(SearchCondition condition);
	/**
	 * 设置查询门店的默认值
	 * @param condition
	 * @return
	 */
	public SearchCondition setParkDayParmForQueryAround(SearchCondition condition);
	/**
	 * app端搜索门店
	 * @param searchCondition
	 * @return
	 */
	public SearchResult searchParkDayAround(SearchCondition searchCondition);
	
	
}
