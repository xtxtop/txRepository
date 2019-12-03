package cn.com.shopec.core.search.service;

import cn.com.shopec.core.search.model.ParkSearchCondition;
import cn.com.shopec.core.search.model.ParkSearchResult;

/**
 * 场站搜索接口
 *
 */
public interface ParkSearchService {

	/**
	 * 根据位置，搜索一定距离内场站
	 * @param condition
	 * @return
	 */
	public ParkSearchResult searchParkByPosition(ParkSearchCondition condition);
	
}
