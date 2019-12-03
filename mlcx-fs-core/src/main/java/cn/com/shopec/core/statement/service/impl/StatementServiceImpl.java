package cn.com.shopec.core.statement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.vo.OrderStatisticalVo;
import cn.com.shopec.core.statement.dao.StatementDao;
import cn.com.shopec.core.statement.model.CashPledge;
import cn.com.shopec.core.statement.model.MemberCount;
import cn.com.shopec.core.statement.model.MoreBusinessSummary;
import cn.com.shopec.core.statement.model.PackageCount;
import cn.com.shopec.core.statement.service.StatementService;

@Service
public class StatementServiceImpl implements StatementService {
	private static final Log log = LogFactory.getLog(StatementServiceImpl.class);
	@Resource
	private StatementDao statementDao; 
	
	@Override
	public PageFinder<MemberCount> pageListmemberCount(Query q) {
		PageFinder<MemberCount> page = new PageFinder<MemberCount>();
		List<MemberCount> list = null;
		long rowCount = 0L;
		try {
			// 调用dao查询满足条件的分页数据
			list = statementDao.pageListmemberCount(q);
			// 调用dao统计满足条件的记录总数
			rowCount = statementDao.pageListmemberCountCount(q);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	@Override
	public List<MemberCount> tomemberCountExport(Query q) {
		return statementDao.pageListmemberCount(q);
	}

	@Override
	public PageFinder<PackageCount> pageListpackageCount(Query q) {
		PageFinder<PackageCount> page = new PageFinder<PackageCount>();
		List<PackageCount> list = null;
		long rowCount = 0L;
		try {
			// 调用dao查询满足条件的分页数据
			list = statementDao.pageListpackageCount(q);
			// 调用dao统计满足条件的记录总数
			rowCount = statementDao.pageListpackageCountCount(q);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	@Override
	public List<PackageCount> toPackageCountExport(Query q) {
		return statementDao.pageListPackageCount(q);
//		return statementDao.pageListpackageCount(q);
	}

	@Override
	public PageFinder<CashPledge> pageListcashPledge(Query q) {
		PageFinder<CashPledge> page = new PageFinder<CashPledge>();
		List<CashPledge> resultList = returnList(q);
		
		//分页的数据只有一条
		int rowCount = 4;
	
		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(resultList);
		page.setRowCount(rowCount);
		return page;
	}

	private List<CashPledge> returnList(Query q) {
		//根据条件去查两条sql
		List<Map<String, Object>> rList = statementDao.refund(q);
		
		List<Map<String, Object>> iList = statementDao.income(q);
		
		List<CashPledge> resultList = new ArrayList<CashPledge>();
		CashPledge zfb = new CashPledge();
		CashPledge wx = new CashPledge();
		CashPledge other = new CashPledge();
		CashPledge all = new CashPledge();
		//sql拿到的结果，放到对象里面
		zfb.setType("支付宝");
		wx.setType("微信");
		other.setType("其他");
		all.setType("合计");
		for (Map<String, Object> r : rList){
			zfb.setCashRefundCount(r.get("zfb_cnt")+"");
			zfb.setRefund(r.get("zfb_amount")+"");
			zfb.setRefundAgentFee(r.get("zfb_agent_fee")+"");
			
			wx.setCashRefundCount(r.get("wx_cnt")+"");
			wx.setRefund(r.get("wx_amount")+"");
			wx.setRefundAgentFee(r.get("wx_agent_fee")+"");
			
			other.setCashRefundCount(r.get("other_cnt")+"");
			other.setRefund(r.get("other_amount")+"");
			other.setRefundAgentFee(r.get("other_agent_fee")+"");
			
			all.setCashRefundCount(r.get("cnt")+"");
			all.setRefund(r.get("amount")+"");
			Double zfbRefund = 0d;
			if(r.get("zfb_agent_fee")!=null){
				zfbRefund = Double.parseDouble(r.get("zfb_agent_fee")+"");
			}
			Double wxRefund = 0d;
			if(r.get("wx_agent_fee")!=null){
				wxRefund = Double.parseDouble(r.get("wx_agent_fee")+"");
			}
			Double otherRefund = 0d;
			if(r.get("other_agent_fee")!=null){
				Double.parseDouble(r.get("other_agent_fee")+"");
			}
			
			all.setRefundAgentFee(zfbRefund+wxRefund+otherRefund+"");
		}
		for	(Map<String, Object> r : iList){
			zfb.setCashPerCount(r.get("zfb_cnt")+"");
			zfb.setIncome(r.get("zfb_amount")+"");
			zfb.setCashAgentFee(r.get("zfb_agent_fee")+"");
			
			wx.setCashPerCount(r.get("wx_cnt")+"");
			wx.setIncome(r.get("wx_amount")+"");
			wx.setCashAgentFee(r.get("wx_agent_fee")+"");
			
			other.setCashPerCount(r.get("other_cnt")+"");
			other.setIncome(r.get("other_amount")+"");
			other.setCashAgentFee("0");
			
			all.setCashPerCount(r.get("cnt")+"");
			all.setIncome(r.get("paidAmount")+"");
			Double zfbRefund = 0d;
			if(r.get("zfb_agent_fee")!=null){
				zfbRefund = Double.parseDouble(r.get("zfb_agent_fee")+"");
			}
			Double wxRefund = 0d;
			if(r.get("wx_agent_fee")!=null){
				wxRefund = Double.parseDouble(r.get("wx_agent_fee")+"");
			}
			all.setCashAgentFee(zfbRefund+wxRefund+"");
		}
		resultList.add(zfb);
		resultList.add(wx);
		resultList.add(other);
		resultList.add(all);
		return resultList;
	}

	@Override
	public List<CashPledge> tocashPledgeExport(Query q) {
		return returnList(q);
	}

	@Override
	public PageFinder<PackageCount> pageListPackageCount(Query q) {
		PageFinder<PackageCount> page = new PageFinder<PackageCount>();
		List<PackageCount> list = null;
		long rowCount = 0L;
		try {
			// 调用dao查询满足条件的分页数据
			list = statementDao.pageListPackageCount(q);
			// 调用dao统计满足条件的记录总数
			rowCount = statementDao.countPackageCount(q);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	@Override
	public PageFinder<MoreBusinessSummary> pageListMoneySummary(Query q) {
			PageFinder<MoreBusinessSummary> page = new PageFinder<MoreBusinessSummary>();
			List<MoreBusinessSummary> list = null;
			long rowCount = 0L;
			try {
				// 调用dao查询满足条件的分页数据
				list = statementDao.listMoneySummary(q);
				// 调用dao统计满足条件的记录总数
				rowCount = list.size();
				
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
			// 将分页数据和记录总数设置到分页结果对象中
			page.setData(list);
			page.setRowCount(rowCount);
			return page;
	}
	
	@Override
	public PageFinder<MoreBusinessSummary> pageListMoneySummaryMonth(Query q) {
			PageFinder<MoreBusinessSummary> page = new PageFinder<MoreBusinessSummary>();
			List<MoreBusinessSummary> list = null;
			long rowCount = 0L;
			try {
				// 调用dao查询满足条件的分页数据
				list = statementDao.listMoneySummaryMonth(q);
				// 调用dao统计满足条件的记录总数
				rowCount = statementDao.countMoneySummaryMonth(q);
				
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
			// 将分页数据和记录总数设置到分页结果对象中
			page.setData(list);
			page.setRowCount(rowCount);
			return page;
	}
	
	@Override
	public PageFinder<MoreBusinessSummary> pageListMoneySummaryDay(Query q) {
			PageFinder<MoreBusinessSummary> page = new PageFinder<MoreBusinessSummary>();
			List<MoreBusinessSummary> list = null;
			long rowCount = 0L;
			try {
				// 调用dao查询满足条件的分页数据
				list = statementDao.listMoneySummaryDay(q);
				// 调用dao统计满足条件的记录总数
				rowCount = statementDao.countMoneySummaryDay(q);
				
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
			// 将分页数据和记录总数设置到分页结果对象中
			page.setData(list);
			page.setRowCount(rowCount);
			return page;
	}

	@Override
	public List<MoreBusinessSummary> listMoneySummaryDefault(MoreBusinessSummary mbs) {
		List<MoreBusinessSummary> list = new ArrayList<MoreBusinessSummary>();
		try {
			// 调用dao查询满足条件的分页数据
			list = statementDao.listMoneySummaryDefault(mbs);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MoreBusinessSummary>(0) : list;
		return list;
	}

	@Override
	public List<MoreBusinessSummary> listMoneySummaryMonthExport(MoreBusinessSummary mbs) {
		List<MoreBusinessSummary> list = new ArrayList<MoreBusinessSummary>();
		try {
			// 调用dao查询满足条件的分页数据
			list = statementDao.listMoneySummaryMonthExport(mbs);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MoreBusinessSummary>(0) : list;
		return list;
	}

	@Override
	public List<MoreBusinessSummary> listMoneySummaryDayExport(MoreBusinessSummary mbs) {
		List<MoreBusinessSummary> list = new ArrayList<MoreBusinessSummary>();
		try {
			// 调用dao查询满足条件的分页数据
			list = statementDao.listMoneySummaryDayExport(mbs);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MoreBusinessSummary>(0) : list;
		return list;
	}
}