package cn.com.shopec.core.franchisee.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.franchisee.model.FranchiseeProfit;
import cn.com.shopec.core.franchisee.model.FranchiseeProfitVo;
import cn.com.shopec.core.franchisee.model.FranchiseeVo;

/**
 * 加盟商收益表 数据库处理类
 */
public interface FranchiseeProfitDao extends BaseDao<FranchiseeProfit, String> {
	/**
	 * 加盟商结算列表
	 */
	public List<FranchiseeVo> pageListSettlement(Query q);

	public Long countSettlement(Query q);
	
	public List<FranchiseeVo> listFranchiseeProfit(Query q);
	
	public List<FranchiseeProfit> listFranchiseeProfitIseeNo(Query q);
	//按月度统计
	public List<FranchiseeProfitVo> pageListFranchiseeProfitByMonths(Query q);
	public Long countFranchiseeProfitByMonths(Query q);
	public FranchiseeProfitVo getFranchiseeProfitVoByMonths(String months,String franchiseeNo);

	//按季度统计
	public List<FranchiseeProfitVo> pageListFranchiseeProfitByQuarter(Query q);
	public Long countFranchiseeProfitByQuarter(Query q);
	public FranchiseeProfitVo getFranchiseeProfitVoByQuarter(String quarter,String year, String franchiseeNo);
	
	//按车辆统计
	public List<FranchiseeProfitVo> pageListFranchiseeCarStatistics(Query q);
	public Long countFranchiseeCarStatistics(Query q);
	//按场站统计
	public List<FranchiseeProfitVo> pageListFranchiseeParkStatistics(Query q);
	public Long countFranchiseeParkStatistics(Query q);
	
}
