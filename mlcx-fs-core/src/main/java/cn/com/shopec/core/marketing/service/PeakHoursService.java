package cn.com.shopec.core.marketing.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.PeakHours;

/**
 * PeakHours 服务接口类
 */
public interface PeakHoursService extends BaseService {

	/**
	 * 根据查询条件，查询并返回PeakHours的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<PeakHours> getPeakHoursList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回PeakHours的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<PeakHours> getPeakHoursPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个PeakHours对象
	 * @param id 主键id
	 * @return
	 */
	public PeakHours getPeakHours(String id);

	/**
	 * 根据主键数组，查询并返回一组PeakHours对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<PeakHours> getPeakHoursByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的PeakHours记录
	 * @param id 主键id
	 * @return
	 */
	public ResultInfo<PeakHours> delPeakHours(String id);
	
	/**
	 * 新增一条PeakHours记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param peakHours 新增的PeakHours数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @return
	 */
	public ResultInfo<PeakHours> addPeakHours(PeakHours peakHours);
	
	/**
	 * 根据主键，更新一条PeakHours记录
	 * @param peakHours 更新的PeakHours数据，且其主键不能为空
	 * @return
	 */
	public ResultInfo<PeakHours> updatePeakHours(PeakHours peakHours);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为PeakHours对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(PeakHours obj);
	/**
	 * 
	 */
	public List<PeakHours> queryPeakHoursList(String id,String ruleNo);
		
}
