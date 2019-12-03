package cn.com.shopec.core.finace.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.CheckAccounts;
import cn.com.shopec.core.finace.model.Invoice;

/**
 * 发票申请表 数据库处理类
 */
public interface InvoiceDao extends BaseDao<Invoice,String> {
/**
 * @author zln 获取一个月内的开票记录
 * */
	List<Invoice> getInvoiceListByMemberMonth(Query q);
	/**
	 * @author zln 获取更多的开票记录
	 * */
List<Invoice> getMoreMonthMyInvoice(Query q);
/**
 * @author zln 获取更多的开票记录总数
 * */
	long countMoreInvoice(Query q);
	/**
	 *获取财务对账会员某一时期的开票金额 
	 * */
Double getInvoiceAmountByMember(@Param("cA")CheckAccounts cA);

	List<Invoice> getInvoiceByOrderNo(@Param("orderNo")String orderNo);

	Invoice getInvoiceDetailByOrderNo(String orderNo);
	
}
