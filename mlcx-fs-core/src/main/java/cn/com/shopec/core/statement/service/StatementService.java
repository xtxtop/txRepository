package cn.com.shopec.core.statement.service;

import java.util.List;

import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.statement.model.MemberCount;
import cn.com.shopec.core.statement.model.MoreBusinessSummary;
import cn.com.shopec.core.statement.model.PackageCount;
import cn.com.shopec.core.statement.model.CashPledge;

public interface StatementService {

	public PageFinder<MemberCount> pageListmemberCount(Query q);

	public List<MemberCount> tomemberCountExport(Query q);

	public PageFinder<PackageCount> pageListpackageCount(Query q);

	public List<PackageCount> toPackageCountExport(Query q);

	public PageFinder<CashPledge> pageListcashPledge(Query q);

	public List<CashPledge> tocashPledgeExport(Query q);
	//套餐汇总-修改
	public PageFinder<PackageCount> pageListPackageCount(Query q);
	//资金汇总 默认查询最近一月的数据
	public PageFinder<MoreBusinessSummary> pageListMoneySummary(Query q);
	//资金汇总 默认查询最近12个月的数据 按月份倒排
	public PageFinder<MoreBusinessSummary> pageListMoneySummaryMonth(Query q);
	//资金汇总 默认查询最近30天数据；按日期倒排
	public PageFinder<MoreBusinessSummary> pageListMoneySummaryDay(Query q);
	//导出
	public List<MoreBusinessSummary> listMoneySummaryDefault(MoreBusinessSummary mbs);
	public List<MoreBusinessSummary> listMoneySummaryMonthExport(MoreBusinessSummary mbs);
	public List<MoreBusinessSummary> listMoneySummaryDayExport(MoreBusinessSummary mbs);
}
