package cn.com.shopec.core.mall.service;

import java.util.List;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.mall.model.MallItemSort;

public interface MallItemSortService extends BaseService {

	/**
	 * 根据查询条件，查询并返回MallItemSort的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<MallItemSort> getMallItemSortList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回MallItemSort的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<MallItemSort> getMallItemSortPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个MallItemSort对象
	 * @param id 主键id
	 * @return
	 */
	public MallItemSort getMallItemSort(String id);

	/**
	 * 根据主键数组，查询并返回一组MallItemSort对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<MallItemSort> getMallItemSortByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的MallItemSort记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MallItemSort> delMallItemSort(String id);
	
	/**
	 * 新增一条MallItemSort记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param itemSort 新增的MallItemSort数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MallItemSort> addMallItemSort(MallItemSort itemSort);
	
	/**
	 * 根据主键，更新一条MallItemSort记录
	 * @param itemSort 更新的MallItemSort数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MallItemSort> updateMallItemSort(MallItemSort itemSort);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为MallItemSort对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(MallItemSort obj);
}
