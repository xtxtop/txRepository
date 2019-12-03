package cn.com.shopec.core.order.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.CheckAccounts;
import cn.com.shopec.core.finace.model.CheckAccountsView;
import cn.com.shopec.core.order.model.Order;
import cn.com.shopec.core.order.model.OrderCountVo;
import cn.com.shopec.core.order.vo.OrderAmountDayVo;
import cn.com.shopec.core.order.vo.OrderCarUsedVo;
import cn.com.shopec.core.order.vo.OrderReportFormVo;
import cn.com.shopec.core.order.vo.OrderReportStatisticalVo;
import cn.com.shopec.core.order.vo.OrderStatisticalVo;
import cn.com.shopec.core.quartz.model.RunDaily;
import cn.com.shopec.core.resource.model.Park;
import cn.com.shopec.core.statement.model.CarRpts;
import cn.com.shopec.core.statement.model.Details;
import cn.com.shopec.core.statement.model.GroupRpts;
import cn.com.shopec.core.statement.model.Money;
import cn.com.shopec.core.statement.model.ParkRpts;
import cn.com.shopec.core.statement.model.Summary;

/**
 * 订单表 数据库处理类
 */
public interface OrderDao extends BaseDao<Order, String> {
	/**
	 * @author zln 根据车辆编号和预约中的订单确定取车的订单
	 */
	public Order getOrderByCarNo(String carNo);

	/**
	 * @author zln 获取取车点的历史记录
	 */
	public List<Park> getByMemberNoTake(String memberNo);

	/**
	 * @author zln 获取还车点的历史记录
	 */
	public List<Park> getByMemberNoReturn(String memberNo);

	/**
	 * @author dyh 判断当前是否有正在进行中的订单
	 */
	public int judgeNowadayOrder(String memberNO);

	/**
	 * @author dyh 得到当前的订单
	 */
	public List<Order> getNowadayOrderByMemberNo(String memberNo);

	/**
	 * 添加首次开门时间
	 */
	public int startOrdeOpenCarDoorTime(String orderNo, Date openCarDoorTime);

	/** 添加计费时间（只设定一次） */
	public int setStartBillingTime(String orderNo, Date startBillingTime);

	/**
	 * 记录第一次开车门时间及开始计费时间，同时订单状态改为“已计费”
	 * 
	 * @param orderNo
	 * @param datetime
	 * @param memberNo
	 * @return
	 */
	public int firsttimeOpenCarDoorAndStartBilling(String orderNo, Date datetime, String memberNo);

	/**
	 * 根据车辆编号，修改订单表，记录第一次开车门时间及开始计费时间，同时订单状态改为“已计费”
	 * 
	 * @param carNo
	 * @param datetime
	 * @return
	 */
	public int updateFirsttimeOpenCarDoorAndStartBillingByCarNo(String carNo, Date datetime);

	/**
	 * 得到所有开始自动计费的订单,并进行相应的处理 条件：1、订单状态为1（已预约）的订单；
	 */
	public List<Order> getAllAutoBeginCharging();

	/**
	 * 得到所有自动取消计费的订单，并进行相应的处理 条件： 1、订单状态为2（已计费）的订单；
	 */
	public List<Order> getAllAutoEndCharging();

	/**
	 * 获取会员的欠款金额
	 */
	public Double getAmountByMemberNo(String memberNo);

	/**
	 * 获取会员的订单数
	 */
	public Long getOrderNumByMemberNo(String memberNo);

	/**
	 * 查询当前状态为订单中和已完成的最新订单信息
	 * 
	 * @param q
	 * @return
	 */
	public List<Order> getIdleCar(Query q);

	/**
	 * 统计当前状态为订单中和已完成的最新订单信息
	 * 
	 * @param q
	 * @return
	 */
	public Long countIdleCar(Query q);

	/*
	 * 获取本月的博士后的已用时长
	 */
	public int getOrderMinutesByMemberNo(Query q);

	/**
	 * 查询可以开票的订单
	 * 
	 * @param q
	 * @return
	 */
	public List<Order> queryAllInvoice(Query q);

	/**
	 * 查询可以开票的订单
	 * 
	 * @param q
	 * @return
	 */
	public List<Order> pageListInvoice(Query q);

	/**
	 * 查询可以开票的订单
	 * 
	 * @param q
	 * @return
	 */
	public Long countInvoice(Query q);

	/**
	 * 取消订单
	 * 
	 * @param orderNo
	 * @param cancelTime
	 * @return
	 */
	public int cancelOrder(String orderNo, Date cancelTime);

	/**
	 * 查询会员的最新订单
	 * 
	 * @param memberNo
	 * @return
	 */
	public List<Order> notPaidOrder(String memberNo);

	/**
	 * 根据条件查询数据
	 * 
	 * @param q
	 * @return
	 */
	public List<Order> queryAlls(Query q);

	/**
	 * 判断用户是否有未支付和正在进行中的订单，不能申请保证金退款 zln
	 */
	public List<Order> getOrderNoRefundByMemberNo(String memberNo);

	/**
	 * 最近结束/取消的订单，距离当前时间，应当要超过20天。这个20天，应该是来自系统参数。未超过20天的也不能申请退款 zln
	 */
	public Order getOrderNewNoRefundByMemberNo(String memberNo);

	/**
	 * 统计当前驶出的车辆
	 * 
	 * @param q
	 * @return
	 */
	public Long countscCar(Query q);

	/**
	 * 根据车辆编号查找当前最新订单
	 */
	public Order getOrderNowByCarNo(String carNo);

	/**
	 * 获取财务对账列表信息
	 */
	public List<String> pageListCheckAccounts(Query q);

	/**
	 * 获取财务对账列表信息数
	 */
	public long countCheckAccounts(Query q);

	/**
	 * 获取单个会员的对账信息
	 */
	public CheckAccounts getCheckAccountsByMember(@Param("cA") CheckAccounts cA);

	public List<String> listCheckAccounts(@Param("cA") CheckAccounts checkAccounts);

	public List<CheckAccountsView> pageListCheckAccountsView(Query q);

	public long countCheckAccountsView(Query q);

	public List<CheckAccountsView> listCheckAccountsView(@Param("cAV") CheckAccountsView checkAccountsView);

	/**
	 * 汇总信息
	 */
	public List<Summary> pageListSummary(Query q);

	public long countSummary(Query q);

	public Double getPackAmountPostDoctor(String companyId, Date date, Date date2);

	/**
	 * 汇总信息导出
	 */
	public List<Summary> getSummaryList(@Param("summary") Summary summary);

	/**
	 * 财务明细信息
	 */
	public List<Details> pageListDetails(Query q);

	public long countDetails(Query q);

	/**
	 * 财务明细导出
	 */
	public List<Details> getDetailsList(@Param("details") Details details);

	public Order getLatelyOrderByCarNo(String carNo);

	/**
	 * 运行日报获取日订单数
	 */
	public Long getOrderNum(Date date2, Date date1);

	/**
	 * 运行日报获取日支付订单数和金额
	 */
	public RunDaily getRunDaily(Date date2, Date date1);

	/*
	 * 财务场站汇总信息
	 */
	public List<ParkRpts> pageListParkRpts(Query q);

	// 财务场站汇总导出信息
	public List<ParkRpts> getParkRptsList(@Param("parkRpts") ParkRpts parkRpts);

	/*
	 * 
	 * 财务车辆信息展示
	 */
	public List<CarRpts> pageListCarRpts(Query q);

	/*
	 * 
	 * 财务车辆汇总信息导出
	 */
	public List<CarRpts> getCarRptsList(@Param("carRpts") CarRpts carRpts);

	/*
	 * 
	 * 财务 集团 信息展示
	 */
	public List<GroupRpts> pageListGroupRpts(Query q);

	// 财务 集团 导出
	public List<GroupRpts> getGroupRptsList(@Param("groupRpts") GroupRpts groupRpts);

	/**
	 * 根据车辆编号查询车辆据现在时间最近的一笔订单（状态为已完成或者已取消）
	 * 
	 * @param carNo
	 * @return
	 */
	public Order getOrderNewestByCarNo(String carNo);

	public List<Order> getNowDayAllOrder(String startTime, String endTime);

	List<Map<String, Object>> getRealTimeInOrderNum();

	/**
	 * 查询前5的站点
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<String> getorderCount(String startTime, String endTime);

	/**
	 * 根据站点查询订单数
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer getorderCountlogctonList(String startTime, String endTime, String loacton);

	/**
	 * 根据站点查询订单金额
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer getorderMoneyCountlogctonList(String startTime, String endTime, String loacton);

	public OrderCountVo getorderAutCount(String startTime, String endTime);

	public Integer getorderCancelCount(String startTime, String endTime);

	/**
	 * 查询当前时间段的订单数量
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer getorderUserCountList(String startTime, String endTime);

	/**
	 * 查询当前时间段的订单总金额
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer getorderUserMoneyList(String startTime, String endTime);

	/**
	 * 查询当前时间段的订单总时长
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer getorderUserTimeList(String startTime, String endTime);

	/**
	 * 查找订单统计数据（报表）
	 */
	public OrderReportFormVo orderNumberNs(Query q);

	/**
	 * 根据条件查找订单统计数据（月、天）
	 */
	public OrderReportFormVo orderNumberReport(Query q);

	public OrderReportFormVo getOrderFirstDayData(Query q);

	public OrderReportFormVo getOrderFirstWeekDayData(Query q);

	/**
	 * 根据条件查找订单统计数据（周）
	 */
	public OrderReportFormVo orderNumberReportWeek(Query q);

	public OrderReportStatisticalVo orderNumberReportStatWeek(Query q);

	/**
	 * 根据条件查找订单统计数据（每日合计）
	 */
	public OrderReportFormVo orderNumberReportNs(Query q);

	public List<Order> getOrderPagedListPayableAmount(Query q);

	public List<Order> getAllAutoStartOrderChargings();

	public Integer updateOrderOvers(List<Order> list);

	/**
	 * 获取收入订单合计总额
	 * 
	 * @param money
	 * @return
	 */
	Double getOrderIncomeTotal(@Param("money") Money money);

	/**
	 * 获取订单的会员数
	 * 
	 * @return
	 */
	Integer countOrderMember();

	/**
	 * 获取交易统计的集合
	 * 
	 * @return
	 */
	Map<String, Object> getTransactionCountMap();

	/**
	 * 获取近10天的订单数
	 * 
	 * @return
	 */
	List<Map<String, Object>> getOrderDay10CountMap();

	/**
	 * 超额订单数
	 * 
	 * @return
	 */
	Integer countExcessOrder();

	public Order getOrderWaring(String memberNo);

	public List<Order> pageListW(Query q);

	/**
	 * 订单汇总---新
	 */
	public List<OrderStatisticalVo> getOrderStatisticalList(Query q);

	public long countOrderStatistical(Query q);

	public List<OrderStatisticalVo> getOrderExportStatList(@Param("orderVo") OrderStatisticalVo orderVo);

	/**
	 * 场站汇总 新
	 */
	public List<ParkRpts> listParkSummary(Query q);

	public long countParkSummary(Query q);

	public List<ParkRpts> listParkSummaryExport(@Param("parkRpts") ParkRpts parkRpts);

	// 订单销售量和订单销售额按日
	OrderAmountDayVo getOrderPayAmountData(String time);// 统计订单付款金额

	OrderAmountDayVo getOrderPayAmountDataFirst(String time);// 统计订单付款金额
																// 统计X轴第一天日期之前的数值，包括第一天

	OrderAmountDayVo getOrderAmountAndBalanceData(String time);// 统计订单金额和余额抵扣金额

	OrderAmountDayVo getOrderAmountAndBalanceDataFirst(String time);// 统计订单金额和余额抵扣金额
																	// 统计X轴第一天日期之前的数值，包括第一天

	// 订单销售量和订单销售额按周
	OrderReportStatisticalVo getOrderPayAmountWeekData(Query q);// 统计订单付款金额

	OrderReportStatisticalVo getOrderAmountAndBalanceWeekData(Query q);// 统计订单金额和余额抵扣金额

	OrderAmountDayVo getOrderAmountAndBalanceWeekDataFirst(String time);

	OrderAmountDayVo getOrderPayAmountWeekDataFirst(String time);

	// 订单销售量和订单销售额按月统计和累计
	OrderReportStatisticalVo orderNumberReportMonth(Query q);// 按月统计销售量

	OrderReportStatisticalVo getOrderPayAmountMonthData(String time);// 按月统计订单付款金额

	OrderReportStatisticalVo getOrderAmountAndBalanceMonthData(String time);// 按月统计订单金额和余额抵扣金额

	OrderReportStatisticalVo getFirstMonthOrderData(Query q);// 先统计X轴第一月之前的新增订单数、完成和取消订单数

	OrderReportStatisticalVo getOrderPayAmountFirstMonth(String time);// 统计订单付款金额
																		// 统计X轴第一月日期之前的数值，包括第一月

	OrderReportStatisticalVo getOrderAmountAndBalanceFirstMonth(String time);// 统计X轴第一月之前的订单金额和余额抵扣额，包括第一月

	List<OrderCarUsedVo> getCarOrderCountMap();
}