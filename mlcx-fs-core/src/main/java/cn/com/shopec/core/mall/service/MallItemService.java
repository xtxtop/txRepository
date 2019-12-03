package cn.com.shopec.core.mall.service;

import java.util.List;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.mall.model.MallItem;

public interface MallItemService extends BaseService {

	/**
	 * 根据查询条件，查询并返回MallItem的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<MallItem> getMallItemList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回MallItem的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<MallItem> getMallItemPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个MallItem对象
	 * @param id 主键id
	 * @return
	 */
	public MallItem getMallItem(String id);

	/**
	 * 根据主键数组，查询并返回一组MallItem对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<MallItem> getMallItemByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的MallItem记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MallItem> delMallItem(String id);
	
	/**
	 * 新增一条MallItem记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param item 新增的MallItem数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MallItem> addMallItem(MallItem item);
	
	/**
	 * 根据主键，更新一条MallItem记录
	 * @param item 更新的MallItem数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<MallItem> updateMallItem(MallItem item);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
	 /**
	 * 为MallItem对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(MallItem obj);
}
