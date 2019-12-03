package cn.com.shopec.core.statement.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.statement.model.MemberCount;
import cn.com.shopec.core.statement.model.MoreBusinessSummary;
import cn.com.shopec.core.statement.model.PackageCount;

public interface StatementDao {

	List<MemberCount> pageListmemberCount(Query q);

	long pageListmemberCountCount(Query q);

	List<PackageCount> pageListpackageCount(Query q);

	long pageListpackageCountCount(Query q);

	List<Map<String, Object>> refund(Query q);

	List<Map<String, Object>> income(Query q);
	
	/**
	 * 套餐汇总 修改
	 */
	List<PackageCount> pageListPackageCount(Query q);

	long countPackageCount(Query q);
	/**
	 * 资金汇总 默认查询最近一月的数据
	 */
	List<MoreBusinessSummary> listMoneySummary(Query q);
	List<MoreBusinessSummary> listMoneySummaryDefault(@Param("mbs")MoreBusinessSummary mbs);

	/**
	 * 资金汇总 默认查询最近12个月的数据 按月份倒排
	 */
	List<MoreBusinessSummary> listMoneySummaryMonth(Query q);
	long countMoneySummaryMonth(Query q);
	List<MoreBusinessSummary> listMoneySummaryMonthExport(@Param("mbs")MoreBusinessSummary mbs);
	
	/**
	 * 资金汇总 默认查询最近30天的数据 按日期倒排
	 */
	List<MoreBusinessSummary> listMoneySummaryDay(Query q);
	long countMoneySummaryDay(Query q);
	List<MoreBusinessSummary> listMoneySummaryDayExport(@Param("mbs")MoreBusinessSummary mbs);
}