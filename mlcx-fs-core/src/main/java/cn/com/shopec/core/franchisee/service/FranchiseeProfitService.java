package cn.com.shopec.core.franchisee.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.franchisee.model.FranchiseeProfit;
import cn.com.shopec.core.franchisee.model.FranchiseeProfitVo;
import cn.com.shopec.core.franchisee.model.FranchiseeVo;

/**
 * 加盟商收益表 服务接口类
 */
public interface FranchiseeProfitService extends BaseService {

	/**
	 * 根据查询条件，查询并返回FranchiseeProfit的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<FranchiseeProfit> getFranchiseeProfitList(Query q);
	/**
	 * 单独统计加盟商收益数据
	 */
	public List<FranchiseeVo> listFranchiseeProfit(Query q);
	/**
	 * 根据加盟商编号查询加盟商收益明细
	 *
	 */
	public List<FranchiseeProfit> listFranchiseeProfitIseeNo(Query q);

	/**
	 * 根据查询条件，分页查询并返回FranchiseeProfit的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<FranchiseeProfit> getFranchiseeProfitPagedList(Query q);
	
	/**
	 * 根据查询条件，按月分页查询加盟商收益数据
	 */
	public PageFinder<FranchiseeProfitVo> getFranchiseeProfitPagedListByMonths(Query q);
	
	/**
	 * 根据月份，查询按月统计加盟商详情
	 */
	public FranchiseeProfitVo getFranchiseeProfitVoByMonths(String months,String franchiseeNo);
	
	/**
	 * 根据季度，查询按季度统计加盟商收益详情
	 * @param franchiseeNo 
	 */
	public FranchiseeProfitVo getFranchiseeProfitVoByQuarter(String quarter,String year, String franchiseeNo);
	
	/**
	 * 根据查询条件，按季度分页查询加盟商收益数据
	 */
	public PageFinder<FranchiseeProfitVo> getFranchiseeProfitPagedListByQuarter(Query q);
	
	/**
	 * 根据查询条件，按加盟商关联车辆查询加盟商车辆订单运营统计
	 */
	public PageFinder<FranchiseeProfitVo> getFranchiseeCarStatisticsPagedList(Query q);
	
	/**
	 * 根据查询提交，按加盟商关联场站查询加盟商场站订单运营统计
	 */
	public PageFinder<FranchiseeProfitVo> getFranchiseeParkStatisticsPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个FranchiseeProfit对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public FranchiseeProfit getFranchiseeProfit(String id);

	/**
	 * 根据主键数组，查询并返回一组FranchiseeProfit对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<FranchiseeProfit> getFranchiseeProfitByIds(String[] ids);

	/**
	 * 根据主键，删除特定的FranchiseeProfit记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<FranchiseeProfit> delFranchiseeProfit(String id, Operator operator);

	/**
	 * 新增一条FranchiseeProfit记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param franchiseeProfit
	 *            新增的FranchiseeProfit数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<FranchiseeProfit> addFranchiseeProfit(FranchiseeProfit franchiseeProfit, Operator operator);

	/**
	 * 根据主键，更新一条FranchiseeProfit记录
	 * 
	 * @param franchiseeProfit
	 *            更新的FranchiseeProfit数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<FranchiseeProfit> updateFranchiseeProfit(FranchiseeProfit franchiseeProfit, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为FranchiseeProfit对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(FranchiseeProfit obj);

	/**
	 * 加盟商结算列表
	 */
	public PageFinder<FranchiseeVo> pageListSettlement(Query q);

}
