package cn.com.shopec.core.dailyrental.search.service;

import cn.com.shopec.core.dailyrental.search.model.ParkSearchCondition;
import cn.com.shopec.core.dailyrental.search.model.ParkSearchResult;

/**
 * 场站搜索接口
 *
 */
public interface DayParkSearchService {

	/**
	 * 根据位置，搜索一定距离内场站
	 * @param condition
	 * @return
	 */
	public ParkSearchResult searchParkByPosition(ParkSearchCondition condition);
	
}
