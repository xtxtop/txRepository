package cn.com.shopec.core.ml.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.ml.model.CReporting;
import cn.com.shopec.core.ml.vo.ReportingVo;

/**
 * CReporting 服务接口类
 */
public interface CReportingService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CReporting的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CReporting> getCReportingList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回CReporting的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CReporting> getCReportingPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个CReporting对象
	 * @param id 主键id
	 * @return
	 */
	public CReporting getCReporting(String id);

	/**
	 * 根据主键数组，查询并返回一组CReporting对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CReporting> getCReportingByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的CReporting记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CReporting> delCReporting(String id, Operator operator);
	
	/**
	 * 新增一条CReporting记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param cReporting 新增的CReporting数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CReporting> addCReporting(CReporting cReporting, Operator operator);
	
	/**
	 * 根据主键，更新一条CReporting记录
	 * @param cReporting 更新的CReporting数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CReporting> updateCReporting(CReporting cReporting, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为CReporting对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CReporting obj);
	//违停 列表
	public PageFinder<ReportingVo> getReporting(Query q);
	//详情
	public ReportingVo getReportingNo(String id);
		
}
