package cn.com.shopec.core.marketing.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.AppLocation;

/**
 * AppLocation 服务接口类
 */
public interface AppLocationService extends BaseService {

	/**
	 * 根据查询条件，查询并返回AppLocation的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<AppLocation> getAppLocationList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回AppLocation的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<AppLocation> getAppLocationPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个AppLocation对象
	 * @param id 主键id
	 * @return
	 */
	public AppLocation getAppLocation(String id);

	/**
	 * 根据主键数组，查询并返回一组AppLocation对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<AppLocation> getAppLocationByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的AppLocation记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<AppLocation> delAppLocation(String id, Operator operator);
	
	/**
	 * 新增一条AppLocation记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param appLocation 新增的AppLocation数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<AppLocation> addAppLocation(AppLocation appLocation, Operator operator);
	
	/**
	 * 根据主键，更新一条AppLocation记录
	 * @param appLocation 更新的AppLocation数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<AppLocation> updateAppLocation(AppLocation appLocation, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为AppLocation对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(AppLocation obj);
		
}
