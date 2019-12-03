package cn.com.shopec.core.marketing.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.GoldBeansSetting;

/**
 * GoldBeansSetting 服务接口类
 */
public interface GoldBeansSettingService extends BaseService {

	/**
	 * 根据查询条件，查询并返回GoldBeansSetting的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<GoldBeansSetting> getGoldBeansSettingList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回GoldBeansSetting的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<GoldBeansSetting> getGoldBeansSettingPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个GoldBeansSetting对象
	 * @param id 主键id
	 * @return
	 */
	public GoldBeansSetting getGoldBeansSetting(String id);

	/**
	 * 根据主键数组，查询并返回一组GoldBeansSetting对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<GoldBeansSetting> getGoldBeansSettingByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的GoldBeansSetting记录
	 * @param id 主键id
	 * @return
	 */
	public ResultInfo<GoldBeansSetting> delGoldBeansSetting(String id);
	
	/**
	 * 新增一条GoldBeansSetting记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param goldBeansSetting 新增的GoldBeansSetting数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @return
	 */
	public ResultInfo<GoldBeansSetting> addGoldBeansSetting(GoldBeansSetting goldBeansSetting);
	
	/**
	 * 根据主键，更新一条GoldBeansSetting记录
	 * @param goldBeansSetting 更新的GoldBeansSetting数据，且其主键不能为空
	 * @return
	 */
	public ResultInfo<GoldBeansSetting> updateGoldBeansSetting(GoldBeansSetting goldBeansSetting);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为GoldBeansSetting对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(GoldBeansSetting obj);
		
}
