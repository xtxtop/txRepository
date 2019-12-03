package cn.com.shopec.core.system.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.system.model.DataDictItem;

/**
 * 数据字典的明细项表 数据库处理类
 */
public interface DataDictItemDao extends BaseDao<DataDictItem, String> {
	/**
	 * 获取数据字典分类代码
	 * @return
	 */
	public List<DataDictItem> getDataDictCatCode();
	public List<DataDictItem> getOrderStarLevelByStarLevelCode(String catCode);
	public List<DataDictItem> getModleByBrand(String parentCatCode);
	public DataDictItem getItemValue(String cityName,String dataDictCatCode);
}
