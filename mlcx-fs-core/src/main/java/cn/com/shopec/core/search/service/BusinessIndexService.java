package cn.com.shopec.core.search.service;

/**
 * 场站索引接口
 *
 */
public interface BusinessIndexService {

	/**
	 * 更新车辆
	 * 
	 * @param car
	 * @return
	 */
	boolean saveOrUpdateCar(String carNo);
	/**
	 * 更新场站
	 * 
	 * @param park
	 * @return
	 */
	boolean saveOrUpdatePark(String parkNo);


}
