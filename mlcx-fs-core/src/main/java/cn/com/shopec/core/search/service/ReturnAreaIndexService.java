package cn.com.shopec.core.search.service;

/**
 * 还车区域索引接口
 *
 */
public interface ReturnAreaIndexService {

	/**
	 * 启用停用还车区域
	 * @param returnArea
	 * @return
	 */
	public boolean saveOrUpdateReturnArea(String returnAreaId);
	/**
	 * 删除还车区域
	 * @param returnAreaId
	 */
	public boolean deleteReturnArea(String returnAreaId);
	/**
	 * 根据车辆位置，搜索车辆是否在当前城市的区域内
	 * @param condition
	 * @return
	 */
	public boolean searchReturnAreaByPosition(Double longitude, Double latitude);
}
