package cn.com.shopec.core.system.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.DataDictItem;

/**
 * 数据字典的明细项表 服务接口类
 */
public interface DataDictItemService extends BaseService {

	/**
	 * 根据查询条件，查询并返回DataDictItem的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<DataDictItem> getDataDictItemList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回DataDictItem的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<DataDictItem> getDataDictItemPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个DataDictItem对象
	 * @param id 主键id
	 * @return
	 */
	public DataDictItem getDataDictItem(String id);

	/**
	 * 根据主键数组，查询并返回一组DataDictItem对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<DataDictItem> getDataDictItemByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的DataDictItem记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DataDictItem> delDataDictItem(String id, Operator operator);
	
	/**
	 * 新增一条DataDictItem记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param dataDictItem 新增的DataDictItem数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DataDictItem> addDataDictItem(DataDictItem dataDictItem, Operator operator);
	
	/**
	 * 根据主键，更新一条DataDictItem记录
	 * @param dataDictItem 更新的DataDictItem数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<DataDictItem> updateDataDictItem(DataDictItem dataDictItem, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为DataDictItem对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(DataDictItem obj);
	
	/**
	 * 根据数据字典项id，取得数据字典项的值
	 * @param id
	 * @return
	 */
	public String getValueOfDataDictItem(String id);
	
	/**
	 * 根据字典分类编码，取得该分类下所有可用的数据字典项，以list形式返回
	 * @param catCode
	 * @return
	 */
	public List<DataDictItem> getDataDictItemListByCatCode(String catCode);
	
	/**
	 * 根据字典项的父级id，取得该父级字典项的所有子项，以list形式返回
	 * @param parentId
	 * @return
	 */
	public List<DataDictItem> getDataDictItemListByParentItemId(String parentId);
	/**
	 * 获取数据字典分类代码
	 * @return
	 */
	public List<DataDictItem> getDataDictCatCode();

	public List<DataDictItem> getModleByBrand(String parentCatCode);

	public PageFinder<DataDictItem> getDataDictItemPagedListWorker(Query q);

	public DataDictItem getItemValue(String cityName,String dataDictCatCode);
}
