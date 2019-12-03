package cn.com.shopec.core.dailyrental.search.service;

/**
 * 场站索引接口
 *
 */
public interface DayBusinessIndexService {

	/**
	 * 更新场站
	 * 
	 * @param park
	 * @return
	 */
	boolean saveOrUpdatePark(String parkNo);


}
