package cn.com.shopec.core.search.service;

import cn.com.shopec.core.resource.model.Park;

/**
 * 场站索引接口
 *
 */
public interface ParkIndexService {

	/**
	 * 保存场站到搜索引擎索引中
	 * @param park
	 * @return
	 */
	public boolean savePark(Park park);
	
	/**
	 * 根据编号，从搜索引擎中删除一个场站
	 * @param parkNo
	 * @return
	 */
	public boolean deletePark(String parkNo);
	
}
